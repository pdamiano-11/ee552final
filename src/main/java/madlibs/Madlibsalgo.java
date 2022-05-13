package madlibs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import opennlp.tools.postag.*;
import opennlp.tools.tokenize.*;
import java.io.*;

public class Madlibsalgo {
    private List<String> initialText = new ArrayList<>();
    private List<String> textWithBlanks = new ArrayList<>();
    public List<String> textNewWords = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private Map<String, Integer> tagMap = new HashMap<>();

    public Madlibsalgo(List<String> text) {
        initialText = preprocess(text);

        tags.add("NOUN");
        tags.add("PROPN");
        tags.add("ADJ");
        tags.add("VERB");
        tags.add("ADV");

        for (String tag : tags) {
            tagMap.put(tag, 0);
        }
    }

    private List<String> preprocess(List<String> text) {
        for (int i = 0; i < text.size(); i++) {
            String row = text.get(i);
            row = row.replaceAll("\\p{Punct}", "");
            text.set(i, row);
        }
        return text;
    }

    public List<String> createMadLibs() {

        try {
            String folder = new File("").getAbsolutePath();
            InputStream isToken = new FileInputStream(folder.concat("/opennlp_models/opennlp-en-ud-ewt-tokens-1.0-1.9.3.bin"));
            TokenizerModel tokenModel = new TokenizerModel(isToken);
            Tokenizer tkn = new TokenizerME(tokenModel);
            InputStream isPos = new FileInputStream(folder.concat("/opennlp_models/opennlp-en-ud-ewt-pos-1.0-1.9.3.bin"));
            POSModel posModel = new POSModel(isPos);
            POSTaggerME pos = new POSTaggerME(posModel);

            
            for (String row : initialText) {
                String[] rowTokens = tkn.tokenize(row);
                String[] rowTags = pos.tag(rowTokens);
                
                String initialTag = tags.get(0);
                int wordIndex = findWordIndex(rowTags);
                while (wordIndex == -1 && initialTag != tags.get(0)) {
                    wordIndex = findWordIndex(rowTags);
                }
                if (wordIndex != -1) {
                    rowTokens[wordIndex] = "________(" + rowTags[wordIndex] + ")";
                    String removedTag = tags.get(tags.size() - 1);
                    int count = tagMap.get(removedTag);
                    count++;
                    tagMap.put(removedTag, count);
                }
                textWithBlanks.add(String.join(" ", rowTokens));
            }
            isPos.close();
            isToken.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return textWithBlanks;
    }

    public Map<String, Integer> returnTagMap() {
        return tagMap;
    }

    private int findWordIndex(String[] t) {
        String pos = tags.get(0);
        int res = -1;
        for (int i = 0; i < t.length; i++) {
            if (pos.equalsIgnoreCase(t[i])) {
                res = i;
                break;
            }
        }
        tags.remove(0);
        tags.add(pos);
        return res;
    }

    public List<String> insertWords(Map<String, List<String>> newWords) {

        String newLine;
        if (textNewWords.size() == 0) {
            for (String line : textWithBlanks) {
                int tagFront = line.indexOf("(");
                int tagBack = line.indexOf(")");
                String tag = line.substring(tagFront + 1, tagBack);
                List<String> words = newWords.get(tag);
            
                if (words == null || words.size() <= 0) {
                    newLine = line;
                } else {
                    newLine = line.replace("________", words.get(0) + " ");
                    words.remove(0);
                }
                newWords.put(tag, words);
                textNewWords.add(newLine);
            }
        }
        return textNewWords;
    }
    public static void main(String[] args) {
        
    }
}

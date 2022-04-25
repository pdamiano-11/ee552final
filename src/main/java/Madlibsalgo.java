import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import opennlp.tools.postag.*;
import opennlp.tools.tokenize.*;
import java.io.*;

public class Madlibsalgo {
    private List<String> initialText = new ArrayList<>();
    private List<String> textWithBlanks = new ArrayList<>();
    private List<String> textNewWords = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private Map<String, Integer> tagMap = new HashMap<>();

    public Madlibsalgo(List<String> text) {
        initialText = preprocess(text);

        tags.add("NOUN");
        tags.add("PROPN");
        tags.add("ADJ");
        tags.add("VERB");
        tags.add("DET");

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

    public String createMadLibs() {

        try {
            InputStream isToken = new FileInputStream("C:/Users/ptrda/opennlp_models/opennlp-en-ud-ewt-tokens-1.0-1.9.3.bin");
            TokenizerModel tokenModel = new TokenizerModel(isToken);
            Tokenizer tkn = new TokenizerME(tokenModel);
            InputStream isPos = new FileInputStream("C:/Users/ptrda/opennlp_models/opennlp-en-ud-ewt-pos-1.0-1.9.3.bin");
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

        return String.join(" ", textWithBlanks);
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

    public List<String> insertWords(List<String> words) {

        for (String line : textWithBlanks) {
            break;
        }

        return textNewWords;
    }
    public static void main(String[] args) {
        
    }
}

package madlibs;

// Importing needed libraries
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import opennlp.tools.postag.*;
import opennlp.tools.tokenize.*;
import java.io.*;

public class Madlibsalgo {
    private List<String> initialText = new ArrayList<>();
    private List<String> textWithBlanks = new ArrayList<>();
    public List<String> textNewWords = new ArrayList<>(); // public so its accessible by the UI for reset purposes
    private List<String> tags = new ArrayList<>(); // the parts of speech tags
    private Map<String, Integer> tagMap = new HashMap<>(); // stores parts of speech removed and how many

    public Madlibsalgo(List<String> text) {
        initialText = preprocess(text); 

        // adding the five main parts of speech to look for
        tags.add("NOUN");
        tags.add("PROPN");
        tags.add("ADJ");
        tags.add("VERB");
        tags.add("ADV");

        for (String tag : tags) {
            tagMap.put(tag, 0); // initializing tag map with keys
        }
    }

    private List<String> preprocess(List<String> text) { // removing punctuation
        for (int i = 0; i < text.size(); i++) {
            String row = text.get(i);
            row = row.replaceAll("\\p{Punct}", "");
            text.set(i, row);
        }
        return text;
    }

    public List<String> createMadLibs() { // main mad libs function to get blanks and remove words

        try { // loading in all the models like tokenizer and part of pseech model
            String folder = new File("").getAbsolutePath();
            InputStream isToken = new FileInputStream(folder.concat("/opennlp_models/opennlp-en-ud-ewt-tokens-1.0-1.9.3.bin"));
            TokenizerModel tokenModel = new TokenizerModel(isToken);
            Tokenizer tkn = new TokenizerME(tokenModel);
            InputStream isPos = new FileInputStream(folder.concat("/opennlp_models/opennlp-en-ud-ewt-pos-1.0-1.9.3.bin"));
            POSModel posModel = new POSModel(isPos);
            POSTaggerME pos = new POSTaggerME(posModel);

            
            for (String row : initialText) { // tokenizing and tagging each row of text from the input
                String[] rowTokens = tkn.tokenize(row);
                String[] rowTags = pos.tag(rowTokens);
                
                // This part of the algorithm removes the words. First, the left most tag in the tags list
                // is taken and used to search for a word that matches the tag. If so, it is removed.
                // If not, the algorithm cycles the left most tag to the right side of the list, and tries
                // again. If no word matching any of the five tags is found, the algorithm does not remove 
                // any words, though this is extremely rare.
                String initialTag = tags.get(0);
                int wordIndex = findWordIndex(rowTags);
                while (wordIndex == -1 && initialTag != tags.get(0)) {
                    wordIndex = findWordIndex(rowTags);
                }
                if (wordIndex != -1) { // actual removal of the word
                    rowTokens[wordIndex] = "________(" + rowTags[wordIndex] + ")";
                    String removedTag = tags.get(tags.size() - 1);
                    int count = tagMap.get(removedTag);
                    count++;
                    tagMap.put(removedTag, count); // adding to tag map
                }
                textWithBlanks.add(String.join(" ", rowTokens)); // adding new text with removed word
            }
            isPos.close();
            isToken.close(); // closing the InputStreams
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return textWithBlanks;
    }

    public Map<String, Integer> returnTagMap() {
        return tagMap;
    }

    private int findWordIndex(String[] t) { // method to find index of word based on its POS tag in a String
        String pos = tags.get(0);
        int res = -1;
        for (int i = 0; i < t.length; i++) {
            if (pos.equalsIgnoreCase(t[i])) {
                res = i;
                break;
            }
        }
        tags.remove(0); // cycling the tags list
        tags.add(pos);
        return res;
    }

    public List<String> insertWords(Map<String, List<String>> newWords) { // method to replace blanks with words

        String newLine;
        if (textNewWords.size() == 0) { // conditional to ensure only run once, as void draw() runs multiple times
            for (String line : textWithBlanks) {
                int tagFront = line.indexOf("(");
                int tagBack = line.indexOf(")"); // locating the POS tag for the row
                String tag = line.substring(tagFront + 1, tagBack);
                List<String> words = newWords.get(tag); // getting the words matching the tag in the given user input
            
                if (words == null || words.size() <= 0) { // checking if there are no words in user input
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

    public boolean checkPOS(String[] words, String posInput) { // POS Validator
        boolean valid = true;
        try {
            String folder = new File("").getAbsolutePath();
            InputStream isPos = new FileInputStream(folder.concat("/opennlp_models/opennlp-en-ud-ewt-pos-1.0-1.9.3.bin"));
            POSModel posModel = new POSModel(isPos);
            POSTaggerME pos = new POSTaggerME(posModel);

            String[] wordTag = pos.tag(words);
            for (int i = 0; i < wordTag.length; i++) {
                if (!wordTag[i].equals(posInput)) {
                    valid = false;
                    break;
                }
            }
            isPos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valid;
    }
    public static void main(String[] args) {
        /* List<String> lines = MadLibsFiles.getMadLines("/main.txt");
        Madlibsalgo mla = new Madlibsalgo(lines);
        String[] words = {"no"};
        System.out.println(mla.checkPOS(words, "ADV")); */

    }
}

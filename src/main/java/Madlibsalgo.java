import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import opennlp.tools.postag.*;
import opennlp.tools.tokenize.*;
import java.io.*;

public class Madlibsalgo {
    private List<String> text = new ArrayList<>();
    private List<String> types = new ArrayList<>();
    

    public Madlibsalgo(List<String> text) {
        this.text = preprocess(text);
        types.add("NOUN");
        types.add("PROPN");
        types.add("ADJ");
        types.add("VERB");
        types.add("DET");
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

            
            for (String row : text) {
                String[] rowTokens = tkn.tokenize(row);
                String[] rowTags = pos.tag(rowTokens);
                
                String initialType = types.get(0);
                int wordIndex = findWord(rowTags);
                while (wordIndex == -1 && initialType != types.get(0)) {
                    wordIndex = findWord(rowTags);
                }
                if (wordIndex != -1) {
                    rowTokens[wordIndex] = "________(" + rowTags[wordIndex] + ")";
                }
                text.set(text.indexOf(row), String.join(" ", rowTokens));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return String.join(" ", text);
    }

    private int findWord(String[] tags) {
        String pos = types.get(0);
        int res = -1;
        for (int i = 0; i < tags.length; i++) {
            if (pos.equalsIgnoreCase(tags[i])) {
                res = i;
                break;
            }
        }
        types.remove(0);
        types.add(pos);
        return res;
    }
    public static void main(String[] args) {
        
    }
}

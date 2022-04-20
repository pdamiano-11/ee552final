import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import opennlp.tools.postag.*;
import java.io.*;

public class Madlibsalgo {
    private List<String> text = new ArrayList<>();
    

    public Madlibsalgo(List<String> text) {
        this.text = text;
    }

    public List<String> preprocess() {
        for (int i = 0; i < text.size(); i++) {
            String row = text.get(i);
            row = row.replaceAll("\\p{Punct}", "");
            text.set(i, row);
        }
        return text;
    }

    public String removeWords() {
        List<String> types = new ArrayList<>();
        types.add("NN");
        types.add("NNP");
        types.add("JJ");
        types.add("VB");

        try {
            InputStream is = new FileInputStream("C:/Users/ptrda/opennlp_models/opennlp-en-ud-ewt-pos-1.0-1.9.3.bin");
            POSModel pos = new POSModel(is);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "test";
    }
    public static void main(String[] args) {
        
    }
}

import java.util.List;

public class Madlibsalgo {
    private String text;

    public Madlibsalgo(String text) {
        this.text = text;
    }

    public String[] preprocess() {
        String[] rows = text.strip().split(" ");
        return rows;
    }
    public static void main(String[] args) {
        
    }
}

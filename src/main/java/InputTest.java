import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class InputTest {
    public static void main(String[] args) throws Exception{
        File myFile = new File("C:/Users/ptrda/Desktop/ee552/ee552final/src/main/java/test.txt");
        
        BufferedReader bf = new BufferedReader(new FileReader(myFile));
        List<String> text = new ArrayList<>();
        String s;

        while((s = bf.readLine()) != null) {
            text.add(s);
        }

        Madlibsalgo mla = new Madlibsalgo(text);
        String fin = mla.createMadLibs();
        System.out.println("f:" + fin);

    }
}

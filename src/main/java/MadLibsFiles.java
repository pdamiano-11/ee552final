import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MadLibsFiles {
    public List<String> getMadLines(String filePath) {
        List<String> MadLines = new ArrayList<>(); //Initialize list of madlib lines
        try {
            File myFile = new File(filePath); //Initialize file
            String line; //Initialize string to hold line

            BufferedReader read = new BufferedReader(new FileReader(myFile)); //open new bufferedreader
        
            while((line = read.readLine()) != null) {
                MadLines.add(line); //Add each line untill no lines left
            }
        
            read.close(); //close the buffered reader
        } catch (Exception e) {
            e.printStackTrace();
        }

        return MadLines; //return the list of each line
    }
    public static void writeToFile(String filename, ArrayList<String> contents) throws IOException{
        // use a BufferedWriter to write the string contents to the file
        try(BufferedWriter out = new BufferedWriter(new FileWriter(filename))){
            for (String temp : contents) {
                out.write(temp);
            }
        }
      }
}
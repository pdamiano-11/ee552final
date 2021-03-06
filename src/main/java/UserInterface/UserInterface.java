package UserInterface;

import processing.core.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import madlibs.*;
import java.util.Map;
import java.util.HashMap;

public class UserInterface extends PApplet {
    // This is the UI File for our Java Final Project
    // Team Members: Brianna Garland, Peter Damianov, Marko Piasevoli, David Barlow

    // Image Imports
    PImage logo;
    PImage genButton;
    PImage begin;
    PImage secHea;
    PImage nextButt;
    PImage resultButton;
    PImage resetButt;
    PImage openFile;
    PImage checkmark;

    // Screen Flags
    boolean startScreen = true;
    boolean displayScreen = false;
    boolean inputScreen1 = false;
    boolean inputScreen2 = false;
    boolean resultScreen = false;
    boolean fillBlanks = true;

    // User input variables
    String input = ""; // text input that user sees on screen
    String[] inptWords; // words saved from input
    char t = 'a'; // type of input to get: a for ADJ, n for NOUN, v for VERB, p for PROPN, d for ADV
    Map<String, List<String>> mp = new HashMap<>(); // main user input map for tags to inputted words

    // Getting text from file and creating madlibs object from it
    List<String> lines = MadLibsFiles.getMadLines("/main.txt");
    Madlibsalgo mla = new Madlibsalgo(lines);
    List<String> blankText = mla.createMadLibs();
    Map<String, Integer> tagMap = mla.returnTagMap();

    // getting number of each POS from algorithm tag map
    int numAdj = tagMap.get("ADJ");
    int numNoun = tagMap.get("NOUN");
    int numVerb = tagMap.get("VERB");
    int numPnoun = tagMap.get("PROPN");
    int numAdV = tagMap.get("ADV");

    // all lists for user input words to put in for user input map, as well as temporary list
    List<String> adj;
    List<String> noun;
    List<String> verb;
    List<String> propn;
    List<String> adv;
    List<String> temp;

    @Override
	public void settings() {
		size(800, 600);
	}

    @Override
    public void setup() { // loading in all images
        String absPath = new File("").getAbsolutePath();
        String folderPath = absPath.concat("/src/main/java/UserInterface/");
        logo = loadImage(folderPath.concat("logo.png"));
        genButton = loadImage(folderPath.concat("generate.png"));
        secHea = loadImage(folderPath.concat("sectionHeader.png"));
        nextButt = loadImage(folderPath.concat("next.png"));
        resultButton = loadImage(folderPath.concat("resultButton.png"));
        resetButt = loadImage(folderPath.concat("reset.png"));
        openFile = loadImage(folderPath.concat("open.png"));
        checkmark = loadImage(folderPath.concat("checkmark.jpg"));
        begin = loadImage(folderPath.concat("begin.png"));
    }

    @Override
    public void draw() {
        background(255);

        // ---------------------------------- Start Screen Logic----------------------------------------
        if (startScreen) {
            image(logo, width / 4, height / 4, logo.width / 2, logo.height / 2);
            image(genButton, (width / 5) + 25, height * 2/3 + 25, genButton.width / 2, genButton.height / 2);
            

            // generate button was| pressed
            if ((mousePressed)
                    && ((mouseX < ((width / 5) + 25) + genButton.width / 2) && (mouseX > (width / 5) + 25))) {// checking if mouse is pressed and if x is in button range
                if ((mouseY < ((height * 2 / 3) + 25 + genButton.height / 2)) && (mouseY > (height * 2 / 3) + 25)) { // checking if mouse y is in button range
                    startScreen = false;
                    displayScreen = true;

                }
            }
        }
        // ---------------------------------- Display Screen Logic----------------------------------------   
        if(displayScreen) { // display text with blanks
            image(logo, 0, 0, logo.width * 3 / 8, logo.height * 3 / 8);
            textSize(30);
            fill(0);
            text("Your Mad Libs Text:", 400, 100);

            int tmpY = 200;
            textSize(12);
            fill(0);
            for (String line : blankText) {
                text(line, 15, tmpY);
                tmpY = tmpY + 20;
            }
            tmpY = 205;
            image(begin, 20, 450, begin.width/3, begin.height/3);

            if ((mousePressed) && ((mouseX < 340) && (mouseX > 20))) { // checking if mouse is pressed and if x is in button range
                if ((mouseY < 690) && (mouseY > 450)) { // checking if mouse y is in button range
                    if (displayScreen) {
                        inputScreen1 = true;
                        displayScreen = false;
                    }
                }
            }
            
        }

        // ---------------------------------- Input Screen 1 Logic----------------------------------------
        if (inputScreen1) {
            image(logo, 0, 0, logo.width * 3 / 8, logo.height * 3 / 8);

            // section header placement
            textSize(20);

            image(secHea, 15, 200, secHea.width * 2 / 5, secHea.height / 2);
            fill(3, 152, 158); // dark color
            text("Enter  " + numAdj + "  Adjectives (UP)", 25, 250);
            fill(130, 191, 194); // light color
            stroke(130, 191, 194);
            rect(17, 265, 248, numAdj * 35);
            fill(255);
            // These next few conditionals are for user input, and apply to all 5 POS inputs below
            if (t == 'a' && inputScreen1) { // checking if adjective and on first input screen
                if (numAdj > 0) { // checking if we can get any adjectives from user
                    text(input, 20, 285); // allowing user to enter text
                    if (keyCode == UP) { // submission
                        inptWords = input.split("\n"); // get words in a list
                        if (mla.checkPOS(inptWords, "ADJ")) { // check each word POS
                            t = 'n'; // switch to NOUN
                            temp = Arrays.asList(inptWords);
                            adj = new ArrayList<>(temp);
                            mp.put("ADJ", adj); // save to user input map
                            input = "";
                        }
                        else { // if incorrect POS, begin again and clear input
                            t = 'a';
                            input = "";
                        }
                    }
                } else { // if no adjectives to get, go to NOUN
                    t = 'n';
                }
            }
            // These image methods act as submission checkers to know that the input is submitted,
            // and they apply to all POS input boxes
            if (t != 'a' && inputScreen1) {
                image(checkmark, 130, 290, checkmark.width/15, checkmark.height/15);
            }

            image(secHea, 275, 200, secHea.width * 2 / 5, secHea.height / 2);
            fill(3, 152, 158); // dark color
            text("Enter  " + numNoun + "  Nouns (DOWN)", 285, 250);
            fill(130, 191, 194); // light color
            stroke(130, 191, 194);
            rect(277, 265, 248, numNoun * 35);
            fill(255);
            if (t == 'n' && inputScreen1) { // same as adjectives but for nouns
                if (numNoun > 0) {
                    text(input, 280, 285);
                    if (keyCode == DOWN) {
                        inptWords = input.split("\n");
                        if (mla.checkPOS(inptWords, "NOUN")) {
                            t = 'v';
                            temp = Arrays.asList(inptWords);
                            noun = new ArrayList<>(temp);
                            mp.put("NOUN", noun);
                            input = "";
                        }
                        else {
                            t = 'n';
                            input = "";
                        }
                    }
                } else {
                    t = 'v';
                }
            }
            if (t != 'a' && t != 'n' && inputScreen1) {
                image(checkmark, 390, 290, checkmark.width/15, checkmark.height/15);
            }

            image(secHea, 535, 200, secHea.width * 2 / 5, secHea.height / 2);
            fill(3, 152, 158); // dark color
            text("Enter  " + numVerb + "  Verbs (UP)", 545, 250);
            fill(130, 191, 194); // light color
            stroke(130, 191, 194);
            rect(537, 265, 248, numVerb * 35);
            fill(255);
            if (t == 'v' && inputScreen1) {
                if (numVerb > 0) {
                    text(input, 540, 285);
                    if (keyCode == UP) {
                        inptWords = input.split("\n");
                        if (mla.checkPOS(inptWords, "VERB")) {
                            t = 'p';
                            temp = Arrays.asList(inptWords);
                            verb = new ArrayList<>(temp);
                            mp.put("VERB", verb);
                            input = "";
                        }
                        else {
                            t = 'v';
                            input = "";
                        }
                    }
                } else {
                    t = 'p';
                }
            }
            if (t != 'a' && t != 'n' && t != 'v' && inputScreen1) {
                image(checkmark, 650, 290, checkmark.width/15, checkmark.height/15);
            }

            // next button
            image(nextButt, (width / 3) + 300 , (height * 5/6) , nextButt.width / 4, nextButt.height / 4);

            // next button was pressed
            if ((mousePressed) && ((mouseX < ((width / 3) + 300) + nextButt.width / 3) && (mouseX > (width / 3) + 300))) { // checking if mouse is pressed and if x is in button range
                if ((mouseY < ((height * 5 / 6) + nextButt.height / 3)) && (mouseY > ((height * 5 / 6)))) { // checking if mouse y is in button range
                    if (inputScreen1) {
                        inputScreen2 = true;
                        inputScreen1 = false;
                        t = 'p';
                        keyCode = 0;
                    }
                }
            }

        }

        // ---------------------------------- Input Screen 2 Logic----------------------------------------
        if (inputScreen2) {
            image(logo, 0, 0, logo.width * 3 / 8, logo.height * 3 / 8);
            // section header
            image(secHea, 145, 200, secHea.width * 2 / 5, secHea.height / 2);
            fill(3, 152, 158); // dark color
            text("Enter  " + numPnoun + "  Proper Nouns", 155, 250);
            fill(130, 191, 194); // light color
            stroke(130, 191, 194);
            rect(147, 265, 248, numPnoun * 35);
            fill(255);
            if (t == 'p' && inputScreen2) {
                if (numPnoun > 0) {
                    text(input, 150, 285);
                    if (keyCode == DOWN) {
                        inptWords = input.split("\n");
                        if (mla.checkPOS(inptWords, "PROPN")) {
                            t = 'd';
                            temp = Arrays.asList(inptWords);
                            propn = new ArrayList<>(temp);
                            mp.put("PROPN", propn);
                            input = "";
                        }
                        else {
                            t = 'p';
                            input = "";
                        }
                    }
                } else {
                    t = 'd';
                }
            }
            if (t != 'p' && inputScreen2) {
                image(checkmark, 260, 290, checkmark.width/15, checkmark.height/15);
            }

            image(secHea, 405, 200, secHea.width * 2 / 5, secHea.height / 2);
            fill(3, 152, 158); // dark color
            text("Enter  " + numAdV + "  Adverbs (UP)", 415, 250);
            fill(130, 191, 194); // light color
            stroke(130, 191, 194);
            rect(407, 265, 248, numAdV * 35);
            fill(255);
            if (t == 'd' && inputScreen2) {
                if (numAdV > 0) {
                    text(input, 410, 285);
                    if (keyCode == UP) {
                        inptWords = input.split("\n");
                        if (mla.checkPOS(inptWords, "ADV")) {
                            t = 'a';
                            temp = Arrays.asList(inptWords);
                            adv = new ArrayList<>(temp);
                            mp.put("ADV", adv);
                            input = "";
                        }
                        else {
                            t = 'd';
                            input = "";
                        }
                    }
                } else {
                    t = 'a';
                }
            }
            if (t != 'p' && t != 'd' && inputScreen2) {
                image(checkmark, 520, 290, checkmark.width/15, checkmark.height/15);
            }

            // result button
            image(resultButton, (width / 3), (height * 5 / 6) + 30, resultButton.width / 3,
                    resultButton.height / 3);

            // result button was pressed
            if ((mousePressed)
                    && ((mouseX < ((width / 3)) + resultButton.width / 3) && (mouseX > (width / 3)))) { // checking if mouse is pressed and if x is in button range
                if ((mouseY < ((height * 5 / 6) + 30 + resultButton.height / 3))
                        && (mouseY > ((height * 5 / 6) + 30))) { // checking if mouse y is in button range
                    if (inputScreen2) {
                        inputScreen2 = false;
                        resultScreen = true;
                    }
                }
            }
        }

        // ---------------------------------- Result Screen Logic----------------------------------------
        if (resultScreen) {
            image(logo, 0, 0, logo.width * 3 / 8, logo.height * 3 / 8);
            image(resetButt, width - 100, 60, resetButt.width / 2, resetButt.height / 2);
            image(openFile, width - 300, 65, openFile.width / 2, openFile.height / 2);

            List<String> finalText = mla.insertWords(mp); // getting user inputted words inserted into text with blanks
            int tempY = 205;
            textSize(12);
            fill(0);
            for (String line : finalText) { // printing out the result text
                text(line, 15, tempY);
                tempY = tempY + 20;
            }
            tempY = 205;

            // reset button pressed
            if ((mousePressed) && ((mouseX < ((width - 100)) + resetButt.width / 2) && (mouseX > (width - 100)))) { // checking if mouse is pressed and if x is in button range
                if ((mouseY < ((60) + resetButt.height / 2)) && (mouseY > ((60)))) { // checking if mouse y is in button range
                    if (resultScreen) {
                        mla.textNewWords = new ArrayList<>(); // emptying final results
                        t = 'a'; // resetting type to adj
                        keyCode = 0; // resetting keyboard input
                        startScreen = true;
                        resultScreen = false;

                    }
                }
            }

            // Open File Button Pressed
            if ((mousePressed) && ((mouseX < ((width - 300)) + openFile.width / 2) && (mouseX > (width - 300)))) { // checking if mouse is pressed and if x is in button range
                if ((mouseY < ((65) + openFile.height / 2)) && (mouseY > ((65)))) { // checking if mouse y is in button range
                    if (resultScreen) {
                        try {
                            MadLibsFiles.writeToFile("results.txt", finalText); // writing final results to file
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // temp just to show the button working
                        text("File Opened", width / 2, height / 2);
                        fill(0, 0, 0);

                    }
                }
            }
        }

    }

    @Override
    public void keyTyped() { // check which key typed and perform action
        if (key == BACKSPACE) {
            if (input.length() != 0) {
                input = input.substring(0, input.length() - 1);
            }
        } else {
            input += key; // driver code behind user seeing text appear on screen
        }
    }



    public static void main(String[] args) {
        PApplet.main(UserInterface.class.getName()); // running processing core in java
    }
}

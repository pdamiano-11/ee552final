package UserInterface;

import processing.core.*;
import java.io.*;

public class UserInterface extends PApplet {
    // This is the UI File for our Java Final Project
    // Team Members: Brianna Garland, Peter Damianov, Marko Piasevoli, David Barlow

    // Image Imports
    PImage logo;
    PImage genButton;
    PImage secHea;
    PImage nextButt;
    PImage resultButton;
    PImage resetButt;
    PImage openFile;

    // Screen Flags
    boolean startScreen = true;
    boolean inputScreen1 = false;
    boolean inputScreen2 = false;
    boolean resultScreen = false;

    // these are temporary but should be sent in from the other file
    int numAdj = 2;
    int numNoun = 2;
    int numVerb = 2;
    int numPnoun = 2;
    int numAdV = 2;

    @Override
	public void settings() {
		size(800, 600);
	}

    @Override
    public void setup() {
        String absPath = new File("").getAbsolutePath();
        String folderPath = absPath.concat("/src/main/java/UserInterface/");
        logo = loadImage(folderPath.concat("logo.png"));
        genButton = loadImage(folderPath.concat("generate.png"));
        secHea = loadImage(folderPath.concat("sectionHeader.png"));
        nextButt = loadImage(folderPath.concat("next.png"));
        resultButton = loadImage(folderPath.concat("resultButton.png"));
        resetButt = loadImage(folderPath.concat("reset.png"));
        openFile = loadImage(folderPath.concat("open.png"));
    }

    @Override
    public void draw() {
        background(255);

        // ---------------------------------- Start Screen Logic
        // ----------------------------------------
        if (startScreen) {
            image(logo, width / 5, height / 5, logo.width / 2, logo.height / 2);
            image(genButton, (width / 5) + 25, height * 2/3, genButton.width / 2, genButton.height / 2);

            // generate button was| pressed
            if ((mousePressed)
                    && ((mouseX < ((width / 5) + 25) + genButton.width / 2) && (mouseX > (width / 5) + 25))) {// checking if mouse is pressed and if x is in button range
                if ((mouseY < ((height * 2 / 3) + genButton.height / 2)) && (mouseY > (height * 2 / 3))) { // checking if mouse y is in button range
                    startScreen = false;
                    inputScreen1 = true;
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
            text("Enter  " + numAdj + "  Adjectives", 55, 250);
            fill(130, 191, 194); // light color
            stroke(130, 191, 194);
            rect(17, 265, 248, numAdj * 35);

            image(secHea, 275, 200, secHea.width * 2 / 5, secHea.height / 2);
            fill(3, 152, 158); // dark color
            text("Enter  " + numNoun + "  Nouns", 315, 250);
            fill(130, 191, 194); // light color
            stroke(130, 191, 194);
            rect(277, 265, 248, numNoun * 35);

            image(secHea, 535, 200, secHea.width * 2 / 5, secHea.height / 2);
            fill(3, 152, 158); // dark color
            text("Enter  " + numVerb + "  Verbs", 575, 250);
            fill(130, 191, 194); // light color
            stroke(130, 191, 194);
            rect(537, 265, 248, numVerb * 35);

            // next button
            image(nextButt, (width / 3) + 40 , (height * 5/6) , nextButt.width / 4, nextButt.height / 4);

            // next button was pressed
            if ((mousePressed) && ((mouseX < ((width / 3) + 40) + nextButt.width / 3) && (mouseX > (width / 3) + 40))) { // checking if mouse is pressed and if x is in button range
                if ((mouseY < ((height * 5 / 6) + nextButt.height / 3)) && (mouseY > ((height * 5 / 6)))) { // checking if mouse y is in button range
                    if (inputScreen1) {
                        inputScreen2 = true;
                        inputScreen1 = false;
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
            text("Enter  " + numPnoun + "  Proper Nouns", 165, 250);
            fill(130, 191, 194); // light color
            stroke(130, 191, 194);
            rect(147, 265, 248, numPnoun * 35);

            image(secHea, 405, 200, secHea.width * 2 / 5, secHea.height / 2);
            fill(3, 152, 158); // dark color
            text("Enter  " + numAdV + "  Adverbs", 435, 250);
            fill(130, 191, 194); // light color
            stroke(130, 191, 194);
            rect(407, 265, 248, numVerb * 35);

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
            rect(40, 200, 700, 350); // This is the area to display the text from the filled in story

            // reset button pressed
            if ((mousePressed) && ((mouseX < ((width - 100)) + resetButt.width / 2) && (mouseX > (width - 100)))) { // checking if mouse is pressed and if x is in button range
                if ((mouseY < ((60) + resetButt.height / 2)) && (mouseY > ((60)))) { // checking if mouse y is in button range
                    if (resultScreen) {
                        startScreen = true;
                        resultScreen = false;

                        // INSERT ANY OTHER RESET LOGIC
                    }
                }
            }

            // Open File Button Pressed
            if ((mousePressed) && ((mouseX < ((width - 300)) + openFile.width / 2) && (mouseX > (width - 300)))) { // checking if mouse is pressed and if x is in button range
                if ((mouseY < ((65) + openFile.height / 2)) && (mouseY > ((65)))) { // checking if mouse y is in button range
                    if (resultScreen) {
                        // INSERT LOGIC TO OPEN THE NEW FILE

                        // temp just to show the button working
                        text("File Opened", width / 2, height / 2);
                        fill(0, 0, 0);
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        PApplet.main(UserInterface.class.getName());
    }
}

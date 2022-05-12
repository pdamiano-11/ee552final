//This is the UI File for our Java Final Project 
//Team Members: Brianna Garland, Peter Damianov, Marko Piasevoli, David Barlow

//Image Imports
PImage logo;
PImage genButton;
PImage secHea;
PImage nextButt;
PImage resultButton;
PImage resetButt;
PImage openFile;

//Screen Flags
boolean startScreen = true;
boolean inputScreen1 = false;
boolean inputScreen2 = false;
boolean resultScreen = false;

//these are temporary but should be sent in from the other file 
int numAdj=13;
int numNoun=10;
int numVerb=8;
int numPnoun=15;
int numAdV=6;

void setup() {
  size(1000, 1000);
  logo = loadImage("logo.png"); 
  genButton = loadImage("generate.png");
  secHea = loadImage("sectionHeader.png");
  nextButt= loadImage("next.png");
  resultButton = loadImage("resultButton.png");
  resetButt=loadImage("reset.png");
  openFile= loadImage("open.png");
}

void draw() {
  background(255);
  
  List<String> test = MadLibsFiles.getMadLines("/src/main/java/madlibs/test.txt");
  for (String line : test) {
    System.out.print(line);
  }
 
  //----------------------------------   Start Screen Logic  ----------------------------------------
  if (startScreen){
    image(logo, width/4, height/4, logo.width/2, logo.height/2);
    image(genButton, (width/4)+23, height*2/4, genButton.width/2, genButton.height/2);
    
    //generate button was| pressed                                                                   
    if((mousePressed)  &&  ((mouseX<((width/4)+23)+genButton.width/2)&&(mouseX>(width/4)+23))) { // checking if mouse is pressed and if x is in button range 
      if((mouseY<((height*2/4)+genButton.height/2)) && (mouseY>(height*2/4))){ //checking if mouse y is in button range 
        startScreen=false; 
        inputScreen1 = true; 
      }
    }
  }
  
  //----------------------------------   Input Screen 1 Logic  ----------------------------------------
  if(inputScreen1){
    image(logo, 0, 0, logo.width*3/8, logo.height*3/8);
    
    //section header placement 
  
    image(secHea, 20, 200, secHea.width/2, secHea.height/2);
    textSize(30);
    fill(3, 152, 158); //dark color
    text("Enter  "+numAdj+"  Adjectives", 55, 250); 
    fill(130, 191, 194); //light color
    stroke(130,191,194);
    rect(22,263,310,numAdj*35);

    image(secHea, 350, 200, secHea.width/2, secHea.height/2);
    fill(3, 152, 158); //dark color
    text("Enter  "+numNoun+"  Nouns", 400, 250); 
    fill(130, 191, 194); //light color
    stroke(130,191,194);
    rect(352,263,310,numNoun*35);
    
    image(secHea, 680, 200, secHea.width/2, secHea.height/2);
    fill(3, 152, 158); //dark color
    text("Enter  "+numVerb+"  Verbs", 730, 250);
    fill(130, 191, 194); //light color
    stroke(130,191,194);
    rect(682,263,310,numVerb*35);
    
    //next button
    image(nextButt, (width/3)+40, (height*4/5)+130, nextButt.width/3, nextButt.height/3);
    
    //next button was pressed                                                                   
    if((mousePressed)  &&  ((mouseX<((width/3)+40)+nextButt.width/3)&&(mouseX>(width/3)+40))) { // checking if mouse is pressed and if x is in button range 
      if((mouseY<((height*4/5)+130+nextButt.height/3)) && (mouseY>((height*4/5)+130))){ //checking if mouse y is in button range 
        if(inputScreen1){       
          inputScreen2=true; 
          inputScreen1 = false; 
        }
      }
    }
    
    
  }
  
  //----------------------------------   Input Screen 2 Logic  ----------------------------------------
  if(inputScreen2){
    image(logo, 0, 0, logo.width*3/8, logo.height*3/8);
    //section header 
    image(secHea, 135, 200, secHea.width/2, secHea.height/2);
    fill(3, 152, 158); //dark color
    text("Enter  "+numPnoun+"  Proper Nouns", 150, 250);
    fill(130, 191, 194); //light color
    stroke(130,191,194);
    rect(138,263,310,numPnoun*35);
    
    image(secHea, 535, 200, secHea.width/2, secHea.height/2);
    fill(3, 152, 158); //dark color
    text("Enter  "+numAdV+"  Adverbs", 580, 250);
    fill(130, 191, 194); //light color
    stroke(130,191,194);
    rect(538,263,310,numVerb*35);
    
     //result button
    image(resultButton, (width/3)+40, (height*4/5)+80, resultButton.width/3, resultButton.height/3);
    
    //result button was pressed                                                                   
    if((mousePressed)  &&  ((mouseX<((width/3)+40)+resultButton.width/3)&&(mouseX>(width/3)+40))) { // checking if mouse is pressed and if x is in button range 
      if((mouseY<((height*4/5)+80+resultButton.height/3)) && (mouseY>((height*4/5)+80))){ //checking if mouse y is in button range 
        if(inputScreen2){
          inputScreen2=false; 
          resultScreen = true; 
        }
      }
    }
  }
  
  //----------------------------------   Result Screen Logic  ----------------------------------------
  if(resultScreen){
    image(logo, 0, 0, logo.width*3/8, logo.height*3/8);
    image(resetButt, width-100, 60, resetButt.width/2, resetButt.height/2);
    image(openFile, width-300, 65, openFile.width/2, openFile.height/2);
    rect( 40, 200, 900, 700);  // This is the area to display the text from the filled in story
    
    //reset button pressed 
    if((mousePressed)  &&  ((mouseX<((width-100))+resetButt.width/2)&&(mouseX>(width-100)))) { // checking if mouse is pressed and if x is in button range 
      if((mouseY<((60)+resetButt.height/2)) && (mouseY>((60)))){ //checking if mouse y is in button range 
        if(resultScreen){
          startScreen=true; 
          resultScreen = false; 
          
          //INSERT ANY OTHER RESET LOGIC 
        }
      }
    }
    
    //Open File Button Pressed
    if((mousePressed)  &&  ((mouseX<((width-300))+openFile.width/2)&&(mouseX>(width-300)))) { // checking if mouse is pressed and if x is in button range 
      if((mouseY<((65)+openFile.height/2)) && (mouseY>((65)))){ //checking if mouse y is in button range 
        if(resultScreen){
          //INSERT LOGIC TO OPEN THE NEW FILE 
          
          //temp just to show the button working 
          text("File Opened", width/2, height/2); 
          fill(0,0,0);
        }
      }
    }
  } 
}

/*
 * Names: Lily Phan and Nancy Zhu
 * Teacher: Ms. Krasteva
 * Date: 11/04/2020
 * Assignment: This project is meant to complete the 2020 ICS3U ISP game. This program will display an interactive hangman game!
 */

//import statements
import java.awt.*; //enables access to AWT package
import hsa.Console; //enables access to HSA console
import java.io.*; //enables access to IO files
import javax.swing.JOptionPane; //enables access to use JOptionPane

public class Hangman
{
    //Declaration Section
    Console c; //console
    String word; //chooses the word the user needs to guess
    String choice = (""); //user choice on what they want to do; also determines the course of action in multiple methods
    String name = (""); //used to hold user entered name
    String[] underScores; //holds the the value the user needs to guess (eg. the word hello will be stored in this array as _ _ _ _ _ until the user guesses each letter)
    boolean userWin = false; //detects whether the user wins or not during each game. Will be used to trigger responses in multiple methods
    boolean playedBefore = false; //detects whether the user has played before or not
    final int POSSIBLE_WORDS = 50; //determines the number of possible words the user can guess
    int oldIndex = 50; //Stops the same word from being used twice, starting value is 50 because the index isn't able to be 50 so the initialization doesn't affect the chooseWord method the first time it's run
    int points = 0; //counts user points
    int drawBodyPart = 0; //determines whether to draw the next body part or not

    public Hangman ()  //class constructor
    {
	c = new Console ("Hangman ISP Game"); //creates the console
    }


    private void title ()  //title method
    {
	c.clear (); //clears the screen
	c.print (' ', 36); //Centres the title
	c.println ("Hangman"); //Title
	c.println (""); //indent
    }


    private void pauseProgram ()  //pauseProgram method
    {
	c.println ("\nPress any key to continue..."); //prompts user input
	c.getChar (); //Pauses program, waits for user to enter any keyboard press
    }


    private String[] checkChar (char userLetter)  //blackbox method checkChar
    {
	boolean check = false; //Decides if a body part will be drawn
	String userWord = ""; //variable to determine how much of the word has been guessed
	for (int i = 0 ; i < word.length () ; i++) //loops through all the letters in the mystery word to compare the user entered letter
	{
	    if (word.charAt (i) == userLetter) //If the letter is entered matches up with the letter in the word, this executes
	    {
		underScores [i] = (userLetter + " "); //Replaces underscore with letter
		check = true; //tells the if statement later on not to draw a body part
		if (choice.equals ("2")) //tests which level the user is playing on
		{
		    points += 1; //increases point by 1 for correct letter
		}
		else if (choice.equals ("3")) //tests which level the user is playing on
		{
		    points += 3; //increases point by 3 for correct letter
		}
	    }
	    userWord = userWord + underScores [i].trim (); //puts the user's current guessing state into a String (eg. if the user guessed "a" in halo, then this String would hold _a__)
	}
	if (userWord.equalsIgnoreCase (word)) //tests if the user has guessed every letter correctly
	{
	    userWin = true; //breaks out of the while loop in executeGame method
	    if (choice.equals ("2")) //tests which level the user is on
	    {
		points += 5; //increases point by 5 for guessing correctly
	    }
	    else if (choice.equals ("3")) //tests which level the user is on
	    {
		points += 10; //increases point by 10 for guessing correctly
	    }
	}
	if (!check) //tests if another body part should be drawn
	{
	    drawBodyPart++; //Adds to the number of body parts drawn
	    if (drawBodyPart >= 8)//tests if the last body part was drawn
	    {
		userWin = true; //breaks out of the loop in executeGame method
	    }
	}
	return underScores; //Returns array
    }


    public void splashScreen ()  //splashScreen method
    {
	 double timeAnimation = 0.0; //used to time how long the animation should run
	//gradient
	//rectangles go from left to right
	c.setColor (new Color (69, 182, 254)); //lightest blue
	c.fillRect (0, 0, 128, 500); //background
	c.setColor (new Color (55, 146, 203)); //darker blue
	c.fillRect (128, 0, 128, 500); //background
	c.setColor (new Color (41, 109, 152)); //darker blue
	c.fillRect (256, 0, 128, 500); //background
	c.setColor (new Color (28, 72, 102)); //darker blue
	c.fillRect (384, 0, 128, 500); //background
	c.setColor (new Color (14, 36, 51)); //darkest blue
	c.fillRect (512, 0, 128, 500); //background

	//main border
	c.setColor (Color.white);
	c.fillRect (13, 13, 614, 5); //top border
	c.fillRect (13, 484, 614, 5); //bottom border
	c.fillRect (13, 13, 5, 476); //left border
	c.fillRect (627, 13, 5, 476); //right border

	//word bank
	c.setFont (new Font ("Century Schoolbook", 4, 30)); //sets font
	c.fillRect (300, 285, 300, 150); //border
	c.setColor (new Color (28, 72, 102)); //dark blue
	c.drawRect (308, 293, 284, 134); //draws the blue border on the word bank
	c.drawString ("A B C D E F G H I", 320, 330); //alphabet
	c.drawString ("J K L M N O P Q R", 320, 370);
	c.drawString ("S T U V W X Y Z", 330, 410);

	//title and credits
	c.setColor (Color.white);
	for (int i = 0 ; i < 315 ; i += 45) //loop to repeatedly draw underscores
	{
	    c.fillRect (300 + i, 150, 35, 5); //draws underscores for hangman word
	    if (i < 180) //stops drawing the underscores for word once there are enough underscores drawn
	    {
		c.fillRect (365 + i, 220, 35, 5); //draws underscores for game word
	    }
	}

	c.setFont (new Font ("Trebuchet MS", Font.BOLD, 46)); //sets font
	c.drawString ("H A N G M A N", 302, 143); //draws title
	c.drawString ("G A M E", 368, 213); //title

	c.setFont (new Font ("Trebuchet MS", Font.PLAIN, 15)); //sets font
	c.drawString ("Produced By Lily Phan & Nancy Zhu", 380, 45); //draws our names

	c.setColor (new Color (204, 0, 0)); //red
	c.setFont (new Font ("Sans Serif", 3, 40)); //makes the font bolded and italic
	c.drawString ("/", 325, 335); //cross over the a
	c.drawString ("/", 445, 375); //cross over the n
	c.drawString ("/", 535, 335); //cross over the h
	c.drawString ("/", 415, 375); //cross over the m
	c.drawString ("/", 505, 335); //cross over the g
	c.drawString ("/", 445, 335); //cross over the e
	c.drawString ("/", 333, 415); //cross over the s
	c.drawString ("/", 393, 415); //cross over the u
	c.drawString ("/", 385, 335); //cross over the c
	c.drawString ("/", 455, 415); //cross over the w
	c.drawString ("/", 535, 375); //cross over the q
	c.drawString ("/", 565, 375); //cross over the r
	c.drawString ("/", 565, 335); //cross over the i
	c.drawString ("/", 350, 375); //cross over the k

	//stickman animation
	while (true) //runs for a short amount of time; loops through the stickman moving right and left
	{
	    for (int i = 0 ; i < 20 ; i++) //loop to animate the stickman moving right
	    {
		c.setColor (new Color (55, 146, 203)); //matches colour to the background
		c.fillRect (128, 0, 128, 500); //redraws the background
		c.setColor (Color.white);
		c.fillRect (13, 13, 614, 5); //redraws the top border
		c.fillRect (13, 484, 614, 5); //redraws the bottom border
		c.fillRect (50, 460, 180, 5); //bottom of stickman stand
		c.fillRect (80, 80, 5, 380); //tall stand part
		c.fillRect (80, 80, 110, 5); //short horizontal part of the stand
		c.fillRect (190, 80, 5, 50); //part of the stand which holds the hangman
		c.fillOval (154, 118, 80, 80); //head
		c.setFont (new Font ("Arial", 4, 30)); //sets the font
		c.setColor (new Color (55, 146, 203)); //changes colour to match the background
		c.drawString ("X X", 170, 175); //draws the eyes
		c.setColor (Color.white); //changes colour
		c.fillRect (179 + i, 180, 5, 158); //draws the body and makes it move

		for (int x = 0 ; x < 5 ; x++) //loop to draw the thick arms and legs
		{
		    c.drawLine (137 + x + i, 425, 176 + x + i, 335); //left leg
		    c.drawLine (225 + x + i, 425, 176 + x + i, 335); //right leg
		    c.drawLine (137 + x + i, 300, 176 + x + i, 210); //left arm
		    c.drawLine (225 + x + i, 300, 176 + x + i, 210); //right arm
		}
		try
		{
		    Thread.sleep (50); //times the animation to make the hangman move slower
		    timeAnimation += 0.05; //adds to the timer
		}
		catch (Exception e)
		{
		}
	    }
	    for (int i = 20 ; i > 0 ; i--) //loop to animate the stickman moving left
	    {
		c.setColor (new Color (55, 146, 203)); //matches the background colour
		c.fillRect (128, 0, 128, 500); //redraws the background
		c.setColor (Color.white);
		c.fillRect (13, 13, 614, 5); //redraws the top border
		c.fillRect (13, 484, 614, 5); //redraws the bottom border
		c.fillRect (50, 460, 180, 5); //bottom of stickman stand
		c.fillRect (80, 80, 5, 380); //tall stand part
		c.fillRect (80, 80, 110, 5); //short horizontal part of the stand
		c.fillRect (190, 80, 5, 50); //part of the stand which holds the hangman
		c.fillOval (154, 118, 80, 80); //head
		c.setFont (new Font ("Arial", 4, 30)); //sets font
		c.setColor (new Color (55, 146, 203)); //sets colour to match the background
		c.drawString ("X X", 170, 175); //draws the eyes
		c.setColor (Color.white); //changes colour
		c.fillRect (179 + i, 180, 5, 158); //draws the body
		for (int x = 5 ; x > 0 ; x--) //lop to draw the thick arms and legs
		{
		    c.drawLine (137 + x + i, 425, 176 + x + i, 335); //left leg
		    c.drawLine (225 + x + i, 425, 176 + x + i, 335); //right leg
		    c.drawLine (137 + x + i, 300, 176 + x + i, 210); //left arm
		    c.drawLine (225 + x + i, 300, 176 + x + i, 210); //right leg
		}
		try
		{
		    Thread.sleep (50); //times the animation to make the hangman move slower
		    timeAnimation += 0.05; //adds to the timer
		}
		catch (Exception e)
		{
		}
	    }
	    if (timeAnimation > 2.2) //tests how long the animation has been going for
	    {
		break; //breaks out of the while loop
	    }
	}

	   while (true) //runs until the user has entered a valid username
	{
	    boolean areSpaces = false; //checks if user entered any spaces in their username; this is disallowed since it messes with the highScore method
	    name = JOptionPane.showInputDialog (null, "Please enter a name that is less than 20 characters", "Username", JOptionPane.QUESTION_MESSAGE); //JOptionPane popup asking the user for their username
	    //Credits for JOptionPane.showInputDialog() line: https://mkyong.com/swing/java-swing-joptionpane-showinputdialog-example/
	    //please note that Ms. Krasteva has approved of this line of code via email
	    if (name == null || name.length () > 20 || name.equals (""))  //user must enter a username and it must be less than 20 characters (user cannot press the x or cancel to delete the popup)
	    {
		JOptionPane.showMessageDialog (null, "Invalid username. Please try again.", "Error", JOptionPane.ERROR_MESSAGE); //error message
	    }
	    else
	    {
		for (int i = 0 ; i < name.length () ; i++) //loop to check if there are spaces in the username; this program will not allow spaces since it messes up the program during the high score method
		{
		    if (name.charAt (i) == ' ') //checks for spaces
		    {
			areSpaces = true; //tells the program that the user has spaces in their username
			JOptionPane.showMessageDialog (null, "Sorry, you can't have spaces in your username.", "Error", JOptionPane.ERROR_MESSAGE); //error message
			break; //breaks out of the for loop
		    }
		}
		if (!areSpaces) //checks if the program found no spaces
		{
		    break; //breaks out of the while loop
		}
	    }
	}
    }


    public void chooseWord ()  //chooseWord method
    {
	int index; //holds the index of the word the user has to choose
	String[] data = new String [POSSIBLE_WORDS]; //creates a string to hold all the possible words
	String fileName = ""; //used to hold the name of the file
	String line = ""; //used to read in data from the file
	if (choice.equals ("2")) //tests if user wants to play level 1
	{
	    fileName = "HangmanLvl1.txt"; //changes the file name
	}
	else if (choice.equals ("3")) //tests if user wants to play level 2
	{
	    fileName = "HangmanLvl2.txt"; //changes the file name
	}
	try //tries to open the file
	{
	    BufferedReader input = new BufferedReader (new FileReader (fileName)); //opens the file (the file opened depends on the level the user chose)
	    for (int count = 0 ; count < POSSIBLE_WORDS ; count++) //we know how big the file is so we don't need to use a while loop; this loop reads data in from the file into an array
	    {
		line = input.readLine (); //Reads the file
		data [count] = line; //Saves data into array
	    }
	    do
	    {
		index = (int) (POSSIBLE_WORDS * Math.random ()); //chooses a random index
	    }
	    while (index == oldIndex); //runs at least once; ensures that the word chosen is not the same as the previous word
	    word = data [index].toLowerCase ().trim (); //Assigns word to one of the strings and trims it in case an extra space was added and sets all characters to lowercase
	    oldIndex = index; //sets oldIndex to be equal to the old index
	    underScores = new String [word.length ()]; //Underscores initialization
	    for (int i = 0 ; i < word.length () ; i++) //Decides the number of underscores
	    {
		underScores [i] = "_ "; //Puts underscores into array
	    }
	}
	catch (IOException e)  //in the case something goes wrong with opening the file, or file doesn't exist
	{
	    JOptionPane.showMessageDialog (null, "Sorry, something went wrong with the files.", "Error", JOptionPane.ERROR_MESSAGE); //error message
	}
    }


    public void instructions ()  //instructions method
    {
	title (); //calls title method to clear the screen
	c.setTextBackgroundColor (Color.pink); //sets text background colour to pink
	c.println ("How To Play Hangman"); //title
	c.setTextBackgroundColor (Color.white);
	c.println ("Welcome to the Hangman Game! In this game, you need to guess letters missing in"); //instructions on how hangman should be played
	c.println ("a mystery word. You must continuously guess the characters until you get the");
	c.println ("word. Every wrong letter results in another body part being drawn on the");
	c.println ("hangman. The game ends when all the body parts are drawn, or when you guess the");
	c.println ("word correctly!");
	c.setTextBackgroundColor (Color.yellow); //sets text background colour to yellow
	c.println ("Level Descriptions"); //title
	c.setTextBackgroundColor (Color.white);
	c.println ("There are two levels. In level one, you will be given a word that is between 4-6"); //instructions on how this game is played
	c.println ("letters. You will be given 1 letter in the word to start for free. You have the");
	c.println ("option to have 1 hint, but you will be deducted 5 points if you use it. In level");
	c.println ("two, you will be given a word that is between 8-12 letters. You will not be");
	c.println ("given a free starter hint, but you can choose to have up to 2 hints.");
	c.setTextBackgroundColor (Color.green); //sets text background colour to green
	c.println ("Point System:"); //title
	c.setTextBackgroundColor (Color.white);
	c.println ("Every Correct Letter in Level 1: +1 Point"); //point system
	c.println ("Every Correct Letter in Level 2: +3 Points");
	c.println ("Word Correctly Guessed in Level 1: +5 Points");
	c.println ("Word Correctly Guessed in Level 2: +10 Points");
	c.println ("Use of Hint In Any Level: -5 Points");
	c.println ("\nNote that your progress will only be saved during the time that you play this"); //note about game progress
	c.println ("game. You will not be able to continue your progress if you exit the program.");
	pauseProgram (); //calls pauseProgram method
    }


    public void mainMenu ()  //mainMenu method
    {
	while (true) //runs until the user enters an appropriate value
	{
	    title (); //executes title
	    c.println ("Please choose one of the following options:"); //shows menu options
	    c.print (' ', 5); //indent
	    c.println ("1. Instructions"); //option
	    c.print (' ', 5); //indent
	    c.println ("2. Play Level 1"); //option
	    c.print (' ', 5); //indent
	    c.println ("3. Play Level 2"); //option
	    c.print (' ', 5); //indent
	    c.println ("4. High Scores"); //option
	    c.print (' ', 5); //indent
	    c.println ("5. Exit"); //option
	    c.println (" "); //indent

	    choice = c.readLine (); //reads in user choice
	    //this is a string instead of a char to ensure the user doesn't spam the keyboard and the program crashes
	    if (!(choice.equals ("1") || choice.equals ("2") || choice.equals ("3") || choice.equals ("4") || choice.equals ("5"))) //tests if user has not entered any of the options
	    {
		JOptionPane.showMessageDialog (null, "Invalid option chosen. Please try again.", "Error", JOptionPane.ERROR_MESSAGE); //error message
	    }
	    else
	    {
		break; //if user entered choice is valid, the while loop is broken
	    }
	}
    }


    public void executeGame ()  //executeGame method
    {
	int index = 0; //used to control which index is chosen in the oldChars method
	boolean ask = true; //user to control how long the program should ask the user to enter a letter
	userWin = false; //resets userWin to false after each game to indicate the user has not won yet
	drawBodyPart = 0; //resets drawBodyPart to 0 after each game; used to control which body part of the stickman is drawn
	String letter = " "; //used to hold the user entered letter
	String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"}; //used to hold all the possible letters
	String[] oldChars = {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "}; //used to hold all the old letters the user has chosen

	while (!userWin) //runs while the user has not guessed the word yet or hasn't won yet
	{
	    ask = true; //sets ask to true during each gameplay to ask the user for a letter
	    title (); //calls title method to clear the screen
	    //Draws body parts and the hangman stand
	    c.setColor (Color.black);
	    c.fillRect (263, 375, 135, 5); //bottom of stickman stand
	    c.fillRect (368, 70, 5, 310); //tall stand part
	    c.fillRect (293, 70, 80, 5); //horizontal short part of the stickman stand
	    c.fillRect (290, 70, 5, 50); //the part of the stickman stand which hangs the man
	    if (drawBodyPart == 1) //tests if user entered a wrong letter
	    {
		c.fillOval (254, 83, 80, 80); //head
	    }
	    else if (drawBodyPart == 2) //tests if user entered a wrong letter again; body parts are redrawn since title clears them afterwards
	    {
		c.fillOval (254, 83, 80, 80); //head
		c.fillRect (289, 125, 5, 148); //Body
	    }
	    else if (drawBodyPart == 3) //tests if user entered a wrong letter again
	    {
		c.fillOval (254, 83, 80, 80); //head
		c.fillRect (289, 125, 5, 148); //Body
		for (int x = 5 ; x > 0 ; x--) //loop to control line thickness
		{
		    c.drawLine (247 + x, 360, 286 + x, 270); //left leg
		}
	    }
	    else if (drawBodyPart == 4) //tests if user entered a wrong letter again
	    {
		c.fillOval (254, 83, 80, 80); //head
		c.fillRect (289, 125, 5, 148); //Body
		for (int x = 5 ; x > 0 ; x--) //loop to control line thickness
		{
		    c.drawLine (247 + x, 360, 286 + x, 270); //left leg
		    c.drawLine (335 + x, 360, 286 + x, 270); //right leg
		}
	    }
	    else if (drawBodyPart == 5) //tests if user entered a wrong letter again
	    {
		c.fillOval (254, 83, 80, 80); //head
		c.fillRect (289, 125, 5, 148); //Body
		for (int x = 5 ; x > 0 ; x--) //loop to control line thickness
		{
		    c.drawLine (247 + x, 360, 286 + x, 270); //left leg
		    c.drawLine (335 + x, 360, 286 + x, 270); //right leg
		    c.drawLine (247 + x, 255, 286 + x, 165); //left arm
		}
	    }
	    else if (drawBodyPart == 6) //tests if user entered a wrong letter again
	    {
		c.fillOval (254, 83, 80, 80); //head
		c.fillRect (289, 125, 5, 148); //Body
		for (int x = 5 ; x > 0 ; x--) //loop to control line thickness
		{
		    c.drawLine (247 + x, 360, 286 + x, 270); //left leg
		    c.drawLine (335 + x, 360, 286 + x, 270); //right leg
		    c.drawLine (247 + x, 255, 286 + x, 165); //left arm
		    c.drawLine (335 + x, 255, 286 + x, 165); //right arm
		}
	    }
	    else if (drawBodyPart == 7) //tests if user entered a wrong letter again
	    {
		c.fillOval (254, 83, 80, 80); //head
		c.fillRect (289, 125, 5, 148); //Body
		for (int x = 5 ; x > 0 ; x--) //loop to control line thickness
		{
		    c.drawLine (247 + x, 360, 286 + x, 270); //left leg
		    c.drawLine (335 + x, 360, 286 + x, 270); //right leg
		    c.drawLine (247 + x, 255, 286 + x, 165); //left arm
		    c.drawLine (335 + x, 255, 286 + x, 165); //right arm
		}
		c.setFont (new Font ("Arial", 4, 30)); //sets font
		c.setColor (Color.white); //changes colour
		c.drawString ("X", 270, 140); //draws the left eye
	    }
	    //draws the letters
	    for (int i = 0 ; i < 13 ; i++) //for loop to output all the possible letters
	    {
		c.setColor (Color.black);
		c.setFont (new Font ("Monospaced", Font.PLAIN, 40)); //we used monospaced so that all the letters could be evenly spaced
		c.drawString (alphabet [i], 35 + i * 45, 420); //outputs the first 13 letters in the top row
		c.drawString (alphabet [i + 13], 35 + i * 45, 465); //outputs the last 13 letters in the bottom row
	    }
	    for (int i = 0 ; i < word.length () ; i++) //for loop to output the current stage the user is at (eg. _ _ l l o)
	    {
		c.setFont (new Font ("Arial", Font.PLAIN, 17)); //sets font
		c.drawString (underScores [i], 5 + i * 17, 80); //outputs the current stage the user is at
	    }
	    while (ask) //runs until the user enters a valid letter
	    {
		c.setCursor (3, 1); //sets the cursor to the 3rd row
		c.print (' ', 80); //erases the entire line
		c.setCursor (3, 1); //sets the cursor to the third row
		c.print ("Enter a letter: "); //prompts user input
		letter = c.readLine (); //reads in a value
		if (letter.length () > 1 || letter.equals ("")) //tests if user entered a value too long or too short
		{
		    JOptionPane.showMessageDialog (null, "Please enter 1 letter.", "Error", JOptionPane.ERROR_MESSAGE); //error message

		}
		else if ((letter.charAt (0) < 'a' || letter.charAt (0) > 'z') && (letter.charAt (0) < 'A' || letter.charAt (0) > 'Z')) //tests if entered value is actually a letter
		{
		    JOptionPane.showMessageDialog (null, "Please enter only letters.", "Error", JOptionPane.ERROR_MESSAGE); //error message
		}
		else //if user entered an appropriate letter
		{
		    boolean saveLetter = true; //boolean to test whether the entered letter should be saved in the array oldChars or not
		    for (int i = 0 ; i < oldChars.length ; i++) //for loop to run through all the Strings in oldChars array
		    {
			if (oldChars [i].equalsIgnoreCase (letter)) //tests if user already entered the letter
			{
			    JOptionPane.showMessageDialog (null, "You already entered this letter!", "Error", JOptionPane.ERROR_MESSAGE); //error message
			    saveLetter = false; //tells program not to save the letter to oldChars array; this prevents duplicate letters being stored in the array
			    break; //breaks out of the loop to stop searching
			}
		    }
		    if (saveLetter) //tests if the letter should be saved in oldChars array; this means that the user entered value is appropriate
		    {
			oldChars [index] = letter; //enters in user entered letter to oldChars array
			index++; //moves the the next index in oldChars
			ask = false; //stops the while loop from asking the user for a letter
		    }
		}
	    }
	    underScores = checkChar (letter.toLowerCase ().charAt (0)); //calls checkChar blackbox method to do the processing
	    //finds the letter the user inputted and replaces it with a blank space
	    for (int i = 0 ; i < alphabet.length ; i++) //loops through the entire alphabet
	    {
		if (letter.equalsIgnoreCase (alphabet [i])) //finds the letter
		{
		    alphabet [i] = " "; //replaces the letter with a blank space
		}
	    }
	}
	c.setColor (Color.white);
	c.fillRect (0, 80, 200, 17); //covers up the old string so that the remaining underscore doesn't show through
	c.setColor (Color.black);
	for (int i = 0 ; i < word.length () ; i++) //draws the letters out one last time since the while loop ends early and doesn't draw the last entered letter
	{
	    c.setFont (new Font ("Arial", Font.PLAIN, 17)); //changes font
	    c.drawString (underScores [i], 5 + i * 17, 80); //outputs the string
	}
	if (drawBodyPart == 8) //tests if the program still needs to draw the last eye
	{
	    c.setFont (new Font ("Arial", 4, 30)); //changes font
	    c.setColor (Color.white); //sets colour to white
	    c.drawString ("X X", 270, 140); //draws both of the eyes
	}
	try
	{
	    Thread.sleep (500); //delays the result screen from outputting so the user can admire the beautiful stickman
	}
	catch (Exception e)
	{
	}
	if (userWin && drawBodyPart < 8) //tests if the user won (if userWin is true and if the whole stickman hasn't been drawn yet)
	{
	    c.setColor (new Color (252, 222, 23)); //yellow
	    c.fillRect (70, 150, 500, 150); //draws yellow rectangle
	    c.setColor (Color.white);
	    c.drawRect (85, 165, 470, 120); //draws white border
	    c.setFont (new Font ("Lucida Bright", Font.BOLD, 33)); //sets font
	    c.drawString ("Congrats, You Won! :)", 140, 210); //congrats message
	}
	else //otherwise the user lost, this outputs
	{
	    c.setColor (Color.red); //red
	    c.fillRect (70, 150, 500, 150); //draws red rectangle
	    c.setColor (Color.white);
	    c.drawRect (85, 165, 470, 120); //draws white border
	    c.setFont (new Font ("Lucida Bright", Font.BOLD, 33)); //sets font
	    c.drawString ("Oops, you lost... :(", 170, 210); //oops message
	}
	//this is outside the if-else statement since this outputs either way
	c.setFont (new Font ("Monospaced", Font.BOLD, 16)); //sets font
	c.drawString ("The word was " + word + ".", 210 - word.length (), 245); //tells user what the word was and centers it
	c.drawString ("Press any key to continue.", 180, 270); //prompts user input
	c.getChar (); //waits for user to press any key
    }


    public void highScore (boolean displayScore)  //highScore method
    {
	String line = ""; //String used to read in data from files
	String[] data; //used to store data from the files
	int[] trackScore; //used to store all scores (from previous plays and from the current user)
	int countFile = 0; //used to count how large the file is
	try //code to update the high scores
	{
	    FileWriter highScore = new FileWriter ("HangmanHighScores.txt", true); //opens the HangmanHighScores.txt file
	    BufferedReader input = new BufferedReader (new FileReader ("HangmanHighScores.txt")); //used to read the file
	    PrintWriter output = new PrintWriter (highScore); //used to write into the HangmanHighScores.txt file
	    line = input.readLine (); //reads the first line
	    while (line != null) //reads how big the file is, as long as the file is not empty
	    {
		countFile++; //counts the number of lines
		line = input.readLine (); //reads the next line
	    }
	    data = new String [countFile]; //makes a String array the size of the file
	    trackScore = new int [countFile]; //makes an int array the size of the file to contain all of the scores (including scores from previous players)
//credits to MCPT for helping with figuring out how to go back to the start of the file to start reading again
	    input.close (); //closes the input
	    input = new BufferedReader (new FileReader ("HangmanHighScores.txt")); //opens the file back up to read it from the start
	    int count = 0; //used to count which line to read
	    while (count < countFile) //runs until count reaches the size of the data array
	    {
		line = input.readLine (); //reads a line in the file
		data [count] = line; //puts line into the array
		count++; //increments count by 1
	    }
	    if (playedBefore) //runs only if the user has played at least once before (in the same game duration; doesn't run if user closes the program and opens it back up again)
	    {
		for (int a = 0 ; a < data.length ; a++)
		{
		    if (data [a].substring (0, data [a].indexOf (" ")).equals (name)) //searches for the player
		    {
			data [a] = (name + " got this many points: " + points); //updates their points in the game
			trackScore [a] = points; //updates their points in the game
			break; //breaks out of the loop
		    }
		}
		PrintWriter highScoreTemp = new PrintWriter (new FileWriter ("HangmanHighScores.txt"));//creates a temporary new file with the same name as the high scores
		for (int i = 0 ; i < data.length ; i++) //this is in a separate for loop since the previous for loop exits early
		{
		    String pointStr = data [i].substring (data [i].indexOf (" got this many points: ") + 23); //separates the number of points from each String in data[]
		    trackScore [i] = Integer.parseInt (pointStr); //parses the points and stores it in trackScore[]
		    highScoreTemp.println (data [i]); //overwrites the HangmanHighScores.txt file to update the high scores in the file
		}
		highScoreTemp.close ();//saves the new updated scores
	    }
	    else //if this is the user's first time playing the game, this runs
	    {
		output.println (name + " got this many points: " + points); //appends the user's information in the file HangmanHighScores.txt
		playedBefore = true; //sets playedBefore to true
	    }
	    if (word == null && displayScore) //tests if user has ever played either level 1 or level 2 before
	    { //this is done because the high scores cannot be sorted properly if the user has never played the game before
		JOptionPane.showMessageDialog (null, "Sorry, you can't see the leaderboard until you play.", "Error", JOptionPane.ERROR_MESSAGE); //error message
	    }
	    else if (displayScore && playedBefore) //tests if the user has played before and wants to display the high scores
	    {
		title (); //executes title method
		//bubble sorting code; sorts out the scores based on points, from highest to lowest
		//website that helped with bubble sorting: https://www.geeksforgeeks.org/bubble-sort/
		for (int i = 0 ; i < trackScore.length - 1 ; i++) //runs through the numbers to compare
		{
		    for (int b = 0 ; b < trackScore.length - i - 1 ; b++) //runs through the numbers to compare
		    {
			if (trackScore [b + 1] > trackScore [b]) //tests if the number to the right of trackScore at [b+1] is greater than the number at trackScore[b]
			{ //the code below continuously sorts the numbers by switching adjacent values around until they are all in order; at the same time, the information in data is being switched around
			    int oldScore = trackScore [b + 1]; //sets the old value
			    String oldWin = data [b + 1]; //sets the old value

			    trackScore [b + 1] = trackScore [b]; //switches the value on the right (b+1) with the one on the left (b) in the array
			    data [b + 1] = data [b]; //switches the value on the right (b+1) with the one on the left (b) in the array

			    trackScore [b] = oldScore; //sets the value at b equal to b+1 old value
			    data [b] = oldWin; //sets the value at b equal to b+1 old value
			}
		    }
		}
		c.println ("Please note, some players may have had the same username as you in the past."); //note to user
		c.setTextBackgroundColor (Color.orange); //sets text background colour
		c.println ("High Scores"); //title
		for (int i = 0 ; i < trackScore.length && i < 10 ; i++) //prints out the top 10 high scores
		{
		    c.setTextBackgroundColor (new Color (255, 234, i * 25)); //gradient
		    c.println ((i + 1) + ". " + data [i]); //scores
		}
		c.setTextBackgroundColor (Color.white);
		c.println ("\nPress c to clear all high score records or any other keyboard press to continue."); //prompts user input
		String clearFile = c.readLine (); //reads in a value from the user on what they want to do
		if (clearFile.equalsIgnoreCase ("c")) //tests if user wants to clear the highScore file
		{
		    highScore = new FileWriter ("HangmanHighScores.txt"); //creates a new fileWriter to overwrite the previous fileWriter
		    output = new PrintWriter (highScore); //creates a new output for the new high score
		    points = 0; //resets user's current points to 0
		    playedBefore = false; //outputs the user's information again
		}
	    }
	    output.close (); //saves the file
	} //error traps in case some of the game data was tampered with or something goes wrong
	catch (IOException e)  //in the case something goes wrong with opening the file
	{
	    JOptionPane.showMessageDialog (null, "Sorry, something went wrong.", "Error", JOptionPane.ERROR_MESSAGE); //error message
	}
	catch (ArrayIndexOutOfBoundsException e)  //in the case something goes wrong with the arrays
	{
	    JOptionPane.showMessageDialog (null, "Sorry, something went wrong.", "Error", JOptionPane.ERROR_MESSAGE); //error message
	}
	catch (NumberFormatException e)  //in the case something goes wrong with the conversion of strings to ints
	{
	    JOptionPane.showMessageDialog (null, "Sorry, something went wrong.", "Error", JOptionPane.ERROR_MESSAGE); //error message
	}
	catch (StringIndexOutOfBoundsException e)  //in the case something goes wrong with the substrings
	{
	    JOptionPane.showMessageDialog (null, "Sorry, something went wrong.", "Error", JOptionPane.ERROR_MESSAGE); //error message
	}
    }


    public void goodbye ()  //goodbye method
    {
	title (); //executes title method
	c.println ("Thank you for playing the Hangman game!"); //goodbye message
	c.println ("\nAll program rights belong to Nancy Zhu & Lily Phan."); //credits
	c.println ("Created November 16, 2020. ICS3UF ISP for Ms. Krasteva."); //program information
	try
	{
	    Thread.sleep (5000); //times the animation
	}
	catch (Exception e)
	{
	}
	System.exit (0); //closes the output screen
    }


    public static void main (String[] args)
    {
	Hangman d = new Hangman (); //creates new Hangman object
	d.splashScreen (); //executes splashScreen method once at the beginning of the program
	while (!d.choice.equals ("5")) //runs until the user wants to exit
	{
	    d.mainMenu (); //executes mainMenu
	    if (d.choice.equals ("1")) //tests if user chooses 1
	    {
		d.instructions (); //executes instructions method
	    }
	    else if (d.choice.equals ("2") || d.choice.equals ("3")) //tests if user wants to play level 1 or 2
	    {
		d.chooseWord (); //executes chooseWord method to choose the word the user needs to guess
		d.executeGame (); //executes executeGame method to enable the user to play the game
		d.highScore (false); //executes highScore method to update the user's high score
	    }
	    else if (d.choice.equals ("4")) //tests if the user wants to display the high scores
	    {
		d.highScore (true); //executes highScore method
	    }
	}
	d.goodbye (); //executes goodbye method once at the end of the program
    } // main method
} // Hangman class

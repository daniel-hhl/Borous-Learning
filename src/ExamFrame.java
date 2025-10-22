/*
 * Name: Daniel Lam
 * Date: June 12, 2024
 * Course Code: ICS3U1/Mrs. Biswas
 * Project Name: Final Summative Project - Computer Assisted Instruction
 * Title: Borous Learning
 * 
 * Description of Program: In this project, we are assigned to create a computer assisted instruction software
 * that a user can interact with to learn about my assigned topic, arrays. This software consists of three main
 * components: lessons, activities, and a final exam. Lessons are displayed with visual references so that
 * students of all learning styles can benefit from this program. Activities are used to help with understanding
 * and build upon knowledge from lessons. Finally, the activities and lessons all lead to the final exam, where
 * the user's knowledge upon the CAI topic is fully challenged.
 * 
 * Skilled used: 
 * - Arrays
 * - Arrays class
 * - Collections class
 * - HTML text formatting in Swing components
 * - ArrayLists
 * - Java GUI Swing components
 * - For Loops
 * - Classes and Objects
 * - Methods
 * - If structures
 * - Scanner objects
 * 
 * Added Features:
 * - Get player image to Face the Proper Direction as they move
 * - Get player image to change if jumping, moving, and stopping 
 * - Add Timing (race to get coins in each level)
 * - Add more accurate timing (5 decimal places)
 * - Add a Menubar - with a number of options (Resume Game, Restart Game, Quit, etc.)
 * - Add Doorways/Portals
 * - Add a pause game button
 * - Add New Levels (with different maps and settings) *Aaron gave me an example of a larger map format
 * - Added HTML formatting to centre text *Bowris Chow assisted me with this
 * - Emptying an array with arrays class //https://stackoverflow.com/questions/4208655/empty-an-array-in-java-processing
 * - Using HTML symbols for "<" // https://www.toptal.com/designers/htmlarrows/math/less-than-sign/
 * - Using a JTextField to automatically wrap tex  https://stackoverflow.com/questions/5766175/word-wrap-in-jbuttons
 * - Using ArrayLists https://www2.lawrence.edu/fast/GREGGJ/CMSC150/062ArrayLists/ArrayLists.html
 * - Using Collections class to shuffle indexes in a list 
 * https://www.geeksforgeeks.org/collections-shuffle-method-in-java-with-examples/
 *
 * Areas of Concern: Frame sizing could be different across devices, refer to submitted video for my screen
 * 
 * Contribution to Assignment:
 * - Student Name: Daniel Lam (from the past)
 * - Java Files: Avatar, ArrayAdventureGUI, Tile
 * - Methods: Most methods within above classes were completed by past Daniel Lam, however, present
 * Daniel Lam has modified much of the game to use the Question class and Scanner class to import
 * questions to be answered, as well as modifying the objective to complete a certain amount of 
 * questions within a decreasing period of time
 * - Percentage: 50%
 * 
 * - Student Name: Daniel Lam (present)
 * - Java Files: ActivitySelection, ExamFrame, FailFrame, Lesson1, Lesson1, Lesson3, LessonSelection,
 * PassFrame, Question, SectionSelection, StartApplication, TitlePage
 * - Methods: all methods within above Java classes
 * - Percentage: 100%
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;

public class ExamFrame extends JFrame implements ActionListener {
	// declare swing widgets
	private JLabel frameBackground = new JLabel(new ImageIcon("frameimages/examPicture.png"));
	private JButton[][] buttonArray = new JButton[2][2];
	private JTextArea questionLabel = new JTextArea();

	// declare exam variables, num of questions correct and num of question
	// completed
	int numCorrect = 0, questionNum = 0;

	// declare array list of question objects
	// https://www2.lawrence.edu/fast/GREGGJ/CMSC150/062ArrayLists/ArrayLists.html
	ArrayList<Question> questionList = new ArrayList<Question>();

	// declare 2 dimensional array list to store and randomize the options for each
	// question
	ArrayList<String> optionList = new ArrayList<String>();

	// lesson variable to show which lesson and activity user has completed
	private int lessonCompleted, activityCompleted;

	// constructor method
	public ExamFrame(int lessonCompleted, int activityCompleted) {
		// set level and activity completed to what it is when it is carried into the
		// constructor method
		this.lessonCompleted = lessonCompleted;
		this.activityCompleted = activityCompleted;

		// call method to assign values to the questions
		randomizeQuestions();

		// call methods to setup frame, labels, and buttons
		placeButtons();

		changeQuestion(questionNum);

		setupFrame();
	}

	// this method uses a scanner object to scan from a text file and assign to a
	// question object, which is then added into a list of question objects, so the
	// questions can be randomized
	private void randomizeQuestions() {
		// try to find file, if not found catch and print file error
		try {
			// new instance of scanner object, using the following delimiter to
			// separate data
			Scanner fileScanner = new Scanner(new File("question.txt"));
			fileScanner.useDelimiter("\t|\r\n");

			// use for loop to create list of 25 question objects
			for (int i = 0; i < 30; i++) {
				// if there is a next double, assign it to variable, if not
				// break loop
				if (fileScanner.hasNextInt()) {
					int questionNum = fileScanner.nextInt();
					String question = fileScanner.next();
					String answer = fileScanner.next();
					String option1 = fileScanner.next();
					String option2 = fileScanner.next();
					String option3 = fileScanner.next();
					String option4 = fileScanner.next();

					questionList.add(new Question(questionNum, question, answer, option1, option2, option3, option4));
				} else {
					break;
				}
			}
			// shuffle array to randomize questions
			Collections.shuffle(questionList);
		}
		// catch statement if file is not found
		catch (FileNotFoundException e) {
			System.out.println("Error: File not found.");
		}
	}

	// this method creates the properties and sets the bounds for buttons on the
	// program, splitting the methods to utilize the divide and conquer concept
	private void placeButtons() {
		// use 2D array to set properties of widgets, since they have
		// similar/related properties
		for (int r = 0; r < buttonArray.length; r++) {
			for (int c = 0; c < buttonArray[r].length; c++) {

				buttonArray[r][c] = new JButton();
				buttonArray[r][c].addActionListener(this);
				buttonArray[r][c].setContentAreaFilled(false);
				buttonArray[r][c].setOpaque(false);
				buttonArray[r][c].setBorderPainted(false);
				buttonArray[r][c].setFocusable(false);
				buttonArray[r][c].setForeground(Color.white);
				buttonArray[r][c].setFont(new Font("Dialog", Font.PLAIN, 15));

				// space buttons out in 4 quadrants
				buttonArray[r][c].setBounds(624 + c * 274, 249 + r * 129, 240, 107);
			}
		}
	}

	// this method is called when a button is pressed to attempt to answer the
	// prompt
	private void changeQuestion(int questionNum) {
		// sets the question label as the next question in the list
		questionLabel.setText(questionList.get(questionNum).getQuestion());

		// clears option list to store the 4 options from the question list's
		// questionNum index
		optionList.clear();
		optionList.add(questionList.get(questionNum).getOption1());
		optionList.add(questionList.get(questionNum).getOption2());
		optionList.add(questionList.get(questionNum).getOption3());
		optionList.add(questionList.get(questionNum).getOption4());

		// randomize the optionList so that program will place buttons randomly
		Collections.shuffle(optionList);

		// setting the text to the button once a button is pressed and updated
		// Bowris helped me with this
		// this website also helped me display "<" symbols using html format
		// https://www.toptal.com/designers/htmlarrows/math/less-than-sign/
		buttonArray[0][0].setText(String.format("<html><body style='text-align:center'>%s<html>", optionList.get(0)));
		buttonArray[0][1].setText(String.format("<html><body style='text-align:center'>%s<html>", optionList.get(1)));
		buttonArray[1][0].setText(String.format("<html><body style='text-align:center'>%s<html>", optionList.get(2)));
		buttonArray[1][1].setText(String.format("<html><body style='text-align:center'>%s<html>", optionList.get(3)));
	}

	// this method places the widgets created on the JFrame, and set the
	// properties of the frame, splitting the methods to utilize the divide and
	// conquer concept
	private void setupFrame() {
		// place text field and declare its properties, creating a label for the
		// question
		questionLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		questionLabel.setBounds(624, 129, 513, 100);
		questionLabel.setVisible(true);
		questionLabel.setEditable(false);
		// https://stackoverflow.com/questions/5766175/word-wrap-in-jbuttons
		questionLabel.setLineWrap(true);
		questionLabel.setWrapStyleWord(true);
		questionLabel.setOpaque(false);
		questionLabel.setForeground(Color.white);

		// set bounds of the background image
		frameBackground.setBounds(0, 0, 1200, 625);

		// set the properties of the frame
		setTitle("Borous Learning");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(null);
		setSize(1200, 625);
		setResizable(false);
		setLocationRelativeTo(null);

		// add buttons, textAreas, and labels to frame
		add(buttonArray[0][0]);
		add(buttonArray[0][1]);
		add(buttonArray[1][0]);
		add(buttonArray[1][1]);
		add(questionLabel);
		add(frameBackground);
	}

	// action events when buttons are pressed
	@Override
	public void actionPerformed(ActionEvent e) {
		// if the source is from the respective button, create new instance of the
		// check if the option user chose is the same as the answer for the question
		// number that user is answering, then increment the number of correct answers
		// user has had
		if (e.getSource() == buttonArray[0][0]) {
			if (optionList.get(0).equals(questionList.get(questionNum).getAnswer()))
				++numCorrect;
		} else if (e.getSource() == buttonArray[0][1]) {
			if (optionList.get(1).equals(questionList.get(questionNum).getAnswer()))
				++numCorrect;
		} else if (e.getSource() == buttonArray[1][0]) {
			if (optionList.get(2).equals(questionList.get(questionNum).getAnswer()))
				++numCorrect;
		} else if (e.getSource() == buttonArray[1][1]) {
			if (optionList.get(3).equals(questionList.get(questionNum).getAnswer()))
				++numCorrect;
		}

		// increment the number of questions user has completed
		++questionNum;

		// if the user has answered 15 questions and there are 12 or more questions
		// correct, let user pass the course, otherwise show that user failed
		if (questionNum == 15) {
			if (numCorrect >= 12) {
				dispose();
				new PassFrame(lessonCompleted, activityCompleted);
				return;
			} else if (numCorrect < 12) {
				dispose();
				new FailFrame(lessonCompleted, activityCompleted);
				return;
			}
		}

		// change the question label and buttons to the next question
		changeQuestion(questionNum);
	}
}
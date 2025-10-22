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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

public class Lesson1 extends JFrame implements ActionListener {
	// declare swing widgets
	private JLabel lessonLabel = new JLabel(new ImageIcon("frameimages/Lesson1.png"));
	private JButton[] buttonArray = new JButton[2]; // 1st index for previous frame button, 2nd for finish lesson
	private JPanel scrollPanel = new JPanel();
	private JScrollPane pane = new JScrollPane(scrollPanel);

	// lesson variable to show which lesson and activity user has completed
	private int lessonCompleted, activityCompleted;

	// constructor method
	public Lesson1(int lessonCompleted, int activityCompleted) {
		// set level and activity completed to what it is when it is carried into the
		// constructor method
		this.lessonCompleted = lessonCompleted;
		this.activityCompleted = activityCompleted;
		// call methods to setup frame, labels, and buttons
		scrollPage();
		placeButtons();
		setupFrame();
	}

	private void scrollPage() {
		// set up swing properties of scroll panel
		scrollPanel.setLayout(null);
		scrollPanel.setPreferredSize(new Dimension(1200, 2900));

		// set swing properties of scroll pane
		pane.setBounds(0, 0, 1185, 588);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		pane.getVerticalScrollBar().setUnitIncrement(15);
		pane.setHorizontalScrollBar(null);

		// place lesson picture on scroll pane
		lessonLabel.setBounds(0, 0, 1200, 2900);
	}

	private void placeButtons() {
		// set properties of buttons using a for loop due to their similar swing
		// properties
		for (int i = 0; i < buttonArray.length; i++) {
			buttonArray[i] = new JButton();
			buttonArray[i].addActionListener(this);
			buttonArray[i].setContentAreaFilled(false);
			buttonArray[i].setOpaque(false);
			buttonArray[i].setBorderPainted(false);
			buttonArray[i].setFocusable(false);
		}

		// set bounds of finish lesson and main menu buttons because their properties
		// are not correlated
		buttonArray[0].setBounds(1027, 25, 117, 51);
		buttonArray[1].setBounds(532, 2819, 136, 51);

	}

	// this method places the widgets created on the JFrame, and set the
	// properties of the frame, splitting the methods to utilize the divide and
	// conquer concept
	private void setupFrame() {
		// set the properties of the frame
		setTitle("Borous Learning");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(null);
		setSize(1200, 625);
		setResizable(false);
		setLocationRelativeTo(null);

		// add buttons, panels, and labels to frame
		add(pane);

		// add JLabel to scroll pane
		scrollPanel.add(buttonArray[0]);
		scrollPanel.add(buttonArray[1]);
		scrollPanel.add(lessonLabel);
	}

	// action events when buttons are pressed
	@Override
	public void actionPerformed(ActionEvent e) {
		// if the source is from the button, create new instance of the
		// appropriate frame, and dispose of previous frame for user convenience
		if (e.getSource() == buttonArray[0]) {
			new LessonSelection(lessonCompleted, activityCompleted);
			dispose();
		} else if (e.getSource() == buttonArray[1]) {
			// determine the correct value to set activity to and go back to activity
			// selection instance
			if (lessonCompleted != 3 && activityCompleted != 3)
				lessonCompleted = 1;
			new LessonSelection(lessonCompleted, activityCompleted);
			dispose();
		}
	}
}
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ActivitySelection extends JFrame implements ActionListener {
	// declare swing widgets
	private JLabel frameBackground = new JLabel(new ImageIcon("frameimages/activitySelection.png"));
	private JButton[] buttonArray = new JButton[3];
	private JButton previousFrame = new JButton();

	// lesson variable to show which lesson and activity user has completed
	private int lessonCompleted, activityCompleted;

	// constructor method
	public ActivitySelection(int lessonCompleted, int activityCompleted) {
		// set level and activity completed to what it is when it is carried into the
		// constructor method
		this.lessonCompleted = lessonCompleted;
		this.activityCompleted = activityCompleted;

		// call methods to setup frame, labels, and buttons
		placeButtons();
		setupFrame();
	}

	// this method creates the properties and sets the bounds for buttons on the
	// program, splitting the methods to utilize the divide and conquer concept
	private void placeButtons() {
		// use array to set properties of widgets, since they have
		// similar/related properties
		for (int i = 0; i < buttonArray.length; i++) {
			buttonArray[i] = new JButton();
			buttonArray[i].addActionListener(this);
			buttonArray[i].setContentAreaFilled(false);
			buttonArray[i].setOpaque(false);
			buttonArray[i].setBorderPainted(false);
			buttonArray[i].setFocusable(false);
			// space buttons out in thirds
			buttonArray[i].setBounds(725 + i * 157, 341, 119, 54);
		}

		// set bounds and properties of button that leads to previous frame
		previousFrame.addActionListener(this);
		previousFrame.setContentAreaFilled(false);
		previousFrame.setOpaque(false);
		previousFrame.setBorderPainted(false);
		previousFrame.setFocusable(false);
		previousFrame.setBounds(1061, 21, 117, 51);
	}

	// this method places the widgets created on the JFrame, and set the
	// properties of the frame, splitting the methods to utilize the divide and
	// conquer concept
	private void setupFrame() {
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

		// add buttons, panels, and labels to frame
		for (int i = 0; i < buttonArray.length; i++)
			add(buttonArray[i]);
		add(previousFrame);
		add(frameBackground);
	}

	// action events when buttons are pressed
	@Override
	public void actionPerformed(ActionEvent e) {
		// if the source is from the button, create new instance of the
		// appropriate frame, and dispose of previous frame for user convenience
		if (e.getSource() == buttonArray[0]) {
			// if the previous activity is completed, let user complete the next lesson
			if ((lessonCompleted == 1 && activityCompleted == 0) || (lessonCompleted == 3 && activityCompleted == 3)) {
				new ArrayAdventureGUI(1, lessonCompleted, activityCompleted);
				dispose();
			}
			// else wise, if the user has not completed the lesson or activity before, tell
			// them
			else if (lessonCompleted < 1 || activityCompleted < 1) {
				JOptionPane.showMessageDialog(this,
						"You must complete the previous lesson before accessing this activity!", "Activity Locked",
						JOptionPane.INFORMATION_MESSAGE);
			}
			// else wise, tell user they have completed the lesson already
			else {
				JOptionPane.showMessageDialog(this, "You have already completed this activity!",
						"Activity Completed Prior", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getSource() == buttonArray[1]) {
			// if level 1 is completed and lesson 2 is completed let user access lesson
			// level 2
			if ((lessonCompleted == 2 && activityCompleted == 1) || (lessonCompleted == 3 && activityCompleted == 3)) {
				new ArrayAdventureGUI(2, lessonCompleted, activityCompleted);
				dispose();
			}
			// else wise, if the user has not completed the lesson or activity before, tell
			// them
			else if (lessonCompleted < 2 || activityCompleted < 1) {
				JOptionPane.showMessageDialog(this,
						"You must complete the previous lesson and activity before accessing this one!",
						"Activity Locked", JOptionPane.INFORMATION_MESSAGE);
			}
			// else wise, tell the user they have completed this lesson already
			else {
				JOptionPane.showMessageDialog(this, "You have already completed this activity!",
						"Activity Completed Prior", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getSource() == buttonArray[2]) {
			// lesson 3 and activity 2 is completed, let user access lesson 3

			if ((lessonCompleted == 3 && activityCompleted == 2) || (lessonCompleted == 3 && activityCompleted == 3)) {
				new ArrayAdventureGUI(3, lessonCompleted, activityCompleted);
				dispose();
			}
			// else wise, if the user has not completed the lesson or activity before, tell
			// them
			else if (lessonCompleted < 3 || activityCompleted < 2) {
				JOptionPane.showMessageDialog(this,
						"You must complete the previous lesson(s) and activity(ies) before accessing this one!",
						"Activity Locked", JOptionPane.INFORMATION_MESSAGE);
			}
			// else wise, tell the user they have completed this activity already
			else {
				JOptionPane.showMessageDialog(this, "You have already completed this activity!",
						"Activity Completed Prior", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getSource() == previousFrame) {
			new SectionSelection(lessonCompleted, activityCompleted);
			dispose();
		}
	}
}
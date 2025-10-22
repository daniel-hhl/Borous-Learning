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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Arrays;

public class ArrayAdventureGUI extends JFrame implements ActionListener, KeyListener {
	// declare objects we plan to use in class before using it

	// constants
	private final int TILE_SIZE = 50, NUM_TILES_WIDTH = 20, NUM_TILES_HEIGHT = 11;

	// set the icons for each element on the game map
	// obstacles
	private final ImageIcon BATTLEMENT = new ImageIcon("images/battlement.png");
	private final ImageIcon WALL = new ImageIcon("images/wall.png");
	private final ImageIcon DOOR = new ImageIcon("images/door.png");
	private final ImageIcon TRAIL = new ImageIcon("images/greenSquare.png");
	private final ImageIcon PORTAL = new ImageIcon("images/portal.gif");

	// tile icons to obtain
	private final ImageIcon COIN = new ImageIcon("images/coin.png");

	// character icon
	private final ImageIcon[] CHARACTER = { new ImageIcon("images/faceback.png"), new ImageIcon("images/facefront.png"),
			new ImageIcon("images/faceleft.png"), new ImageIcon("images/faceright.png"),
			new ImageIcon("images/faceback.gif"), new ImageIcon("images/facefront.gif"),
			new ImageIcon("images/faceleft.gif"), new ImageIcon("images/faceright.gif") };

	// player object
	private Avatar avatar = new Avatar(CHARACTER[1]);

	// declare lists, variables, and arrays to store question objects and options
	private ArrayList<Question> questionList = new ArrayList<Question>();
	private String[] choiceArray = new String[4];
	private String choice;
	private int questionsAnswered = 0;
	private int randomNum;

	// GUI components
	private JPanel castleMazePanel = new JPanel();
	private JPanel pauseScreen = new JPanel();
	private Tile[][] castleMaze = new Tile[NUM_TILES_HEIGHT][NUM_TILES_WIDTH];

	// game variable
	private int coinPoints = 0;
	private double time = 100;
	private int level;

	// declare labels
	private JPanel scoreboardPanel = new JPanel();
	private JLabel scoreLabel = new JLabel("Coins Obtained: 0");
	public Timer gameTimer = new Timer(10, this); // every 10ms, or 0.01s, the
													// action is performed
	private JLabel timerLabel = new JLabel("Time Elapsed: 100");

	// declare JButtons
	private JButton pause = new JButton("PAUSE");
	private JButton restart = new JButton("RESTART"), resume = new JButton("RESUME"),
			mainMenu = new JButton("MAIN MENU");

	// lesson variable to show which lesson and activity user has completed
	private int lessonCompleted, activityCompleted;

	// constructor method for this class
	public ArrayAdventureGUI(int level, int lessonCompleted, int activityCompleted) {
		// set level and activity completed to what it is when it is carried into the
		// constructor method
		this.lessonCompleted = lessonCompleted;
		this.activityCompleted = activityCompleted;
		this.level = level;

		scoreboardPanelSetup();
		randomizeQuestions();
		castleMazePanelSetup(level);
		pauseScreenSetup();
		frameSetup();
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

			// use the level to determine which difficulty the lessons should correspond to
			if (level == 1) {
				for (int i = 10; i < 30; i++)
					questionList.remove(10);
			} else if (level == 2) {
				for (int i = 0; i < 10; i++)
					questionList.remove(0);
				for (int i = 20; i < 30; i++)
					questionList.remove(10);
			} else if (level == 3) {
				for (int i = 0; i < 20; i++)
					questionList.remove(0);
			}

			// shuffle array to randomize questions
			Collections.shuffle(questionList);
		}
		// catch statement if file is not found
		catch (FileNotFoundException e) {
			System.out.println("Error: File not found.");
		}
	}

	// sets up pause screen that becomes visible in a press of pause button
	private void pauseScreenSetup() {
		// set information on panel
		pauseScreen.setBounds(400, 150, 200, 300);
		pauseScreen.setBackground(Color.GRAY);
		pauseScreen.setLayout(new GridLayout(3, 1, 0, 0));

		// set information for button
		restart.setFont(new Font("Consola", Font.PLAIN, 25));
		restart.addActionListener(this);
		restart.setBackground(Color.GRAY);
		restart.setForeground(Color.white);
		restart.setBorderPainted(true);
		restart.setFocusable(false);

		// set information for button
		resume.setFont(new Font("Consola", Font.PLAIN, 25));
		resume.addActionListener(this);
		resume.setBackground(Color.GRAY);
		resume.setForeground(Color.white);
		resume.setBorderPainted(true);
		resume.setFocusable(false);

		// set information for button
		mainMenu.setFont(new Font("Consola", Font.PLAIN, 25));
		mainMenu.setBackground(Color.GRAY);
		mainMenu.setForeground(Color.white);
		mainMenu.setBorderPainted(true);
		mainMenu.addActionListener(this);
		mainMenu.setFocusable(false);

		// add buttons and panels and set visibility to false, as we dont want panel to
		// show up at start of game
		pauseScreen.setVisible(false);
		pauseScreen.add(resume);
		pauseScreen.add(restart);
		pauseScreen.add(mainMenu);
	}

	// sets up scoreboard panel to keep track of score
	private void scoreboardPanelSetup() {
		// set bounds of panel and set the layout
		scoreboardPanel.setBounds(0, 0, TILE_SIZE * NUM_TILES_WIDTH, 35);
		scoreboardPanel.setLayout(new BorderLayout());

		// set information on panels, use border layout to place buttons
		scoreLabel.setFont(new Font("Consola", Font.PLAIN, 25));

		// set information on labels, use border layout to place buttons
		// center text for more aesthtetic balance
		// https://stackoverflow.com/questions/16957329/borderlayout-center-doesnt-center
		timerLabel.setFont(new Font("Consola", Font.PLAIN, 25));
		timerLabel.setHorizontalAlignment(JLabel.CENTER);

		// set information on pause button
		pause.setBackground(Color.GRAY);
		pause.setFont(new Font("Consola", Font.PLAIN, 20));
		pause.addActionListener(this);
		// prevents keys from not moving when focused
		// https://stackoverflow.com/questions/8074316/keylistener-not-working-after-clicking-button
		pause.setFocusable(false);

		// place labels and buttons
		scoreboardPanel.add(scoreLabel, BorderLayout.WEST);
		scoreboardPanel.add(timerLabel, BorderLayout.CENTER);
		scoreboardPanel.add(pause, BorderLayout.EAST);
		gameTimer.start();

	}

	// sets up all tiles and panels for the castle maze game
	private void castleMazePanelSetup(int level) {
		// set bounds of actual panel and create grid layout to store tiles
		castleMazePanel.setBounds(0, 35, TILE_SIZE * NUM_TILES_WIDTH, TILE_SIZE * NUM_TILES_HEIGHT);
		castleMazePanel.setLayout(new GridLayout(NUM_TILES_HEIGHT, NUM_TILES_WIDTH, 0, 0));

		// method to take .txt file and convert it into a maze layout
		loadCastleMaze(level);
		// places player on map randomly
		placePlayer();
	}

	// takes .txt file and convert it into a maze layout
	private void loadCastleMaze(int level) {
		// declare local variables
		int row = 0;
		char[] lineArray; // {'B', 'C', ... , 'B'}

		// try catch to see if file and code is usable
		try {
			// create scanner object
			Scanner input = new Scanner(new File("Level" + Integer.toString(level) + ".txt"));

			// while lineArray has a next char while looping through txt file
			while (input.hasNext()) {
				// this to char array method takes strings and converts to char
				lineArray = input.nextLine().toCharArray();

				// call fill Tile method
				for (int col = 0; col < lineArray.length; col++) {
					fillTile(lineArray[col], row, col);

				}
				row++;

				// close scanner object to make sure scanner object does not
				// corrupt file
			}
			input.close();

		} catch (FileNotFoundException error) {
			System.out.println(error);
		}

	}

	// with the castleMaze array, we can determine the image icons using if
	// structure
	private void fillTile(char c, int row, int col) {
		// store row and col in a tile object with a 2d array
		castleMaze[row][col] = new Tile(row, col);

		// if structure to determine the letter with the corresponding ImageIcon
		if (c == 'B') {
			castleMaze[row][col].setIcon(BATTLEMENT);
		} else if (c == 'W') {
			castleMaze[row][col].setIcon(WALL);
		} else if (c == 'D') {
			castleMaze[row][col].setIcon(DOOR);
		} else if (c == 'C') {
			castleMaze[row][col].setIcon(COIN);
		} else if (c == '.') {
			castleMaze[row][col].setIcon(TRAIL);
		} else if (c == 'P') {
			castleMaze[row][col].setIcon(PORTAL);
		}

		// adds icon to panel
		castleMazePanel.add(castleMaze[row][col]);
	}

	// place player on a random spot on the map
	private void placePlayer() {
		// call find empty tile method, finds a tile that fill good
		Tile tile = findEmptyTile();

		// set row and col to that tile
		avatar.setRow(tile.getRow());
		avatar.setCol(tile.getCol());

		castleMaze[avatar.getRow()][avatar.getCol()].setIcon(avatar.getIcon());
	}

	// finds empty tile
	private Tile findEmptyTile() {
		// new tile to find empty and compare
		Tile tile = new Tile();

		// generate random row and column index where player will spawn
		do {
			// rows and cols are the length and widths of the tiles, + 2
			tile.setRow((int) (Math.random() * 9) + 2);
			tile.setCol((int) (Math.random() * 18) + 2);
		} while (castleMaze[tile.getRow()][tile.getCol()].getIcon() != TRAIL);

		return tile;
	}

	// sets up frame
	private void frameSetup() {
		// set the title to something amazing
		setTitle("Borous Learning");

		// I literally played around witht his to get the dimensions looking
		// good!
		// These numbers were figured out through LOTS of trial and error
		// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		setSize(TILE_SIZE * NUM_TILES_WIDTH + 16, TILE_SIZE * NUM_TILES_HEIGHT + 75);

		// use Border Layout to manage layout of the frame
		setLayout(null);

		// add panels to frame
		add(pauseScreen);
		add(scoreboardPanel);
		add(castleMazePanel);

		// add key lsitener for movement of avatar
		addKeyListener(this);

		// set properties of frame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	// checks which action is peformed and performs required action
	@Override
	public void actionPerformed(ActionEvent event) {
		// if pause is pressed
		if (event.getSource() == pause) {
			gameTimer.stop();
			pauseScreen.setVisible(true);
		}
		// if timer has action, change timer in the variable and label
		else if (event.getSource() == gameTimer) {
			time -= 0.01;
			if (time < 0) {
				gameTimer.stop();
				timerLabel.setText("Time Elapsed: 0");
				String[] options = { "Retry Level", "Quit" };

				// ask user to retry or quit
				int retry = JOptionPane.showOptionDialog(this, "You ran out of time!", "Level Failed!",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

				// if they retry, open a new instance of this class with the same level as
				// before
				if (retry == 0) {
					new ArrayAdventureGUI(level, lessonCompleted, activityCompleted);
					dispose();
				}
				// if they quit, go back to activity selection
				else {
					new ActivitySelection(lessonCompleted, activityCompleted);
					dispose();
				}
			}
			timerLabel.setText("Time Elapsed: " + String.format("%.6s", (Double.toString(time))));
		}
		// if source is resume, resume game
		else if (event.getSource() == resume) {
			pauseScreen.setVisible(false);
			gameTimer.start();
		}
		// restart game with same parameters
		else if (event.getSource() == restart) {
			dispose();
			new ArrayAdventureGUI(level, lessonCompleted, activityCompleted);
		}
		// go back frame and carry over arguments
		else if (event.getSource() == mainMenu) {
			dispose();
			new ActivitySelection(lessonCompleted, activityCompleted);
		}
	}

	// this method calls the move avatar method in the respective direction when key
	// is pressed
	@Override
	public void keyPressed(KeyEvent arg0) {
		// if key pressed is 0, and the game is not paused
		if (arg0.getKeyCode() == KeyEvent.VK_W && pauseScreen.isVisible() == false) {
			// set icon to the icon moving in the up direction
			avatar.setIcon(CHARACTER[4]);

			// if the avatar is moving into the portal, call enter portal method
			if (castleMaze[avatar.getRow() - 1][avatar.getCol() + 0].getIcon() == PORTAL)
				enterPortal(1);

			// if nothing is blocking avatar, let it change its row and col by 1 up
			else if (castleMaze[avatar.getRow() - 1][avatar.getCol() + 0].getIcon() != WALL
					&& castleMaze[avatar.getRow() - 1][avatar.getCol() + 0].getIcon() != BATTLEMENT
					&& castleMaze[avatar.getRow() - 1][avatar.getCol() + 0].getIcon() != DOOR)
				moveAvatar(-1, 0);
			else
				// if the avatar cannot move, still, set the character to look like its walking,
				// just into the wall for comedic effect
				castleMaze[avatar.getRow()][avatar.getCol()].setIcon(CHARACTER[4]);
		}
		// repeat similar processed for the rest of keys s, a, and d
		else if (arg0.getKeyCode() == KeyEvent.VK_S && pauseScreen.isVisible() == false) {
			avatar.setIcon(CHARACTER[5]);

			if (castleMaze[avatar.getRow() + 1][avatar.getCol() + 0].getIcon() == PORTAL)
				enterPortal(2);

			else if (castleMaze[avatar.getRow() + 1][avatar.getCol() + 0].getIcon() != WALL
					&& castleMaze[avatar.getRow() + 1][avatar.getCol() + 0].getIcon() != BATTLEMENT
					&& castleMaze[avatar.getRow() + 1][avatar.getCol() + 0].getIcon() != DOOR)
				moveAvatar(1, 0);

			else
				castleMaze[avatar.getRow()][avatar.getCol()].setIcon(CHARACTER[5]);
		} else if (arg0.getKeyCode() == KeyEvent.VK_A && pauseScreen.isVisible() == false) {
			avatar.setIcon(CHARACTER[6]);

			if (castleMaze[avatar.getRow() + 0][avatar.getCol() - 1].getIcon() == PORTAL)
				enterPortal(3);

			else if (castleMaze[avatar.getRow() + 0][avatar.getCol() - 1].getIcon() != WALL
					&& castleMaze[avatar.getRow() + 0][avatar.getCol() - 1].getIcon() != BATTLEMENT
					&& castleMaze[avatar.getRow() + 0][avatar.getCol() - 1].getIcon() != DOOR)
				moveAvatar(0, -1);

			else
				castleMaze[avatar.getRow()][avatar.getCol()].setIcon(CHARACTER[6]);
		} else if (arg0.getKeyCode() == KeyEvent.VK_D && pauseScreen.isVisible() == false) {
			avatar.setIcon(CHARACTER[7]);

			if (castleMaze[avatar.getRow() + 0][avatar.getCol() + 1].getIcon() == PORTAL)
				enterPortal(4);

			else if (castleMaze[avatar.getRow() + 0][avatar.getCol() + 1].getIcon() != WALL
					&& castleMaze[avatar.getRow() + 0][avatar.getCol() + 1].getIcon() != BATTLEMENT
					&& castleMaze[avatar.getRow() + 0][avatar.getCol() + 1].getIcon() != DOOR)
				moveAvatar(0, 1);

			else
				castleMaze[avatar.getRow()][avatar.getCol()].setIcon(CHARACTER[7]);
		}
	}

	// key released method is used to determine which icon to set avatar to when
	// stopped
	@Override
	public void keyReleased(KeyEvent arg0) {
		// use if structure to determine, and only do this when pause screen is not
		// visible
		if (pauseScreen.isVisible() == false) {
			// whatever the key is, set the tile character is on to the appropriate icon to
			// seem like character is facing the pressed direction
			if (arg0.getKeyCode() == KeyEvent.VK_W)
				castleMaze[avatar.getRow()][avatar.getCol()].setIcon(CHARACTER[0]);
			else if (arg0.getKeyCode() == KeyEvent.VK_S)
				castleMaze[avatar.getRow()][avatar.getCol()].setIcon(CHARACTER[1]);
			else if (arg0.getKeyCode() == KeyEvent.VK_A)
				castleMaze[avatar.getRow()][avatar.getCol()].setIcon(CHARACTER[2]);
			else if (arg0.getKeyCode() == KeyEvent.VK_D)
				castleMaze[avatar.getRow()][avatar.getCol()].setIcon(CHARACTER[3]);
		}
	}

	// no need to use key typed method since we want user to move when key is held
	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	// moves avatar to specific row and col when called
	private void moveAvatar(int dRow, int dCol) {
		// set the previous tile to trail so avatar can move
		castleMaze[avatar.getRow()][avatar.getCol()].setIcon(TRAIL);

		// if the tile in front has a coin in it
		if (castleMaze[avatar.getRow() + dRow][avatar.getCol() + dCol].getIcon() == COIN) {
			// set that tile to a trail and move the avatar to that tile
			castleMaze[avatar.getRow() + dRow][avatar.getCol() + dCol].setIcon(TRAIL);
			avatar.move(dRow, dCol);

			// use an if structure to change the gif image to a static png, since key was
			// not released before calling this moveAvatar method
			if (avatar.getIcon() == CHARACTER[4])
				castleMaze[avatar.getRow()][avatar.getCol()].setIcon(CHARACTER[0]);
			else if (avatar.getIcon() == CHARACTER[5])
				castleMaze[avatar.getRow()][avatar.getCol()].setIcon(CHARACTER[1]);
			else if (avatar.getIcon() == CHARACTER[6])
				castleMaze[avatar.getRow()][avatar.getCol()].setIcon(CHARACTER[2]);
			else if (avatar.getIcon() == CHARACTER[7])
				castleMaze[avatar.getRow()][avatar.getCol()].setIcon(CHARACTER[3]);

			// give user a random question when coin is collected
			// generate random num within the list size
			randomNum = (int) (Math.random() * questionList.size());

			// https://stackoverflow.com/questions/4208655/empty-an-array-in-java-processing
			// clear out the choice array from previous options
			Arrays.fill(choiceArray, null);

			// get options for that question list index and store in indexes
			choiceArray[0] = questionList.get(randomNum).getOption1();
			choiceArray[1] = questionList.get(randomNum).getOption2();
			choiceArray[2] = questionList.get(randomNum).getOption3();
			choiceArray[3] = questionList.get(randomNum).getOption4();

			// randomize choices in array so user does not memorize the answer placements
			for (int i = 0; i < choiceArray.length; i++) {
				int indexSwap = (int) (Math.random() * choiceArray.length);
				String temp = choiceArray[indexSwap];
				choiceArray[indexSwap] = choiceArray[i];
				choiceArray[i] = temp;
			}

			// show input dialog JOptionPane so user can choose which option they believe is
			// right
			choice = (String) JOptionPane.showInputDialog(null, questionList.get(randomNum).getQuestion(),
					"Question " + Integer.toString(questionsAnswered + 1), JOptionPane.QUESTION_MESSAGE, null,
					choiceArray, choiceArray[0]);

			// stop game timer when looking at results
			gameTimer.stop();

			// if there is a choice and the choice is equal to the answer
			if (choice != null && choice.equals(questionList.get(randomNum).getAnswer())) {
				// increment coinPoints and display update the text on the scorelabel
				scoreLabel.setText("Coins Obtained: " + Integer.toString(++coinPoints));
				JOptionPane.showMessageDialog(this, "Congratulations, you selected the correct answer!", "Correct!",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				// give feedback on which answer they should have picked
				JOptionPane.showMessageDialog(this,
						"Unfortunately, you selected the wrong answer.\n\nQuestion: "
								+ questionList.get(randomNum).getQuestion() + "\nAnswer: "
								+ questionList.get(randomNum).getAnswer(),
						"Incorrect!", JOptionPane.INFORMATION_MESSAGE);

			}

			// remove that question object from list
			questionList.remove(randomNum);

			// start the game timer again after user exits out of message dialog box, and
			// increment questions answered counter
			gameTimer.start();
			++questionsAnswered;
		} else {
			// if there is no coin, move avatar normally
			avatar.move(dRow, dCol);
			castleMaze[avatar.getRow()][avatar.getCol()].setIcon(avatar.getIcon());
		}

		// if all coins are collected in a level, display the appropriate results
		if (questionsAnswered == 10 && coinPoints != 10) {
			gameTimer.stop();
			String[] options = { "Retry Level", "Quit" };

			// ask user to retry or quit
			int retry = JOptionPane.showOptionDialog(this, "You must score 10/10 on this level before continuing!",
					"Level Failed!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

			// if they retry, open a new instance of this class with the same level as
			// before
			if (retry == 0) {
				new ArrayAdventureGUI(level, lessonCompleted, activityCompleted);
				dispose();
			}
			// if they quit, go back to activity selection
			else {
				new ActivitySelection(lessonCompleted, activityCompleted);
				dispose();
			}
		} else if (questionsAnswered == 10 && coinPoints == 10) {
			// stop game timer since user won the game
			gameTimer.stop();

			// use if structure to determine the correct value to set activity to and go
			// back to activity selection instance
			if (activityCompleted != 3) {
				if (level == 1) {
					activityCompleted = 1;
					// let user know they passed the level
					JOptionPane.showMessageDialog(this, "You passed the level!", "Level Passed!",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (level == 2) {
					// let user know they passed the level
					JOptionPane.showMessageDialog(this, "You passed the level!", "Level Passed!",
							JOptionPane.INFORMATION_MESSAGE);
					activityCompleted = 2;
				} else if (level == 3) {
					activityCompleted = 3;
					JOptionPane.showMessageDialog(this,
							"Congratulations on passing the course! Now, you may start studying for your final exam.",
							"Level Passed!", JOptionPane.INFORMATION_MESSAGE);

				}
			}
			new ActivitySelection(lessonCompleted, activityCompleted);
			dispose();
		}

	}

	// if user enters portal, send them to the opposite portal
	private void enterPortal(int portal) {
		// set the old row and col of character to trail
		castleMaze[avatar.getRow()][avatar.getCol()].setIcon(TRAIL);

		// use if structure to determine which portal user entered from, as well as
		// which portal to send to
		if (portal == 1) {
			avatar.setCol(8);
			avatar.setRow(9);
		} else if (portal == 2) {
			avatar.setCol(11);
			avatar.setRow(1);
		} else if (portal == 3) {
			avatar.setCol(18);
			avatar.setRow(5);
		} else {
			avatar.setCol(1);
			avatar.setRow(4);
		}
	}
}
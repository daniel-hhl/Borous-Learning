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

public class StartApplication {
	public static void main(String[] args) {
		// create new instance of title page class, the starting instance of frame for
		// Borous Learning program
		new TitlePage(0, 0);
	}
}

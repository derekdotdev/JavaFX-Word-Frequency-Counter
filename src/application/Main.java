package application;

import java.io.File;
import java.sql.ResultSet;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import application.data.Database;
import application.data.WebScrape;
import application.views.prompts.ConfirmBox;
import application.views.prompts.QuestionBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/** Application scrapes text from a website and displays top10 
 *  (and all) word occurrences to a JavaFX GUI.
 *  @author derekdileo */
public class Main extends Application {
	
	// Variables for call to QuestionBox.display()
	public static boolean defaultSite = false;
	protected static String userWebsite = null;
	protected static String sourceHead = null;
	protected static String sourceEnd = null;
	private String defaultWebsite =  "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
	private String defaultSourceHead = "<h1>The Raven</h1>";
	private String defaultSourceEnd = "<!--end chapter-->";
	private String title = "Word Frequency Analyzer";
	private String instruction = "Enter a URL to count frequency of each word.";
	private String siteLabel = "Website to Parse";
	private String sitePlaceholder = "Enter a website to evaluate";
	private String startLabel = "Where to start.";
	private String startPlaceholder= "Text from first line";
	private String endLabel = "Where to finish.";
	private String endPlaceholder = "Text from last line.";
	private String[] defaultEntries = {defaultWebsite, defaultSourceHead, defaultSourceEnd};
	private String[] questionBoxPrompts = {title, instruction, siteLabel, sitePlaceholder, startLabel, startPlaceholder, endLabel, endPlaceholder};
	
	// QuestionBox.display now accepts a third string array to pass to an AlertBox when it launches.
	// This enables us to provide some app instructions to the user. 
	private String appIntroTitle = "Welcome to Word Frequency Counter";
	private String appIntroMessage = "For best results, right-click and inspect the text you'd like to parse. \nThen, copy and paste the elements into the start and finish boxes.";
	private String[] appIntro = {appIntroTitle, appIntroMessage};
	
	// String array to hold QuestionBox.display() responses.
	public static String[] userResponses;
	
	// Local Lists and Maps to hold return values from Class methods
	private String[] wordsArray;
	
	// Varibles used to show / hide text on GUI
	private StringBuilder sbTen;
	private StringBuilder sbAll;
	
	// These are accessed by the four Controller classes to print to GUI 
	public static String sbTenString;
	public static String sbAllString;

	// Declare FileHandler for Database.logger
	public static Logger my_log;
	public static FileHandler fileHandler;
	public static File file;
	
	/** Main method calls launch() to start JavaFX GUI.
	 *  @param args mandatory parameters for command line method call */
	public static void main(String[] args) {

		try {
			// Instantiate Logger, set level
			my_log = Logger.getLogger("application.Main");
			my_log.setLevel(Level.INFO);

			// Create file for logging and instantiate FileHandler
			file = new File("log.txt");
			if(!file.exists()) {
				file.createNewFile();
			}

			// Instantiate FileHandler with append set to true
			fileHandler = new FileHandler("log.txt", true);

			// Add fileHandler to log
			my_log.addHandler(fileHandler);

			// Set formatting of log file
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);

			my_log.info("Info msg");
			my_log.warning("warning msg");
			my_log.severe("severe msg");

		} catch (Exception e) {
			System.out.println("Error creating Logger in Main: " + e.getMessage());
			my_log.severe("Logger could not be created in Main");
		}

		// Create wordsTable if it doesn't exist
		try {
			Database.createWordsTable("words");
		} catch (Exception e) {
			System.out.println("Error creating words table in main()!");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		launch();
	}
	
	// Declare stage (window) outside of start() method
	// so it is accessible to closeProgram() method
	protected static Stage window;
	
	/** The start method (which is called on the JavaFX Application Thread) 
	 * is the entry point for this application and is called after the init 
	 * method has returned- most of the application logic takes place here. */
	@Override
	public void start(Stage primaryStage) {
		
		// Get user input for website, startLine & endLine...
		// Or set to default values if none are entered by user
		userResponses = processUserInput();
		
		// This boolean is used to determine which scene is loaded 
		// (with or without EAP's The Raven graphic elements) 
		if (userResponses[0].equals(defaultWebsite)) {
			defaultSite = true;
		}
		
		// String array created by WebScrape.parseSite() 
		// which contains every word (and multiples)
		wordsArray = WebScrape.parseSite(userResponses[0], userResponses[1], userResponses[2]);
		
		// Process wordsArray and push to database. 
		// If word exists, increment its frequency
		WebScrape.wordsToDB(wordsArray);
		
		// SELECT * FROM words ORDER BY DESC and return ResultSet
		ResultSet results = Database.getResults();
		
		// Push results to GUI
		displayResults(results);
		
		// Rename stage to window for sanity
		window = primaryStage;
		
		// Set stage title
		window.setTitle("Word Frequency Analyzer");
		
		// Handle close button request. 
		// Launch ConfirmBox to confirm if user wishes to quit
		window.setOnCloseRequest(e -> {
			// Consume the event to allow closeProgram() to do its job
			e.consume();
			closeProgram();
		});
		
		try {
			if (defaultSite) {
				BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/views/Main.fxml"));
				//setUserAgentStylesheet(STYLESHEET_CASPIAN);
				Scene scene = new Scene(root,800,600);
				// use FXML to point to CSS!
				//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				window.setScene(scene);
				window.show();
			} else {
				BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/views/MainDefault.fxml"));
				//setUserAgentStylesheet(STYLESHEET_CASPIAN);
				Scene scene = new Scene(root,800,600);
				// use FXML to point to CSS!
				//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				window.setScene(scene);
				window.show();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	/** Method calls QuestionBox to ask user for a website to parse as well as
	 *  where the parsing should start and end.
	 *  @return a String array with responses to pass to WebScrape.parseSite() Method. */
	private String[] processUserInput() {
		// Create string array to hold QuestionBox responses (site, startPoint, endPoint).
		String[] responses = new String[3];
		
		// Gather responses and return to caller
		responses = QuestionBox.display(questionBoxPrompts, defaultEntries, appIntro);
		return responses;
		
	}
	
	/** Method to convert printed database contents to topTen and All windows on JavaFX GUI.
	 *  @param rs is the ResultSet returned from Database.getResults() method. */
	private void displayResults(ResultSet rs) {
		try {
			// Build a string of top 10 results to push to Main.fxml GUI
			sbTen = new StringBuilder();
			sbTen.append("Top Ten Results\n\n");
			
			// Build a string of all results to push to AllResults.fxml GUI
			sbAll = new StringBuilder();
			sbAll.append("All Results\n\n");
			
			// Variables for buildString()
			String word = null;
			int frequency = 0;
			int wordCount = 0;
			
			// Scan through result set
			while(rs.next()) {
				word = rs.getString(1);
				frequency = rs.getInt(2);
			
				String line = buildString(word, frequency, wordCount);
			
				// Handle top10 and all results lists
				if (wordCount < 10) {
					sbTen.append(line);
					sbAll.append(line);
				} else {
					sbAll.append(line);
				}
				
				wordCount++;
				
			}
			
			// Save results to String variables which are called from either:
			// MainC-, MainDefaultC-, AllResultsC- or AllResultsDefaultC- ontrollers to push to GUI
			sbTenString = sbTen.toString();
			sbAllString = sbAll.toString();
		
			rs.close();
		} catch (Exception e) {
			System.out.println("Exception in Main.displayResults()" + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/** Method to create a string for each word/frequency set in database which uses \t to account for word size 
	 *  and places a blank space before the numbers 1-9 in order to make top10 results more uniform.
	 *  @param word is the word pulled from the database.
	 *  @param frequency is the number of times the word occurred on the parsed site.
	 *  @param count keeps track of the position in the list (which is in descending order by frequency). 
	 *  @return String to append to StringBuilder for top10 and/or all results which is pushed to GUI. */
	private String buildString(String word, int frequency, int count) {
		
		int size = word.length();
		
		// Add a space before numbers 1-9 to make top10 list appear more uniform
		if (count < 9) {
			// An attempt to make the word / frequency printouts more uniform, regardless of word length
			if(size <= 4) {
				return "\n " + (count + 1) + ") Word: " + word + "\t\t\t\tFrequency: " + frequency;
			} else if (size > 4 && size <= 11) {
				return "\n " + (count + 1) + ") Word: " + word + "\t\t\tFrequency: " + frequency;
			} else if (size > 11 && size <= 13){
				return "\n " + (count + 1) + ") Word: " + word + "\t\tFrequency: " + frequency;
			} else {
				return "\n " + (count + 1) + ") Word: " + word + "\tFrequency: " + frequency;
			}
		} else {
			if(size <= 4) {
				return "\n" + (count + 1) + ") Word: " + word + "\t\t\t\tFrequency: " + frequency;
			} else if (size > 4 && size <= 11) {
				return "\n" + (count + 1) + ") Word: " + word + "\t\t\tFrequency: " + frequency;
			} else if (size > 11 && size <= 13){
				return "\n" + (count + 1) + ") Word: " + word + "\t\tFrequency: " + frequency;
			} else {
				return "\n" + (count + 1) + ") Word: " + word + "\tFrequency: " + frequency;
			}
		
		}
		
	}
		
	/** closeProgram() Method uses ConfirmBox class to confirm is user wants to quit */
	public static void closeProgram() {
       // Ask if user wants to exit (no title necessary, leave blank)
       Boolean answer = ConfirmBox.display("", "Are you sure you want to quit?");
       if (answer) {
           // Run any necessary code before window closes:
		   try {
	    		// Drop and re-create words table
				Database.deleteTable("words");
				Database.createWordsTable("words");
				System.out.println("Window Closed!");
				window.close();
		   } catch (Exception e) {
			   System.out.println(e.getMessage());
			   e.printStackTrace();
		   }
    	   
       }
       
	}
	
}

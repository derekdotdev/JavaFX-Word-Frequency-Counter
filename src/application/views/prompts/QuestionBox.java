package application.views.prompts;

import java.net.URL;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** QuestionBox class utilizes an internal display method to ask a user to input some 
 *  data pertaining to the web text to be parsed and returns as a String array
 *  @author Derek DiLeo*/
public class QuestionBox {

    // Define String array to be returned to caller
    protected static String[] responses = new String[3];
    
    /** Method that asks the user a question and returns a String of either their response or, if left
     *  blank, a default response which in passed from the caller. 
     *  @param prompts is a String array that contains the all of the text variables for the window (title, heading, labels, etc). 
     *  @param defaultEntries direct the app to parse EAP's The Raven (per professor's original instructions).
     *  @param alertBox holds title and message Strings to provide user with some instructions at launch.
     *  @return String array of the user's responses.
     *  @author Derek DiLeo */
    public static String[] display(String[] prompts, String[] defaultEntries, String[] alertBox) {

    	// Provide instructions to the user
        AlertBox.display(alertBox[0], alertBox[1]);
        
        Stage window = new Stage(); // window is easier to grasp than 'stage'

        // Create StackPanes (two needed for CSS to function correctly)
        StackPane qBoxStackPane1 = new StackPane();
        StackPane qBoxStackPane2 = new StackPane();

        // Create layout and add padding
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));

        // Block any input events until this window is closed
        window.initModality(Modality.APPLICATION_MODAL);

        // Set window title and dimensions
        window.setTitle(prompts[0]);
        window.setMinWidth(250);
        window.setMinHeight(100);

        // Create a label to display prompts[1] passed from caller
        Label header = new Label();
        header.setText(prompts[1]);
        header.setFocusTraversable(true);
        header.setFont(Font.font("Avenir", FontWeight.BOLD, 16));
        
        // Create text fields for user input
        Label siteLabel = new Label();
        siteLabel.setText(prompts[2]);
        TextField siteField = new TextField();
        siteField.setPromptText(prompts[3]);
        
        Label startLabel = new Label();
        startLabel.setText(prompts[4]);
        TextField startField = new TextField();
        startField.setPromptText(prompts[5]);
        
        Label endLabel = new Label();
        endLabel.setText(prompts[6]);
        TextField endField = new TextField();
        endField.setPromptText(prompts[7]);

        // Create two buttons and set their behavior
        Button submitButton = new Button("Submit");
        Button testButton = new Button("    ");
        
        // Make testButton an easter egg (hover will change cursor)
        // see bottom for testButton.setOnMouseEntered/Exited Methods
        testButton.setOpacity(0.00); 
        testButton.setFocusTraversable(false);
        
        // When "Submit" button clicked, 
        // validate and collect user responses
        submitButton.setOnAction(e -> {
        	// Validate URL, start and end fields
        	String site = siteField.getText();
        	String start = startField.getText();
        	String end = endField.getText();
        	boolean validURL = isValidURL(site);
        	boolean validEntries = areValidEntries(start, end);
        	
        	if(validURL) {
        		if(validEntries) {
        			responses[0] = site;
        			responses[1] = start;
        			responses[2] = end;
        			window.close();
        		} else {
        			AlertBox.display("Error", "Start/Finish fields cannot be left blank.");
        		}
        	} else {
        		AlertBox.display("Error Invalid URL", "Please Try Again.");
        	}
        	
        });
        
        // Allow Enter key to trigger "Submit" button
        submitButton.setOnKeyPressed(e -> {
        	if (e.getCode().equals(KeyCode.ENTER)) {
        		submitButton.fire();
            }
        });
        
        // When "Test" button clicked, use default entries
        // Not focus-traversable, no ENTER key functionality
        testButton.setOnAction(e -> {
            responses[0] = defaultEntries[0];
            responses[1] = defaultEntries[1];
            responses[2] = defaultEntries[2];
            window.close();
        });
        
        // Create an AnchorPane container for buttons at bottom
        // Anchor them to either side of the Pane
        AnchorPane buttons = new AnchorPane();
        AnchorPane.setLeftAnchor(submitButton, 0.0);
        AnchorPane.setRightAnchor(testButton, 0.0);
        
        // Add all elements to layout
        buttons.getChildren().addAll(submitButton, testButton);
        layout.getChildren().addAll(header, siteLabel, siteField, startLabel, startField, endLabel, endField, buttons);
        layout.setAlignment(Pos.CENTER_LEFT);
        qBoxStackPane1.getChildren().addAll(qBoxStackPane2, layout);

        // Create and set Scene from stackPane1
        Scene scene = new Scene(qBoxStackPane1);
//        scene.getStylesheets().add("//styles.css"); Need to use FXML to enable CSS!
        window.setScene(scene);
        
        // If mouse hovers over button, change cursor
        testButton.setOnMouseEntered(e -> {
    		scene.setCursor(Cursor.HAND);
        });
        
        // When mouse leaves, change it back
        testButton.setOnMouseExited(e -> {
        	scene.setCursor(Cursor.DEFAULT);
        });

        // Show window and wait for user interaction
        window.showAndWait();
        return responses;
    }
  
    /** Method to validate URL entered by user.
     *  @param urlString is the URL passed into QuestionBox by user.
     *  @return boolean value of URL validity: valid = true. */
    public static boolean isValidURL(String urlString) {
    	try {
    		URL url = new URL(urlString);
    		url.toURI();
    		return true;
    	} catch (Exception e) {
    		System.out.println("Invalid URL passed to QuestionBox!");
    		return false;
    	}
    	
    }
    
    /** Method to validate that start and end fields are not left blank.
     *  @param start is the start field passed into QuestionBox by user.
     *  @param end is the end field passed into QuestionBox by user.
     *  @return boolean if both are not left blank. */
    public static boolean areValidEntries(String start, String end) {
    	if (!start.equals("") && !end.equals("")) {
    		return true;
    	} 
    	return false;
    }
    
}

package application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/** Controller class for Main.fxml which calls 
 *  initialize() prior to launching GUI. 
 *  @author Derek DiLeo */
public class MainDefaultController implements Initializable {
	
	// Declare local FXML Tags
	@FXML Button showHideButton;
	@FXML Hyperlink hyperlink;
	@FXML ImageView image;
	@FXML ImageView image2;
	@FXML Label copyrightLabel;
	@FXML Label labelText;
	@FXML MenuBar menuBar;
	@FXML MenuItem fileCloseButton;
	@FXML MenuItem helpAboutButton;
	
	// int value of copyright symbol for GUI footer  
	private int copyrightSymbol = 169;
	
	// URL of GitHub repo
	private String aboutSite = "https://github.com/derekdileo/cen3024-module-006-word-frequency-count-gui";

	/** Method runs prior to GUI being displayed and calls 'Main.sbAllString' 
	 *  String built in MainController to be pushed to GUI 
	 *  @author Derek DiLeo */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Copyright symbol for GUI footer
		String s = Character.toString((char) copyrightSymbol);
		copyrightLabel.setText(s);
		
		// Display generic image
		Image imageFile2 = new Image("/resources/img/image2.png");
		ImageView image2 = new ImageView();
		image2.setImage(imageFile2);			
		
		// Display top ten results
		labelText.setText(Main.sbTenString);
		
	}
	
	/** Method to call Main.closeProgram() when File, Close clicked.
	 *  @param event when user clicks File, Close */
	@FXML public void handleFileClose(ActionEvent event) {
		Main.closeProgram();
	}

	/** Method to launch github repo page on default browser.
	 *  @param event when user clicks Help, About */
	@FXML public void handleHelpAbout(ActionEvent event) {
		try {
			  Desktop desktop = java.awt.Desktop.getDesktop();
			  URI oURL = new URI(aboutSite);
			  desktop.browse(oURL);
			} catch (Exception e) {
			  e.printStackTrace();
			}
		
	}

	/** Method launches website where poem was sourced.
	 *  @param event when user clicks on "Source" hyperlink in footer. */
	@FXML public void handleHyperlink(ActionEvent event) {
		try {
			  Desktop desktop = java.awt.Desktop.getDesktop();
			  URI oURL = new URI(Main.userResponses[0]);
			  desktop.browse(oURL);
			} catch (Exception e) {
			  e.printStackTrace();
			}
	}

	/** Method to switch the Scene to AllResultsDefault.fxml
	 *  @param event when user clicks "Show All Results" button. */
	@FXML public void handleShowHideButton(ActionEvent event) {
		
		try {
			Parent allResultsViewParent = FXMLLoader.load(getClass().getResource("AllResultsDefault.fxml"));
			Scene allResultsViewScene = new Scene(allResultsViewParent);
			allResultsViewScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			// Get the Stage information
			Stage window = (Stage)(((Node) event.getSource()).getScene().getWindow());
			window.setScene(allResultsViewScene);
			window.show();
		} catch (IOException e) {
			System.out.println("Error switching to AllResultsDefault.fxml: IOException: " + e);
			e.printStackTrace();
		}
		
	}
	
}

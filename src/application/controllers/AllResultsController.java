package application.controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import application.Main;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/** Controller class for AllResults.fxml which calls 
 *  initialize() prior to launching GUI. 
 *  @author Derek DiLeo */
public class AllResultsController implements Initializable  {

	// Instantiate logger
	private Logger logger = Main.main_log;

	// Declare local FXML Tags
	@FXML Label copyrightLabel;
	@FXML MenuItem fileCloseButton;
	@FXML Hyperlink handleHyperlink;
	@FXML MenuItem helpAboutButton;
	@FXML ImageView image;
	@FXML MenuBar menuBar;
	@FXML Button showHideButton;
	@FXML ScrollPane scrollPaneAllResults;
	@FXML TextArea textAreaAllResults;
	
	// int value of copyright symbol for GUI footer  
	private int copyrightSymbol = 169;
	
	// URL of GitHub repo
	private String aboutSite = "https://github.com/derekdotdev/cen3024-module-006-word-frequency-count-gui";
	
	/** Method runs prior to GUI being displayed and calls 'Main.sbAllString' 
	 *  String built in MainController to be pushed to GUI 
	 *  @author Derek DiLeo */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Copyright symbol for GUI footer
		String s = Character.toString((char) copyrightSymbol);
		copyrightLabel.setText(s);
		
		// Push All Results to GUI
		textAreaAllResults.setText(Main.sbAllString);

		logger.info("Loading Raven view all results from AllResultsController!");
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
			  logger.info("Help > about clicked in AllResultsController!");
			} catch (Exception e) {
			  e.printStackTrace();
			  logger.severe("Unable to handle help > about from AllResultsDefaultController: " + e.getMessage());
			}
		
	}

	/** Method launches website where text was sourced.
	 *  @param event when user clicks on "Source" hyperlink in footer. */
	@FXML public void handleHyperlink(ActionEvent event) {
		try {
			  Desktop desktop = java.awt.Desktop.getDesktop();
			  URI oURL = new URI(Main.userResponses[0]);
			  desktop.browse(oURL);
			  logger.info("Hyper link clicked in AllResultsController!");
			} catch (Exception e) {
			  e.printStackTrace();
			  logger.severe("Unable to handle hyperlink in AllResultsController: " + e.getMessage());
			}
	}

	/** When this method is called, it will change the Scene to Main.fxml
	 *  @param event when user clicks "Show Top Ten Results" button. */
	@FXML public void handleShowHideButton(ActionEvent event) {
		try {
			Parent mainViewParent = FXMLLoader.load(getClass().getResource("/application/views/Main.fxml"));
			Scene mainViewScene = new Scene(mainViewParent);
			mainViewScene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			// Get the Stage information
			Stage window = (Stage)(((Node) event.getSource()).getScene().getWindow());
			window.setScene(mainViewScene);
			window.show();
		} catch (IOException e) {
			System.out.println("Error switching to Main.fxml: IOException: " + e);
			e.printStackTrace();
			logger.info("Unable to switch to Main.fxml from AllResultsController: " + e.getMessage());
		}
		
	}

}
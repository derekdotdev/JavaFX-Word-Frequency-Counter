package application.views.prompts;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** AlertBox class utilizes its display method to inform the user of something and persists until closed.
 * @author Derek DiLeo */
public class AlertBox {

    /** display() method displays the AlertBox to the user
     * @param title title of the pop-up window to be displayed
     * @param message message displayed in the pop-up window via Label class
     * @author Derek DiLeo  */
    public static void display(String title, String message) {
        Stage window = new Stage(); // window is easier to grasp than 'stage'

        // Block any input events until this window is closed
        window.initModality(Modality.APPLICATION_MODAL);

        // Set window title and dimensions
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(100);

        // Ask user for fibonacci position (positive integers only!)
        Label label = new Label();
        label.setText(message);

        // Create button and define behavior
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> window.close());
        
        // Allow Enter key to trigger "Submit" button
        okButton.setOnKeyPressed(e -> {
        	if (e.getCode().equals(KeyCode.ENTER)) {
        		okButton.fire();
            }
        });

        // Create layout, add padding + elements, set center alignment
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(label, okButton);
        layout.setAlignment(Pos.CENTER);

        // Create and set Scene from VBox layout
        Scene scene = new Scene(layout);
//        scene.getStylesheets().add("//styles.css");     Need to use FXML to enable CSS!!
//        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        window.setScene(scene);

        // Show window and wait for user interaction
        window.showAndWait();
    }
}

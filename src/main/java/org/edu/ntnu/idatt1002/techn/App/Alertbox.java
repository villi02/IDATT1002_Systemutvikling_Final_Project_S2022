package org.edu.ntnu.idatt1002.techn.App;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * /Author Stian Wilhelmsen
 * /Version 0.2
 * A simple class which has a public method to display an alertbox when something
 * is wrong in the program.
 */
public class Alertbox {

    /**
     * A method to display message
     * Static method, because it is used all over in the program
     * @param title Title of message as a String
     * @param message The message as a String
     */
    public static void display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(250);

        Label label = new Label();
        label.setText(message);

        Button closeButton = new Button("Ok");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}


package org.edu.ntnu.idatt1002.techn.App;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.edu.ntnu.idatt1002.techn.Database;


/**
 * class that starts the application
 * /author Aleksander Olsvik
 * /version 0.3
 */
public class TournamentApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * A meethod to handle start of application
     * @param stage The stage as a Stage
     * @throws Exception if the file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws Exception {
        Database.createDatabase("Users.db");
        Database.createTables("Users.db");
        Database.addAdmin();
        Parent root = FXMLLoader.load(getClass().getResource("/Homepage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("E-sport Tournament");

        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            logout(stage);
        });

    }

    /**
     * A method to handle the logout sequence
     * @param stage the stage as a Stage
     */
    public void logout(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to exit");
        alert.setContentText("Do you want to save before exiting?");

        if (alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }

}

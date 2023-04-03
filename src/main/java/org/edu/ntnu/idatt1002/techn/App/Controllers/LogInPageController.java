package org.edu.ntnu.idatt1002.techn.App.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.edu.ntnu.idatt1002.techn.App.Alertbox;
import org.edu.ntnu.idatt1002.techn.Database;
import org.edu.ntnu.idatt1002.techn.TournamentFileManagement;

import java.io.IOException;

/**
 * /Author Balder August Tørud Rømo
 * /Version v0.7
 */
public class LogInPageController {

    /**
     * Initializing elements
     */
    public TextField txtInpUser;
    public PasswordField tetInpPSW;
    public Button button;

    private Scene scene;
    private Stage stage;

    Database db = new Database();


    /**
     * Method to display TeamCreatorScene
     * @param event the event as an Event
     * @throws IOException - if loading fails
     */
    public void switchToTeamCreator(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/TeamCreatorScene.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Method for displaying FrontPage(LogInPage). If user is already logged in, this method will switch
     * to teamCreator page.
     * @param event the event as an Event
     * @throws IOException - if loading fails
     */
    public void switchToFrontPage(ActionEvent event) throws IOException{
        if (Database.isLoggedIn()) {
            switchToTeamCreator(event);
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/LogInPage.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Method for displaying StartUpPage
     * @param event the event as an Event
     * @throws IOException - if loading fails
     */
    public void switchToStartUpPage(ActionEvent event)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/Homepage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method for viewing in View Only mode
     * @param event the event as an Event
     * @throws IOException - if loading fails
     */
    public void switchToViewOnlyMode(ActionEvent event)throws IOException{
        try{
            if (TournamentFileManagement.readTournamentRunner() == null) {
                Alertbox.display("Error", "No tournament available");
            } else {
                Parent root = FXMLLoader.load(getClass().getResource("/ViewOnlyModeScene.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e){
            Alertbox.display("Error", "No tournament available");
        }
    }


    /**
     * Method to handle when the login button is clicked
     * @param event the event as an Event
     * @throws IOException - if loading fails
     */
    public void btnClickLogin(ActionEvent event) throws IOException {

        String username = txtInpUser.getText();
        String password = tetInpPSW.getText();
        boolean success = Database.instance().checkUser(username, password);

        if (Database.isLoggedIn()) {
            switchToTeamCreator(event);
        } else if (success){
            Database.setLoggedIn(true);
            switchToTeamCreator(event);

        } else {
            Alertbox.display("Login error", "Wrong username or password");
        }

    }

    /**
     * Method to handle when help button is clicked
     * @param event the event as an Event
     */
    public void helpButton(ActionEvent event){
        Alertbox.display("Help","If you're a guest user: Press the view only button.\nIf you're an admin: Press the login button");
    }

    /**
     * Method to handle the user logout
     * @param event the event as an Event
     */
    public void logout(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you want to save before exiting?: ");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}

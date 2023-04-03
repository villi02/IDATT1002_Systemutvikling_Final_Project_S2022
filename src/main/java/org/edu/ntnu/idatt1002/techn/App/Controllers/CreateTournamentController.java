package org.edu.ntnu.idatt1002.techn.App.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.edu.ntnu.idatt1002.techn.App.Alertbox;
import org.edu.ntnu.idatt1002.techn.FileManagement;
import org.edu.ntnu.idatt1002.techn.Teams.Team;
import org.edu.ntnu.idatt1002.techn.Tournament.Runners.SingleEliminationRunner;
import org.edu.ntnu.idatt1002.techn.Tournament.Tournament;
import org.edu.ntnu.idatt1002.techn.Tournament.Runners.TournamentRunner;
import org.edu.ntnu.idatt1002.techn.TournamentFileManagement;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * The type Create tournament controller.
 * /author Aleksander Olsvik
 * /version 0.6
 */
public class CreateTournamentController implements Initializable {

    private Stage stage;
    private Scene scene;
    private final String[] tournamentTypes = {"Single Elimination"};
    private TournamentRunner tournament;
    private ArrayList<Team> teamsToBeAdded;
    private ArrayList<Team> teamsAddedToTournament = new ArrayList<>();
    private ArrayList<String> teamNamesToBeAdded = new ArrayList<>(); // Legges til i TournamentTeams ListView
    private ArrayList<String> teamNamesAdded = new ArrayList<>();


    /**
     * instantiating the fxml
     */
    @FXML
    public Button teamCreateButton;
    public Button currentTournamentButton;
    public Button homepageButton;
    public Button createTournament;
    public Button previewBracket;
    public ComboBox<String> tournamentType;
    public TextField tournamentNameField;
    public ListView<String> listViewTournamentTeams;
    public ListView<String> listViewTeamsAddedToTournament;
    public ImageView bracketImage;
    public Label labelBracket;

    /**
     * A method to initialize
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            teamsToBeAdded = FileManagement.readAllTeamsFromFiles();
            displayTeams();
        } catch (Exception e) {
            e.printStackTrace();
        }
        listViewTournamentTeams.getItems().addAll(teamNamesToBeAdded);
        tournamentType.getItems().addAll(tournamentTypes);
    }

    /**
     * Go to homepage.
     *
     * @param e the event
     */
    public void goToHomepage(ActionEvent e) {
        try {
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Homepage.fxml")));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Switch to InformationPage
     * @param event on click
     * @throws IOException if the file could not load
     */
    public void switchToTeamInformationPage(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/TeamInfo.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Switch to current tournament scene.
     *
     * @param e the event
     */
    public void switchToCurrentTournamentScene(ActionEvent e) throws IOException {
        if(FileManagement.readAllTeamsFromFiles()==null){
            Alertbox.display("Error", "You must create a tournament before entering this page");
        } else{
            try{
                Parent root = FXMLLoader.load((getClass().getResource("/CurrentTournament.fxml")));
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch(Exception exception){
                exception.printStackTrace();
                Alertbox.display("Error", "You must create a tournament before entering this page");
            }
        }
    }

    /**
     * Switch to team creator.
     *
     * @param e the event
     */
    public void switchToTeamCreator(ActionEvent e) {
        try {
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("TeamCreatorScene.fxml")));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Method to display teams yet to be added to
     * tournament and teams added to tournament
     */
    private void displayTeams() {

        if(!listViewTournamentTeams.getItems().isEmpty()){
            listViewTournamentTeams.getItems().clear();
        }
        if(!listViewTeamsAddedToTournament.getItems().isEmpty()){
            listViewTeamsAddedToTournament.getItems().clear();
        }

        for (Team t : teamsToBeAdded) {
            if(!t.getName().equals("Bypass")) listViewTournamentTeams.getItems().add(t.getName());
        }
        for (Team t : teamsAddedToTournament) {
            if(!t.getName().equals("Bypass")) listViewTeamsAddedToTournament.getItems().add(t.getName());
        }

        addTeams();
        removeTeams();

    }

    /**
     * Method to add teams to a tournament.
     * Will be added by clicking on the team in the list
     * When added to the tournament, it will be removed from the ListView
     */
    private void addTeams() {
        listViewTournamentTeams.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            String clickedTeamName = String.valueOf(listViewTournamentTeams.getSelectionModel().getSelectedItems());
            for (Team t : teamsToBeAdded) {
                String currentTeamName = "[" + t.getName() + "]";
                if (currentTeamName.equalsIgnoreCase(clickedTeamName)) {
                    teamsToBeAdded.remove(t);
                    teamNamesToBeAdded.remove(t.getName());
                    teamsAddedToTournament.add(t);
                    teamNamesAdded.add(t.getName());
                    displayTeams();
                    break;
                }
            }
        });
    }

    /**
     * Method to remove teams to be registered in a tournament.
     * Will be removed by clicking on the team in the tournament
     * When removed, it will be added to the other ListView
     */
    private void removeTeams() {
        listViewTeamsAddedToTournament.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            String clickedTeamName = String.valueOf(listViewTeamsAddedToTournament.getSelectionModel().getSelectedItems());
            for (Team t : teamsAddedToTournament) {
                String thisName = "[" + t.getName() + "]";
                if (thisName.equalsIgnoreCase(clickedTeamName)) {
                    teamsAddedToTournament.remove(t);
                    teamNamesAdded.remove(t.getName());
                    teamNamesToBeAdded.add(t.getName());
                    teamsToBeAdded.add(t);
                    displayTeams();
                    break;
                }
            }
        });
    }


    /**
     * Create tournament from the inputs, which is the teams, the name and the type
     * This will create a SingleElimination tournament now, but in future updates it can create the other types as well
     *
     * @param e the event
     */
    public void createTournament(ActionEvent e){
        if (getTournamentName().isBlank()) {
            Alertbox.display("Error", "There is no name for the tournament.");
        }else if(!Objects.equals(tournamentType.getValue(), "Single Elimination")) {
            Alertbox.display("Error", "No tournament is chosen.");
        } else if(teamsAddedToTournament.size()<2) {
            Alertbox.display("Error", "There must be a minimum of two teams in a tournament");
        } else if(teamsAddedToTournament.size()>16){
            Alertbox.display("Error", "There can be a maximum of 16 teams in a tournament");
        }else{
            if (tournamentType.getValue().equals("Single Elimination"))
            tournament = new SingleEliminationRunner(new Tournament(getTournamentName(), tournamentType.getValue(),teamsAddedToTournament));
            else if (tournamentType.getValue().equals("Round Robin"))
                tournament = new SingleEliminationRunner(new Tournament(getTournamentName(), tournamentType.getValue(),teamsAddedToTournament));
            else
                tournament = new SingleEliminationRunner(new Tournament(getTournamentName(), tournamentType.getValue(),teamsAddedToTournament));

            //tournament.getTournament().addAllTeams(teamsAddedToTournament);
            tournament.createTournament();
            try {
                TournamentFileManagement.writeTournamnetRunnerToFile(tournament);
            } catch (IOException ex) {
                Alertbox.display("Error", "Something went wrong when storing the tournament");
            }
            try{
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/CurrentTournament.fxml")));
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }catch(Exception exception){
                exception.printStackTrace();
            }
        }
    }

    /**
     * Preview tournament bracket.
     * Will load an image on the scene
     * @param e the event
     */
    public void previewTournamentBracket(ActionEvent e){
        Image image = null;
        try{
            if (teamsAddedToTournament.size() <= 2) {
                image = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/Images/teams2.png"))));
            } else if (teamsAddedToTournament.size() <= 4) {
                image = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/Images/teams4.png"))));
            } else if (teamsAddedToTournament.size() <= 8) {
                image = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/Images/teams8.png"))));
            } else if (teamsAddedToTournament.size() <= 16) {
                image = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/Images/teams16.png"))));
            }
            if (image != null) {
                bracketImage.setVisible(true);
                labelBracket.setVisible(true);
                bracketImage.setImage(image);
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }


    }

    /**
     * Get tournament name string.
     * @return the name as a String
     */
    private String getTournamentName() {
        return tournamentNameField.getText();
    }
}

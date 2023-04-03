package org.edu.ntnu.idatt1002.techn.App.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.edu.ntnu.idatt1002.techn.App.Alertbox;
import org.edu.ntnu.idatt1002.techn.FileManagement;
import org.edu.ntnu.idatt1002.techn.Teams.Player;
import org.edu.ntnu.idatt1002.techn.Teams.Team;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
import java.util.ResourceBundle;

/**
 * /author Jonatan Andre Vevang and Stian Wilhelmsen
 * /version v0.4
 */
public class TeamInfoController implements Initializable {

    /**
     * Initialize key components
     */
    @FXML
    public ListView<String> teamList;
    public ListView<String> playerList;
    public ListView<String> playerInfoList;
    public Label teamNameLabel;

    /**
     * Initialize key elements
     */
    private Stage stage;
    private Scene scene;
    private Team currentTeam;
    public ArrayList<Team> teams;
    private ArrayList<String> teamNames;
    private ArrayList<String> playerNamesInCurrentTeam;

    /**
     * A constructor for the TeamInfoController class
     * @throws IOException - if eading fails
     */
    public TeamInfoController() throws IOException{
        this.teams = FileManagement.readAllTeamsFromFiles();
        this.playerNamesInCurrentTeam = new ArrayList<>();
        this.teamNames = new ArrayList<>();
    }

    public void switchToCurrentTournament(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/CurrentTournament.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e){
            Alertbox.display("No current tournament", "You must create a tournament before entering this page");
        }
    }

    /**
     * The method for switching to the Tournament generator page
     * @param event the event
     * @throws IOException when the method can't find the file
     */
    public void switchToTournamentGenerator(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/TournamentCreatorScene.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * A method to switch to the front page
     * @param event the event
     * @throws IOException - if loading fails
     */
    public void switchToFrontPage(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/Homepage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * A method for switching to the team creator page
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
     * A method to display player information
     */
    private void displayPlayerInformation() {
        playerInfoList.getItems().clear();
        playerList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            String currentPlayerSelected = String.valueOf(playerList.getSelectionModel().getSelectedItems());
            for (Player p : currentTeam.getPlayers()) {
                String playerName = "[" + p.getIGN() + "]";
                if (playerName.equals(currentPlayerSelected)) {
                    displayCurrentPlayer(p);
                }
            }

        });
    }

    /**
     * A method to display teams
     */
    private void displayTeams() {
        for (Team team : teams) {
            if(!team.getName().equals("Bypass")) teamNames.add(team.getName());
        }

        teamList.getItems().clear();
        teamList.getItems().addAll(teamNames);
        teamList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            String currenTeamName = String.valueOf(teamList.getSelectionModel().getSelectedItems());
            for (Team t : teams) {
                String name = "[" + t.getName() + "]";
                if (name.equalsIgnoreCase(currenTeamName)) {
                    setCurrentTeam(t);
                    setPlayerNamesInCurrentTeam();
                    displayCurrentTeam();

                }
            }
        });
    }

    /**
     * A method to set players in current team
     */
    private void setPlayerNamesInCurrentTeam() {
        playerNamesInCurrentTeam.clear();
        for (int i = 0; i < currentTeam.getSize(); i++) {
            playerNamesInCurrentTeam.add(currentTeam.getPlayers().get(i).getIGN());
        }
    }

    /**
     * A method to display current team
     */
    private void displayCurrentTeam() {
        playerList.getItems().clear();
        String wins = "Wins: " + currentTeam.getWins();
        String losses = "Losses: " + currentTeam.getLosses();
        String tournamentWins= "Tournament wins: " + currentTeam.getTournamentWins();
        teamNameLabel.setText(currentTeam.getName());
        playerList.getItems().addAll(wins,losses,tournamentWins,"");
        playerList.getItems().addAll(playerNamesInCurrentTeam);
    }

    /**
     * A method to display current player
     * @param p - the player
     */
    private void displayCurrentPlayer(Player p) {
        playerInfoList.getItems().clear();
        playerInfoList.getItems().addAll(getPlayerInfo(p));
    }

    /**
     * A method to get player info
     * @param p player who's info you want
     * @return the infor as a list<String>
     */
    private List<String> getPlayerInfo(Player p){
        List<String> playerInfo = new ArrayList<>();
        String[] info = p.toString().split("\n");
        for (String s : info) {
            playerInfo.add(s);
            playerInfo.add("\n");
        }
        return playerInfo;
    }

    /**
     * A method to set current team
     * @param team the team to be set to
     */
    private void setCurrentTeam(Team team) {
        this.currentTeam = team;
    }

    /**
     * A method to switch
     * @param event - click
     * @throws IOException - if loading fails
     */
    public void switchToViewOnlyMode(ActionEvent event) throws IOException{
        if (FileManagement.readAllTeamsFromFiles() == null) {
            Alertbox.display("Error", "No tournament available");
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/ViewOnlyModeScene.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * A method to initialize
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            teams = FileManagement.readAllTeamsFromFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayTeams();
        displayPlayerInformation();
    }
}
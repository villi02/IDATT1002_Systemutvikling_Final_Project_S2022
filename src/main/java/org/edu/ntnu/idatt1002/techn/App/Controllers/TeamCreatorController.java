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
import java.util.List;
import java.util.ResourceBundle;

/**
 * /author Stian Wilhelmsen
 * /version v0.9
 */
public class TeamCreatorController implements Initializable {

    /**
     * Initializing key components
     */

    @FXML
    public TextField txtInpFirstName;
    public TextField txtInpLastName;
    public TextField txtInpIGN;
    public TextField txtInpAge;
    public TextField txtInpNationality;
    public CheckBox checkboxPlayer;
    public CheckBox checkboxCoach;
    public CheckBox checkboxCaptain;
    public TextField txtInpTeamName;
    public TextField txtInpTeamNationality;
    public ListView<String> teamList;
    public ListView<String> currentTeamList;
    public Label txtCurrentTeamName;
    public Label txtCurrentPlayer;
    private Stage stage;
    private Scene scene;

    private Team currentTeam;
    private Player currentPlayer;
    public ArrayList<Team> teams;
    private ArrayList<String> playerNamesInCurrentTeam;
    private ArrayList<String> teamNames;
    private List<Character> wrongInput = List.of();

    /**
     * Constructor for TeamCreatorController
     */
    public TeamCreatorController() {
        try{
            this.teams = FileManagement.readAllTeamsFromFiles();
        } catch (Exception e){
            e.printStackTrace();
            this.teams=new ArrayList<>();
        }
        this.playerNamesInCurrentTeam = new ArrayList<>();
        this.teamNames = new ArrayList<>();
    }

    private boolean validateInput(String input){
        for(int i=0; i<input.length(); i++){
            if(wrongInput.contains(input.charAt(i))) return false;
        }
        return true;
    }

    /**
     * Switches page to CurrentTournament
     * @param event the event as an Event
     * @throws IOException
     */
    public void switchToCurrentTournament(ActionEvent event) throws IOException {
        if(FileManagement.readAllTeamsFromFiles()==null){
            Alertbox.display("Error", "You must create a tournament before entering this page");
        } else{
            try{
                Parent root = FXMLLoader.load((getClass().getResource("/CurrentTournament.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch(Exception e){
                e.printStackTrace();
                Alertbox.display("Error", "You must create a tournament before entering this page");
            }
        }
    }

    /**
     * Method to switch to TournamentGenerator page
     * @param event the event as an Event
     * @throws IOException if file loading goes wrong
     */
    public void switchToTournamentGenerator(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/TournamentCreatorScene.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method to switch to FrontPage
     * @param event the event as an Event
     * @throws IOException if file loading fails
     */
    public void switchToFrontPage(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/Homepage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method to switch to TeamInformationPage
     * @param event the event as an Event
     * @throws IOException if file loading fails
     */
    public void switchToTeamInformationPage(ActionEvent event)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/TeamInfo.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




    /**
     * Method to handle when the add player button is clicked
     * If no team is registered yet, method will give alertbox and not register the player.
     */
    public void btnClickAddPlayer() throws IOException {
        if (teams.size() == 0 || currentTeam == null) {
            Alertbox.display("Error", "No team is registered yet.");
        } else {
            Player player = getPlayer();
            if(player!=null){
                currentTeam.addPlayer(player);
                playerNamesInCurrentTeam.add(player.getIGN());
                displayCurrentTeam();
                FileManagement.writeAllTeamsToFile(teams);
            }
        }
    }

    /**
     * Method to handle when add team button is clicked
     * If team is already registered, alertbox will display and team wont be registered.
     */
    public void btnClickAddTeam() throws IOException {
        String teamName = txtInpTeamName.getText();
        String teamNationality = txtInpTeamNationality.getText();

        if (teamNames.contains(teamName)) {
            Alertbox.display("Error", "This team has already been registered");
        } else {
            Team teamToAdd = new Team(teamName, teamNationality);
            teams.add(teamToAdd);
            setCurrentTeam(teamToAdd);
            setPlayerNamesInCurrentTeam();
            displayCurrentTeam();
            displayTeams();
            FileManagement.writeAllTeamsToFile(teams);
        }
    }

    /**
     * A method to handle when the remove team button is clicked
     * Removes the team.txt file and from teamNameList.
     */
    public void btnRemoveTeam() throws IOException {
        teams.remove(currentTeam);
        FileManagement.removeTeam(currentTeam);
        currentTeam = null;
        FileManagement.writeAllTeamsToFile(teams);
        displayTeams();
    }

    /**
     * Method to handle when the clear player button is clicked
     * Clears all player information text fields.
     */
    public void btnClickClearPlayerField() {
        clearAllPlayerFields();
    }

    /**
     * A method to handle when the remove player button is clicked
     * If no player is selected, alertbox will display and no player is removed.
     */
    public void btnRemovePlayer() throws IOException {
        if (currentPlayer == null)
            Alertbox.display("Error", "You need to select the player to be removed");
        currentTeam.removePlayer(currentPlayer);
        displayCurrentTeam();
        playerNamesInCurrentTeam.remove(currentPlayer.getIGN());
        currentPlayer = null;
        FileManagement.writeAllTeamsToFile(teams);
        displayCurrentTeam();
        txtCurrentPlayer.setText("");
    }

    /**
     * Method to get player
     * Gets information from player text fields and if a text field is blank, alertbox will display
     * and the player is not registered.
     * @return the player as a Player
     */
    private Player getPlayer() {
        Player playerToBeAdded = null;
        if (txtInpAge.getText().equals("") || txtInpIGN.getText().equals("") || txtInpFirstName.getText().equals("null")
                || txtInpLastName.getText().equals("") || txtInpNationality.getText().equals("") || txtInpAge.getText().equals("")) {
            Alertbox.display("Error!", "TextField cant be blank");
        } else if (!validateInput(txtInpAge.getText()) || !validateInput(txtInpIGN.getText()) ||
                !validateInput(txtInpFirstName.getText()) || !validateInput(txtInpLastName.getText()) ||
                !validateInput(txtInpNationality.getText()) || !validateInput(txtInpAge.getText()))
        {
            Alertbox.display("Error!", "TextField can't contain these characters: " + wrongInput);
        }else{

            String firstName = txtInpFirstName.getText();
            String lastName = txtInpLastName.getText();
            String IGN = txtInpIGN.getText();
            String Nationality = txtInpNationality.getText();
            int age;
            try{
                age = Integer.parseInt(txtInpAge.getText());
            } catch(Exception e){
                Alertbox.display("Error", "The age must be a number");
                return null;
            }
            if (playerNamesInCurrentTeam.contains(IGN)) {
                Alertbox.display("Error", "Player is already registered");
            } else {
                if (checkboxCaptain.isSelected()) { playerToBeAdded = new Player(age, firstName, lastName, IGN, Nationality, "Captain"); }
                if (checkboxPlayer.isSelected()) { playerToBeAdded = new Player(age, firstName, lastName, IGN, Nationality, "Player"); }
                if (checkboxCoach.isSelected()) { playerToBeAdded = new Player(age, firstName, lastName, IGN, Nationality, "Coach"); }
                return playerToBeAdded;
            }
        }

        return null;
    }





    /**
     * Method to display all teams registered. Will add all teams from teamnames to teamList
     * and display teams.
     */
    private void displayTeams() {
        teamNames.clear();
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
                    currentPlayer = null;
                    txtCurrentPlayer.setText("");
                }
            }
        });
    }

    /**
     * Method to handle displaying current team
     */
    private void displayCurrentTeam() {
        currentTeamList.getItems().clear();
        currentTeamList.getItems().addAll(playerNamesInCurrentTeam);
        currentTeamList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            String currenPlayerName = String.valueOf(currentTeamList.getSelectionModel().getSelectedItems());
            for (Player p : currentTeam.getPlayers()) {
                String name = "[" + p.getIGN() + "]";
                if (name.equalsIgnoreCase(currenPlayerName)) {
                    this.currentPlayer = p;
                    txtCurrentPlayer.setText(currentPlayer.getIGN());
                }
            }
        });
    }

    /**
     * Method to set player names in current team
     * When a team is selected, playername list will update to currentTeam chosen
     */
    private void setPlayerNamesInCurrentTeam() {
        playerNamesInCurrentTeam.clear();
        for (int i = 0; i < currentTeam.getSize(); i++) {
            playerNamesInCurrentTeam.add(currentTeam.getPlayers().get(i).getIGN());
        }
    }

    /**
     * Method to set current team
     * @param team The team to be set to as the currentTeam
     */
    private void setCurrentTeam(Team team) {
        this.currentTeam = team;
        txtCurrentTeamName.setText(currentTeam.getName());
    }

    /**
     * method to clear all player fields
     */
    private void clearAllPlayerFields() {
        txtInpFirstName.clear();
        txtInpLastName.clear();
        txtInpIGN.clear();
        txtInpAge.clear();
        txtInpNationality.clear();
    }

    /**
     * (custom) Method to initialize
     * @param url the url as a URL
     * @param resourceBundle the resource bundle as a ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            teams = FileManagement.readAllTeamsFromFiles();
        } catch (IOException e) {
            teams=new ArrayList<>();
        }
        displayTeams();
    }

}

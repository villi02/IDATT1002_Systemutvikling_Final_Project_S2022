package org.edu.ntnu.idatt1002.techn.App.Controllers;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.edu.ntnu.idatt1002.techn.App.Alertbox;
import org.edu.ntnu.idatt1002.techn.FileManagement;
import org.edu.ntnu.idatt1002.techn.Teams.Team;
import org.edu.ntnu.idatt1002.techn.Tournament.Match;
import org.edu.ntnu.idatt1002.techn.Tournament.Runners.TournamentRunner;
import org.edu.ntnu.idatt1002.techn.TournamentFileManagement;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CurrentTournamentController implements Initializable {

    /**
     * Initialize elements
     */
    @FXML
    public Label team1;
    public Label team2;
    public Label team3;
    public Label team4;
    public Label team5;
    public Label team6;
    public Label team7;
    public Label team8;
    public Label team9;
    public Label team10;
    public Label team11;
    public Label team12;
    public Label team13;
    public Label team14;
    public Label team15;
    public Label team16;
    public Label team17;
    public Label team18;
    public Label team19;
    public Label team20;
    public Label team21;
    public Label team22;
    public Label team23;
    public Label team24;
    public Label team25;
    public Label team26;
    public Label team27;
    public Label team28;
    public Label team29;
    public Label team30;
    public TextField score1;
    public TextField score2;
    public TextField score3;
    public TextField score4;
    public TextField score5;
    public TextField score6;
    public TextField score7;
    public TextField score8;
    public TextField score9;
    public TextField score10;
    public TextField score11;
    public TextField score12;
    public TextField score13;
    public TextField score14;
    public TextField score15;
    public TextField score16;
    public TextField score17;
    public TextField score18;
    public TextField score19;
    public TextField score20;
    public TextField score21;
    public TextField score22;
    public TextField score23;
    public TextField score24;
    public TextField score25;
    public TextField score26;
    public TextField score27;
    public TextField score28;
    public TextField score29;
    public TextField score30;
    public VBox roundOf16;
    public VBox roundOf8;
    public VBox roundOf4;
    public VBox roundOf2;
    public Label txtTitle;
    public Button btnAdvanceTournament;
    public Label txtDisplayWinner;

    /**
     * Initialize values for later use
     */
    private TournamentRunner tournament;
    private ArrayList<Label> teamLabels;
    private ArrayList<TextField> scoreTxtFields;
    private int[][] limits = {{65,90}, {97,122}};

    /**
     * (custom) Method to initialize
     * run addTeamLabels();
     * run addResultsLabels();
     * run disableVboxWhenInvisible();
     * run cleanUpBracketView();
     * @param url the url as a URL
     * @param resourceBundle the resource bundle as a ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            tournament = TournamentFileManagement.readTournamentRunner();
            txtTitle.setText(tournament.getTournament().getName());
        } catch (IOException e) {
            Alertbox.display("Error", "Something went wrong when loading the tournament");
        }

        teamLabels = new ArrayList<>();
        scoreTxtFields = new ArrayList<>();
        txtDisplayWinner.setVisible(false);

        addTeamLabels();
        addResultsLabels();
        disableVboxWhenInvisible();
        fillInMatchesThisRound();
        if(tournament.getMatchesPlayed().size()!=0){
            fillInMatchesPlayedThisRound();
        }
        cleanUpBracketView();
        if(!tournament.isFinished()){
            for(int i=0; i<tournament.getMatchesPlayed().size();i++){
                if((i % 2 == 0 && !scoreTxtFields.get(i+1).getText().isBlank()) || !scoreTxtFields.get(i).getText().isBlank()){
                    scoreTxtFields.get(i).setDisable(true);
                    scoreTxtFields.get(i+1).setDisable(true);
                }
            }
        } else {
            for(TextField t : scoreTxtFields){
                t.setDisable(true);
            }
            txtDisplayWinner.setVisible(true);
            btnAdvanceTournament.setDisable(true);
            txtDisplayWinner.setText("Winners: " + tournament.getTournamentWinner().getName());
        }
    }

    /**
     * The method being called when the btnAdvanceTournament is clicked
     * This method disables the textfields and calls the method registerResults when all the textFields are disabled
     */
    public void advanceTournament() {
        int numberOfMathcesThisRound = tournament.getMatches().size()*2 + getStartIndex();
        int counter = getStartIndex();
        for (int i = 0; i < scoreTxtFields.size(); i++) {
            if (scoreTxtFields.get(counter).getText().isBlank()){
                Alertbox.display("Error", "Some of the results are empty");
                break;
            }
            if (counter != numberOfMathcesThisRound && ((counter + 1) % 2 != 0) && scoreTxtFields.get(counter).getText().equals(scoreTxtFields.get(counter+1).getText())){
                Alertbox.display("Error", "Some of the results are equal");
                break;
            }
            if(invalidInput(scoreTxtFields.get(counter).getText())){
                Alertbox.display("Error", "Invalid non-letter input");
                break;
            }
            if (counter < numberOfMathcesThisRound)
                scoreTxtFields.get(counter).setDisable(true);

            counter++;
            if (counter == numberOfMathcesThisRound) {
                registerResults(getStartIndex());
                break;
            }
        }
        try {
            for (Team team : tournament.getTournament().getTeams()) {
                TournamentFileManagement.updateTeam(team);
            }
            TournamentFileManagement.writeTournamnetRunnerToFile(tournament);
        } catch (IOException e) {
            Alertbox.display("Error", "Something went wrong when storing the tournament");
        }

    }

    private boolean invalidInput(String s){
        char[] stringAsChars = s.toCharArray();
        for(int i=0; i<stringAsChars.length; i++) {
            if(!Character.isDigit(stringAsChars[i])) return true;
        }
        return false;
    }

    /**
     * The private method for serializing the teams from the tournament to the files
     * @return the list of teams that's getting serialized
     */
    private ArrayList<Team> getTeamsToStore() {
        var listToSerialize = new ArrayList<Team>();
        ArrayList<Team> teams = null;
        try {
            teams = FileManagement.readAllTeamsFromFiles();
        } catch (IOException e) {
            Alertbox.display("Error", "Something went wrong when loading teams");
        }
        var teamsFromTournamnet = tournament.getTournament().getTeams();
        for (Team teamInTournamnet : teamsFromTournamnet) {
            for (Team teamInTeamBank : teams) {
                if (teamInTournamnet.getName().equalsIgnoreCase(teamInTeamBank.getName())) {
                    if (!listToSerialize.contains(teamInTournamnet) && !listToSerialize.contains(teamInTeamBank))
                        listToSerialize.add(teamInTournamnet);
                }
            }
        }

        for (Team t : teams) {
            if (!listToSerialize.contains(t))
                listToSerialize.add(t);
        }
        return listToSerialize;
    }

    /**
     * The method that registers the results of the matches
     * The result of the match is placed in a fixed size array
     * Then the match get registered with the result
     *
     * The winner is displayed when the tournament is finished
     * @param startIndex is the indexing for getting the correct txtfields and labels
     */
    private void registerResults(int startIndex) {
        int[] results = new int[tournament.getMatches().size()*2];
        int counter = 0;
        for (int i = startIndex; i < tournament.getMatches().size()*2 + startIndex; i++) {
            results[counter++] = Integer.parseInt(scoreTxtFields.get(i).getText().trim());
        }
        int scoreCounter = 0;
        for (Match m : tournament.getMatches()) {
            tournament.registerResult(m, results[scoreCounter++], results[scoreCounter]);
            scoreCounter++;
        }
        if (tournament.isFinished()){
            txtDisplayWinner.setVisible(true);
            btnAdvanceTournament.setDisable(true);
            txtDisplayWinner.setText("Winners: " + tournament.getTournamentWinner().getName());
        }
        else
            fillInMatchesThisRound();
    }

    /**
     * This cleans up the bracket view for the user.
     * Makes it easier to read and view the tournament
     */
    private void cleanUpBracketView() {
        if(tournament.getTournament().numberOfTeams()>16){
            Alertbox.display("Error", "Too many teams in the tournament to display");
        } else if(tournament.getTournament().numberOfTeams()==8){
            roundOf16.setVisible(false);
            roundOf8.setLayoutX(0);
            roundOf4.setLayoutX(100+ roundOf8.getPrefWidth());
            roundOf2.setLayoutX(200+roundOf8.getPrefWidth()+roundOf4.getPrefWidth());
        } else if(tournament.getTournament().numberOfTeams()==4){
            roundOf16.setVisible(false);
            roundOf8.setVisible(false);
            roundOf4.setLayoutX(0);
            roundOf2.setLayoutX(100+roundOf4.getPrefWidth());
        } else if(tournament.getTournament().numberOfTeams()==2){
            roundOf16.setVisible(false);
            roundOf8.setVisible(false);
            roundOf4.setVisible(false);
            roundOf2.setLayoutX(0);
        }
    }

    /**
     * A method to fill the matches that have already been played
     */
    private void fillInMatchesPlayedThisRound() {
        int startIndex = getFinalStartIndex();
        int matchCounter;
        for(matchCounter=0; matchCounter<tournament.getMatchesPlayed().size(); matchCounter++){
            String team1 = tournament.getMatchesPlayed().get(matchCounter).getTeam1().getName();
            String team2 = tournament.getMatchesPlayed().get(matchCounter).getTeam2().getName();
            String[] result=tournament.getMatchesPlayed().get(matchCounter).getResult().split("-");
            String score1 = result[0].trim();
            String score2 = result[1].trim();
            teamLabels.get(startIndex+matchCounter*2).setText(team1);
            teamLabels.get(startIndex+matchCounter*2+1).setText(team2);
            scoreTxtFields.get(startIndex+matchCounter*2).setText(score1);
            scoreTxtFields.get(startIndex+matchCounter*2+1).setText(score2);
        }
    }

    /**
     * This method fills in the matches for the active round
     */
    private void fillInMatchesThisRound() {
        int startLabelIndex = getStartIndex();
        int matchCounter;
        if(tournament.getTournamentWinner()==null){
            for(matchCounter=0; matchCounter<tournament.getMatches().size(); matchCounter++){
                String team1 = tournament.getMatches().get(matchCounter).getTeam1().getName();
                String team2 = tournament.getMatches().get(matchCounter).getTeam2().getName();
                String score1 = "0";
                String score2 = "0";

                if(team1.equalsIgnoreCase("Bypass")){
                    score2="2";
                } else if(team2.equalsIgnoreCase("Bypass")){
                    score1="2";
                }

                teamLabels.get(startLabelIndex +matchCounter*2).setText(team1);
                teamLabels.get(startLabelIndex +matchCounter*2+1).setText(team2);
                scoreTxtFields.get(startLabelIndex +matchCounter*2).setText(score1);
                scoreTxtFields.get(startLabelIndex +matchCounter*2+1).setText(score2);
            }
        }
    }

    /**
     * This makes it so that the brackets which are not in use are not visible
     */
    private void disableVboxWhenInvisible() {
        roundOf16.disableProperty().bind(new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return !roundOf16.isVisible();
            }
        });
        roundOf8.disableProperty().bind(new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return !roundOf8.isVisible();
            }
        });
        roundOf4.disableProperty().bind(new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return !roundOf4.isVisible();
            }
        });
    }

    /**
     * The method for getting the indexation, so that the tournament is displayed correct on the correct labels and texfields
     * @return the starting index
     */
    private int getStartIndex() {
        int bracketSize = tournament.getTournament().getBracketSize();
        int counterRounds = tournament.getCounterRounds();
        if (bracketSize == 8){
            if (counterRounds == 0)
                return 0;
            else if (counterRounds == 1)
                return 16;
            else if (counterRounds == 2)
                return 24;
        }
        else if (bracketSize == 4){
            if (counterRounds == 0)
                return 16;
            else if (counterRounds == 1)
                return 24;
        }
        else if (bracketSize == 2){
            if (counterRounds == 0)
                return 24;
        }
        return 28;
    }

    /**
     * A method to return final index
     * @return the final index as an int
     */
    private int getFinalStartIndex() {
        int bracketSize = tournament.getTournament().getBracketSize();
        if (bracketSize == 8){
            return 0;
        }
        else if (bracketSize == 4){
            return 16;
        }
        else if (bracketSize == 2){
            return 24;
        }
        return 28;
    }
    /**
     * The method for switching to the TeamCreator page
     * @param event the event
     * @throws IOException when the method can't find the file
     */
    public void switchToTeamCreator(ActionEvent event) throws IOException {
        switchScene("/TeamCreatorScene.fxml", event);
        TournamentFileManagement.writeTournamnetRunnerToFile(tournament);
    }

    /**
     * The method for switching to the Tournament generator page
     * @param event the event
     * @throws IOException when the method can't find the file
     */
    public void switchToTournamentGenerator(ActionEvent event) throws IOException {
        switchScene("/TournamentCreatorScene.fxml", event);
        TournamentFileManagement.writeTournamnetRunnerToFile(tournament);
    }

    /**
     * The method for switching to the Front page
     * @param event the event
     * @throws IOException when the method can't find the file
     */
    public void switchToFrontPage(ActionEvent event) throws IOException{
        switchScene("/Homepage.fxml", event);
        TournamentFileManagement.writeTournamnetRunnerToFile(tournament);
    }

    /**
     * The private method for switching scene
     * @param path to the FXML file
     * @param event the event
     * @throws IOException when the method can't find the file
     */
    private void switchScene(String path, ActionEvent event)throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * The method for adding all the teamlabels in an arraylist
     */
    private void addTeamLabels(){
        teamLabels.add(team1);
        teamLabels.add(team2);
        teamLabels.add(team3);
        teamLabels.add(team4);
        teamLabels.add(team5);
        teamLabels.add(team6);
        teamLabels.add(team7);
        teamLabels.add(team8);
        teamLabels.add(team9);
        teamLabels.add(team10);
        teamLabels.add(team11);
        teamLabels.add(team12);
        teamLabels.add(team13);
        teamLabels.add(team14);
        teamLabels.add(team15);
        teamLabels.add(team16);
        teamLabels.add(team17);
        teamLabels.add(team18);
        teamLabels.add(team19);
        teamLabels.add(team20);
        teamLabels.add(team21);
        teamLabels.add(team22);
        teamLabels.add(team23);
        teamLabels.add(team24);
        teamLabels.add(team25);
        teamLabels.add(team26);
        teamLabels.add(team27);
        teamLabels.add(team28);
        teamLabels.add(team29);
        teamLabels.add(team30);
        teamLabels.forEach(s-> s.setMinWidth(110));

    }

    /**
     * The method for adding all the textFields in an arraylist
     */
    private void addResultsLabels(){
        scoreTxtFields.add(score1);
        scoreTxtFields.add(score2);
        scoreTxtFields.add(score3);
        scoreTxtFields.add(score4);
        scoreTxtFields.add(score5);
        scoreTxtFields.add(score6);
        scoreTxtFields.add(score7);
        scoreTxtFields.add(score8);
        scoreTxtFields.add(score9);
        scoreTxtFields.add(score10);
        scoreTxtFields.add(score11);
        scoreTxtFields.add(score12);
        scoreTxtFields.add(score13);
        scoreTxtFields.add(score14);
        scoreTxtFields.add(score15);
        scoreTxtFields.add(score16);
        scoreTxtFields.add(score17);
        scoreTxtFields.add(score18);
        scoreTxtFields.add(score19);
        scoreTxtFields.add(score20);
        scoreTxtFields.add(score21);
        scoreTxtFields.add(score22);
        scoreTxtFields.add(score23);
        scoreTxtFields.add(score24);
        scoreTxtFields.add(score25);
        scoreTxtFields.add(score26);
        scoreTxtFields.add(score27);
        scoreTxtFields.add(score28);
        scoreTxtFields.add(score29);
        scoreTxtFields.add(score30);
        scoreTxtFields.forEach(s-> s.setMinWidth(25));
    }
}
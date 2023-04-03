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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.edu.ntnu.idatt1002.techn.App.Alertbox;
import org.edu.ntnu.idatt1002.techn.Teams.Team;
import org.edu.ntnu.idatt1002.techn.Tournament.Runners.TournamentRunner;
import org.edu.ntnu.idatt1002.techn.TournamentFileManagement;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * This class will control the view only mode, which is viewed when you press "View tournament" on the homepage
 * /author Aleksander Olsvik
 * /version 0.9
 */

public class ViewOnlyModeController implements Initializable {

    @FXML
    public Label tournamentNameLabel;
    public Label winnerLabel;

    /**
     * the labels used to display the team names.
     */
    public Label team1, team2, team3, team4, team5, team6, team7, team8, team9, team10;
    public Label team11, team12, team13, team14, team15, team16, team17, team18, team19, team20;
    public Label team21, team22, team23, team24, team25, team26, team27, team28, team29, team30;

    /**
     * the labels used to display the scores of the match
     */
    public Label score1, score2, score3, score4, score5, score6, score7, score8, score9, score10;
    public Label score11, score12, score13, score14, score15, score16, score17, score18, score19, score20;
    public Label score21, score22, score23, score24, score25, score26, score27, score28, score29, score30;


    /**
     * the main VBOXes containing the matches
     */
    public VBox roundOf16;
    public VBox roundOf8;
    public VBox roundOf4;
    public VBox roundOf2;

    public ComboBox<String> teamSelector;
    public Label selectLabel;
    public ListView<String> teamViewer;
    public Button backToHomepage;


    private final ArrayList<Label> teamLabels=new ArrayList<>();
    private final ArrayList<Label> scoreLabels = new ArrayList<>();
    private TournamentRunner tournament;
    private ArrayList<String> displayNames=new ArrayList<>();


    /**
     * Initializes the tournament view.
     * Uses many private methods to keep the initialize method cleaner
     * Will fill in the information about the teams,and the scores of the teams that has played
     * Will also calculate / decide what brackets (VBOX) and Labels to show
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            tournament = TournamentFileManagement.readTournamentRunner();
        } catch (IOException e) {
            Alertbox.display("Error", "Something went wrong when loading the tournament");
        }
        fillInComboBox();
        addTeamLabels();
        addResultsLabels();
        setTextForWinnerAndTournamentName();
        disableVBOX();
        setVisibleBrackets();
        addMatchesToBrackets();
    }

    /**
     * A method to set the text, and the visibility of the tournament winner
     * Will not be shown if there is no winner to display, meaning the tournament is ongoing
     */
    private void setTextForWinnerAndTournamentName() {
        if(tournament.isFinished()){
            winnerLabel.setText("Tournament winner\n"+ tournament.getTournamentWinner().getName());
            winnerLabel.setVisible(true);
        }
        tournamentNameLabel.setText(tournament.getTournament().getName());
    }

    /**
     * Method to fill in the team names in the drop-down box
     * Will not fill in the Bypass teams, as these have no players, nor any information
     */
    private void fillInComboBox() {
        for(Team t: tournament.getTournament().getTeams()){
            if(!t.getName().equalsIgnoreCase("Bypass")){
                displayNames.add(t.getName());
            }
        }
        teamSelector.getItems().addAll(displayNames);
    }


    /**
     * Will only display a tournament of 16 teams or fewer.
     * Sets the visibility and location of the VBOXes, to make the brackets always stay towards the left
     * This will vary based on how many teams are in the tournament
     */
    private void setVisibleBrackets() {
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
     * Method to get the index for the labels used.
     * If a tournament has 8 teams, for example, the labels from 17 (index 16) and up will be used
     * @return - the index for the labels used
     */
    private int getStartLabelIndex() {
        int startLabelIndex=28;
        if(tournament.getTournament().getBracketSize()==4){
            startLabelIndex=16;
        } else if(tournament.getTournament().getBracketSize()==2){
            startLabelIndex=24;
        }
        return startLabelIndex;
    }

    /**
     * This method will fill in the matches that has been played already
     * If the tournament is ongoing, it will also fill in the coming round
     */
    private void addMatchesToBrackets() {
        int startLabelIndex = getStartLabelIndex();
        int matchCounter;
        int differenceIndex=tournament.getMatchesPlayed().size()*2;

        if(!tournament.isFinished()){
            for(matchCounter=0; matchCounter<tournament.getMatchesThisRound().size(); matchCounter++){
                String team1 = tournament.getMatches().get(matchCounter).getTeam1().getName();
                String team2 = tournament.getMatches().get(matchCounter).getTeam2().getName();
                String score1 = "0";
                String score2 = "0";

                if(team1.equalsIgnoreCase("Bypass")){
                    score2="2";
                } else if(team2.equalsIgnoreCase("Bypass")){
                    score1="2";
                }
                teamLabels.get(startLabelIndex +matchCounter*2+differenceIndex).setText(team1);
                teamLabels.get(startLabelIndex +matchCounter*2+1+differenceIndex).setText(team2);
                scoreLabels.get(startLabelIndex +matchCounter*2+differenceIndex).setText(score1);
                scoreLabels.get(startLabelIndex +matchCounter*2+1+differenceIndex).setText(score2);
            }
        }

        if(tournament.getMatchesPlayed().size()!=0){
            for(matchCounter=0; matchCounter<tournament.getMatchesPlayed().size(); matchCounter++){
                String team1 = tournament.getMatchesPlayed().get(matchCounter).getTeam1().getName();
                String team2 = tournament.getMatchesPlayed().get(matchCounter).getTeam2().getName();
                String[] result=tournament.getMatchesPlayed().get(matchCounter).getResult().split("-");
                String score1 = result[0].trim();
                String score2 = result[1].trim();
                teamLabels.get(startLabelIndex +matchCounter*2).setText(team1);
                teamLabels.get(startLabelIndex +matchCounter*2+1).setText(team2);
                scoreLabels.get(startLabelIndex +matchCounter*2).setText(score1);
                scoreLabels.get(startLabelIndex +matchCounter*2+1).setText(score2);
                scoreLabels.get(startLabelIndex +matchCounter*2).setTextAlignment(TextAlignment.CENTER);
                scoreLabels.get(startLabelIndex +matchCounter*2+1).setTextAlignment(TextAlignment.CENTER);
            }
        }
    }

    /**
     * Method to bind the disability of the brackets (VBOX) to their visibility
     */
    private void disableVBOX() {
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
     * Adds all the labels to display teams into an arraylist, to make them manageable
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
     * Adds all the labels to display teams into an arraylist, to make them manageable
     */
    private void addResultsLabels(){
        scoreLabels.add(score1);
        scoreLabels.add(score2);
        scoreLabels.add(score3);
        scoreLabels.add(score4);
        scoreLabels.add(score5);
        scoreLabels.add(score6);
        scoreLabels.add(score7);
        scoreLabels.add(score8);
        scoreLabels.add(score9);
        scoreLabels.add(score10);
        scoreLabels.add(score11);
        scoreLabels.add(score12);
        scoreLabels.add(score13);
        scoreLabels.add(score14);
        scoreLabels.add(score15);
        scoreLabels.add(score16);
        scoreLabels.add(score17);
        scoreLabels.add(score18);
        scoreLabels.add(score19);
        scoreLabels.add(score20);
        scoreLabels.add(score21);
        scoreLabels.add(score22);
        scoreLabels.add(score23);
        scoreLabels.add(score24);
        scoreLabels.add(score25);
        scoreLabels.add(score26);
        scoreLabels.add(score27);
        scoreLabels.add(score28);
        scoreLabels.add(score29);
        scoreLabels.add(score30);
        scoreLabels.forEach(s-> s.setMinWidth(25));
    }


    /**
     * Method to switch the scene to homepage if you click the "Back to homepage" button
     * @param event - the click
     */
    public void backToHomepage(ActionEvent event){
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load((getClass().getResource("/Homepage.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Method to switch the scene to team info if you click the "Get more info about the teams" button
     * @param event - the click
     */
    public void goToTeamInfo(ActionEvent event){
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load((getClass().getResource("/TeamInfoForViewOnly.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Method to display the IGN (in-game name) of the players of a selected team.
     * Will display the team selected by the combo box
     */
    public void displayTeam(){
        teamViewer.getItems().clear();
        String teamName = teamSelector.getValue();
        Team team=null;
        for(Team t: tournament.getTournament().getTeams()){
            if(teamName.equalsIgnoreCase(t.getName())){
                team=t;
            }
        }
        ArrayList<String> ignList=new ArrayList<>();
        team.getPlayers().forEach(s->ignList.add(s.getIGN()));
        selectLabel.setText(team.getName());
        teamViewer.getItems().addAll(ignList);
    }
}
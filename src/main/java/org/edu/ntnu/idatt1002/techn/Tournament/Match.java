package org.edu.ntnu.idatt1002.techn.Tournament;

import org.edu.ntnu.idatt1002.techn.Teams.Team;

import java.io.Serializable;

/**
 * The class that represents a match between two teams
 * /author Aleksander Olsvik
 * /version 0.5
 */
public class Match {
    private final Team team1;
    private final Team team2;
    private Team winner;
    private Boolean registered;
    private String result;

    /**
     * The constructor for the match-class
     *
     * @param team1 Is one of the two teams in a match
     * @param team2 Is the other team competing
     */
    public Match(Team team1, Team team2) throws IllegalArgumentException{
        if (team1 == null || team2 == null)
            throw new IllegalArgumentException("The teams provided are null");
        this.team1 = team1;
        this.team2 = team2;
        this.registered =false;
        this.result="0-0";
    }

    /**
     * in case of a walkover, where the match consists of only one team
     * the participating team will automatically win 3-0 and proceed
     *
     * @param team is the team
     */
    public Match(Team team) throws IllegalArgumentException{
        if (team == null)
            throw new IllegalArgumentException("The team provided is null");
        this.team1 = team;
        this.team2 = new Team("Bypass");
        this.registered = false;
        this.registerMatchResult(2,0);
    }

    /**
     * The method for registering a result from a match
     * Number of wins in the match determines witch team that advances and witch that is eliminated
     *
     * @param winsTeam1 Team 1's number of wins in the match
     * @param winsTeam2 Team 2's number of wins in the match
     * The winning team
     */
    public void registerMatchResult(int winsTeam1, int winsTeam2) {
        if(!registered){
            Team winner;
            if (winsTeam1 > winsTeam2) {
                team1.increaseWins();
                team2.increaseLosses();
                winner = team1;
            }
            else{
                team2.increaseWins();
                team1.increaseLosses();
                winner = team2;
            }
            this.winner=winner;
            register();
            setResult(winsTeam1, winsTeam2);
        }
    }

    /**
     * The method for retrieving team 1 of the match
     *
     * @return team 1
     */
    public Team getTeam1() {
        return team1;
    }

    /**
     * The method for retrieving team 2 of the match
     *
     * @return team 2
     */
    public Team getTeam2() {
        return team2;
    }

    /**
     * the winner of the match, if this has been set
     * @return the winning team
     */
    public Team getWinner(){
        return winner;
    }

    /**
     * method to set if the match has been registered
     */
    private void register() {
        this.registered = true;
    }

    /**
     * method to get if the match has been registered
     * @return true / false
     */
    public Boolean getRegistered() {
        return registered;
    }

    /**
     * Method to get the result of the match
     * @return a string, the result
     */
    public String getResult(){
        return result;
    }

    /**
     * The method for setting the winning team of the match
     * @param winner is the winning team of the match
     */
    public void setWinner(Team winner) {
        this.winner = winner;
    }

    /**
     * The method for setting the result of the match
     * @param result is the result set
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * set the result of a match
     * @param winsTeam1 - wins team 1
     * @param winsTeam2 - wins team 2
     */
    private void setResult(int winsTeam1, int winsTeam2) {
        this.result = winsTeam1 + "-" + winsTeam2;
    }

    /**
     * Override toString method
     *
     * @return the match class as a String
     */
    @Override
    public String toString() {
        return team1.getName() + " vs. " + team2.getName();
    }
}

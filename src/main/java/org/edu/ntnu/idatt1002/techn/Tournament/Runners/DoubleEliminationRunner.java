package org.edu.ntnu.idatt1002.techn.Tournament.Runners;

import org.edu.ntnu.idatt1002.techn.Teams.Team;
import org.edu.ntnu.idatt1002.techn.Tournament.Match;
import org.edu.ntnu.idatt1002.techn.Tournament.Tournament;

import java.util.ArrayList;

/**
 * The class for running a double elimination tournament
 * /author Jarand Romestrand
 * /version 0.1
 */
public class DoubleEliminationRunner extends TournamentRunner{
    /**
     * The method for creating a tournament
     */
    @Override
    public void createTournament() {
        // TODO: 29.04.2022 make code
    }

    /**
     * The method for getting the matches in the tournament
     *
     * @return a list of matches
     */
    @Override
    public ArrayList<Match> getMatches() {
        // TODO: 29.04.2022 make code
        return null;
    }

    /**
     * The method for registering a result for a match
     *
     * @param match   The match being registered
     * @param result1 the score for team1
     * @param result2 the score for team 2
     */
    @Override
    public void registerResult(Match match, int result1, int result2) {
        // TODO: 29.04.2022 make code
    }

    /**
     * The method for getting the tournamnet
     *
     * @return the tournament
     */
    @Override
    public Tournament getTournament() {
        // TODO: 29.04.2022 make code
        return null;
    }

    /**
     * The method for getting the tournamnet winner
     *
     * @return the winning team
     */
    @Override
    public Team getTournamentWinner() {
        // TODO: 29.04.2022 make code
        return null;
    }

    /**
     * The method for getting the round the tournament is on
     *
     * @return the number round that the runner is on
     */
    @Override
    public int getCounterRounds() {
        // TODO: 29.04.2022 make code
        return 0;
    }

    /**
     * The method for getting the matches already played
     *
     * @return the list of registered matches
     */
    @Override
    public ArrayList<Match> getMatchesPlayed() {
        // TODO: 29.04.2022 make code
        return null;
    }

    /**
     * The method for getting the matches for this round
     *
     * @return a list of matches
     */
    @Override
    public ArrayList<Match> getMatchesThisRound() {
        // TODO: 29.04.2022 make code
        return null;
    }

    /**
     * The method for getting the list of winners this round
     *
     * @return list of teams
     */
    @Override
    public ArrayList<Team> getWinnersThisRound() {
        return null;
    }

    /**
     * Sets the tournament as finished
     */
    @Override
    public void setFinished() {
        // TODO: 29.04.2022 make code
    }

    /**
     * The method for checking if a tournament is finnished or not
     *
     * @return true if the tournament is finnished
     */
    @Override
    public boolean isFinished() {
        return false;
    }

    /**
     * The method for getting the match that the runner is on
     *
     * @return the counter for matches registered this round
     */
    @Override
    public int getCounterMatchesRegistered() {
        // TODO: 29.04.2022 make code
        return 0;
    }

    /**
     * The method for setting the matches played
     *
     * @param matchesPlayed being sat
     */
    @Override
    public void setMatchesPlayed(ArrayList<Match> matchesPlayed) {

    }

    /**
     * The method for setting the winners this round
     *
     * @param winnersThisRound being sat
     */
    @Override
    public void setWinnersThisRound(ArrayList<Team> winnersThisRound) {

    }

    /**
     * The method for setting the matches this round
     *
     * @param matchesThisRound being sat
     */
    @Override
    public void setMatchesThisRound(ArrayList<Match> matchesThisRound) {

    }

    /**
     * The method for setting the counter rounds
     *
     * @param counterRounds being sat
     */
    @Override
    public void setCounterRounds(int counterRounds) {

    }

    /**
     * The method for setting the counter matches registered
     *
     * @param counterMatchesRegistered being sat
     */
    @Override
    public void setCounterMatchesRegistered(int counterMatchesRegistered) {

    }
}

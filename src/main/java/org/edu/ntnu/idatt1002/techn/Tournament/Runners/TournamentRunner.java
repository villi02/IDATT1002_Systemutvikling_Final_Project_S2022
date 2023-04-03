package org.edu.ntnu.idatt1002.techn.Tournament.Runners;

import org.edu.ntnu.idatt1002.techn.Teams.Team;
import org.edu.ntnu.idatt1002.techn.Tournament.Match;
import org.edu.ntnu.idatt1002.techn.Tournament.Tournament;

import java.util.ArrayList;

/**
 * The class for running a tournament
 * /author Jarand Romestrand
 * /version 0.2
 */
public abstract class TournamentRunner {

    /**
     * The method for creating a tournament
     */
    public abstract void createTournament();

    /**
     * The method for registering a result for a match
     *
     * @param match   The match being registered
     * @param result1 the score for team1
     * @param result2 the score for team 2
     */
    public abstract void registerResult(Match match, int result1, int result2);

    /**
     * The method for getting the matches in the tournament
     *
     * @return a list of matches
     */
    public abstract ArrayList<Match> getMatches();

    /**
     * The method for getting the tournamnet
     *
     * @return the tournament
     */
    public abstract Tournament getTournament();

    /**
     * The method for getting the tournamnet winner
     *
     * @return the winning team
     */
    public abstract Team getTournamentWinner();

    /**
     * The method for getting the round the tournament is on
     *
     * @return the number round that the runner is on
     */
    public abstract int getCounterRounds();

    /**
     * The method for getting the matches already played
     *
     * @return the list of registered matches
     */
    public abstract ArrayList<Match> getMatchesPlayed();

    /**
     * The method for getting the matches for this round
     *
     * @return a list of matches
     */
    public abstract ArrayList<Match> getMatchesThisRound();

    /**
     * The method for getting the list of winners this round
     *
     * @return list of teams
     */
    public abstract ArrayList<Team> getWinnersThisRound();

    /**
     * Sets the tournament as finished
     */
    public abstract void setFinished();

    /**
     * The method for checking if a tournament is finnished or not
     *
     * @return true if the tournament is finnished
     */
    public abstract boolean isFinished();

    /**
     * The method for getting the match that the runner is on
     *
     * @return the counter for matches registered this round
     */
    public abstract int getCounterMatchesRegistered();

    /**
     * The method for setting the matches played
     *
     * @param matchesPlayed being sat
     */
    public abstract void setMatchesPlayed(ArrayList<Match> matchesPlayed);

    /**
     * The method for setting the winners this round
     *
     * @param winnersThisRound being sat
     */
    public abstract void setWinnersThisRound(ArrayList<Team> winnersThisRound);

    /**
     * The method for setting the matches this round
     *
     * @param matchesThisRound being sat
     */
    public abstract void setMatchesThisRound(ArrayList<Match> matchesThisRound);

    /**
     * The method for setting the counter rounds
     *
     * @param counterRounds being sat
     */
    public abstract void setCounterRounds(int counterRounds);

    /**
     * The method for setting the counter matches registered
     *
     * @param counterMatchesRegistered being sat
     */
    public abstract void setCounterMatchesRegistered(int counterMatchesRegistered);
}

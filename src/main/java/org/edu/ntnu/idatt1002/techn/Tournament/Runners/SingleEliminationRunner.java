package org.edu.ntnu.idatt1002.techn.Tournament.Runners;

import org.edu.ntnu.idatt1002.techn.App.Alertbox;
import org.edu.ntnu.idatt1002.techn.FileManagement;
import org.edu.ntnu.idatt1002.techn.Teams.Team;
import org.edu.ntnu.idatt1002.techn.Tournament.Match;
import org.edu.ntnu.idatt1002.techn.Tournament.Tournament;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The class for running a single elimination tournament
 * /author Jarand Romestrand
 * /version 0.3
 */
public class SingleEliminationRunner extends TournamentRunner {

    /**
     * The tournament being ran
     */
    private final Tournament tournament;

    /**
     * The lists for keeping control of the teams and matches for advancing and running a tournament
     */
    private ArrayList<Match> matchesPlayed;
    private ArrayList<Team> winnersThisRound;
    private ArrayList<Match> matchesThisRound;
    private boolean isFinished;

    /**
     * The counters for keeping track of the state of the tournament
     */
    private int counterRounds;
    private int counterMatchesRegistered;

    /**
     * The constructor for a single elimination tournament runner
     *
     * @param tournament the tournament being ran
     */
    public SingleEliminationRunner(Tournament tournament) {
        this.tournament = tournament;
        this.winnersThisRound = new ArrayList<>();
        this.matchesThisRound = new ArrayList<>();
        this.matchesPlayed = new ArrayList<>();

        this.isFinished = false;
        this.counterRounds = 0;
        this.counterMatchesRegistered = 0;
    }

    /**
     * The method for creating a tournament
     */
    @Override
    public void createTournament() {
        setTournamentBracketSize();
        setNumberOfRounds();
        fillInByPasses();
        tournament.shuffleTeams();
        for (int i = 0; i < tournament.getBracketSize(); i++) {
            matchesThisRound.add(new Match(tournament.getTeams().get(i*2), tournament.getTeams().get(i*2+1)));
        }
    }

    /**
     * The method for filling in bypasses for the first round of the tournament
     */
    private void fillInByPasses() {
        int numberOfBypasses = tournament.getBracketSize()*2 - tournament.numberOfTeams();
        try{
            for(int i=0; i<numberOfBypasses;i++){
                tournament.getTeams().add(FileManagement.readTeamFromFile("Bypass"));
            }
        } catch (Exception e){
            Alertbox.display("Error", "Could not fill in the teams");
        }
    }

    /**
     * The method for setting the bracket size of the tournament
     */
    private void setTournamentBracketSize(){
        int numberOfMatchesRound1=0;
        for(int i=0; i<8; i++){
            if((tournament.numberOfTeams() - (Math.pow(2,i)))<=0){
                numberOfMatchesRound1= (int) Math.pow(2, (i-1));
                break;
            }
        }
        tournament.setBracketSize(numberOfMatchesRound1);
    }

    /**
     * The method for getting the matches in the tournament
     *
     * @return a list of matches
     */
    @Override
    public ArrayList<Match> getMatches() {
        return matchesThisRound;
    }

    /**
     * The method for registering a result for a match
     *
     * @param match The match being registered
     * @param result1 the score for team1
     * @param result2 the score for team 2
     */
    @Override
    public void registerResult(Match match, int result1, int result2) {
        if(match.getTeam1().getName().equalsIgnoreCase("Bypass"))
            match.registerMatchResult(0,2);
        else if (match.getTeam2().getName().equalsIgnoreCase("Bypass"))
            match.registerMatchResult(2,0);
        else
            match.registerMatchResult(result1,result2);

        winnersThisRound.add(match.getWinner());
        counterMatchesRegistered++;
        matchesPlayed.add(match);

        if (counterMatchesRegistered == matchesThisRound.size())
            matchesThisRound = getMatchesNextRound();
    }

    /**
     * The method for getting the matches for the next round in the tournament
     * this method advances the tournament
     *
     * @return the new list og new matches for this round
     */
    private ArrayList<Match> getMatchesNextRound() {
        if(winnersThisRound.isEmpty())
            return new ArrayList<>();

        counterRounds++;
        if (counterRounds == tournament.getNumberOfRounds()) {
            tournament.setTournamentWinner(winnersThisRound.get(0));
            setFinished();
            return new ArrayList<>();
        }
        ArrayList<Match> list = new ArrayList<>();
        for (int i=0; i< winnersThisRound.size(); i+=2)
            list.add(new Match(winnersThisRound.get(i), winnersThisRound.get(i+1)));

        counterMatchesRegistered = 0;
        winnersThisRound.clear();

        return list;
    }

    /**
     * The method for setting the number of rounds in the tournament
     */
    private void setNumberOfRounds(){
        int numberOfRounds=0;
        int bracketSize= tournament.getBracketSize();
        while(bracketSize>=1){
            numberOfRounds++;
            bracketSize=bracketSize/2;
        }
        tournament.setNumberOfRounds(numberOfRounds);
    }

    /**
     * The method for getting the match that the runner is on
     *
     * @return the counter for matches registered this round
     */
    @Override
    public int getCounterMatchesRegistered() {
        return counterMatchesRegistered;
    }

    /**
     * The method for getting the matches for this round
     *
     * @return a list of matches
     */
    @Override
    public ArrayList<Match> getMatchesThisRound() {
        return matchesThisRound;
    }

    /**
     * The method for getting the matches played
     *
     * @return the matches played
     */
    @Override
    public ArrayList<Match> getMatchesPlayed() {
        return matchesPlayed;
    }

    /**
     * The method for getting the round the tournament is on
     *
     * @return the number round that the runner is on
     */
    @Override
    public int getCounterRounds() {
        return counterRounds;
    }

    /**
     * The method for getting the tournamnet
     *
     * @return the tournament
     */
    @Override
    public Tournament getTournament() {
        return this.tournament;
    }

    /**
     * The method for getting the tournamnet winner
     *
     * @return the winning team
     */
    @Override
    public Team getTournamentWinner() {
        return tournament.getTournamentWinner();
    }

    /**
     * The method for getting the list of winners this round
     *
     * @return list of teams
     */
    @Override
    public ArrayList<Team> getWinnersThisRound() {
        return winnersThisRound;
    }

    /**
     * The method for checking if a tournament is finnished or not
     * @return true if the tournament is finnished
     */
    @Override
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Sets the tournament as finished
     */
    @Override
    public void setFinished() {
        isFinished = true;
    }

    /**
     * The method for setting the matches played
     *
     * @param matchesPlayed being sat
     */
    @Override
    public void setMatchesPlayed(ArrayList<Match> matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    /**
     * The method for setting the winners this round
     *
     * @param winnersThisRound being sat
     */
    @Override
    public void setWinnersThisRound(ArrayList<Team> winnersThisRound) {
        this.winnersThisRound = winnersThisRound;
    }

    /**
     * The method for setting the matches this round
     *
     * @param matchesThisRound being sat
     */
    @Override
    public void setMatchesThisRound(ArrayList<Match> matchesThisRound) {
        this.matchesThisRound = matchesThisRound;
    }

    /**
     * The method for setting the counter rounds
     *
     * @param counterRounds being sat
     */
    @Override
    public void setCounterRounds(int counterRounds) {
        this.counterRounds = counterRounds;
    }

    /**
     * The method for setting the counter matches registered
     *
     * @param counterMatchesRegistered being sat
     */
    @Override
    public void setCounterMatchesRegistered(int counterMatchesRegistered) {
        this.counterMatchesRegistered = counterMatchesRegistered;
    }

    /**
     * an overrided equals method
     *
     * @param o being compared
     * @return true or false depending on if the object is equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleEliminationRunner that = (SingleEliminationRunner) o;
        return Objects.equals(tournament, that.tournament);
    }

    /**
     * An overrided toString method
     *
     * @return The object as a String
     */
    @Override
    public String toString() {
        return "SingleEliminationRunner{" +
                "tournament=" + tournament +
                '}';
    }
}

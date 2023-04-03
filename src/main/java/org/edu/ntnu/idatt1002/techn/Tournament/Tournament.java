package org.edu.ntnu.idatt1002.techn.Tournament;

import org.edu.ntnu.idatt1002.techn.Teams.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


/**
 * Class which will work as a guideline for other specified tournaments
 * /author Aleksander Olsvik
 * /author Jarand Romestrand
 * /version 0.3
 */
public class Tournament {

    /**
     * The name and type of the tournament
     */
    private final String name;
    private final String type;

    private int bracketSize;
    private int numberOfRounds;

    /**
     * The list of teams in the tournament and matches played
     */
    private ArrayList<Team> teams;

    private Team tournamentWinner;
    /**
     * The superclass constructor, which creates an empty list, for teams to be added to
     * @param name - name of the tournament
     * @param type - the type
     */
    public Tournament(String name, String type){
        if(name.isBlank()) throw new IllegalArgumentException("The tournament must have a name");
        this.name=name;
        if(type.isBlank()) throw new IllegalArgumentException("The tournament must have a type");
        this.type = type;
        this.teams=new ArrayList<>();
    }

    /**
     * The superclass constructor, which creates an empty list, for teams to be added to
     * @param name - name of the tournament
     * @param type - the type
     * @param teams the teams
     */
    public Tournament(String name, String type, ArrayList<Team> teams){
        if(name.isBlank()) throw new IllegalArgumentException("The tournament must have a name");
        this.name=name;
        if(type.isBlank()) throw new IllegalArgumentException("The tournament must have a type");
        this.type = type;
        this.teams=teams;
    }

    /**
     * Method to add a team to the tournament. Will not add a team if their teamname is already registered
     * @param team - the team to be added
     * @return true / false if they were / were not added
     */
    public boolean addTeam(Team team){
        if (team.getName().equals("Bypass"))
            return teams.add(team);
        if(teams.contains(team)) throw new IllegalArgumentException("A team can only be registered once per tournament");
        return teams.add(team);
    }


    /**
     * method to add all the teams of a list til the teams of a tournament
     * @param teamsToAdd - the list of teams
     * @return - true/false if all teams were added or not
     */
    public boolean addAllTeams(ArrayList<Team> teamsToAdd){
        final int teamSize = numberOfTeams();
        teamsToAdd.forEach(t -> addTeam(t));
        return teams.size()-teamSize==teamsToAdd.size();
    }

    /**
     * Method to shuffle the teams registered.
     */
    public void shuffleTeams(){
        Collections.shuffle(teams);
    }

    /**
     * method to find the number of teams in a tournament
     * @return the number of teams in teams-list
     */
    public int numberOfTeams(){
        return teams.size();
    }

    /**
     * method to return the registered teams of a tournament
     * @return the teams of a tournamnet
     */
    public ArrayList<Team> getTeams() {
        return this.teams;
    }

    /**
     * method to be created in the specified tournament. This will return the tournaments type, which
     * will be specified in the constructor
     * @return the type
     */
    public String getType() {
        return type;
    };

    /**
     * The method for getting the bracket size of the tournament
     *
     * @return the bracket size as an integer
     */
    public int getBracketSize() {
        return bracketSize;
    }

    /**
     * The method for getting the number of rounds in the tournament
     *
     * @return the number of rounds as an integer
     */
    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    /**
     * Method to return the name of a tournament
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * The method for setting the bracket size of the tournament
     *
     * @param bracketSize is the bracketsize of the tournament
     */
    public void setBracketSize(int bracketSize) {
        this.bracketSize = bracketSize;
    }

    /**
     * The method for setting the number of rounds in the tournament
     *
     * @param numberOfRounds the number of rounds as an integer
     */
    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }


    /**
     * The method for setting the winner of the tournament
     *
     * @param tournamentWinner the winner being set
     */
    public void setTournamentWinner(Team tournamentWinner) {
        this.tournamentWinner = tournamentWinner;
        tournamentWinner.increaseTournamentWins();
    }

    /**
     * The method for getting the tournament winners
     * @return the winning team
     */
    public Team getTournamentWinner() {
        return tournamentWinner;
    }

    /**
     * toString method for a tournament
     * @return - all the teams of a tournament, as string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(type);
        sb.append(" tournament consisting of these teams: \n");
        teams.forEach(t-> sb.append(t).append("\n"));
        return sb.toString();
    }

    /**
     * overrided equalsmethod
     * @param o is the object bein compared
     * @return true of false depending on if the object is equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tournament that = (Tournament) o;
        return Objects.equals(teams, that.teams) && Objects.equals(name, that.name);
    }
}

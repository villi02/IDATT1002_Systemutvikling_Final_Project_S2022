package org.edu.ntnu.idatt1002.techn.Teams;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * /Author Jonatan Andre Vevang
 * /Version v0.5
 * Class representing a Team consisting of Players.
 */
public class Team {

    private ArrayList<Player> players; // Storing players in teams
    private int wins; // Total wins
    private int losses; // Total losses
    private String name; // Name of team
    private String nationality; // The teams nationality
    private int tournamentWins;

    /**
     * Constructor ONLY used for bypass teams
     * @param name team name
     * @throws IllegalArgumentException if name is blank
     */
    public Team(String name) throws IllegalArgumentException {
        this.players = new ArrayList<>();
        this.wins = 0;
        this.losses = 0;
        this.tournamentWins=0;
        if (name == null) {
            throw new IllegalArgumentException("'Name' can't be left blank");
        } else {
            setName(name);
        }
    }

    /**
     * Constructor for team
     * @param name
     * @param nationality
     * @throws IllegalArgumentException if name is null
     */
    public Team(String name,String nationality) throws IllegalArgumentException {
        this.players = new ArrayList<>();
        this.wins = 0;
        this.losses = 0;
        this.tournamentWins=0;
        if (name == null) {
            throw new IllegalArgumentException("'Name' can't be left blank");
        } else {
            setName(name);
        }
        setNationality(nationality);
    }

    /**
     * Constructor for team
     * @param players
     * @param name
     * @param nationality
     */
    public Team(ArrayList<Player> players, String name,String nationality){
        this.players = players;
        this.wins = 0;
        this.losses = 0;
        this.tournamentWins=0;
        this.name = name;
        setNationality(nationality);
    }

    /**
     * Get total wins for the team
     * @return total wins for the team
     */
    public int getWins() {
        return wins;
    }

    /**
     * Gets total players in a team
     * @return total players in the team
     */
    public int getSize() {
        return players.size();
    }
    /**
     * Increases number of wins by 1
     */
    public void increaseWins() {
        this.wins +=1;
    }

    /**
     * set the number of wins
     * @param wins the wins
     */
    public void setWins(int wins) {
        this.wins = wins;
    }

    /**
     * Increases the tournament wins by 1
     */
    public void increaseTournamentWins() {
        this.tournamentWins+=1;
    }

    /**
     * get tournament wins
     * @return the tournament wins
     */
    public int getTournamentWins() {
        return tournamentWins;
    }

    /**
     * set the tournament wins of a team
     * @param tournamentWins the wins
     */
    public void setTournamentWins(int tournamentWins) {
        this.tournamentWins = tournamentWins;
    }

    /**
     * Get total losses for the team
     * @return total losses for the team
     */
    public int getLosses() {
        return losses;
    }

    /**
     * set the losses
     * @param losses - the losses
     */
    public void setLosses(int losses) {
        this.losses = losses;
    }

    /**
     * Increases the number of losses by 1
     */
    public void increaseLosses() {
        this.losses+=1;
    }

    /**
     * Get the name for the team
     * @return the name for the team
     */
    public String getName() {
        return name;
    }

    /**
     * Set new name for the team
     * @param name new name for the team
     */
    public void setName(String name) {
        if(name.isBlank()) throw new IllegalArgumentException("The team must have a name");
        this.name = name;
    }

    /**
     * Get the nationality of a team
     * @return the nationality of a team
     */
    public String getNationality(){
        return nationality;
    }

    /**
     * Set nationality of team.
     * @param nationality teams home country
     * @throws IllegalArgumentException if @param nationality is blank
     */
    public void setNationality(String nationality) throws IllegalArgumentException{
        if(nationality.isBlank()) throw new IllegalArgumentException("The team must have a nationality");
        this.nationality = nationality;
    }

    /**
     * Method to add players to a team. If @param player already exists in team, method return false.
     * If not, add that player to this.team.
     * @param player the player to be added
     * @return return boolean
     */
    public boolean addPlayer(Player player){
        if (players.contains(player))
            return false;
        return players.add(player);
    }

    /**
     * Method for adding a list of players
     * @param listOfPlayers is the list of players being added
     */
    public void addAllPlayers(List<Player> listOfPlayers) {
        listOfPlayers.forEach(this::addPlayer);
    }

    /**
     * Method to remove players from team
     * @param player the player to be removed
     * @return return boolean
     */
    public boolean removePlayer(Player player){
        return players.remove(player);
    }
    
    /**
     * Method to get all players
     * @return all players registered
     */
    public ArrayList<Player> getPlayers(){
        return players;
    }

    /**
     * equials method
     * @param o - to be compared
     * @return - tru/false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return getName().equals(team.getName());
    }

    /**
     * hash
     * @return - the object hashed
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    /**
     * toString method
     * @return relevant information about the team name, total wins and total losses
     */
    @Override
    public String toString() {
        return "Teamname: " + name + " Total wins: " + wins + " Total losses: " + losses;
    }
}

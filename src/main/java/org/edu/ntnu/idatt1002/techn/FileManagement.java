package org.edu.ntnu.idatt1002.techn;

import org.edu.ntnu.idatt1002.techn.Teams.Player;
import org.edu.ntnu.idatt1002.techn.Teams.Team;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * class to write the teams to file
 * /version 0.2
 * /author Aleksander Olsvik
 */
public class FileManagement {
    private static File file;
    private static final String FILE_PATH_TEAMS = "Files/Teams/";
    private static final String FILE_ENDING = ".txt";
    private static final String FILE_PATH_FILES = "Files/";


    /**
     * method to write a new team to its own file
     * @param team - the team
     * @throws IOException - if the writing does not work
     */
    public static void writeTeamToFile(Team team) throws IOException{
        String fileName = team.getName().replaceAll("\\s", "");
        file = new File(FILE_PATH_TEAMS+fileName+FILE_ENDING);
        if(file.exists()){
            try(FileWriter fw = new FileWriter(file)){
                StringBuilder sb = getTeamInfo(team);
                fw.write(sb.toString());
                updateTeamNamesToFile(team);
            } catch (Exception e){
                throw new IOException("Could not write this team to a file");
            }
        }else{
            StringBuilder sb = getTeamInfo(team);
            try(FileWriter fw = new FileWriter(file)){
                fw.write(sb.toString());
                updateTeamNamesToFile(team);
            } catch (Exception e){
                throw new IOException("Could not write this team to a file");
            }
        }
    }

    private static StringBuilder getTeamInfo(Team team) {
        StringBuilder sb = new StringBuilder(team.getName()).append("\n");
        sb.append(team.getWins()).append("\n");
        sb.append(team.getLosses()).append("\n");
        sb.append(team.getTournamentWins()).append("\n");
        sb.append(team.getNationality()).append("\n");

        for(Player player : team.getPlayers()){
            sb.append(getPlayerInfo(player)).append("\n");
        }
        return sb;
    }

    /**
     * method to read all the teams registered in the files
     * @return - an arraylist with all the armies
     * @throws IOException - if the writing fails
     */
    public static ArrayList<Team> readAllTeamsFromFiles() throws IOException {
        ArrayList<Team> teams = new ArrayList<>();
        ArrayList<String> teamNames = readTeamNamesFromFile();

        if(teamNames.isEmpty()){
            return null;
        }

        Team teamToAdd;
        for(String s : teamNames){
            teamToAdd=readTeamFromFile(s);
            teams.add(teamToAdd);
        }
        return teams;
    }

    /**
     * method to write a list of teams to Files folder
     * @param teams - the list of all the teams
     * @throws IOException - if the writing fails
     */
    public static void writeAllTeamsToFile(ArrayList<Team> teams) throws IOException {
        for(Team team : teams){
            FileManagement.writeTeamToFile(team);
        }
    }

    /**
     * method to remove a team from the files stored
     * @param team - the team to remove
     * @throws IOException - if the removal fails
     */
    public static void removeTeam(Team team) throws IOException{
        file = new File(FILE_PATH_FILES+"FileNames"+FILE_ENDING);
        String teamFileNameToDelete = team.getName().replaceAll("\\s", "");
        File fileToDelete = new File(FILE_PATH_TEAMS + teamFileNameToDelete + FILE_ENDING);
        ArrayList<String> teamNames = readTeamNamesFromFile();
        if(teamNames.contains(teamFileNameToDelete)){
            if(fileToDelete.delete()){
                removeTeamNameFromFile(teamFileNameToDelete);
            }
        }
    }

    /**
     * method to read a single team from a file
     * @param teamName the team name
     * @return - the Team
     * @throws IOException - if something fail with the reading
     */
    public static Team readTeamFromFile(String teamName) throws IOException{
        file = new File(FILE_PATH_TEAMS+teamName+FILE_ENDING);
        Team team;
        String finalTeamName;
        int wins;
        int losses;
        int tournamentWins;
        String teamNationality;
        Player player;
        String[] playerInfo;
        int age;
        String firstName;
        String lastName;
        String ign;
        String playerNationality;
        String role;
        if(!teamName.equals("Bypass")){
            try(Scanner sc = new Scanner(file)){
                finalTeamName = sc.nextLine();
                wins=Integer.parseInt(sc.nextLine());
                losses=Integer.parseInt(sc.nextLine());
                tournamentWins=Integer.parseInt(sc.nextLine());
                teamNationality=sc.nextLine();

                if(finalTeamName.isBlank() || teamNationality.isBlank()){
                    return null;
                }

                team=new Team(finalTeamName,teamNationality);
                team.setWins(wins);
                team.setLosses(losses);
                team.setTournamentWins(tournamentWins);
                while(sc.hasNext()){
                    playerInfo = sc.nextLine().split(",");
                    age=Integer.parseInt(playerInfo[0]);
                    firstName=playerInfo[1].trim();
                    lastName=playerInfo[2].trim();
                    ign=playerInfo[3];
                    playerNationality=playerInfo[4];
                    role=playerInfo[5];
                    player=new Player(age,firstName,lastName,ign,playerNationality,role);
                    team.addPlayer(player);
                }
            } catch (Exception e){
                throw new IOException("Could not read the team from file");
            }
        }else team = new Team(teamName);
        return team;
    }

    /**
     * method to update the teams stored, by adding another team
     * @param team - the team to add
     * @throws IOException - if something fails
     */
    public static void updateTeamNamesToFile(Team team) throws IOException{
        file = new File(FILE_PATH_FILES+"TeamNames"+FILE_ENDING);
        String nameToBeWritten = team.getName().replaceAll("\\s","");
        StringBuilder names = new StringBuilder();
        ArrayList<String> teamNames=readTeamNamesFromFile();
        teamNames.forEach(s-> names.append(s).append("\n"));
        if(!teamNames.contains(nameToBeWritten)){
            names.append(nameToBeWritten).append("\n");
            try(FileWriter fw = new FileWriter(file)){
                fw.write(names.toString());
            }catch (Exception e){
                throw new IOException("Could not write exception due to: "+ e.getMessage());
            }
        }
    }

    /**
     * method to write all the team names to file
     * @param teamNames - an array list containing the team names
     * @throws IOException - if something fails with the writing
     */
    public static void writeTeamNamesToFile(ArrayList<String> teamNames) throws IOException{
        file = new File(FILE_PATH_FILES+"TeamNames"+FILE_ENDING);
        StringBuilder names = new StringBuilder();
        teamNames.forEach(s-> names.append(s).append("\n"));
        try(FileWriter fw = new FileWriter(file)){
            fw.write(names.toString());
        }catch (Exception e){
            throw new IOException("Could not write exception due to: "+ e.getMessage());
        }
    }

    /**
     * method to remove the team name from the stored team names
     * @param teamName - the team name
     * @throws IOException - if the removal fails
     */
    public static void removeTeamNameFromFile(String teamName) throws IOException{
        ArrayList<String> teamNames = readTeamNamesFromFile();
        teamNames.remove(teamName);
        teamNames.trimToSize();
        writeTeamNamesToFile(teamNames);

    }

    /**
     * method to read and return all the team names from a file
     * @return - the team names
     * @throws IOException - if the reading fails
     */
    public static ArrayList<String> readTeamNamesFromFile() throws IOException{
        file = new File(FILE_PATH_FILES+"TeamNames"+FILE_ENDING);
        String teamName;
        ArrayList<String> teamNames = new ArrayList<>();
        try(Scanner sc = new Scanner(file)){
            while(sc.hasNext()){
                teamName=sc.nextLine();
                teamNames.add(teamName);
            }
        } catch (Exception e){
            throw new IOException("Could not read the army names from the files");
        }
        return teamNames;
    }

    /**
     * method to get the info about a player, with separating commas
     * @param player - the player
     * @return - the info
     */
    private static String getPlayerInfo(Player player){
        return  player.getAge() + "," +
                player.getFirstName() + "," +
                player.getLastName() + "," +
                player.getIGN() + "," +
                player.getNationality() + "," +
                player.getRole();
    }
}
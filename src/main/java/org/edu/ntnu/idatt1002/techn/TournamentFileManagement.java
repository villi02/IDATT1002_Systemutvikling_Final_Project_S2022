package org.edu.ntnu.idatt1002.techn;

import org.edu.ntnu.idatt1002.techn.Teams.Player;
import org.edu.ntnu.idatt1002.techn.Teams.Team;
import org.edu.ntnu.idatt1002.techn.Tournament.Match;
import org.edu.ntnu.idatt1002.techn.Tournament.Runners.SingleEliminationRunner;
import org.edu.ntnu.idatt1002.techn.Tournament.Runners.TournamentRunner;
import org.edu.ntnu.idatt1002.techn.Tournament.Tournament;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The class for reading and writing tournament runners to a file
 *
 * /author Jarand Romestrand
 * /version 0.3
 */
public class TournamentFileManagement {

    private static File file;
    private static final String FILE_PATH_TOURNAMENT = "Files/Tournament/";
    private static final String FILE_PATH_TEAMS = "Files/Teams/";
    private static final String FILE_ENDING = ".txt";
    private static final String FILE_PATH_FILES = "Files/";

    /**
     * The method for reading a tournament runner
     *
     * @return the tournament runner
     * @throws IOException if something goes wrong  when reading the file
     */
    public static TournamentRunner readTournamentRunner() throws IOException {
        Tournament tournament = null;
        file = new File(FILE_PATH_TOURNAMENT + "currentTournament" + FILE_ENDING);
        try(Scanner sc = new Scanner(file)){

            String name = "";
            String type = "";
            int bracketSize = 0;
            int numberOfRounds = 0;
            String teamWinnerName = "";

            int counter = 0;
            while(sc.hasNext()){
                String info =sc.nextLine();

                if (counter == 0)
                    name = info;
                else if (counter == 1)
                    type = info;
                else if (counter == 2)
                    bracketSize = Integer.parseInt(info);
                else if (counter == 3)
                    numberOfRounds = Integer.parseInt(info);
                else
                    teamWinnerName = info;

                counter++;
            }
            tournament = new Tournament(name, type);
            tournament.setBracketSize(bracketSize);
            tournament.setNumberOfRounds(numberOfRounds);
            if (!teamWinnerName.equals("null"))
                tournament.setTournamentWinner(readTeamFromFile(teamWinnerName));

        } catch (Exception e){
            throw new IOException("Could not read the team names from the files");
        }

        ArrayList<Team> teams = readListOfTeamNames(FILE_PATH_FILES+"TeamNamesTournament"+FILE_ENDING);
        tournament.addAllTeams(teams);
        List<Match> matchesPlayed = readListOfMatches(FILE_PATH_TOURNAMENT + "matchesPlayed" + FILE_ENDING);
        List<Match> matchesThisRound = readListOfMatches( FILE_PATH_TOURNAMENT + "matchesThisRound" + FILE_ENDING);
        List<Team> teamsWinners = readListOfTeamNames(FILE_PATH_TOURNAMENT + "teamWinners" + FILE_ENDING);


        TournamentRunner runner = null;
        file = new File(FILE_PATH_TOURNAMENT + "runner" + FILE_ENDING);
        try(Scanner sc = new Scanner(file)){

            int counterRounds = 0;
            int counterMatches = 0;
            boolean isFinished = false;

            int counter = 0;
            while(sc.hasNext()){
                String info = sc.nextLine();
                if (counter == 0)
                    counterRounds = Integer.parseInt(info);
                else if (counter == 1)
                    counterMatches = Integer.parseInt(info);
                else
                    isFinished = Boolean.parseBoolean(info);

                counter++;
            }

            if (tournament.getType().equals("Single Elimination")) {
                runner = new SingleEliminationRunner(tournament);

                if (isFinished)
                    runner.setFinished();
                else {
                    runner.setMatchesPlayed((ArrayList<Match>) matchesPlayed);
                    runner.setMatchesThisRound((ArrayList<Match>) matchesThisRound);
                    runner.setWinnersThisRound((ArrayList<Team>) teamsWinners);
                    runner.setCounterRounds(counterRounds);
                    runner.setCounterMatchesRegistered(counterMatches);
                }
            }
            // TODO: 29.04.2022 code in the other torunament types
        } catch (Exception e){
            throw new IOException("Could not read the teams names from the files");
        }

        return runner;
    }

    public static void updateTeamsFromTournament(ArrayList<Team> teams)throws IOException{
        for(Team team : teams){
            updateTeam(team);
        }
    }

    public static void updateTeam(Team team)throws IOException{
        var allTeams= FileManagement.readAllTeamsFromFiles();
        for(Team t : allTeams){
            if(t.equals(team)) t=team;
        }
        FileManagement.writeAllTeamsToFile(allTeams);
    }
    /**
     * The method for reading a list of team names
     * @param fileName the file being read
     * @return the list of teams
     * @throws IOException if something goes wrong when reading
     */
    private static ArrayList<Team> readListOfTeamNames(String fileName) throws IOException {
        file = new File(fileName);
        List<String> teamNames = new ArrayList<>();

        try(Scanner sc = new Scanner(file)){
            while(sc.hasNext()){
                teamNames.add(sc.nextLine());
            }
        } catch (Exception e){
            throw new IOException("Could not read the tournament  from the files");
        }

        List<Team> allTeams = readAllTeamsFromFiles();

        ArrayList<Team> teams = new ArrayList<>();
        for (Team t : allTeams) {
            for (String name : teamNames) {
                if (t.getName().equals(name))
                    teams.add(t);
            }
        }

        return teams;
    }

    /**
     * The method for reading a list of matches
     * @param fileName the file being read
     * @return the list of matches
     * @throws IOException if something goes wrong when reading
     */
    private static List<Match> readListOfMatches(String fileName) throws IOException {
        file = new File(fileName);
        List<Match> matches = new ArrayList<>();

        try(Scanner sc = new Scanner(file)){

            String team1 = "";
            String team2 = "";
            String winner = "";
            String result = "";
            int counter = 0;
            Match m = null;
            while(sc.hasNext()){
                String info = sc.nextLine();
                if (!info.isBlank()) {
                    if (counter == 0)
                        team1 = info;
                    else if (counter == 1)
                        team2 = info;
                    else if (counter == 2)
                        winner = info;
                    else{
                        result = info;
                        counter=-1;

                        m = new Match(readTeamFromFile(team1), readTeamFromFile(team2));
                        if (!winner.equals("null")) {
                            m.setWinner(readTeamFromFile(winner));
                            m.setResult(result);
                        }
                        matches.add(m);
                    }
                    counter++;
                }

            }


        } catch (Exception e){
            throw new IOException("Could not read the matches from the file");
        }

        return matches;
    }

    /**
     * The method fro writing a tournament runner to a file
     * @param runner the runner being written down
     * @throws IOException if something goes wrong when writing to the file
     */
    public static void writeTournamnetRunnerToFile(TournamentRunner runner) throws IOException {
        writeTournamentToFile(runner.getTournament());
        writeListOfMatches(runner.getMatchesPlayed(),FILE_PATH_TOURNAMENT + "matchesPlayed" + FILE_ENDING);
        writeListOfMatches(runner.getMatchesThisRound(), FILE_PATH_TOURNAMENT + "matchesThisRound" + FILE_ENDING);
        writeTeamNamesToFile(runner.getWinnersThisRound(), FILE_PATH_TOURNAMENT + "teamWinners" + FILE_ENDING);

        file = new File(FILE_PATH_TOURNAMENT + "runner" + FILE_ENDING);
        StringBuilder sb = new StringBuilder();
        sb.append(runner.getCounterRounds()).append("\n");
        sb.append(runner.getCounterMatchesRegistered()).append("\n");
        sb.append(runner.isFinished()).append("\n");

        try(FileWriter fw = new FileWriter(file)){
            fw.write(sb.toString());
        } catch (Exception e){
            throw new IOException("Could not write this team to a file");
        }
    }

    /**
     * The method for writing a list of mathes to a file
     * @param matchesPlayed the list of being read
     * @param fileName the file being written to
     * @throws IOException if something goes wrong while writing to the file
     */
    private static void writeListOfMatches(ArrayList<Match> matchesPlayed, String fileName) throws IOException {
        file = new File(fileName);
        StringBuilder sb = new StringBuilder();

        for (Match m : matchesPlayed) {
            sb.append(getMatchAsString(m)).append("\n");
        }

        try(FileWriter fw = new FileWriter(file)){
            fw.write(sb.toString());
        } catch (Exception e){
            throw new IOException("Could not write this team to a file");
        }
    }

    /**
     * The method for getting a match as a string
     * @param m the match requested as a string
     * @return the match as a string
     */
    private static String getMatchAsString(Match m) {
        StringBuilder sb = new StringBuilder();
        sb.append(m.getTeam1().getName()).append("\n");
        sb.append(m.getTeam2().getName()).append("\n");
        if (m.getWinner() == null)
            sb.append("null").append("\n");
        else
            sb.append(m.getWinner().getName()).append("\n");
        sb.append(m.getResult()).append("\n");
        return String.valueOf(sb);
    }

    /**
     * The method for writing a tournament to a file
     * @param tournament the tournament being written down
     * @throws IOException if something goes wrong when writing to the file
     */
    private static void writeTournamentToFile(Tournament tournament) throws IOException {
        writeTeamNamesToFile(tournament.getTeams(), FILE_PATH_FILES+"TeamNamesTournament"+FILE_ENDING);
        file = new File(FILE_PATH_TOURNAMENT+"currentTournament"+FILE_ENDING);

        StringBuilder sb = new StringBuilder(tournament.getName()).append("\n");
        sb.append(tournament.getType()).append("\n");
        sb.append(tournament.getBracketSize()).append("\n");
        sb.append(tournament.getNumberOfRounds()).append("\n");
        if (tournament.getTournamentWinner() == null)
            sb.append("null");
        else
            sb.append(tournament.getTournamentWinner()).append("\n");

        try(FileWriter fw = new FileWriter(file)){
            fw.write(sb.toString());
        } catch (Exception e){
            throw new IOException("Could not write this team to a file");
        }
    }

    /**
     * The method for writing team names to a file
     * @param teams the list of teams
     * @param fileName the file being writen to
     * @throws IOException if something goes wrong when writing to the file
     */
    private static void writeTeamNamesToFile(ArrayList<Team> teams, String fileName) throws IOException {
        file = new File(fileName);
        StringBuilder names = new StringBuilder();
        teams.forEach(s-> names.append(s.getName()).append("\n"));
        try(FileWriter fw = new FileWriter(file)){
            fw.write(names.toString());
        }catch (Exception e){
            throw new IOException("Could not write exception due to: "+ e.getMessage());
        }
    }

    /**
     * The method for reading all the teams registered from the file
     * @return the list of teams
     * @throws IOException if something goes wrong when reading the file
     */
    private static ArrayList<Team> readAllTeamsFromFiles() throws IOException {
        ArrayList<Team> teams = new ArrayList<>();
        ArrayList<String> teamNames = readTeamNamesFromFile();
        Team teamToAdd;
        for(String s : teamNames){
            teamToAdd=readTeamFromFile(s);
            teams.add(teamToAdd);
        }
        return teams;
    }

    /**
     * The method for reading the team names from a file
     * @return the list of team names
     * @throws IOException if something goes wrong when reading the team names from a file
     */
    private static ArrayList<String> readTeamNamesFromFile() throws IOException{
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
     * The method for reading a team from a file
     * @param teamName the name of the team requested
     * @return the team
     * @throws IOException if something goes wrong when reading a team from a file
     */
    private static Team readTeamFromFile(String teamName) throws IOException{
        String fileName = teamName.replaceAll("\\s","");
        file = new File(FILE_PATH_TEAMS+fileName+FILE_ENDING);
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
        if (!teamName.equals("Bypass")) {
            try (Scanner sc = new Scanner(file)) {
                finalTeamName = sc.nextLine();
                wins = Integer.parseInt(sc.nextLine());
                losses = Integer.parseInt(sc.nextLine());
                tournamentWins = Integer.parseInt(sc.nextLine());
                teamNationality = sc.nextLine();
                team = new Team(finalTeamName, teamNationality);
                team.setWins(wins);
                team.setLosses(losses);
                team.setTournamentWins(tournamentWins);
                while (sc.hasNext()) {
                    playerInfo = sc.nextLine().split(",");
                    age = Integer.parseInt(playerInfo[0]);
                    firstName = playerInfo[1];
                    lastName = playerInfo[2];
                    ign = playerInfo[3];
                    playerNationality = playerInfo[4];
                    role = playerInfo[5];
                    player = new Player(age, firstName, lastName, ign, playerNationality, role);
                    team.addPlayer(player);
                }
            } catch (Exception e) {
                throw new IOException("Could not read the team from file");
            }
        }else team = new Team(teamName);
        return team;
    }
}

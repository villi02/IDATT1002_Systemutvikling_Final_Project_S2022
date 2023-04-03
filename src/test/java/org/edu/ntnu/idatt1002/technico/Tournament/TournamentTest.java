package org.edu.ntnu.idatt1002.technico.Tournament;

import org.edu.ntnu.idatt1002.techn.Teams.Player;
import org.edu.ntnu.idatt1002.techn.Teams.Team;
import org.edu.ntnu.idatt1002.techn.Tournament.Match;
import org.edu.ntnu.idatt1002.techn.Tournament.Runners.SingleEliminationRunner;
import org.edu.ntnu.idatt1002.techn.Tournament.Runners.TournamentRunner;
import org.edu.ntnu.idatt1002.techn.Tournament.Tournament;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TournamentTest {
    Team team1=new Team("Team1");
    Team team2=new Team("Team2","Chinese");
    Tournament tournament=new Tournament("Test tournament", "Single Elimination");
    private void addTestData(){
        Player p1 = new Player(19, "Soren", "Bjergsen","Bjergsen", "Danish", "Top");
        Player p2 = new Player(20, "Per", "Olsen", "Polsen", "Norwegian", "jungle");
        Player p3 = new Player(21, "Pål", "Bjergsen", "Pjergen", "German", "mid");
        Player p4 = new Player(22, "Tor", "Bjergsen", "Tjergsen", "British", "adc");
        Player p5 = new Player(23, "Hans", "Bjergsen","Hjergnsen", "French", "support");
        Player e1 = new Player(19, "Jens", "Bjergsen","Jergsen", "Norwegian", "Top");
        Player e2 = new Player(20, "Peter", "Olsen","Plarksen", "Danish", "jungle");
        Player e3 = new Player(21, "Lars", "Bjergsen", "Lergsen", "French", "mid");
        Player e4 = new Player(22, "Frits", "Bjergsen", "Fritsen", "German", "adc");
        Player e5 = new Player(23, "Jan", "Bjergsen", "Jangsen", "British", "support");
        team1.addPlayer(p1);
        team1.addPlayer(p2);
        team1.addPlayer(p3);
        team1.addPlayer(p4);
        team1.addPlayer(p5);
        team2.addPlayer(e1);
        team2.addPlayer(e2);
        team2.addPlayer(e3);
        team2.addPlayer(e4);
        team2.addPlayer(e5);
        tournament.addTeam(team1);
        tournament.addTeam(team2);
    }

    @Nested
    public class ConstructorTest{
        @Test
        public void exceptionThrownWhenNameIsBlank(){
            assertThrows(IllegalArgumentException.class,()-> new Tournament("", "Single Elimination"));
            assertThrows(IllegalArgumentException.class,()-> new Tournament("Name", ""));
        }

    }

    @Nested
    public class SingleEliminationTest{
        @Test
        public void addTeamToTournament(){
            SingleEliminationRunner test = new SingleEliminationRunner(new Tournament("Kombos ESPORT", "Single Elimination"));
            addTestData();
            assertEquals(0,test.getTournament().getTeams().size());
            test.getTournament().addTeam(team1);
            assertEquals(1,test.getTournament().getTeams().size());
            test.getTournament().addTeam(team2);
            assertEquals(2,test.getTournament().getTeams().size());
        }

        @Test
        public void addAllTeamsToTournament(){
            ArrayList<Team> newTeams = new ArrayList<>();
            SingleEliminationRunner test = new SingleEliminationRunner(new Tournament("Kombos ESPORT", "Single Elimination"));
            for(int i=0; i<10;i++){
                String name="Kombo" +i;
                newTeams.add(new Team(name));
            }
            assertEquals(0,test.getTournament().getTeams().size());
            test.getTournament().addAllTeams(newTeams);
            assertEquals(10,test.getTournament().getTeams().size());
        }

        @Test
        public void cannotAddTheSameTeam(){
            SingleEliminationRunner test = new SingleEliminationRunner(new Tournament("Kombos ESPORT", "Single Elimination"));
            Team teamTimesTwo = new Team("Adding this team twice");
            assertDoesNotThrow(()->test.getTournament().addTeam(teamTimesTwo));
            assertThrows(IllegalArgumentException.class,()->test.getTournament().addTeam(teamTimesTwo));
        }

        @Test
        public void getTheCorrectBracketSize(){
            SingleEliminationRunner test = new SingleEliminationRunner(new Tournament("Kombos ESPORT", "Single Elimination"));
            for(int i=0; i<13;i++){
                String name="Team"+i;
                test.getTournament().addTeam(new Team(name));
            }
            test.createTournament();
            assertEquals(8,test.getTournament().getBracketSize());
            test = new SingleEliminationRunner(new Tournament("Kombos ESPORT", "Single Elimination"));
            for(int i=13; i<31;i++){
                String name="Team"+i;
                test.getTournament().addTeam(new Team(name));
            }
            test.createTournament();
            assertEquals(16,test.getTournament().getBracketSize());
        }

        @Test
        public void getCorrectNumberOfMatches(){
            SingleEliminationRunner test = new SingleEliminationRunner(new Tournament("Kombos ESPORT", "Single Elimination"));
            for(int i=0; i<13;i++){
                String name="Team"+i;
                test.getTournament().addTeam(new Team(name));
            }
            test.createTournament();
            assertEquals(16-1, test.getTournament().getTeams().size() - 1);
        }

        @Test
        public void fillInCorrectAmountOfTeams(){
            ArrayList<Team> newTeams = new ArrayList<>();
            SingleEliminationRunner test = new SingleEliminationRunner(new Tournament("Kombos ESPORT", "Single Elimination"));
            for(int i=0; i<13;i++){
                String name="Team"+i;
                test.getTournament().addTeam(new Team(name));
            }
            assertEquals(13,test.getTournament().numberOfTeams());
            test.createTournament();
            assertEquals(16,test.getTournament().numberOfTeams());
        }

        @Test
        public void twoBypassTeams(){
            Team team1 = new Team("Bypass");
            Team team2 = new Team("bypass");
            Match match = new Match(team1,team2);
            SingleEliminationRunner se = new SingleEliminationRunner(new Tournament("Kombo", "Single Elimination"));
            se.getTournament().addTeam(team1); se.getTournament().addTeam(team2);
            se.registerResult(match,2,0);
        }

        @Test
        public void testToSeIfASingleEliminationCanBeRanAndFinished() {
            TournamentRunner runner = new SingleEliminationRunner(new Tournament("name", "Single Elimination"));

            for (int i = 0; i < 7; i++) {
                runner.getTournament().addTeam(new Team("team " + i));
            }
            runner.createTournament();
            assertEquals(0, runner.getMatchesPlayed().size());
            for (int i = 0; i < runner.getTournament().getNumberOfRounds() - 1; i++) {
                for (int j = 0; j < runner.getMatchesThisRound().size(); j++) {
                    runner.registerResult(runner.getMatchesThisRound().get(j), 2, 0);
                }
            }
            runner.registerResult(runner.getMatchesThisRound().get(0), 2, 0);
            assertTrue(runner.isFinished());
        }
    }
}

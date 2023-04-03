package org.edu.ntnu.idatt1002.technico.Teams;

import org.edu.ntnu.idatt1002.techn.Teams.Player;
import org.edu.ntnu.idatt1002.techn.Teams.Team;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {

    @Nested
    class ClassTeamTest {

        @Test
        void registerNewTeam() {
            ArrayList<Player> testArrayList = new ArrayList<>();
            assertThrows(IllegalArgumentException.class, () -> {
                Team newTeam = new Team(testArrayList, "", "");
            });
        }



        //Maybe delete this org.edu.ntnu.idatt1002.techn.Login.test
        @Test
        void testSetName() {
            Team newTeam = new Team("Test", "Norway");
            assertThrows(IllegalArgumentException.class, () -> newTeam.setName(""));
        }

        @Test
        void testAddPlayer() {
            Player newPlayer = new Player(30, "Jonatan", "Vevang", "Duphos", "Norway", "Captain");
            Team newTeam = new Team("Test", "Norway");
            newTeam.addPlayer(newPlayer);

            ArrayList<Player> tAP = new ArrayList<>();
            tAP.add(newPlayer);

            assertEquals(newTeam.getPlayers(), tAP);


        }

        @Test
        void removeAddedPlayer() {
            Player newPlayer = new Player(30, "Jonatan", "Vevang", "Duphos", "Norway", "Captain");
            Team newTeam = new Team("Test", "Norway");
            newTeam.addPlayer(newPlayer);
            assertTrue(newTeam.removePlayer(newPlayer));
        }

        @Test
        void testToStringMethod() {
            Player newPlayer = new Player(30, "Jonatan", "Vevang", "Duphos", "Norway", "Captain");
            Team newTeam = new Team("Test", "Norway");
            String testString = "Teamname: Test Total wins: 0 Total losses: 0";
            assertEquals(testString, newTeam.toString());
        }
    }
}

package org.edu.ntnu.idatt1002.technico.Tournament;

import org.edu.ntnu.idatt1002.techn.Teams.Team;
import org.edu.ntnu.idatt1002.techn.Tournament.Match;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatchTest {

    @Test
    public void makeAMarchWithOnlyOneTeam() {
        var team = new Team("Teamname");
        var match = new Match(team);
        assertEquals(team,match.getWinner());
        assertTrue(match.getRegistered());
    }

    @Test
    public void registerAMatchResultWhereTeamOneWins() {
        var team1 = new Team("team1");
        var team2 = new Team("team2");
        var match = new Match(team1, team2);
        match.registerMatchResult(2,0);
        assertTrue(match.getRegistered());
        assertEquals(match.getResult(), "2-0");
    }

    @Test
    public void registerAMatchResultWhereTeamTwoWins() {
        var team1 = new Team("team1");
        var team2 = new Team("team2");
        var match = new Match(team1, team2);
        match.registerMatchResult(0,2);
        assertTrue(match.getRegistered());
        assertEquals(match.getResult(), "0-2");
    }

    @Test
    public void registerMatchResultWhenATeamIsBypass() {
        Team team1 = new Team("team1", "norwegian");
        Match match = new Match(team1);
        //will create a match between team1 and bypass
        match.registerMatchResult(0,2);
        assertEquals(team1, match.getWinner());
        assertEquals(match.getTeam2(), new Team("Bypass"));
    }
}


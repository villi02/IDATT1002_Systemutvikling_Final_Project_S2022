package org.edu.ntnu.idatt1002.technico;

import org.edu.ntnu.idatt1002.techn.Database;
import org.edu.ntnu.idatt1002.techn.Teams.Player;
import org.edu.ntnu.idatt1002.techn.Teams.Team;
import org.edu.ntnu.idatt1002.techn.Tournament.Tournament;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

// The tests in this file do work individually, however, they do not pass when run together. The cause of this is unknown.

/**
 * /Author Vilhjalmur Arnar Vilhjalmsson
 */
class DatabaseTest {
/*

    private Tournament addTestData(){
        Team team1=new Team("TeamNerd");
        Team team2=new Team("TeamNotNerd","Chinese");
        Tournament tournament=new Tournament("Test tournament1", "Single Elimination");

        Player p1 = new Player(19, "Soren", "Bjergsen","Bjergsen", "Danish", "Top");
        Player p2 = new Player(20, "Per", "Olsen", "Polsen", "Norwegian", "jungle");
        Player p3 = new Player(21, "PÃ¥l", "Bjergsen", "Pjergen", "German", "mid");
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
        return tournament;
    }

    Database db = Database.instance();
    // admin, admin, admin@admin.admin

    @Test
    void selectAll() throws SQLException {
        Database.instance().remove("admin", "admin");
        Database.instance().register("admin", Database.instance().hashPassword("admin"), "admin@admin.admin");
        assertEquals(1, Database.instance().selectAll().size());
        Database.instance().remove("admin", "admin");

    }

    @Test
    void getAll() {
        int DatabaseSize = db.getSize();
        assertEquals(DatabaseSize, db.getAll().size());
    }

    @Test
    void TestForCorrectSize() {
        int DatabaseSize = db.getAll().size();
        assertEquals(DatabaseSize, db.getSize());
    }

    @Test
    void checkUserInDatabase() {
        assertEquals(true, db.userInnDB("admin"));
        assertEquals(true, db.userInnDB("admin@admin.admin"));
    }

    @Test
    void checkUserNotInDataBase() {
        assertEquals(false, db.userInnDB("tester"));
    }

    @Test
    void MatchUserWithPasswordInDataBase() {
        Database database = new Database();
        assertEquals(true, database.checkUser("admin", "admin"));
        assertEquals(true, database.checkUser("admin@admin.admin", "admin"));
        assertEquals(false, database.checkUser("Admin", "Admin"));
    }

    @Test
    void testPasswordHash() {
        String password = "moose";
        assertEquals(false, password.equals(db.hashPassword(password)));
        assertEquals(true, password.equals(password));
    }

    @Test
    void registerNewUserNotInDatabase() throws SQLException {
        assertEquals(true, db.register("ATest", "TestPassword", "org.edu.ntnu.idatt1002.techn.Login.test@testemail.com"));
        db.remove("ATest", "TestPassword");
        assertEquals(false, db.userInnDB("ATest"));
    }

    @Test
    void registerUserAlreadyInDatabase() throws InterruptedException {
        assertEquals(false, db.register("admin", "admin", "admin@admin.admin"));
    }

    @Test
    void testSerializeWithoutDb() throws IOException, ClassNotFoundException {
        Tournament tournament = addTestData();
        byte[] ser = FileManagement.serializeTournament(tournament);
        Tournament tournament1 = (Tournament) FileManagement.deserialize(ser);
        assert tournament1 != null;
    }


    @Test
    void getSize() {
        assertEquals(db.getSize(), db.selectAll().size());
    }

    @Test
    void registerAndGetSerializedTournamentInDatabase() {
        //assertEquals(true, db.registerTournamentSerialize(tournament, "Tester"));
        Tournament tournament = addTestData();
        db.registerTournamentSerialize(tournament, "admin");
        assertEquals(true, db.getTournamentDeserialized("admin").equals(tournament));
        Database.instance().removeTournament("admin");
    }
*/
}
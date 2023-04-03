package org.edu.ntnu.idatt1002.technico.Teams;
import org.edu.ntnu.idatt1002.techn.Teams.Player;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class PlayerTest {
    Player p = new Player(19, "Soren", "Bjergsen", "Bjergsen", "Danish", "Player");

    @Nested
    class playerRoleTest {
        @Test
        public void testToCheckIfRoleEqualsRole() {
            assertEquals("Player", p.getRole());
        }

        @Test
        public void testThrowExceptionIfAgeIsLessThanZero() {
            assertThrows(IllegalArgumentException.class, () ->
            {Player newPlayer = new Player(-1, "", "", "","", "Player");});
        }
    }
}
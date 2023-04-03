package org.edu.ntnu.idatt1002.technico.Teams;

import org.edu.ntnu.idatt1002.techn.Teams.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Nested
    class personRegistrationTest {
        @Test
        public void throwIfPersonsAgeIsLessThanZero() {
            assertThrows(IllegalArgumentException.class,() ->
            {Person newPerson = new Person(-1, "", "", "") {
            };});
        }

        @Test
        public void getParametersTests() {
            Person newPerson = new Person(19, "Stian", "Wilhelmsen", "Norwegian") {
            };
            assertEquals(19, newPerson.getAge());
            assertEquals("Stian", newPerson.getFirstName());
            assertEquals("Wilhelmsen", newPerson.getLastName());
            assertEquals("Norwegian", newPerson.getNationality());
            assertEquals("Stian Wilhelmsen", newPerson.getFullName());
        }
    }
}
package org.edu.ntnu.idatt1002.techn.Teams;

/**
 * /Author Stian Wilhelmsen
 * /Version v0.3
 * The type Player.
 */
public class Player extends Person {

    /**
     * Initialize variable for later use
     */
    private String role;
    private String IGN;

    /**
     * Constructor for player, extending from person
     * @param age         the age
     * @param firstName   the first name
     * @param lastName    the last name
     * @param IGN         the in game name
     * @param nationality the nationality
     * @param role        the role
     */
    public Player(int age, String firstName, String lastName,String IGN, String nationality, String role) {
        super(age, firstName, lastName, nationality);
        setRole(role);
        setIGN(IGN);
    }

    /**
     * Get the role of the player
     * @return the role of the player
     */
    public String getRole() {
        return role;
    }

    /**
     * Set the role of the player
     * Throws new IllegalArgumentException if role is blank because role can not be an empty string
     * @param role either player, coach or captain
     */
    public void setRole(String role) {
        if(role.isBlank()){
            throw new IllegalArgumentException(" The role can not be empty ");
        }else{
            this.role = role;
        }
    }

    /**
     * Gets the IGN of a player
     * @return IGN of the player
     */
    public String getIGN() {
        return IGN;
    }

    /**
     * Set IGN of the player
     * Throws new IllegalArgumentException if IGN is blank because role can not be an empty string
     * @param IGN in game name
     */
    public void setIGN(String IGN) {
        if(IGN.isBlank()){
            throw new IllegalArgumentException(" The IGN can not be empty ");
        }else{
            this.IGN = IGN;
        }
    }

    /**
     * toString method
     * @return information about the player(name,age,nationality,role)
     */

    @Override
    public String toString() {
        return "Name: " + getFullName() + "\n" +
                "Age: " + getAge() + "\n" +
                "IGN: " + getIGN() + "\n" +
                "Nationality: " + getNationality() + "\n" +
                "Role: " + getRole();
    }


}
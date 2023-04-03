package org.edu.ntnu.idatt1002.techn.Teams;


/**
 * /Author Jonatan Andre Vevang
 * /Version v0.3
 */
public abstract class Person {

    /**
     * Initialize variables for later use
     */
    private int age; // Age to person
    private String firstName; // Firstname of person
    private String lastName; // Lastname of person
    private String nationality; // Country

    /**
     * Constructor for person
     * @param age
     * @param firstName
     * @param lastName
     * @param nationality
     */
    public Person(int age, String firstName, String lastName, String nationality) {
        if(nationality.isBlank()){
            throw new IllegalArgumentException(" Nationality cant be blank ");
        }
        setAge(age);
        setFirstName(firstName);
        setLastName(lastName);
        this.nationality = nationality;
    }

    /**
     * Get the age of the person
     * @return the age of the person
     */
    public int getAge() {
        return age;
    }

    /**
     * Set the age of a person
     * Throws new IllegalArgumentException if age in below or equal to zero and higher then 150
     * @param age
     */
    public void setAge(int age) {
        if(age <= 0 || age > 150){
            throw new IllegalArgumentException(" The age of the person must be greater then 0 ");
        }else{
            this.age = age;
        }
    }

    /**
     * Get the first name of the person
     * @return the first name of the person
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the first name of the person
     * Throws new IllegalArgumentException if first name is blank because first name can not be an empty string
     * @param firstName
     */
    public void setFirstName(String firstName) {
        if(firstName.isBlank()){
            throw new IllegalArgumentException("The first name can not be empty");
        }else{
            this.firstName = firstName;
        }
    }

    /**
     * Get the last name of the person
     * @return the last name of the person
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name of the person
     * Throws new IllegalArgumentException if is last name is blank because last name can not be an empty string
     * @param lastName
     */
    public void setLastName(String lastName) {
        if(lastName.isBlank()){
            throw new IllegalArgumentException("The last name can not be empty");
        }else{
            this.lastName = lastName;
        }
    }

    /**
     * Get the full name of the person
     * @return the full name of the person
     */
    public String getFullName(){
        return getFirstName() + " " + getLastName();
    }

    /**
     * Get the nationality of the person
     * @return the nationality of the person
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * toString method
     * @return information about the person(name,age,nationality)
     */
    @Override
    public String toString() {
        return "Name: " + getFullName() + " Age: " + age + " Nationality: " + nationality;
    }
}

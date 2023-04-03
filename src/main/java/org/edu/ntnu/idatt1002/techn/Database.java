package org.edu.ntnu.idatt1002.techn;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

// The tests for this class do pass when run individually, but not collectively. The reason for this is unknown.


/**
 * a class which is used for the login function of the program.
 * /Author Vilhjalmur Arnar Vilhjalmsson
 * /Version v1.2
 */
public class Database {

    /**
     * Database object to be used
     */
    private static Database database;

    /**
     * URL of the location of database to be used
     */
    private static String databaseURL = "jdbc:sqlite:" + "Users.db";

    /**
     * This value is used in btnClickLogin method in FrontPageController class.
     * It stores info regarding if a user is logged in or not.
     */
    private static boolean loggedIn;

    /**
     * Logged in value
     * @return true if user is logged in
     */
    public static boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Set method
     * @param loggedIn logged in value
     */
    public static void setLoggedIn(boolean loggedIn) {
        Database.loggedIn = loggedIn;
    }

    /**
     * A method to connect to the database
     * @return an object which is to be used for interaction with the database
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseURL);
    }


    /**
     * Method to get the info of all user, intended to duplicate SELECT command
     * @return all users
     */
    public ArrayList<String> selectAll(){
        String sql = "SELECT user_name, password, email FROM users";
        ArrayList<String> allUsers = new ArrayList<>();

        try (Connection conn = Database.instance().getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {

                StringBuilder str = new StringBuilder();
                str.append(rs.getString("user_name") + "\t");
                str.append(rs.getString("password") + "\t");
                str.append(rs.getString("email"));
                allUsers.add(str.toString());
            }
            return allUsers;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * A method to retrieve all the entries in the database
     * @return The users in the database as a Hashmap
     */
    public HashMap<String, ArrayList<String>> getAll(){
        HashMap<String, ArrayList<String>> allUsers = new HashMap<>();
        String sql = "SELECT user_name, password, email FROM users";

        try (Connection conn = Database.instance().getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                ArrayList<String> passAndMail = new ArrayList<>();
                passAndMail.add(rs.getString("password"));
                passAndMail.add(rs.getString("email"));
                allUsers.put(rs.getString("user_name"), passAndMail);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allUsers;
    }


    /**
     * A method to check if user is in the database
     * @param usrNmOrMail The username or email of the user to be checked as a String
     * @return a boolean value, true if the user is in database
     */
    public boolean userInnDB(String usrNmOrMail) {
        String sql = "SELECT user_name, password, email FROM users WHERE user_name=? OR email=?;";
        try (Connection conn = Database.instance().getConnection()){
            PreparedStatement stmt  = conn.prepareStatement(sql);
            stmt.setString(1, usrNmOrMail);
            stmt.setString(2, usrNmOrMail);
            ResultSet rs    = stmt.executeQuery();
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * A method to check the user, checks if inn database and if the password matches
     * @param usrnmOrMail The username of the user as a String
     * @param password The password (not hashed) of the user as a String
     * @return true if both password and username match
     */
    public boolean checkUser(String usrnmOrMail, String password){
        if (!userInnDB(usrnmOrMail)){
            return false;
        }
        String sql = "SELECT user_name, password, email FROM users WHERE user_name=? OR email=?;";
        try (Connection conn = Database.instance().getConnection()){
            PreparedStatement stmt  = conn.prepareStatement(sql);
            stmt.setString(1, usrnmOrMail);
            stmt.setString(2, usrnmOrMail);
            ResultSet rs    = stmt.executeQuery();
            if (rs.getString("user_name").equals(usrnmOrMail) || rs.getString("email").equals(usrnmOrMail)){
                return rs.getString("password").equals(hashPassword(password));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



        return false;
    }
    /**
     * Hashes a password
     * @param password the password to be hashed as a String
     * @return a hashed password as a String
     */
    public String hashPassword(String password){
        String generatedPassword = "";
        try
        {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Add password bytes to digest
            md.update(password.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest();

            // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            // Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * A method to register/insert a new user into the database
     * @param username The username of the new user as a String
     * @param password The password (not hashed) of the new user as a String
     * @param email The email of the new user as a String
     * @return True if the registration/insertion is successful
     */
    public boolean register(String username, String password, String email) {
        if (userInnDB(username)){
            return false;
        }

        String sql = "INSERT INTO users(user_name,password,email) VALUES(?,?,?)";

        try (Connection conn = Database.instance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashPassword(password));
            pstmt.setString(3, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    /**
     * A method to remove a user from the database
     * @param usrnmOrMail The username or email of the user to be removed as a String
     * @param password The password of the user to be removed as a String
     * @return True if the removal is successful
     * @throws SQLException - if it fails
     */
    public boolean remove(String usrnmOrMail, String password) throws SQLException {
        if (!userInnDB(usrnmOrMail)) {
            return false;
        }
        String sql = "DELETE FROM users WHERE user_name=? OR email=?;";
        try (Connection conn = Database.instance().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usrnmOrMail);
            stmt.setString(2, usrnmOrMail);
            stmt.execute();

            return (!remove(usrnmOrMail, password));
        }
    }

    /**
     * Returns size of table containing users
     * @return The amount of entries in users database
     */
    public int getSize(){
        String sql = "SELECT COUNT(*) FROM users;";
        try (Connection conn = Database.instance().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            return rs.getInt("COUNT(*)");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }



    /**
     * A method to remove a tournament from database
     * @param userName the username of the creator of the tournament
     * @return true if successfully removed
     */
    public boolean removeTournament(String userName) {
        String sql = "DELETE FROM tournaments WHERE tournament_creator=?;";
        String check = "SELECT tournament FROM tournaments WHERE tournament_creator=?;";
        try (Connection conn = Database.instance().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.execute();
            PreparedStatement ckstmt = conn.prepareStatement(check);
            ResultSet rs = ckstmt.executeQuery();
            if (rs.isClosed()) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * A method to create a database if it doesn't exist
     * @param databaseName the name of the database to be created
     */
    public static void createDatabase(String databaseName) {
        String url = "jdbc:sqlite:" + databaseName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            } else {
                System.out.println("Database Connection not found");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * A method to create a new table if it doesn't exist
     * @param databaseName the name of the database in which tables will be created
     */
    public static void createTables(String databaseName) {
        String url = "jdbc:sqlite:" + databaseName;

        String usersSql = "CREATE TABLE IF NOT EXISTS users (\n"
                + " user_name TEXT, \n"
                + " password TEXT, \n"
                + " email TEXT \n"
                + ");";

        String tournamentSql = "CREATE TABLE IF NOT EXISTS tournaments (\n"
                + " tournament_name TEXT, \n"
                + " tournament BLOB, \n"
                + " tournament_creator TEXT \n"
                + ");";

        String teamSql = "CREATE TABLE IF NOT EXISTS tournaments (\n"
                + " tournament BLOB, \n"
                + " team_creator TEXT \n"
                + ");";

        try{
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(usersSql);
            stmt.execute(tournamentSql);
            stmt.execute(teamSql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

        /**
         * A method to add an admin to the application database
         */
        public static void addAdmin(){
            String sql = "INSERT INTO users(user_name,password,email) VALUES(?,?,?)";
            try (Connection conn = Database.instance().getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "admin");
                pstmt.setString(2, Database.instance().hashPassword("admin"));
                pstmt.setString(3, "admin@admin.admin");
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    /**
     * A method to get an instance of a database from a static setting
     * @return the database
     */
    public static Database instance() {
            if (database == null){
                database = new Database();
                return database;
            }
            return database;
        }


}
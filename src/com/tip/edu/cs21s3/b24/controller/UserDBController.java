package com.tip.edu.cs21s3.b24.controller;

import com.tip.edu.cs21s3.b24.model.UserModel;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class UserDBController {

    // JDBC connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dbvitalpay?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "3afjWZHRVJUaHAzu";

    public UserDBController() {
    }

    // Method to establish a connection to the database
    private Connection connectToDatabase() throws SQLException {
        // Load MySQL JDBC driver (not needed in newer versions of JDBC, but still good practice)
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // Method to add a new user to the database
    public void addUser(String username, String password, String role) {

        String insertQuery = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = connectToDatabase(); PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Method to get all users from the database
    public UserModel getUsers() {
        String selectQuery = "SELECT id, user_id, first_name, last_name, address, username, password, role FROM users";

        try (Connection conn = connectToDatabase(); PreparedStatement pstmt = conn.prepareStatement(selectQuery); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String user_id = rs.getString("user_id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String address = rs.getString("address");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");

                // Print the data (or process it as needed)
                System.out.println("ID: " + id + "\n Username: " + username + "\n Password: " + password + "\n Role: " + role);

                // Return a new User object with the data
                return new UserModel(id, user_id, first_name, last_name, address, username, password, role);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;  // Return null in case of any SQL error
        }

        return null; // Return null deafault
    }

    // Method to get a user by username (example of searching for a specific user)
    public UserModel getUserByUserPass(String user, String pass) {
        String selectQuery = "SELECT id, user_id, first_name, last_name, address, username, password, role FROM users WHERE username = ? AND password = ?";

        try (Connection conn = connectToDatabase(); PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
            pstmt.setString(1, user);
            pstmt.setString(2, pass);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String user_id = rs.getString("user_id");
                    String first_name = rs.getString("first_name");
                    String last_name = rs.getString("last_name");
                    String address = rs.getString("address");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String role = rs.getString("role");

                    // Return a new User object with the data
                    return new UserModel(id, user_id, first_name, last_name, address, username, password, role);
                } else {
                    System.out.println("User not found.");
                    return null;  // Return null if no user found
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;  // Return null in case of any SQL error
        }
    }
}

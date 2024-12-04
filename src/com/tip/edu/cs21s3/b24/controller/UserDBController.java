package com.tip.edu.cs21s3.b24.controller;

import com.tip.edu.cs21s3.b24.model.PatientModel;
import com.tip.edu.cs21s3.b24.model.UserSession;
import com.tip.edu.cs21s3.b24.model.UserStaffModel;
import java.awt.List;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class UserDBController {

    // JDBC connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dbvitalpay?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "SPPvVcXvZ8Nz2xdD";


    // Constructor to initialize the controller with a table model
    public UserDBController() {
    }

    // Method to establish a connection to the database
    private Connection connectToDatabase() throws SQLException {
        // Load MySQL JDBC driver (not needed in newer versions of JDBC, but still good practice)
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // Method to add a new user to the database
    public boolean addUserStaff(String user_id, String first_name, String last_name, String address, String password, String role) {

        String insertQuery = "INSERT INTO users (user_id, first_name, last_name, address, password, role, archive) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connectToDatabase(); PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            pstmt.setString(1, user_id);
            pstmt.setString(2, first_name);
            pstmt.setString(3, last_name);
            pstmt.setString(4, address);
            pstmt.setString(5, password);
            pstmt.setString(6, role);
            pstmt.setString(7, "no");
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    
    // Method to archive a staff from the database
    public boolean archiveUserStaff(String userId) {
        String updateQuery = "UPDATE users SET archive = ? WHERE user_id = ?";

        try (Connection conn = connectToDatabase(); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            pstmt.setString(1, "yes");
            pstmt.setString(2, userId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Return true if the user was archiving successfully
        } catch (SQLException e) {
            System.err.println("Error archiving user: " + e.getMessage());
            return false;
        }
    }

    // Method to delete a review from the database and update the table model
    public boolean deleteUserStaff(String userId) {
        String deleteQuery = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = connectToDatabase(); PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {

            pstmt.setString(1, userId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Return true if the user was deleted successfully
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    // Method to get a user by username (example of searching for a specific user)
    public UserStaffModel getUserStaffByUserPass(String userId, String pass) {
        String selectQuery = "SELECT id, user_id, first_name, last_name, address, password, role, archive FROM users WHERE user_id = ? AND password = ?";

        try (Connection conn = connectToDatabase(); PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, pass);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String user_id = rs.getString("user_id");
                    String first_name = rs.getString("first_name");
                    String last_name = rs.getString("last_name");
                    String address = rs.getString("address");
                    String password = rs.getString("password");
                    String role = rs.getString("role");
                    String archive = rs.getString("archive");

                    // Return a new User object with the data
                    return new UserStaffModel(id, user_id, first_name, last_name, address, password, role, archive);
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

    // Fetch user data from the database
    public Object[][] fetchUserStaffData() {
        ArrayList<Object[]> userData = new ArrayList<>();

        String selectQuery = "SELECT user_id, first_name, last_name, role, archive FROM users ORDER BY id DESC";

        try (Connection conn = connectToDatabase(); PreparedStatement pstmt = conn.prepareStatement(selectQuery); ResultSet rs = pstmt.executeQuery()) {

            userData.clear(); // clear list first

            while (rs.next()) {
                String user_id = rs.getString("user_id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String role = rs.getString("role");
                String archive = rs.getString("archive");
                
                String currentUserId = UserSession.getInstance().getUserId();
                
                if(!archive.equals("yes") && !currentUserId.equals(String.format("%s %s (%s)", first_name, last_name, user_id))) {
                    userData.add(new Object[]{user_id, first_name + " " + last_name, role});
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching user data: " + e.getMessage());
        }

        // Convert List to a array
        return userData.toArray(new Object[0][0]); // Conversion to Object[][] for JTable compatibility
    }

    // Method to get all users from the database
    public UserStaffModel getAllUsers() {
        String selectQuery = "SELECT id, user_id, first_name, last_name, address, username, password, role, archive FROM users";

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
                String archive = rs.getString("archive");

                // Print the data (or process it as needed)
                // System.out.println("ID: " + id + "\n Username: " + username + "\n Password: " + password + "\n Role: " + role);
                // Return a new User object with the data
                return new UserStaffModel(id, user_id, first_name, last_name, address, password, role, archive);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;  // Return null in case of any SQL error
        }

        return null; // Return null deafault
    }
    
    public boolean insertPatient(PatientModel patient) {
        String insertQuery = "INSERT INTO patients ("
                + "patient_id, first_name, middle_name, last_name, phone, date_of_birth, address, "
                + "gender, blood_group, major_diseases, symptoms, diagnosis, medicines, "
                + "ward_required, type_of_ward, insurance_provider, company_name, id_card, archive"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = connectToDatabase(); PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            pstmt.setString(1, patient.getPatientId());
            pstmt.setString(2, patient.getFirstName());
            pstmt.setString(3, patient.getMiddleName());
            pstmt.setString(4, patient.getLastName());
            pstmt.setString(5, patient.getPhone());
            pstmt.setString(6, patient.getDateOfBirth());
            pstmt.setString(7, patient.getAddress());
            pstmt.setBoolean(8, patient.isGender()); // true for male, false for female
            pstmt.setString(9, patient.getBloodGroup());
            pstmt.setString(10, patient.getMajorDiseases());
            pstmt.setString(11, patient.getSymptoms());
            pstmt.setString(12, patient.getDiagnosis());
            pstmt.setString(13, patient.getMedicines());
            pstmt.setBoolean(14, patient.isWardRequired());
            pstmt.setString(15, patient.getTypeOfWard());
            pstmt.setString(16, patient.getInsuranceProvider());
            pstmt.setString(17, patient.getCompanyName());
            pstmt.setString(18, patient.getIdCard());
            pstmt.setBoolean(19, patient.isArchive());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting patient: " + e.getMessage());
            return false;
        }
    }
    
    // Fetch user data from the database
    public Object[][] fetchPatientsData() {
        ArrayList<Object[]> patientData = new ArrayList<>();

        String selectQuery = "SELECT patient_id, first_name, middle_name, last_name, diagnosis, archive FROM patients ORDER BY id DESC";

        try (Connection conn = connectToDatabase(); PreparedStatement pstmt = conn.prepareStatement(selectQuery); ResultSet rs = pstmt.executeQuery()) {

            patientData.clear(); // clear list first

            while (rs.next()) {
                String patient_id = rs.getString("patient_id");
                String first_name = rs.getString("first_name");
                String middle_name = rs.getString("middle_name");
                String last_name = rs.getString("last_name");
                String diagnosis = rs.getString("diagnosis");
                boolean archive = rs.getBoolean("archive");
                
                String isHaveMidle = middle_name.isEmpty() ? " " : " " + middle_name + " ";
                
                if(!archive) {
                    patientData.add(new Object[]{patient_id, first_name + isHaveMidle + last_name, diagnosis});
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching patient data: " + e.getMessage());
        }

        // Convert List to a array
        return patientData.toArray(new Object[0][0]); // Conversion to Object[][] for JTable compatibility
    }
    
    // Method to archive a staff from the database
    public boolean archivePatient(String patientId) {
        String updateQuery = "UPDATE patients SET archive = ? WHERE patient_id = ?";

        try (Connection conn = connectToDatabase(); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            pstmt.setBoolean(1, true);
            pstmt.setString(2, patientId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Return true if the patient was archiving successfully
        } catch (SQLException e) {
            System.err.println("Error archiving patient: " + e.getMessage());
            return false;
        }
    }
    
    // Fetch user data from the database
    public Object[][] fetchPatientPrescription(String patient_id) {
        ArrayList<Object[]> patientData = new ArrayList<>();
        
        // SQL query to fetch data
        String query = "SELECT patient_id, drug_name, drug_name, quantity, unit_price FROM patients_prescription WHERE patient_id = ?";

        try (Connection conn = connectToDatabase(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, patient_id);

            patientData.clear(); // clear list first
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String patientId = rs.getString("patient_id");
                    String drugCode = rs.getString("drug_code");
                    String drugName = rs.getString("drug_name");
                    int quantity = rs.getInt("quantity");
                    double unitPrice = rs.getDouble("unit_price");
                    double totalCost = quantity * unitPrice;

                    // Print row data
                    patientData.add(new Object[]{drugCode, drugName, quantity, unitPrice, totalCost});
                } else {
                    System.out.println("User not found.");
                    return null;  // Return null if no user found
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;  // Return null in case of any SQL error
        }
        
        // Convert List to a array
        return patientData.toArray(new Object[0][0]); // Conversion to Object[][] for JTable compatibility
    }
    
    public boolean insertPatientPrescription(String patientId, String drugCode, String drugName, int quantity, Double unitPrice) {
        // SQL INSERT query
        String query = "INSERT INTO patients_prescription (patient_id, drug_code, drug_name, quantity, unit_price) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = connectToDatabase();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            // Set values for placeholders
            preparedStatement.setString(1, patientId);
            preparedStatement.setString(2, drugCode);
            preparedStatement.setString(3, drugName);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setDouble(5, unitPrice);

            // Execute the INSERT query
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            System.err.println("Error inserting patient: " + e.getMessage());
            return false;
        }
    }
}

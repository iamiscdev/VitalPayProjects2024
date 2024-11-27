package com.tip.edu.cs21s3.b24.view;

import javax.swing.*;
import java.awt.*;

public class RegStaff extends JFrame {

    private JTextField firstname;
    private JTextField lastname;
    private JTextField address;
    private JTextField username;
    private JPasswordField password;
    private JComboBox<String> role;
    private JButton savedetails;
    private JButton backButton; // Declare back button

    public RegStaff() {
        setTitle("Vital Pay");
        setSize(500, 500);  // Adjusted size to fit the extra fields
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("VitalPay", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(new Color(0x1961dd));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(title);

        titlePanel.add(Box.createVerticalStrut(10));

        JLabel staffRegLabel = new JLabel("Staff Registration", SwingConstants.CENTER);
        staffRegLabel.setFont(new Font("Arial", Font.BOLD, 20));
        staffRegLabel.setForeground(new Color(0x1961dd));
        staffRegLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(staffRegLabel);

        add(titlePanel, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2, 10, 10)); // 6 rows, 2 columns to fit new fields
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // First Name
        inputPanel.add(new JLabel("First Name:", SwingConstants.LEFT));
        firstname = new JTextField();
        firstname.setPreferredSize(new Dimension(200, 30));
        inputPanel.add(firstname);

        // Last Name
        inputPanel.add(new JLabel("Last Name:", SwingConstants.LEFT));
        lastname = new JTextField();
        lastname.setPreferredSize(new Dimension(200, 30));
        inputPanel.add(lastname);

        // Address
        inputPanel.add(new JLabel("Street Address:", SwingConstants.LEFT));
        address = new JTextField();
        address.setPreferredSize(new Dimension(200, 30));
        inputPanel.add(address);

        // Username
        inputPanel.add(new JLabel("Username:", SwingConstants.LEFT));
        username = new JTextField();
        username.setPreferredSize(new Dimension(200, 30));
        inputPanel.add(username);

        // Password
        inputPanel.add(new JLabel("Password:", SwingConstants.LEFT));
        password = new JPasswordField();
        password.setPreferredSize(new Dimension(200, 30));
        inputPanel.add(password);

        // Role
        inputPanel.add(new JLabel("Role:", SwingConstants.LEFT));
        String[] roles = {"Nurse", "Doctor", "Admin"}; // Adjust roles if necessary
        role = new JComboBox<>(roles);
        role.setPreferredSize(new Dimension(200, 30));
        inputPanel.add(role);

        add(inputPanel, BorderLayout.CENTER);

        // Save Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));  // Horizontal flow layout for buttons

        // Save Details Button
        savedetails = new JButton("Save Details");
        savedetails.setPreferredSize(new Dimension(110, 40));
        savedetails.setFocusable(false);
        buttonPanel.add(savedetails);

        // Back Button
        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(110, 40));
        backButton.setFocusable(false);
        buttonPanel.add(backButton);  // Add back button to the panel

        add(buttonPanel, BorderLayout.SOUTH);

        // Action for Save Details
        savedetails.addActionListener(e -> {
            String firstName = firstname.getText().trim();
            String lastName = lastname.getText().trim();
            String addressText = address.getText().trim();
            String selectedRole = (String) role.getSelectedItem();
            String userNameText = username.getText().trim();
            String passwordText = new String(password.getPassword()).trim();
            
            // You can process this information further (e.g., saving it to a database or using it elsewhere)
            System.out.println("Saving details for: " + firstName + " " + lastName + ", Role: " + selectedRole);
        });

        // Action for Back Button
        backButton.addActionListener(e -> {
            // Go back to the previous screen or close the current frame
            //new VitalPayAdmin().setVisible(true);
            this.setVisible(false);
        });
    }

    public static void main(String[] args) {
        new RegStaff().setVisible(true);
    }
}

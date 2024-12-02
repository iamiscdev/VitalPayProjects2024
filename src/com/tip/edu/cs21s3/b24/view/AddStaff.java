package com.tip.edu.cs21s3.b24.view;

import com.tip.edu.cs21s3.b24.controller.UserDBController;
import com.tip.edu.cs21s3.b24.dialog.CustomDialog;
import com.tip.edu.cs21s3.b24.model.Constants;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class AddStaff extends JFrame {

    private JTextField firstname;
    private JTextField lastname;
    private JTextField address;
    private JPasswordField password;
    private JPasswordField confirmpassword;
    private JComboBox<String> role;
    private JButton savedetails;
    private JButton backButton;

    private UserDBController db;

    public AddStaff() {

        db = new UserDBController();

        setTitle("Vital Pay - Add Staff");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        
        // Title Panel (same style as in VitalPayLogin and VitalPayAdmin)
        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 150, 136), getWidth(), 0, new Color(115, 147, 179));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setPreferredSize(new Dimension(getWidth(), 100));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(35, 10, 20, 10));

        JLabel titleLabel = new JLabel("Staff Registration");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 21));
        titleLabel.setForeground(Constants.TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(titleLabel);

        add(titlePanel, BorderLayout.NORTH);

        // Input Panel (matching the style of VitalPayAdmin)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2, 10, 10)); // 7 rows, 2 columns
        inputPanel.setBackground(Constants.BACKGROUND_COLOR);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // First Name
        inputPanel.add(new JLabel("First Name:", SwingConstants.LEFT));
        firstname = new JTextField();
        firstname.setPreferredSize(new Dimension(200, 30));
        firstname.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1)); // Set teal green border for JTextField
        inputPanel.add(firstname);

        // Last Name
        inputPanel.add(new JLabel("Last Name:", SwingConstants.LEFT));
        lastname = new JTextField();
        lastname.setPreferredSize(new Dimension(200, 30));
        lastname.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1)); // Set teal green border for JTextField
        inputPanel.add(lastname);

        // Address
        inputPanel.add(new JLabel("Address:", SwingConstants.LEFT));
        address = new JTextField();
        address.setPreferredSize(new Dimension(200, 30));
        address.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1)); // Set teal green border for JTextField
        inputPanel.add(address);

        // Password
        inputPanel.add(new JLabel("Password:", SwingConstants.LEFT));
        password = new JPasswordField();
        password.setPreferredSize(new Dimension(200, 30));
        password.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1)); // Set teal green border for JTextField
        inputPanel.add(password);

        // Confirm Password
        inputPanel.add(new JLabel("Confirm Password:", SwingConstants.LEFT));
        confirmpassword = new JPasswordField();
        confirmpassword.setPreferredSize(new Dimension(200, 30));
        confirmpassword.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1)); // Set teal green border for JTextField
        inputPanel.add(confirmpassword);
        
        // Role
        inputPanel.add(new JLabel("Role:", SwingConstants.LEFT));
        String[] roles = { "Doctor", "Nurse", "Admin" };
        role = new JComboBox<>(roles);
        role.setPreferredSize(new Dimension(200, 30));
        role.setBackground(Constants.PRIMARY_COLOR); // Set background color of JComboBox to Teal
        role.setForeground(Constants.TEXT_COLOR);  // Set text color for items in JComboBox

        // Customize the List (inside JComboBox)
        role.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton arrowButton = super.createArrowButton();
                arrowButton.setBackground(Constants.PRIMARY_COLOR);  // Set arrow button background to match
                return arrowButton;
            }
        });

        // Set the divider color (line between items) to teal green
        role.setBorder(BorderFactory.createLineBorder(Constants.PRIMARY_COLOR));

        inputPanel.add(role);

        add(inputPanel, BorderLayout.CENTER);

        // Button Panel (consistent with buttons in other UIs)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Constants.BACKGROUND_COLOR);

        // Save Details Button
        savedetails = new JButton("Save");
        savedetails.setPreferredSize(new Dimension(120, 40));
        savedetails.setBackground(Constants.PRIMARY_COLOR);
        savedetails.setForeground(Constants.TEXT_COLOR);
        savedetails.setFocusPainted(false);
        savedetails.setFont(new Font("Poppins", Font.BOLD, 14));
        buttonPanel.add(savedetails);

        // Back Button
        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.setBackground(Constants.SECONDARY_COLOR);
        backButton.setForeground(Constants.TEXT_COLOR);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Poppins", Font.BOLD, 14));
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Action for Save Details
        savedetails.addActionListener(e -> {
            String firstName = firstname.getText().trim();
            String lastName = lastname.getText().trim();
            String addressText = address.getText().trim();
            String passwordText = new String(password.getPassword()).trim();
            String confirmpasswordText = new String(confirmpassword.getPassword()).trim();
            String selectedRole = (String) role.getSelectedItem();

            if (passwordText.equals(confirmpasswordText)) {
                
                String user_id = generateUserId(selectedRole, 6);
                
                if (db.addUserStaff(user_id, firstName, lastName, addressText, passwordText, selectedRole)) {

                    CustomDialog.showMessage(
                            this,
                            "New staff added successfully<br><br>User id: " + user_id + "<br>Password: "+ passwordText + "<br><br>Please save your details!" ,
                            "Success",
                            "success"
                    );

                    this.setVisible(false);
                    AdminDashboard.reloadTableData();
                } else {

                    CustomDialog.showMessage(
                            this,
                            "Adding staff unsuccessful",
                            "Error",
                            "error"
                    );
                }

            } else {
                
                CustomDialog.showMessage(
                        this,
                        "Password doesn't match",
                        "Error",
                        "error"
                );
            }
        });

        // Action for Back Button
        backButton.addActionListener(e -> {
            this.setVisible(false);  // Close the current frame
        });
    }

    private String generateUserId(String role, int length) {
        String characters = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        switch (role) {
            case "Admin":
                sb.append("A-");
                break;
            case "Nurse":
                sb.append("N-");
                break;
            case "Doctor":
                sb.append("D-");
                break;
            default:
                break;
        }

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        new AddStaff().setVisible(true);
    }
}

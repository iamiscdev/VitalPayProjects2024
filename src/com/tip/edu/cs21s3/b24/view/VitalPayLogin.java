package com.tip.edu.cs21s3.b24.view;

import com.tip.edu.cs21s3.b24.controller.UserDBController;
import com.tip.edu.cs21s3.b24.dialog.CustomDialog;
import com.tip.edu.cs21s3.b24.model.Constants;
import com.tip.edu.cs21s3.b24.model.UserStaffModel;
import com.tip.edu.cs21s3.b24.model.UserSession;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;

public final class VitalPayLogin extends JFrame {

    private final JTextField userIdField;
    private final JPasswordField passwordField;
    private final JButton loginButton;

    public VitalPayLogin() {
        // Frame settings
        setTitle("Login - VitalPay");
        setSize(400, 400);
        setBackground(Constants.BACKGROUND_COLOR);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with light gray background and consistent spacing
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Constants.BACKGROUND_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        add(mainPanel);

        // Title Label with primary color
        JLabel titleLabel = new JLabel("Sign In", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 28));
        titleLabel.setBackground(Constants.PRIMARY_COLOR);  // Teal color
        titleLabel.setForeground(Constants.PRIMARY_COLOR);  // Teal color
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Spacer

        // Username field with styled border and placeholder
        userIdField = new JTextField("Username");
        styleTextField(userIdField);
        addPlaceholder(userIdField, "Username");
        mainPanel.add(userIdField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer

        // Password field with styled border and placeholder
        passwordField = new JPasswordField("Password");
        styleTextField(passwordField);
        addPlaceholder(passwordField, "Password");
        mainPanel.add(passwordField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacer

        // Login Button with consistent styling
        loginButton = new JButton("SIGN IN");
        styleButton(loginButton);
        mainPanel.add(loginButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Spacer
        
        // Forgot Password Label with light gray color and cursor change
        JLabel forgotPasswordLabel = new JLabel("Forgot your password?");
        forgotPasswordLabel.setFont(new Font("Poppins", Font.PLAIN, 12));
        forgotPasswordLabel.setForeground(new Color(150, 150, 150)); // Light gray
        forgotPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(forgotPasswordLabel);

        // Add login action listener
        addLoginListener(e -> handleLogin());

        setVisible(true);
    }

    private void handleLogin() {
        String userId = getUserId();
        String password = getPassword();

        UserDBController db = new UserDBController();
        UserStaffModel user = db.getUserStaffByUserPass(userId, password);

        if (user != null) {
            if ("Admin".equals(user.getRole())) {
                UserSession.getInstance().setUserId(String.format("%s %s (%s)", user.getFirstName(), user.getLastName(), userId));
                UserSession.getInstance().setRole(user.getRole());

                this.setVisible(false);
                VitalPayAdmin admin = new VitalPayAdmin();
                admin.setVisible(true);

                CustomDialog.showMessage(
                        this,
                        "Login Successful! Welcome, " + user.getFirstName() + " " + user.getLastName(),
                        "Success",
                        "success"
                );

            } else {
                VitalPayStaff dashboard = new VitalPayStaff(user);
                dashboard.setVisible(true);
            }
        } else {
            CustomDialog.showMessage(
                    this,
                    "Invalid Username or Password",
                    "Error",
                    "error"
            );
        }
    }

    // Style the text fields with consistent border color and font
    private void styleTextField(JTextField field) {
        field.setFont(new Font("Poppins", Font.PLAIN, 14));
        field.setBackground(new Color(240, 255, 240)); // Light greenish background
        field.setForeground(Constants.TEXT_COLOR); // Dark gray text
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Constants.PRIMARY_COLOR), // Teal border
                BorderFactory.createEmptyBorder(10, 15, 10, 15) // Padding inside
        ));
        field.setMaximumSize(new Dimension(300, 40));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Poppins", Font.BOLD, 14));
        button.setBackground(Constants.SECONDARY_COLOR);
        button.setForeground(Constants.TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(300, 40));
    }

    private void addPlaceholder(JTextComponent textField, String placeholder) {
        textField.setText(placeholder);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Constants.TEXT_COLOR_SECONDARY); // Dark gray text
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(new Color(150, 150, 150)); // Placeholder color
                }
            }
        });
    }

    public String getUserId() {
        return userIdField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }
}

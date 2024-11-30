package com.tip.edu.cs21s3.b24.dialog;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomDialog {

    public static void showMessage(Component parent, String message, String title, String type) {
        // Create a custom dialog
        JDialog dialog = new JDialog((Frame) null, title, true);
        dialog.setSize(320, 340);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(parent);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        dialog.add(mainPanel, BorderLayout.CENTER);

        // Icon panel
        JPanel iconPanel = new JPanel();
        iconPanel.setBackground(Color.WHITE);
        JLabel iconLabel = new JLabel();
        switch (type.toLowerCase()) {
            case "success":
                iconLabel.setIcon(UIManager.getIcon("OptionPane.informationIcon")); // Success Icon
                break;
            case "error":
                iconLabel.setIcon(UIManager.getIcon("OptionPane.errorIcon")); // Error Icon
                break;
            case "warning":
                iconLabel.setIcon(UIManager.getIcon("OptionPane.warningIcon")); // Warning Icon
                break;
            default:
                iconLabel.setIcon(UIManager.getIcon("OptionPane.questionIcon")); // Default Icon
        }
        
        iconPanel.add(iconLabel);
        mainPanel.add(iconPanel);

        // Message Label
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setForeground(new Color(50, 50, 50));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacer
        mainPanel.add(messageLabel);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.setBackground(new Color(0, 150, 136)); // Teal color
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        okButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(okButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        mainPanel.add(buttonPanel);

        // Show the dialog
        dialog.setVisible(true);
    }
    
    public static boolean showConfirm(Component parent, String message, String title) {
        // Create a shared atomic variable to store the user's choice
        AtomicBoolean userChoice = new AtomicBoolean(false);

        // Create a custom dialog
        JDialog dialog = new JDialog((Frame) null, title, true);
        dialog.setSize(300, 200);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(parent);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        dialog.add(mainPanel, BorderLayout.CENTER);

        // Message Label
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setForeground(new Color(50, 50, 50));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(messageLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        // Yes Button
        JButton yesButton = new JButton("Yes");
        yesButton.setFont(new Font("Arial", Font.BOLD, 14));
        yesButton.setBackground(new Color(0, 150, 136)); // Teal color
        yesButton.setForeground(Color.WHITE);
        yesButton.setFocusPainted(false);
        yesButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        yesButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        yesButton.addActionListener(e -> {
            userChoice.set(true); // User confirmed
            dialog.dispose();
        });

        // No Button
        JButton noButton = new JButton("No");
        noButton.setFont(new Font("Arial", Font.BOLD, 14));
        noButton.setBackground(Color.RED); // Red color
        noButton.setForeground(Color.WHITE);
        noButton.setFocusPainted(false);
        noButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        noButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        noButton.addActionListener(e -> {
            userChoice.set(false); // User declined
            dialog.dispose();
        });

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        mainPanel.add(buttonPanel);

        // Show the dialog and wait for user action
        dialog.setVisible(true);

        // Return the user's choice
        return userChoice.get();
    }
}
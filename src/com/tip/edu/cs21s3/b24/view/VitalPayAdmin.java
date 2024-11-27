package com.tip.edu.cs21s3.b24.view;

import com.tip.edu.cs21s3.b24.model.UserModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public final class VitalPayAdmin extends JFrame implements ActionListener {

    // Declare instance variables for buttons
    private JButton searchBtn;
    private JButton registerBtn;
    private JButton reportBtn;
    private JButton addPatientBtn;
    private JButton logoutBtn;

    private JLabel userNameValue;
    private JLabel userTypeValue;

    public VitalPayAdmin() {

        // Frame settings
        setTitle("Admin Dashboard - VitalPay");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);  // Increased window size to fit both info panel and table
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        // Initialize and add components
        add(createGradientLabel(), BorderLayout.NORTH);
        add(createButtonPanel(), BorderLayout.WEST);
        add(createUserInfoPanel(), BorderLayout.PAGE_START);  // User info at the top-left
        add(createTablePanel(), BorderLayout.CENTER);  // Table on the right

        setVisible(true);
    }

    // Create gradient title label
    private JLabel createGradientLabel() {
        JLabel gradientLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Set gradient
                GradientPaint gradient = new GradientPaint(0, 0, Color.RED, getWidth(), 0, Color.BLUE);
                g2d.setPaint(gradient);

                // Draw text
                g2d.setFont(new Font("Poppins", Font.BOLD, 30));
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2d.drawString("VitalPay", 0, 30);
            }
        };
        gradientLabel.setBounds(325, 10, 200, 50);
        return gradientLabel;
    }

    // Create button panel on the left side
    public JPanel createButtonPanel() {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(5, 1, 10, 10));
        btnPanel.setPreferredSize(new Dimension(150, 200));  // Adjusted width
        btnPanel.setBackground(new Color(240, 240, 240));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Padding around table

        // Buttons
        searchBtn = createButton("Search");
        registerBtn = createButton("Register (Staff)");
        reportBtn = createButton("General Report");
        addPatientBtn = createButton("Add Patient");
        logoutBtn = createButton("Logout");

        // Action listeners
        registerBtn.addActionListener(this);
        addPatientBtn.addActionListener(this);
        logoutBtn.addActionListener(this);

        // Add buttons to panel
        btnPanel.add(searchBtn);
        btnPanel.add(registerBtn);
        btnPanel.add(reportBtn);
        btnPanel.add(addPatientBtn);
        btnPanel.add(logoutBtn);

        return btnPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerBtn) {
            RegStaff staffreg = new RegStaff();
            //this.setVisible(false);
            staffreg.setVisible(true);
        } else if (e.getSource() == addPatientBtn) {
            AddPatient addpatient = new AddPatient();
            //this.setVisible(false);
            addpatient.setVisible(true);
        } else if (e.getSource() == logoutBtn) {
            VitalPayLogin login = new VitalPayLogin();
            RegStaff staffreg = new RegStaff();
            AddPatient addpatient = new AddPatient();
            this.setVisible(false);
            addpatient.setVisible(false);
            staffreg.setVisible(false);
            login.setVisible(true);
        }
    }

    // Create User Info Panel at the top-left
    private JPanel createUserInfoPanel() {

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(null);
        infoPanel.setBackground(new Color(115, 147, 179));
        infoPanel.setPreferredSize(new Dimension(250, 110));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Padding around the panel

        // Labels for user info
        JLabel unLabel = createLabel("Name");
        unLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        unLabel.setForeground(Color.WHITE);
        unLabel.setBounds(290, 50, 80, 20);

        userNameValue = new JLabel("*********");
        userNameValue.setFont(new Font("Arial", Font.PLAIN, 14));
        userNameValue.setForeground(Color.YELLOW);
        userNameValue.setBounds(380, 50, 600, 20);

        JLabel userTypeLabel = createLabel("Role");
        userTypeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userTypeLabel.setForeground(Color.WHITE);
        userTypeLabel.setBounds(290, 80, 80, 20);

        userTypeValue = new JLabel("********");
        userTypeValue.setFont(new Font("Arial", Font.PLAIN, 14));
        userTypeValue.setForeground(Color.YELLOW);
        userTypeValue.setBounds(380, 80, 600, 20);

        // Add labels to panel
        infoPanel.add(unLabel);
        infoPanel.add(userNameValue);
        infoPanel.add(userTypeLabel, BorderLayout.CENTER);
        infoPanel.add(userTypeValue, BorderLayout.CENTER);

        return infoPanel;
    }

    // Create table panel for displaying a table of users or data
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());  // Use BorderLayout for better table alignment
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Padding around table

        // Sample column names for the table
        String[] columnNames = {"ID", "Username", "Role"};

        Object[][] data = {
            {1, "john_doe", "Admin"},
            {2, "jane_doe", "Staff"},
            {3, "bob_smith", "Doctor"}
        };

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);  // Add table to the center of the panel

        return tablePanel;
    }

    // Setter for Name
    public void setName(String name) {
        userNameValue.setText(name);
    }

    // Setter for Role
    public void setRole(String role) {
        userTypeValue.setText(role);
    }

    // Create a button with text and margin
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setMargin(new Insets(10, 20, 10, 20));  // Add padding inside buttons
        return button;
    }

    // Create a label with text and position
    private JLabel createLabel(String text) {
        return createLabel(text, Color.WHITE);
    }

    // Create a label with text and color
    private JLabel createLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));  // Padding inside label
        return label;
    }

}

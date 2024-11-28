package com.tip.edu.cs21s3.b24.view;

import com.tip.edu.cs21s3.b24.controller.UserDBController;
import com.tip.edu.cs21s3.b24.model.UserSession;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public final class VitalPayAdmin extends JFrame implements ActionListener {

    // Declare instance variables for buttons
    private JButton searchBtn;
    private JButton registerBtn;
    private JButton reportBtn;
    private JButton addPatientBtn;
    private JButton logoutBtn;

    private JLabel userNameValue;
    private JLabel userTypeValue;

    public static UserDBController db;
    public static JTable userTable;  // Declare the table for users

    public VitalPayAdmin() {

        db = new UserDBController();

        // Frame settings
        setTitle("Admin Dashboard - VitalPay");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);  // Increased window size to fit both info panel and table
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        // Get username and role from the UserSession singleton
        String username = UserSession.getInstance().getUsername();
        String userRole = UserSession.getInstance().getRole();

        // Initialize and add components
        add(createGradientLabel(), BorderLayout.NORTH);
        add(createButtonPanel(), BorderLayout.WEST);
        add(createUserInfoPanel(username, userRole), BorderLayout.PAGE_START);  // User info at the top-left
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
            AddStaff staffreg = new AddStaff();
            //this.setVisible(false);
            staffreg.setVisible(true);
        } else if (e.getSource() == addPatientBtn) {
            AddPatient addpatient = new AddPatient();
            //this.setVisible(false);
            addpatient.setVisible(true);
        } else if (e.getSource() == logoutBtn) {
            VitalPayLogin login = new VitalPayLogin();
            AddStaff staffreg = new AddStaff();
            AddPatient addpatient = new AddPatient();
            this.setVisible(false);
            addpatient.setVisible(false);
            staffreg.setVisible(false);
            login.setVisible(true);
        }
    }

    // Create User Info Panel at the top-left
    private JPanel createUserInfoPanel(String username, String userRole) {
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

        userNameValue = new JLabel(username);  // Get username from the session
        userNameValue.setFont(new Font("Arial", Font.PLAIN, 14));
        userNameValue.setForeground(Color.YELLOW);
        userNameValue.setBounds(380, 50, 600, 20);

        JLabel userTypeLabel = createLabel("Role");
        userTypeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userTypeLabel.setForeground(Color.WHITE);
        userTypeLabel.setBounds(290, 80, 80, 20);

        userTypeValue = new JLabel(userRole);  // Get user role from the session
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

        // Create a panel to hold the button above the table
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));  // Right-aligned layout for the button panel
        buttonPanel.setBackground(new Color(240, 240, 240));  // Light background for the button panel

        // Create small "Refresh" button and add it to the buttonPanel
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setFocusable(false);
        refreshBtn.setMargin(new Insets(3, 8, 3, 8));  // Small padding for the button
        refreshBtn.addActionListener(e -> reloadTableData());  // Add action to refresh table data

        // Add the refresh button to the buttonPanel
        buttonPanel.add(refreshBtn);

        // Column names for the table
        String[] columnNames = {"User ID", "Username", "Role", "Actions"};

        // Fetch data from the database
        Object[][] data = db.fetchUserData();

        // Create a table model with an additional column for buttons
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;  // Prevent editing
            }
        };

        // Create the table with the custom model
        userTable = new JTable(tableModel);

        // Set custom editor and renderer for the Actions column
        userTable.getColumn("Actions").setCellRenderer(new ButtonEditorRenderer());
        userTable.getColumn("Actions").setCellEditor(new ButtonEditorRenderer());

        userTable.setRowHeight(35);  // Set a row height large enough to fit the buttons

        // Wrap the table in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(userTable);

        // Add buttonPanel above the table
        tablePanel.add(buttonPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);  // Add the table below the button

        return tablePanel;
    }

    // Custom cell editor and renderer for buttons
    public static class ButtonEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

        private final JPanel renderPanel;
        private final JPanel editPanel;
        private final JButton editButton;
        private final JButton archiveButton;
        private int currentRow; // Track the current row being edited

        public ButtonEditorRenderer() {
            // Initialize components for rendering
            renderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            editPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            // Buttons for display
            editButton = new JButton("Edit");
            archiveButton = new JButton("Archive");

            // Add buttons to both panels
            renderPanel.add(new JButton("Edit")); // Non-functional, for appearance only
            renderPanel.add(new JButton("Archive"));
            editPanel.add(editButton);
            editPanel.add(archiveButton);

            // Add action listeners for the edit panel buttons
            editButton.addActionListener(e -> handleEditAction(currentRow));
            archiveButton.addActionListener(e -> handleArchiveAction(currentRow));
        }

        private void handleEditAction(int row) {
            System.out.println("Editing user at row: " + row);
            fireEditingStopped(); // Commit edit
        }

        private void handleArchiveAction(int row) {
            System.out.println("Archiving user at row: " + row);
            fireEditingStopped(); // Commit edit
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Style the panel based on selection
            if (isSelected) {
                renderPanel.setBackground(table.getSelectionBackground());
            } else {
                renderPanel.setBackground(table.getBackground());
            }
            return renderPanel; // Return the renderer panel
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentRow = table.convertRowIndexToModel(row); // Get the correct model row
            return editPanel; // Return the editor panel
        }

        @Override
        public Object getCellEditorValue() {
            return null; // No specific value needed
        }
    }

    // Reload table data when required (e.g., after an update or action)
    public static void reloadTableData() {

        // Column names for the table
        String[] columnNames = {"User ID", "Username", "Role", "Actions"};

        // Fetch updated data
        Object[][] updatedData = db.fetchUserData();

        // Update table model with new data
        userTable.setModel(new DefaultTableModel(updatedData, columnNames));

        // Set custom editor and renderer for the Actions column
        userTable.getColumn("Actions").setCellRenderer(new ButtonEditorRenderer());
        userTable.getColumn("Actions").setCellEditor(new ButtonEditorRenderer());

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

package com.tip.edu.cs21s3.b24.view;

import com.tip.edu.cs21s3.b24.controller.UserDBController;
import com.tip.edu.cs21s3.b24.dialog.CustomDialog;
import com.tip.edu.cs21s3.b24.model.Constants;
import com.tip.edu.cs21s3.b24.model.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public final class AdminDashboard extends JFrame implements ActionListener {

    private JButton searchBtn, registerBtn, reportBtn, addPatientBtn, logoutBtn;
    private JLabel userNameValue, userTypeValue;

    public static UserDBController db;
    public static JTable userTable;

    // Class-level references to track open frames
    private AddStaff addStaffFrame;
    private AddPatient addPatientFrame;
    private VitalPayPatientBilling addPatientMedsFrame;
    private VitalPayReport addReportFrame;

    private static boolean isStaffView = true;

    public AdminDashboard() {
        db = new UserDBController();

        // Frame settings
        setTitle("Admin Dashboard - VitalPay");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700); // Adjusted window size
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        // Get username and role from the UserSession
        String username = UserSession.getInstance().getUserId();
        String userRole = UserSession.getInstance().getRole();

        // Add components
        //add(createGradientLabel(), BorderLayout.NORTH);
        //add(createHeaderPanel(), BorderLayout.NORTH); // Header with gradient title
        add(createButtonPanel(), BorderLayout.WEST); // Sidebar buttons
        add(createUserInfoPanel(username, userRole), BorderLayout.PAGE_START); // User info
        add(createTablePanel(), BorderLayout.CENTER); // Table area

        setVisible(true);
    }

    // Create sidebar button panel
    private JPanel createButtonPanel() {
        JPanel btnPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        btnPanel.setPreferredSize(new Dimension(180, getHeight()));
        btnPanel.setBackground(Constants.BACKGROUND_COLOR);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Buttons
        searchBtn = createStyledButton("Search");
        registerBtn = createStyledButton("Register (Staff)");
        reportBtn = createStyledButton("General Report");
        addPatientBtn = createStyledButton("Add Patient");
        logoutBtn = createStyledButton("Logout");

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

    // Create user info panel at the top
    private JPanel createUserInfoPanel(String username, String userRole) {
        // Create panel with custom background gradient
        JPanel infoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 150, 136), getWidth(), 0, new Color(115, 147, 179));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Stack components vertically
        infoPanel.setPreferredSize(new Dimension(getWidth(), 130));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
        infoPanel.setOpaque(true);

        // Title label
        JLabel titleLabel = new JLabel("VitalPay Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 21));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align

        // Name and role content in a panel
        JPanel userDetailsPanel = new JPanel();
        userDetailsPanel.setLayout(new GridLayout(2, 1)); // 2 rows for Name and Role
        userDetailsPanel.setOpaque(false); // Transparent background

        // Name row
        JPanel nameRow = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center align horizontally
        nameRow.setOpaque(false);
        JLabel nameLabel = new JLabel("Name (User Id): ");
        nameLabel.setFont(new Font(Constants.FONT_STYLE, Font.PLAIN, 14));
        nameLabel.setForeground(Color.WHITE);
        
        JLabel userNameValue = new JLabel(username);
        userNameValue.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 14));
        userNameValue.setForeground(Color.YELLOW);
        nameRow.add(nameLabel);
        nameRow.add(userNameValue);

        // Role row
        JPanel roleRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        roleRow.setOpaque(false);
        JLabel roleLabel = new JLabel("Role: ");
        roleLabel.setFont(new Font(Constants.FONT_STYLE, Font.PLAIN, 14));
        roleLabel.setForeground(Color.WHITE);
        JLabel userRoleValue = new JLabel(userRole);
        userRoleValue.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 14));
        userRoleValue.setForeground(Color.YELLOW);
        roleRow.add(roleLabel);
        roleRow.add(userRoleValue);

        // Add rows to the user details panel
        userDetailsPanel.add(nameRow);
        userDetailsPanel.add(roleRow);

        // Add components to the infoPanel
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(10)); // Add space between title and details
        infoPanel.add(userDetailsPanel);

        return infoPanel;
    }

    // Create table panel
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Refresh button
        JButton viewStaffBtn = createStyledButton2("View Staff");
        viewStaffBtn.addActionListener(e
                -> reloadStaffTableData()
        );

        JButton viewPatientBtn = createStyledButton2("View Patient");
        viewPatientBtn.addActionListener(e
                -> reloadPatientTableData()
        );

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(viewStaffBtn);
        buttonPanel.add(viewPatientBtn);
        
        tablePanel.add(buttonPanel, BorderLayout.NORTH);

        if (isStaffView) {
            // Table
            String[] columnNames = {"User ID", "Name", "Role", "Actions"};
            Object[][] data = db.fetchUserStaffData();
            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 3; // Only Actions column editable
                }
            };
            userTable = new JTable(tableModel);
            userTable.getTableHeader().setReorderingAllowed(false);
        } else {
            // Table
            String[] columnNames = {"Patient ID", "Name", "Diagnosis", "Actions"};
            Object[][] data = db.fetchPatientsData();
            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 3; // Only Actions column editable
                }
            };
            userTable = new JTable(tableModel);
            userTable.getTableHeader().setReorderingAllowed(false);
        }

        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Set row height for better readability
        userTable.setRowHeight(35);

        // Customize table header
        JTableHeader header = userTable.getTableHeader();
        header.setBackground(Constants.PRIMARY_COLOR); // Teal header background
        header.setForeground(Constants.TEXT_COLOR); // White text
        header.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 14));

        // Add custom button renderer/editor for Actions column
        userTable.getColumn("Actions").setCellRenderer(new ButtonEditorRenderer());
        userTable.getColumn("Actions").setCellEditor(new ButtonEditorRenderer());

        JScrollPane scrollPane = new JScrollPane(userTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerBtn) {

            // Open AddStaff and track the instance
            if (addStaffFrame == null || !addStaffFrame.isShowing()) {
                addStaffFrame = new AddStaff();
                addStaffFrame.setVisible(true);
            }

        } else if (e.getSource() == addPatientBtn) {
            // Open AddPatient and track the instance
            if (addPatientFrame == null || !addPatientFrame.isShowing()) {
                addPatientFrame = new AddPatient();
                addPatientFrame.setVisible(true);
            }

        } else if (e.getSource() == logoutBtn) {
        
            // Confirm deletion
            boolean confirmed = CustomDialog.showConfirm(
                    null,
                    "Are you sure you want to log out?",
                    "Logout Confirmation"
            );

            if (confirmed) {
                // Dispose the current frame
                this.dispose();

                // Dispose any other open frames
                if (addStaffFrame != null) {
                    addStaffFrame.dispose();
                }

                if (addPatientFrame != null) {
                    addPatientFrame.dispose();
                }

                // Open the login frame
                new VitalPayLogin().setVisible(true);
            }

        }
    }

    // Reload table data
    public static void reloadTableData() {
        if (isStaffView) {
            reloadStaffTableData();
        } else {
            reloadPatientTableData();
        }
    }

    // Reload Staff table data
    public static void reloadStaffTableData() {

        isStaffView = true;

        String[] columnNames = {"User ID", "Name", "Role", "Actions"};
        Object[][] updatedData = db.fetchUserStaffData();

        DefaultTableModel tableModel = new DefaultTableModel(updatedData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only Actions column editable
            }
        };

        userTable.setModel(tableModel);
        userTable.getColumn("Actions").setCellRenderer(new ButtonEditorRenderer());
        userTable.getColumn("Actions").setCellEditor(new ButtonEditorRenderer());
    }

    // Reload Patient table data
    public static void reloadPatientTableData() {

        isStaffView = false;

        String[] columnNames = {"Patient ID", "Name", "Diagnosis", "Actions"};
        Object[][] updatedData = db.fetchPatientsData();

        DefaultTableModel tableModel = new DefaultTableModel(updatedData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only Actions column editable
            }
        };

        userTable.setModel(tableModel);
        userTable.getColumn("Actions").setCellRenderer(new ButtonEditorRenderer());
        userTable.getColumn("Actions").setCellEditor(new ButtonEditorRenderer());
    }

    // Create a styled button
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 14));
        button.setBackground(Constants.PRIMARY_COLOR); // Teal background
        button.setForeground(Constants.TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Create a styled button
    private JButton createStyledButton2(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 12));
        button.setPreferredSize(new Dimension(110, 30));
        button.setBackground(Constants.PRIMARY_COLOR); // Teal background
        button.setForeground(Constants.TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

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

            // Initialize buttons
            editButton = createStyledButton(isStaffView ? "Edit": "Billing");
            archiveButton = createStyledButton("Archive");

            // Add buttons to the edit panel
            editPanel.add(editButton);
            editPanel.add(archiveButton);

            // Add action listeners for buttons
            editButton.addActionListener(e -> handleEditAction(currentRow));
            archiveButton.addActionListener(e -> handleArchiveAction(currentRow));

            // Create render buttons
            renderPanel.add(createStyledButton(isStaffView ? "Edit": "Billing", true));
            renderPanel.add(createStyledButton("Archive", true));
        }

        private void handleEditAction(int row) {
            String Id = userTable.getValueAt(row, 0).toString(); // Get the value in in column 0
            
            if (isStaffView) {
                System.out.println("Editing user at row: " + row);
            } else {
                System.out.println("Editing patient at row: " + row);
                new VitalPayPatientBilling(Id).setVisible(true);
            }

            fireEditingStopped(); // Commit edit and close editor
        }

        private void handleArchiveAction(int row) {
            String Id = userTable.getValueAt(row, 0).toString(); // Get the value in in column 0

            if (isStaffView) {
                System.out.println("Archiving user with ID: " + Id);

                // Confirm deletion
                boolean confirmed = CustomDialog.showConfirm(
                        null,
                        "Are you sure you want to archive this patient?",
                        "Archiving Confirmation"
                );

                if (confirmed) {
                    System.out.println("User archiving confirmed.");

                    boolean success = db.archiveUserStaff(Id); // Call the delete method with user_id
                    if (success) {

                        CustomDialog.showMessage(
                                null,
                                "User archiving successfully.",
                                "Success",
                                "success"
                        );

                        reloadTableData(); // Refresh the table
                    } else {

                        CustomDialog.showMessage(
                                null,
                                "Failed to achiving user.",
                                "error",
                                "error"
                        );

                    }
                } else {
                    System.out.println("User archiving canceled.");
                }
            } else {
                System.out.println("Archiving patient with ID: " + Id);

                // Confirm deletion
                boolean confirmed = CustomDialog.showConfirm(
                        null,
                        "Are you sure you want to archive this user?",
                        "Archiving Confirmation"
                );

                if (confirmed) {
                    System.out.println("Patient archiving confirmed.");

                    boolean success = db.archivePatient(Id); // Call the delete method with user_id
                    if (success) {

                        CustomDialog.showMessage(
                                null,
                                "Patient archiving successfully.",
                                "Success",
                                "success"
                        );

                        reloadTableData(); // Refresh the table
                    } else {

                        CustomDialog.showMessage(
                                null,
                                "Failed to achiving patient.",
                                "error",
                                "error"
                        );
                    }
                } else {
                    System.out.println("Patient archiving canceled.");
                }
            }

            fireEditingStopped(); // Close the cell editor
        }

        // Create a styled button with enabled/disabled options
        private JButton createStyledButton(String text, boolean enabled) {
            JButton button = new JButton(text);
            button.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 12));
            button.setBackground(Constants.PRIMARY_COLOR);
            button.setForeground(Constants.TEXT_COLOR);
            button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding inside the button
            button.setFocusable(false);
            button.setEnabled(enabled);
            button.setCursor(new Cursor(enabled ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
            return button;
        }

        // Overloaded method for enabled buttons (default to enabled = true)
        private JButton createStyledButton(String text) {
            return createStyledButton(text, true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Ensure the button text color stays white when selected or unselected
            JButton renderButton = (JButton) renderPanel.getComponent(0);  // Get the Edit button
            renderButton.setForeground(Constants.TEXT_COLOR); // Keep text white for both selected and unselected

            renderButton = (JButton) renderPanel.getComponent(1);  // Get the Archive button
            renderButton.setForeground(Constants.TEXT_COLOR); // Keep text white for both selected and unselected

            // Style the panel based on selection (adjust button background based on selection)
            if (isSelected) {
                renderPanel.setBackground(table.getSelectionBackground());
            } else {
                renderPanel.setBackground(table.getBackground());
            }
            return renderPanel;
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
}

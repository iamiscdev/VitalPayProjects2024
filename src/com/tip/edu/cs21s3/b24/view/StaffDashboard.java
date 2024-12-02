package com.tip.edu.cs21s3.b24.view;

import com.tip.edu.cs21s3.b24.controller.UserDBController;
import com.tip.edu.cs21s3.b24.dialog.CustomDialog;
import com.tip.edu.cs21s3.b24.model.Constants;
import com.tip.edu.cs21s3.b24.model.UserSession;
import com.tip.edu.cs21s3.b24.model.UserStaffModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public final class StaffDashboard extends JFrame implements ActionListener {

    private JButton addPatientBtn, generateBillBtn, logoutBtn;
    private JLabel userNameValue, userTypeValue;

    private static UserDBController db;
    private static JTable patientTable;

    public StaffDashboard(UserStaffModel staff) {
        db = new UserDBController();

        // Frame settings
        setTitle("Staff Dashboard - VitalPay");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700); // Adjusted window size
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        // Get username and role from the UserSession
        String username = UserSession.getInstance().getUserId();
        String userRole = UserSession.getInstance().getRole();

        // Add components
        add(createButtonPanel(), BorderLayout.WEST); // Sidebar buttons
        add(createUserInfoPanel(username, userRole), BorderLayout.PAGE_START); // User info
        add(createTablePanel(), BorderLayout.CENTER); // Table area

        setVisible(true);
    }

    // Create sidebar button panel
    private JPanel createButtonPanel() {
        JPanel btnPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        btnPanel.setPreferredSize(new Dimension(180, getHeight()));
        btnPanel.setBackground(Constants.BACKGROUND_COLOR);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Buttons
        addPatientBtn = createStyledButton("Add Patient");
        generateBillBtn = createStyledButton("Generate Bill");
        logoutBtn = createStyledButton("Logout");

        // Action listeners
        addPatientBtn.addActionListener(this);
        generateBillBtn.addActionListener(this);
        logoutBtn.addActionListener(this);

        // Add buttons to panel
        btnPanel.add(addPatientBtn);
        btnPanel.add(generateBillBtn);
        btnPanel.add(logoutBtn);

        return btnPanel;
    }

    // Create user info panel at the top
    private JPanel createUserInfoPanel(String username, String userRole) {
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
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setPreferredSize(new Dimension(getWidth(), 130));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.setOpaque(true);

        JLabel titleLabel = new JLabel("VitalPay Staff Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 21));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel userDetailsPanel = new JPanel(new GridLayout(2, 1));
        userDetailsPanel.setOpaque(false);

        JPanel nameRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        nameRow.setOpaque(false);
        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setFont(new Font(Constants.FONT_STYLE, Font.PLAIN, 14));
        nameLabel.setForeground(Color.WHITE);
        JLabel userNameValue = new JLabel(username);
        userNameValue.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 14));
        userNameValue.setForeground(Color.YELLOW);
        nameRow.add(nameLabel);
        nameRow.add(userNameValue);

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

        userDetailsPanel.add(nameRow);
        userDetailsPanel.add(roleRow);

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(userDetailsPanel);

        return infoPanel;
    }

    // Create table panel
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Patient ID", "Name", "Diagnosis", "Actions"};
        Object[][] data = db.fetchPatientsData();
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        patientTable = new JTable(tableModel);

        patientTable.setRowHeight(35);
        JTableHeader header = patientTable.getTableHeader();
        header.setBackground(new Color(0, 150, 136));
        header.setForeground(Constants.TEXT_COLOR);
        header.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 14));

        // Add custom button renderer/editor for Actions column
        patientTable.getColumn("Actions").setCellRenderer(new ButtonEditorRenderer());
        patientTable.getColumn("Actions").setCellEditor(new ButtonEditorRenderer());

        JScrollPane scrollPane = new JScrollPane(patientTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 14));
        button.setBackground(Constants.PRIMARY_COLOR);
        button.setForeground(Constants.TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Reload Patient table data
    public static void reloadPatientTableData() {

        String[] columnNames = {"Patient ID", "Name", "Diagnosis", "Actions"};
        Object[][] updatedData = db.fetchPatientsData();

        DefaultTableModel tableModel = new DefaultTableModel(updatedData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only Actions column editable
            }
        };

        patientTable.setModel(tableModel);
        patientTable.getColumn("Actions").setCellRenderer(new ButtonEditorRenderer());
        patientTable.getColumn("Actions").setCellEditor(new ButtonEditorRenderer());
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
            editButton = createStyledButton("Precribe");
            archiveButton = createStyledButton("Archive");

            // Add buttons to the edit panel
            editPanel.add(editButton);
            editPanel.add(archiveButton);

            // Add action listeners for buttons
            editButton.addActionListener(e -> handleEditAction(currentRow));
            archiveButton.addActionListener(e -> handleArchiveAction(currentRow));

            // Create render buttons (non-clickable)
            renderPanel.add(createStyledButton("Precribe", false));
            renderPanel.add(createStyledButton("Archive", false));
        }

        private void handleEditAction(int row) {
            System.out.println("Editing patient at row: " + row);
            new VitalPayPatientBilling().setVisible(true);

            fireEditingStopped(); // Commit edit and close editor
        }

        private void handleArchiveAction(int row) {
            String Id = patientTable.getValueAt(row, 0).toString(); // Get the value in in column 0

            System.out.println("Archiving patient with ID: " + Id);

            // Confirm deletion
            boolean confirmed = CustomDialog.showConfirm(
                    null,
                    "Are you sure you want to archive this patient?",
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

                    reloadPatientTableData(); // Refresh the table
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPatientBtn) {
            new AddPatient().setVisible(true);
        } else if (e.getSource() == logoutBtn) {
            dispose();
            new VitalPayLogin().setVisible(true);
        }
    }
}

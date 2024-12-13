/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tip.edu.cs21s3.b24.view;

import com.tip.edu.cs21s3.b24.model.Constants;
import static com.tip.edu.cs21s3.b24.view.AdminDashboard.db;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;

public class VitalPayReport extends JFrame implements ActionListener {

    private JLabel noOfItem;
    private JTable patientTable;
    private JPanel tablePanel;
    private DefaultTableModel tableModel;
    private JButton backBtn, generate;
    private JComboBox<String> reporttype;

    public VitalPayReport() {
        // Create the main frame
        setTitle("General Report");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Initialize and add components
        initializeComponents();

        setVisible(true);
    }

    private void initializeComponents() {

        JPanel report = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 150, 136), getWidth(), 0, new Color(115, 147, 179));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        report.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        report.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel labelId = new JLabel("Type of Report");
        labelId.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 18));
        labelId.setForeground(Constants.TEXT_COLOR);
        report.add(labelId);

        String[] type = {"List of Patient", "List of Staff"};
        reporttype = new JComboBox<>(type);
        reporttype.setPreferredSize(new Dimension(200, 30));
        reporttype.setBackground(Constants.BACKGROUND_COLOR); // Set background color of JComboBox to Teal
        reporttype.setForeground(Constants.TEXT_COLOR_BLACK);  // Set text color for items in JComboBox
        reporttype.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1));
        reporttype.setFocusable(false);
        report.add(reporttype);

        generate = createButton("Generate");
        generate.setPreferredSize(new Dimension(150, 30));
        generate.setBackground(Constants.TEXT_COLOR);
        generate.setForeground(Constants.TEXT_COLOR_BLACK);
        generate.setFocusPainted(false);
        generate.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 12));
        generate.addActionListener(this);
        report.add(generate);

        add(report, BorderLayout.NORTH);
        
        noOfItem = new JLabel("Total list: ");
        noOfItem.setVisible(false);
        
        add(noOfItem, BorderLayout.WEST);
        
        tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(tablePanel, BorderLayout.CENTER);
        
        // Add button panel
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel() {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Buttons
        backBtn = createButton("Back");
        backBtn.setPreferredSize(new Dimension(100,30));
        backBtn.setBackground(Constants.SECONDARY_COLOR);
        backBtn.setForeground(Constants.TEXT_COLOR);
        backBtn.setFocusPainted(false);
        backBtn.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 14));
        backBtn.addActionListener(this);

        // Add buttons to the panel
        btnPanel.add(backBtn);

        return btnPanel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            this.setVisible(false);
            new AdminDashboard().setVisible(true);
        } else if (e.getSource() == generate) {
            String selectedReport = (String) reporttype.getSelectedItem();
            generateReport(selectedReport);
        }
    }

    private void generateReport(String reportType) {
        if ("List of Patient".equals(reportType)) {
            updateTableColumns(new String[]{
                "Patient ID", "Name", "Diagnosis"
            }, true);
            
            populateTable(fetchPatientData());
        } else if ("List of Staff".equals(reportType)) {
            updateTableColumns(new String[]{
                "User ID", "Name", "Role"
            }, false);
            
            populateTable(fetchStaffData());
        }
    }

    private void updateTableColumns(String[] columns, boolean isPatient) {

        Object[][] data = isPatient ? db.fetchPatientsData() : db.fetchUserStaffData();

        tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        patientTable = new JTable(tableModel);
        patientTable.getTableHeader().setReorderingAllowed(false);

        JTableHeader header = patientTable.getTableHeader();
        header.setBackground(Constants.PRIMARY_COLOR); // Teal header background
        header.setForeground(Constants.TEXT_COLOR); // White text
        header.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 12));

        tablePanel.removeAll();
        JScrollPane tableScrollPane = new JScrollPane(patientTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        tablePanel.revalidate();
        tablePanel.repaint();
        
        int rowCount = patientTable.getRowCount();
        noOfItem.setText("Total List: \n " + rowCount);
        noOfItem.setVisible(true);
    }

    private void populateTable(Object[][] data) {
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    private Object[][] fetchPatientData() {

        return new Object[][]{};
    }

    private Object[][] fetchStaffData() {

        return new Object[][]{};
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VitalPayReport::new);
    }
}

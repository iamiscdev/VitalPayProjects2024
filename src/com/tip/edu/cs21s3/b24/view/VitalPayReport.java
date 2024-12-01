/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tip.edu.cs21s3.b24.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VitalPayReport extends JFrame implements ActionListener {

    private JTable patientTable;
    private JPanel tablePanel;
    private DefaultTableModel tableModel;
    private JButton backBtn, generate;
    private JComboBox<String> reporttype;

    public VitalPayReport() {
        // Create the main frame
        setTitle("General Report");
        setSize(800, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        // Add a window listener to detect when the close (X) button is clicked
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Close the frame only when the close (X) button is clicked
                dispose();
            }
        });
        
        // Initialize and add components
        initializeComponents();

        setVisible(true);
    }

    private void initializeComponents() {

        JPanel report = new JPanel();
        report.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        report.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        report.add(new JLabel("Type of Report"));

        String[] type = {"List of Patient", "List of Staff"};
        reporttype = new JComboBox<>(type);
        reporttype.setPreferredSize(new Dimension(200, 30));
        reporttype.setFocusable(false);
        report.add(reporttype);

        generate = createButton("Generate Report");
        generate.setPreferredSize(new Dimension(150, 30));
        generate.setFocusable(false);
        generate.addActionListener(this);
        report.add(generate);

        add(report, BorderLayout.NORTH);

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
        backBtn.setPreferredSize(new Dimension(100, 30));
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
            new VitalPayAdmin().setVisible(true);
        } else if (e.getSource() == generate) {
            String selectedReport = (String) reporttype.getSelectedItem();
            generateReport(selectedReport);
        }
    }

    private void generateReport(String reportType) {
        if ("List of Patient".equals(reportType)) {
            updateTableColumns(new String[]{
                "First Name", "Last Name", "Gender",
                "Contact Number", "Major Disease", "Bills"
            });
            
            populateTable(fetchPatientData());
        } else if ("List of Staff".equals(reportType)) {
            updateTableColumns(new String[]{
                "First Name", "Last Name", "Role"
            });
            
            populateTable(fetchStaffData());
        }
    }

    private void updateTableColumns(String[] columns) {
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        patientTable = new JTable(tableModel);
        patientTable.getTableHeader().setReorderingAllowed(false);

        tablePanel.removeAll();
        JScrollPane tableScrollPane = new JScrollPane(patientTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        tablePanel.revalidate();
        tablePanel.repaint();
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

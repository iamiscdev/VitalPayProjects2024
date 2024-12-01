/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tip.edu.cs21s3.b24.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VitalPayPatientMeds extends JFrame implements ActionListener {
    private JTextField patientIdField, payField, balanceField;
    private JLabel totalPrescriptionLabel, totalDiagnosticsLabel, totalAmountLabel;
    private JTable prescriptionTable, diagnosticsTable;
    private DefaultTableModel prescriptionTableModel, diagnosticsTableModel;
    private JButton addDrugBtn, removeDrugBtn, addTestBtn, removeTestBtn, generateBillBtn, clearBtn, backBtn;

    public VitalPayPatientMeds() {
        setTitle("Prescribe Patient");
        setSize(900, 800);
        setLayout(null);
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
        
        initializeComponents();
        setVisible(true);
    }

    private void initializeComponents() {
        // Patient Information Section
        JLabel patientIdLabel = new JLabel("Patient ID:");
        patientIdLabel.setBounds(30, 20, 80, 25);
        add(patientIdLabel);

        patientIdField = new JTextField();
        patientIdField.setBounds(110, 20, 150, 25);
        add(patientIdField);

        JLabel patientNameLabel = new JLabel("Patient Name:   <d2 lalabas output ng patient name>");
        patientNameLabel.setBounds(300, 20, 500, 25);
        add(patientNameLabel);


        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(650, 20, 100, 25);
        searchBtn.addActionListener(this);
        add(searchBtn);

        // Prescription Details
        JLabel prescriptionLabel = new JLabel("Prescription Details:");
        prescriptionLabel.setBounds(30, 60, 200, 25);
        add(prescriptionLabel);

        String[] prescriptionColumns = {"Drug Code", "Drug Name", "Quantity", "Unit Price", "Total Cost"};
        prescriptionTableModel = new DefaultTableModel(prescriptionColumns, 0);
        prescriptionTable = new JTable(prescriptionTableModel);
        JScrollPane prescriptionScrollPane = new JScrollPane(prescriptionTable);
        prescriptionScrollPane.setBounds(30, 90, 820, 150);
        add(prescriptionScrollPane);

        addDrugBtn = new JButton("Add Drug");
        addDrugBtn.setBounds(30, 250, 100, 25);
        addDrugBtn.addActionListener(this);
        add(addDrugBtn);

        removeDrugBtn = new JButton("Remove Drug");
        removeDrugBtn.setBounds(150, 250, 150, 25);
        removeDrugBtn.addActionListener(this);
        add(removeDrugBtn);

        // Diagnostics Details
        JLabel diagnosticsLabel = new JLabel("Diagnostics Details:");
        diagnosticsLabel.setBounds(30, 290, 200, 25);
        add(diagnosticsLabel);

        String[] diagnosticsColumns = {"Test Code", "Test Name", "Cost"};
        diagnosticsTableModel = new DefaultTableModel(diagnosticsColumns, 0);
        diagnosticsTable = new JTable(diagnosticsTableModel);
        JScrollPane diagnosticsScrollPane = new JScrollPane(diagnosticsTable);
        diagnosticsScrollPane.setBounds(30, 320, 820, 150);
        add(diagnosticsScrollPane);

        addTestBtn = new JButton("Add Test");
        addTestBtn.setBounds(30, 480, 100, 25);
        addTestBtn.addActionListener(this);
        add(addTestBtn);

        removeTestBtn = new JButton("Remove Test");
        removeTestBtn.setBounds(150, 480, 150, 25);
        removeTestBtn.addActionListener(this);
        add(removeTestBtn);

        // Summary Section
        totalPrescriptionLabel = new JLabel("Total Prescription Cost: 0");
        totalPrescriptionLabel.setBounds(30, 520, 200, 25);
        add(totalPrescriptionLabel);

        totalDiagnosticsLabel = new JLabel("Total Diagnostics Cost: 0");
        totalDiagnosticsLabel.setBounds(30, 550, 200, 25);
        add(totalDiagnosticsLabel);

        totalAmountLabel = new JLabel("Total Amount: 0");
        totalAmountLabel.setBounds(30, 580, 200, 25);
        add(totalAmountLabel);

        JLabel payLabel = new JLabel("Pay:");
        payLabel.setBounds(400, 520, 50, 25);
        add(payLabel);

        payField = new JTextField();
        payField.setBounds(450, 520, 150, 25);
        add(payField);

        JLabel balanceLabel = new JLabel("Balance:");
        balanceLabel.setBounds(400, 550, 70, 25);
        add(balanceLabel);

        balanceField = new JTextField();
        balanceField.setBounds(450, 550, 150, 25);
        balanceField.setEditable(false);
        add(balanceField);

        // Action Buttons
        generateBillBtn = new JButton("Generate Bill");
        generateBillBtn.setBounds(650, 520, 150, 25);
        generateBillBtn.addActionListener(this);
        add(generateBillBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(650, 580, 150, 25);
        backBtn.addActionListener(this);
        add(backBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addDrugBtn) {
            // Wlang logic
            JOptionPane.showMessageDialog(this, "Add Drug functionality to be implemented.");
        } else if (e.getSource() == removeDrugBtn) {
            int selectedRow = prescriptionTable.getSelectedRow();
            if (selectedRow >= 0) {
                prescriptionTableModel.removeRow(selectedRow);
            }
        } else if (e.getSource() == addTestBtn) {
            // Wlang Lpgic
            JOptionPane.showMessageDialog(this, "Add Test functionality to be implemented.");
        } else if (e.getSource() == removeTestBtn) {
            int selectedRow = diagnosticsTable.getSelectedRow();
            if (selectedRow >= 0) {
                diagnosticsTableModel.removeRow(selectedRow);
            }
        } else if (e.getSource() == generateBillBtn) {
            // Generate bill logic
            JOptionPane.showMessageDialog(this, "Generate Bill functionality to be implemented.");
        } else if (e.getSource() == clearBtn) {
            prescriptionTableModel.setRowCount(0);
            diagnosticsTableModel.setRowCount(0);
            totalPrescriptionLabel.setText("Total Prescription Cost: 0");
            totalDiagnosticsLabel.setText("Total Diagnostics Cost: 0");
            totalAmountLabel.setText("Total Amount: 0");
            payField.setText("");
            balanceField.setText("");
        } else if (e.getSource() == backBtn) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VitalPayPatientMeds::new);
    }
}

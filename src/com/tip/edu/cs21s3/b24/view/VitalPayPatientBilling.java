package com.tip.edu.cs21s3.b24.view;

import com.tip.edu.cs21s3.b24.model.Constants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.JTableHeader;

public class VitalPayPatientBilling extends JFrame implements ActionListener {

    private JTextField patientIdField, daysField;
    private JLabel totalPrescriptionLabel, totalDiagnosticsLabel, totalRoomChargesLabel, vatLabel, grossTotalLabel, coverageLabel, payableLabel;
    private JTable prescriptionTable, diagnosticsTable;
    private DefaultTableModel prescriptionTableModel, diagnosticsTableModel;
    private JComboBox<String> planComboBox, roomTypeComboBox;
    private JButton addDrugBtn, removeDrugBtn, addTestBtn, removeTestBtn, generateBillBtn, backBtn;

    public VitalPayPatientBilling() {
        setTitle("Patient Billing");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 750);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        add(createTitlePanel(), BorderLayout.NORTH);
        add(initializeComponents(), BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createTitlePanel() {
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
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 35)); // Center components in the panel
        titlePanel.setPreferredSize(new Dimension(getWidth(), 100));

        // Initialize and customize the JLabel
        JLabel tLabel = new JLabel("Patient Billing");
        tLabel.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 24));
        tLabel.setForeground(Color.WHITE);

        // Add label to the titlePanel
        titlePanel.add(tLabel);

        return titlePanel;
    }

    private JPanel initializeComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        // Patient Information Section
        JLabel patientIdLabel = new JLabel("Patient ID:");
        patientIdLabel.setBounds(30, 10, 80, 25);
        mainPanel.add(patientIdLabel);

        patientIdField = new JTextField();
        patientIdField.setBounds(110, 10, 150, 25);
        mainPanel.add(patientIdField);

        JLabel patientNameLabel = new JLabel("Patient Name: <output>");
        patientNameLabel.setBounds(300, 10, 500, 25);
        mainPanel.add(patientNameLabel);

        // Prescription Details Section
        JLabel prescriptionLabel = new JLabel("Prescription Details:");
        prescriptionLabel.setBounds(30, 50, 200, 25);
        mainPanel.add(prescriptionLabel);

        String[] prescriptionColumns = {"Drug Code", "Drug Name", "Quantity", "Unit Price", "Total Cost"};
        prescriptionTableModel = new DefaultTableModel(prescriptionColumns, 0);
        prescriptionTable = new JTable(prescriptionTableModel);
        prescriptionTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane prescriptionScrollPane = new JScrollPane(prescriptionTable);
        prescriptionScrollPane.setBounds(30, 80, 600, 150);
        mainPanel.add(prescriptionScrollPane);

        // Customize table header
        JTableHeader header = prescriptionTable.getTableHeader();
        header.setBackground(Constants.PRIMARY_COLOR); // Teal header background
        header.setForeground(Constants.TEXT_COLOR); // White text
        header.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 12));
        
        addDrugBtn = new JButton("Add Drug");
        addDrugBtn.setBounds(30, 240, 100, 25);
        addDrugBtn = styleButton(addDrugBtn); // Apply button style
        addDrugBtn.addActionListener(this);
        mainPanel.add(addDrugBtn);

        removeDrugBtn = new JButton("Remove Drug");
        removeDrugBtn.setBounds(150, 240, 150, 25);
        removeDrugBtn = styleButton(removeDrugBtn); // Apply button style
        removeDrugBtn.addActionListener(this);
        mainPanel.add(removeDrugBtn);

        // Diagnostics Details Section
        JLabel diagnosticsLabel = new JLabel("Diagnostics Details:");
        diagnosticsLabel.setBounds(30, 280, 200, 25);
        mainPanel.add(diagnosticsLabel);

        String[] diagnosticsColumns = {"Test Code", "Test Name", "Cost"};
        diagnosticsTableModel = new DefaultTableModel(diagnosticsColumns, 0);
        diagnosticsTable = new JTable(diagnosticsTableModel);
        diagnosticsTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane diagnosticsScrollPane = new JScrollPane(diagnosticsTable);
        diagnosticsScrollPane.setBounds(30, 310, 600, 150);
        mainPanel.add(diagnosticsScrollPane);

        // Customize table header
        JTableHeader header2 = diagnosticsTable.getTableHeader();
        header2.setBackground(Constants.PRIMARY_COLOR); // Teal header background
        header2.setForeground(Constants.TEXT_COLOR); // White text
        header2.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 12));
        
        addTestBtn = new JButton("Add Test");
        addTestBtn.setBounds(30, 470, 100, 25);
        addTestBtn = styleButton(addTestBtn); // Apply button style
        addTestBtn.addActionListener(this);
        mainPanel.add(addTestBtn);

        removeTestBtn = new JButton("Remove Test");
        removeTestBtn.setBounds(150, 470, 150, 25);
        removeTestBtn = styleButton(removeTestBtn); // Apply button style
        removeTestBtn.addActionListener(this);
        mainPanel.add(removeTestBtn);

        // Room Details Section
        JLabel roomTypeLabel = new JLabel("Room Type:");
        roomTypeLabel.setBounds(30, 510, 100, 25);
        mainPanel.add(roomTypeLabel);

        roomTypeComboBox = new JComboBox<>(new String[]{"Ward", "Semi-Private", "Private"});
        roomTypeComboBox.setBounds(130, 510, 150, 25);
        mainPanel.add(roomTypeComboBox);

        JLabel daysLabel = new JLabel("Days:");
        daysLabel.setBounds(300, 510, 50, 25);
        mainPanel.add(daysLabel);

        daysField = new JTextField();
        daysField.setBounds(350, 510, 50, 25);
        mainPanel.add(daysField);

        // Plan Selection Section
        JLabel planLabel = new JLabel("Maxicare Plan:");
        planLabel.setBounds(30, 540, 100, 25);
        mainPanel.add(planLabel);

        planComboBox = new JComboBox<>(new String[]{"Silver", "Gold", "Platinum", "Platinum Plus"});
        planComboBox.setBounds(130, 540, 150, 25);
        mainPanel.add(planComboBox);

        // Summary Section
        JLabel summaryLabel = new JLabel("Bill Breakdown:");
        summaryLabel.setBounds(700, 50, 200, 25);
        mainPanel.add(summaryLabel);

        totalPrescriptionLabel = new JLabel("Total Prescription Cost: 0");
        totalPrescriptionLabel.setBounds(700, 80, 300, 25);
        mainPanel.add(totalPrescriptionLabel);

        totalDiagnosticsLabel = new JLabel("Total Diagnostics Cost: 0");
        totalDiagnosticsLabel.setBounds(700, 110, 300, 25);
        mainPanel.add(totalDiagnosticsLabel);

        totalRoomChargesLabel = new JLabel("Total Room Charges: 0");
        totalRoomChargesLabel.setBounds(700, 140, 300, 25);
        mainPanel.add(totalRoomChargesLabel);

        vatLabel = new JLabel("VAT: 0");
        vatLabel.setBounds(700, 170, 300, 25);
        mainPanel.add(vatLabel);

        grossTotalLabel = new JLabel("Gross Total: 0");
        grossTotalLabel.setBounds(700, 200, 300, 25);
        mainPanel.add(grossTotalLabel);

        coverageLabel = new JLabel("Maxicare Coverage: 0");
        coverageLabel.setBounds(700, 230, 300, 25);
        mainPanel.add(coverageLabel);

        payableLabel = new JLabel("Payable Amount: 0");
        payableLabel.setBounds(700, 260, 300, 25);
        mainPanel.add(payableLabel);

        // Action Buttons
        generateBillBtn = new JButton("Generate Bill");
        generateBillBtn.setBounds(700, 300, 150, 25);
        generateBillBtn = styleButton(generateBillBtn); // Apply button style
        generateBillBtn.addActionListener(this);
        mainPanel.add(generateBillBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(700, 340, 150, 25);
        backBtn = styleButton2(backBtn); // Apply button style
        backBtn.addActionListener(this);
        mainPanel.add(backBtn);

        return mainPanel;
    }

    // Button Style Helper Method
    private JButton styleButton(JButton button) {
        button.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 12)); // Set button font
        button.setBackground(Constants.PRIMARY_COLOR); // Teal background
        button.setForeground(Constants.TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    // Button Style Helper Method
    private JButton styleButton2(JButton button) {
        button.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 12)); // Set button font
        button.setBackground(Constants.SECONDARY_COLOR);
        button.setForeground(Constants.TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addDrugBtn) {
            // Add Drug Logic
            String drugCode = JOptionPane.showInputDialog(this, "Enter Drug Code:");
            String drugName = JOptionPane.showInputDialog(this, "Enter Drug Name:");
            String quantityStr = JOptionPane.showInputDialog(this, "Enter Quantity:");
            String unitPriceStr = JOptionPane.showInputDialog(this, "Enter Unit Price:");

            try {
                int quantity = Integer.parseInt(quantityStr);
                double unitPrice = Double.parseDouble(unitPriceStr);
                double totalCost = quantity * unitPrice;

                prescriptionTableModel.addRow(new Object[]{drugCode, drugName, quantity, unitPrice, totalCost});

                updatePrescriptionTotal();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for quantity or unit price.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == removeDrugBtn) {
            // Remove Drug Logic
            int selectedRow = prescriptionTable.getSelectedRow();
            if (selectedRow != -1) {
                prescriptionTableModel.removeRow(selectedRow);
                updatePrescriptionTotal();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a drug to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == addTestBtn) {
            // Add Test Logic
            String testCode = JOptionPane.showInputDialog(this, "Enter Test Code:");
            String testName = JOptionPane.showInputDialog(this, "Enter Test Name:");
            String costStr = JOptionPane.showInputDialog(this, "Enter Test Cost:");

            try {
                double cost = Double.parseDouble(costStr);

                diagnosticsTableModel.addRow(new Object[]{testCode, testName, cost});
                updateDiagnosticsTotal();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for test cost.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == removeTestBtn) {
            // Remove Test Logic
            int selectedRow = diagnosticsTable.getSelectedRow();
            if (selectedRow != -1) {
                diagnosticsTableModel.removeRow(selectedRow);
                updateDiagnosticsTotal();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a test to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == generateBillBtn) {
            // Generate Bill Logic
            try {
                double totalPrescription = calculatePrescriptionTotal();
                double totalDiagnostics = calculateDiagnosticsTotal();
                double roomCharges = calculateRoomCharges();
                double vat = (totalPrescription + totalDiagnostics + roomCharges) * Constants.VAT_RATE;
                double grossTotal = totalPrescription + totalDiagnostics + roomCharges + vat;

                int coverage = getCoverageLimit();
                double payableAmount = Math.max(0, grossTotal - coverage);

                // Update labels
                totalPrescriptionLabel.setText("Total Prescription Cost: " + totalPrescription);
                totalDiagnosticsLabel.setText("Total Diagnostics Cost: " + totalDiagnostics);
                totalRoomChargesLabel.setText("Total Room Charges: " + roomCharges);
                vatLabel.setText("VAT: " + vat);
                grossTotalLabel.setText("Gross Total: " + grossTotal);
                coverageLabel.setText("Maxicare Coverage: " + grossTotal);
                payableLabel.setText("Payable Amount: " + payableAmount);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for room charges or days.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == backBtn) {
            // Back Button Logic
            if (e.getSource() == backBtn) {
                this.dispose();
                this.dispose();
            }
        }
    }

    private void updatePrescriptionTotal() {
        totalPrescriptionLabel.setText("Total Prescription Cost: " + calculatePrescriptionTotal());
    }

    private void updateDiagnosticsTotal() {
        totalDiagnosticsLabel.setText("Total Diagnostics Cost: " + calculateDiagnosticsTotal());
    }

    private double calculatePrescriptionTotal() {
        double total = 0;
        for (int i = 0; i < prescriptionTableModel.getRowCount(); i++) {
            total += (double) prescriptionTableModel.getValueAt(i, 4);
        }
        return total;
    }

    private double calculateDiagnosticsTotal() {
        double total = 0;
        for (int i = 0; i < diagnosticsTableModel.getRowCount(); i++) {
            total += (double) diagnosticsTableModel.getValueAt(i, 2);
        }
        return total;
    }

    private double calculateRoomCharges() throws NumberFormatException {
        String roomType = (String) roomTypeComboBox.getSelectedItem();
        int days = Integer.parseInt(daysField.getText());
        double rate = switch (roomType) {
            case "Private" ->
                5000;
            case "Semi-Private" ->
                3000;
            case "Ward" ->
                1500;
            default ->
                0;
        };
        return rate * days;
    }

    private int getCoverageLimit() {
        String plan = (String) planComboBox.getSelectedItem();
        return switch (plan) {
            case "Silver" ->
                Constants.SILVER_COVERAGE;
            case "Gold" ->
                Constants.GOLD_COVERAGE;
            case "Platinum" ->
                Constants.PLATINUM_COVERAGE;
            case "Platinum Plus" ->
                Constants.PLATINUMPLUS_COVERAGE;
            default ->
                0;
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VitalPayPatientBilling::new);
    }
}

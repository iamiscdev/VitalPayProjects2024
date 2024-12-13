package com.tip.edu.cs21s3.b24.view;

import com.tip.edu.cs21s3.b24.controller.UserDBController;
import com.tip.edu.cs21s3.b24.dialog.CustomDialog;
import com.tip.edu.cs21s3.b24.model.Constants;
import com.tip.edu.cs21s3.b24.model.PatientModel;
import static com.tip.edu.cs21s3.b24.view.AdminDashboard.db;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.table.JTableHeader;

public class VitalPayPatientBilling extends JFrame implements ActionListener {

    private JTextField patientIdField, daysField;
    private JLabel totalPrescriptionLabel, totalDiagnosticsLabel, totalRoomChargesLabel, vatLabel, grossTotalLabel, coverageLabel, payableLabel;
    private JTable prescriptionTable, diagnosticsTable;
    private DefaultTableModel prescriptionTableModel, diagnosticsTableModel;
    private JComboBox<String> planComboBox, roomTypeComboBox;
    private JButton addDrugBtn, removeDrugBtn, addTestBtn, removeTestBtn, generateBillBtn, backBtn;

    public UserDBController db;
    public PatientModel patient;
    public String patient_id;
    public String[] prescriptionColumns = {"Drug Code", "Drug Name", "Quantity", "Unit Price", "Total Cost"};
    public String[] diagnosticsColumns = {"Test Name", "Test Description", "Cost"};
    
    public VitalPayPatientBilling(String patient_id) {
        db = new UserDBController();
        this.patient_id =patient_id;
        this.patient = db.fetchPatientById(patient_id);
        
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
        patientIdField.setText(patient.getPatientId());
        patientIdField.setEnabled(false);
        patientIdField.setBounds(110, 10, 150, 25);
        mainPanel.add(patientIdField);

        JLabel patientNameLabel = new JLabel("Patient Name: " + String.format("%s, %s %s ", patient.getLastName(), patient.getFirstName(), patient.getMiddleName()));
        patientNameLabel.setBounds(300, 10, 500, 25);
        mainPanel.add(patientNameLabel);

        // Prescription Details Section
        JLabel prescriptionLabel = new JLabel("Prescription Details:");
        prescriptionLabel.setBounds(30, 50, 200, 25);
        mainPanel.add(prescriptionLabel);
        
        Object[][] data = db.fetchPatientPrescription(patient_id);
        prescriptionTableModel = new DefaultTableModel(data, prescriptionColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        prescriptionTable = new JTable(prescriptionTableModel);
        // Set the selection mode (0 corresponds to selecting rows)
        prescriptionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

        removeDrugBtn = new JButton("Void Drug");
        removeDrugBtn.setBounds(150, 240, 150, 25);
        removeDrugBtn = styleButton(removeDrugBtn); // Apply button style
        removeDrugBtn.addActionListener(this);
        mainPanel.add(removeDrugBtn);

        // Diagnostics Details Section
        JLabel diagnosticsLabel = new JLabel("Diagnostics Details:");
        diagnosticsLabel.setBounds(30, 280, 200, 25);
        mainPanel.add(diagnosticsLabel);
        
        Object[][] dataDiagnostic = db.fetchPatientDiagnostic(patient_id);
        diagnosticsTableModel = new DefaultTableModel(dataDiagnostic, diagnosticsColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        diagnosticsTable = new JTable(diagnosticsTableModel);
        // Set the selection mode (0 corresponds to selecting rows)
        diagnosticsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

        removeTestBtn = new JButton("Void Test");
        removeTestBtn.setBounds(150, 470, 150, 25);
        removeTestBtn = styleButton(removeTestBtn); // Apply button style
        removeTestBtn.addActionListener(this);
        mainPanel.add(removeTestBtn);

        // Room Details Section
        JLabel roomTypeLabel = new JLabel("Room Type:");
        roomTypeLabel.setBounds(30, 510, 100, 25);
        mainPanel.add(roomTypeLabel);
        roomTypeLabel.setVisible(patient.isWardRequired());

        
        roomTypeComboBox = new JComboBox<>(new String[]{"General", "Semi-Private", "Private"});
        roomTypeComboBox.setBounds(130, 510, 150, 25);
        roomTypeComboBox.setSelectedItem(patient.getTypeOfWard().trim());
        roomTypeComboBox.setBackground(Constants.PRIMARY_COLOR); // Set background color of JComboBox to Teal
        roomTypeComboBox.setForeground(Constants.TEXT_COLOR);  // Set text color for items in JComboBox
        mainPanel.add(roomTypeComboBox);
        
        roomTypeComboBox.setVisible(patient.isWardRequired());
        
        JLabel daysLabel = new JLabel("Days:");
        daysLabel.setBounds(300, 510, 50, 25);
        mainPanel.add(daysLabel);
        daysLabel.setVisible(patient.isWardRequired());

        daysField = new JTextField();
        daysField.setBounds(350, 510, 50, 25);
        mainPanel.add(daysField);
        daysField.setVisible(patient.isWardRequired());

        // Plan Selection Section
        JLabel planLabel = new JLabel(patient.getInsuranceProvider() + " Plan");
        // Get the font metrics for the label's font
        FontMetrics metrics1 = planLabel.getFontMetrics(planLabel.getFont());

        // Calculate the width of the text
        int textWidth1 = metrics1.stringWidth(planLabel.getText());
        
        planLabel.setBounds(30, 540, textWidth1, 25);
        mainPanel.add(planLabel);
        
        planLabel.setVisible(!patient.getInsuranceProvider().isEmpty());

        planComboBox = new JComboBox<>(new String[]{"Silver", "Gold", "Platinum", "Platinum Plus"});
        
        planComboBox.setBounds(textWidth1 + 35, 540, 150, 25);
        planComboBox.setBackground(Constants.PRIMARY_COLOR); // Set background color of JComboBox to Teal
        planComboBox.setForeground(Constants.TEXT_COLOR);  // Set text color for items in JComboBox
        mainPanel.add(planComboBox);
        planComboBox.setVisible(!patient.getInsuranceProvider().isEmpty());
        
        // Summary Section
        JLabel summaryLabel = new JLabel("===ACKNOWLEDGEMENT RECEIPT===");

        // Get the font metrics for the label's font
        FontMetrics metrics = summaryLabel.getFontMetrics(summaryLabel.getFont());

        // Calculate the width of the text
        int textWidth = metrics.stringWidth(summaryLabel.getText());
        
        summaryLabel.setBounds(700, 50, textWidth, 25);
        mainPanel.add(summaryLabel);

        int yPosition = 80; // Start Y-position for the first label below summaryLabel

        totalPrescriptionLabel = new JLabel("Total Prescription Cost: 0");
        totalPrescriptionLabel.setBounds(700, yPosition, 300, 25);
        mainPanel.add(totalPrescriptionLabel);
        yPosition += 30; // Increment Y-position

        totalDiagnosticsLabel = new JLabel("Total Diagnostics Cost: 0");
        totalDiagnosticsLabel.setBounds(700, yPosition, 300, 25);
        mainPanel.add(totalDiagnosticsLabel);
        yPosition += 30; // Increment Y-position

        // Add totalRoomChargesLabel if the ward is required
        if (patient.isWardRequired()) {
            totalRoomChargesLabel = new JLabel("Total Room Charges: 0");
            totalRoomChargesLabel.setBounds(700, yPosition, 300, 25);
            mainPanel.add(totalRoomChargesLabel);
            yPosition += 30; // Increment Y-position
        }

        // Add coverageLabel if the insurance provider is specified
        if (!patient.getInsuranceProvider().isEmpty()) {
            coverageLabel = new JLabel(patient.getInsuranceProvider() + " Coverage: 0.00");
            coverageLabel.setBounds(700, yPosition, 300, 25);
            mainPanel.add(coverageLabel);
            yPosition += 30; // Increment Y-position
        }

        vatLabel = new JLabel("VAT: 0.00");
        vatLabel.setBounds(700, yPosition, 300, 25);
        mainPanel.add(vatLabel);
        yPosition += 30; // Increment Y-position

        grossTotalLabel = new JLabel("Gross Total: 0.00");
        grossTotalLabel.setBounds(700, yPosition, 300, 25);
        mainPanel.add(grossTotalLabel);
        yPosition += 30; // Increment Y-position

        payableLabel = new JLabel("Payable Amount: 0.00");
        payableLabel.setBounds(700, yPosition, 300, 25);
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
        
        updatePrescriptionTotal();
        updateDiagnosticsTotal();
        return mainPanel;
    }
    
    private void reloadPrescription() {

        Object[][] data = db.fetchPatientPrescription(patient_id);
        prescriptionTableModel = new DefaultTableModel(data, prescriptionColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        prescriptionTable.setModel(prescriptionTableModel);
        updatePrescriptionTotal();
    }
    
    private void reloadDiagnostic() {
        Object[][] dataDiagnostic = db.fetchPatientDiagnostic(patient_id);
        diagnosticsTableModel = new DefaultTableModel(dataDiagnostic, diagnosticsColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        diagnosticsTable.setModel(diagnosticsTableModel);
        updateDiagnosticsTotal();
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

                if (db.insertPatientPrescription(patient.getPatientId(), drugCode, drugName, quantity, unitPrice)) {

                    CustomDialog.showMessage(
                            this,
                            "Prescription added successfully",
                            "Success",
                            "success"
                    );

                    reloadPrescription();
                } else {
                    CustomDialog.showMessage(
                            this,
                            "Adding prescription unsuccessful",
                            "Error",
                            "error"
                    );
                }
            } catch (NumberFormatException ex) {
                
                CustomDialog.showMessage(
                            this,
                            "Invalid input for quantity or unit price.",
                            "Error",
                            "error"
                    );
            }
        } else if (e.getSource() == removeDrugBtn) {

            // Remove Drug Logic
            int selectedRow = prescriptionTable.getSelectedRow();
            if (selectedRow != -1) {

                String drugCode = prescriptionTable.getValueAt(selectedRow, 0).toString();
                if (db.deletePatientPrescription(patient.getPatientId(), drugCode)) {
                    reloadPrescription();
                }
            } else {

                CustomDialog.showMessage(
                        this,
                        "Please select a drug to remove.",
                        "Error",
                        "error"
                );

            }
        } else if (e.getSource() == addTestBtn) {
            // Add Test Logic
            String testCode = JOptionPane.showInputDialog(this, "Enter Test Name:");
            String testDes = JOptionPane.showInputDialog(this, "Enter Test Description:");
            String costStr = JOptionPane.showInputDialog(this, "Enter Cost:");

            try {
                double cost = Double.parseDouble(costStr);

                if (db.insertPatientDiagnostic(patient.getPatientId(), testCode, testDes, cost)) {

                    CustomDialog.showMessage(
                            this,
                            "Diagnostic added successfully",
                            "Success",
                            "success"
                    );

                    reloadDiagnostic();
                } else {
                    CustomDialog.showMessage(
                            this,
                            "Adding diagnostic unsuccessful",
                            "Error",
                            "error"
                    );
                }
            } catch (NumberFormatException ex) {
                
                CustomDialog.showMessage(
                            this,
                            "Invalid input for unit price.",
                            "Error",
                            "error"
                    );
            }
        } else if (e.getSource() == removeTestBtn) {

            // Remove Test Logic
            int selectedRow = diagnosticsTable.getSelectedRow();
            if (selectedRow != -1) {

                String testName = diagnosticsTable.getValueAt(selectedRow, 0).toString();
                if (db.deletePatientDiagnostic(patient.getPatientId(), testName)) {
                    reloadDiagnostic();
                }
            } else {

                CustomDialog.showMessage(
                        this,
                        "Please select a test to remove.",
                        "Error",
                        "error"
                );

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
                totalPrescriptionLabel.setText("Total Prescription Cost: " + getDoublePlaces(totalPrescription));
                totalDiagnosticsLabel.setText("Total Diagnostics Cost: " + getDoublePlaces(totalDiagnostics));
                
                if (patient.isWardRequired()) {
                    totalRoomChargesLabel.setText("Total Room Charges: " + getDoublePlaces(roomCharges));
                }
                
                
                vatLabel.setText("VAT: " + getDoublePlaces(vat));
                grossTotalLabel.setText("Gross Total: " + getDoublePlaces(grossTotal));
                
                if (!patient.getInsuranceProvider().isEmpty()) {
                    coverageLabel.setText(patient.getInsuranceProvider() + " Coverage: " + getDoublePlaces(grossTotal));
                }
                
                payableLabel.setText("Payable Amount: " + getDoublePlaces(payableAmount));

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
        totalPrescriptionLabel.setText("Total Prescription Cost: " + getDoublePlaces(calculatePrescriptionTotal()));
    }

    private void updateDiagnosticsTotal() {
        totalDiagnosticsLabel.setText("Total Diagnostics Cost: " + getDoublePlaces(calculateDiagnosticsTotal()));
    }

    private double calculatePrescriptionTotal() {
        double total = 0;
        for (int i = 0; i < prescriptionTableModel.getRowCount(); i++) {
            total += (double) Double.parseDouble((String) prescriptionTableModel.getValueAt(i, 4));
        }
        return total;
    }

    private double calculateDiagnosticsTotal() {
        double total = 0;
        for (int i = 0; i < diagnosticsTableModel.getRowCount(); i++) {
            total += (double) Double.parseDouble((String) diagnosticsTableModel.getValueAt(i, 2));
        }
        return total;
    }

    private double calculateRoomCharges() throws NumberFormatException {
        if (patient.isWardRequired()) {
            String roomType = (String) roomTypeComboBox.getSelectedItem();
            int days = Integer.parseInt(daysField.getText());
            double rate = switch (roomType) {
                case "Private" ->
                    5000;
                case "Semi-Private" ->
                    3000;
                case "General" ->
                    1500;
                default ->
                    0;
            };
            return rate * days;
        } else {
            return 0.00;
        }
    }

    private int getCoverageLimit() {
        if (!patient.getInsuranceProvider().isEmpty()) {
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
        } else {
            return 0;
        }
    }
    
    private String getDoublePlaces(double dp){
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(dp);
    }
}

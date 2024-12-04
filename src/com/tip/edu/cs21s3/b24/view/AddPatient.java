package com.tip.edu.cs21s3.b24.view;

import com.tip.edu.cs21s3.b24.controller.UserDBController;
import com.tip.edu.cs21s3.b24.dialog.CustomDialog;
import com.tip.edu.cs21s3.b24.model.Constants;
import com.tip.edu.cs21s3.b24.model.PatientModel;
import com.tip.edu.cs21s3.b24.model.UserSession;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.swing.border.LineBorder;

public class AddPatient extends JFrame implements ActionListener {

    private JButton saveBtn, backBtn;
    private JLabel tLabel, fnLabel, mnLabel, snLabel, numbLabel, dobLabel, addressLabel, bgLabel, genLabel, disLabel, symptomsLabel, diagnosisLabel, medicinesLabel, wardLabel, wardTypeLabel;
    private JTextField fnField, mnField, snField, numbField, dobField, addressField, bgField, disField, symptomsField, diagnosisField, medicinesField, insuranceField, companyNameField, idCardField;
    private JRadioButton maleRadio, femaleRadio;
    private ButtonGroup genderGroup;
    private JCheckBox wardCheckBox;
    private JComboBox<String> wardTypeComboBox;

    private boolean isEditing;
    private PatientModel patient;
    private UserDBController db;

    public AddPatient() {
        this(null); // Call the overloaded constructor with no patient (add mode)
    }
    
    public AddPatient(PatientModel patient){

        db = new UserDBController();

        this.patient = patient;
        this.isEditing = (patient != null);
        
        // Set the frame title dynamically based on mode
        setTitle(isEditing ? "Edit Patient" : "New Patient Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(800, 800);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Initialize and add components
        add(createTitlePanel(), BorderLayout.PAGE_START);
        add(createFormPanel(), BorderLayout.CENTER);

         // Prepopulate fields if editing
        if (isEditing) {
            populateFields();
        }
        
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
        tLabel = new JLabel(isEditing ? "EDIT PATIENT FORM" : "NEW PATIENT FORM");
        tLabel.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 24));
        tLabel.setForeground(Color.WHITE);

        // Add label to the titlePanel
        titlePanel.add(tLabel);

        return titlePanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);  // Manual positioning

        // Initialize and add components to the panel
        fnLabel = new JLabel("First Name:");
        fnLabel.setBounds(50, 100, 100, 20);
        formPanel.add(fnLabel);

        fnField = new JTextField();
        fnField.setPreferredSize(new Dimension(200, 30));
        fnField.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1)); // Set teal green border for JTextField
        fnField.setBounds(150, 100, 150, 25);
        formPanel.add(fnField);

        mnLabel = new JLabel("Middle Name:");
        mnLabel.setBounds(50, 140, 100, 20);
        formPanel.add(mnLabel);

        mnField = new JTextField();
        mnField.setPreferredSize(new Dimension(200, 30));
        mnField.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1));
        mnField.setBounds(150, 140, 150, 25);
        formPanel.add(mnField);

        snLabel = new JLabel("Last Name:");
        snLabel.setBounds(50, 180, 100, 20);
        formPanel.add(snLabel);

        snField = new JTextField();
        snField.setPreferredSize(new Dimension(200, 30));
        snField.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1));
        snField.setBounds(150, 180, 150, 25);
        formPanel.add(snField);

        numbLabel = new JLabel("Phone Number:");
        numbLabel.setBounds(50, 220, 100, 20);
        formPanel.add(numbLabel);

        numbField = new JTextField();
        numbField.setPreferredSize(new Dimension(200, 30));
        numbField.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1));
        numbField.setBounds(150, 220, 150, 25);
        formPanel.add(numbField);

        dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(50, 260, 100, 20);
        formPanel.add(dobLabel);

        dobField = new JTextField("YYYY-MM-DD");
        dobField.setPreferredSize(new Dimension(200, 30));
        dobField.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1));
        dobField.setBounds(150, 260, 150, 25);
        formPanel.add(dobField);

        addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 300, 100, 20);
        formPanel.add(addressLabel);

        addressField = new JTextField();
        addressField.setPreferredSize(new Dimension(200, 30));
        addressField.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1));
        addressField.setBounds(150, 300, 150, 25);
        formPanel.add(addressField);

        bgLabel = new JLabel("Blood Group:");
        bgLabel.setBounds(50, 340, 100, 20);
        formPanel.add(bgLabel);

        bgField = new JTextField();
        bgField.setPreferredSize(new Dimension(200, 30));
        bgField.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1));
        bgField.setBounds(150, 340, 150, 25);
        formPanel.add(bgField);

        disLabel = new JLabel("Major Diseases (if any):");
        disLabel.setBounds(50, 380, 200, 20);
        formPanel.add(disLabel);

        disField = new JTextField();
        disField.setPreferredSize(new Dimension(200, 30));
        disField.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1));
        disField.setBounds(50, 410, 250, 50);
        formPanel.add(disField);

        genLabel = new JLabel("Gender:");
        genLabel.setBounds(50, 480, 100, 20);
        formPanel.add(genLabel);

        maleRadio = new JRadioButton("Male");
        maleRadio.setSelected(true);
        maleRadio.setBounds(150, 480, 70, 20);
        femaleRadio = new JRadioButton("Female");
        femaleRadio.setBounds(220, 480, 80, 20);

        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);

        formPanel.add(maleRadio);
        formPanel.add(femaleRadio);

        symptomsLabel = new JLabel("Symptoms:");
        symptomsLabel.setBounds(400, 100, 100, 20);
        formPanel.add(symptomsLabel);

        symptomsField = new JTextField();
        symptomsField.setPreferredSize(new Dimension(200, 30));
        symptomsField.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1));
        symptomsField.setBounds(500, 100, 200, 25);
        formPanel.add(symptomsField);

        diagnosisLabel = new JLabel("Diagnosis:");
        diagnosisLabel.setBounds(400, 140, 100, 20);
        formPanel.add(diagnosisLabel);

        diagnosisField = new JTextField();
        diagnosisField.setPreferredSize(new Dimension(200, 30));
        diagnosisField.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1));
        diagnosisField.setBounds(500, 140, 200, 25);
        formPanel.add(diagnosisField);

        medicinesLabel = new JLabel("Medicines:");
        medicinesLabel.setBounds(400, 180, 100, 20);
        formPanel.add(medicinesLabel);

        medicinesField = new JTextField();
        medicinesField.setPreferredSize(new Dimension(200, 30));
        medicinesField.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1));
        medicinesField.setBounds(500, 180, 200, 25);
        formPanel.add(medicinesField);

        wardLabel = new JLabel("Ward Required?");
        wardLabel.setBounds(400, 220, 150, 25);
        formPanel.add(wardLabel);

        wardCheckBox = new JCheckBox("YES");
        wardCheckBox.setPreferredSize(new Dimension(200, 30));
        wardCheckBox.setBackground(new Color(0, 150, 136)); // Set background color to Teal
        wardCheckBox.setFocusPainted(false); // Remove the default focus border
        wardCheckBox.setForeground(Color.WHITE); // White text color
        wardCheckBox.setOpaque(true); // Ensure it has a solid background
        wardCheckBox.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 136), 2, true)); // Border color
        wardCheckBox.setBounds(550, 220, 50, 25);
        wardCheckBox.addActionListener(this);
        formPanel.add(wardCheckBox);

        wardTypeLabel = new JLabel("Type of Ward:");
        wardTypeLabel.setBounds(400, 260, 150, 25);
        wardTypeLabel.setVisible(false);
        formPanel.add(wardTypeLabel);

        wardTypeComboBox = new JComboBox<>(new String[]{"General", "Semi-Private", "Private"});
        wardTypeComboBox.setPreferredSize(new Dimension(200, 30));
        wardTypeComboBox.setBackground(Constants.PRIMARY_COLOR); // Set background color of JComboBox to Teal
        wardTypeComboBox.setForeground(Constants.TEXT_COLOR);  // Set text color for items in JComboBox

        wardTypeComboBox.setVisible(false);
        wardTypeComboBox.setBounds(550, 260, 150, 25);
        formPanel.add(wardTypeComboBox);

        JLabel insuranceLabel = new JLabel("Insurance Provider:");
        insuranceLabel.setBounds(400, 300, 150, 25);
        formPanel.add(insuranceLabel);

        insuranceField = new JTextField();
        insuranceField.setPreferredSize(new Dimension(200, 30));
        insuranceField.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1));
        insuranceField.setBounds(550, 300, 200, 25);
        formPanel.add(insuranceField);

        JLabel companyNameLabel = new JLabel("Company Name:");
        companyNameLabel.setBounds(400, 340, 150, 25);
        formPanel.add(companyNameLabel);

        companyNameField = new JTextField();
        companyNameField.setPreferredSize(new Dimension(200, 30));
        companyNameField.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1));
        companyNameField.setBounds(550, 340, 200, 25);
        formPanel.add(companyNameField);

        JLabel idCardLabel = new JLabel("ID Card:");
        idCardLabel.setBounds(400, 380, 150, 25);
        formPanel.add(idCardLabel);

        idCardField = new JTextField();
        idCardField.setPreferredSize(new Dimension(200, 30));
        idCardField.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1));
        idCardField.setBounds(550, 380, 200, 25);
        formPanel.add(idCardField);

        // Buttons
        // Save Details Button
        saveBtn = new JButton(isEditing ? "Update" : "Save");
        saveBtn.setPreferredSize(new Dimension(120, 40));
        saveBtn.setBackground(Constants.PRIMARY_COLOR);
        saveBtn.setForeground(Constants.TEXT_COLOR);
        saveBtn.setFocusPainted(false);
        saveBtn.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 14));
        saveBtn.setBounds(250, 530, 120, 40);

        // Back Button
        backBtn = new JButton("Back");
        backBtn.setPreferredSize(new Dimension(120, 40));
        backBtn.setBackground(Constants.SECONDARY_COLOR);
        backBtn.setForeground(Constants.TEXT_COLOR);
        backBtn.setFocusPainted(false);
        backBtn.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 14));
        backBtn.setBounds(390, 530, 120, 40);

        // Action listeners
        saveBtn.addActionListener(this);
        backBtn.addActionListener(this);

        // Add buttons to the panel
        formPanel.add(saveBtn);
        formPanel.add(backBtn);

        return formPanel;
    }

    private void populateFields() {
        fnField.setText(patient.getFirstName());
        mnField.setText(patient.getMiddleName());
        snField.setText(patient.getLastName());
        numbField.setText(patient.getPhone());
        dobField.setText(patient.getDateOfBirth());
        addressField.setText(patient.getAddress());
        bgField.setText(patient.getBloodGroup());
        disField.setText(patient.getMajorDiseases());
        symptomsField.setText(patient.getSymptoms());
        diagnosisField.setText(patient.getDiagnosis());
        medicinesField.setText(patient.getMedicines());
        wardCheckBox.setSelected(patient.isWardRequired());
        wardTypeComboBox.setSelectedItem(patient.getTypeOfWard());
        insuranceField.setText(patient.getInsuranceProvider());
        companyNameField.setText(patient.getCompanyName());
        idCardField.setText(patient.getIdCard());
        
        if (patient.isGender()) {
            maleRadio.setSelected(true);
        } else {
            femaleRadio.setSelected(false);
        }

        // Show ward type controls if ward is required
        wardTypeLabel.setVisible(patient.isWardRequired());
        wardTypeComboBox.setVisible(patient.isWardRequired());
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBtn) {
            
             // Extract data from user inputs
            String firstName = fnField.getText();
            String middleName = mnField.getText();
            String lastName = snField.getText();
            String phoneNumber = numbField.getText();
            String dob = dobField.getText();
            String address = addressField.getText();
            String bloodGroup = bgField.getText();
            String diseases = disField.getText();
            String symptoms = symptomsField.getText();
            String diagnosis = diagnosisField.getText();
            String medicines = medicinesField.getText();
            boolean wardRequired = wardCheckBox.isSelected();
            String wardType = wardRequired ? (String) wardTypeComboBox.getSelectedItem() : "General";
            String insuranceProvider = insuranceField.getText();
            String companyName = companyNameField.getText();
            String idCard = idCardField.getText();
            boolean genderMale = maleRadio.isSelected();
            
            // Validate inputs
            if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || dob.isEmpty()) {
                CustomDialog.showMessage(
                        this,
                        "Please fill in all required fields.",
                        "Warning",
                        "warning"
                );
                return;
            } 
            
            if (!insuranceProvider.isEmpty()) {
                if (companyName.isEmpty() || idCard.isEmpty()) {
                    CustomDialog.showMessage(
                            this,
                            "Please fill in the Company Name and ID Card",
                            "Warning",
                            "warning"
                    );
                    return;
                }
            }
            
             // Create the PatientModel object
                PatientModel patient_ = new PatientModel(
                        isEditing ? patient.getPatientId() : generatePatientId(6) , // Generate a unique patient ID
                        firstName,
                        middleName,
                        lastName,
                        phoneNumber,
                        dob,
                        address,
                        genderMale,
                        bloodGroup,
                        diseases,
                        symptoms,
                        diagnosis,
                        medicines,
                        wardRequired,
                        wardType,
                        insuranceProvider,
                        companyName,
                        idCard,
                        UserSession.getInstance().getUserId(),
                        false // Adjust this field if needed
                );
            
            String userRole = UserSession.getInstance().getRole();
            
            // Update patient object if editing
            if (isEditing) {
                // Update patient in the database
                if (db.updatePatient(patient_)) {
                    CustomDialog.showMessage(this, "Patient updated successfully", "Success", "success");
                     if(userRole.equals("Admin")) {
                        AdminDashboard.reloadTableData();
                    } else {
                        StaffDashboard.reloadPatientTableData();
                    }
                    this.dispose();
                } else {
                    CustomDialog.showMessage(this, "Updating patient unsuccessful", "Error", "error");
                }

            } else {
                
                if (db.insertPatient(patient_)) {
                    CustomDialog.showMessage(this, "Patient added successfully", "Success", "success");
                    if(userRole.equals("Admin")) {
                        AdminDashboard.reloadTableData();
                    } else {
                        StaffDashboard.reloadPatientTableData();
                    }
                    this.dispose();
                } else {
                    CustomDialog.showMessage(this, "Adding patient unsuccessful", "Error", "error");
                }
            }
        } else if (e.getSource() == backBtn) {
            this.dispose();
        } else if (e.getSource() == wardCheckBox) {
            // Toggle visibility of ward type
            boolean isChecked = wardCheckBox.isSelected();
            wardTypeLabel.setVisible(isChecked);
            wardTypeComboBox.setVisible(isChecked);
        }
    }

    private String generatePatientId(int length) {
        String characters = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        sb.append("P-");
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddPatient::new);
    }
}

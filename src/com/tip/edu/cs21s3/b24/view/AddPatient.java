package com.tip.edu.cs21s3.b24.view;

import com.tip.edu.cs21s3.b24.view.VitalPayAdmin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPatient extends JFrame implements ActionListener {

    private JButton addBtn;
    private JButton backBtn;
    private JLabel tLabel, idLabel, fnLabel, mnLabel, snLabel, numbLabel, dobLabel, genLabel;
    private JTextField fnField, mnField, snField, numbField, dobField;
    private JComboBox<String> idComboBox;
    private JRadioButton maleRadio, femaleRadio;
    private ButtonGroup genderGroup;

    public AddPatient() {
        //Create the main frame
        setTitle("New Patient Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        //Initialize and add components
        initializeComponents();
        add(createButtonPanel());

        setVisible(true);
    }

    private void initializeComponents() {
        //Title label
        tLabel = new JLabel("NEW PATIENT FORM");
        tLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        tLabel.setBounds(200, 10, 300, 30);
        add(tLabel);

        //ID label and combo box
        idLabel = new JLabel("ID:");
        idLabel.setBounds(50, 60, 100, 20);
        add(idLabel);

        String[] idOptions = {"ViCard", "Barangay ID"};
        idComboBox = new JComboBox<>(idOptions);
        idComboBox.setBounds(150, 60, 150, 25);
        add(idComboBox);

        //First name
        fnLabel = new JLabel("First Name:");
        fnLabel.setBounds(50, 100, 100, 20);
        add(fnLabel);

        fnField = new JTextField();
        fnField.setBounds(150, 100, 150, 25);
        add(fnField);

        //Middle name
        mnLabel = new JLabel("Middle Name:");
        mnLabel.setBounds(50, 140, 100, 20);
        add(mnLabel);

        mnField = new JTextField();
        mnField.setBounds(150, 140, 150, 25);
        add(mnField);

        //Surname
        snLabel = new JLabel("Surname:");
        snLabel.setBounds(50, 180, 100, 20);
        add(snLabel);

        snField = new JTextField();
        snField.setBounds(150, 180, 150, 25);
        add(snField);

        //Phone number
        numbLabel = new JLabel("Phone Number:");
        numbLabel.setBounds(50, 220, 100, 20);
        add(numbLabel);

        numbField = new JTextField();
        numbField.setBounds(150, 220, 150, 25);
        add(numbField);

        //Date of Birth
        dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(50, 260, 100, 20);
        add(dobLabel);

        dobField = new JTextField("YYYY-MM-DD");
        dobField.setBounds(150, 260, 150, 25);
        add(dobField);

        //Gender
        genLabel = new JLabel("Gender:");
        genLabel.setBounds(50, 300, 100, 20);
        add(genLabel);

        maleRadio = new JRadioButton("Male");
        maleRadio.setBounds(150, 300, 70, 20);
        femaleRadio = new JRadioButton("Female");
        femaleRadio.setBounds(220, 300, 80, 20);

        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);

        add(maleRadio);
        add(femaleRadio);
    }

    private JPanel createButtonPanel() {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(1, 2, 10, 10));
        btnPanel.setBounds(150, 400, 250, 40);

        //Buttons
        addBtn = createButton("Add");
        backBtn = createButton("Back");

        //Action listeners
        backBtn.addActionListener(this);

        //Add buttons to the panel
        btnPanel.add(addBtn);
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
            //new VitalPayAdmin().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddPatient::new);
    }
}

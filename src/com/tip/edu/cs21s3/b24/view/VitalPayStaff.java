package com.tip.edu.cs21s3.b24.view;

import com.tip.edu.cs21s3.b24.model.UserStaffModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VitalPayStaff extends JFrame implements ActionListener {
    
    private JButton patientButton;
    private JButton archiveButton;
    private JButton logoutButton;
    private UserStaffModel staff;
       
    public VitalPayStaff(UserStaffModel staff){
        this.staff = staff;
        
        // Frame ng mismong system siyempre
        setTitle("VitalPay");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(null);
        setResizable(false);
        
        // title na label pa san ka pa
        JLabel titleLabel = new JLabel("VitalPay", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(200, 10, 200, 30);
        add(titleLabel);

        // pindutan
        patientButton = new JButton("Add Patient");
        patientButton.setBounds(50, 80, 150, 30);
        patientButton.addActionListener(this);
        patientButton.setFocusable(false);
        add(patientButton);

        archiveButton = new JButton("Generate Bill");
        archiveButton.setBounds(50, 120, 150, 30);
        archiveButton.addActionListener(this);
        archiveButton.setFocusable(false);
        add(archiveButton);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(50, 160, 150, 30);
        logoutButton.addActionListener(this);
        logoutButton.setFocusable(false);
        add(logoutButton);

        // pede makita rito un and ut
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(112, 128, 144));
        infoPanel.setBounds(250, 80, 300, 150);
        infoPanel.setLayout(null);

        // Mismong Username bali pangalan ko na lang nilagay ko (weni)
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(20, 20, 80, 20);
        infoPanel.add(usernameLabel);

        JLabel usernameValue = new JLabel(staff.getFirstName() + " " + staff.getLastName());
        usernameValue.setFont(new Font("Arial", Font.BOLD, 14));
        usernameValue.setForeground(Color.RED);
        usernameValue.setBounds(120, 20, 100, 20);
        infoPanel.add(usernameValue);

        // registered as staff
        JLabel userTypeLabel = new JLabel("UserType: ");
        userTypeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userTypeLabel.setForeground(Color.WHITE);
        userTypeLabel.setBounds(20, 60, 80, 20);
        infoPanel.add(userTypeLabel);

        JLabel userTypeValue = new JLabel(staff.getRole());
        userTypeValue.setFont(new Font("Arial", Font.BOLD, 14));
        userTypeValue.setForeground(Color.RED);
        userTypeValue.setBounds(120, 60, 100, 20);
        infoPanel.add(userTypeValue);

        add(infoPanel);
        setVisible(true);
        
        //Special thanks kay barredo sa pag help sakin)
    }
    
    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == patientButton){
            this.setVisible(false);
            AddPatient addpatient = new AddPatient();
            addpatient.setVisible(true);
        }
        
        if(e.getSource() == logoutButton){
            this.setVisible(false);
            VitalPayLogin login = new VitalPayLogin();
            login.setVisible(true);
        }
    }
   
}

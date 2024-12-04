/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tip.edu.cs21s3.b24.view;

import com.tip.edu.cs21s3.b24.model.Constants;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

public class VitalPaySearch extends JFrame implements ActionListener {
     private JTable patientTable;
    private JPanel tablePanel;
    private DefaultTableModel tableModel;
    private JButton backBtn, search;
    private JTextField patientID;

    public VitalPaySearch() {
        // Create the main frame
        setTitle("Search Patient");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        // Initialize and add components
        initializeComponents();

        setVisible(true);
    }

    private void initializeComponents() {
        
        JPanel ID = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 150, 136), getWidth(), 0, new Color(115, 147, 179));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        ID.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        ID.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel labelId = new JLabel("Patient ID:");
        labelId.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 18));
        labelId.setForeground(Constants.TEXT_COLOR);
        ID.add(labelId);

        patientID = new JTextField();
        patientID.setPreferredSize(new Dimension(200, 30));
        patientID.setBorder(new LineBorder(Constants.PRIMARY_COLOR, 1)); // Set teal green border for JTextField
        ID.add(patientID);
        
        search = createButton("Search");
        search.setPreferredSize(new Dimension(100, 30));
        search.setBackground(Constants.TEXT_COLOR);
        search.setForeground(Constants.TEXT_COLOR_BLACK);
        search.setFocusPainted(false);
        search.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 12));
        search.addActionListener(this);
        ID.add(search);

        add(ID, BorderLayout.NORTH);
        
        tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Table for patient details
        String[] columns = {
            "Patient ID", "First Name", "Middle Name", "Last Name",
            "Contact Number", "Age", "Gender", "Blood Group", "Address", "Major Disease"
        };
        tableModel = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        patientTable = new JTable(tableModel);
        patientTable.getTableHeader().setReorderingAllowed(false);

        // Add table inside a scroll pane
        JScrollPane tableScrollPane = new JScrollPane(patientTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        //Add Table panel
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
        } 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VitalPaySearch::new);
    }
}

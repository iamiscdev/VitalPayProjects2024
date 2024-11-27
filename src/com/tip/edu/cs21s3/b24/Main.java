/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.tip.edu.cs21s3.b24;

import com.tip.edu.cs21s3.b24.view.VitalPayLogin;
import javax.swing.SwingUtilities;

/**
 *
 * @author Admin
 */

public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VitalPayLogin().setVisible(true);
        });
    }
}
package com.tip.edu.cs21s3.b24;

import com.tip.edu.cs21s3.b24.view.VitalPayLogin;
import javax.swing.SwingUtilities;

public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VitalPayLogin().setVisible(true);
        });
    }
}
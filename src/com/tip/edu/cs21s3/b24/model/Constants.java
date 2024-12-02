/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tip.edu.cs21s3.b24.model;

import java.awt.Color;

/**
 *
 * @author Admin
 */
public class Constants {
    
    public static final String FONT_STYLE = "Arial";
    
     // Color constants for uniformity (as in VitalPayLogin and VitalPayAdmin)
    public static final Color PRIMARY_COLOR = new Color(0x009688);  // Teal (for consistency)
    public static final Color SECONDARY_COLOR = new Color(0xFF5722);  // Orange (for buttons)
    public static final Color BACKGROUND_COLOR = new Color(0xF5F5F5);  // Light Gray
    public static final Color TEXT_COLOR = new Color(0xFFFFFF);  // white for text
    public static final Color TEXT_COLOR_SECONDARY = new Color(0x333333);  // white for text
    
    // Calculation for billing
    public static final double VAT_RATE = 0.12;
    public static final int SILVER_COVERAGE = 100000;
    public static final int GOLD_COVERAGE = 150000;
    public static final int PLATINUM_COVERAGE = 200000;
    public static final int PLATINUMPLUS_COVERAGE = 250000;
}

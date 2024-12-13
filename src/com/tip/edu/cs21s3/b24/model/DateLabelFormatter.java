/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tip.edu.cs21s3.b24.model;

import javax.swing.JFormattedTextField;

/**
 *
 * @author Isaac Rei Aniceta
 * @description DateLabelFormatter model
 * 
 */

public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

    private final String datePattern = "yyyy-MM-dd";
    private final java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws java.text.ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws java.text.ParseException {
        if (value != null) {
            java.util.Calendar cal = (java.util.Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }
}
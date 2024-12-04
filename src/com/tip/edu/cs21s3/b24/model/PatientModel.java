/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tip.edu.cs21s3.b24.model;

public class PatientModel {

    // Fields matching the database columns
    private String patientId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private String dateOfBirth;
    private String address;
    private boolean gender; // True for male, False for female (based on TINYINT(1))
    private String bloodGroup;
    private String majorDiseases;
    private String symptoms;
    private String diagnosis;
    private String medicines;
    private boolean wardRequired; // True if ward is required
    private String typeOfWard;
    private String insuranceProvider;
    private String companyName;
    private String idCard;
    private String created_by;
    private boolean archive; // True if archived

    // Constructor
    public PatientModel(String patientId, String firstName, String middleName, String lastName,
                        String phone, String dateOfBirth, String address, boolean gender,
                        String bloodGroup, String majorDiseases, String symptoms, String diagnosis,
                        String medicines, boolean wardRequired, String typeOfWard,
                        String insuranceProvider, String companyName, String idCard, String created_by, boolean archive) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.majorDiseases = majorDiseases;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.medicines = medicines;
        this.wardRequired = wardRequired;
        this.typeOfWard = typeOfWard;
        this.insuranceProvider = insuranceProvider;
        this.companyName = companyName;
        this.idCard = idCard;
        this.created_by = created_by;
        this.archive = archive;
    }

    // Getters and setters
    public String getPatientId() {
        return patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public boolean isGender() {
        return gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getMajorDiseases() {
        return majorDiseases;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getMedicines() {
        return medicines;
    }

    public boolean isWardRequired() {
        return wardRequired;
    }

    public String getTypeOfWard() {
        return typeOfWard;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getIdCard() {
        return idCard;
    }
    
    public String getCreatedBy() {
        return created_by;
    }

    public boolean isArchive() {
        return archive;
    }
}


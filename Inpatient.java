/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.hospital.models;
import com.mycompany.hospital.enums.PatientCategory;
/**
 *
 * 
 */
//Patient who stays in the hospital
public class Inpatient extends Patient {
    private String ward;
    private String bedNumber;

    public Inpatient(String patientId, String firstName, String lastName, int age, String gender, String medicalCondition, String ward, String bedNumber) {
        super(patientId, firstName, lastName, age, gender, medicalCondition, PatientCategory.INPATIENT);
        this.ward = ward;
        this.bedNumber = bedNumber;
    }

    public String getWard() { return ward; }
    public void setWard(String ward) { this.ward = ward; }
    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String bedNumber) { this.bedNumber = bedNumber; }
}
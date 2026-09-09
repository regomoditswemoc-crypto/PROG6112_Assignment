/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospital.services;

import com.company.hospital.models.Inpatient;
import com.company.hospital.models.Patient;
import com.mycompany.hospital.enums.*;
import java.util.*;

public class HospitalSystem {

    private static final int ROWS = 4;
    private static final int COLS = 5;
    private static final int TOTAL_BEDS = 20;

    private List<Patient> patients;
    private String[][] beds;
    private BedStatus[][] bedStatus;
    private Map<String, String> bedAllocations;

    public HospitalSystem() {
        patients = new ArrayList<>();
        bedAllocations = new HashMap<>();
        initializeBeds();
    }

    private void initializeBeds() {
        beds = new String[ROWS][COLS];
        bedStatus = new BedStatus[ROWS][COLS];
        int bedNumber = 1;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                beds[row][col] = String.format("B%02d", bedNumber);
                bedStatus[row][col] = BedStatus.AVAILABLE;
                bedNumber++;
            }
        }
    }

    // ===== PATIENT MANAGEMENT =====
    public boolean registerPatient(Patient patient) {
        if (searchPatient(patient.getPatientId()) != null) {
            return false;
        }
        patients.add(patient);
        return true;
    }

    public Patient searchPatient(String patientId) {
        for (Patient patient : patients) {
            if (patient.getPatientId().equals(patientId)) {
                return patient;
            }
        }
        return null;
    }

    public boolean updatePatient(String patientId, String firstName, int age, String medicalCondition) {
        Patient patient = searchPatient(patientId);
        if (patient == null) {
            return false;
        }
        patient.setFirstName(firstName);
        patient.setAge(age);
        patient.setMedicalCondition(medicalCondition);
        return true;
    }

    public boolean deletePatient(String patientId) {
        Patient patient = searchPatient(patientId);
        if (patient == null) {
            return false;
        }
        for (Map.Entry<String, String> entry : bedAllocations.entrySet()) {
            if (entry.getValue().equals(patientId)) {
                return false;
            }
        }
        patients.remove(patient);
        return true;
    }
    
    public List<Patient> getPatients() {
        return patients;
    }

    public void sortPatientsByName() {
        Collections.sort(patients, new Comparator<Patient>() {
            @Override
            public int compare(Patient p1, Patient p2) {
                return p1.getFirstName().compareTo(p2.getFirstName());
            }
        });
    }

    public void sortPatientsById() {
        Collections.sort(patients, new Comparator<Patient>() {
            @Override
            public int compare(Patient p1, Patient p2) {
                return p1.getPatientId().compareTo(p2.getPatientId());
            }
        });
    }

    // ===== BED MANAGEMENT =====
    public boolean allocateBed(String patientId, String bedNumber) {
        Patient patient = searchPatient(patientId);
        if (patient == null) return false;
        if (!(patient instanceof Inpatient)) return false;

        for (Map.Entry<String, String> entry : bedAllocations.entrySet()) {
            if (entry.getValue().equals(patientId)) {
                return false;
            }
        }

        int[] bedPosition = findBedPosition(bedNumber);
        if (bedPosition == null) return false;
        int row = bedPosition[0];
        int col = bedPosition[1];

        if (bedStatus[row][col] == BedStatus.OCCUPIED) return false;

        int occupiedCount = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (bedStatus[r][c] == BedStatus.OCCUPIED) occupiedCount++;
            }
        }
        if (occupiedCount >= TOTAL_BEDS) return false;

        bedStatus[row][col] = BedStatus.OCCUPIED;
        bedAllocations.put(bedNumber, patientId);
        ((Inpatient) patient).setBedNumber(bedNumber);
        return true;
    }

    public boolean releaseBed(String bedNumber) {
        int[] bedPosition = findBedPosition(bedNumber);
        if (bedPosition == null) return false;

        int row = bedPosition[0];
        int col = bedPosition[1];

        if (bedStatus[row][col] == BedStatus.AVAILABLE) return false;

        bedStatus[row][col] = BedStatus.AVAILABLE;
        bedAllocations.remove(bedNumber);
        return true;
    }

    private int[] findBedPosition(String bedNumber) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (beds[row][col].equals(bedNumber)) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }
}
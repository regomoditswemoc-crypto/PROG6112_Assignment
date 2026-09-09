/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospital.app;

import com.company.hospital.models.Inpatient;
import com.company.hospital.models.Patient;
import com.mycompany.hospital.enums.PatientCategory;
import com.mycompany.hospital.services.HospitalSystem;
import java.util.Scanner;

public class HospitalApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HospitalSystem system = new HospitalSystem();
        int choice;

        do {
            System.out.println("\n=== HOSPITAL MENU ===");
            System.out.println("1. Register Patient");
            System.out.println("2. Search Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Display All Patients");
            System.out.println("6. Allocate Bed");
            System.out.println("7. Release Bed");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.println("===================");
                    System.out.println("===== Registration ===");
                    System.out.println("======================");
                    
                    System.out.print("ID: "); String id = scanner.nextLine();
                    System.out.print("First Name: "); String fName = scanner.nextLine();
                    System.out.print("Last Name: "); String lName = scanner.nextLine();
                    System.out.print("Age: "); int age = scanner.nextInt(); scanner.nextLine();
                    System.out.print("Gender: "); String gender = scanner.nextLine();
                    System.out.print("Condition: "); String condition = scanner.nextLine();
                    System.out.print("Is this an Inpatient? (y/n): "); String isIn = scanner.nextLine();
                    
                    if (isIn.equalsIgnoreCase("y")) {
                        System.out.print("Ward: "); String ward = scanner.nextLine();
                        system.registerPatient(new Inpatient(id, fName, lName, age, gender, condition, ward, ""));
                    } else {
                        system.registerPatient(new Patient(id, fName, lName, age, gender, condition, PatientCategory.OUTPATIENT));
                    }
                    System.out.println("Patient registered!");
                    break;
                case 2:
               
                    System.out.print("Enter Patient ID: "); String sId = scanner.nextLine();
                    Patient p = system.searchPatient(sId);
                    if (p != null) p.displayDetails();
                    else System.out.println("Patient not found.");
                    break;
                case 3:
                    System.out.println("================================");
                    System.out.println("============= Search ==============");
                    System.out.println("====================================");
                    
                    System.out.print("Enter Patient ID: "); String uId = scanner.nextLine();
                    System.out.print("New First Name: "); String uFName = scanner.nextLine();
                    System.out.print("New Age: "); int uAge = scanner.nextInt(); scanner.nextLine();
                    System.out.print("New Condition: "); String uCondition = scanner.nextLine();
                    if (system.updatePatient(uId, uFName, uAge, uCondition)) System.out.println("Updated!");
                    else System.out.println("Failed to update.");
                    break;
                case 4:
                     System.out.println("================================");
                    System.out.println("============= Delete ==============");
                    System.out.println("====================================");
                    
                    System.out.print("Enter Patient ID to delete: "); String dId = scanner.nextLine();
                    if (system.deletePatient(dId)) System.out.println("Deleted!");
                    else System.out.println("Failed to delete.");
                    break;
                case 5:
                     System.out.println("================================");
                    System.out.println("============= Display All ==============");
                    System.out.println("====================================");
                    
                    for (Patient patient : system.getPatients()) {
                        patient.displayDetails();
                        System.out.println("---");
                    }
                    break;
                case 6:
                     System.out.println("================================");
                    System.out.println("============= Allocate Bed ==============");
                    System.out.println("====================================");
                    
                    System.out.print("Patient ID: "); String aId = scanner.nextLine();
                    System.out.print("Bed Number (B01-B20): "); String bed = scanner.nextLine();
                    if (system.allocateBed(aId, bed)) System.out.println("Bed allocated!");
                    else System.out.println("Failed to allocate bed.");
                    break;
                case 7:
                     System.out.println("================================");
                    System.out.println("============= Release Bed ==============");
                    System.out.println("====================================");
                    
                    System.out.print("Bed Number to release: "); String rBed = scanner.nextLine();
                    if (system.releaseBed(rBed)) System.out.println("Bed released!");
                    else System.out.println("Failed to release bed.");
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
        
        scanner.close();
    }
}
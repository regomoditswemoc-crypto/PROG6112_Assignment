This is a Java application for managing patients and beds in a hospital. 
The program allows you to register patients, search for them, update their details, 
delete them, and assign or release hospital beds.

How the Program Works:
The system has two types of patients:
- Outpatients: Do not need a bed.
- Inpatients: Stay in the ward and are assigned a specific bed.

The ward is set up as a grid of 20 beds (4 rows and 5 columns).

The Main Features 
1. Register a new patient.
2. Search for a patient using their ID.
3. Update a patient's first name, age, and medical condition.
4. Delete a patient (as long as they don't have a bed).
5. Display all registered patients.
6. Allocate a bed to an Inpatient (only if the bed is free).
7. Release a bed back to the available list.


-----------
This project also includes JUnit tests to check that the system works correctly 
(e.g., preventing duplicate IDs, preventing full wards, and sorting patients).

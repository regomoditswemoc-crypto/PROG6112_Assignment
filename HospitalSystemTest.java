/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.company.hospital.models.Inpatient;
import com.company.hospital.models.Patient;
import com.mycompany.hospital.services.HospitalSystem;
import com.mycompany.hospital.enums.PatientCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * 
 */
public class HospitalSystemTest {
 
    private HospitalSystem system;

    @BeforeEach
    public void setUp() {
        system = new HospitalSystem();
    }

    @Test
    public void testRegisterPatient() {
        Patient patient = new Patient("P001", "Kagiso", "Molefe", 28, "Male", "High blood pressure", PatientCategory.OUTPATIENT);
        boolean result = system.registerPatient(patient);
        assertTrue(result);
        Patient found = system.searchPatient("P001");
        assertNotNull(found);
        assertEquals("Kagiso", found.getFirstName());
    }

    @Test
    public void testSearchPatient() {
        Patient patient = new Patient("P002", "Thandiwe", "Zulu", 32, "Female", "Sinus infection", PatientCategory.OUTPATIENT);
        system.registerPatient(patient);
        Patient found = system.searchPatient("P002");
        assertNotNull(found);
        assertEquals("Thandiwe", found.getFirstName());
        Patient notFound = system.searchPatient("P999");
        assertNull(notFound);
    }

    @Test
    public void testUpdatePatient() {
        Patient patient = new Patient("P003", "Lungile", "Ndlovu", 45, "Female", "Stomach pain", PatientCategory.OUTPATIENT);
        system.registerPatient(patient);
        boolean result = system.updatePatient("P003", "Lungelo", 46, "Ulcer");
        assertTrue(result);
        Patient updated = system.searchPatient("P003");
        assertEquals("Lungelo", updated.getFirstName());
        assertEquals(46, updated.getAge());
        assertEquals("Ulcer", updated.getMedicalCondition());
    }

    @Test
    public void testDeletePatient() {
        Patient patient = new Patient("P004", "Mpho", "Mokwena", 37, "Male", "Fracture", PatientCategory.OUTPATIENT);
        system.registerPatient(patient);
        boolean result = system.deletePatient("P004");
        assertTrue(result);
        Patient deleted = system.searchPatient("P004");
        assertNull(deleted);
    }

    @Test
    public void testAllocateBed() {
        Inpatient inpatient = new Inpatient("P005", "Lethabo", "Mokoena", 55, "Male", "Stroke", "Ward A", "");
        system.registerPatient(inpatient);
        boolean result = system.allocateBed("P005", "B01");
        assertTrue(result);
    }

    @Test
    public void testReleaseBed() {
        Inpatient inpatient = new Inpatient("P006", "Thabo", "Mahlangu", 62, "Male", "Diabetes", "Ward A", "");
        system.registerPatient(inpatient);
        system.allocateBed("P006", "B02");
        boolean result = system.releaseBed("B02");
        assertTrue(result);
        boolean result2 = system.releaseBed("B02");
        assertFalse(result2);
    }

    @Test
    public void testPreventDuplicatePatientId() {
        Patient patient1 = new Patient("P007", "Nomsa", "Mduli", 27, "Female", "Flu", PatientCategory.OUTPATIENT);
        system.registerPatient(patient1);
        Patient patient2 = new Patient("P007", "Bongani", "Dlamini", 24, "Male", "Fever", PatientCategory.OUTPATIENT);
        boolean result = system.registerPatient(patient2);
        assertFalse(result);
    }

    @Test
    public void testPreventOccupiedBedAllocation() {
        Inpatient inpatient1 = new Inpatient("P008", "Boitumelo", "Tshabalala", 33, "Female", "Pneumonia", "Ward A", "");
        system.registerPatient(inpatient1);
        system.allocateBed("P008", "B03");
        Inpatient inpatient2 = new Inpatient("P009", "Sipho", "Nkosi", 40, "Male", "Arthritis", "Ward A", "");
        system.registerPatient(inpatient2);
        boolean result = system.allocateBed("P009", "B03");
        assertFalse(result);
    }

    @Test
    public void testOnlyInpatientsGetBeds() {
        Patient outpatient = new Patient("P010", "Sipho", "Buthulezi", 22, "Male", "Ear infection", PatientCategory.OUTPATIENT);
        system.registerPatient(outpatient);
        boolean result = system.allocateBed("P010", "B04");
        assertFalse(result);
    }

    @Test
    public void testSortPatientsByName() {
        Patient p1 = new Patient("P011", "Zanele", "Zulu", 25, "Female", "Cough", PatientCategory.OUTPATIENT);
        Patient p2 = new Patient("P012", "Kopano", "Mokoena", 30, "Male", "Fever", PatientCategory.OUTPATIENT);
        Patient p3 = new Patient("P013", "Ayanda", "Sundae", 28, "Female", "Headache", PatientCategory.OUTPATIENT);
        system.registerPatient(p1);
        system.registerPatient(p2);
        system.registerPatient(p3);
        system.sortPatientsByName();
    }

    @Test
    public void testSortPatientsById() {
        Patient p1 = new Patient("P020", "Tshepo", "Modise", 42, "Male", "Back pain", PatientCategory.OUTPATIENT);
        Patient p2 = new Patient("P010", "Dineo", "Sekgobela", 26, "Female", "Asthma", PatientCategory.OUTPATIENT);
        Patient p3 = new Patient("P015", "Sibusiso", "Mthembu", 35, "Male", "Skin rash", PatientCategory.OUTPATIENT);
        system.registerPatient(p1);
        system.registerPatient(p2);
        system.registerPatient(p3);
        system.sortPatientsById();
    }

    @Test
    public void testPreventDoubleBooking() {
        Inpatient inpatient = new Inpatient("P014", "Thandeka", "Mthembu", 39, "Female", "Diabetes", "Ward A", "");
        system.registerPatient(inpatient);
        system.allocateBed("P014", "B05");
        boolean result = system.allocateBed("P014", "B06");
        assertFalse(result);
    }

    @Test
    public void testCountPatients() {
        int initialCount = system.getPatients().size();
        assertEquals(0, initialCount);
        Patient patient = new Patient("P015", "Hlengiwe", "Shangaan", 34, "Female", "High cholesterol", PatientCategory.OUTPATIENT);
        system.registerPatient(patient);
        int newCount = system.getPatients().size();
        assertEquals(1, newCount);
    }

    @Test
    public void testPreventFullWardAllocation() {
        for (int i = 1; i <= 20; i++) {
            String id = "P" + String.format("%03d", i + 100);
            String name = "Patient" + i;
            Inpatient inpatient = new Inpatient(id, name, "Test", 40, "Male", "Test Condition", "Ward A", "");
            system.registerPatient(inpatient);
            String bed = String.format("B%02d", i);
            system.allocateBed(id, bed);
        }
        Inpatient extra = new Inpatient("P999", "Extra", "Patient", 25, "Male", "Emergency", "Ward A", "");
        system.registerPatient(extra);
        boolean result = system.allocateBed("P999", "B21");
        assertFalse(result);
    }

    @Test
    public void testReleaseAndReallocateBed() {
        Inpatient patient1 = new Inpatient("P020", "Morena", "Sekhukhune", 48, "Male", "Arthritis", "Ward A", "");
        system.registerPatient(patient1);
        system.allocateBed("P020", "B10");
        system.releaseBed("B10");
        Inpatient patient2 = new Inpatient("P021", "Khethiwe", "Ndlovu", 36, "Female", "Migraine", "Ward A", "");
        system.registerPatient(patient2);
        boolean result = system.allocateBed("P021", "B10");
        assertTrue(result);
    }
}


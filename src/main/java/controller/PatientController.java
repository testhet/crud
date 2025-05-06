package controller;

import Validation_helper.InputValidator;
import dao.PatientDAO;
import model.Patient;

import java.sql.SQLException;

public class PatientController {
    private PatientDAO patientDAO = new PatientDAO();

    public void registerPatient() throws SQLException {
        Patient patient = new Patient();

        patient.setDate_of_birth(InputValidator.getValidatedDate("Enter DOB : "));
        patient.setGender(InputValidator.getValidatedGender("Enter Gender of Patient : "));
        patient.setAddress(InputValidator.getValidatedTextField("Enter Address : "));
        patient.setPhone(InputValidator.getValidatedPhone("Enter Patient's Phone Number : "));
        patient.setEmergency_contact_number(InputValidator.getValidatedPhone("Enter Emergency Contact Number : "));
        patient.setInsuranceID(InputValidator.getValidatedInsuranceId("Enter Insurance ID : "));
        patient.setInsurance_provider(InputValidator.getValidatedName("Enter Insurance Provider Name : "));

        patientDAO.registerPatient(patient);


    }
}

package controller;

import Validation_helper.InputValidator;
import dao.PatientDAO;
import model.Patient;
import model.User;

import java.sql.SQLException;

public class PatientController {


    private PatientDAO patientDAO = new PatientDAO();
    private User currentUser;
    public PatientController(User currentUser) {
        this.currentUser = currentUser;
    }

    public void registerPatient() throws SQLException {
        UserController userController = new UserController();
        User user = userController.addUser("Patient");
        try{

        if (user == null) {
            System.out.println("User registration failed.");
            return;
        }

    Patient patient = new Patient();
    patient.setUser(user);
    patient.setDate_of_birth(InputValidator.getValidatedDatePast("Enter DOB : "));
    patient.setGender(InputValidator.getValidatedGender("Enter Gender of Patient : "));
    patient.setAddress(InputValidator.getValidatedTextField("Enter Address : "));
    patient.setPhone(InputValidator.getValidatedPhone("Enter Patient's Phone Number : "));
    patient.setEmergency_contact_number(InputValidator.getValidatedPhone("Enter Emergency Contact Number : "));
    patient.setInsuranceID(InputValidator.getValidatedInsuranceId("Enter Insurance ID : "));
    patient.setInsurance_provider(InputValidator.getValidatedName("Enter Insurance Provider Name : "));


    patientDAO.registerPatient(patient);
    System.out.println("Patient Registered Successfully");

        } catch (Exception e) {
    System.out.println("Error Registering patient");
    userController.deleteUser(user);
        }
    }

    public void updateUser()throws SQLException{

        String Address = InputValidator.getValidatedTextField("Enter New Address: ");
        long phone = InputValidator.getValidatedPhone("Enter New Phone Number: ");
        long emergencyContact = InputValidator.getValidatedPhone("Enter New Emergency Contact number: ");
        int id = currentUser.getId();

        patientDAO.updatePatientDetails(Address,phone,emergencyContact,id);
        System.out.println("Successfully Updated User Profile!!!");
    }

}

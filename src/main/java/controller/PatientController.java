package controller;

import Validation_helper.InputValidator;
import dao.PatientDAO;
import dao.UserDAO;
import model.Patient;
import model.User;
import java.sql.SQLException;


public class PatientController {

    private PatientDAO patientDAO = new PatientDAO();
    private  UserDAO userDAO = new UserDAO();
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
    patient.setDate_of_birth(InputValidator.getValidatedDatePast("Enter DOB(YYYY-MM-DD) : "));
    patient.setGender(InputValidator.getValidatedGender("Enter Gender of Patient(Male/Female/Other) : "));
    patient.setAddress(InputValidator.getValidatedTextField("Enter Address : "));
    patient.setPhone(InputValidator.getValidatedPhone("Enter Patient's Phone Number : "));
    patient.setEmergency_contact_number(InputValidator.getValidatedPhone("Enter Emergency Contact Number : "));
    patient.setInsuranceID(InputValidator.getValidatedInsuranceId("Enter Insurance ID : "));
    patient.setInsurance_provider(InputValidator.getValidatedName("Enter Insurance Provider Name : "));
    patientDAO.registerPatient(patient);
    System.out.println("Patient Registered Successfully");
        } catch (Exception e) {
    System.out.println("Error Registering patient");
            assert user != null;
            userDAO.deleteUser(user.getId());
        }
    }


    public void updatePatient(User user)throws SQLException{
        String Address = InputValidator.getValidatedTextField("Enter New Address: ");
        long phone = InputValidator.getValidatedPhone("Enter New Phone Number: ");
        long emergencyContact = InputValidator.getValidatedPhone("Enter New Emergency Contact number: ");
        int id = currentUser.getId();
        patientDAO.updatePatientDetails(Address,phone,emergencyContact,id);
        System.out.println("Successfully Updated Patient Profile!!!");
    }

    public void viewProfile(User user) throws SQLException{
        patientDAO.viewProfile(user);
    }

}

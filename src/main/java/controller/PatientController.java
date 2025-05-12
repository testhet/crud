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

    public void registerPatient() throws SQLException {
        UserController userController = new UserController();
        User user = userController.addUser("Patient");
        try {
            if (user == null) {
                System.out.println("User registration failed.");
                return;
            }
            Patient patient = new Patient();
            patient.setUser(user);
            patient.setDate_of_birth(InputValidator.getValidatedDatePast("Enter DOB(YYYY-MM-DD) : "));
            patient.setGender(InputValidator.getValidatedGender("Enter Gender of Patient(Male/Female/Other) : "));
            patient.setAddress(InputValidator.getValidatedTextField("Enter Address : "));
            boolean phoneExists;
            long a;
            do {
                a = InputValidator.getValidatedPhone("Enter Patient's Phone Number: ");
                phoneExists = patientDAO.phoneExist(a);
                if (phoneExists) {
                    System.out.println("This phone number is already in use. Please try a different number.");
                } else {
                    patient.setPhone(a);
                }
            } while (phoneExists);
            patient.setEmergency_contact_number(InputValidator.getValidatedPhone("Enter Emergency Contact Number : "));
            patient.setInsuranceID(InputValidator.getValidatedInsuranceId("Enter Insurance ID : "));
            boolean insuranceExist;
            String id;
            do {
                id = InputValidator.getValidatedInsuranceId("Enter Insurance ID : ");
                insuranceExist = patientDAO.insuranceIDExist(id);
                if (insuranceExist) {
                    System.out.println("This phone number is already in use. Please try a different number.");
                } else {
                    patient.setPhone(a);
                }
            } while (insuranceExist);
            patient.setInsurance_provider(InputValidator.getValidatedName("Enter Insurance Provider Name : "));
            patientDAO.registerPatient(patient);
            System.out.println("Patient Registered Successfully");
        } catch (Exception e) {
    System.out.println("Error Registering patient" + e.getMessage());
            assert user != null;
            userDAO.deleteUser(user.getId());
        }
    }


    public void updatePatient(User user)throws SQLException{
        String Address = InputValidator.getValidatedTextField("Enter New Address: ");
        long phone = InputValidator.getValidatedPhone("Enter New Phone Number: ");
        long emergencyContact = InputValidator.getValidatedPhone("Enter New Emergency Contact number: ");
        int id = user.getId();
        patientDAO.updatePatientDetails(Address,phone,emergencyContact,id);
        System.out.println("Successfully Updated Patient Profile!!!");
    }

    public void viewProfile(User user) throws SQLException{
        patientDAO.viewProfile(user);
    }

}

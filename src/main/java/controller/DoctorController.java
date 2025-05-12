package controller;

import Validation_helper.InputValidator;
import dao.DoctorDAO;
import dao.PatientDAO;
import dao.UserDAO;
import model.Doctor;
import model.User;
import java.sql.SQLException;

public class DoctorController {

    private DoctorDAO doctorDAO = new DoctorDAO();
    private UserDAO userDAO  = new UserDAO();
    private PatientDAO patientDAO = new PatientDAO();



    public void registerDoctor() throws SQLException {
        UserController controller = new UserController();
        User user = controller.addUser("Doctor");
        try {
            if (user == null) {
                System.out.println("User registration failed.");
                return;
            }
            Doctor doctor = new Doctor();
            doctor.setUser(user);
            doctor.setSpecialization(InputValidator.getValidatedTextField("Enter Doctors's Specialization : "));
            doctor.setDepartment(InputValidator.getValidatedDepartment("Select Department For Doctor : "));
            doctorDAO.registerDoctor(doctor);
            System.out.println("Doctor Registered Successfully!!!");
        } catch (Exception e) {
            System.out.println("Error Registering Doctor!!");
            assert user != null;
            userDAO.deleteUser(user.getId());
        }
    }

    public void doctorsProfile() throws SQLException {
          doctorDAO.doctorsProfile();
    }


    public void viewAssociatedPatientController(User user)throws SQLException{
       doctorDAO.viewAssociatedPatient(user.getId());
    }


    public void deletePatient(User user)throws SQLException{
        if(user.getRole().equalsIgnoreCase("doctor")) {
            int id;
            do {
                viewAssociatedPatientController(user);
                id = InputValidator.getValidatedInt("Enter Patient Id You Want Do Delete: \n To Exit Enter 0 :");
                if(id == 0){
                    break;
                }
                if (patientDAO.viewProfile(id)) {
                    String confirmation = "CONFIRM";
                    String cnfInput = InputValidator.getValidatedTextField(" --> Write CONFIRM To Delete Patient \n --> Write Anything Else to go Back : ");
                    if (confirmation.equals(cnfInput)) {
                        doctorDAO.deletePatient(id);
                        System.out.println("Successfully Deleted Patient With ID: " + id);
                    }
                }else {
                    System.out.println("Enter Valid Patient Id From the Above list");
                }
            } while (!patientDAO.viewProfile(id));
        }else {
            System.out.println("Only Doctors can Delete patient.");
        }
    }
}

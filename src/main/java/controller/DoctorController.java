package controller;

import Validation_helper.InputValidator;
import dao.DoctorDAO;
import model.Doctor;
import model.User;

import java.sql.SQLException;

public class DoctorController {

    private DoctorDAO doctorDAO = new DoctorDAO();
    private User currentUser;

    public DoctorController(User currentUser) {
        this.currentUser = currentUser;
    }

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
            doctor.setSpecialization(InputValidator.getValidatedTextField("Enter Doctors's Specialization"));
            doctor.setDepartment(InputValidator.getValidatedDepartment("Select Department For Doctor"));

            doctorDAO.registerDoctor(doctor);
            System.out.println("Successfully Registered Doctor!!!");
        } catch (Exception e) {
            System.out.println("Error Registering Doctor!!");
            controller.deleteUser(user);
        }
    }

    public void deletePatient()throws SQLException{
        if(currentUser.getRole().equalsIgnoreCase("doctor")){
            int id = InputValidator.getValidatedInt("Enter Patient Id You Want Do Delete");
            doctorDAO.deletePatient(id);
            System.out.println("Successfully Deleted Patient With ID: "+ id);
        }else {
            System.out.println("Only Doctors can Delete patient.");
        }
    }
}

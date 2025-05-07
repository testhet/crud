package controller;

import Validation_helper.InputValidator;
import dao.DoctorDAO;
import model.Doctor;
import model.User;

import java.sql.SQLException;

public class DoctorController {

    private DoctorDAO doctorDAO = new DoctorDAO();

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
}

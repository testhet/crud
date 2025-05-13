package controller;

import Validation_helper.InputValidator;
import dao.DoctorDAO;
import dao.UserDAO;
import model.Doctor;
import model.User;
import java.sql.SQLException;

public class DoctorController {

    private DoctorDAO doctorDAO = new DoctorDAO();
    private UserDAO userDAO  = new UserDAO();


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

            viewAssociatedPatientController(user);
            while (true) {
                int id = InputValidator.getValidatedInt("Enter Patient ID to delete (Enter 0 to Exit): ");
                if (id == 0) {
                    break;
                }
                if (!doctorDAO.associatedPatientExist(user.getId(),id)) {
                    System.out.println("Invalid Patient ID. Please select a valid ID from the list.");
                    continue;
                }
                String confirmation = InputValidator.getValidatedTextField(
                        " --> Type 'CONFIRM' to delete patient\n --> Type anything else to cancel: "
                );
                if ("CONFIRM".equalsIgnoreCase(confirmation.trim())) {
                    doctorDAO.deletePatient(id);
                    System.out.println("Successfully deleted patient with ID: " + id);
                } else {
                    System.out.println("Deletion canceled.");
                    break;
                }
            }
        }else {
            System.out.println("Only Doctors can Delete patient.");
        }
    }
}

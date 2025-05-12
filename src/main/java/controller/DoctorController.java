package controller;

import Validation_helper.InputValidator;
import dao.DoctorDAO;
import dao.UserDAO;
import model.Doctor;
import model.User;
import java.sql.ResultSet;
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


    public void viewAssociatedPatientController(User user)throws SQLException{
        ResultSet rs = doctorDAO.viewAssociatedPatient(user.getId());
        boolean found = false;
        System.out.printf("%-5s %-15s %-12s %-8s %-20s %-12s %-15s%n",
                "PatientID", "Name", "DOB", "Gender", "Address", "Phone", "InsuranceID");
        System.out.println("-------------------------------------------------------------------------------");
        while (rs.next()) {
            found = true;
            int patientId = rs.getInt("PatientID");
            String name = rs.getString("PatientName");
            String dob = rs.getString("DOB");
            String gender = rs.getString("gender");
            String address = rs.getString("address");
            String phone = rs.getString("phone");
            String insuranceID = rs.getString("insuranceID");
            System.out.printf("%-5d %-15s %-12s %-8s %-20s %-12s %-15s%n",
                    patientId, name, dob, gender, address, phone, insuranceID);
        }
        if (!found) {
            System.out.println("No patients found associated with doctor ID: " + user.getId());
        }
        rs.close();
    }


    public void deletePatient(User user)throws SQLException{
        if(user.getRole().equalsIgnoreCase("doctor")){
            viewAssociatedPatientController(user);
            int id = InputValidator.getValidatedInt("Enter Patient Id You Want Do Delete: ");
            String confirmation = "CONFIRM";
            String cnfInput = InputValidator.getValidatedTextField("Write CONFIRM To Delete Patient \n Write Anything Else to go Back");
            if(confirmation.equals(cnfInput)){
                doctorDAO.deletePatient(id);
                System.out.println("Successfully Deleted Patient With ID: "+ id);
            }
        }else {
            System.out.println("Only Doctors can Delete patient.");
        }
    }
}

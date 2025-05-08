package controller;

import dao.UserDAO;
import Validation_helper.InputValidator;

import model.User;


import java.sql.SQLException;


public class UserController {

    private UserDAO userDAO = new UserDAO();

    public User addUser(String role) throws SQLException {
        User user = new User();
        String email = "";

        while (true) {
            System.out.println("Enter Email : ");
            email = InputValidator.getValidatedEmail(email);

            try {
                if (userDAO.isEmailExist(email)) {
                    System.out.println("Email already exists. Please enter a different one.");
                } else {
                    break;
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
                return null;
            }
        }

        user.setEmail(email);
        user.setUser_name(InputValidator.getValidatedName("Enter Your Full Name : "));
        user.setPassword(InputValidator.getValidatedPassword("Set Your Password : "));
        user.setRole(role);

        int userId = userDAO.addUser(user);
        user.setId(userId);

        return user;
    }

    public void deleteUser(User user) throws SQLException {
        int id = InputValidator.getValidatedInt("");
        userDAO.deleteUser(id);
    }

    public void updatePassword () throws SQLException{
        String email = "";
        String password="";
        String cnfPassword="";

        while (true) {
            System.out.println("Enter Email For Which You Want To Change Password: ");
            email = InputValidator.getValidatedEmail(email);

                        if(!userDAO.isEmailExist(email)){
                            System.out.println("Email Does Not Exist, Please Enter Existing Email.");
                            continue;
                        }

                        do {
                            System.out.println("Enter New Password: ");
                            password = InputValidator.getValidatedPassword(password);

                            System.out.println("Enter Confirm Password: ");
                            cnfPassword = InputValidator.getValidatedPassword(cnfPassword);

                            if (!password.equals(cnfPassword)) {
                                System.out.println("Passwords do not match. Please try again.");
                            }
                        } while (!password.equals(cnfPassword));

                        userDAO.updatePassword(email,password);
            System.out.println("Password updated successfully for Email: "+ email);

        }
    }

    public void login() throws SQLException {
        String email = InputValidator.getValidatedEmail("Enter email: ");
        String password = InputValidator.getValidatedPassword("Enter Password: ");

        User user = userDAO.userLogin(email,password);

        if(user != null){
            System.out.println("Login Successful for "+ user.getRole() + " " + user.getUser_name());
        }
    }
}

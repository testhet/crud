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
            email = InputValidator.getValidatedEmail("Enter Email With You Want to Sign-up : ");
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

    public void forgotPassword () throws SQLException{
        String email ;
        String password="";
        String cnfPassword="";
        String dob ;
        while (true) {
            email = InputValidator.getValidatedEmail("Enter Email For Which You Want To Change Password : ");
            System.out.println();
            dob = InputValidator.getValidatedDatePast("Enter Your Date Of birth In \"YYYY-MM-DD\" Format : ");
                        if(!userDAO.isEmailExist(email)){
                            System.out.println("Email Does Not Exist, Please Enter Existing Email.");
                            continue;
                        }
                        if(!userDAO.dobMatch(email,dob)){
                            System.out.println("Provided DOB does not Match With User's DOB.");
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
            break;
        }
    }



    public User login() throws SQLException {
        String email = InputValidator.getValidatedEmail("Enter email: ");
        String password = InputValidator.getValidatedPassword("Enter Password: ");
        User user = userDAO.userLogin(email,password);
        if(user != null){
            System.out.println("Login Successful for "+ user.getRole() + " " + user.getUser_name());
        }
        return user;
    }

    public void updatePassword (User user) throws SQLException{
        String password = "";
        String cnfPassword = "";
        String email = user.getEmail();
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
        System.out.println("Password updated successfully!");

    }
}

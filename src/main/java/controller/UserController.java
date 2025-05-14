package controller;

import dao.UserDAO;
import Validation_helper.InputValidator;
import model.User;
import java.sql.SQLException;



public class UserController {
    private UserDAO userDAO = new UserDAO();


    public User addUser(String role) throws SQLException {
        User user = new User();
        String email ;
        while (true) {
            email = InputValidator.getValidatedEmail("Enter Email With You Want to Sign-up : ");
            try {
                if (userDAO.isEmailExist(email)) {
                    System.out.println("Email already exists. Please enter a different one : ");
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
        String password;
        String cnfPassword;
        String dob ;
        while (true) {
            email = InputValidator.getValidatedEmail("Enter Email For Which You Want To Change Password : ");
            System.out.println();
                        if(!userDAO.isEmailExist(email)){
                            System.out.println("Email Does Not Exist, Please Enter Existing Email.");
                            break;
                        }
                        else if(userDAO.isDoctor(email)){
                            System.out.println("Doctor Can't Forget Password! THEY ARE SMART");
                            break;
                        }
                        dob = InputValidator.getValidatedDatePast("Enter Your Date Of birth In \"YYYY-MM-DD\" Format : ");
                        if(!userDAO.dobMatch(email,dob)){
                            System.out.println("Provided DOB does not Match With User's DOB.");
                            continue;
                        }
                        do {
                            password = InputValidator.getValidatedPassword("Enter New Password: ");
                            System.out.println();
                            cnfPassword = InputValidator.getValidatedPassword("Enter Confirm Password: ");
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
        User user = null;
        String email;
        boolean emailExist;
        do {
            email = InputValidator.getValidatedEmail("Enter Email : (To Exit Enter 0)");
            if("0".equals(email)){
                return null;
            }
            emailExist = userDAO.isEmailExist(email);
            if (!emailExist) {
                System.out.println("User With Provided Email Does Not Exist");
            }
        } while (!emailExist) ;

        while (user == null) {
            String password = InputValidator.getValidatedPassword("Enter Password: ");
            try {
                user = userDAO.userLogin(email, password);
                if (user == null) {
                    System.out.println("Invalid credentials. Please try again.");
                }
            } catch (SQLException e) {
                System.out.println("Login failed due to a system error: " + e.getMessage());
                break;
            }
        }
        assert user != null;
        System.out.println("Login Successful for " + user.getRole() + " " + user.getUser_name());
        return user;
    }


    public void updatePassword (User user) throws SQLException{
        String password ;
        String cnfPassword ;
        String email = user.getEmail();
            do {
                password = InputValidator.getValidatedPassword("Enter New Password (OR Enter 0 TO exit) : ");
                if("0".equals(password)){
                    return;
                }
                cnfPassword = InputValidator.getValidatedPassword("Enter Confirm Password: ");
                if (!password.equals(cnfPassword)) {
                    System.out.println("Passwords do not match. Please try again.");
                }
            } while (!password.equals(cnfPassword));
        userDAO.updatePassword(email,password);
        System.out.println("Password updated successfully!");
    }
}

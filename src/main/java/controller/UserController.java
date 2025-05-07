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


    public void loginUserFromInput() {
        String email = InputValidator.getValidatedEmail("Enter email: ");
        String password = InputValidator.getValidatedPassword("Enter Password: ");

        User user = userDAO.userLogin(email,password);

        if(user != null){
            System.out.println("Login Successful for "+ user.getRole() + " " + user.getUser_name());
        }
    }
}

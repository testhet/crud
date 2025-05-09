package controller;

import model.User;

import java.sql.SQLException;
import java.util.Scanner;

public class consoleController {
    User currentUser = null;
    UserController userController= new UserController();

    Scanner scanner = new Scanner(System.in);

    public void startApp() throws SQLException {

    int choice =0;
    do{
        System.out.println("""
                    
                    === Hospital Management System ===
                    1. Log in
                    2. Doctor Registration
                    3. Patient Registration
                    4. Forgot Password
                    5. Exit
                    
                    """);
        System.out.print("Enter your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.next();
        }
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                handleLogin(scanner);
                break;
            case 2:
                registerDoctor(scanner);
                break;
            case 3:
                registerPatient(scanner);
                break;
            case 4:
                forgotPassword(scanner);
                break;
            case 5:
                System.out.println("Exiting system. Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }while (choice!=5);
    }


    private void handleLogin(Scanner scanner) throws SQLException {
       currentUser = userController.login();
       if(currentUser.getRole().equalsIgnoreCase("doctor")){
           System.out.println("""
                   Welcome to Doctor Menu
                       1.View Appointments
                       2.View Associated Patients
                       3.Logout
                       4.Update Password
                   """);

       } else if (currentUser.getRole().equalsIgnoreCase("patient")) {
           System.out.println("""
                   Welcome To Patient Menu
                       1.Schedule Appointments
                       2.View Appointments
                       3.Cancel Appointments
                       4.Re-Schedule Appointments
                       5.Update Profile
                       6.Update Password
                       7.Logout
                   """);

       }
    }

    private void forgotPassword(Scanner scanner) {

    }

    private void registerPatient(Scanner scanner) {

    }

    private void registerDoctor(Scanner scanner) {

    }
}



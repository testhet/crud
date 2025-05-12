package controller;

import model.User;


import java.sql.SQLException;
import java.util.Scanner;

public class consoleController {
    User currentUser = null;
    UserController userController= new UserController();
    AppointmentController appointmentController = new AppointmentController();
    PatientController patientController = new PatientController();
    DoctorController doctorController = new DoctorController();


    Scanner scanner = new Scanner(System.in);

    public void startApp() throws SQLException {


    int choice ;
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
                handleLogin();
                break;
            case 2:
                System.out.println("Doctor Registration");
                doctorController.registerDoctor();
                break;
            case 3:
                System.out.println("Patient Registration");
                patientController.registerPatient();
                break;
            case 4:
                userController.forgotPassword();
                break;
            case 5:
                System.out.println("Exiting system. Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }while (choice!=5);
    }


    private void handleLogin() throws SQLException {
       currentUser = userController.login();

       if(currentUser.getRole().equalsIgnoreCase("doctor")){
           doctorMenu(currentUser);
       } else if (currentUser.getRole().equalsIgnoreCase("patient")) {
            patientMenu(currentUser);
       }
    }

    private void patientMenu(User currentUser) throws SQLException{
        int choice ;
        do { System.out.println("""
                     Welcome To Patient Menu
                  ---------------------------------
                       1.View Profile
                       2.Schedule Appointments
                       3.View Appointments
                       4.Cancel Appointments
                       5.Re-Schedule Appointments
                       6.Update Profile
                       7.Update Password
                       8.Logout
                      \s""");
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    patientController.viewProfile(currentUser);
                    continue;
                case 2:
                    assert currentUser != null;
                    appointmentController.addAppointmentFromUser(currentUser);
                    continue;
                case 3:
                    assert currentUser != null;
                    appointmentController.viewAppointments(currentUser);
                    continue;
                case 4:
                    assert currentUser != null;
                    appointmentController.viewAppointments(currentUser);
                    appointmentController.updateAppointmentStatus(currentUser);
                    continue;
                case 5:
                    assert currentUser != null;
                    appointmentController.viewAppointments(currentUser);
                    appointmentController.reScheduleAppointment(currentUser);
                    continue;
                case 6:
                    patientController.viewProfile(currentUser);
                    assert currentUser != null;
                    patientController.updatePatient(currentUser);
                    continue;
                case 7:
                    assert currentUser != null;
                    userController.updatePassword(currentUser);
                case 8:
                    System.out.println("logging Out......");
                    currentUser = null;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }while (choice!=8);

    }

    private void doctorMenu(User currentUser) throws SQLException {

        int choice;
        do{ System.out.println("""
                   Welcome to Doctor Menu
                  ------------------------
                       1.View Appointments
                       2.View Associated Patients
                       3.Delete Patients BY ID
                       4.Logout
                       5.Update Password
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
                    appointmentController.viewAppointments(currentUser);
                    break;
                case 2:
                    doctorController.viewAssociatedPatientController(currentUser);
                    break;
                case 3:
                    doctorController.deletePatient(currentUser);
                    break;
                case 4:
                    System.out.println("Logging Out.....");
                    currentUser = null;
                    break;
                case 5:
                    userController.updatePassword(currentUser);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }while (choice!=4);
    }

}





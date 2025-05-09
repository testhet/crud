package controller;

import model.User;


import java.sql.SQLException;
import java.util.Scanner;

public class consoleController {
    User currentUser = null;
    UserController userController= new UserController();
    AppointmentController appointmentController = new AppointmentController(currentUser);
    PatientController patientController = new PatientController(currentUser);
    DoctorController doctorController = new DoctorController(currentUser);


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
                       1.Schedule Appointments
                       2.View Appointments
                       3.Cancel Appointments
                       4.Re-Schedule Appointments
                       5.Update Profile
                       6.Update Password
                       7.Logout
                     \s
                      \s
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
                    appointmentController.addAppointmentFromUser(currentUser);
                    continue;
                case 2:
                    appointmentController.viewAppointments(currentUser);
                    continue;
                case 3:
                    appointmentController.viewAppointments(currentUser);
                    appointmentController.updateAppointmentStatus(currentUser);
                    continue;
                case 4:
                    appointmentController.viewAppointments(currentUser);
                    appointmentController.reScheduleAppointment(currentUser);
                    continue;
                case 5:
                    patientController.viewProfile(currentUser);
                    patientController.updatePatient(currentUser);
                    continue;
                case 6:
                    userController.updatePassword(currentUser);
                case 7:
                    System.out.println("logging Out......");
                    currentUser = null;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }while (choice!=7);

    }

    private void doctorMenu(User currentUser) throws SQLException {

        int choice = 0;
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
                    appointmentController.viewAppointments(this.currentUser);
                    break;
                case 2:
                    doctorController.viewAssociatedPatientController(this.currentUser);
                    break;
                case 3:
                    doctorController.deletePatient(this.currentUser);
                    break;
                case 4:
                    System.out.println("Logging Out.....");
                    this.currentUser = null;
                    break;
                case 5:
                    userController.updatePassword(this.currentUser);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }while (choice!=4);
    }

}





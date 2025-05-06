package main;

import controller.AppointmentController;
import controller.UserController;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        UserController controller = new UserController();
        Scanner scanner = new Scanner(System.in);

        controller.loginUserFromInput();

//        AppointmentController controller = new AppointmentController();
//        Scanner scanner = new Scanner(System.in);
//
//        while (true) {
//            System.out.println("\n1. Add Appointment");
//            System.out.println("2. Update Appointment");
//            System.out.println("0. Exit");
//            System.out.print("Choose an option: ");
//            int choice = scanner.nextInt();
//
//            switch (choice) {
//                case 1:
//                    controller.addAppointmentFromUser();
//                    break;
//                case 2:
//                    controller.updateAppointmentFromUser();
//                    break;
//                case 0:
//                    System.out.println("Exiting...");
//                    return;
//                default:
//                    System.out.println("Invalid option.");
//            }
//        }
    }
}

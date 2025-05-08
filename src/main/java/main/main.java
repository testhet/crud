package main;

import controller.AppointmentController;
import controller.DoctorController;
import controller.PatientController;
import controller.UserController;

import java.sql.SQLException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws SQLException {

        DoctorController controller = new DoctorController();
        Scanner scanner = new Scanner(System.in);

        controller.registerDoctor();

    }
}

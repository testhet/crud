package main;

import controller.PatientController;
import controller.consoleController;
import model.User;

import java.sql.SQLException;

public class main {
    public static void main(String[] args) throws SQLException {
        consoleController controller = new consoleController();
        controller.startApp();

    }
}

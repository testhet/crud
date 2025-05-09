package main;

import controller.consoleController;
import java.sql.SQLException;

public class main {
    public static void main(String[] args) throws SQLException {
        consoleController controller = new consoleController();
        controller.startApp();

    }
}

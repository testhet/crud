package config;

import java.io.FileInputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBconnection {
public static Connection conn= null;

    public static Connection getConnection() {
        if (conn == null){
            Properties properties = new Properties();
            try (FileInputStream file = new FileInputStream("src/main/java/config/config.properties")){
                properties.load(file);

                String url = properties.getProperty("db.url");
                String username = properties.getProperty("db.username");
                String password = properties.getProperty("db.password");

                conn = DriverManager.getConnection(url , username, password);
                System.out.println("db connected Successfully");

            } catch (Exception e) {
                System.out.println("Error connecting to database.");
                e.printStackTrace();
            }
        }
        return conn;
    }
}

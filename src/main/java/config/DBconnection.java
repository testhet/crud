package config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class DBconnection {
    private static HikariDataSource dataSource;

    static {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/java/config/config.properties")) {
            properties.load(input);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getProperty("db.url"));
            config.setUsername(properties.getProperty("db.username"));
            config.setPassword(properties.getProperty("db.password"));
            config.setMaximumPoolSize(1);
            config.setMaxLifetime(600000);
            config.setConnectionTimeout(5000);

            dataSource = new HikariDataSource(config);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}


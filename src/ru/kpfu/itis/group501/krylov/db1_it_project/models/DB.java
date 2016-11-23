/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/19/16 8:11 PM
 * 11-501
 * Task
 */
package ru.kpfu.itis.group501.krylov.db1_it_project.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/19/16 8:11 PM
 * 11-501
 * Task 
 */
class DB {

    private static DB instance = new DB();
    static DB getInstance() {
        return instance;
    }

    private Connection connection;

    Connection getConnection() {
        return connection;
    }

    private DB() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Properties properties = new Properties();
            properties.put("sslmode", "require");
            properties.put("user", System.getProperty("DB_USER"));
            properties.put("password", System.getProperty("DB_PASSWORD"));

            connection = DriverManager.getConnection(
                    "jdbc:postgresql://"+System.getProperty("DB_PATH"), properties
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

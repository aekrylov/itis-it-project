/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/19/16 8:11 PM
 * 11-501
 * Task
 */
package app.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/db1_it_project_v2",
                    "postgres",
                    "postgres"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

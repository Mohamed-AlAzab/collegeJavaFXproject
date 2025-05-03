package com.example.restaurantmanagementsystem.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    public static Connection dbConnection() {
        Connection con = null;
        try {
            // Optional, but good practice:
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String url = "jdbc:oracle:thin:@localhost:1521:XE"; // Corrected URL
            String user = "RESTAURANT";
            String password = "123";

            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Connect Error: " + e.getMessage());
        }
        return con;
    }
}

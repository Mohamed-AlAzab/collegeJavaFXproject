package com.example.restaurantmanagementsystem.model;

import com.example.restaurantmanagementsystem.view.SignInScene;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.restaurantmanagementsystem.view.component.AlertComponent.showAlert;

public class User {
    private String email;
    private String name;
    private String password;

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static boolean emailExists(String email) {
        String sql = "SELECT * FROM USERS WHERE EMAIL = ?";
        try (Connection conn = DB.dbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception ex) {
            System.out.println("Error adding menu item: " + ex.getMessage());
            return false;
        }
    }

    public static boolean registerUser(String username, String email, String password) {
        String sql = "INSERT INTO USERS(NAME, EMAIL, PASSWORD, STAFF) VALUES (?, ?, ?, 0)";
        try (Connection conn = DB.dbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);
            return ps.executeUpdate() == 1;
        } catch (Exception ex) {
            System.out.println("Error adding menu item: " + ex.getMessage());
            return false;
        }
    }
}

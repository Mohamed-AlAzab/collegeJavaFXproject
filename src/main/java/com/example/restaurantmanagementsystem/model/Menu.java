package com.example.restaurantmanagementsystem.model;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static com.example.restaurantmanagementsystem.view.component.AlertComponent.showAlert;

// TODO: Make the menu have menu item (separate it).

public class Menu {
    private int id;
    private String name;
    private double price;
    private String description;

    public Menu(int id, String name, double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public static boolean addItem(int id, String name, double price, String description) {
        String sql = "INSERT INTO menu (id, name, price, description) VALUES (?, ?, ?, ?)";

        try (Connection conn = DB.dbConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setDouble(3, price);
            pst.setString(4, description);
            return pst.executeUpdate() == 1;

        } catch (Exception ex) {
            System.out.println("Error adding menu item: " + ex.getMessage());
            return false;
        }
    }

    public static boolean deleteItem(int id){

        String sql = "DELETE FROM menu WHERE id = ?";
        try (Connection conn = DB.dbConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);

            return pst.executeUpdate() == 1;
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            return false;
        }
    }
}
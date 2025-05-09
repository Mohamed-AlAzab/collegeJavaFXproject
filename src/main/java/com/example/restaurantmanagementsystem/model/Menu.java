package com.example.restaurantmanagementsystem.model;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.restaurantmanagementsystem.view.component.AlertComponent.showAlert;

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

    public static ArrayList<Menu> fetchAllItem(){
        Connection connection=DB.dbConnection();
        String sql="select * from MENU";
        ArrayList<Menu> data = new ArrayList<>();
        try{
            PreparedStatement pst=connection.prepareStatement(sql);
            ResultSet res= pst.executeQuery();
            while (res.next()){
                data.add(new Menu(res.getInt(1),res.getString(2),res.getDouble(3),res.getString(4)));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return data;
    }
}
package com.example.restaurantmanagementsystem.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Order {
    private int id;
    private Date date;

    public Order(int id, Date date) {
        this.id = id;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static boolean insertOrder(int id, String customerName) throws SQLException {
        String sql = "INSERT INTO ORDERS (ID, NAME ,TIME) VALUES (?, ? , ?)";
        try (Connection conn = DB.dbConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.setString(2, customerName);
            return pst.executeUpdate() == 1;
        }
    }

    public static boolean deleteOrder(int id) throws SQLException {
        String sql = "DELETE FROM ORDERS WHERE ORDER_ID = ?";
        try (Connection conn = DB.dbConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate() == 1;
        }
    }
}
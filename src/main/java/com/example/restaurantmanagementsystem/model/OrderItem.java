package com.example.restaurantmanagementsystem.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderItem {
    private int id;
    private int orderId;
    private int menuId;
    private int quantity;

    public OrderItem(int id, int orderId, int menuId, int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.menuId = menuId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static void addOrderItem(int orderId, int menuId, int quantity) {
        String sql = "INSERT INTO order_items (order_id, menu_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DB.dbConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, orderId);
            pst.setInt(2, menuId);
            pst.setInt(3, quantity);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
            throw new RuntimeException("Failed to add order item: " + e.getMessage(), e);
        }
    }
}

package com.example.restaurantmanagementsystem.model;

import java.sql.*;

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

    public static boolean deleteOrder(int orderId) throws SQLException {
        String sql = "DELETE FROM ORDERS WHERE ID = ?";
        try (Connection conn = DB.dbConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, orderId);
            return pst.executeUpdate() == 1;
        }
    }

    public static Integer createOrder() {
        String sql = "INSERT INTO ORDERS (ID, TIME) VALUES (order_seq.NEXTVAL, CURRENT_TIMESTAMP)";
        try (Connection conn = DB.dbConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.executeUpdate();

            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT order_seq.CURRVAL FROM dual")) {
                if (rs.next()) {
                    return rs.getInt(1);  // Return the generated ID
                }
            }
        } catch (SQLException ex){
            System.out.println(ex.toString());
        }
        return null;
    }
}
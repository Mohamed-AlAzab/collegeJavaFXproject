package com.example.restaurantmanagementsystem.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Table {
    int id;
    String type;
    int capacity;
    int isReserved;

    public Table(int id, String type, int capacity, int isReserved) {
        this.type = type;
        this.id = id;
        this.capacity = capacity;
        this.isReserved = isReserved;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getIsReserved() {
        return this.isReserved;
    }

    public void setIsReserved(int isReserved) {
        this.isReserved = isReserved;
    }

    public static boolean tableExists(int id) {
        String sql = "SELECT * FROM TABLES WHERE ID = ?";
        try (Connection conn = DB.dbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception ex) {
            System.out.println("Error adding table item: " + ex.getMessage());
            return false;
        }
    }

    public static boolean addTable(int id, String type, int capacity){
        String sql = "INSERT INTO TABLES(ID, TYPE, CAPACITY, ISRESERVED) VALUES (?, ?, ?, 0)";
        try (Connection conn = DB.dbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, type);
            ps.setInt(3, capacity);
            return ps.executeUpdate() == 1;
        } catch (Exception ex) {
            System.out.println("Error adding Table item: " + ex.getMessage());
            return false;
        }
    }

    public static boolean deleteTable(int id){
        String sql = "DELETE FROM TABLES WHERE ID= ?";
        try (Connection conn = DB.dbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (Exception ex) {
            System.out.println("Error delete Table item: " + ex.getMessage());
            return false;
        }
    }

    public static boolean updateTable(int id, String type, int capacity) {
        String sql = "UPDATE TABLES SET TYPE = ?, CAPACITY = ?, ISRESERVED = 0 WHERE ID = ?";
        try (Connection conn = DB.dbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type);
            ps.setInt(2, capacity);
            ps.setInt(3, id);
            return ps.executeUpdate() == 1;
        } catch (Exception ex) {
            System.out.println("Error updating Table item: " + ex.getMessage());
            return false;
        }
    }
}
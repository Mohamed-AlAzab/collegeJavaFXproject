package com.example.restaurantmanagementsystem.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class Employee {
    private int id;
    private int userId;
    private String name;
    protected double salary;
    protected double tips;
    protected double discount;
    protected double bonus;

    public Employee(int userId, String name, double salary, double tips, double discount, double bonus) {
        this.userId = userId;
        this.name = name;
        this.salary = salary;
        this.tips = tips;
        this.discount = discount;
        this.bonus = bonus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getTips() {
        return tips;
    }

    public void setTips(double tips) {
        this.tips = tips;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public abstract double calculateSalary();

    public abstract String getRole();

    public static Integer getUserID(String email){
        String sql = "SELECT ID FROM USERS WHERE EMAIL = ?";

        try (Connection conn = DB.dbConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt("ID");
            } else { return null; }
        } catch (Exception ex) {
            System.out.println("Error retrieving user ID: " + ex.getMessage());
            return null;
        }
    }

    public static String getEmail(int userId){
        String sql = "SELECT EMAIL FROM USERS WHERE ID = ?";

        try (Connection conn = DB.dbConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getString("EMAIL");
            } else { return null; }
        } catch (Exception ex) {
            System.out.println("Error retrieving user email: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nUser ID: " + userId +
                "\nName: " + name +
                "\nRole: " + getRole() +
                "\nSalary: " + salary +
                "\nTips: " + tips +
                "\nDiscount: " + discount +
                "\nBonus: " + bonus +
                "\nPay: " + calculateSalary() +
                "\n";
    }
}
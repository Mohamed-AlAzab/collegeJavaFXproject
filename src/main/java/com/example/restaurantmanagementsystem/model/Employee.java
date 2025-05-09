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

    public static Employee getEmployeeByID(int id) {
        String sql = "SELECT * FROM EMPLOYEES WHERE ID = ?";

        try (Connection conn = DB.dbConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String role = rs.getString("ROLE");
                int userId = rs.getInt("USER_ID");
                String name = rs.getString("NAME");
                double salary = rs.getDouble("SALARY");
                double discount = rs.getDouble("DISCOUNT");
                double bonus = rs.getDouble("BONUS");
                double tips = rs.getDouble("TIPS");
                double hourlyRate = rs.getDouble("HOURLYRATE");
                double hoursWorked = rs.getDouble("HOURSWORKED");
                int ordersDelivered = rs.getInt("ORDERSDELIVERED");
                double payPerOrder = rs.getDouble("PAYPERORDER");

                return switch (role) {
                    case "Chef" -> new Chef(userId, name, salary, discount, bonus);
                    case "Delivery Person" ->
                            new Delivery(userId, name, ordersDelivered, payPerOrder, tips, discount, bonus);
                    case "Kitchen Assistant" ->
                            new KitchenAssistant(userId, name, hoursWorked, hourlyRate, tips, discount, bonus);
                    case "Manager" -> new Manager(userId, name, salary, discount, bonus);
                    case "Waiter" -> new Waiter(userId, name, tips, hourlyRate, hoursWorked, discount, bonus);
                    default -> {
                        System.out.println("Unknown role: " + role);
                        yield null;
                    }
                };
            } else {
                return null;
            }

        } catch (Exception ex) {
            System.out.println("Error retrieving employee: " + ex.getMessage());
            return null;
        }
    }

    public static Integer getEmployeeID(String email) {
        String sql = "SELECT ID FROM EMPLOYEES WHERE USER_ID = ?";

        try (Connection conn = DB.dbConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            Integer userId = getUserID(email);
            if (userId == null) {
                System.out.println("User ID not found for email: " + email);
                return null;
            }

            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt("ID");
            } else {
                System.out.println("Employee not found for user ID: " + userId);
                return null;
            }
        } catch (Exception ex) {
            System.out.println("Error retrieving employee ID: " + ex.getMessage());
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
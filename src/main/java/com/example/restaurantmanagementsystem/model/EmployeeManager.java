package com.example.restaurantmanagementsystem.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class EmployeeManager {
    private Stack<Operation> operationHistory = new Stack<>();
    private List<Employee> initialState = new ArrayList<>();

    public EmployeeManager() {
        initialState.addAll(getAllEmployeesFromDB());
    }

    public void addEmployee(Employee e) {
        String sql = "INSERT INTO employees (employeeId, name, role, salary, discount, bonus, hoursWorked, hourlyRate, tips, ordersDelivered, payPerOrder) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DB.dbConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, e.getEmployeeId());
            pstmt.setString(2, e.getName());
            pstmt.setString(3, e.getRole());
            pstmt.setDouble(4, e instanceof Chef || e instanceof Manager ? e.salary : 0);

            if (e instanceof Chef) {
                pstmt.setDouble(5, ((Chef) e).getDiscount());
                pstmt.setDouble(6, 0);
                pstmt.setInt(7, 0);
                pstmt.setDouble(8, 0);
                pstmt.setDouble(9, 0);
                pstmt.setInt(10, 0);
                pstmt.setDouble(11, 0);
            } else if (e instanceof Manager) {
                pstmt.setDouble(5, 0);
                pstmt.setDouble(6, ((Manager) e).getBonus());
                pstmt.setInt(7, 0);
                pstmt.setDouble(8, 0);
                pstmt.setDouble(9, 0);
                pstmt.setInt(10, 0);
                pstmt.setDouble(11, 0);
            } else if (e instanceof Waiter) {
                pstmt.setDouble(5, 0);
                pstmt.setDouble(6, 0);
                pstmt.setInt(7, ((Waiter) e).getHoursWorked());
                pstmt.setDouble(8, ((Waiter) e).getHourlyRate());
                pstmt.setDouble(9, ((Waiter) e).getTips());
                pstmt.setInt(10, 0);
                pstmt.setDouble(11, 0);
            } else if (e instanceof Delivery) {
                pstmt.setDouble(5, 0);
                pstmt.setDouble(6, 0);
                pstmt.setInt(7, 0);
                pstmt.setDouble(8, 0);
                pstmt.setDouble(9, 0);
                pstmt.setInt(10, ((Delivery) e).getOrdersDelivered());
                pstmt.setDouble(11, ((Delivery) e).getPayPerOrder());
            } else if (e instanceof KitchenAssistant) {
                pstmt.setDouble(5, 0);
                pstmt.setDouble(6, 0);
                pstmt.setInt(7, ((KitchenAssistant) e).getHoursWorked());
                pstmt.setDouble(8, ((KitchenAssistant) e).getHourlyRate());
                pstmt.setDouble(9, 0);
                pstmt.setInt(10, 0);
                pstmt.setDouble(11, 0);
            } else {
                pstmt.setDouble(5, 0);
                pstmt.setDouble(6, 0);
                pstmt.setInt(7, 0);
                pstmt.setDouble(8, 0);
                pstmt.setDouble(9, 0);
                pstmt.setInt(10, 0);
                pstmt.setDouble(11, 0);
            }

            pstmt.executeUpdate();
            operationHistory.push(new Operation("ADD", e));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeEmployee(int employeeId) {
        Employee employeeToRemove = null;
        for (Employee e : getAllEmployees()) {
            if (e.getEmployeeId() == employeeId) {
                employeeToRemove = e;
                break;
            }
        }

        if (employeeToRemove != null) {
            String sql = "DELETE FROM employees WHERE employeeId = ?";
            try (Connection conn = DB.dbConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, employeeId);
                pstmt.executeUpdate();
                operationHistory.push(new Operation("DELETE", employeeToRemove));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Employee> getAllEmployees() {
        return getAllEmployeesFromDB();
    }

    private List<Employee> getAllEmployeesFromDB() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Connection conn = DB.dbConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int employeeId = rs.getInt("employeeId");
                String name = rs.getString("name");
                String role = rs.getString("role");

                Employee employee = null;
                if (role.equals("Chef")) {
                    double salary = rs.getDouble("salary");
                    double discount = rs.getDouble("discount");
                    employee = new Chef(employeeId, name, salary, discount);
                } else if (role.equals("Manager")) {
                    double salary = rs.getDouble("salary");
                    double bonus = rs.getDouble("bonus");
                    employee = new Manager(employeeId, name, salary, bonus);
                } else if (role.equals("Waiter")) {
                    int hoursWorked = rs.getInt("hoursWorked");
                    double hourlyRate = rs.getDouble("hourlyRate");
                    double tips = rs.getDouble("tips");
                    employee = new Waiter(employeeId, name, tips, hourlyRate, hoursWorked);
                } else if (role.equals("Delivery Person")) {
                    int ordersDelivered = rs.getInt("ordersDelivered");
                    double payPerOrder = rs.getDouble("payPerOrder");
                    employee = new Delivery(employeeId, name, ordersDelivered, payPerOrder);
                } else if (role.equals("Kitchen Assistant")) {
                    int hoursWorked = rs.getInt("hoursWorked");
                    double hourlyRate = rs.getDouble("hourlyRate");
                    employee = new KitchenAssistant(employeeId, name, hoursWorked, hourlyRate);
                }

                if (employee != null) {
                    employees.add(employee);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employees;
    }

    public void undoLastOperation() {
        if (operationHistory.isEmpty()) {
            return;
        }

        Operation lastOp = operationHistory.pop();
        if (lastOp.getType().equals("ADD")) {
            removeEmployee(lastOp.getEmployee().getEmployeeId());
        } else if (lastOp.getType().equals("DELETE")) {
            addEmployee(lastOp.getEmployee());
        }
    }

    public void resetToInitialState() {
        String deleteSql = "DELETE FROM employees";
        try (Connection conn = DB.dbConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        for (Employee e : initialState) {
            addEmployee(e);
        }
        operationHistory.clear();
    }
}
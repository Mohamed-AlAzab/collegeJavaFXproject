package com.example.restaurantmanagementsystem.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.example.restaurantmanagementsystem.controller.EmployeeController.getEmail;
import static com.example.restaurantmanagementsystem.controller.UserController.*;

public class EmployeeManager {
    private Stack<Operation> operationHistory = new Stack<>();
    private List<Employee> initialState = new ArrayList<>();

    public EmployeeManager() {
        initialState.addAll(fetchAllEmployees());
    }

    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO EMPLOYEES (name, role, salary, discount, bonus, hoursWorked, hourlyRate, tips, ordersDelivered, payPerOrder, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DB.dbConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, employee.getName());
            pst.setString(2, employee.getRole());
            pst.setDouble(3, employee.getSalary());
            pst.setDouble(4, employee.getDiscount());
            pst.setDouble(5, employee.getBonus());

            switch (employee) {
                case Manager manager ->{
                    pst.setNull(6, Types.DOUBLE);
                    pst.setNull(7, Types.DOUBLE);
                    pst.setNull(8, Types.DOUBLE);
                    pst.setNull(9, Types.INTEGER);
                    pst.setNull(10, Types.DOUBLE);
                }
                case Chef chef->{
                    pst.setNull(6, Types.DOUBLE);
                    pst.setNull(7, Types.DOUBLE);
                    pst.setNull(8, Types.DOUBLE);
                    pst.setNull(9, Types.INTEGER);
                    pst.setNull(10, Types.DOUBLE);
                }
                case Waiter waiter -> {
                    pst.setDouble(6, ((Waiter) employee).getHoursWorked());
                    pst.setDouble(7, ((Waiter) employee).getHourlyRate());
                    pst.setDouble(8, employee.getTips());
                    pst.setNull(9, Types.INTEGER);
                    pst.setNull(10, Types.DOUBLE);
                }
                case Delivery delivery -> {
                    pst.setNull(6, Types.DOUBLE);
                    pst.setNull(7, Types.DOUBLE);
                    pst.setDouble(8, employee.getTips());
                    pst.setInt(9, ((Delivery) employee).getOrdersDelivered());
                    pst.setDouble(10, ((Delivery) employee).getPayPerOrder());
                }
                case KitchenAssistant assistant -> {
                    pst.setDouble(6, ((KitchenAssistant) employee).getHoursWorked());
                    pst.setDouble(7, ((KitchenAssistant) employee).getHourlyRate());
                    pst.setDouble(8, employee.getTips());
                    pst.setNull(9, Types.INTEGER);
                    pst.setNull(10, Types.DOUBLE);
                }
                default -> {
                    pst.setNull(6, Types.DOUBLE);
                    pst.setNull(7, Types.DOUBLE);
                    pst.setDouble(8, employee.getTips());
                    pst.setNull(9, Types.INTEGER);
                    pst.setNull(10, Types.DOUBLE);
                }
            }

            pst.setInt(11, employee.getUserId());
            pst.executeUpdate();
            addToStaff(getEmail(employee.getUserId()));
            operationHistory.push(new Operation("ADD", employee, getEmail(employee.getUserId())));
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public void removeEmployee(int id) {
        Employee employeeToRemove = fetchAllEmployees().stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);

        if (employeeToRemove != null) {
            String sql = "DELETE FROM employees WHERE id = ?";
            try (Connection conn = DB.dbConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, id);
                pst.executeUpdate();
                removeFromStaff(getEmail(id));
                operationHistory.push(new Operation("DELETE", employeeToRemove, getEmail(employeeToRemove.getId())));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void updateEmployee(Employee employee, String oldEmail) {
        Employee originalEmployee = fetchAllEmployees().stream()
                .filter(e -> e.getId() == employee.getId())
                .findFirst()
                .orElse(null);
        if (originalEmployee == null) {
            return;
        }
        String sql = "UPDATE employees SET name = ?, role = ?, salary = ?, tips = ?, discount = ?, bonus = ?, hoursWorked = ?, hourlyRate = ?, ordersDelivered = ?, payPerOrder = ?, user_Id = ? WHERE id = ?";

        try (Connection conn = DB.dbConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, employee.getName());
            pst.setString(2, employee.getRole());
            pst.setDouble(3, employee.getSalary());
            pst.setDouble(4, employee.getTips());
            pst.setDouble(5, employee.getDiscount());
            pst.setDouble(6, employee.getBonus());

            if (employee instanceof Waiter waiter) {
                pst.setDouble(7, waiter.getHoursWorked());
                pst.setDouble(8, waiter.getHourlyRate());
                pst.setNull(9, Types.INTEGER);
                pst.setNull(10, Types.DOUBLE);
            } else if (employee instanceof Delivery delivery) {
                pst.setNull(7, Types.INTEGER);
                pst.setNull(8, Types.DOUBLE);
                pst.setInt(9, delivery.getOrdersDelivered());
                pst.setDouble(10, delivery.getPayPerOrder());
            } else if (employee instanceof KitchenAssistant assistant) {
                pst.setDouble(7, assistant.getHoursWorked());
                pst.setDouble(8, assistant.getHourlyRate());
                pst.setNull(9, Types.INTEGER);
                pst.setNull(10, Types.DOUBLE);
            } else {
                pst.setNull(7, Types.INTEGER);
                pst.setNull(8, Types.DOUBLE);
                pst.setNull(9, Types.INTEGER);
                pst.setNull(10, Types.DOUBLE);
            }

            pst.setInt(11, employee.getUserId());
            pst.setInt(12, employee.getId());
            pst.executeUpdate();

            if(!getEmail(employee.getUserId()).equals(oldEmail)){
                System.out.println("Old Email: " + oldEmail);
                addToStaff(getEmail(employee.getUserId()));
                removeFromStaff(oldEmail);
            }

            operationHistory.push(new Operation("UPDATE", originalEmployee, getEmail(employee.getUserId())));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Employee> fetchAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";

        try (Connection conn = DB.dbConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employee employee = createEmployeeFromResultSet(rs);
                if (employee != null) {
                    employees.add(employee);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return employees;
    }

    private Employee createEmployeeFromResultSet(ResultSet rs) throws SQLException {
        int employeeId = rs.getInt("id");
        int userId = rs.getInt("user_id");
        String name = rs.getString("name");
        String role = rs.getString("role");
        double salary = rs.getDouble("salary");
        double tips = rs.getDouble("tips");
        double discount = rs.getDouble("discount");
        double bonus = rs.getDouble("bonus");

        Employee employee = switch (role) {
            case "Chef" -> new Chef(userId, name, salary, discount, bonus);
            case "Manager" -> new Manager(userId, name, salary, discount, bonus);
            case "Waiter" -> new Waiter(
                    userId, name, tips, rs.getDouble("hourlyRate"),
                    rs.getDouble("hoursWorked"), discount, bonus
            );
            case "Delivery Person" -> new Delivery(
                    userId, name, rs.getInt("ordersDelivered"),
                    rs.getDouble("payPerOrder"), tips, discount, bonus
            );
            case "Kitchen Assistant" -> new KitchenAssistant(
                    userId, name, rs.getDouble("hoursWorked"),
                    rs.getDouble("hourlyRate"), tips, discount, bonus
            );
            default -> null;
        };

        if (employee != null) {
            employee.setId(employeeId);
        }
        return employee;
    }

    public void undoLastOperation() {
        if (!operationHistory.isEmpty()) {
            Operation lastOp = operationHistory.pop();
            if (lastOp.getType().equals("ADD")) {
                removeEmployee(lastOp.getEmployee().getId());
            } else if (lastOp.getType().equals("DELETE")) {
                addEmployee(lastOp.getEmployee());
            } else if (lastOp.getType().equals("UPDATE")) {
                updateEmployee(lastOp.getEmployee(), lastOp.getOldEmail());
            }
        }
    }

    public void resetToInitialState() {
        clearDatabase();
        initialState.forEach(this::addEmployee);
        operationHistory.clear();
    }

    private void clearDatabase() {
        String deleteSql = "DELETE FROM employees";
        try (Connection conn = DB.dbConnection();
             PreparedStatement pst = conn.prepareStatement(deleteSql)) {
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    private static class Operation {
        private final String type;
        private final Employee employee;
        private final String oldEmail;

        public Operation(String type, Employee employee, String oldEmail) {
            this.type = type;
            this.employee = employee;
            this.oldEmail = oldEmail;
        }

        public String getOldEmail() {
            return oldEmail;
        }

        public String getType() {
            return type;
        }

        public Employee getEmployee() {
            return employee;
        }
    }
}
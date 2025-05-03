package com.example.restaurantmanagementsystem.model;

public abstract class Employee {
    protected int employeeId;
    protected String name;
    protected double salary;

    public Employee(int employeeId, String name, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.salary = salary;
    }

    public abstract double calculateSalary();

    public abstract String getRole();

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ID: " + employeeId +
                "\nName: " + name +
                "\nRole: " + getRole() +
                "\nPay: " + calculateSalary() +
                "\n";
    }
}

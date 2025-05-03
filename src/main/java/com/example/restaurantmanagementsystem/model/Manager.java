package com.example.restaurantmanagementsystem.model;

public class Manager extends Employee {
    private double bonus;

    public Manager(int employeeId, String name, double salary, double bonus) {
        super(employeeId, name, salary);
        this.bonus = bonus;
    }

    @Override
    public double calculateSalary() {
        return this.salary + bonus;
    }

    @Override
    public String getRole() {
        return "Manager";
    }

    public double getBonus() {
        return bonus;
    }
}
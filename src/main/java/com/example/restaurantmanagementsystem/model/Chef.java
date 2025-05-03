package com.example.restaurantmanagementsystem.model;

public class Chef extends Employee {
    private double discount;

    public Chef(int employeeId, String name, double salary, double discount) {
        super(employeeId, name, salary);
        this.discount = discount;
    }

    @Override
    public double calculateSalary() {
        return this.salary - discount;
    }

    @Override
    public String getRole() {
        return "Chef";
    }

    public double getDiscount() {
        return discount;
    }
}

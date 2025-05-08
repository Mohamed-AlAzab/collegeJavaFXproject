package com.example.restaurantmanagementsystem.model;

public class Chef extends Employee {
    public Chef(int userId, String name, double salary, double discount, double bonus) {
        super(userId, name, salary, 0, discount, bonus);
    }

    @Override
    public double calculateSalary() {
        return this.salary + this.bonus - this.discount;
    }

    @Override
    public String getRole() {
        return "Chef";
    }
}
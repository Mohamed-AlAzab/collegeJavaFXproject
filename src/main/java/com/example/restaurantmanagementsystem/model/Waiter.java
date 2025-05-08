package com.example.restaurantmanagementsystem.model;

public class Waiter extends Employee {
    private double hourlyRate;
    private double hoursWorked;

    public Waiter(int userId, String name, double tips, double hourlyRate, double hoursWorked, double discount, double bonus) {
        super(userId, name, 0, tips, discount, bonus);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return (hourlyRate * hoursWorked) + this.tips + this.bonus - this.discount;
    }

    @Override
    public String getRole() {
        return "Waiter";
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
}
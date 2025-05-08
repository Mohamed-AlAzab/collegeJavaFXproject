package com.example.restaurantmanagementsystem.model;

public class KitchenAssistant extends Employee {
    private double hoursWorked;
    private double hourlyRate;

    public KitchenAssistant(int userId, String name, double hoursWorked, double hourlyRate, double tips, double discount, double bonus) {
        super(userId, name, 0, tips, discount, bonus);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }
    @Override
    public double calculateSalary() {
        return (hoursWorked * hourlyRate) + this.tips + this.bonus - this.discount;
    }
    @Override
    public String getRole() {
        return "Kitchen Assistant";
    }
    public double getHoursWorked() {
        return hoursWorked;
    }
    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
    public double getHourlyRate() {
        return hourlyRate;
    }
    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}
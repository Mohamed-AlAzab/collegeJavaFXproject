package com.example.restaurantmanagementsystem.model;

public class KitchenAssistant extends Employee {
    private int hoursWorked;
    private double hourlyRate;

    public KitchenAssistant(int employeeId, String name, int hoursWorked, double hourlyRate) {
        super(employeeId, name, 0);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public String getRole() {
        return "Kitchen Assistant";
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }
}
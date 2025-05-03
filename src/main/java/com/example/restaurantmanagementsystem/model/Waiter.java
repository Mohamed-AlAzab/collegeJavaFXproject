package com.example.restaurantmanagementsystem.model;

public class Waiter extends Employee {
    private int hoursWorked;
    private double hourlyRate;
    private double tips;

    public Waiter(int employeeId, String name, double tips, double hourlyRate, int hoursWorked) {
        super(employeeId, name, 0);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
        this.tips = tips;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked + tips;
    }

    @Override
    public String getRole() {
        return "Waiter";
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public double getTips() {
        return tips;
    }
}
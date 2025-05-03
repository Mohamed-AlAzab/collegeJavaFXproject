package com.example.restaurantmanagementsystem.model;

public class Operation {
    private String type;
    private Employee employee;

    public Operation(String type, Employee employee) {
        this.type = type;
        this.employee = employee;
    }

    public String getType() {
        return type;
    }

    public Employee getEmployee() {
        return employee;
    }
}
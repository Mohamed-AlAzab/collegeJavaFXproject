package com.example.restaurantmanagementsystem.model;

public class Delivery extends Employee {
    private int ordersDelivered;
    private double payPerOrder;

    public Delivery(int employeeId, String name, int ordersDelivered, double payPerOrder) {
        super(employeeId, name, 0);
        this.ordersDelivered = ordersDelivered;
        this.payPerOrder = payPerOrder;
    }

    @Override
    public double calculateSalary() {
        return ordersDelivered * payPerOrder;
    }

    @Override
    public String getRole() {
        return "Delivery Person";
    }

    public int getOrdersDelivered() {
        return ordersDelivered;
    }

    public double getPayPerOrder() {
        return payPerOrder;
    }
}
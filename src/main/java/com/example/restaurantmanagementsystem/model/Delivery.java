package com.example.restaurantmanagementsystem.model;

public class Delivery extends Employee {
    private int ordersDelivered;
    private double payPerOrder;

    public Delivery(int userId, String name, int ordersDelivered, double payPerOrder, double tips, double discount, double bonus) {
        super(userId, name, 0, tips, discount, bonus);
        this.ordersDelivered = ordersDelivered;
        this.payPerOrder = payPerOrder;
    }

    @Override
    public double calculateSalary() {
        return (ordersDelivered * payPerOrder) + this.tips + this.bonus - this.discount;
    }

    @Override
    public String getRole() {
        return "Delivery Person";
    }

    public int getOrdersDelivered() {
        return ordersDelivered;
    }

    public void setOrdersDelivered(int ordersDelivered) {
        this.ordersDelivered = ordersDelivered;
    }

    public double getPayPerOrder() {
        return payPerOrder;
    }

    public void setPayPerOrder(double payPerOrder) {
        this.payPerOrder = payPerOrder;
    }
}
package com.example.restaurantmanagementsystem.model;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private String customerName;
    private LocalDate date;

    public Reservation(int id, String customerName, LocalDate date) {
        this.id = id;
        this.customerName = customerName;
        this.date = date;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
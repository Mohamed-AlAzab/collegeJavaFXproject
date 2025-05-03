package com.example.restaurantmanagementsystem.model;

import java.time.LocalDate;

public class Reservation {
    private int tableId;
    private String customerName;
    private LocalDate date;

    public Reservation(int tableId, String customerName, LocalDate date) {
        this.tableId = tableId;
        this.customerName = customerName;
        this.date = date;
    }

    public int getTableId() {
        return this.tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
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
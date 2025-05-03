package com.example.restaurantmanagementsystem.model;

public class Table {
    String type;
    int tableID;
    int capacity;
    int isReserved;

    public Table(String type, int tableID, int capacity, int isReserved) {
        this.type = type;
        this.tableID = tableID;
        this.capacity = capacity;
        this.isReserved = isReserved;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTableID() {
        return this.tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getIsReserved() {
        return this.isReserved;
    }

    public void setIsReserved(int isReserved) {
        this.isReserved = isReserved;
    }
}
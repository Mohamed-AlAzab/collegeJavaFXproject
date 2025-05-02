package com.example.restaurantmanagementsystem.controller;

import com.example.restaurantmanagementsystem.model.Menu;

public class MenuController {
    public static boolean addItem(int id, String name, double price, String description) {
        return Menu.addItem(id, name, price, description);
    }

    public static boolean deleteItem(int id) {
        return Menu.deleteItem(id);
    }
}

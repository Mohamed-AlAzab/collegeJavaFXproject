package com.example.restaurantmanagementsystem.controller;

import com.example.restaurantmanagementsystem.model.Menu;

import java.util.ArrayList;

public class MenuController {
    public static boolean addItem(int id, String name, double price, String description) {
        return Menu.addItem(id, name, price, description);
    }

    public static boolean deleteItem(int id) {
        return Menu.deleteItem(id);
    }

    public static ArrayList<Menu> fetchAllItem(){
        return Menu.fetchAllItem();
    }
}

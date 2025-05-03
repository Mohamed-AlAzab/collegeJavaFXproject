package com.example.restaurantmanagementsystem.controller;

import com.example.restaurantmanagementsystem.model.User;


public class UserController {
    public static String register(String username, String email, String password) {
        if (User.emailExists(email)) {
            return "exists";
        }
        if (User.registerUser(username, email, password)) {
            return "success";
        } else {
            return "failure";
        }
    }
}

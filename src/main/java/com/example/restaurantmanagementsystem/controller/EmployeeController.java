package com.example.restaurantmanagementsystem.controller;

import com.example.restaurantmanagementsystem.model.Employee;

public class EmployeeController {
    public static Integer getUserID(String email){
        return Employee.getUserID(email);
    }

    public static String getEmail(int userId){
        return Employee.getEmail(userId);
    }
}
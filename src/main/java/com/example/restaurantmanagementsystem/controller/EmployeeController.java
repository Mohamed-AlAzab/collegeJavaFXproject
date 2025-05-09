package com.example.restaurantmanagementsystem.controller;

import com.example.restaurantmanagementsystem.model.Employee;

public class EmployeeController {
    public static Integer getUserID(String email){
        return Employee.getUserID(email);
    }

    public static String getEmail(int userId){
        return Employee.getEmail(userId);
    }

    public static Employee getEmployeeByID(int id){
        return Employee.getEmployeeByID(id);
    }

    public static Integer getEmployeeID(String email){
        return Employee.getEmployeeID(email);
    }
}
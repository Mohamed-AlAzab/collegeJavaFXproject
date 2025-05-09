package com.example.restaurantmanagementsystem.view;


import com.example.restaurantmanagementsystem.model.*;
import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static com.example.restaurantmanagementsystem.controller.EmployeeController.getEmployeeByID;

public class EmployeeScene {
    private GridPane gridPane = new GridPane();
    private Stage stage;

    public EmployeeScene(Stage stage, int employeeId) {
        this.stage = stage;
        initControls(employeeId);
    }

    private void initControls(int employeeId) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        Employee emp = getEmployeeByID(employeeId);

        if (emp != null) {
            int row = 0;
            gridPane.add(new Label("Name: " + emp.getName()), 0, row++);
            gridPane.add(new Label("Role: " + emp.getRole()), 0, row++);
            gridPane.add(new Label("Base Salary: " + emp.getSalary()), 0, row++);
            gridPane.add(new Label("Tips: " + emp.getTips()), 0, row++);
            gridPane.add(new Label("Discount: " + emp.getDiscount()), 0, row++);
            gridPane.add(new Label("Bonus: " + emp.getBonus()), 0, row++);
            gridPane.add(new Label("Total Salary: " + emp.calculateSalary()), 0, row++);

            switch (emp) {
                case Delivery delivery -> {
                    gridPane.add(new Label("Orders Delivered: " + delivery.getOrdersDelivered()), 0, row++);
                    gridPane.add(new Label("Pay Per Order: " + delivery.getPayPerOrder()), 0, row++);
                }
                case Waiter waiter -> {
                    gridPane.add(new Label("Hourly Rate: " + waiter.getHourlyRate()), 0, row++);
                    gridPane.add(new Label("Hours Worked: " + waiter.getHoursWorked()), 0, row++);
                }
                case KitchenAssistant assistant -> {
                    gridPane.add(new Label("Hourly Rate: " + assistant.getHourlyRate()), 0, row++);
                    gridPane.add(new Label("Hours Worked: " + assistant.getHoursWorked()), 0, row++);
                }
                default -> {
                }
            }
        } else {
            gridPane.add(new Label("Employee not found."), 0, 0);
        }
    }

    public Scene getScene() {
        return new Scene(gridPane, SceneSize.width, SceneSize.height);
    }
}
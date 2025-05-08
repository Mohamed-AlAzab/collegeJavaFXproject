package com.example.restaurantmanagementsystem.controller;

import com.example.restaurantmanagementsystem.model.Table;
import javafx.scene.control.Alert;

import static com.example.restaurantmanagementsystem.view.component.AlertComponent.showAlert;

public class TableController {
    public static void addTable(int id, String type, int capacity){
        if(Table.addTable(id, type, capacity)){
            showAlert(Alert.AlertType.INFORMATION, "Success", "Add table successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Error adding table");
        }
    }

    public static void deleteTable(int id){
        if(Table.tableExists(id)){
            if(Table.deleteTable(id)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Deleted table successfully!");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Not Found", "Table not found");
        }
    }

    public static void updateTable(int id, String type, int capacity){
        if(Table.tableExists(id)){
            if(Table.updateTable(id, type, capacity)){
                showAlert(Alert.AlertType.INFORMATION, "Success", "Update table successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Error updating table");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Not Found", "Table not found");
        }
    }
}
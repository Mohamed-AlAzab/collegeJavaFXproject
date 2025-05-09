package com.example.restaurantmanagementsystem.controller;

import com.example.restaurantmanagementsystem.model.User;
import com.example.restaurantmanagementsystem.view.AdminScene;
import com.example.restaurantmanagementsystem.view.EmployeeScene;
import com.example.restaurantmanagementsystem.view.MainScene;
import com.example.restaurantmanagementsystem.view.SignInScene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import static com.example.restaurantmanagementsystem.controller.EmployeeController.getEmployeeID;
import static com.example.restaurantmanagementsystem.view.component.AlertComponent.showAlert;


public class UserController {
    public static void register(String username, String email, String password, Stage stage) {
        if (User.emailExists(email)) {
            stage.setScene(new SignInScene(stage).getScene());
            showAlert(Alert.AlertType.INFORMATION, "Alert", "Already have account");
            return ;
        }
        if (User.registerUser(username, email, password)) {
            stage.setScene(new SignInScene(stage).getScene());
            showAlert(Alert.AlertType.INFORMATION, "Success", "Account created successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed", "Failed to create account.");
        }
    }

    public static void login(String email, String password, Stage stage) {
        if (User.loginUser(email, password)) {
            if(User.staff(email) == 2){
                stage.setScene(new AdminScene(stage).getScene());
            } else if (User.staff(email) == 1) {
                stage.setScene(new EmployeeScene(stage, getEmployeeID(email)).getScene());
            } else {
                stage.setScene(new MainScene(stage).getScene());
            }
        } else {
            showAlert(Alert.AlertType.ERROR,"Wrong", "Wrong password or username");
        }
    }

    public static void addToStaff(String email) {
        User.addToStaff(email);
    }

    public static void removeFromStaff(String email) {
        User.removeFromStaff(email);
    }
}

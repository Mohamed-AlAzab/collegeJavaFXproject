package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.model.DB;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import static com.example.restaurantmanagementsystem.controller.UserController.register;
import static com.example.restaurantmanagementsystem.view.component.AlertComponent.showAlert;

public class SignUpScene {
    Connection connection = DB.dbConnection();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    Label title = new Label("Sign Up");

    TextField userTextField = new TextField();
    TextField emailTextField = new TextField();
    PasswordField passwordField = new PasswordField();
    PasswordField confirmPasswordField = new PasswordField();

    Button signupButton = new Button("Sign Up");

    Text first = new Text("Already have an account? ");
    Text second = new Text("Log in");
    TextFlow textFlow = new TextFlow(first, second);

    GridPane gridPane = new GridPane();
    Stage stage;

    public SignUpScene(Stage stage) {
        this.stage = stage;
        initControls();
        initActions();
    }

    public void initControls () {
        userTextField.setPromptText("Username");
        emailTextField.setPromptText("Email");
        passwordField.setPromptText("Password");
        confirmPasswordField.setPromptText("Confirm Password");

        gridPane.add(title, 0,0,2,1);
        gridPane.add(userTextField, 0, 1);
        gridPane.add(emailTextField, 0, 2);
        gridPane.add(passwordField, 0, 3);
        gridPane.add(confirmPasswordField, 0, 4);
        gridPane.add(textFlow, 0, 5);
        gridPane.add(signupButton, 0, 6);
        signupButton.setMaxWidth(Double.MAX_VALUE);

        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        second.setFill(javafx.scene.paint.Color.BLUE);
    }

    public void initActions () {
        signupButton.setOnAction(e -> {
            if (emailTextField.getText().isEmpty() || userTextField.getText().isEmpty() || passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR,"Alert", "There are empty field");
                return;
            }
            if (!isValidEmail(emailTextField.getText())) {
                showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
                return;
            }
            if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                showAlert(Alert.AlertType.ERROR,"", "Passwords don't match");
                return;
            }
            String email = emailTextField.getText();
            String username = userTextField.getText();
            String password = passwordField.getText();
            /*
                String salt = PasswordUtil.salt;
                String hashedPassword = PasswordUtil.hashPassword(password, salt);
            */
            String result = register(email, username, password);
            switch (result) {
                case "exists":
                    stage.setScene(new SignInScene(stage).getScene());
                    showAlert(Alert.AlertType.INFORMATION, "Alert", "Already have account");
                    break;
                case "success":
                    stage.setScene(new SignInScene(stage).getScene());
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Account created successfully!");
                    break;
                case "failure":
                    showAlert(Alert.AlertType.ERROR, "Failed", "Failed to create account.");
                    break;
                case "error":
                    showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred.");
                    break;
            }
        });
        second.setOnMouseClicked(e -> {
            stage.setScene(new SignInScene(stage).getScene());
        });
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    public Scene getScene () {
        return new Scene(gridPane,1100,850);
    }
}
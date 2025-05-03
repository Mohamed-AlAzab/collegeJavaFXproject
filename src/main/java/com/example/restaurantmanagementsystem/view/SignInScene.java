package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.model.DB;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.restaurantmanagementsystem.view.component.AlertComponent.showAlert;

public class SignInScene {
    Connection connection = DB.dbConnection();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    Label title = new Label("Log In");

    TextField emailTextField = new TextField();
    PasswordField passwordField = new PasswordField();

    Text first = new Text("Don't have an account? ");
    Text second = new Text("Sign up");
    TextFlow textFlow = new TextFlow(first, second);

    Button loginButton = new Button("Log In");
    Button Back = new Button("Back");

    GridPane gridPane = new GridPane();
    Stage stage;

    public SignInScene(Stage stage){
        this.stage = stage;
        initControls();
        initActions();
    }

    public void initControls () {
        emailTextField.setPromptText("Email");
        passwordField.setPromptText("Password");

        gridPane.add(title, 0,0,2,1);
        gridPane.add(emailTextField, 0, 1);
        gridPane.add(passwordField, 0, 2);
        gridPane.add(textFlow, 0, 3);
        gridPane.add(loginButton, 0, 4);
        loginButton.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(Back, 0, 5);

        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        second.setFill(javafx.scene.paint.Color.BLUE);
    }

    public void initActions () {
        loginButton.setOnAction(e -> {
            if (emailTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR,"", "Enter all the fields");
                return;
            }
            String username = emailTextField.getText();
            String password = passwordField.getText();
            // String salt = PasswordUtil.salt;
            // String hashedPassword = PasswordUtil.hashPassword(password, salt);
            String sql1 = "SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD = ?";
            try {
                preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    stage.setScene(new AdminScene(stage).getScene());
                }
                else {
                    showAlert(Alert.AlertType.ERROR,"Wrong", "Wrong password or email");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        });
        Back.setOnAction(e -> {
            stage.setScene(new MainScene(stage).getScene());
        });
        second.setOnMouseClicked(e -> {
            stage.setScene(new SignUpScene(stage).getScene());
        });
    }

    public Scene getScene () {
        return new Scene(gridPane,1100,850);
    }
}
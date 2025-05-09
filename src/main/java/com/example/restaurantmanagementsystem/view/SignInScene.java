package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.Objects;

import static com.example.restaurantmanagementsystem.controller.UserController.login;
import static com.example.restaurantmanagementsystem.view.component.AlertComponent.showAlert;

public class SignInScene {
    Label title = new Label("Log In");

    TextField emailTextField = new TextField();
    PasswordField passwordField = new PasswordField();

    Text first = new Text("Don't have an account? ");
    Text signUpButton = new Text("Sign up");
    TextFlow textFlow = new TextFlow(first, signUpButton);

    Button loginButton = new Button("Log In");
    Button backButton = new Button("Back");

    GridPane gridPane = new GridPane();
    Stage stage;

    public SignInScene(Stage stage){
        this.stage = stage;
        initControls();
        initActions();
    }

    public void initControls () {
        emailTextField.setPromptText("Username");
        passwordField.setPromptText("Password");

        gridPane.add(title, 0,0,2,1);
        gridPane.add(emailTextField, 0, 1);
        gridPane.add(passwordField, 0, 2);
        gridPane.add(textFlow, 0, 3);
        gridPane.add(loginButton, 0, 4);
        gridPane.add(backButton, 0, 5);
        loginButton.setMaxWidth(Double.MAX_VALUE);
        backButton.setMaxWidth(Double.MAX_VALUE);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        signUpButton.setFill(javafx.scene.paint.Color.BLUE);

        emailTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
                event.consume();
            }
        });
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButton.fire();
                event.consume();
            }
        });
    }

    public void initActions () {
        loginButton.setOnAction(e -> {
            if (emailTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR,"", "Enter all the fields");
                return;
            }
            String email = emailTextField.getText();
            String password = passwordField.getText();
            // String salt = PasswordUtil.salt;
            // String hashedPassword = PasswordUtil.hashPassword(password, salt);
            login(email, password, stage);
        });
        backButton.setOnAction(e -> {
            stage.setScene(new MainScene(stage).getScene());
        });
        signUpButton.setOnMouseClicked(e -> {
            stage.setScene(new SignUpScene(stage).getScene());
        });
    }

    public Scene getScene () {
        Scene scene=new Scene(gridPane, SceneSize.width,SceneSize.height);
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("/com/example/restaurantmanagementsystem/Style.css")).toExternalForm());
        return scene;
    }
}
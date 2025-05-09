package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.Objects;

import static com.example.restaurantmanagementsystem.controller.UserController.register;
import static com.example.restaurantmanagementsystem.view.component.AlertComponent.showAlert;

public class SignUpScene {
    Label title = new Label("Sign Up");

    TextField userTextField = new TextField();
    TextField emailTextField = new TextField();
    PasswordField passwordField = new PasswordField();
    PasswordField confirmPasswordField = new PasswordField();

    Button signupButton = new Button("Sign Up");
    Button backButton = new Button("Back");

    Text first = new Text("Already have an account? ");
    Text loginButton = new Text("Log in");
    TextFlow textFlow = new TextFlow(first, loginButton);

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
        gridPane.add(backButton,0,7);
        signupButton.setMaxWidth(Double.MAX_VALUE);
        backButton.setMaxWidth(Double.MAX_VALUE);

        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        loginButton.setFill(javafx.scene.paint.Color.BLUE);

        userTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                emailTextField.requestFocus();
                event.consume();
            }
        });
        emailTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
                event.consume();
            }
        });
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                confirmPasswordField.requestFocus();
                event.consume();
            }
        });
        confirmPasswordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                signupButton.fire();
                event.consume();
            }
        });
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
            register(username, email, password, stage);
        });
        loginButton.setOnMouseClicked(e -> {
            stage.setScene(new SignInScene(stage).getScene());
        });
        backButton.setOnAction(e->{
            stage.setScene(new MainScene(stage).getScene());
        });
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z][A-Za-z0-9]*\\.[A-Za-z]+$";
        return email.matches(emailRegex);
    }

    public Scene getScene () {
        Scene scene=new Scene(gridPane, SceneSize.width, SceneSize.height);
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("/com/example/restaurantmanagementsystem/Style.css")).toExternalForm());
        return scene;
    }
}
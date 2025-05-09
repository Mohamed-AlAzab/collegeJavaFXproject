package com.example.restaurantmanagementsystem.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {
    public void start(Stage stage) {
        Scene mainScene = new MainScene(stage).getScene();
        mainScene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("/com/example/restaurantmanagementsystem/Style.css")).toExternalForm());        stage.setTitle("Restaurant");
        stage.setMaximized(false);
        stage.setScene(mainScene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
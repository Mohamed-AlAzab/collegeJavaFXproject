package com.example.restaurantmanagementsystem.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public void start(Stage stage) {
        Scene mainScene = new MainScene(stage).getScene();
        stage.setTitle("Restaurant");
        stage.setMaximized(false);
        stage.setScene(mainScene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
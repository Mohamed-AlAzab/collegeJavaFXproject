package com.example.restaurantmanagementsystem.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.restaurantmanagementsystem.view.component.CardComponent.createCard;

public class AdminScene extends Application {
    @Override
    public void start(Stage stage) {
        StackPane menuCard = createCard(
                "https://cdn-icons-png.flaticon.com/512/2921/2921822.png",
                "Menu",
                stage
        );

        StackPane card2 = createCard(
                "https://cdn-icons-png.flaticon.com/512/1828/1828843.png",
                "Favorite",
                stage
        );

        StackPane card3 = createCard(
                "https://cdn-icons-png.flaticon.com/512/892/892781.png",
                "Profile",
                stage
        );

        menuCard.setOnMouseClicked(event -> {
            try {
                new MenuScene().start(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        HBox cardRow = new HBox(20, menuCard, card2, card3);
        cardRow.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(cardRow);
        root.setPadding(new Insets(30));
        Scene mainScene = new Scene(root, 1100, 800, Color.web("#f0f0f0"));

        stage.setTitle("Admin Screen");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
// database username: restaurant
// application express username: restaurant
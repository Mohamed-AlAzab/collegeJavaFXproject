package com.example.restaurantmanagementsystem.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static com.example.restaurantmanagementsystem.view.component.CardComponent.Card;

public class MainScene {
    Stage stage;
    StackPane reservationCard = Card(
            "https://cdn-icons-png.flaticon.com/512/2921/2921822.png", "Reservation"
    );
    StackPane card = Card(
            "https://cdn-icons-png.flaticon.com/512/1828/1828843.png", "Reservation"
    );
    StackPane card1 = Card(
            "https://cdn-icons-png.flaticon.com/512/892/892781.png", "Reservation"
    );
    StackPane tempAdmin = Card(
            "https://cdn-icons-png.flaticon.com/512/892/892781.png", "Admin"
    );

    HBox cardRow = new HBox(20, reservationCard, card, card1, tempAdmin);
    StackPane root = new StackPane(cardRow);

    public MainScene(Stage stage) {
        this.stage = stage;
        initControls();
        initActions();
    }

    public void initControls() {
        cardRow.setAlignment(Pos.CENTER);
    }

    public void initActions() {
        reservationCard.setOnMouseClicked((e) ->{
            stage.setScene((new ReserveMainScene(stage)).getScene());
        });
        card1.setOnMouseClicked((e) ->{
            stage.setScene((new ReserveMainScene(stage)).getScene());
        });
        card.setOnMouseClicked((e) ->{
            stage.setScene((new ReserveMainScene(stage)).getScene());
        });
        tempAdmin.setOnMouseClicked((e) ->{
            stage.setScene((new SignInScene(stage)).getScene());
        });
    }

    public Scene getScene() {
        return new Scene(root,1100,850);
    }
}
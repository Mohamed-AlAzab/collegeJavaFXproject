package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static com.example.restaurantmanagementsystem.view.component.CardComponent.Card;

public class MainScene {
    Stage stage;
    String adminIcon = "C:\\Users\\DCS\\Desktop\\assets\\admin.png";
    String reservationIcon = "C:\\Users\\DCS\\Desktop\\assets\\reservation.png";
    String menuIcon = "C:\\Users\\DCS\\Desktop\\assets\\menu.png";

    StackPane reservationCard = Card(reservationIcon, "Reservation");
    StackPane menuIconCard = Card(menuIcon, "Menu");
    StackPane tempAdminCard = Card(adminIcon, "Admin");

    HBox cardRow = new HBox(20, reservationCard, menuIconCard, tempAdminCard);
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
            stage.setScene((new ReservationScene(stage)).getScene());
        });
        menuIconCard.setOnMouseClicked((e) ->{
            stage.setScene((new MenuScene(stage)).getScene());
        });
        tempAdminCard.setOnMouseClicked((e) ->{
            stage.setScene((new SignInScene(stage)).getScene());
        });
    }

    public Scene getScene() {
        return new Scene(root, SceneSize.width,SceneSize.height);
    }
}
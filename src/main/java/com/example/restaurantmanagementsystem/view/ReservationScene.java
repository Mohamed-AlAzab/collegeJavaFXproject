package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static com.example.restaurantmanagementsystem.view.component.CardComponent.Card;

public class ReservationScene {
    Stage stage;
    String reservationIcon = "C:\\Users\\DCS\\Desktop\\assets\\reservation.png";
    String cancelReserveIcon = "C:\\Users\\DCS\\Desktop\\assets\\cancelReserve.png";
    String backIcon = "C:\\Users\\DCS\\Desktop\\assets\\back.png";

    StackPane reserveCard = Card(reservationIcon, "Reservation");
    StackPane cancelReserveCard = Card(cancelReserveIcon, "Cancel Reservation");
    StackPane backCard = Card(backIcon, "Back");

    HBox cardRow = new HBox(20, reserveCard, cancelReserveCard, backCard);
    StackPane root = new StackPane(cardRow);

    public ReservationScene(Stage stage) {
        this.stage = stage;
        initControls();
        initActions();
    }

    public void initControls() {
        cardRow.setAlignment(Pos.CENTER);
    }

    public void initActions() {
        reserveCard.setOnMouseClicked((e) ->{
            stage.setScene((new MakeReservationScene(stage)).getScene());
        });
        cancelReserveCard.setOnMouseClicked((e) ->{
            stage.setScene((new CancelReservationScene(stage)).getScene());
        });
        backCard.setOnMouseClicked(e->{
            stage.setScene(new MainScene(stage).getScene());
        });
    }

    public Scene getScene() {
        return new Scene(root, SceneSize.width,SceneSize.height);
    }
}

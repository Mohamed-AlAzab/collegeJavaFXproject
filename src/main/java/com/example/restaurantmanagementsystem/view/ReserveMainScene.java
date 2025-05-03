package com.example.restaurantmanagementsystem.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static com.example.restaurantmanagementsystem.view.component.CardComponent.Card;

public class ReserveMainScene {
    Stage stage;
    StackPane cardReserve = Card(
            "https://cdn-icons-png.flaticon.com/512/2921/2921822.png", "Reservation"
    );
    StackPane cardCancelReserve = Card(
            "https://cdn-icons-png.flaticon.com/512/1828/1828843.png", "Cancel Reservation"
    );
    StackPane cardTablesReserved = Card(
            "https://cdn-icons-png.flaticon.com/512/892/892781.png", "Show Reservation"
    );

    HBox cardRow = new HBox(20, cardReserve, cardCancelReserve, cardTablesReserved);
    StackPane root = new StackPane(cardRow);

    public ReserveMainScene(Stage stage) {
        this.stage = stage;
        initControls();
        initActions();
    }

    public void initControls() {
        cardRow.setAlignment(Pos.CENTER);
    }

    public void initActions() {
        cardReserve.setOnMouseClicked((e) ->{
            stage.setScene((new ReserveScene(stage)).getScene());
        });
        cardTablesReserved.setOnMouseClicked((e) ->{
            stage.setScene((new TableScene(stage)).getScene());
        });
        cardCancelReserve.setOnMouseClicked((e) ->{
            stage.setScene((new CancelScene(stage)).getScene());
        });
    }

    public Scene getScene() {
        return new Scene(root,1100,850);
    }
}

package com.example.restaurantmanagementsystem.view;

import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



import static com.example.restaurantmanagementsystem.view.component.CardComponent.Card;

public class AdminScene {
    Stage stage;
    StackPane menuCard = Card(
            "https://cdn-icons-png.flaticon.com/512/2921/2921822.png",
            "Menu management"
    );
    StackPane employeeCard = Card(
            "https://cdn-icons-png.flaticon.com/512/1828/1828843.png",
            "Employee management"
    );
    StackPane reservationCard = Card(
            "https://cdn-icons-png.flaticon.com/512/892/892781.png",
            "Reservation management"
    );
    StackPane accountingCard = Card(
            "https://cdn-icons-png.flaticon.com/512/892/892781.png",
            "Accounting management"
    );


    HBox cardRow = new HBox(20, menuCard, employeeCard, reservationCard, accountingCard);
    StackPane root = new StackPane(cardRow);

    public AdminScene(Stage stage) {
        this.stage = stage;
        initControls();
        initActions();
    }

    public void initControls() {
        cardRow.setAlignment(Pos.CENTER);
    }

    public void initActions() {
        menuCard.setOnMouseClicked(event -> {
            try {
                new MenuScene().start(new Stage()); // Launches in a new window
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        });
    }

    public Scene getScene() {
        return new Scene(root, 1100, 850);
    }
}
// database username: restaurant
// application express username: restaurant
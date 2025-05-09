package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static com.example.restaurantmanagementsystem.view.component.CardComponent.Card;

public class MenuScene {
    Stage stage;
    String AddOrderIcon = "C:\\Users\\DCS\\Desktop\\assets\\cartAdd.png";
    String cancelOrderIcon = "C:\\Users\\DCS\\Desktop\\assets\\cancelReserve.png";
    String backIcon ="C:\\Users\\DCS\\Desktop\\assets\\back.png";

    StackPane backCard = Card(backIcon,"Back");
    StackPane AddOrderCard = Card(AddOrderIcon, "Add Order");
    StackPane cancelOrderCard = Card(cancelOrderIcon, "Cancel Order");

    HBox cards = new HBox(20, AddOrderCard, cancelOrderCard, backCard);

    public MenuScene(Stage stage) {
        this.stage = stage;
        inItControl();
        inItAction();
    }

    void inItControl(){
        cards.setAlignment(Pos.CENTER);
        cards.setPadding(new Insets(30));
    }

    void inItAction(){
        AddOrderCard.setOnMouseClicked((e)->{
            stage.setScene((new MakeOrderScene(stage)).getScene());
        });
        cancelOrderCard.setOnMouseClicked((e)->{
            stage.setScene((new CancelOrderScene(stage)).getScene());
        });
        backCard.setOnMouseClicked(e->{
            stage.setScene(new MainScene(stage).getScene());
        });
    }

    Scene getScene(){
        return new Scene (cards, SceneSize.width,SceneSize.height);
    }
}
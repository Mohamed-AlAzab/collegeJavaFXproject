package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.example.restaurantmanagementsystem.view.component.CardComponent.Card;

public class UserMenuMainScene {
    Stage stage;
    String AddOrderIcon = "C:\\Users\\DCS\\Desktop\\assets\\cartAdd.png";
    String cancelOrderIcon = "C:\\Users\\DCS\\Desktop\\assets\\cancelReserve.png";
    String menuIcon = "C:\\Users\\DCS\\Desktop\\assets\\menu.png";
    String backicon="C:\\Users\\DCS\\Desktop\\assets\\back.png";

    StackPane backcard=Card(backicon,"Back");
    StackPane AddOrderCard = Card(AddOrderIcon, "Add Order");
    StackPane cancelOrderCard = Card(cancelOrderIcon, "Cancel Order");
    StackPane menuCard = Card(menuIcon, "Menu");

    HBox cardRow = new HBox(20, AddOrderCard, cancelOrderCard);
    HBox cardRow2=new HBox(20,menuCard,backcard);
    //StackPane root = new StackPane(cardRow);
    VBox cards=new VBox(20,cardRow,cardRow2);

    public UserMenuMainScene(Stage stage) {
        this.stage = stage;
        inItControl();
        inItAction();
    }

    void inItControl(){
        cardRow.setAlignment(Pos.CENTER);
        cardRow2.setAlignment(Pos.CENTER);
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
        menuCard.setOnMouseClicked((e)->{
            stage.setScene((new UserMenuScene(stage)).getScene());
        });
        backcard.setOnMouseClicked(e->{
            stage.setScene(new MainScene(stage).getScene());
        });
    }

    Scene getScene(){
        return new Scene (cards, SceneSize.width,SceneSize.height);
    }
}
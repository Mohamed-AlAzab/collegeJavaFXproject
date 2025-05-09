package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.controller.OrderController;
import com.example.restaurantmanagementsystem.model.Menu;
import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.restaurantmanagementsystem.controller.MenuController.fetchAllItem;
import static com.example.restaurantmanagementsystem.view.component.AlertComponent.showAlert;
import static com.example.restaurantmanagementsystem.view.component.CardComponent.FoodCard;

public class MakeOrderScene {
    private Stage stage;
    private VBox layout = new VBox(20);
    private Connection conn;
    Map<Menu, Spinner<Integer>> itemSpinners = new HashMap<>();

    Button backButton = new Button("Back");
    Button placeOrderBtn = new Button("Place Order");

    public MakeOrderScene(Stage stage) {
        this.stage = stage;
        initControls();
        initActions();
    }

    public void initControls() {
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        FlowPane menuBox = new FlowPane();
        menuBox.setHgap(20);
        menuBox.setVgap(20);
        menuBox.setPadding(new Insets(10));
        menuBox.setPrefWrapLength(SceneSize.width - 40);

        for(Menu item : fetchAllItem()) {
            Spinner<Integer> quantitySpinner = new Spinner<>(0, 20, 0);
            VBox foodCard = FoodCard(
                    "C:\\Users\\DCS\\Desktop\\assets\\food.png",
                    item.getName(),
                    "$" + item.getPrice(),
                    item.getDescription(),
                    quantitySpinner
            );
            itemSpinners.put(item, quantitySpinner);
            menuBox.getChildren().add(foodCard);
        }

        HBox buttons = new HBox(10, placeOrderBtn, backButton);
        buttons.setAlignment(Pos.CENTER);
        menuBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(menuBox, buttons);
    }

    public void initActions(){
        placeOrderBtn.setOnAction(e -> {
            try {
                OrderController.placeOrder(itemSpinners);
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error placing order: " + ex.getMessage());
                System.out.println(ex.toString());
            }
        });
        backButton.setOnAction(e -> stage.setScene(new MenuScene(stage).getScene()));
    }

    public Scene getScene() {
        Scene scene=new Scene(layout, SceneSize.width, SceneSize.height);
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("/com/example/restaurantmanagementsystem/Style.css")).toExternalForm());
        return scene;
    }
}
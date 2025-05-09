package com.example.restaurantmanagementsystem.view.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;

public class CardComponent {
    public static StackPane Card(String imagePathOrUrl, String labelText) {
        Image image;

        File file = new File(imagePathOrUrl);
        if (file.exists()) {
            image = new Image(file.toURI().toString());
        } else {
            image = new Image(imagePathOrUrl);
        }

        ImageView icon = new ImageView(image);
        icon.setFitWidth(50);
        icon.setFitHeight(50);

        Label label = new Label(labelText);
        label.getStyleClass().add("card-label");

        VBox content = new VBox(10, icon, label);
        content.setAlignment(Pos.CENTER);
        content.getStyleClass().add("card-content");

        StackPane card = new StackPane(content);
        card.setMaxSize(250, 300);
        card.setPrefSize(250, 300);
        card.getStyleClass().add("card");

        return card;
    }

    public static VBox FoodCard(String imagePathOrUrl, String foodName, String price, String description, Spinner<Integer> quantitySpinner) {
        Image image;

        File file = new File(imagePathOrUrl);
        if (file.exists()) {
            image = new Image(file.toURI().toString(), 90, 90, true, true);
        } else {
            image = new Image("file:default.png", 90, 90, true, true);
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(90);
        imageView.setPreserveRatio(true);
        imageView.getStyleClass().add("food-image");

        Label nameLabel = new Label(foodName);
        nameLabel.getStyleClass().add("food-name");

        Label priceLabel = new Label(price);
        priceLabel.getStyleClass().add("food-price");

        Label descLabel = new Label(description);
        descLabel.setWrapText(true);
        descLabel.getStyleClass().add("food-desc");

        quantitySpinner.setMaxWidth(Double.MAX_VALUE);
        quantitySpinner.getStyleClass().add("food-quantity-spinner");

        VBox content = new VBox(10.0, imageView, nameLabel, priceLabel, descLabel, quantitySpinner);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(12));
        content.getStyleClass().add("food-card");

        return content;
    }
}

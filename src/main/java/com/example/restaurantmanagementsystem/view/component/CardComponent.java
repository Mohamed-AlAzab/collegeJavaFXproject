package com.example.restaurantmanagementsystem.view.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.File;

public class CardComponent {
    public static StackPane Card(String imagePathOrUrl, String labelText) {
        Image image;

        File file = new File(imagePathOrUrl);
        if (file.exists()) {
            image = new Image(file.toURI().toString());
        } else {
            // Fallback to URL (or resource)
            image = new Image(imagePathOrUrl);
        }

        ImageView icon = new ImageView(image);
        icon.setFitWidth(50);
        icon.setFitHeight(50);

        Label label = new Label(labelText);

        VBox content = new VBox(10, icon, label);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(10));
        content.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(10), Insets.EMPTY)));

        StackPane card = new StackPane(content);
        card.setMaxSize(250, 300);
        card.setMinSize(250, 300);
        card.setPrefSize(250, 300);
        card.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(12), Insets.EMPTY)));
        card.setBorder(new Border(new BorderStroke(Color.LIGHTBLUE, BorderStrokeStyle.SOLID, new CornerRadii(12), BorderWidths.DEFAULT)));

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

        Label nameLabel = new Label(foodName);
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Label priceLabel = new Label(price);
        priceLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

        Label descLabel = new Label(description);
        descLabel.setWrapText(true);
        descLabel.setStyle("-fx-font-size: 12px;");

        quantitySpinner.setMaxWidth(Double.MAX_VALUE);

        VBox content = new VBox(10.0, imageView, nameLabel, priceLabel, descLabel, quantitySpinner);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(12));
        content.setPrefSize(220, 280);
        content.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        content.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));

        return content;
    }
}

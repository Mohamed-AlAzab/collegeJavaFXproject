package com.example.restaurantmanagementsystem.view.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CardComponent {
    public static StackPane createCard(String imageUrl, String labelText, Stage stage) {
        ImageView icon = new ImageView(new Image(imageUrl));
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
}

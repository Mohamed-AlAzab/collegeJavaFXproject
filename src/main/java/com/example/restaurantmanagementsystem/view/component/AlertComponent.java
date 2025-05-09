package com.example.restaurantmanagementsystem.view.component;

import com.example.restaurantmanagementsystem.view.App;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

import java.util.Objects;

public class AlertComponent {
    public static void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                Objects.requireNonNull(App.class.getResource("/com/example/restaurantmanagementsystem/Style.css")).toExternalForm()
        );
        dialogPane.getStyleClass().add("alert");

        alert.showAndWait();
    }

}

package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.model.DB;
import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static com.example.restaurantmanagementsystem.view.component.AlertComponent.showAlert;

public class CancelOrderScene {
    private Stage stage;
    GridPane gridPane = new GridPane();
    Label title = new Label("Cancel Order by ID");
    TextField orderIdField = new TextField();
    Button cancelButton = new Button("Cancel Order");
    Button backButton = new Button("Back");

    public CancelOrderScene(Stage stage) {
        this.stage = stage;
        initControls();
        initActions();
    }

    public void initControls() {
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(15);
        gridPane.setAlignment(Pos.CENTER);

        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label orderIdLabel = new Label("Order ID:");

        orderIdField.setPromptText("Enter Order ID");
        orderIdField.setPrefWidth(250);

        cancelButton.setMaxWidth(Double.MAX_VALUE);
        backButton.setMaxWidth(Double.MAX_VALUE);

        gridPane.add(title, 0, 0, 2, 1);
        gridPane.add(orderIdLabel, 0, 1);
        gridPane.add(orderIdField, 1, 1);
        gridPane.add(cancelButton, 0, 2, 2, 1);
        gridPane.add(backButton, 0, 3, 2, 1);
    }

    public void initActions () {
        cancelButton.setOnAction(e -> {
            String orderId = orderIdField.getText().trim();
            if (!orderId.matches("\\d+")) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid numeric Order ID.");
                return;
            }
            try {
                cancelOrderById(Integer.parseInt(orderId));
            } catch (SQLException ex) {
                System.out.println(ex.toString());
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to cancel order.");
            }
        });
        backButton.setOnAction(e->{
            stage.setScene(new MenuScene(stage).getScene());
        });
    }

    private void cancelOrderById(int orderId) throws SQLException {
        String checkSQL = "SELECT id FROM orders WHERE id = ?";
        try (Connection conn = DB.dbConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSQL)) {
            conn.setAutoCommit(false);

            checkStmt.setInt(1, orderId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                showAlert(Alert.AlertType.WARNING, "Not Found", "No order found with ID: " + orderId);
                return;
            }

            // First, delete order items manually
            try (PreparedStatement deleteItems = conn.prepareStatement(
                    "DELETE FROM order_items WHERE order_id = ?")) {
                deleteItems.setInt(1, orderId);
                deleteItems.executeUpdate();
            }

            // Then delete the order
            try (PreparedStatement deleteOrder = conn.prepareStatement(
                    "DELETE FROM orders WHERE id = ?")) {
                deleteOrder.setInt(1, orderId);
                deleteOrder.executeUpdate();
            }

            conn.commit();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Order ID " + orderId + " cancelled successfully.");

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to cancel order.");
        }
    }

    public Scene getScene() {
        Scene scene=new Scene(gridPane, SceneSize.width, SceneSize.height);
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("/com/example/restaurantmanagementsystem/Style.css")).toExternalForm());
        return scene;
    }
}
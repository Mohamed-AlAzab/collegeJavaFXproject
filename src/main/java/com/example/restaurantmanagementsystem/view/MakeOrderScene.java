package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.model.DB;
import com.example.restaurantmanagementsystem.model.Menu;
import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.example.restaurantmanagementsystem.view.component.AlertComponent.showAlert;

public class MakeOrderScene {
    private Stage stage;
    Connection conn = null;
    VBox layout = new VBox(10);

    public MakeOrderScene(Stage stage) {
        this.stage = stage;
        createScene();
    }

    public void createScene(){
        layout.setPadding(new Insets(15));

        Label title = new Label("Menu");
        layout.getChildren().add(title);

        Map<Menu, Spinner<Integer>> itemSpinners = new HashMap<>();

        try{
            conn = DB.dbConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM menu");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Menu item = new Menu(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description")
                );

                VBox itemBox = new VBox(5);
                Label nameLabel = new Label(item.getName() + " - $" + item.getPrice());
                Label descLabel = new Label(item.getDescription());

                Spinner<Integer> quantitySpinner = new Spinner<>(0, 20, 0);
                itemSpinners.put(item, quantitySpinner);

                itemBox.getChildren().addAll(nameLabel, descLabel, quantitySpinner);
                layout.getChildren().add(itemBox);
            }
        } catch (SQLException ex){
            System.out.println(ex.toString());
        }

        Button placeOrderBtn = new Button("Place Order");
        placeOrderBtn.setOnAction(e -> {
            try {
                placeOrder(itemSpinners);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Order placed successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        layout.getChildren().add(placeOrderBtn);
        Button backbutton=new Button("Back");
        backbutton.setOnAction(e->{
            stage.setScene(new UserMenuMainScene(stage).getScene());
        });
        layout.getChildren().add(backbutton);
    }

    private void placeOrder(Map<Menu, Spinner<Integer>> items) throws SQLException {
        conn.setAutoCommit(false);

        // Insert order
        PreparedStatement orderStmt = conn.prepareStatement("INSERT INTO orders (time) VALUES (CURRENT_TIMESTAMP)", new String[]{"id"});
        orderStmt.executeUpdate();
        ResultSet keys = orderStmt.getGeneratedKeys();
        keys.next();
        int orderId = keys.getInt(1);

        // Insert each item
        PreparedStatement itemStmt = conn.prepareStatement(
                "INSERT INTO order_items (order_id, menu_id, quantity) VALUES (?, ?, ?)"
        );

        for (Map.Entry<Menu, Spinner<Integer>> entry : items.entrySet()) {
            int qty = entry.getValue().getValue();
            if (qty > 0) {
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, entry.getKey().getId());
                itemStmt.setInt(3, qty);
                itemStmt.addBatch();
            }
        }

        itemStmt.executeBatch();
        conn.commit();
    }

    public Scene getScene() {
        return new Scene(layout, SceneSize.width,SceneSize.height);
    }
}
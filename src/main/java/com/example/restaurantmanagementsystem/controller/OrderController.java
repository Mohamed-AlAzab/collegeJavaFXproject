package com.example.restaurantmanagementsystem.controller;

import com.example.restaurantmanagementsystem.model.Menu;
import com.example.restaurantmanagementsystem.model.Order;
import com.example.restaurantmanagementsystem.model.OrderItem;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;

import java.sql.SQLException;
import java.util.Map;

import static com.example.restaurantmanagementsystem.view.component.AlertComponent.showAlert;

public class OrderController {
    public static void placeOrder(Map<Menu, Spinner<Integer>> itemSpinners) {
        Integer orderId = Order.createOrder();
        if (orderId != null) {
            boolean anyItemAdded = false;

            for (Map.Entry<Menu, Spinner<Integer>> entry : itemSpinners.entrySet()) {
                Spinner<Integer> spinner = entry.getValue();
                Integer qty = spinner.getValue();

                if (qty != null && qty > 0) {
                    OrderItem.addOrderItem(orderId, entry.getKey().getId(), qty);
                    anyItemAdded = true;
                }
            }

            if (anyItemAdded) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Order placed successfully!\n Order # " + orderId);
            } else {
                try {
                    Order.deleteOrder(orderId);
                    showAlert(Alert.AlertType.WARNING, "Warning", "No items selected. Order was not placed.");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Could not clean up empty order: " + e.getMessage());
                }
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not create order.");
        }
    }
}

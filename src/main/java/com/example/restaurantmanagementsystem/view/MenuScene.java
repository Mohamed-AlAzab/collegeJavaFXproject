package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.controller.MenuController;
import com.example.restaurantmanagementsystem.model.DB;
import com.example.restaurantmanagementsystem.model.Menu;
import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.restaurantmanagementsystem.view.component.AlertComponent.showAlert;

public class MenuScene extends Application {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ArrayList<Menu> data;
    TableView<Menu> table;

    @Override
    public void start(Stage stage) throws IOException {
        Button addItemButton = new Button("Add Item");
        Text AddText = new Text("Add New Item");
        Label idLabel = new Label("ID");
        Label nameLabel = new Label("Name");
        Label priceLabel = new Label("Price");
        Label descriptionLabel = new Label("Description");

        TextField idTextField = new TextField();
        TextField nameTextField = new TextField();
        TextField priceTextField = new TextField();
        TextField descriptionTextField = new TextField();

        GridPane gAdd = new GridPane();
        gAdd.add(AddText, 0, 0, 2, 1);
        gAdd.add(idLabel, 0, 1);
        gAdd.add(idTextField, 1, 1);
        gAdd.add(nameLabel, 0, 2);
        gAdd.add(nameTextField, 1, 2);
        gAdd.add(priceLabel, 0, 3);
        gAdd.add(priceTextField, 1, 3);
        gAdd.add(descriptionLabel, 0, 4);
        gAdd.add(descriptionTextField, 1, 4);
        gAdd.add(addItemButton, 0, 5, 2, 1);

        addItemButton.setMaxWidth(Double.MAX_VALUE);

        gAdd.setVgap(10);
        gAdd.setHgap(10);
        gAdd.setAlignment(Pos.CENTER);
        gAdd.setPadding(new Insets(20));

        Button updateNameButton = new Button("Update Name");
        Button updatePriceButton = new Button("Update Price");
        Button updateDescriptionButton = new Button("Update Description");
        Button updateAllButton = new Button("Update all item details");

        Text UpdateText = new Text("Update by ID");
        Label idUpdateLabel = new Label("ID");
        Label nameUpdateLabel = new Label("New Name");
        Label priceUpdateLabel = new Label("New Price");
        Label descriptionUpdateLabel = new Label("New Description");

        TextField idUpdateTextField = new TextField();
        TextField nameUpdateTextField = new TextField();
        TextField priceUpdateTextField = new TextField();
        TextField descriptionUpdateTextField = new TextField();

        GridPane gUpdate = new GridPane();
        gUpdate.add(UpdateText, 0, 0, 2, 1);
        gUpdate.add(idUpdateLabel, 0, 1);
        gUpdate.add(idUpdateTextField, 1, 1);
        gUpdate.add(nameUpdateLabel, 0, 2);
        gUpdate.add(nameUpdateTextField, 1, 2);
        gUpdate.add(priceUpdateLabel, 0, 3);
        gUpdate.add(priceUpdateTextField, 1, 3);
        gUpdate.add(descriptionUpdateLabel, 0, 4);
        gUpdate.add(descriptionUpdateTextField, 1, 4);

        gUpdate.add(updateNameButton, 0, 5, 2, 1);
        gUpdate.add(updatePriceButton, 0, 6, 2, 1);
        gUpdate.add(updateDescriptionButton, 0, 7, 2, 1);
        gUpdate.add(updateAllButton, 0, 8, 2, 2);

        updateAllButton.setMaxWidth(Double.MAX_VALUE);
        updateNameButton.setMaxWidth(Double.MAX_VALUE);
        updateDescriptionButton.setMaxWidth(Double.MAX_VALUE);
        updatePriceButton.setMaxWidth(Double.MAX_VALUE);

        gUpdate.setVgap(10);
        gUpdate.setHgap(10);
        gUpdate.setAlignment(Pos.CENTER);
        gUpdate.setPadding(new Insets(20));

        Button DeleteItemButton = new Button("Delete Item");
        DeleteItemButton.setMaxWidth(Double.MAX_VALUE);
        Text DeleteText = new Text("Delete Item by ID");
        Label labelDeleteId = new Label("ID");

        TextField idDeleteTextField = new TextField();

        GridPane gDelete = new GridPane();
        gDelete.add(DeleteText, 0, 0, 2, 1);
        gDelete.add(labelDeleteId, 0, 1);
        gDelete.add(idDeleteTextField, 1, 1);
        gDelete.add(DeleteItemButton, 0, 2, 2, 1);

        gDelete.setVgap(10);
        gDelete.setHgap(10);
        gDelete.setAlignment(Pos.CENTER);
        gDelete.setPadding(new Insets(20));

        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("Menu is empty (No rows to display)"));

        TableColumn<Menu, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Menu, String> nameCol = new TableColumn<>("NAME");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Menu, Double> priceCol = new TableColumn<>("PRICE");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Menu, String> descriptionCol = new TableColumn<>("DESCRIPTION");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.getColumns().addAll(idCol, nameCol, priceCol, descriptionCol);

        VBox vTable = new VBox(table);
        vTable.setPrefWidth(800);
        table.setPrefWidth(800);
        vTable.setPadding(new Insets(20));

        try {
            show();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        addItemButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        AddText.setStyle("-fx-font: normal bold 20px 'solid'");
        updateNameButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        updatePriceButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        updateDescriptionButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        updateAllButton.setStyle("-fx-background-color: orange; -fx-text-fill: black;");
        UpdateText.setStyle("-fx-font: normal bold 20px 'solid'");
        DeleteItemButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        DeleteText.setStyle("-fx-font: normal bold 20px 'solid'");

        // Add Item in database
        addItemButton.setOnAction(e -> {
            int id = Integer.parseInt(idTextField.getText());
            String name = nameTextField.getText();
            double price = Double.parseDouble(priceTextField.getText());
            String description = descriptionTextField.getText();

            if (MenuController.addItem(id, name, price, description)) {
                try {
                    show();
                    idTextField.setText("");
                    nameTextField.setText("");
                    priceTextField.setText("");
                    descriptionTextField.setText("");
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
                showAlert(Alert.AlertType.INFORMATION, "Success", "Item Added Successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Something went wrong.");
            }
        });

        updateNameButton.setOnAction(e -> {
            if (idUpdateTextField.getText().isEmpty() || nameUpdateTextField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter both ID and New Name.");
                return;
            }
            if (isEmpty(idUpdateTextField, nameUpdateTextField)) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter both ID and Name.");
                return;
            }
            if (!isValidNumber(idUpdateTextField)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "ID must be a number.");
                return;
            }
            if (!isValidString(nameUpdateTextField)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Name", "Name must contain letters, not just numbers.");
                return;
            }

            try {
                conn = DB.dbConnection();
                String sql = "UPDATE menu SET name = ? WHERE id = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, nameUpdateTextField.getText());
                pst.setInt(2, Integer.parseInt(idUpdateTextField.getText()));
                int j = pst.executeUpdate();
                if (j == 1) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Name Updated Successfully!");
                    show();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Failed", "ID may be wrong.");
                }
                pst.close();
                conn.close();
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "ID must be a number.");
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        updatePriceButton.setOnAction(e -> {
            if (idUpdateTextField.getText().isEmpty() || priceUpdateTextField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter both ID and New Price.");
                return;
            }
            if (isEmpty(idUpdateTextField, priceUpdateTextField)) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter both ID and Price.");
                return;
            }
            if (!isValidNumber(idUpdateTextField)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "ID must be a number.");
                return;
            }
            if (!isValidNumber(priceUpdateTextField)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Price must be a valid number.");
                return;
            }

        try {
            conn = DB.dbConnection();
            String sql = "UPDATE menu SET price = ? WHERE id = ?";

                pst = conn.prepareStatement(sql);
                pst.setDouble(1, Double.parseDouble(priceUpdateTextField.getText()));
                pst.setInt(2, Integer.parseInt(idUpdateTextField.getText()));

                int j = pst.executeUpdate();
                if (j == 1) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Price Updated Successfully!");
                    show();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Failed", "ID may be wrong.");
                }
                pst.close();
                conn.close();
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        updateDescriptionButton.setOnAction(e -> {
            if (idUpdateTextField.getText().isEmpty() || descriptionUpdateTextField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter both ID and New Description.");
                return;
            }
            if (isEmpty(idUpdateTextField, descriptionUpdateTextField)) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter both ID and Description.");
                return;
            }
            if (!isValidNumber(idUpdateTextField)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "ID must be a number.");
                return;
            }
            if (!isValidString(descriptionUpdateTextField)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Description", "Description must contain letters, not just numbers.");
                return;
            }
            try {
                conn = DB.dbConnection();
                String sql = "UPDATE menu SET description = ? WHERE id = ?";

                pst = conn.prepareStatement(sql);
                pst.setString(1, descriptionUpdateTextField.getText());
                pst.setInt(2, Integer.parseInt(idUpdateTextField.getText()));

                int j = pst.executeUpdate();
                if (j == 1) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Description Updated Successfully!");
                    show();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Failed", "ID may be wrong.");
                }
                pst.close();
                conn.close();
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        updateAllButton.setOnAction(e -> {
            if (idUpdateTextField.getText().isEmpty()|| nameUpdateTextField.getText().isEmpty()|| priceUpdateTextField.getText().isEmpty() || descriptionUpdateTextField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please Enter All Item Details");
                return;
            }
            try {
                conn = DB.dbConnection();
                String sql = "UPDATE menu SET name=?,price = ?,description=? WHERE id = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, nameUpdateTextField.getText());
                pst.setDouble(2, Double.parseDouble(priceUpdateTextField.getText()));
                pst.setString(3, descriptionUpdateTextField.getText());
                pst.setInt(4, Integer.parseInt(idUpdateTextField.getText()));

                int j = pst.executeUpdate();
                if (j == 1) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Item Details Updated Successfully!");
                    show();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Failed", "ID may be wrong.");
                }
                pst.close();
                conn.close();
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });
        // Delete Item from database
        DeleteItemButton.setOnAction(e -> {
            int id = Integer.parseInt(idDeleteTextField.getText());
            if (MenuController.deleteItem(id)) {
                try {
                    show();
                    idDeleteTextField.setText("");
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
                showAlert(Alert.AlertType.INFORMATION, "Success", "Item Deleted Successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "ID doesn't exist.");
            }
        });

        VBox v = new VBox(20, gAdd, gUpdate);
        v.setPadding(new Insets(20));
        v.setAlignment(Pos.TOP_CENTER);
        FlowPane rr = new FlowPane(v,gDelete);
        HBox root = new HBox(10, v,rr, vTable);
        root.setPadding(new Insets(20));
        Scene scene = new Scene(root, SceneSize.width,SceneSize.height);
        stage.setTitle("Menu Management");
        stage.setScene(scene);
        stage.show();
    }

    private boolean isValidNumber(TextField field) {
        try {
            Integer.parseInt(field.getText().trim());
            Integer.parseInt(field.getText().trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidString(TextField field) {
        String text = field.getText().trim();
        return !text.isEmpty() && !text.matches("\\d+");
    }

    private boolean isEmpty(TextField... fields) {
        for (TextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public void show() throws SQLException {
        data = new ArrayList<>();
        conn = DB.dbConnection();
        pst = conn.prepareStatement("SELECT * FROM menu");
        res = pst.executeQuery();
        while (res.next()) {
            Menu menu =
                new Menu(
                    res.getInt(1),
                    res.getString(2),
                    res.getDouble(3),
                    res.getString(4)
                );
            data.add(menu);
        }
        pst.close();
        conn.close();
        // function make the array list to normal array make sort or make search
        table.getItems().setAll(data);
    }

    public static void main(String[] args) {
        launch();
    }
}
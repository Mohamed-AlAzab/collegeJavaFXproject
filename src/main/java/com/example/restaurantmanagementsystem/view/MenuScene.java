package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.controller.MenuController;
import com.example.restaurantmanagementsystem.model.DB;
import com.example.restaurantmanagementsystem.model.Menu;
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

import static com.example.restaurantmanagementsystem.view.component.AlertComponent.showAlert;

public class MenuScene extends Application {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ObservableList<Menu> data;
    TableView<Menu> table;

    @Override
    public void start(Stage stage) throws IOException {
        Button AddItemButton = new Button("Add Item");
        Text AddText = new Text("Add New Item");
        Label lId = new Label("ID");
        Label lName = new Label("Name");
        Label lPrice = new Label("Price");
        Label lDesc = new Label("Description");

        TextField tId = new TextField();
        TextField tName = new TextField();
        TextField tPrice = new TextField();
        TextField tDesc = new TextField();

        GridPane gAdd = new GridPane();
        gAdd.add(AddText, 0, 0, 2, 1);
        gAdd.add(lId, 0, 1);
        gAdd.add(tId, 1, 1);
        gAdd.add(lName, 0, 2);
        gAdd.add(tName, 1, 2);
        gAdd.add(lPrice, 0, 3);
        gAdd.add(tPrice, 1, 3);
        gAdd.add(lDesc, 0, 4);
        gAdd.add(tDesc, 1, 4);
        gAdd.add(AddItemButton, 0, 5, 2, 1);

        gAdd.setVgap(10);
        gAdd.setHgap(10);
        gAdd.setAlignment(Pos.CENTER);
        gAdd.setPadding(new Insets(20));

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Button UpdateNameBtn = new Button("Update Name");
        Button UpdatePriceBtn = new Button("Update Price");
        Button UpdateDescBtn = new Button("Update Description");
        Button UpdateAllBtn = new Button("Update all item details");

        Text UpdateText = new Text("Update by ID");
        Label lUid = new Label("ID");
        Label lUname = new Label("New Name");
        Label lUprice = new Label("New Price");
        Label lUdesc = new Label("New Description");

        TextField tUid = new TextField();
        TextField tUname = new TextField();
        TextField tUprice = new TextField();
        TextField tUdesc = new TextField();

        GridPane gUpdate = new GridPane();
        gUpdate.add(UpdateText, 0, 0, 2, 1);
        gUpdate.add(lUid, 0, 1);
        gUpdate.add(tUid, 1, 1);
        gUpdate.add(lUname, 0, 2);
        gUpdate.add(tUname, 1, 2);
        gUpdate.add(lUprice, 0, 3);
        gUpdate.add(tUprice, 1, 3);
        gUpdate.add(lUdesc, 0, 4);
        gUpdate.add(tUdesc, 1, 4);

        gUpdate.add(UpdateNameBtn, 0, 5, 2, 1);
        gUpdate.add(UpdatePriceBtn, 0, 6, 2, 1);
        gUpdate.add(UpdateDescBtn, 0, 7, 2, 1);
        gUpdate.add(UpdateAllBtn, 0, 8, 2, 2);

        gUpdate.setVgap(10);
        gUpdate.setHgap(10);
        gUpdate.setAlignment(Pos.CENTER);
        gUpdate.setPadding(new Insets(20));

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Button DeleteItemButton = new Button("Delete Item");
        Text DeleteText = new Text("Delete Item by ID");
        Label labelDeleteId = new Label("ID");

        TextField tidd = new TextField();

        GridPane gDelete = new GridPane();
        gDelete.add(DeleteText, 0, 0, 2, 1);
        gDelete.add(labelDeleteId, 0, 1);
        gDelete.add(tidd, 1, 1);
        gDelete.add(DeleteItemButton, 0, 2, 2, 1);

        gDelete.setVgap(10);
        gDelete.setHgap(10);
        gDelete.setAlignment(Pos.CENTER);
        gDelete.setPadding(new Insets(20));

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        AddItemButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        AddText.setStyle("-fx-font: normal bold 20px 'solid'");

        UpdateNameBtn.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        UpdatePriceBtn.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        UpdateDescBtn.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        UpdateAllBtn.setStyle("-fx-background-color: orange; -fx-text-fill: black;");
        UpdateText.setStyle("-fx-font: normal bold 20px 'solid'");

        DeleteItemButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        DeleteText.setStyle("-fx-font: normal bold 20px 'solid'");

        // Add Item in database
        AddItemButton.setOnAction(e -> {
            int id = Integer.parseInt(tId.getText());
            String name = tName.getText();
            double price = Double.parseDouble(tPrice.getText());
            String description = tDesc.getText();

            if (MenuController.addItem(id, name, price, description)) {
                try {
                    show();
                    tId.setText("");
                    tName.setText("");
                    tPrice.setText("");
                    tDesc.setText("");
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
                showAlert(Alert.AlertType.INFORMATION, "Success", "Item Added Successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Something went wrong.");
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        UpdateNameBtn.setOnAction(e -> {
            if (tUid.getText().isEmpty() || tUname.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter both ID and New Name.");
                return;
            }

            if (isEmpty(tUid, tUname)) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter both ID and Name.");
                return;
            }

            if (!isInteger(tUid)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "ID must be a number.");
                return;
            }

            if (!isValidString(tUname)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Name", "Name must contain letters, not just numbers.");
                return;
            }

            try {
                conn = DB.dbConnection();
                String sql = "UPDATE menu SET name = ? WHERE id = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, tUname.getText());
                pst.setInt(2, Integer.parseInt(tUid.getText()));
                int j = pst.executeUpdate();
                if (j == 1) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Name Updated Successfully!");
                    show();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Failed", "ID may be wrong.");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "ID must be a number.");
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            } finally {
                closeConnections();
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        UpdatePriceBtn.setOnAction(e -> {
            if (tUid.getText().isEmpty() || tUprice.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter both ID and New Price.");
                return;
            }

            if (isEmpty(tUid, tUprice)) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter both ID and Price.");
                return;
            }

            if (!isInteger(tUid)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "ID must be a number.");
                return;
            }
            if (!isValidNumber(tUprice)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Price must be a valid number.");
                return;
            }

        try {
            conn = DB.dbConnection();
            String sql = "UPDATE menu SET price = ? WHERE id = ?";

                pst = conn.prepareStatement(sql);
                pst.setDouble(1, Double.parseDouble(tUprice.getText()));
                pst.setInt(2, Integer.parseInt(tUid.getText()));

                int j = pst.executeUpdate();
                if (j == 1) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Price Updated Successfully!");
                    show();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Failed", "ID may be wrong.");
                }
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            } finally {
                closeConnections();
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        UpdateDescBtn.setOnAction(e -> {
            if (tUid.getText().isEmpty() || tUdesc.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter both ID and New Description.");
                return;
            }

            if (isEmpty(tUid, tUdesc)) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter both ID and Description.");
                return;
            }

            if (!isInteger(tUid)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "ID must be a number.");
                return;
            }

            if (!isValidString(tUdesc)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Description", "Description must contain letters, not just numbers.");
                return;
            }
            try {
                conn = DB.dbConnection();
                String sql = "UPDATE menu SET description = ? WHERE id = ?";

                pst = conn.prepareStatement(sql);
                pst.setString(1, tUdesc.getText());
                pst.setInt(2, Integer.parseInt(tUid.getText()));

                int j = pst.executeUpdate();
                if (j == 1) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Description Updated Successfully!");
                    show();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Failed", "ID may be wrong.");
                }
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            } finally {
                closeConnections();
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        UpdateAllBtn.setOnAction(e -> {
            if (tUid.getText().isEmpty()|| tUname.getText().isEmpty()|| tUprice.getText().isEmpty() || tUdesc.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please Enter All Item Details");
                return;
            }
        try {
            conn = DB.dbConnection();
            String sql = "UPDATE menu SET name=?,price = ?,description=? WHERE id = ?";

                pst = conn.prepareStatement(sql);
                pst.setString(1, tUname.getText());
                pst.setDouble(2, Double.parseDouble(tUprice.getText()));
                pst.setString(3, tUdesc.getText());
                pst.setInt(4, Integer.parseInt(tUid.getText()));

                int j = pst.executeUpdate();
                if (j == 1) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Item Details Updated Successfully!");
                    show();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Failed", "ID may be wrong.");
                }
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            } finally {
                closeConnections();
            }
        });
        // Delete Item from database
        DeleteItemButton.setOnAction(e -> {
            int id = Integer.parseInt(tidd.getText());
            if (MenuController.deleteItem(id)) {
                try {
                    show();
                    tidd.setText("");
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
        Scene scene = new Scene(root, 1100, 800);
        // scene.getStylesheets().add("menu.css");
        stage.setTitle("Menu Management");
        stage.setScene(scene);
        stage.show();
    }

    private void closeConnections() {
        try { if (pst != null) pst.close(); } catch (Exception e) {
            System.out.println(e.toString());
        }
        try { if (conn != null) conn.close(); } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private boolean isValidNumber(TextField field) {
        try {
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

    private boolean isInteger(TextField field) {
        try {
            Integer.parseInt(field.getText().trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void show() throws SQLException {
        data = FXCollections.observableArrayList();
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
        table.setItems(data);
    }

    public static void main(String[] args) {
        launch();
    }
}
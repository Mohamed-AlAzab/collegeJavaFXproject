package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.model.Accounting;
import com.example.restaurantmanagementsystem.model.AccountingDAO;
import com.example.restaurantmanagementsystem.model.DB;
import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class AccountingManagementScene {

    private final AccountingDAO dao;
    private final ObservableList<Accounting> recordList;
    private VBox layout;

    public AccountingManagementScene(Stage stage) {
        Connection conn = DB.dbConnection();
        this.dao = new AccountingDAO(conn);
        this.recordList = FXCollections.observableArrayList();

        TableView<Accounting> table = new TableView<>();
        TableColumn<Accounting, Integer> idCol = new TableColumn<>("ID");
        TableColumn<Accounting, LocalDate> dateCol = new TableColumn<>("Date");
        TableColumn<Accounting, String> typeCol = new TableColumn<>("Type");
        TableColumn<Accounting, String> descCol = new TableColumn<>("Description");
        TableColumn<Accounting, Double> amountCol = new TableColumn<>("Amount");

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        table.getColumns().addAll(idCol, dateCol, typeCol, descCol, amountCol);
        table.setItems(recordList);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        DatePicker datePicker = new DatePicker(LocalDate.now());
        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("income", "expense");
        typeBox.setValue("income");

        TextField descField = new TextField();
        descField.setPromptText("Description");

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        Button addBtn = new Button("Add");
        Button deleteBtn = new Button("Delete Selected");
        Button backBtn = new Button("Back");

        HBox form = new HBox(10, datePicker, typeBox, descField, amountField, addBtn, deleteBtn, backBtn);
        form.setPadding(new Insets(10));

        layout = new VBox(10, table, form);
        layout.setPadding(new Insets(15));

        loadRecords();

        addBtn.setOnAction(e -> {
            try {
                LocalDate date = datePicker.getValue();
                String type = typeBox.getValue();
                String desc = descField.getText();
                double amount = Double.parseDouble(amountField.getText());

                Accounting record = new Accounting(0, date, type, desc, amount);
                dao.insertRecord(record);
                loadRecords();
                descField.clear();
                amountField.clear();
            } catch (Exception ex) {
                showAlert("Invalid input or DB error: " + ex.getMessage());
            }
        });

        deleteBtn.setOnAction(e -> {
            Accounting selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    dao.deleteRecord(selected.getId());
                    loadRecords();
                } catch (SQLException ex) {
                    showAlert("Error deleting record: " + ex.getMessage());
                }
            } else {
                showAlert("Please select a record to delete.");
            }
        });

        backBtn.setOnAction(e -> {
            stage.setScene(new AdminScene(stage).getScene());
        });
    }

    private void loadRecords() {
        try {
            recordList.setAll(dao.getAllRecords());
        } catch (SQLException e) {
            showAlert("Failed to load records: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    public Scene getScene() {
        Scene scene = new Scene(layout, SceneSize.width, SceneSize.height);
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("/com/example/restaurantmanagementsystem/Style.css")).toExternalForm());
        return scene;
    }
}

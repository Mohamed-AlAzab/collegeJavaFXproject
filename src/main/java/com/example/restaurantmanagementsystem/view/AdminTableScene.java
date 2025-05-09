package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.model.Table;
import com.example.restaurantmanagementsystem.model.TableManager;
import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

import static com.example.restaurantmanagementsystem.controller.TableController.*;
import static com.example.restaurantmanagementsystem.view.component.AlertComponent.showAlert;

public class AdminTableScene extends Application {
    private TableManager tableManager;
    private ObservableList<Table> tableList;

    @Override
    public void start(Stage primaryStage) {
        TableView<Table> tables = new TableView<>();
        tables.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Table, Integer> idColumn = new TableColumn<>("Table ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Table, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Table, Integer> capacityColumn = new TableColumn<>("Capacity");
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        TableColumn<Table, Boolean> isReservedColumn = new TableColumn<>("Is Reserved");
        isReservedColumn.setCellValueFactory(new PropertyValueFactory<>("isReserved"));

        tables.getColumns().addAll(idColumn, typeColumn, capacityColumn, isReservedColumn);

        tableManager = new TableManager();
        tableList = FXCollections.observableArrayList(tableManager.fetchAllTable());
        tables.setItems(tableList);

        Button addButton = new Button("Add Table");
        addButton.setPrefWidth(150);

        Button deleteButton = new Button("Delete Table");
        deleteButton.setPrefWidth(150);
        deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        Button updateButton = new Button("Update Table");
        updateButton.setPrefWidth(150);

        VBox buttonBox = new VBox(10, addButton, updateButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setPadding(new Insets(10));

        StackPane tablePane = new StackPane(tables);
        tablePane.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setLeft(buttonBox);
        root.setCenter(tablePane);

        addButton.setOnAction(e -> {
            Dialog<Table> dialog = addTableDialog();
            dialog.showAndWait().ifPresent(table -> {
                addTable(table.getId(), table.getType(), table.getCapacity());
                tableList.setAll(tableManager.fetchAllTable());
            });
        });

        deleteButton.setOnAction(e -> {
            Table selected = tables.getSelectionModel().getSelectedItem();
            if (selected != null) {
                deleteTable(selected.getId());
                tableList.setAll(tableManager.fetchAllTable());
            } else {
                showAlert(Alert.AlertType.INFORMATION, "No Selection", "Please select a table to delete.");
            }
        });

        updateButton.setOnAction(e -> {
            Table selected = tables.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Dialog<Table> dialog = updateTableDialog(selected);
                dialog.showAndWait().ifPresent(table -> {
                    updateTable(table.getId(), table.getType(), table.getCapacity());
                    tableList.setAll(tableManager.fetchAllTable());
                });
            } else {
                showAlert(Alert.AlertType.INFORMATION, "No Selection", "Please select a table to update.");
            }
        });

        Scene scene = new Scene(root, SceneSize.width, SceneSize.height);
        primaryStage.setTitle("Table Management");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("/com/example/restaurantmanagementsystem/Style.css")).toExternalForm());
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    private Dialog<Table> addTableDialog() {
        Dialog<Table> dialog = new Dialog<>();
        dialog.setTitle("Add New Table");
        dialog.setHeaderText("Enter Table Details");
        dialog.setResizable(true);

        VBox content = new VBox(10);
        content.setPadding(new Insets(15));

        TextField idField = new TextField();
        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Regular", "VIP");
        typeCombo.setPromptText("Select Type");
        TextField capacityField = new TextField();

        content.getChildren().addAll(
                new Label("Table ID:"), idField,
                new Label("Type:"), typeCombo,
                new Label("Capacity:"), capacityField
        );

        dialog.getDialogPane().setContent(content);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == okButtonType) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String type = typeCombo.getValue();
                    int capacity = Integer.parseInt(capacityField.getText());

                    if (type == null) {
                        showAlert(Alert.AlertType.ERROR, "Input Error", "Please select a table type.");
                        return null;
                    }

                    return new Table(id, type, capacity, 0); // default isReserved = false
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numbers.");
                }
            }
            return null;
        });
        dialog.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(App.class.getResource("/com/example/restaurantmanagementsystem/Style.css")).toExternalForm()
        );
        return dialog;
    }

    private Dialog<Table> updateTableDialog(Table selectedTable) {
        Dialog<Table> dialog = new Dialog<>();
        dialog.setTitle("Update Table");
        dialog.setHeaderText("Edit Table Details");
        dialog.setResizable(true);

        VBox content = new VBox(10);
        content.setPadding(new Insets(15));

        TextField idField = new TextField(String.valueOf(selectedTable.getId()));
        idField.setDisable(true);

        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Regular", "VIP");
        typeCombo.setValue(selectedTable.getType());

        TextField capacityField = new TextField(String.valueOf(selectedTable.getCapacity()));

        content.getChildren().addAll(
                new Label("Table ID:"), idField,
                new Label("Type:"), typeCombo,
                new Label("Capacity:"), capacityField
        );

        dialog.getDialogPane().setContent(content);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == okButtonType) {
                try {
                    int id = selectedTable.getId();
                    String type = typeCombo.getValue();
                    int capacity = Integer.parseInt(capacityField.getText());

                    if (type == null) {
                        showAlert(Alert.AlertType.ERROR, "Input Error", "Please select a table type.");
                        return null;
                    }
                    return new Table(id, type, capacity, selectedTable.getIsReserved());
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numbers.");
                }
            }
            return null;
        });
        dialog.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(App.class.getResource("/com/example/restaurantmanagementsystem/Style.css")).toExternalForm()
        );
        return dialog;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.model.DB;
import com.example.restaurantmanagementsystem.model.Table;
import com.example.restaurantmanagementsystem.model.TableManager;
import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.restaurantmanagementsystem.view.component.AlertComponent.showAlert;

public class MakeReservationScene {
    Stage stage;
    Connection conn = null;
    TableView<Table> tableView = new TableView<>();
    PreparedStatement pst = null;
    ResultSet res = null;
    ArrayList<Table> data;

    ComboBox<String> typeFilter = new ComboBox<>();
    Button backButton = new Button("Back");

    TextField tableIDTextField = new TextField();
    TextField customerNameTextField = new TextField();
    DatePicker datePicker = new DatePicker();
    Button reserveButton = new Button("Reserve");

    TableManager tables = new TableManager();
    HBox main;

    public MakeReservationScene(Stage stage) {
        this.stage = stage;
        initControls();
        initActions();
    }

    private void initControls() {
        typeFilter.getItems().addAll("All", "Regular", "VIP");
        typeFilter.setValue("All");

        tableView.setPlaceholder(new Label("No rows to display"));
        TableColumn<Table, Integer> c1 = new TableColumn<>("TableID");
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Table, String> c2 = new TableColumn<>("Type");
        c2.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<Table, Integer> c3 = new TableColumn<>("Capacity");
        c3.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        tableView.getColumns().addAll(c1, c2, c3);
        c1.setPrefWidth(150);
        c2.setPrefWidth(150);
        c3.setPrefWidth(150);

        try {
            showFilteredTables();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        HBox filterBox = new HBox(10, new Label("Filter by Type:"), typeFilter, backButton);
        filterBox.setAlignment(Pos.CENTER_RIGHT);
        filterBox.setPadding(new Insets(10));

        VBox tableBox = new VBox(10, tableView);
        tableBox.setPadding(new Insets(10));
        tableBox.setAlignment(Pos.CENTER);

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.add(tableIDTextField, 0, 0);
        form.add(customerNameTextField, 0, 1);
        form.add(datePicker, 0, 2);
        form.add(reserveButton, 0, 3);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(20));

        tableIDTextField.setPromptText("Table ID");
        customerNameTextField.setPromptText("Customer Name");
        datePicker.setPromptText("Date");
        reserveButton.setMaxWidth(Double.MAX_VALUE);

        VBox reserveBox = new VBox(10, new Label("Reserve Table"), form);
        reserveBox.setAlignment(Pos.CENTER);
        reserveBox.setPadding(new Insets(10));

        VBox mainTable = new VBox(10, filterBox, tableBox);
        mainTable.setAlignment(Pos.CENTER);
        mainTable.setPadding(new Insets(10));

        main = new HBox(20, reserveBox, mainTable);
        main.setAlignment(Pos.CENTER);
        main.setPadding(new Insets(20));
    }

    private void initActions() {
        backButton.setOnAction(e -> stage.setScene((new ReservationScene(stage)).getScene()));
        typeFilter.setOnAction(e -> {
            try {
                showFilteredTables();
            } catch (SQLException ex) {
                System.out.println("Error filtering tables: " + ex.getMessage());
            }
        });
        reserveButton.setOnAction(e -> {
            if (!tableIDTextField.getText().isEmpty() && !customerNameTextField.getText().isEmpty() && datePicker.getValue() != null) {
                int id = Integer.parseInt(tableIDTextField.getText());
                String name = customerNameTextField.getText();
                LocalDate date = datePicker.getValue();
                int[] result = tables.makeReservation(id, name, date);

                if (result[0] == 1 && result[1] == 1) {
                    showAlert(Alert.AlertType.INFORMATION, "Success!", "Reserve Success");
                    try {
                        showFilteredTables();
                    } catch (SQLException ex) {
                        System.out.println(ex.toString());
                    }
                } else {
                    tables.cancelReservation(id);
                    showAlert(Alert.AlertType.ERROR, "Failed", "Reserve failed");
                }
            } else { showAlert(Alert.AlertType.ERROR, "Enter all values", "Empty field"); }
        });
        tableView.setOnMouseClicked(e -> {
            Table selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                tableIDTextField.setText(String.valueOf(selected.getId()));
            }
        });
    }

    private void showFilteredTables() throws SQLException {
        data = new ArrayList<>();
        conn = DB.dbConnection();

        String selectedType = typeFilter.getValue();
        if (selectedType == null || selectedType.equals("All")) {
            pst = conn.prepareStatement("SELECT * FROM TABLES WHERE ISRESERVED = 0");
        } else {
            pst = conn.prepareStatement("SELECT * FROM TABLES WHERE TYPE = ? AND ISRESERVED = 0");
            pst.setString(1, selectedType);
        }

        res = pst.executeQuery();
        while (res.next()) {
            data.add(new Table(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4)));
        }

        pst.close();
        conn.close();

        tableView.getItems().setAll(sortByCapacity(data));
    }

    private ArrayList<Table> sortByCapacity(ArrayList<Table> data) {
        if (data.size() <= 1) return data;

        int mid = data.size() / 2;
        ArrayList<Table> left = new ArrayList<>(data.subList(0, mid));
        ArrayList<Table> right = new ArrayList<>(data.subList(mid, data.size()));

        left = sortByCapacity(left);
        right = sortByCapacity(right);

        return mergeByCapacity(left, right);
    }

    private ArrayList<Table> mergeByCapacity(ArrayList<Table> left, ArrayList<Table> right) {
        ArrayList<Table> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getCapacity() <= right.get(j).getCapacity()) {
                merged.add(left.get(i));
                i++;
            } else {
                merged.add(right.get(j));
                j++;
            }
        }

        while (i < left.size()) merged.add(left.get(i++));
        while (j < right.size()) merged.add(right.get(j++));

        return merged;
    }

    public Scene getScene() {
        Scene scene=new Scene(main, SceneSize.width, SceneSize.height);
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("/com/example/restaurantmanagementsystem/Style.css")).toExternalForm());
        return scene;
    }
}
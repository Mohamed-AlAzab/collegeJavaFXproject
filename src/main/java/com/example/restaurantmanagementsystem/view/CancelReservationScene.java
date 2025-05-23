package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.model.DB;
import com.example.restaurantmanagementsystem.model.Reservation;
import com.example.restaurantmanagementsystem.model.TableManager;
import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
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

public class CancelReservationScene {
    Stage stage;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet res;
    TableView<Reservation> table = new TableView<>();
    ArrayList<Reservation> data;
    TableManager manager = new TableManager();
    Label searchTitle = new Label("Search Reservation");
    Label nameLabel = new Label("Name");
    TextField nameTextField = new TextField();
    Button searchButton = new Button("Search");
    Button backButton = new Button("Back");
    Label cancelTitleLabel = new Label("Cancel Reservation");
    Label tableIdLabel = new Label("TableID");
    TextField tableIdTextField = new TextField();
    Button cancelButton = new Button("Cancel");
    GridPane root2 = new GridPane();

    GridPane root= new GridPane();
    VBox v1 = new VBox(root, root2);
    VBox v2 = new VBox(table);
    FlowPane flowPane = new FlowPane(v1, v2);

    public CancelReservationScene(Stage stage) {
        this.stage = stage;
        this.initControls();
        this.initActions();
    }

    public void initControls() {
        root.add(searchTitle, 0, 0, 2, 1);
        root.add(nameLabel, 0, 1);
        root.add(searchButton, 0, 2);
        root.add(nameTextField, 1, 1);
        root.setAlignment(Pos.CENTER_LEFT);
        root.setVgap(10);
        root.setHgap(10);
        root.setPadding(new Insets(20));
        root2.add(cancelTitleLabel, 0, 0, 2, 1);
        root2.add(tableIdLabel, 0, 1);
        root2.add(cancelButton, 0, 2);
        root2.add(tableIdTextField, 1, 1);
        root2.add(backButton, 1, 2);
        root2.setHgap((double)10.0F);
        root2.setVgap((double)10.0F);
        root2.setAlignment(Pos.CENTER_LEFT);
        root2.setPadding(new Insets((double)20.0F));
        table.setPlaceholder(new Label("No rows to display"));
        TableColumn<Reservation, Integer> c1 = new TableColumn<>("TableID");
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Reservation, String> c2 = new TableColumn<>("Customer Name");
        c2.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        TableColumn<Reservation, LocalDate> c3 = new TableColumn<>("date");
        c3.setCellValueFactory(new PropertyValueFactory<>("date"));
        table.getColumns().addAll(c1, c2, c3);
        c1.setPrefWidth(150);
        c2.setPrefWidth(150);
        c3.setPrefWidth(150);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setVgap(10);
        flowPane.setHgap(10);
        flowPane.setPadding(new Insets(20));
    }

    public void initActions() {
        searchButton.setOnAction(e -> {
            if (nameTextField.getText().isEmpty()) { showAlert(Alert.AlertType.ERROR, "Enter Name","Name field is empty"); }
            else {
                try {
                    if (data == null) { loadAllReservations(); }
                    String name = nameTextField.getText();
                    ArrayList<Reservation> result = linearSearchByName(name);

                    if (result.isEmpty()) { showAlert(Alert.AlertType.INFORMATION, "Not found", "No Reservations Found"); }
                    table.getItems().setAll(result);
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }
            }
        });
        backButton.setOnAction((e) -> {
            stage.setScene((new ReservationScene(stage)).getScene());
        });
        cancelButton.setOnAction((e) -> {
            if (tableIdTextField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Enter TableID","Table id field is empty");
            } else {
                int[] arr;
                int id = Integer.parseInt(tableIdTextField.getText());
                arr = manager.cancelReservation(id);
                if (arr[0] == 1 && arr[1] == 1) {
                    showAlert(Alert.AlertType.CONFIRMATION, "Reservation Canceled","Reservation Canceled");
                    stage.setScene(new MainScene(stage).getScene());
                } else { showAlert(Alert.AlertType.ERROR, "Wrong","Something went wrong"); }
            }
        });
    }

    private void loadAllReservations() throws SQLException {
        conn = DB.dbConnection();
        String sql = "SELECT * FROM RESERVATION";
        pst = conn.prepareStatement(sql);
        res = pst.executeQuery();

        data = new ArrayList<>();
        while (res.next()) {
            data.add(new Reservation(
                    res.getInt(1),
                    res.getString(2),
                    res.getDate(3).toLocalDate()
            ));
        }

        pst.close();
        conn.close();
    }

    private ArrayList<Reservation> linearSearchByName(String name) {
        ArrayList<Reservation> results = new ArrayList<>();
        for (Reservation reservation : data) {
            if (reservation.getCustomerName().equalsIgnoreCase(name.trim())) {
                results.add(reservation);
            }
        }
        return results;
    }

    public Scene getScene() {
        Scene scene=new Scene(flowPane, SceneSize.width, SceneSize.height);
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("/com/example/restaurantmanagementsystem/Style.css")).toExternalForm());
        return scene;
    }
}
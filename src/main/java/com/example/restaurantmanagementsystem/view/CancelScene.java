package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.model.DB;
import com.example.restaurantmanagementsystem.model.Reservation;
import com.example.restaurantmanagementsystem.model.TableManager;
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

public class CancelScene {
    Stage stage;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet res;
    TableView<Reservation> table = new TableView();
    ArrayList<Reservation> data;
    TableManager manager = new TableManager();
    Label titlesearch = new Label("Search Reservation");
    Label lname = new Label("Name");
    TextField tname = new TextField();
    Button bsearch = new Button("Search");
    Button bback = new Button("Back");
    Label titlecancel = new Label("Cancel Reservation");
    Label ltableid = new Label("TableID");
    TextField ttableid = new TextField();
    Button bcancel = new Button("Cancel");
    GridPane root2 = new GridPane();
    Alert a1=new Alert(Alert.AlertType.ERROR, "Enter Name");
    Alert a2= new Alert(Alert.AlertType.ERROR, "Enter TableID");
    Alert a3= new Alert(Alert.AlertType.CONFIRMATION, "Reservation Canceled");
    Alert a4= new Alert(Alert.AlertType.ERROR, "Something went wrong");
    GridPane root= new GridPane();
    VBox v1= new VBox(root,root2);
    VBox v2 = new VBox(table);
    FlowPane f= new FlowPane(v1, v2);

    public CancelScene(Stage stage) {
        this.stage = stage;
        this.initControls();
        this.initActions();
    }

    public void initControls() {
        root.add(titlesearch, 0, 0, 2, 1);
        root.add(lname, 0, 1);
        root.add(bsearch, 0, 2);
        root.add(tname, 1, 1);
        root.setAlignment(Pos.CENTER_LEFT);
        root.setVgap((double)10.0F);
        root.setHgap((double)10.0F);
        root.setPadding(new Insets((double)20.0F));
        root2.add(titlecancel, 0, 0, 2, 1);
        root2.add(ltableid, 0, 1);
        root2.add(bcancel, 0, 2);
        root2.add(ttableid, 1, 1);
        root2.add(bback, 1, 2);
        root2.setHgap((double)10.0F);
        root2.setVgap((double)10.0F);
        root2.setAlignment(Pos.CENTER_LEFT);
        root2.setPadding(new Insets((double)20.0F));
        table.setPlaceholder(new Label("No rows to display"));
        TableColumn<Reservation, Integer> c1 = new TableColumn("TableID");
        c1.setCellValueFactory(new PropertyValueFactory("tableId"));
        TableColumn<Reservation, String> c2 = new TableColumn("Customer Name");
        c2.setCellValueFactory(new PropertyValueFactory("customerName"));
        TableColumn<Reservation, LocalDate> c3 = new TableColumn("date");
        c3.setCellValueFactory(new PropertyValueFactory("date"));
        table.getColumns().addAll(new TableColumn[]{c1, c2, c3});
        c1.setPrefWidth((double)150.0F);
        c2.setPrefWidth((double)150.0F);
        c3.setPrefWidth((double)150.0F);
        f.setAlignment(Pos.CENTER);
        f.setVgap((double)10.0F);
        f.setHgap((double)10.0F);
        f.setPadding(new Insets(20));
    }

    public void initActions() {
        this.bsearch.setOnAction((e) -> {
            if (tname.getText().isEmpty()) {
                a1.showAndWait();
            } else {
                try {
                    conn = DB.dbConnection();
                    String name = tname.getText();
                    String sql = "SELECT * FROM RESERVATION WHERE CUSTOMER_NAME = ?";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, name);
                    res = pst.executeQuery();
                    data = new ArrayList<>();

                    while(res.next()) {
                        data.add(new Reservation(
                            res.getInt(1),
                            res.getString(2),
                            res.getDate(3).toLocalDate()
                        ));
                    }

                    pst.close();
                    conn.close();
                    table.getItems().setAll(data);
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }

            }
        });
        this.bback.setOnAction((e) -> stage.setScene((new MainScene(stage)).getScene()));
        this.bcancel.setOnAction((e) -> {
            if (this.ttableid.getText().isEmpty()) {
                this.a2.showAndWait();
            } else {
                int[] arr = new int[2];
                int id = Integer.parseInt(ttableid.getText());
                arr = manager.cancelReservation(id);
                if (arr[0] == 1 && arr[1] == 1) {
                    this.a3.showAndWait();
                } else {
                    this.a4.showAndWait();
                }

            }
        });
    }

    public Scene getScene() {
        return new Scene(f,1100,850);
    }
}
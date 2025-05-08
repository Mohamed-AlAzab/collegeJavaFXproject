package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.model.DB;
import com.example.restaurantmanagementsystem.model.Table;
import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TableScene {
    Stage stage;
    Connection conn = null;
    TableView<Table> table = new TableView<>();
    PreparedStatement pst = null;
    ResultSet res = null;
    ArrayList<Table> data;
    Button backButton = new Button("Back");
    GridPane root = new GridPane();
    VBox v1 = new VBox(table);
    VBox v2 = new VBox(root);
    VBox v3 = new VBox(v1,v2);

    public TableScene(Stage stage) {
        this.stage = stage;
        initControls();
        initActions();
    }

    public void show() throws SQLException {
        data = new ArrayList<>();
        conn = DB.dbConnection();
        pst = conn.prepareStatement("Select * from TABLES");
        res = pst.executeQuery();
        while(res.next()) {
            data.add(new Table(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4)));
        }
        pst.close();
        conn.close();
        table.getItems().setAll(data);
    }

    public void initControls() {
        root.add(this.backButton, 0, 0);
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER_RIGHT);
        table.setPlaceholder(new Label("No rows to display"));
        TableColumn<Table, Integer> c1 = new TableColumn<>("TableID");
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Table, String> c2 = new TableColumn<>("Type");
        c2.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<Table, Integer> c3 = new TableColumn<>("Capacity");
        c3.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        TableColumn<Table, Integer> c4 = new TableColumn<>("IsReserved");
        c4.setCellValueFactory(new PropertyValueFactory<>("isReserved"));
        table.getColumns().addAll(c1, c2, c3, c4);
        c1.setPrefWidth(150);
        c2.setPrefWidth(150);
        c3.setPrefWidth(150);
        c4.setPrefWidth(150);

        try {
            show();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        v3.setPadding(new Insets(20));
        v3.setAlignment(Pos.CENTER);
    }

    public void initActions() {
        backButton.setOnAction((e) ->{
            stage.setScene((new ReservationScene(stage)).getScene());
        });
    }

    public Scene getScene() {
        return new Scene(v3, SceneSize.width,SceneSize.height);
    }
}

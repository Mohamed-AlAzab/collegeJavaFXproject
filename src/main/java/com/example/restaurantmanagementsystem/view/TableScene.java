package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.model.DB;
import com.example.restaurantmanagementsystem.model.Table;
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
            data.add(new Table(res.getString(1), res.getInt(2), res.getInt(3), res.getInt(4)));
        }
        pst.close();
        conn.close();
        table.getItems().setAll(data);
    }

    public void initControls() {
        root.add(this.backButton, 0, 0);
        root.setPadding(new Insets(20));
        root.setHgap((double)10.0F);
        root.setVgap((double)10.0F);
        root.setAlignment(Pos.CENTER_RIGHT);
        table.setPlaceholder(new Label("No rows to display"));
        TableColumn<Table, String> c1 = new TableColumn<>("Type");
        c1.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<Table, Integer> c2 = new TableColumn<>("TableID");
        c2.setCellValueFactory(new PropertyValueFactory<>("tableID"));
        TableColumn<Table, Integer> c3 = new TableColumn<>("Capacity");
        c3.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        TableColumn<Table, Integer> c4 = new TableColumn<>("IsReserved");
        c4.setCellValueFactory(new PropertyValueFactory<>("isReserved"));
        table.getColumns().addAll(c1, c2, c3, c4);
        c1.setPrefWidth((double)150.0F);
        c2.setPrefWidth((double)150.0F);
        c3.setPrefWidth((double)150.0F);
        c4.setPrefWidth((double)150.0F);

        try {
            this.show();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        v3.setPadding(new Insets(20));
        v3.setAlignment(Pos.CENTER);
    }

    public void initActions() {
        backButton.setOnAction((e) ->{
            stage.setScene((new TableScene(stage)).getScene());
        });
    }

    public Scene getScene() {
        return new Scene(v3, 1100, 850);
    }
}

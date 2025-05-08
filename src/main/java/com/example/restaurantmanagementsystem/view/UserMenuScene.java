package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.model.Menu;
import com.example.restaurantmanagementsystem.util.SceneSize;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.restaurantmanagementsystem.model.Menu.fetchAllData;

public class UserMenuScene {
    Stage stage;
    private TableView<Menu> table;
    Button backbutton=new Button("Back");
    GridPane gridPane=new GridPane();
    FlowPane root = new FlowPane();
    VBox v2 = new VBox(root);
    VBox v3 = new VBox(20,v2,backbutton);

    public UserMenuScene(Stage stage) {
        this.stage = stage;
        initControl();
        initActions();
    }

    void initControl() {
        table = new TableView<>();
        root.getChildren().add(table);
        root.setAlignment(Pos.CENTER);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Menu, String> nameCol = new TableColumn<>("NAME");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Menu, Double> priceCol = new TableColumn<>("PRICE");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Menu, String> descriptionCol = new TableColumn<>("DESCRIPTION");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.getColumns().addAll(nameCol, priceCol, descriptionCol);

        root.setAlignment(Pos.CENTER);
        try {
            show();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        v3.setPadding(new Insets(20));
        v3.setAlignment(Pos.CENTER);

    }

    public void show() throws SQLException {
        ArrayList<Menu> data = fetchAllData();
        table.getItems().setAll(data);
    }
    public void initActions(){
        backbutton.setOnAction(e->{
            stage.setScene(new UserMenuMainScene(stage).getScene());
        });
    }

    Scene getScene() {
        return new Scene(v3, SceneSize.width,SceneSize.height);
    }
}
package com.example.restaurantmanagementsystem.view;
import com.example.restaurantmanagementsystem.model.TableManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.time.LocalDate;
public class ReserveScene {
    Stage stage;
    TableManager tables = new TableManager();
    Label title = new Label("Reserve Table");
    Label ltableid = new Label("TableID");
    Label lcustomername = new Label("Customer Name");
    Label ldate = new Label("Date");
    TextField ttableid = new TextField();
    TextField tcustomername = new TextField();
    DatePicker ddate = new DatePicker();
    Button breserve = new Button("Reserve");
    Button bback = new Button("Back");
    Alert alert1= new Alert(Alert.AlertType.ERROR, "Enter all values");
    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Reserve Success");
    Alert alert3= new Alert(Alert.AlertType.ERROR, "Reserve failed");
    GridPane root= new GridPane();

    public ReserveScene(Stage stage) {
        this.stage = stage;
        initCotrols();
        initActions();
    }

    public void initCotrols() {
        this.root.add(this.title, 0, 0, 2, 1);
        this.root.add(this.ltableid, 0, 1);
        this.root.add(lcustomername, 0, 2);
        this.root.add(ldate, 0, 3);
        this.root.add(breserve, 0, 4);
        this.root.add(ttableid, 1, 1);
        this.root.add(tcustomername, 1, 2);
        this.root.add(ddate, 1, 3);
        this.root.add(bback, 1, 4);
        this.root.setHgap(10);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
    }

    public void initActions() {
        this.breserve.setOnAction((e) -> {
            if (!ttableid.getText().isEmpty() && !tcustomername.getText().isEmpty() && this.ddate.getValue() != null) {
                int[] arr = new int[2];
                int id = Integer.parseInt(ttableid.getText());
                String name = tcustomername.getText();
                LocalDate date = (LocalDate)ddate.getValue();
                arr = tables.makeReservation(id, name, date);
                if (arr[0] == 1 && arr[1] == 1) {
                    alert2.showAndWait();
                } else {
                    tables.cancelReservation(id);
                    alert3.showAndWait();
                }

            } else {
                alert1.showAndWait();
            }
        });
        bback.setOnAction((e) -> stage.setScene((new MainScene(stage)).getScene()));
    }

    public Scene getScene() {
        return new Scene(root, 1100, 850);
    }
}
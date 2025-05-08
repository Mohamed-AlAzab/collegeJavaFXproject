package com.example.restaurantmanagementsystem.view;
import com.example.restaurantmanagementsystem.model.TableManager;
import com.example.restaurantmanagementsystem.util.SceneSize;
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
    Button reserveButton = new Button("Reserve");
    Button backButton = new Button("Back");
    Alert alert1 = new Alert(Alert.AlertType.ERROR, "Enter all values");
    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Reserve Success");
    Alert alert3 = new Alert(Alert.AlertType.ERROR, "Reserve failed");
    GridPane root= new GridPane();

    public ReserveScene(Stage stage) {
        this.stage = stage;
        initControls();
        initActions();
    }

    public void initControls() {
        root.add(this.title, 0, 0, 2, 1);
        root.add(this.ltableid, 0, 1);
        root.add(lcustomername, 0, 2);
        root.add(ldate, 0, 3);
        root.add(reserveButton, 0, 4);
        root.add(ttableid, 1, 1);
        root.add(tcustomername, 1, 2);
        root.add(ddate, 1, 3);
        root.add(backButton, 1, 4);
        root.setHgap(10);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
    }

    public void initActions() {
        reserveButton.setOnAction((e) -> {
            if (!ttableid.getText().isEmpty() && !tcustomername.getText().isEmpty() && ddate.getValue() != null) {
                int[] arr;
                int id = Integer.parseInt(ttableid.getText());
                String name = tcustomername.getText();
                LocalDate date = ddate.getValue();
                arr = tables.makeReservation(id, name, date);
                if (arr[0] == 1 && arr[1] == 1) {
                    alert2.showAndWait();
                    stage.setScene(new MainScene(stage).getScene());
                } else {
                    tables.cancelReservation(id);
                    alert3.showAndWait();
                }
            } else {
                alert1.showAndWait();
            }

        });
        backButton.setOnAction((e) -> stage.setScene((new ReservationScene(stage)).getScene()));
    }

    public Scene getScene() {
        return new Scene(root, SceneSize.width,SceneSize.height);
    }
}
package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



import static com.example.restaurantmanagementsystem.view.component.CardComponent.Card;

public class AdminScene {
    Stage stage;
    String menuIcon = "C:\\Users\\DCS\\Desktop\\assets\\menu.png";
    String employeesIcon = "C:\\Users\\DCS\\Desktop\\assets\\employees.png";
    String reservationIcon = "C:\\Users\\DCS\\Desktop\\assets\\tablesReserved.png";
    String accountingIcon = "C:\\Users\\DCS\\Desktop\\assets\\accounting.png";
    String backIcon="C:\\Users\\DCS\\Desktop\\assets\\back.png";

    StackPane menuCard = Card(menuIcon, "Menu management");
    StackPane employeeCard = Card(employeesIcon, "Employee management");
    StackPane tableCard = Card(reservationIcon, "Table management");
    StackPane accountingCard = Card(accountingIcon, "Accounting management");
    StackPane backCard=Card(backIcon,"Back");

    HBox cardRow = new HBox(20, menuCard, employeeCard, tableCard);
    HBox cardRow2=new HBox(20,accountingCard,backCard);
    VBox root = new VBox(20,cardRow,cardRow2);

    public AdminScene(Stage stage) {
        this.stage = stage;
        initControls();
        initActions();
    }

    public void initControls() {
        cardRow.setAlignment(Pos.CENTER);
        cardRow2.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
    }

    public void initActions() {
        menuCard.setOnMouseClicked(event -> {
            try {
                new MenuScene().start(new Stage()); // Launches in a new window
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        });
        employeeCard.setOnMouseClicked(event -> {
            try {
                new EmployeeMainScene().start(new Stage()); // Launches in a new window
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        });
        tableCard.setOnMouseClicked(event -> {
            try {
                new AdminTableScene().start(new Stage());
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        });
        accountingCard.setOnMouseClicked(event -> {
            stage.setScene(new AccountingManagementScene(stage).getScene());
        });
        backCard.setOnMouseClicked(e->{
            stage.setScene(new MainScene(stage).getScene());
        });
    }

    public Scene getScene() {
        return new Scene(root, SceneSize.width,SceneSize.height);
    }
}
// database username: restaurant
// application express username: restaurant
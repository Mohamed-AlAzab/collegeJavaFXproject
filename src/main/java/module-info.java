module com.example.restaurantmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.oracle.database.jdbc;


    opens com.example.restaurantmanagementsystem to javafx.fxml;

    exports com.example.restaurantmanagementsystem.view;
    opens com.example.restaurantmanagementsystem.view to javafx.fxml;
    exports com.example.restaurantmanagementsystem.model;
    opens com.example.restaurantmanagementsystem.model to javafx.fxml;
    exports com.example.restaurantmanagementsystem.view.component;
    opens com.example.restaurantmanagementsystem.view.component to javafx.fxml;
}
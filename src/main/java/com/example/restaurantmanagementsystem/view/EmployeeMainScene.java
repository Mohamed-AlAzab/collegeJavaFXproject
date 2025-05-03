package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeMainScene extends Application {
    private EmployeeManager manager;
    private ObservableList<Employee> employees;

    @Override
    public void start(Stage primaryStage) {
        TableView<Employee> table = new TableView<>();
        TableColumn<Employee, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

        TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Employee, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRole()));

        TableColumn<Employee, Double> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().calculateSalary()).asObject());


        table.getColumns().addAll(idColumn, nameColumn, roleColumn, salaryColumn);


        manager = new EmployeeManager();
        employees = FXCollections.observableArrayList(manager.getAllEmployees());
        table.setItems(employees);

        // إضافة أزرار
        Button addButton = new Button("Add Employee");
        Button deleteButton = new Button("Delete Employee");
        Button undoButton = new Button("Undo");
        Button resetButton = new Button("Reset to Initial State");

        // زر الإضافة
        addButton.setOnAction(e -> {
            Dialog<Employee> dialog = new Dialog<>();
            dialog.setTitle("Add New Employee");
            dialog.setHeaderText("Enter Employee Details");

            // إعداد الحقول
            TextField idField = new TextField();
            TextField nameField = new TextField();
            ComboBox<String> roleCombo = new ComboBox<>();
            roleCombo.getItems().addAll("Chef", "Manager", "Waiter", "Delivery Person", "Kitchen Assistant");

            TextField salaryField = new TextField();
            TextField discountField = new TextField();
            TextField bonusField = new TextField();
            TextField hoursWorkedField = new TextField();
            TextField hourlyRateField = new TextField();
            TextField tipsField = new TextField();
            TextField ordersDeliveredField = new TextField();
            TextField payPerOrderField = new TextField();

            VBox dialogContent = new VBox(10);
            dialogContent.setPadding(new Insets(10));
            dialogContent.getChildren().addAll(
                    new Label("ID:"), idField,
                    new Label("Name:"), nameField,
                    new Label("Role:"), roleCombo
            );

            // تغيير الحقول بناءً على الدور
            roleCombo.setOnAction(event -> {
                dialogContent.getChildren().clear();
                dialogContent.getChildren().addAll(
                        new Label("ID:"), idField,
                        new Label("Name:"), nameField,
                        new Label("Role:"), roleCombo
                );

                String role = roleCombo.getValue();
                if (role != null) {
                    switch (role) {
                        case "Chef":
                            dialogContent.getChildren().addAll(
                                    new Label("Salary:"), salaryField,
                                    new Label("Discount:"), discountField
                            );
                            break;
                        case "Manager":
                            dialogContent.getChildren().addAll(
                                    new Label("Salary:"), salaryField,
                                    new Label("Bonus:"), bonusField
                            );
                            break;
                        case "Waiter":
                            dialogContent.getChildren().addAll(
                                    new Label("Hours Worked:"), hoursWorkedField,
                                    new Label("Hourly Rate:"), hourlyRateField,
                                    new Label("Tips:"), tipsField
                            );
                            break;
                        case "Delivery Person":
                            dialogContent.getChildren().addAll(
                                    new Label("Orders Delivered:"), ordersDeliveredField,
                                    new Label("Pay Per Order:"), payPerOrderField
                            );
                            break;
                        case "Kitchen Assistant":
                            dialogContent.getChildren().addAll(
                                    new Label("Hours Worked:"), hoursWorkedField,
                                    new Label("Hourly Rate:"), hourlyRateField
                            );
                            break;
                    }
                }
            });

            dialog.getDialogPane().setContent(dialogContent);


            ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButtonType) {
                    try {
                        int id = Integer.parseInt(idField.getText());
                        String name = nameField.getText();
                        String role = roleCombo.getValue();

                        Employee employee = null;
                        switch (role) {
                            case "Chef":
                                double salary = Double.parseDouble(salaryField.getText());
                                double discount = Double.parseDouble(discountField.getText());
                                employee = new Chef(id, name, salary, discount);
                                break;
                            case "Manager":
                                salary = Double.parseDouble(salaryField.getText());
                                double bonus = Double.parseDouble(bonusField.getText());
                                employee = new Manager(id, name, salary, bonus);
                                break;
                            case "Waiter":
                                int hoursWorked = Integer.parseInt(hoursWorkedField.getText());
                                double hourlyRate = Double.parseDouble(hourlyRateField.getText());
                                double tips = Double.parseDouble(tipsField.getText());
                                employee = new Waiter(id, name, tips, hourlyRate, hoursWorked);
                                break;
                            case "Delivery Person":
                                int ordersDelivered = Integer.parseInt(ordersDeliveredField.getText());
                                double payPerOrder = Double.parseDouble(payPerOrderField.getText());
                                employee = new Delivery(id, name, ordersDelivered, payPerOrder);
                                break;
                            case "Kitchen Assistant":
                                hoursWorked = Integer.parseInt(hoursWorkedField.getText());
                                hourlyRate = Double.parseDouble(hourlyRateField.getText());
                                employee = new KitchenAssistant(id, name, hoursWorked, hourlyRate);
                                break;
                        }
                        return employee;
                    } catch (NumberFormatException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid numbers.");
                        alert.showAndWait();
                        return null;
                    }
                }
                return null;
            });

            dialog.showAndWait().ifPresent(employee -> {
                if (employee != null) {
                    manager.addEmployee(employee);
                    employees.setAll(manager.getAllEmployees());
                }
            });
        });
        deleteButton.setOnAction(e -> {
            Employee selectedEmployee = table.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null) {
                manager.removeEmployee(selectedEmployee.getEmployeeId());
                employees.setAll(manager.getAllEmployees());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an employee to delete.");
                alert.showAndWait();
            }
        });
        undoButton.setOnAction(e -> {
            manager.undoLastOperation();
            employees.setAll(manager.getAllEmployees());
        });

        resetButton.setOnAction(e -> {
            manager.resetToInitialState();
            employees.setAll(manager.getAllEmployees());
        });
        HBox buttonBox = new HBox(10, addButton, deleteButton, undoButton, resetButton);
        buttonBox.setPadding(new Insets(10));
        VBox vbox = new VBox(table, buttonBox);
        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setTitle("Employee Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
        manager.addEmployee(new Chef(1, "Ahmed", 5000, 500));
        manager.addEmployee(new Manager(2, "Sara", 6000, 1000));
        manager.addEmployee(new Waiter(3, "Mohamed", 200, 50, 40));
        manager.addEmployee(new Delivery(4, "Ali", 20, 30));
        manager.addEmployee(new KitchenAssistant(5, "Fatima", 35, 40));
        employees.setAll(manager.getAllEmployees());
    }
    public static void main(String[] args) {
        launch(args);
    }
}
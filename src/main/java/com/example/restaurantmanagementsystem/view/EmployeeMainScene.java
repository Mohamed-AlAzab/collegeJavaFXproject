package com.example.restaurantmanagementsystem.view;

import com.example.restaurantmanagementsystem.model.*;
import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;

import static com.example.restaurantmanagementsystem.controller.EmployeeController.getEmail;
import static com.example.restaurantmanagementsystem.controller.EmployeeController.getUserID;
import static com.example.restaurantmanagementsystem.view.component.AlertComponent.showAlert;

public class EmployeeMainScene extends Application {
    private EmployeeManager manager;
    private ObservableList<Employee> employees;

    @Override
    public void start(Stage primaryStage) {
        TableView<Employee> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Employee, Integer> idColumn = new TableColumn<>("Employee ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Employee, Integer> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Employee, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRole()));

        TableColumn<Employee, Double> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().calculateSalary()).asObject());

        table.getColumns().addAll(idColumn, userIdColumn, nameColumn, roleColumn, salaryColumn);

        manager = new EmployeeManager();
        employees = FXCollections.observableArrayList(manager.fetchAllEmployees());
        table.setItems(employees);

        Button addButton = new Button("Add Employee");
        addButton.setPrefWidth(150);

        Button deleteButton = new Button("Delete Employee");
        deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        deleteButton.setPrefWidth(150);

        Button updateButton = new Button("Update Employee");
        updateButton.setPrefWidth(150);

        Button undoButton = new Button("Undo");
        undoButton.setPrefWidth(150);

        VBox buttonBox = new VBox(10, addButton, updateButton, undoButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setPadding(new Insets(10));

        StackPane tablePane = new StackPane(table);
        tablePane.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setLeft(buttonBox);
        root.setCenter(tablePane);

        addButton.setOnAction(e -> {
            Dialog<Employee> dialog = addEmployeeDialog();
            dialog.showAndWait().ifPresent(employee -> {
                manager.addEmployee(employee);
                employees.setAll(manager.fetchAllEmployees());
            });
        });

        deleteButton.setOnAction(e -> {
            Employee selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                manager.removeEmployee(selected.getId());
                employees.setAll(manager.fetchAllEmployees());
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Selection");
                alert.setHeaderText(null);
                alert.setContentText("Please select an employee to delete.");
                alert.showAndWait();
            }
        });

        updateButton.setOnAction(e -> {
            Employee selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Dialog<Employee> dialog = updateEmployeeDialog(selected);
                dialog.showAndWait().ifPresent(employee -> {
                    manager.updateEmployee(employee, getEmail(selected.getUserId()));
                    employees.setAll(manager.fetchAllEmployees());
                    showAlert(Alert.AlertType.INFORMATION, "Success!", "Updated Success");
                });
            } else {
                showAlert(Alert.AlertType.INFORMATION, "No Selection","Please select an employee to upgrade.");
            }
        });

        undoButton.setOnAction(e -> {
            manager.undoLastOperation();
            employees.setAll(manager.fetchAllEmployees());
        });

        Scene scene = new Scene(root, SceneSize.width, SceneSize.height);
        primaryStage.setTitle("Employee Management System");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("/com/example/restaurantmanagementsystem/Style.css")).toExternalForm());
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    private Dialog<Employee> addEmployeeDialog() {
        Dialog<Employee> dialog = new Dialog<>();
        dialog.setTitle("Add New Employee");
        dialog.setHeaderText("Enter Employee Details");
        dialog.setResizable(true);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox content = new VBox(10);
        content.setPadding(new Insets(15));

        TextField emailField = new TextField();
        emailField.setPrefWidth(300);
        TextField nameField = new TextField();
        nameField.setPrefWidth(300);
        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("Chef", "Manager", "Waiter", "Delivery", "Kitchen Assistant");
        roleCombo.setPrefWidth(300);
        roleCombo.setPromptText("Select Role");

        TextField salaryField = new TextField();
        TextField discountField = new TextField();
        TextField bonusField = new TextField();
        TextField hoursField = new TextField();
        TextField rateField = new TextField();
        TextField tipsField = new TextField();
        TextField ordersField = new TextField();
        TextField payPerOrderField = new TextField();

        content.getChildren().addAll(
                new Label("Email:"), emailField,
                new Label("Name:"), nameField,
                new Label("Role:"), roleCombo
        );

        Runnable updateFields = () -> {
            String role = roleCombo.getValue();
            content.getChildren().remove(6, content.getChildren().size());

            salaryField.clear();
            discountField.clear();
            bonusField.clear();
            hoursField.clear();
            rateField.clear();
            tipsField.clear();
            ordersField.clear();
            payPerOrderField.clear();

            if (role != null) {
                switch (role) {
                    case "Chef":
                    case "Manager":
                        content.getChildren().addAll(
                                new Label("Salary:"), salaryField,
                                new Label("Discount:"), discountField,
                                new Label("Bonus:"), bonusField
                        );
                        Platform.runLater(() -> salaryField.requestFocus());
                        break;
                    case "Waiter":
                    case "Kitchen Assistant":
                        content.getChildren().addAll(
                                new Label("Hours Worked:"), hoursField,
                                new Label("Hourly Rate:"), rateField,
                                new Label("Tips:"), tipsField,
                                new Label("Discount:"), discountField,
                                new Label("Bonus:"), bonusField
                        );
                        Platform.runLater(() -> hoursField.requestFocus());
                        break;
                    case "Delivery Person":
                        content.getChildren().addAll(
                                new Label("Orders Delivered:"), ordersField,
                                new Label("Pay Per Order:"), payPerOrderField,
                                new Label("Tips:"), tipsField,
                                new Label("Discount:"), discountField,
                                new Label("Bonus:"), bonusField
                        );
                        Platform.runLater(() -> ordersField.requestFocus());
                        break;
                }
            }
        };

        roleCombo.setOnAction(e -> updateFields.run());

        emailField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                nameField.requestFocus();
                event.consume();
            }
        });

        nameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) { roleCombo.requestFocus(); event.consume(); }
        });

        roleCombo.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && roleCombo.getValue() != null) {
                String role = roleCombo.getValue();
                switch (role) {
                    case "Chef":
                    case "Manager":
                        salaryField.requestFocus();
                        break;
                    case "Waiter":
                    case "Kitchen Assistant":
                        hoursField.requestFocus();
                        break;
                    case "Delivery":
                        ordersField.requestFocus();
                        break;
                }
                event.consume();
            }
        });

        salaryField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) { discountField.requestFocus(); event.consume(); }
        });

        discountField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) { bonusField.requestFocus(); event.consume(); }
        });

        bonusField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Button okButton = (Button) dialog.getDialogPane().lookupButton(
                        dialog.getDialogPane().getButtonTypes().stream()
                                .filter(bt -> bt.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                                .findFirst().orElse(null)
                );
                if (okButton != null) {
                    Platform.runLater(() -> okButton.requestFocus());
                }
                event.consume();
            }
        });

        hoursField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                rateField.requestFocus();
                event.consume();
            }
        });

        rateField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                tipsField.requestFocus();
                event.consume();
            }
        });

        tipsField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                discountField.requestFocus();
                event.consume();
            }
        });

        ordersField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                payPerOrderField.requestFocus();
                event.consume();
            }
        });

        payPerOrderField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                tipsField.requestFocus();
                event.consume();
            }
        });

        scrollPane.setContent(content);
        dialog.getDialogPane().setContent(scrollPane);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setOnShown(event -> {
            Platform.runLater(() -> {
                emailField.requestFocus();
                Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
                okButton.setDefaultButton(false);
            });
        });
        Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);

        okButton.setOnAction(event -> {
            try {
                if(getUserID(emailField.getText()) == null){
                    showAlert(Alert.AlertType.ERROR, "Invalid", "Email is empty");
                    return;
                }
                int userId = getUserID(emailField.getText());

                String name = nameField.getText();
                String role = roleCombo.getValue();

                if (role == null) {
                    showAlert(Alert.AlertType.ERROR, "Input Error", "Please select a role.");
                    Platform.runLater(() -> roleCombo.requestFocus());
                    event.consume();
                    return;
                }

                if (name.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Input Error", "Name cannot be empty.");
                    Platform.runLater(() -> nameField.requestFocus());
                    event.consume();
                    return;
                }

                Employee employee = null;
                switch (role) {
                    case "Chef":
                    case "Manager":
                        if (salaryField.getText().isEmpty() || discountField.getText().isEmpty() ||
                                bonusField.getText().isEmpty()) {
                            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill all fields.");
                            event.consume();
                            return;
                        }
                        if (role.equals("Chef")) {
                            employee = new Chef(userId, name,
                                    Double.parseDouble(salaryField.getText()),
                                    Double.parseDouble(discountField.getText()),
                                    Double.parseDouble(bonusField.getText()));
                        } else {
                            employee = new Manager(userId, name,
                                    Double.parseDouble(salaryField.getText()),
                                    Double.parseDouble(discountField.getText()),
                                    Double.parseDouble(bonusField.getText()));
                        }
                        break;
                    case "Waiter":
                    case "Kitchen Assistant":
                        if (hoursField.getText().isEmpty() || rateField.getText().isEmpty() ||
                                tipsField.getText().isEmpty() || discountField.getText().isEmpty() ||
                                bonusField.getText().isEmpty()) {
                            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill all fields.");
                            event.consume();
                            return;
                        }
                        if (role.equals("Waiter")) {
                            employee = new Waiter(userId, name,
                                    Double.parseDouble(tipsField.getText()),
                                    Double.parseDouble(rateField.getText()),
                                    Double.parseDouble(hoursField.getText()),
                                    Double.parseDouble(discountField.getText()),
                                    Double.parseDouble(bonusField.getText()));
                        } else {
                            employee = new KitchenAssistant(userId, name,
                                    Double.parseDouble(hoursField.getText()),
                                    Double.parseDouble(rateField.getText()),
                                    Double.parseDouble(tipsField.getText()),
                                    Double.parseDouble(discountField.getText()),
                                    Double.parseDouble(bonusField.getText()));
                        }
                        break;
                    case "Delivery Person":
                        if (ordersField.getText().isEmpty() || payPerOrderField.getText().isEmpty() ||
                                tipsField.getText().isEmpty() || discountField.getText().isEmpty() ||
                                bonusField.getText().isEmpty()) {
                            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill all fields.");
                            event.consume();
                            return;
                        }
                        employee = new Delivery(userId, name,
                                Integer.parseInt(ordersField.getText()),
                                Double.parseDouble(payPerOrderField.getText()),
                                Double.parseDouble(tipsField.getText()),
                                Double.parseDouble(discountField.getText()),
                                Double.parseDouble(bonusField.getText()));
                        break;
                }

                if (employee != null) {
                    dialog.setResult(employee);
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numbers.");
                Platform.runLater(() -> emailField.requestFocus());
                event.consume();
            }
        });

        dialog.setResultConverter(button -> {
            if (button == okButtonType) return null;
            return null;
        });

        dialog.getDialogPane().getStyleClass().add("employee-dialog");

        emailField.getStyleClass().add("text-field");
        nameField.getStyleClass().add("text-field");
        roleCombo.getStyleClass().add("combo-box");

        salaryField.getStyleClass().add("number-field");
        discountField.getStyleClass().add("number-field");
        bonusField.getStyleClass().add("number-field");
        hoursField.getStyleClass().add("number-field");
        rateField.getStyleClass().add("number-field");
        tipsField.getStyleClass().add("number-field");
        ordersField.getStyleClass().add("number-field");
        payPerOrderField.getStyleClass().add("number-field");

        content.getStyleClass().add("dialog-content");

        dialog.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(App.class.getResource("/com/example/restaurantmanagementsystem/Style.css")).toExternalForm()
        );
        return dialog;
    }

    private Dialog<Employee> updateEmployeeDialog(Employee selectedEmployee) {
        Dialog<Employee> dialog = new Dialog<>();
        dialog.setTitle("Upgrade Employee");
        dialog.setHeaderText("Update Employee Details");
        dialog.setResizable(true);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox content = new VBox(10);
        content.setPadding(new Insets(15));

        TextField emailField = new TextField(String.valueOf(getEmail(selectedEmployee.getUserId())));
        emailField.setPrefWidth(300);
        TextField nameField = new TextField(selectedEmployee.getName());
        nameField.setPrefWidth(300);
        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("Chef", "Manager", "Waiter", "Delivery Person", "Kitchen Assistant");
        roleCombo.setPrefWidth(300);
        roleCombo.setValue(selectedEmployee.getRole());

        TextField salaryField = new TextField();
        TextField discountField = new TextField();
        TextField bonusField = new TextField();
        TextField hoursField = new TextField();
        TextField rateField = new TextField();
        TextField tipsField = new TextField();
        TextField ordersField = new TextField();
        TextField payPerOrderField = new TextField();

        content.getChildren().addAll(
                new Label("Email:"), emailField,
                new Label("Name:"), nameField,
                new Label("Role:"), roleCombo
        );

        Runnable updateFields = () -> {
            String role = roleCombo.getValue();
            content.getChildren().remove(6, content.getChildren().size());

            salaryField.clear();
            discountField.clear();
            bonusField.clear();
            hoursField.clear();
            rateField.clear();
            tipsField.clear();
            ordersField.clear();
            payPerOrderField.clear();

            if (role != null && role.equals(selectedEmployee.getRole())) {
                if (selectedEmployee instanceof Chef) {
                    Chef chef = (Chef) selectedEmployee;
                    salaryField.setText(String.valueOf(chef.getSalary()));
                    discountField.setText(String.valueOf(chef.getDiscount()));
                    bonusField.setText(String.valueOf(chef.getBonus()));
                } else if (selectedEmployee instanceof Manager) {
                    Manager manager = (Manager) selectedEmployee;
                    salaryField.setText(String.valueOf(manager.getSalary()));
                    discountField.setText(String.valueOf(manager.getDiscount()));
                    bonusField.setText(String.valueOf(manager.getBonus()));
                } else if (selectedEmployee instanceof Waiter) {
                    Waiter waiter = (Waiter) selectedEmployee;
                    hoursField.setText(String.valueOf(waiter.getHoursWorked()));
                    rateField.setText(String.valueOf(waiter.getHourlyRate()));
                    tipsField.setText(String.valueOf(waiter.getTips()));
                    discountField.setText(String.valueOf(waiter.getDiscount()));
                    bonusField.setText(String.valueOf(waiter.getBonus()));
                } else if (selectedEmployee instanceof KitchenAssistant) {
                    KitchenAssistant assistant = (KitchenAssistant) selectedEmployee;
                    hoursField.setText(String.valueOf(assistant.getHoursWorked()));
                    rateField.setText(String.valueOf(assistant.getHourlyRate()));
                    tipsField.setText(String.valueOf(assistant.getTips()));
                    discountField.setText(String.valueOf(assistant.getDiscount()));
                    bonusField.setText(String.valueOf(assistant.getBonus()));
                } else if (selectedEmployee instanceof Delivery) {
                    Delivery delivery = (Delivery) selectedEmployee;
                    ordersField.setText(String.valueOf(delivery.getOrdersDelivered()));
                    payPerOrderField.setText(String.valueOf(delivery.getPayPerOrder()));
                    tipsField.setText(String.valueOf(delivery.getTips()));
                    discountField.setText(String.valueOf(delivery.getDiscount()));
                    bonusField.setText(String.valueOf(delivery.getBonus()));
                }
            }

            if (role != null) {
                switch (role) {
                    case "Chef":
                    case "Manager":
                        content.getChildren().addAll(
                                new Label("Salary:"), salaryField,
                                new Label("Discount:"), discountField,
                                new Label("Bonus:"), bonusField
                        );
                        Platform.runLater(() -> salaryField.requestFocus());
                        break;
                    case "Waiter":
                    case "Kitchen Assistant":
                        content.getChildren().addAll(
                                new Label("Hours Worked:"), hoursField,
                                new Label("Hourly Rate:"), rateField,
                                new Label("Tips:"), tipsField,
                                new Label("Discount:"), discountField,
                                new Label("Bonus:"), bonusField
                        );
                        Platform.runLater(() -> hoursField.requestFocus());
                        break;
                    case "Delivery Person":
                        content.getChildren().addAll(
                                new Label("Orders Delivered:"), ordersField,
                                new Label("Pay Per Order:"), payPerOrderField,
                                new Label("Tips:"), tipsField,
                                new Label("Discount:"), discountField,
                                new Label("Bonus:"), bonusField
                        );
                        Platform.runLater(() -> ordersField.requestFocus());
                        break;
                }
            }
        };
        roleCombo.setOnAction(e -> updateFields.run());
        updateFields.run();
        emailField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                nameField.requestFocus();
                event.consume();
            }
        });

        nameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                roleCombo.requestFocus();
                event.consume();
            }
        });

        roleCombo.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && roleCombo.getValue() != null) {
                String role = roleCombo.getValue();
                switch (role) {
                    case "Chef":
                    case "Manager":
                        salaryField.requestFocus();
                        break;
                    case "Waiter":
                    case "Kitchen Assistant":
                        hoursField.requestFocus();
                        break;
                    case "Delivery Person":
                        ordersField.requestFocus();
                        break;
                }
                event.consume();
            }
        });

        salaryField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                discountField.requestFocus();
                event.consume();
            }
        });

        discountField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                bonusField.requestFocus();
                event.consume();
            }
        });

        bonusField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Button okButton = (Button) dialog.getDialogPane().lookupButton(
                        dialog.getDialogPane().getButtonTypes().stream()
                                .filter(bt -> bt.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                                .findFirst().orElse(null)
                );
                if (okButton != null) {
                    Platform.runLater(() -> okButton.requestFocus());
                }
                event.consume();
            }
        });

        hoursField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                rateField.requestFocus();
                event.consume();
            }
        });

        rateField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                tipsField.requestFocus();
                event.consume();
            }
        });

        tipsField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                discountField.requestFocus();
                event.consume();
            }
        });

        ordersField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                payPerOrderField.requestFocus();
                event.consume();
            }
        });

        payPerOrderField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                tipsField.requestFocus();
                event.consume();
            }
        });

        scrollPane.setContent(content);
        dialog.getDialogPane().setContent(scrollPane);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setOnShown(event -> {
            Platform.runLater(() -> {
                emailField.requestFocus();
                Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
                okButton.setDefaultButton(false);
            });
        });

        Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setOnAction(event -> {
            try {
                if(getUserID(emailField.getText()) == null){
                    showAlert(Alert.AlertType.ERROR, "Invalid", "Email is empty");
                    return;
                }
                int userId = getUserID(emailField.getText());

                String name = nameField.getText();
                String role = roleCombo.getValue();

                if (role == null) {
                    showAlert(Alert.AlertType.ERROR, "Input Error", "Please select a role.");
                    Platform.runLater(() -> roleCombo.requestFocus());
                    event.consume();
                    return;
                }

                if (name.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Input Error", "Name field can't be empty.");
                    Platform.runLater(() -> nameField.requestFocus());
                    event.consume();
                    return;
                }

                Employee employee = null;
                switch (role) {
                    case "Chef":
                    case "Manager":
                        if (salaryField.getText().isEmpty() || discountField.getText().isEmpty() ||
                                bonusField.getText().isEmpty()) {
                            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill all fields.");
                            event.consume();
                            return;
                        }
                        if (role.equals("Chef")) {
                            employee = new Chef(userId, name,
                                    Double.parseDouble(salaryField.getText()),
                                    Double.parseDouble(discountField.getText()),
                                    Double.parseDouble(bonusField.getText()));
                        } else {
                            employee = new Manager(userId, name,
                                    Double.parseDouble(salaryField.getText()),
                                    Double.parseDouble(discountField.getText()),
                                    Double.parseDouble(bonusField.getText()));
                        }
                        employee.setId(selectedEmployee.getId());
                        break;
                    case "Waiter":
                    case "Kitchen Assistant":
                        if (hoursField.getText().isEmpty() || rateField.getText().isEmpty() ||
                                tipsField.getText().isEmpty() || discountField.getText().isEmpty() ||
                                bonusField.getText().isEmpty()) {
                            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill all fields.");
                            event.consume();
                            return;
                        }
                        if (role.equals("Waiter")) {
                            employee = new Waiter(userId, name,
                                    Double.parseDouble(tipsField.getText()),
                                    Double.parseDouble(rateField.getText()),
                                    Double.parseDouble(hoursField.getText()),
                                    Double.parseDouble(discountField.getText()),
                                    Double.parseDouble(bonusField.getText()));
                        } else {
                            employee = new KitchenAssistant(userId, name,
                                    Double.parseDouble(hoursField.getText()),
                                    Double.parseDouble(rateField.getText()),
                                    Double.parseDouble(tipsField.getText()),
                                    Double.parseDouble(discountField.getText()),
                                    Double.parseDouble(bonusField.getText()));
                        }
                        employee.setId(selectedEmployee.getId());
                        break;
                    case "Delivery Person":
                        if (ordersField.getText().isEmpty() || payPerOrderField.getText().isEmpty() ||
                                tipsField.getText().isEmpty() || discountField.getText().isEmpty() ||
                                bonusField.getText().isEmpty()) {
                            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill all fields.");
                            event.consume();
                            return;
                        }
                        employee = new Delivery(userId, name,
                                Integer.parseInt(ordersField.getText()),
                                Double.parseDouble(payPerOrderField.getText()),
                                Double.parseDouble(tipsField.getText()),
                                Double.parseDouble(discountField.getText()),
                                Double.parseDouble(bonusField.getText()));
                        employee.setId(selectedEmployee.getId());
                        break;
                }

                if (employee != null) dialog.setResult(employee);

            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numbers.");
                Platform.runLater(() -> emailField.requestFocus());
                event.consume();
            }
        });

        dialog.setResultConverter(button -> {
            if (button == okButtonType) return null;
            return null;
        });
        dialog.getDialogPane().getStyleClass().add("employee-dialog");

        emailField.getStyleClass().add("text-field");
        nameField.getStyleClass().add("text-field");
        roleCombo.getStyleClass().add("combo-box");

        salaryField.getStyleClass().add("number-field");
        discountField.getStyleClass().add("number-field");
        bonusField.getStyleClass().add("number-field");
        hoursField.getStyleClass().add("number-field");
        rateField.getStyleClass().add("number-field");
        tipsField.getStyleClass().add("number-field");
        ordersField.getStyleClass().add("number-field");
        payPerOrderField.getStyleClass().add("number-field");

        content.getStyleClass().add("dialog-content");

        dialog.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(App.class.getResource("/com/example/restaurantmanagementsystem/Style.css")).toExternalForm()
        );

        return dialog;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
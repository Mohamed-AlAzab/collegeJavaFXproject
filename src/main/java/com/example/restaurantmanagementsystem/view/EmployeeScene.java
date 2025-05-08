package com.example.restaurantmanagementsystem.view;


import com.example.restaurantmanagementsystem.util.SceneSize;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EmployeeScene {
  Label label = new Label("Hello");
  GridPane gridPane = new GridPane();
  Stage stage;
  public EmployeeScene(Stage stage){
      this.stage = stage;
      initControls();
  }
  public void initControls(){
      gridPane.add(label,0,0);
      gridPane.setAlignment(Pos.CENTER);
  }
  public Scene getScene(){
      return new Scene(gridPane, SceneSize.width, SceneSize.height);
  }
}
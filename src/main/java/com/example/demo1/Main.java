package com.example.demo1;

import com.example.demo1.controller.CaloriasController;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
      new CaloriasController().showEstaPantalla(stage);
    }

    public static void main(String[] args) {
        launch(args);  // Lanzar la aplicación JavaFX
    }

}


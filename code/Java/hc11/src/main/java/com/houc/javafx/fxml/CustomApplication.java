package com.houc.javafx.fxml;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CustomApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        CustomControl customControl = new CustomControl();
        customControl.setText("Hello");
        primaryStage.setScene(new Scene(customControl));
        primaryStage.setWidth(300);
        primaryStage.setHeight(200);
        primaryStage.show();
    }
}

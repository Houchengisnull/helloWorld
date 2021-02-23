package com.houc.javafx.fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EasyController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/easy.fxml"));
        Parent load = (Parent) fxmlLoader.load();
        Scene scene = new Scene(load, 640, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

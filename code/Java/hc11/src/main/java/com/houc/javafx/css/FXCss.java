package com.houc.javafx.css;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FXCss extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new HBox(), 500, 400);
        scene.getStylesheets().add(getClass().getResource("/css/test.css").toString());
        /*for (String stylesheet : scene.getStylesheets()) {
            System.out.println(stylesheet);
        }*/
        primaryStage.setTitle("Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

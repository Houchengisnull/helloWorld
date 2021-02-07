package com.houc.javafx.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FXLabel extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label label = new Label();
        Label text_label = new Label("text label");

        Image image = new Image(getClass().getResourceAsStream("/icon/icon.jpg"), 50.0d, 50.0d, true, true);
        Label icon_label = new Label("icon label", new ImageView(image));

        VBox vBox = new VBox();
        vBox.getChildren().add(label);
        vBox.getChildren().add(text_label);
        vBox.getChildren().add(icon_label);

        Scene scene = new Scene(vBox, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

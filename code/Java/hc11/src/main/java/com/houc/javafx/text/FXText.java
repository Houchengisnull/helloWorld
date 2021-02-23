package com.houc.javafx.text;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FXText extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox pane = new HBox();
        Scene scene = new Scene(pane, 500, 400);
        scene.getStylesheets().add(getClass().getResource("/css/test.css").toString());

        Text text = new Text(10, 20, "Stroke and Fill");
        text.setId("fancytext");
        text.setFont(Font.font("Verdana", 20));
        text.setFill(Color.RED);

        pane.getChildren().add(text);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

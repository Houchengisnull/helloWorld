package com.houc.javafx.layout;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlignmentApplication extends Application {

    public static void main(String[] args) {
        log.debug("******* main ********");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 500, 100);
        primaryStage.setScene(scene);

        VBox left = new VBox();
        left.getChildren().add(new Button("left button"));
        borderPane.setLeft(left);

        VBox center = new VBox();
        center.getChildren().add(new Button("center button"));
        center.setAlignment(Pos.CENTER);
        borderPane.setCenter(center);

        VBox right = new VBox();
        right.setAlignment(Pos.BOTTOM_RIGHT);
        right.getChildren().add(new Button("right button"));
        borderPane.setRight(right);

        primaryStage.show();
    }
}

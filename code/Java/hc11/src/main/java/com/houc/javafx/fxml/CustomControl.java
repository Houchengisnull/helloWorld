package com.houc.javafx.fxml;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CustomControl extends VBox {

    @FXML
    private TextField textField;

    public CustomControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/custom_control.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getText() {
        return textProperty().get();
    }

    public void setText(String value) {
        textProperty().setValue(value);
    }

    public StringProperty textProperty(){
        return textField.textProperty();
    }

    @FXML
    protected void doSomething(){
        System.out.println("This buttom was clicked.");
    }
}

module hc {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires lombok;
    requires slf4j.api;
    requires transitive org.mapstruct.processor;

    // 粒度非常小
    exports com.houc.javafx.css;
    exports com.houc.javafx.layout;
    exports com.houc.javafx.ui;
    exports com.houc.javafx.text;
}
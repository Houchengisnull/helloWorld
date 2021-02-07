module hc {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires lombok;
    requires slf4j.api;
    requires transitive org.mapstruct.processor;

    exports com.houc.javafx.ui to javafx.graphics;
}
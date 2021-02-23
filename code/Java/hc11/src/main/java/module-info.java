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
    exports com.houc.javafx.fxml;

    // 指定相应模块 对其他模块开放
    opens com.houc.javafx.fxml to javafx.fxml;
}
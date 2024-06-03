module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;
    requires fontawesomefx;
    requires javafx.graphics;
    requires com.jfoenix;
    requires kernel;
    requires layout;
    requires io;

    opens com.example to javafx.fxml;

    exports com.example;
}

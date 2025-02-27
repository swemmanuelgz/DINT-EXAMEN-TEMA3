module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires layout;
    requires kernel;

    opens com.example.demo1 to javafx.fxml, javafx.base;
    opens com.example.demo1.modelo to javafx.fxml, javafx.base;
    exports com.example.demo1;
    exports com.example.demo1.controller;
    opens com.example.demo1.controller to javafx.base, javafx.fxml;
}
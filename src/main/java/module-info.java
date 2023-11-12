module com.example.dictionaryy {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires javafx.media;
    requires org.json;

    opens com.example.dictionaryy to javafx.fxml;
    exports com.example.dictionaryy;
    exports controller;
    opens controller to javafx.fxml;
}
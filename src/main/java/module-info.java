module com.example.dictionaryy {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires javafx.media;
    requires org.json;
    requires java.desktop;
    requires java.net.http;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpmime;

    opens com.example.dictionaryy to javafx.fxml;
    exports com.example.dictionaryy;
    exports controller;
    opens controller to javafx.fxml;
}
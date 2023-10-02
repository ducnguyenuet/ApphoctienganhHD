module com.example.dictionaryy {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dictionaryy to javafx.fxml;
    exports com.example.dictionaryy;
}
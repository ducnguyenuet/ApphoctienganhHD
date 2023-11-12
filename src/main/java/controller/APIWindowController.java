package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.example.dictionaryy.translatorFromAPI;

public class APIWindowController implements Initializable {
    private String langFrom = "en";
    private String langTo = "vi";
    private String text;
    @FXML
    private ComboBox<String> comboBox1;

    @FXML
    private ComboBox<String> comboBox2;

    ObservableList<String> languageList = FXCollections.observableArrayList("en", "vi", "fr");
    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    @FXML
    private Button button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox1.setItems(languageList);
        comboBox2.setItems(languageList);

//        comboBox1.setValue("anh");
//        comboBox2.setValue("anh");
    }

    public void comboBox1Changed() {
        langFrom = comboBox1.getValue();
    }

    public void comboBox2Changed() {
        langTo = comboBox2.getValue();
    }

    public void textField1Changed() {

    }

    public void buttonTranslateClickedOn() throws IOException {
        text = textField1.getText();
        String translatedText = translatorFromAPI.translate(langFrom, langTo, text);
        textField2.setText(translatedText);
    }



}

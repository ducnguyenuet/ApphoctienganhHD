package controller;

import com.example.dictionaryy.translatorFromAPI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.util.Pair;

public class APIWindowController implements Initializable {
    private String langFrom = "en";
    private String langTo = "vi";
    private String text;

    @FXML
    private ComboBox<String> comboBox1;

    @FXML
    private ComboBox<String> comboBox2;

    @FXML
    private TextArea textField1;

    @FXML
    private TextArea textField2;

    @FXML
    private Button button;

    private ObservableList<String> languageList;
    private Map<String, String> languageMap;
    private List<String> languageOrder;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        languageList = FXCollections.observableArrayList();
        languageMap = new HashMap<>();
        languageOrder = new ArrayList<>();

        populateLanguageMap();

        // Thêm tất cả key từ languageMap vào languageList theo thứ tự từ danh sách languageOrder
        languageList.addAll(languageOrder);

        comboBox1.setItems(languageList);
        comboBox2.setItems(languageList);
    }

    public void comboBox1Changed() {
        langFrom = languageMap.get(comboBox1.getValue());
    }

    public void comboBox2Changed() {
        langTo = languageMap.get(comboBox2.getValue());
    }

    public void textField1Changed() {

    }

    public void buttonTranslateClickedOn() throws IOException {
        text = textField1.getText();
        translatorFromAPI translator = new translatorFromAPI();
        if (langFrom.equals(langTo)) {
            textField2.setText(text);
        } else {
            String translatedText = translator.translate(langFrom, langTo, text);
            textField2.setText(translatedText);
        }
    }

    private void populateLanguageMap() {
        List<Pair<String, String>> languagePairs = new ArrayList<>();

        // Đường dẫn tới file chứa mã ngôn ngữ
        String filePath = "src/main/resources/data/language-codes.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String key = parts[1].trim();
                    String value = parts[0].trim();
                    languagePairs.add(new Pair<>(key, value));
                    languageOrder.add(key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ánh xạ từ tên ngôn ngữ đến mã ngôn ngữ
        for (Pair<String, String> pair : languagePairs) {
            languageMap.put(pair.getKey(), pair.getValue());
        }
    }
}

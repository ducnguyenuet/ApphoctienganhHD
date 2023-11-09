package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import com.example.dictionaryy.translatorFromAPI;
import java.io.IOException;

public class APIWindowController {
    @FXML
    private TextField apiTextField;

    @FXML
    private void translateAction(ActionEvent event) {
        String text = apiTextField.getText();
        try {
            String translatedText = translatorFromAPI.translate("en", "vi", text);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

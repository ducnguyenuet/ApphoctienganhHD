package controller;


import com.example.dictionaryy.AlertBox;
import javafx.scene.control.*;
import javafx.scene.media.*;
import com.example.dictionaryy.Database;
import com.example.dictionaryy.WordOfDB;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController implements Initializable {
    @FXML
    public BorderPane defaut;
    @FXML
    public BorderPane myDictPane;
    @FXML
    public TextField SearchType;
    @FXML
    public Button Search;
    @FXML
    public ListView ViewSearch;
    @FXML
    public Button ShowAW;
    @FXML
    public Button DeleButton;
    @FXML
    private WebView webView;
    @FXML
    private WebEngine engine;

    @Override
    public void initialize(URL url, ResourceBundle defaut) {
        webView = new WebView();
        engine = webView.getEngine();
        engine.load("https://translate.google.com/?hl=vi");

        SearchType.textProperty().addListener((observable, oldValue, newValue) -> {
            Searching(null);
        });

        ViewSearch.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            DeleButton.isDisable();
        });

    }

    @FXML
    public void md(MouseEvent mouseEvent) {
        defaut.setCenter(myDictPane);
    }

    @FXML
    public void wb(MouseEvent mouseEvent) {
        defaut.setCenter(webView);
    }

    @FXML
    public void game(MouseEvent mouseEvent) {}

    private void loadScene(String s) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
        defaut.setCenter(root);
    }
    @FXML
    void Searching(MouseEvent mouseEvent)
    {
        Database db = new Database();
        ArrayList<WordOfDB> List = db.getAllWord();
        db.close();
        String searchText = SearchType.getText();
        if (!searchText.isEmpty())
        {
            ArrayList<WordOfDB> viewList = new ArrayList<>();
            for (WordOfDB it:List)
            {
                if (it.getWord_target().startsWith(searchText))
                {
                    viewList.add(it);
                }
            }
            ViewSearch.setItems(FXCollections.observableArrayList(viewList));
        }
        else
        {
            ViewSearch.setItems(FXCollections.observableArrayList());
        }
    }


    public void ShowWords(ActionEvent event) {
        Database db = new Database();
        ArrayList<WordOfDB> List = db.getAllWord();
        db.close();
        ViewSearch.setItems(FXCollections.observableArrayList(List));
    }

    public void deleteType(ActionEvent event) {
        SearchType.setText("");
    }

    public void speaking(ActionEvent event) {
        WordOfDB word = (WordOfDB)ViewSearch.getSelectionModel().getSelectedItem();
        String audio = word.getAudio();
        String URL = "https:" + audio;
        Media sound = new Media(URL);

        MediaPlayer mp = new MediaPlayer(sound);
        mp.play();

    }

    public void DeleteWord(ActionEvent event) {
        if(ViewSearch.getSelectionModel().getSelectedItem()!= null)
        {
            try {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Confirm Action");
                alert.setContentText("Are you sure you want to perform this action?");

                ButtonType buttonTypeOK = new ButtonType("OK");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);


                alert.showAndWait().ifPresent(response -> {
                    if (response == buttonTypeOK) {
                        WordOfDB word = (WordOfDB) ViewSearch.getSelectionModel().getSelectedItem();
                        Database db = new Database();
                        db.deleteThisWord(word.getWord_target());
                        db.close();
                    } else {
                        System.out.println("Người dùng đã hủy bỏ.");

                    }
                });

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

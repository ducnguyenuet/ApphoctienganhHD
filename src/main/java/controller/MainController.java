package controller;

import javafx.scene.media.*;
import com.example.dictionaryy.Database;
import com.example.dictionaryy.WordOfDB;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
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
}

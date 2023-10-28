package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class MainController implements Initializable {
    @FXML
    public BorderPane defaut;
    @FXML
    public BorderPane myDictPane;
    private WebView webView;
    private WebEngine engine;

    @Override
    public void initialize(URL url, ResourceBundle defaut) {
        webView = new WebView();
        engine = webView.getEngine();
        engine.load("https://translate.google.com/?hl=vi");
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
    public void game(MouseEvent mouseEvent) {
        loadScene("game.fxml");
    }

    private void loadScene(String s) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
        defaut.setCenter(root);
    }


}

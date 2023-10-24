package com.example.dictionaryy;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    @FXML
    public BorderPane defaut;
    @FXML
    public BorderPane myDictPane;

    @Override
    public void initialize(URL url, ResourceBundle defaut)
    {

    }
    @FXML
    public void md(MouseEvent mouseEvent) {
        defaut.setCenter(myDictPane);
    }
    @FXML
    public void wb(MouseEvent mouseEvent) {
        loadScene("webDict.fxml");
    }

    @FXML
    public void game(MouseEvent mouseEvent) {
        loadScene("game.fxml");
    }
    private void loadScene(String s)
    {
        Parent root = null;
        try {
           root = FXMLLoader.load(getClass().getResource(s));
        } catch (IOException e){
            e.printStackTrace();
        }
        defaut.setCenter(root);
    }
}
package com.example.dictionaryy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 972, 593);
        stage.setTitle("MyApp");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {


//        WordOfDB word = new WordOfDB("a","aagoat","messi","sdd","sdsds","sdsds","sdds","dsd","dsd","ddd");
//        Database db = new Database();
//        db.addNewWord(word);
        launch();
    }
}
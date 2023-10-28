package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class webController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            WebView webView = new WebView();
            WebEngine engine = webView.getEngine();
            engine.load("https://translate.google.com/?hl=vi");

            Scene scene = new Scene(webView);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

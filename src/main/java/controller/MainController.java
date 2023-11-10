package controller;
import com.example.dictionaryy.*;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.media.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainController implements Initializable {
    @FXML
    private Button apiIcon;
    @FXML
    private Stage apiStage;
    @FXML
    public BorderPane defaut;
    @FXML
    public BorderPane myDictPane;
    @FXML
    public BorderPane myAPI;
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
    public TextArea wordOutPut;
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
        wordOutPut.textProperty().addListener((observable, oldValue, newValue) -> {
            Output(null);
        });
        //apiButton.setOnAction(event -> translateWithAPI(event));
//        apiIcon.setOnMouseClicked(event -> apiTranslation());
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
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE,null,e);
        }
        defaut.setCenter(root);
    }
    @FXML
    void Searching(InputMethodEvent event)
    {
        Database db = new Database();
        ArrayList<WordOfDB> List = db.getAllWord();
        db.close();
        String searchText = SearchType.getText().trim().toLowerCase();
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
            wordOutPut.setText("");
        }
    }

    @FXML
    public void api(MouseEvent mouseEvent) throws IOException {
        loadScene("/com/example/dictionaryy/api_window.fxml");
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
                WordOfDB word = (WordOfDB)ViewSearch.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(word.getWord_target());
                alert.setContentText("Are you sure to delete this word?");

                ButtonType buttonTypeOK = new ButtonType("OK");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);


                alert.showAndWait().ifPresent(response -> {
                    if (response == buttonTypeOK) {
                        WordOfDB word2 = (WordOfDB) ViewSearch.getSelectionModel().getSelectedItem();
                        Database db = new Database();
                        db.deleteThisWord(word2.getWord_target());
                        ArrayList<WordOfDB> List = db.getAllWord();
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
                        db.close();
                        wordOutPut.setText("");
                    } else {

                    }
                });

            } catch (Exception e){
                e.printStackTrace();
            }
        } else{
            Alert WarnAlert = new Alert(Alert.AlertType.WARNING);
            WarnAlert.setTitle("Warning");
            WarnAlert.setContentText("choose the word to be deleted first");
            ButtonType cancelButton = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
            WarnAlert.getButtonTypes().setAll(cancelButton);
            WarnAlert.showAndWait();
        }
    }


    public void Output(MouseEvent mouseEvent) {
        WordOfDB word = (WordOfDB)ViewSearch.getSelectionModel().getSelectedItem();
        //wordOutPut.setStyle("-fx-font-family: Dancing Script; -fx-font-size: 18; -fx-font-weight: normal; -fx-font-posture: regular");
        wordOutPut.setText(word.getInfo());

    }

    public void addStage(ActionEvent event) {
            try{
                Parent root = null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryy/edit.fxml"));
                root = loader.load();
                EditController editController = loader.getController();
                editController.setAdd(true);
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                Button closeStage = (Button) scene.lookup("#okBut");
                closeStage.setOnMouseClicked(e ->
                {
                    stage.close();
                    SearchType.clear();
                });
            } catch (IOException e)
            {
                e.printStackTrace();
            }


    }

    public void changeStage(ActionEvent event) {
        if(ViewSearch.getSelectionModel().getSelectedItem()!= null) {
            try {
                WordOfDB wordBefore = (WordOfDB) ViewSearch.getSelectionModel().getSelectedItem();
                Parent root = null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryy/edit.fxml"));
                root = loader.load();
                EditController editController = loader.getController();
                editController.setAdd(false);
                editController.setWordBeReplace(wordBefore);
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                Button closeStage = (Button) scene.lookup("#okBut");
                TextField wt = (TextField) scene.lookup("#wt");
                TextField we = (TextField) scene.lookup("#we");
                TextField ad = (TextField) scene.lookup("#ad");
                TextField tl = (TextField) scene.lookup("#tl");
                TextField tp = (TextField) scene.lookup("#tp");
                TextField pn = (TextField) scene.lookup("#pn");
                TextField sn = (TextField) scene.lookup("#sn");
                TextField ep = (TextField) scene.lookup("#ep");
                TextField df = (TextField) scene.lookup("#df");
                wt.setText(wordBefore.getWord_target());
                we.setText(wordBefore.getWord_explain());
                ad.setText(wordBefore.getAudio());
                tl.setText(wordBefore.getTargetLang());
                tp.setText(wordBefore.getType());
                pn.setText(wordBefore.getPronounce());
                sn.setText(wordBefore.getSynonyms());
                ep.setText(wordBefore.getExample());
                df.setText(wordBefore.getDefinition());
                closeStage.setOnMouseClicked(e ->
                {
                    stage.close();
                    SearchType.clear();
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void toAPI(InputMethodEvent inputMethodEvent) { loadScene("api_window.fxml");
    }
}

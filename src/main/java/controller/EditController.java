package controller;

import com.example.dictionaryy.Database;
import com.example.dictionaryy.WordOfDB;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditController {
    public Button okBut;
    private WordOfDB wordBeReplace;
    private boolean add;
    public TextField wt;
    public TextField we;
    public TextField ad;
    public TextField tl;
    public TextField tp;
    public TextField pn;
    public TextField sn;
    public TextField ep;
    public TextField df;

    public WordOfDB getWordBeReplace() {
        return wordBeReplace;
    }

    public void setWordBeReplace(WordOfDB wordBeReplace) {
        this.wordBeReplace = wordBeReplace;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public void updateNewWord(ActionEvent event) {
        WordOfDB wordEdit = new WordOfDB("aa",wt.getText(),we.getText(),ad.getText(),tl.getText(),tp.getText(),pn.getText(),sn.getText(),ep.getText(),df.getText());
        Database db = new Database();
        if(add == true)
        {
            db.addNewWord(wordEdit);
        }
        else {
            db.ChangeThisWord(wordBeReplace.getWord_target(),wordEdit);
        }
        db.close();
    }

    public void initialize(URL url, ResourceBundle defaut){
//        wt.textProperty().addListener((observable, oldValue, newValue) -> {
//            updateNewWord(null);
//        });
//        we.textProperty().addListener((observable, oldValue, newValue) -> {
//            updateNewWord(null);
//        });
//        ad.textProperty().addListener((observable, oldValue, newValue) -> {
//            updateNewWord(null);
//        });
//        tl.textProperty().addListener((observable, oldValue, newValue) -> {
//            updateNewWord(null);
//        });
//        tp.textProperty().addListener((observable, oldValue, newValue) -> {
//            updateNewWord(null);
//        });
//        pn.textProperty().addListener((observable, oldValue, newValue) -> {
//            updateNewWord(null);
//        });
//        sn.textProperty().addListener((observable, oldValue, newValue) -> {
//            updateNewWord(null);
//        });
//        ep.textProperty().addListener((observable, oldValue, newValue) -> {
//            updateNewWord(null);
//        });
//        df.textProperty().addListener((observable, oldValue, newValue) -> {
//            updateNewWord(null);
//        });

    }
}

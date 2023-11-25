package controller;

import com.example.dictionaryy.Database;
import com.example.dictionaryy.WordOfDB;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.random;

public class Quiz2Controller {

    private WordOfDB keyWord;

    public WordOfDB getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(WordOfDB keyWord) {
        this.keyWord = keyWord;
    }

    public Label exp;
    public Button ans1;
    public Button ans2;
    public Button ans3;
    public Button ans4;

    public void setQuiz()
    {
        String hideEx = keyWord.getExample().replace(keyWord.getWord_target(), "......");
        exp.setText(hideEx);
        Map<Integer,Button> myMap = new HashMap<>();
        myMap.put(0,ans1);
        myMap.put(1,ans2);
        myMap.put(2,ans3);
        myMap.put(3,ans4);
        Random random = new Random();
        int keyCloud = random.nextInt(3);
        for(Map.Entry<Integer,Button> entry: myMap.entrySet()){
            if(entry.getKey().equals(keyCloud)){
                entry.getValue().setText(keyWord.getWord_target());
            }
            else{
                Database db = new Database();
                ArrayList<WordOfDB> List = db.getAllWord();
                int k = List.size();
                db.close();
                Random rd = new Random();
                int x = rd.nextInt(k);
                entry.getValue().setText(List.get(x).getWord_target());
            }
        }
    }


    public void choose(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();
        if(buttonText.equals(keyWord.getWord_target()))
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Your answer is correct!");
            alert.setHeaderText(keyWord.getInfo());
            alert.setContentText("Good job ^^,keep going!!");
            alert.getDialogPane().setGraphic(new ImageView("checked.png"));
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Your answer is incorrect!");
            alert.setHeaderText(keyWord.getInfo());
            alert.setContentText("try your best next time!");
            alert.showAndWait();
        }
    }
}


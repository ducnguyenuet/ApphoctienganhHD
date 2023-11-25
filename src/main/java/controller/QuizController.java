package controller;

import com.example.dictionaryy.WordOfDB;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class QuizController {
    private WordOfDB keyWord;

    public WordOfDB getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(WordOfDB keyWord) {
        this.keyWord = keyWord;
    }

    public Button keySound;
    public Label df;
    public TextField input;
    public Button okBut;

    public void setQuiz()
    {
        df.setText(keyWord.getDefinition());
    }

    public void summit(ActionEvent event) {
        String text = input.getText();
        if(text.equals(keyWord.getWord_target())){
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

    public void speakk(ActionEvent event) {
        String audio = keyWord.getAudio();
        String URL = "https:" + audio;
        Media sound = new Media(URL);

        MediaPlayer mp = new MediaPlayer(sound);
        mp.play();
    }
}

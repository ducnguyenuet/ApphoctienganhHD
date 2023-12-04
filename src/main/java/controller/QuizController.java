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
    public Button clBut;
    private WordOfDB keyWord;

    private boolean checkRs;

    public boolean isCheckRs() {
        return checkRs;
    }

    public void setCheckRs(boolean checkRs) {
        this.checkRs = checkRs;
    }

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

    public void setQuiz() {
        df.setText(keyWord.getDefinition());
        df.setWrapText(true);
    }

    public void summit(ActionEvent event) {
        String text = input.getText();
        if (text.equals(keyWord.getWord_target())) {
            checkRs = true;

        } else {
            checkRs = false;
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

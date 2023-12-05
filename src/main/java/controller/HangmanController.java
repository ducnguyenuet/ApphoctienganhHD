package controller;

import com.example.dictionaryy.Database;
import com.example.dictionaryy.WordOfDB;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class HangmanController {
    public Label ytl;
    public Label ec;
    public Label key;
    public TextField input;
    public Label hintText;
    public ImageView balloon;

    private WordOfDB keyWord;

    private int turnLeft = 7;
    private String keyText ="";
    private String enter= "";

    Media mp1 = new Media(new File("src/main/resources/sound/correctHang.mp3").toURI().toString());
    Media mp2 = new Media(new File("src/main/resources/sound/incorrectHang.mp3").toURI().toString());
    Media mp3 = new Media(new File("src/main/resources/sound/winHang.mp3").toURI().toString());
    Media mp4 = new Media(new File("src/main/resources/sound/loseHang.mp3").toURI().toString());
    static Media themeSongH = new Media(new File("src/main/resources/sound/themeSongHang.mp3").toURI().toString());
    MediaPlayer mediaPlayer1 = new MediaPlayer(mp1);
    MediaPlayer mediaPlayer2 = new MediaPlayer(mp2);
    MediaPlayer mediaPlayer3 = new MediaPlayer(mp3);
    MediaPlayer mediaPlayer4 = new MediaPlayer(mp4);
    static MediaPlayer mediaPlayer = new MediaPlayer(themeSongH);

    public WordOfDB getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(WordOfDB keyWord) {
        this.keyWord = keyWord;
    }

    public void playBackgroundMusic() {
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }


    public void setKey()
    {
        playBackgroundMusic();
        Database db = new Database();
        ArrayList<WordOfDB> List = db.getAllWord();
        int k = List.size();
        db.close();
        Random rd = new Random();
        int x = rd.nextInt(k);

        while (List.get(x).getDefinition().isEmpty()&& List.get(x).getDefinition().equals("null")&&List.get(x).getWord_target().length()>14) {
            x = rd.nextInt(k);
        }

        keyWord = List.get(x);
        int tmp = keyWord.getWord_target().length();
        keyText = "";
        for (int i=0;i<tmp;i++)
        {
            keyText += "_";
        }
        key.setText(keyText);
        hintText.setText("");
        turnLeft = 7;
        ytl.setText(String.valueOf(turnLeft));
        enter = "";
        ec.setText(enter);
        Image image7 = new Image(new File("src/main/resources/image/7.jpg").toURI().toString());
        balloon.setImage(image7);
    }

    public void getHint(ActionEvent event) {
        hintText.setText(keyWord.getDefinition());
        hintText.setWrapText(true);
    }



    public void guest(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            String ip = input.getText();
            input.clear();
            if(ip.length()!=1){
                Alert WarnAlert = new Alert(Alert.AlertType.WARNING);
                WarnAlert.setTitle("Warning");
                WarnAlert.setContentText("invalid input! You can only guest 1 character!");
                WarnAlert.showAndWait();
            } else{
                if(keyWord.getWord_target().contains(ip))
                {
                    mediaPlayer1.stop();
                    mediaPlayer1.play();
                    int k = keyWord.getWord_target().length();
                    StringBuilder stringBuilder = new StringBuilder(keyText);
                    for(int i=0;i<k;i++)
                    {
                        if(keyWord.getWord_target().charAt(i)==ip.charAt(0))
                        {
                            stringBuilder.setCharAt(i,ip.charAt(0));
                            key.setText(stringBuilder.toString());
                            keyText = stringBuilder.toString();
                        }
                    }
                    if(!key.getText().contains("_"))
                    {   mediaPlayer.stop();
                        mediaPlayer3.stop();
                        mediaPlayer3.play();
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("You win!");
                        alert.setHeaderText(keyWord.getInfo());
                        alert.setContentText("Good job ^^");
                        alert.setOnCloseRequest(event -> {
                            setKey();
                        });
                        alert.show();

                    }
                } else {
                    mediaPlayer2.stop();
                    mediaPlayer2.play();
                    turnLeft--;
                    ytl.setText(String.valueOf(turnLeft));
                    setImage(turnLeft);
                    if(turnLeft == 0)
                    {
                        mediaPlayer.stop();
                        mediaPlayer4.stop();
                        mediaPlayer4.play();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Your lose");
                        alert.setHeaderText(keyWord.getInfo());
                        alert.setContentText("try your best next time!");
                        alert.setOnCloseRequest(event -> {
                            setKey();
                        });
                        alert.show();

                    }
                }
                enter = enter + ip +", ";
                ec.setText(enter);
            }
        }
    }

    public void setImage(int x){
        switch (x) {
            case 6:
                Image image6 = new Image(new File("src/main/resources/image/6.jpg").toURI().toString());
                balloon.setImage(image6);
                break;
            case 5:
                Image image5 = new Image(new File("src/main/resources/image/5.jpg").toURI().toString());
                balloon.setImage(image5);
                break;
            case 4:
                Image image4 = new Image(new File("src/main/resources/image/4.jpg").toURI().toString());
                balloon.setImage(image4);
                break;
            case 3:
                Image image3 = new Image(new File("src/main/resources/image/3.jpg").toURI().toString());
                balloon.setImage(image3);
                break;
            case 2:
                Image image2 = new Image(new File("src/main/resources/image/2.jpg").toURI().toString());
                balloon.setImage(image2);
                break;
            case 1:
                Image image1 = new Image(new File("src/main/resources/image/1.jpg").toURI().toString());
                balloon.setImage(image1);
                break;
            case 0:
                Image image0 = new Image(new File("src/main/resources/image/0.jpg").toURI().toString());
                balloon.setImage(image0);
                break;
        }
    }
}

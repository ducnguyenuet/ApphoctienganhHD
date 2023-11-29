package controller;

import com.example.dictionaryy.Database;
import com.example.dictionaryy.WordOfDB;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class WordChainController {
    public VBox chatBox;
    public TextField type;
    public Button send;
    public void play(MouseEvent mouseEvent){
        String text = type.getText();
        int s = text.length();
        char find = text.charAt(s-1);
        Label myTurn= new Label(text);
        myTurn.setStyle("-fx-background-color:blue;-fx-background-radius:20px;-fx-text-fill: White;-fx-padding: 6px;");
        HBox myWord = new HBox();
        myWord.setAlignment(Pos.BOTTOM_RIGHT);
        myWord.setPrefHeight(53);
        myWord.setPrefWidth(341);
        myWord.getChildren().add(myTurn);

        File file = new File("src/main/resources/image/faker.png");
        ImageView faker = new ImageView(file.toURI().toString());
        faker.setFitHeight(49);
        faker.setFitWidth(53);
        Label hisTurn = new Label("You win!");
        hisTurn.setStyle("-fx-background-color:white;-fx-background-radius:20px;-fx-padding: 6px;");
        HBox HisWord = new HBox();
        HisWord.setAlignment(Pos.BOTTOM_LEFT);
        HisWord.setPrefHeight(53);
        HisWord.setPrefWidth(341);
        Database db = new Database();
        ArrayList<WordOfDB> List = db.getAllWord();
        int k = List.size();
        db.close();
        ArrayList<WordOfDB> findList = new ArrayList<>();
        int index = binaryStartWith(List,find);
        if (index > 0)
        {
            for(int i= index;i<k;i++)
            {
                if(startsWith(List.get(i).getWord_target(),find))
                {
                    findList.add(List.get(i));
                }
                else break;
            }
            int fl = findList.size();
            Random rd = new Random();
            int x = rd.nextInt(fl);
            hisTurn.setText(findList.get(x).getWord_target());
        }
        HisWord.getChildren().addAll(faker,hisTurn);
        chatBox.getChildren().addAll(myWord,HisWord);
        type.clear();

    }

    private static int binaryStartWith(ArrayList<WordOfDB> list, char st) {
        int low = 0;
        int high = list.size() - 1;
        int appearFirst = -1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            WordOfDB midVal = list.get(mid);
            String wordTarget = midVal.getWord_target();
            if(wordTarget.isEmpty())
            {
                low = mid+1;
                continue;
            }
            char midChar = midVal.getWord_target().charAt(0); // Lấy ký tự đầu tiên

            if (midChar == st) {
                return mid;
            } else if (midChar < st) {
                low = mid + 1;
            } else if (midChar > st && startsWith(midVal.getWord_target(), st)) {
                high = mid - 1;
                appearFirst = mid;
            } else if (midChar > st && !startsWith(midVal.getWord_target(), st)) {
                high = mid - 1;
            }
        }
        return appearFirst;
    }

    private static boolean startsWith(String target, char prefix) {
        return target.length() > 0 && target.charAt(0) == prefix;
    }




}

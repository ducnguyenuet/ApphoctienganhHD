package com.example.dictionaryy;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class AlertBox {
    public static void display(String title,String message)
    {
//        Stage window = new Stage();
//
//        window.initModality(Modality.APPLICATION_MODAL);
//        window.setTitle(title);
//        window.setMinWidth(400);
//
//        Label label = new Label();
//        label.setText(message);
////        ButtonType okButton = new ButtonType("OK");
////        ButtonType cancelButton = new ButtonType("CANCEL");
//
//        HBox layout = new HBox(10);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(null);

        Label label = new Label(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setContent(label);

        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(okButton, cancelButton);

        // Hiển thị cảnh báo
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);


    }
}
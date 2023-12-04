package controller;

import com.example.dictionaryy.ImageTranslator;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

public class ImageTranslatorController implements Initializable {

    @FXML
    private TextArea resultTextField;

    @FXML
    private VBox imageContainer;

    @FXML
    private Button translateButton;

    @FXML
    private ComboBox<String> comboBox = new ComboBox<>();

    private String lang = "vi";
    private String imageURL;
    private ImageView imageView;
    private Dragboard db;
    private Image image;
    private List<String> imageUrls = new ArrayList<>();
    private boolean hasImage = false;

    private APIWindowController apiWindowController = new APIWindowController();
    private ObservableList<String> languageList = apiWindowController.getLanguageList();
    private Map<String, String> languageMap = apiWindowController.getLanguageMap();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Thực hiện việc khởi tạo các thành phần trong giao diện người dùng
        comboBox.setItems(languageList);
        resultTextField.setText("Kết quả dịch được hiển thị ở đây: \n");
    }

    @FXML
    private void onDragOver(DragEvent event) {
        // Phương thức xử lý khi kéo thả
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    @FXML
    private void onDragDropped(DragEvent event) {
        // Phương thức xử lý khi thả
        db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            if (hasImage) {
                // Nếu đã có ảnh, thay thế ảnh cũ bằng ảnh mới
                imageContainer.getChildren().clear();
                imageUrls.clear();
            }
            //imageUrls.clear(); // Xóa các URL ảnh trước đó
            for (File file : db.getFiles()) {
                String filePath = file.getAbsolutePath();
                image = new Image("file:" + filePath);
                imageView = new ImageView(image);
                imageView.setFitWidth(300);
                imageView.setPreserveRatio(true);
                imageContainer.getChildren().add(imageView);
                // Thực hiện các thao tác dịch ảnh và hiển thị kết quả ở resultTextField ở đây
                imageURL = ImageTranslator.uploadImageToImgur(filePath);
                imageUrls.add(imageURL);
            }
            hasImage = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void buttonClickedOn() {
        // Phương thức xử lý khi nhấn nút
        if (comboBox.getValue() == null) {
            lang = "en"; // Đặt mặc định là "en" khi không có ngôn ngữ được chọn
        } else {
            lang = languageMap.get(comboBox.getValue());
        }
        String res = ImageTranslator.translateImage(imageURL, lang);
        resultTextField.setText("Ảnh được dịch sang ngôn ngữ " + comboBox.getValue() + " là: \n" + res);
    }

    @FXML
    private void comboBoxChanged() {
        lang = languageMap.get(comboBox.getValue());
    }
}

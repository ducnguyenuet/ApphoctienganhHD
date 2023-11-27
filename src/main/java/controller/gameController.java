package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;


public class gameController extends Application {
    Label scorelabel = new Label();
    Label l2 = new Label();
    Button bton = new Button();
    int score, index;
    Stage primaryStage = new Stage();
    Ellipse bird = new Ellipse();
    Rectangle ground = new Rectangle();
    ArrayList<Rectangle> barriers = new ArrayList<>();
    int W = 892, H = 592;
    int ticks;
    double yToaDo;
    Group root = new Group();
    boolean gameOver;
    Label l = new Label();
    LinearGradient ld;
    IntegerStringConverter str = new IntegerStringConverter();
    Timeline tm = new Timeline();
    Scene scene = new Scene(root);
    Image cloud;
    ImageView cloudv;
    int X,Y;
    DropShadow ds1;
    int remainingLives = 3;
    ImageView heart1, heart2, heart3;
    AnchorPane windowGame = new AnchorPane();

    File fontFile = new File("src/main/resources/font/newFont01.ttf");
    String fontPath = fontFile.toURI().toString();
    Font customFont = Font.loadFont(fontPath, 20); // 20 là kích thước font

    Media themeSong = new Media(new File("src/main/resources/sound/themeSong.mp3").toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(themeSong);

    private void initializeHearts(int remainingHearts) {
        if (remainingHearts >= 0) {
            heart1 = new ImageView(new Image(new File("src/main/resources/image/heart00.png").toURI().toString()));
            heart2 = new ImageView(new Image(new File("src/main/resources/image/heart00.png").toURI().toString()));
            heart3 = new ImageView(new Image(new File("src/main/resources/image/heart00.png").toURI().toString()));

            // Set fit width and height for hearts
            double heartSize = 40;
            heart1.setFitWidth(heartSize);
            heart1.setFitHeight(heartSize);
            heart2.setFitWidth(heartSize);
            heart2.setFitHeight(heartSize);
            heart3.setFitWidth(heartSize);
            heart3.setFitHeight(heartSize);

            // Set positions for hearts
            double heartsSpacing = 5;
            heart1.setLayoutX(0);
            heart1.setLayoutY(15);
            heart2.setLayoutX(heartSize + heartsSpacing);
            heart2.setLayoutY(15);
            heart3.setLayoutX((heartSize + heartsSpacing) * 2);
            heart3.setLayoutY(15);

            // Add hearts to the root Group
            root.getChildren().addAll(heart1, heart2, heart3);

            if (remainingHearts == 2) {
                root.getChildren().remove(heart3);
            } else if (remainingHearts == 1) {
                root.getChildren().remove(heart3);
                root.getChildren().remove(heart2);
            } else if (remainingHearts == 0) {
                root.getChildren().remove(heart3);
                root.getChildren().remove(heart2);
                root.getChildren().remove(heart1);
            }
        }
    }

    void addBarrier() {
        int space = 300;
        int width = 100;
        int height = 50 + (int)(Math.random() * 200);

        barriers.add(new Rectangle(W + width + (barriers.size() * 200),H - height - 95,width,height));
        barriers.add(new Rectangle(W + width + (barriers.size() - 1) * 200,0,width,H - height - space));
    }

    boolean gameOverSoundPlayed = false;

    void Collision() {
        for (Rectangle barrier : barriers) {
            if ((barrier.getBoundsInParent().intersects(bird.getBoundsInParent()))) {
                gameOver = true;
                if (bird.getCenterX() <= barrier.getX()) {
                    bird.setCenterX(barrier.getX() - 2 * bird.getRadiusX() + 10);
                } else {
                    if (barrier.getY() != 0) {
                        bird.setCenterY(barrier.getY() - 2 * bird.getRadiusY());
                    } else if (bird.getCenterY() > barrier.getHeight()) {
                        bird.setCenterY(barrier.getHeight());
                    }
                }
            }
        }

        if(bird.getCenterY() > H - 95 || bird.getCenterY() < 0) {
            gameOver = true;
        }

        if(gameOver) {

            bird.setCenterY(H - 95 - bird.getRadiusY());
            if (remainingLives > 0) {
                l.setText("Try Again ?\n   Score: " + str.toString(score / 2));
                l.setFont(Font.font(customFont.getFamily(), 50));
                l.setLayoutX(primaryStage.getWidth() / 2 - 95);
                l.setLayoutY(primaryStage.getHeight() / 2 - 50);
                l.setTextFill(Color.RED);
            } else {
                Image overImage = new Image(new File("src/main/resources/image/bgOver.png").toURI().toString());
                ImageView overBackground = new ImageView(overImage);
                overBackground.setFitWidth(W);
                overBackground.setFitHeight(H);
                root.getChildren().addAll(overBackground);

                Label lo = new Label();
                lo.setText("Game Over\n   Score: " + str.toString(score / 2));
                lo.setFont(Font.font(customFont.getFamily(), 42));
                lo.setLayoutX(primaryStage.getWidth() / 2 - 95);
                lo.setLayoutY(primaryStage.getHeight() / 2 - 125);
                lo.setTextFill(Color.RED);
                root.getChildren().addAll(lo);
        }}
    }

    void Jump() {
        if(!gameOver) {
            if(yToaDo > 0) {
                yToaDo = 0;
            }
            yToaDo = yToaDo - 8;
        }
    }


    void reStart() {
        initializeGame();
        displayStartMessage();
        handleKeyPressToStart();
    }

    void initializeGame() {
        bird.setCenterX(W / 2 - 10);
        bird.setCenterY(H / 2 - 10);
        gameOver = false;
        yToaDo = 0;
        score = 0;
        scorelabel.setText("  Score: " + str.toString(score));
        root.getChildren().remove(bton);
        root.getChildren().removeAll(barriers);
        barriers.clear();
        generateInitialBarriers();
    }

    void generateInitialBarriers() {
        int i = 0;
        while(i < 100) {
            addBarrier();
            i++;
        }
    }

    void displayStartMessage() {
        String message = "Press UP or SPACE to start.";
        configureStartMessage(message);
        showStartMessageOnScreen();
    }

    void configureStartMessage(String message) {
        l2.setText(message);
        l2.setFont(customFont);
        l2.setTextFill(Color.BLACK);
    }

    void showStartMessageOnScreen() {
        Text text = new Text(l2.getText());
        text.setFont(l2.getFont());
        double textWidth = text.getLayoutBounds().getWidth();
        double textHeight = text.getLayoutBounds().getHeight();

        double centerX = (primaryStage.getWidth() - textWidth) / 2;
        double centerY = (primaryStage.getHeight() - textHeight) / 2 + 50;
        l2.setLayoutX(centerX);
        l2.setLayoutY(centerY);

        tm.pause();
        root.getChildren().add(l2);
    }

    void handleKeyPressToStart() {
        scene.setOnKeyReleased(k -> {
            String code = k.getCode().toString();
            if(code.equals("UP") || code.equals("SPACE")) {
                startGame();
            }
        });
    }

    void startGame() {
        root.getChildren().addAll(barriers);
        root.getChildren().remove(l2);
        tm.play();
    }


    @Override
    public void start(Stage window) {

        primaryStage = window;
        primaryStage.setTitle("Flappy Bird");
        primaryStage.setHeight(H);
        primaryStage.setWidth(W);
        primaryStage.setResizable(false);

        root = new Group();

        // Load hình ảnh nền
        Image backgroundImage = new Image(new File("src/main/resources/image/bg.jpg").toURI().toString());

        // Tạo hai ImageView từ cùng một hình ảnh nền
        ImageView background1 = new ImageView(backgroundImage);
        ImageView background2 = new ImageView(backgroundImage);

        // Đặt kích thước của ImageView
        background1.setFitWidth(W);
        background1.setFitHeight(H);
        background2.setFitWidth(W);
        background2.setFitHeight(H);

        // Đặt vị trí của ImageView 2 ở bên phải của ImageView 1
        background2.setTranslateX(W);

        root.getChildren().addAll(background1, background2);

        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Đặt vòng lặp vô hạn
        mediaPlayer.play();

        // Tạo hiệu ứng di chuyển liền mạch
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(background1.translateXProperty(), 0), new KeyValue(background2.translateXProperty(), W)),
                new KeyFrame(Duration.seconds(10), new KeyValue(background1.translateXProperty(), -W), new KeyValue(background2.translateXProperty(), 0))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();



        initializeHearts(3);

        //Shadow for bird
//        DropShadow ds1 = new DropShadow();
//        ds1.setOffsetY(4.0f);
//        ds1.setOffsetX(4.0f);
//        ds1.setOffsetX(4.0f);
//        ds1.setColor(Color.BLACK);


        //shadow for button
        DropShadow ds2 = new DropShadow();

        ds2.setOffsetY(4.0f);
        ds2.setOffsetX(4.0f);
        ds2.setColor(Color.DARKGREY);

        File file = new File("src/main/resources/image/birdNew.png");
        Image img = new Image(file.toURI().toString());
        ImagePattern ip = new ImagePattern(img);

        img = new Image(file.toURI().toString());
        ip = new ImagePattern(img);


        bird = new Ellipse();
        bird.setFill(ip);
        bird.setRadiusX((img.getWidth() / 2) + 2);
        bird.setRadiusY((img.getHeight() / 2) + 2);
        bird.setCenterX(W / 2 - 10);
        bird.setCenterY(H / 2 - 10);
        bird.setEffect(ds1);

        index = 0;
        yToaDo = 0;

        str = new IntegerStringConverter();

        l = new Label();
        l2 = new Label();
        scorelabel = new Label();

        scorelabel.setFont(Font.font(customFont.getFamily(), 30));

        ld = new LinearGradient(0.0, 0.0, 1.0, 0.0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.web("#2ECC71")),
                new Stop(0.9, Color.web("#27AE60")),
                new Stop(1.0, Color.BLACK));

        barriers = new ArrayList<Rectangle>();

        ground = new Rectangle(0,H - 95,W,120);
        ground.setFill(Color.TRANSPARENT);

        tm = new Timeline();
        tm.setCycleCount(Animation.INDEFINITE);

        gameOver = false;

        bton = new Button();
        bton.setText("Restart");
        bton.setTranslateX(400);
        bton.setTranslateY(380);
        bton.setPrefSize(100,50);
        bton.setTextFill(Color.BLUE);
        bton.setFont(customFont);
        bton.setEffect(ds2);

        KeyFrame kf1 = new KeyFrame(Duration.millis(20), e -> {

            ticks++;

            if(ticks % 2 == 0 && yToaDo < 15) {
                yToaDo = yToaDo + 1.4;
            }

            X = X - 3;
            bird.setCenterY(bird.getCenterY() + yToaDo);

            scene.setOnKeyReleased(k -> {
                String code = k.getCode().toString();
                if(code=="UP" || code.equals("SPACE")) {
                    Jump();
                }
            });

            Collision();

            if (gameOver) {
                Media overSound = new Media(new File("src/main/resources/sound/over.mp3").toURI().toString());
                MediaPlayer overMediaPlayer = new MediaPlayer(overSound);
                overMediaPlayer.play();

                if (!(root.getChildren().contains(l))) {
                    root.getChildren().addAll(l, bton);
                }
                bton.setOnMouseClicked(k -> {
                    root.getChildren().remove(l);
                    root.getChildren().remove(heart3);
                    root.getChildren().remove(heart2);
                    root.getChildren().remove(heart1);
                    remainingLives--;
                    initializeHearts(remainingLives);
                    reStart();
                });
            }
        });

        KeyFrame kf2 = new KeyFrame(Duration.millis(20), e -> {

            for(int i = 0; i < barriers.size(); ++i) {

                Rectangle barrier = barriers.get(i);
                barrier.setFill(ld);
                barrier.setEffect(ds1);
                barrier.setX((barrier.getX() - 5));

                if(barrier.getY() == 0 && bird.getCenterX() + bird.getRadiusX() > barrier.getX() + barrier.getWidth() / 2 - 5
                        && bird.getCenterX() + bird.getRadiusX() < barrier.getX() + barrier.getWidth() / 2 + 5) {

                    Media passSound = new Media(new File("src/main/resources/sound/pass.mp3").toURI().toString());
                    MediaPlayer passMediaPlayer = new MediaPlayer(passSound);
                    passMediaPlayer.play();

                    score++;
                    scorelabel.setText("  Score: " + str.toString(score / 2));
                    scorelabel.setTextFill(Color.DARKBLUE);
                }
            }

            for(int i = 0; i < barriers.size(); ++i) {

                Rectangle barrier = barriers.get(i);

                if((barrier.getX() + barrier.getWidth()) < 0) {
                    barriers.remove(i);
                }
            }
        });

        tm.getKeyFrames().addAll(kf1,kf2);

        //root.getChildren().add(cloudv);
        root.getChildren().addAll(scorelabel);
        root.getChildren().add(ground);
        root.getChildren().add(bird);

        scene = new Scene(root);
        scene.setFill(Color.LIGHTBLUE);
        reStart();

        primaryStage.setScene(scene);
        primaryStage.setHeight(H);
        primaryStage.setWidth(W);
        primaryStage.show();
    }

    @FXML
    void startButtonClicked(ActionEvent event) {
        start(primaryStage); // Gọi đến phương thức start() với primaryStage hoặc stage cần thiết

    }

    public static void main(String[] args) {
        launch(args);
    }
}
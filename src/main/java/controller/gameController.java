package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
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
    int W = 800, H = 700;
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

    private void initializeHearts(int remainingHearts) {
        if (remainingHearts >= 0) {
            heart1 = new ImageView(new Image(new File("src/main/resources/image/heart.png").toURI().toString()));
            heart2 = new ImageView(new Image(new File("src/main/resources/image/heart.png").toURI().toString()));
            heart3 = new ImageView(new Image(new File("src/main/resources/image/heart.png").toURI().toString()));

            // Set fit width and height for hearts
            double heartSize = 20;
            heart1.setFitWidth(heartSize);
            heart1.setFitHeight(heartSize);
            heart2.setFitWidth(heartSize);
            heart2.setFitHeight(heartSize);
            heart3.setFitWidth(heartSize);
            heart3.setFitHeight(heartSize);

            // Set positions for hearts
            double heartsSpacing = 5;
            heart1.setLayoutX(10);
            heart1.setLayoutY(30);
            heart2.setLayoutX(10 + heartSize + heartsSpacing);
            heart2.setLayoutY(30);
            heart3.setLayoutX(10 + (heartSize + heartsSpacing) * 2);
            heart3.setLayoutY(30);

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
        int height = 50 + (int)(Math.random()*300);

        barriers.add(new Rectangle(W + width + (barriers.size() * 200),H - height - 120,width,height));
        barriers.add(new Rectangle(W + width + (barriers.size() - 1) * 200,0,width,H - height - space));
    }
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

        if(bird.getCenterY() > H - 120 || bird.getCenterY() < 0) {
            gameOver = true;
        }

        if(gameOver) {
            //remainingLives--;
            //initializeHearts(2);

            bird.setCenterY(H - 120 - bird.getRadiusY());
            l.setText("Game Over\n   Score: " + str.toString(score / 2));
            l.setFont(new Font("Arial",50));
            l.setLayoutX(primaryStage.getWidth() / 2 - 130);
            l.setLayoutY(primaryStage.getHeight() / 2 - 50);
            l.setTextFill(Color.RED);
        }
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
        scorelabel.setText("Score: " + str.toString(score));
        root.getChildren().remove(bton);
        root.getChildren().removeAll(barriers);
        barriers.clear();
        generateInitialBarriers();
    }

    void generateInitialBarriers() {
        int i = 0;
        while(i < 20) {
            addBarrier();
            i++;
        }
    }

    void displayStartMessage() {
        String message = "Bấm phím lên hoặc SPACE để bắt đầu";
        configureStartMessage(message);
        showStartMessageOnScreen();
    }

    void configureStartMessage(String message) {
        l2.setText(message);
        l2.setFont(new Font("Arial", 20));
        l2.setTextFill(Color.DARKBLUE);
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

        initializeHearts(3);

        //Shadow for bird
        DropShadow ds1 = new DropShadow();
        ds1.setOffsetY(4.0f);
        ds1.setOffsetX(4.0f);
        ds1.setColor(Color.BLACK);
<<<<<<< HEAD

        //shadow for button
        DropShadow ds2 = new DropShadow();
<<<<<<< Updated upstream
        ds1.setOffsetY(4.0f);
        ds1.setOffsetX(4.0f);
        ds1.setColor(Color.BLACK);
=======
        ds2.setOffsetY(4.0f);
        ds2.setOffsetX(4.0f);
        ds2.setColor(Color.DARKGREY);

        File file = new File("src/main/resources/image/birdFrame0.png");
        File file2 = new File("src/main/resources/image/cloud.png");
        Image img = new Image(file.toURI().toString());
        ImagePattern ip = new ImagePattern(img);
        cloud = new Image(file2.toURI().toString());
        cloudv = new ImageView(cloud);
        X = W + (int)cloud.getWidth();
=======
>>>>>>> Stashed changes
        File file = new File("src/main/resources/image/birdFrame0.png");
        Image img = new Image(file.toURI().toString());
        //Image img=new Image("image/birdFrame0.png");
        ImagePattern ip=new ImagePattern(img);

        File file2 = new File("src/main/resources/image/cloud.png");
        cloud=new Image(file2.toURI().toString());
        cloudv=new ImageView(cloud);
        X=W+(int)cloud.getWidth();
>>>>>>> 9b709a508e0b81c3c95e5e7cdb220164577e2508
        cloudv.setX(X);
        Y = 10 + (int)(Math.random() * 100);
        cloudv.setY(Y);

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

        scorelabel.setFont(new Font("Arial",20));

        ld = new LinearGradient(0.0, 0.0, 1.0, 0.0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.GREY),
                new Stop(1.0, Color.BLACK));

        barriers = new ArrayList<Rectangle>();

        ground = new Rectangle(0,H - 120,W,120);
        ground.setFill(Color.GREEN);

        tm = new Timeline();
        tm.setCycleCount(Animation.INDEFINITE);

        gameOver = false;

        bton = new Button();
        bton.setText("Restart");
        bton.setTranslateX(350);
        bton.setTranslateY(600);
        bton.setPrefSize(100,50);
        bton.setTextFill(Color.BLUE);
        bton.setFont(new Font("Arial",20));
        bton.setEffect(ds2);

        KeyFrame kf1 = new KeyFrame(Duration.millis(20), e -> {

            ticks++;

            if(ticks % 2 == 0 && yToaDo < 15) {
                yToaDo = yToaDo + 1.4;
            }

            X = X - 3;
            cloudv.setX(X);

            if(X < ( - (int)cloud.getWidth())) {
                X = W + (int)cloud.getWidth();
                cloudv.setX(X);
                Y = 10 + (int)(Math.random() * 100);
                cloudv.setY(Y);
            }

            //double y = bird.getCenterY() + yToaDo;
            bird.setCenterY(bird.getCenterY() + yToaDo);

            scene.setOnKeyReleased(k -> {
                String code = k.getCode().toString();
                if(code=="UP" || code.equals("SPACE")) {
                    Jump();
                }
            });

            Collision();

            if (gameOver) {
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
                    score++;
                    scorelabel.setText("Score: " + str.toString(score / 2));
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

        root.getChildren().add(cloudv);
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
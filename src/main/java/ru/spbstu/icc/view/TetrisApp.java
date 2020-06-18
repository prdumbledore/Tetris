package ru.spbstu.icc.view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import ru.spbstu.icc.controller.Controller;
import ru.spbstu.icc.model.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TetrisApp extends Application {
    public static final int MOVE = Controller.MOVE;
    public static final int SIZE = Controller.MOVE;
    public static final int XMAX = Controller.XMAX;
    public static final int YMAX = Controller.YMAX;
    public static int score = 0;
    public static int lines = 0;
    private int level = 1;
    private int top = 0;
    private double time = 0;
    private double x = 0.020;
    List<Text> tableText = new ArrayList<>();
    private final String[] keys = new String[20];
    private final String[] values = new String[20];
    private boolean game = true;
    private final Map<Integer, Pair<Integer, Double>> levelBylines = new TreeMap<>() {
        {
            put(20, new Pair<>(2, 0.03));
            put(50, new Pair<>(3, 0.04));
            put(100, new Pair<>(4, 0.05));
            put(200, new Pair<>(5, 0.06));
            put(400, new Pair<>(6, 0.07));
            put(800, new Pair<>(7, 0.08));
            put(1500, new Pair<>(8, 0.09));
            put(4000, new Pair<>(9, 0.095));
            put(8000, new Pair<>(10, 0.1));
        }
    };

    public static Stage window;
    public static String userName;

    private Model object;
    private Model nextObj = Model.setRectangle();

    public static Pane groupStartPage;
    public static Pane group;

    static {
        try {
            groupStartPage = FXMLLoader.load(TetrisApp.class.getResource("/controller/StartPage.fxml"));
            group = FXMLLoader.load(TetrisApp.class.getResource("/view/TetrisApp.fxml"));
        } catch (IOException ignored) {
        }
    }

    public static Scene startPage = new Scene(groupStartPage, 600, 450);
    public static Scene scene = new Scene(group, XMAX + 200, YMAX);

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        window = stage;
        window.setScene(startPage);
        window.setResizable(false);
        window.sizeToScene();
        window.setTitle("Tetris");
        window.getIcons().add(new Image(String.valueOf(getClass().getResource("/design/logo.png"))));
        window.show();
    }

    Button but = new Button("Finish the game");
    Text scoreText = new Text("Score: ");
    Text line = new Text("Lines: ");
    Text levelText = new Text("Level: ");
    Text highScore = new Text("High Score:");

    private final AnimationTimer timers = new AnimationTimer() {
        @Override
        public void handle(long now) {
            time += x;
            if (time >= 0.5) {
                if (object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0 || object.d.getY() == 0)
                    top++;
                else
                    top = 0;

                if (top == 2) {
                    Text game_over = new Text("GAME OVER");
                    game_over.setX(10);
                    game_over.setY(YMAX >> 1);
                    game_over.setFill(Color.RED);
                    game_over.setStyle("-fx-font: 70 arial;");
                    changeRecords();
                    group.getChildren().addAll(game_over);
                    game = false;
                }

                if (top == 3) {
                    stopGame();
                }

                if (game) {
                    moveDown(object);
                    changeTableRecords();
                    scoreText.setText("Score: " + score);
                    line.setText("Lines: " + lines);
                    for (Map.Entry<Integer, Pair<Integer, Double>> entry : levelBylines.entrySet()) {
                        if (lines == entry.getKey()) {
                            level = entry.getValue().getKey();
                            x = entry.getValue().getValue();
                        }
                    }
                    levelText.setText("Level: " + level);
                }
                time = 0;
            }
        }
    };

    public void startGame(Stage stage) {
        game = true;

        but.setPrefHeight(30);
        but.setPrefWidth(135);
        but.setStyle("-fx-font-size: 14;");
        but.setStyle("-fx-font-style: OCR A Extended");
        but.setStyle("-fx-background-color: white");
        but.setLayoutX(XMAX + 32.5);
        but.setLayoutY(725);

        scoreText.setStyle("-fx-font-size: 14;");
        scoreText.setStyle("-fx-font-family: 'OCR A Extended'");
        scoreText.setY(100);
        scoreText.setX(XMAX + 10);
        scoreText.setFill(Color.SALMON);

        line.setStyle("-fx-font-size: 14;");
        line.setStyle("-fx-font-family: 'OCR A Extended'");
        line.setY(150);
        line.setX(XMAX + 10);
        line.setFill(Color.YELLOW);

        levelText.setStyle("-fx-font-size: 14;");
        levelText.setStyle("-fx-font-family: 'OCR A Extended'");
        levelText.setY(200);
        levelText.setX(XMAX + 10);
        levelText.setFill(Color.AQUA);

        highScore.setStyle("-fx-font-size: 14;");
        highScore.setStyle("-fx-font-family: 'OCR A Extended'");
        highScore.setY(250);
        highScore.setX(XMAX + 10);
        highScore.setFill(Color.LIME);
        group.getChildren().addAll(scoreText, line, levelText, highScore, but);

        Model model = nextObj;
        group.getChildren().addAll(model.a, model.b, model.c, model.d);
        moveOnKeyPress(model);
        group.requestFocus();
        object = model;
        nextObj = Model.setRectangle();

        setTableRecords();

        stage.setOnCloseRequest(event -> {
            changeRecords();
            Platform.exit();
            System.exit(0);
        });

        timers.start();

        but.setOnAction(event -> stopGame());
    }

    private void stopGame() {
        timers.stop();
        changeRecords();
        group.getChildren().clear();
        try {
            group = FXMLLoader.load(TetrisApp.class.getResource("/view/TetrisApp.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene = new Scene(group, XMAX + 200, YMAX);
        Controller.grid = new int[XMAX / SIZE][YMAX / SIZE];
        Controller.rects = new Rectangle[XMAX / SIZE][YMAX / SIZE];

        score = 0;
        lines = 0;
        level = 1;
        top = 0;
        time = 0;
        x = 0.020;
        window.close();
        window.setScene(startPage);
        window.setResizable(false);
        window.sizeToScene();
        window.setTitle("Tetris");
        window.getIcons().add(new Image(String.valueOf(getClass().getResource("/design/logo.png"))));
        window.show();
    }

    private void moveOnKeyPress(Model model) {
        scene.setOnKeyPressed(key -> {
            switch (key.getCode()) {
                case RIGHT:
                    Controller.moveRight(model);
                    break;
                case DOWN:
                    moveDown(model);
                    break;
                case LEFT:
                    Controller.moveLeft(model);
                    break;
                case UP:
                    if (!model.color.equals(Color.GOLD)) Controller.turn(model, model.direction, model.distance);
                    break;
            }
        });
    }

    private void moveDown(Model model) {
        if (model.a.getY() == YMAX - SIZE || model.b.getY() == YMAX - SIZE || model.c.getY() == YMAX - SIZE
                || model.d.getY() == YMAX - SIZE || Controller.moveRect(model.a) || Controller.moveRect(model.b) || Controller.moveRect(model.c) || Controller.moveRect(model.d)) {
            fillGrid(model.a);
            fillGrid(model.b);
            fillGrid(model.c);
            fillGrid(model.d);

            fillRect(model.a);
            fillRect(model.b);
            fillRect(model.c);
            fillRect(model.d);

            Controller.removeRows(group);

            Model model1 = nextObj;
            nextObj = Model.setRectangle();
            object = model1;
            group.getChildren().addAll(model1.a, model1.b, model1.c, model1.d);
            moveOnKeyPress(model1);
        }

        if (Controller.checkDownAndNull(model.a) && Controller.checkDownAndNull(model.b)
                && Controller.checkDownAndNull(model.c) && Controller.checkDownAndNull(model.d)) {
            model.a.setY(model.a.getY() + MOVE);
            model.b.setY(model.b.getY() + MOVE);
            model.c.setY(model.c.getY() + MOVE);
            model.d.setY(model.d.getY() + MOVE);
        }
    }

    private void fillGrid(Rectangle a) {
        Controller.grid[(int) (a.getX() / SIZE)][(int) a.getY() / SIZE] = 1;
    }

    private void fillRect(Rectangle a) {
        Controller.rects[(int) (a.getX() / SIZE)][(int) a.getY() / SIZE] = a;
    }

    private void setTableRecords() {

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/records.txt"))) {
            String line;
            int i = 20;
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(" ");
                keys[i - 1] = arr[0];
                values[i - 1] = arr[1];
                tableText.add(new Text(i + ". " + arr[0] + " - " + arr[1]));
                tableText.get(20 - i).setStyle("-fx-font-size: 14;");
                tableText.get(20 - i).setStyle("-fx-font-family: 'OCR A Extended'");
                tableText.get(20 - i).setY(250 + i * 20);
                tableText.get(20 - i).setX(XMAX + 10);
                tableText.get(20 - i).setFill(Color.WHITE);
                i -= 1;
            }
            group.getChildren().addAll(tableText);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int i = 19;

    private void changeTableRecords() {
        while (i >= -1) {
            if (i != -1 && Integer.parseInt(values[i]) < score) {
                if (i < 19 && i >= 0) {
                    tableText.get(18 - i).setText((i + 2) + ". " + keys[i] + " - " + values[i]);
                    keys[i + 1] = keys[i];
                    values[i + 1] = values[i];
                }
                tableText.get(19 - i).setText((i + 1) + ". " + userName + " - " + score);
                keys[i] = userName;
                values[i] = String.valueOf(score);
                i--;
            } else if (i < 19 && Integer.parseInt(values[i + 1]) < score) {
                tableText.get(18 - i).setText((i + 2) + ". " + userName + " - " + score);
                values[i + 1] = String.valueOf(score);
                break;
            } else break;
        }
    }

    private void changeRecords() {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 19; i >= 0; i--) {
                if (!keys[i].equals("-")) {
                    sb.append(keys[i]).append(" ").append(values[i]).append(System.lineSeparator());
                } else {
                    sb.append("-").append(" ").append("0").append(System.lineSeparator());
                }
            }
            FileWriter writer = new FileWriter("src/main/resources/records.txt");
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scene getScene() {
        return scene;
    }

}
package ru.spbstu.icc.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.spbstu.icc.controller.Controller;
import ru.spbstu.icc.model.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class TetrisApp extends Application {
    public static final int MOVE = 40;
    public static final int SIZE = 40;
    public static int XMAX = SIZE * 12;
    public static int YMAX = SIZE * 24;
    public static int[][] Grid = new int[XMAX / SIZE][YMAX / SIZE];
    public static Pane group;

    static {
        try {
            group = FXMLLoader.load(TetrisApp.class.getResource("../../../../resources/TetrisApp.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scene scene = new Scene(group, XMAX + 200, YMAX);

    private static Model object;

    public static int score = 0;
    private static int lines = 0;
    private static int level = 1;
    private static int top = 0;
    private static boolean game = true;
    private static Model nextObj = Controller.setRectangle();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Text scoreText = new Text("Score: ");
        scoreText.setStyle("-fx-font: 14 arial;");
        scoreText.setY(100);
        scoreText.setX(XMAX + 10);
        Text line = new Text("Lines: ");
        line.setStyle("-fx-font: 14 arial;");
        line.setY(150);
        line.setX(XMAX + 10);
        Text levelText = new Text("Level: ");
        levelText.setStyle("-fx-font: 14 arial;");
        levelText.setY(200);
        levelText.setX(XMAX + 10);
        Text highScore = new Text("High Score:");
        highScore.setStyle("-fx-font: 14 arial;");
        highScore.setY(300);
        highScore.setX(XMAX + 10);
        group.getChildren().addAll(scoreText, line, levelText, highScore);

        Model model = nextObj;
        group.getChildren().addAll(model.a, model.b, model.c, model.d);
        moveOnKeyPress(model);
        object = model;
        nextObj = Controller.setRectangle();
        stage.setScene(scene);
        stage.setTitle("Tetris");
        stage.show();
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
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
                        group.getChildren().addAll(game_over);
                        game = false;
                    }

                    if (game) {
                        MoveDown(object);
                        scoreText.setText("Score: " + score);
                        line.setText("Lines: " + lines);
                        switch (lines) {
                            case 1:
                                level = 2;
                                break;
                            case 25:
                                level = 3;
                                break;
                            case 50:
                                level = 4;
                                break;
                            case 100:
                                level = 5;
                                break;
                            case 200:
                                level = 6;
                                break;
                        }
                        levelText.setText("Level: " + level);
                    }

                });
            }
        };
        timer.schedule(timerTask, 0, 300 * level);

    }

    private void moveOnKeyPress(Model model) {
        scene.setOnKeyPressed(key -> {
            switch (key.getCode()) {
                case RIGHT:
                    Controller.moveRight(model);
                    break;
                case DOWN:
                    MoveDown(model);
                    score++;
                    break;
                case LEFT:
                    Controller.moveLeft(model);
                    break;
                case SPACE:
                    if (!model.getName().equals("o")) Controller.turn(model, model.direction, model.distance);
                    break;
            }
        });
    }

    private void RemoveRows(Pane pane) {
        ArrayList<Node> rects = new ArrayList<>();
        ArrayList<Integer> line = new ArrayList<>();
        ArrayList<Node> newRects = new ArrayList<>();
        int full = 0;
        for (int i = 0; i < Grid[0].length; i++) {
            for (int[] ints : Grid) {
                if (ints[i] == 1)
                    full++;
            }
            if (full == Grid.length)
                line.add(i);
            full = 0;
        }
        if (line.size() > 0)
            do {
                for (Node node : pane.getChildren()) {
                    if (node instanceof Rectangle)
                        rects.add(node);
                }
                score += 50;
                lines++;

                for (Node node : rects) {
                    Rectangle a = (Rectangle) node;
                    if (a.getY() == line.get(0) * SIZE) {
                        Grid[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                        pane.getChildren().remove(node);
                    } else
                        newRects.add(node);
                }

                for (Node node : newRects) {
                    Rectangle a = (Rectangle) node;
                    if (a.getY() < line.get(0) * SIZE) {
                        Grid[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                        a.setY(a.getY() + SIZE);
                    }
                }
                line.remove(0);
                rects.clear();
                newRects.clear();
                for (Node node : pane.getChildren()) {
                    if (node instanceof Rectangle)
                        rects.add(node);
                }
                for (Node node : rects) {
                    Rectangle a = (Rectangle) node;
                    try {
                        Grid[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 1;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
                rects.clear();
            } while (line.size() > 0);
    }

    private void MoveDown(Model model) {
        if (model.a.getY() == YMAX - SIZE || model.b.getY() == YMAX - SIZE || model.c.getY() == YMAX - SIZE
                || model.d.getY() == YMAX - SIZE || moveRect(model.a) || moveRect(model.b) || moveRect(model.c) || moveRect(model.d)) {
            Grid[(int) (model.a.getX() / SIZE)][(int) model.a.getY() / SIZE] = 1;
            Grid[(int) model.b.getX() / SIZE][(int) model.b.getY() / SIZE] = 1;
            Grid[(int) model.c.getX() / SIZE][(int) model.c.getY() / SIZE] = 1;
            Grid[(int) model.d.getX() / SIZE][(int) model.d.getY() / SIZE] = 1;
            RemoveRows(group);

            Model model1 = nextObj;
            nextObj = Controller.setRectangle();
            object = model1;
            group.getChildren().addAll(model1.a, model1.b, model1.c, model1.d);
            moveOnKeyPress(model1);
        }

        if (model.a.getY() + MOVE < YMAX && model.b.getY() + MOVE < YMAX && model.c.getY() + MOVE < YMAX
                && model.d.getY() + MOVE < YMAX) {
            int movea = Grid[(int) model.a.getX() / SIZE][((int) model.a.getY() / SIZE) + 1];
            int moveb = Grid[(int) model.b.getX() / SIZE][((int) model.b.getY() / SIZE) + 1];
            int movec = Grid[(int) model.c.getX() / SIZE][((int) model.c.getY() / SIZE) + 1];
            int moved = Grid[(int) model.d.getX() / SIZE][((int) model.d.getY() / SIZE) + 1];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                model.a.setY(model.a.getY() + MOVE);
                model.b.setY(model.b.getY() + MOVE);
                model.c.setY(model.c.getY() + MOVE);
                model.d.setY(model.d.getY() + MOVE);
            }
        }
    }

    private boolean moveRect(Rectangle rectangle) {
        return (Grid[(int) rectangle.getX() / SIZE][((int) rectangle.getY() / SIZE) + 1] == 1);
    }


}




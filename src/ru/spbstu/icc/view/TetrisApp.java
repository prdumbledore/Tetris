package ru.spbstu.icc.view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.spbstu.icc.controller.Controller;
import ru.spbstu.icc.model.Direction;
import ru.spbstu.icc.model.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TetrisApp extends Application {
    public static final int MOVE = 40;
    public static final int SIZE = 40;
    public static int XMAX = SIZE * 12;
    public static int YMAX = SIZE * 20;
    public static int[][] Grid = new int[XMAX / SIZE][YMAX / SIZE];
    public static int score = 0;
    private static int lines = 0;
    private static int level = 1;
    private static int top = 0;
    public double time = 0;
    public double x = 0.020;
    List<Text> tableText = new ArrayList<>();
    private final String[] keys = new String[20];
    private final String[] values = new String[20];
    public static String userName;
    private static boolean game = true;

    private static Model object;
    private static Model nextObj = Controller.setRectangle();


    public static Stage window;

    public static Pane group;
    public static Pane groupStartPage;

    static {
        try {
            groupStartPage = FXMLLoader.load(TetrisApp.class.getResource("../../../../resources/StartPage.fxml"));
            group = FXMLLoader.load(TetrisApp.class.getResource("../../../../resources/TetrisApp.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scene startPage = new Scene(groupStartPage, 600 , 450);
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
        window.getIcons().add(new Image("resources/logo.png"));
        window.show();
    }

    Button but = new Button("Finish the game");
    Text scoreText = new Text("Score: ");
    Text line = new Text("Lines: ");
    Text levelText = new Text("Level: ");
    Text highScore = new Text("High Score:");

    public AnimationTimer timers = new AnimationTimer() {
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
                    changeRecordsTXT();
                    group.getChildren().addAll(game_over);
                    game = false;
                }

                if (top == 3) {
                    timers.stop();
                    changeRecordsTXT();
                    endGame();
                    window.close();
                    window.setScene(startPage);
                    window.setResizable(false);
                    window.sizeToScene();
                    window.setTitle("Tetris");
                    window.getIcons().add(new Image("resources/logo.png"));
                    window.show();
                }

                if (game) {
                    MoveDown(object);
                    changeTableRecords();
                    scoreText.setText("Score: " + score);
                    line.setText("Lines: " + lines);
                    switch (lines) {
                        case 20:
                            level = 2;
                            x = 0.030;
                            break;
                        case 50:
                            level = 3;
                            x = 0.040;
                            break;
                        case 100:
                            level = 4;
                            x = 0.050;
                            break;
                        case 200:
                            level = 5;
                            x = 0.060;
                            break;
                        case 400:
                            level = 6;
                            x = 0.070;
                            break;
                        case 800:
                            level = 7;
                            x = 0.080;
                            break;
                        case 1500:
                            level = 8;
                            x = 0.090;
                            break;
                        case 4000:
                            level = 9;
                            x = 0.095;
                            break;
                        case 8000:
                            level = 10;
                            x = 0.1;
                            break;
                    }
                    levelText.setText("Level: " + level);
                }
                time = 0;

            }
        }
    };

    public void thread(Stage stage) {
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
        object = model;
        nextObj = Controller.setRectangle();

        setTableRecords();

        stage.setOnCloseRequest(event -> {
            changeRecordsTXT();
            Platform.exit();
            System.exit(0);
        });

        timers.start();

        but.setOnAction(event -> {
            timers.stop();
            changeRecordsTXT();
            endGame();
            window.close();
            window.setScene(startPage);
            window.setResizable(false);
            window.sizeToScene();
            window.setTitle("Tetris");
            window.getIcons().add(new Image("resources/logo.png"));
            window.show();
        });
    }

    private void moveOnKeyPress(Model model) {
        scene.setOnKeyPressed(key -> {
            switch (key.getCode()) {
                case RIGHT:
                    moveRight(model);
                    break;
                case DOWN:
                    MoveDown(model);
                    break;
                case LEFT:
                    moveLeft(model);
                    break;
                case UP:
                    if (!model.getName().equals("o")) turn(model, model.direction, model.distance);
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

    public static void moveRight(Model model) {
        if (model.a.getX() + MOVE <= XMAX - SIZE && model.b.getX() + MOVE <= XMAX - SIZE   //проверка на границе справа
                && model.c.getX() + MOVE <= XMAX - SIZE && model.d.getX() + MOVE <= XMAX - SIZE) {
            int movea = Grid[((int) model.a.getX() / SIZE) + 1][((int) model.a.getY() / SIZE)];
            int moveb = Grid[((int) model.b.getX() / SIZE) + 1][((int) model.b.getY() / SIZE)];
            int movec = Grid[((int) model.c.getX() / SIZE) + 1][((int) model.c.getY() / SIZE)];
            int moved = Grid[((int) model.d.getX() / SIZE) + 1][((int) model.d.getY() / SIZE)];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                model.a.setX(model.a.getX() + MOVE);
                model.b.setX(model.b.getX() + MOVE);
                model.c.setX(model.c.getX() + MOVE);
                model.d.setX(model.d.getX() + MOVE);
            }
        }
    }

    public static void moveLeft(Model model) {
        if (model.a.getX() - MOVE >= 0 && model.b.getX() - MOVE >= 0   //проверка на границе слева
                && model.c.getX() - MOVE >= 0 && model.d.getX() - MOVE >= 0) {
            int movea = Grid[((int) model.a.getX() / SIZE) - 1][((int) model.a.getY() / SIZE)];
            int moveb = Grid[((int) model.b.getX() / SIZE) - 1][((int) model.b.getY() / SIZE)];
            int movec = Grid[((int) model.c.getX() / SIZE) - 1][((int) model.c.getY() / SIZE)];
            int moved = Grid[((int) model.d.getX() / SIZE) - 1][((int) model.d.getY() / SIZE)];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                model.a.setX(model.a.getX() - MOVE);
                model.b.setX(model.b.getX() - MOVE);
                model.c.setX(model.c.getX() - MOVE);
                model.d.setX(model.d.getX() - MOVE);
            }
        }
    }

    private boolean moveRect(Rectangle rectangle) {
        return (Grid[(int) rectangle.getX() / SIZE][((int) rectangle.getY() / SIZE) + 1] == 1);
    }

    public static void turn(Model model, ArrayList<ArrayList<Direction>> direction, ArrayList<Integer> distance) {
        int dx = 0, dy = 0;
        Rectangle a = new Rectangle();
        Rectangle b = new Rectangle();
        Rectangle c = new Rectangle();
        Rectangle d = new Rectangle();
        ArrayList<ArrayList<Direction>> directions = new ArrayList<>();
        Collections.addAll(directions, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        for (int i = 0; i < 4; i++) {

            for (Direction direct : direction.get(i)) {
                Direction directionNext1 = direct.next();
                Direction directionNext2 = directionNext1.next();
                dx += distance.get(i) * directionNext1.x + distance.get(i) * directionNext2.x;
                dy += distance.get(i) * directionNext1.y + distance.get(i) * directionNext2.y;
                directions.get(i).add(directionNext1);
            }

            switch (i) {
                case 0:
                    a.setX(model.a.getX() + dx * MOVE);
                    a.setY(model.a.getY() + dy * MOVE);
                    break;
                case 1:
                    b.setX(model.b.getX() + dx * MOVE);
                    b.setY(model.b.getY() + dy * MOVE);
                    break;
                case 2:
                    c.setX(model.c.getX() + dx * MOVE);
                    c.setY(model.c.getY() + dy * MOVE);
                    break;
                case 3:
                    d.setX(model.d.getX() + dx * MOVE);
                    d.setY(model.d.getY() + dy * MOVE);
                    break;
            }

            dx = 0;
            dy = 0;

        }

        if (checkingPossibilityTurn(a) && checkingPossibilityTurn(b) &&
                checkingPossibilityTurn(c) && checkingPossibilityTurn(d)) {
            model.a.setX(a.getX());
            model.a.setY(a.getY());
            model.b.setX(b.getX());
            model.b.setY(b.getY());
            model.c.setX(c.getX());
            model.c.setY(c.getY());
            model.d.setX(d.getX());
            model.d.setY(d.getY());
            model.direction = directions;
        }
    }


    private static boolean checkingPossibilityTurn(Rectangle rect) {
        boolean xbool;
        boolean ybool;

        xbool = rect.getX() <= XMAX - SIZE && rect.getX() >= 0;
        ybool = rect.getY() > 0 && rect.getY() < YMAX;

        return xbool && ybool && Grid[(int) rect.getX() / SIZE][(int) rect.getY() / SIZE] == 0;
    }

    private void setTableRecords() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/resources/records.txt"))) {
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

    private void changeRecordsTXT() {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 19; i >= 0; i--) {
                if (!keys[i].equals("-")) {
                    sb.append(keys[i]).append(" ").append(values[i]).append(System.lineSeparator());
                } else {
                    sb.append("-").append(" ").append("0").append(System.lineSeparator());
                }
            }
            FileWriter writer = new FileWriter("src/resources/records.txt");
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void endGame() {
        group.getChildren().clear();
        try {
            group = FXMLLoader.load(TetrisApp.class.getResource("../../../../resources/TetrisApp.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene = new Scene(group, XMAX + 200, YMAX);
        Grid = new int[XMAX / SIZE][YMAX / SIZE];

        score = 0;
        lines = 0;
        level = 1;
        top = 0;
        time = 0;
        x = 0.020;
    }
}
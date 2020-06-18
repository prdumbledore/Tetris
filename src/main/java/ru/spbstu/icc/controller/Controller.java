package ru.spbstu.icc.controller;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import ru.spbstu.icc.model.Direction;
import ru.spbstu.icc.model.Model;
import ru.spbstu.icc.view.TetrisApp;

import java.util.ArrayList;
import java.util.Collections;

public class Controller {
    public static final int MOVE = 40;
    public static final int SIZE = 40;
    public static final int XMAX = SIZE * 12;
    public static final int YMAX = SIZE * 20;
    public static int[][] grid = new int[XMAX / SIZE][YMAX / SIZE];
    public static Rectangle[][] rects = new Rectangle[XMAX / SIZE][YMAX / SIZE];

    public static void moveRight(Model model) {
        if (model.a.getX() + MOVE <= XMAX - SIZE && model.b.getX() + MOVE <= XMAX - SIZE
                && model.c.getX() + MOVE <= XMAX - SIZE && model.d.getX() + MOVE <= XMAX - SIZE) {
            if (checkNullForMoveRight(model.a) == 0
                    && checkNullForMoveRight(model.b) == 0
                    && checkNullForMoveRight(model.c) == 0
                    && checkNullForMoveRight(model.d) == 0) {
                model.a.setX(model.a.getX() + MOVE);
                model.b.setX(model.b.getX() + MOVE);
                model.c.setX(model.c.getX() + MOVE);
                model.d.setX(model.d.getX() + MOVE);
            }
        }
    }

    private static int checkNullForMoveRight(Rectangle a) {
        return grid[((int) a.getX() / SIZE) + 1][(int) a.getY() / SIZE];
    }

    public static void moveLeft(Model model) {
        if (model.a.getX() - MOVE >= 0 && model.b.getX() - MOVE >= 0
                && model.c.getX() - MOVE >= 0 && model.d.getX() - MOVE >= 0) {
            if (checkNullForMoveLeft(model.a) == 0 &&
                    checkNullForMoveLeft(model.b) == 0 &&
                    checkNullForMoveLeft(model.c) == 0 &&
                    checkNullForMoveLeft(model.d) == 0) {
                model.a.setX(model.a.getX() - MOVE);
                model.b.setX(model.b.getX() - MOVE);
                model.c.setX(model.c.getX() - MOVE);
                model.d.setX(model.d.getX() - MOVE);
            }
        }
    }

    private static int checkNullForMoveLeft(Rectangle a) {
        return grid[((int) a.getX() / SIZE) - 1][(int) a.getY() / SIZE];
    }

    public static boolean moveRect(Rectangle rectangle) {
        return (grid[(int) rectangle.getX() / SIZE][((int) rectangle.getY() / SIZE) + 1] == 1);
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

        return xbool && ybool && grid[(int) rect.getX() / SIZE][(int) rect.getY() / SIZE] == 0;
    }

    public static void removeRows(Pane pane) {
        ArrayList<Integer> line = new ArrayList<>();
        int full = 0;
        for (int i = 0; i < grid[0].length; i++) {
            for (int[] ints : grid) {
                if (ints[i] == 1)
                    full++;
            }
            if (full == grid.length)
                line.add(i);
            full = 0;
        }
        if (line.size() > 0)
            do {
                TetrisApp.score += 50;
                TetrisApp.lines++;

                int y = line.get(0);

                for (int i = 0; i < XMAX / SIZE; i++) {
                    Rectangle rect = rects[i][y];
                    rects[i][y] = null;
                    grid[i][y] = 0;
                    pane.getChildren().remove(rect);
                }

                for (int i = 0; i < XMAX / SIZE; i++) {
                    for (int j = y - 1; j >= 0; j--) {
                        Rectangle rect = rects[i][j];
                        if (rect != null) {
                            pane.getChildren().remove(rect);
                            pane.getChildren().remove(rects[i][j + 1]);

                            rect.setY(rect.getY() + SIZE);

                            grid[i][j + 1] = 1;
                            rects[i][j + 1] = rect;
                            grid[i][j] = 0;
                            rects[i][j] = null;

                            pane.getChildren().add(rects[i][j + 1]);
                        }
                    }
                }



                line.remove(0);

            } while (line.size() > 0);
    }

    public static boolean checkDownAndNull(Rectangle a) {
        return a.getY() + MOVE < YMAX && grid[(int) a.getX() / SIZE][((int) a.getY() / SIZE) + 1] == 0;
    }

    public static boolean checkUpAndNull(Rectangle a) {
        return a.getY() - MOVE > 0 && grid[(int) a.getX() / SIZE][((int) a.getY() / SIZE) - 1] == 0;
    }

}

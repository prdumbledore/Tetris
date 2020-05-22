package ru.spbstu.icc.controller;

import javafx.scene.shape.Rectangle;
import ru.spbstu.icc.model.Direction;
import ru.spbstu.icc.model.Model;
import ru.spbstu.icc.view.TetrisApp;

import java.util.ArrayList;
import java.util.Collections;

public class Controller {
    public static final int MOVE = TetrisApp.MOVE;
    public static final int SIZE = TetrisApp.SIZE;
    private static final int YMAX = TetrisApp.YMAX;
    public static int XMAX = TetrisApp.XMAX;
    public static int MIDDLE = XMAX / 2;
    public static int[][] Grid = TetrisApp.Grid;

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

    public static Model setRectangle() {
        int block = (int) (Math.random() * 105);
        String name;
        ArrayList<Integer> distance = new ArrayList<>();
        ArrayList<ArrayList<Direction>> direction = new ArrayList<>();
        Rectangle a = new Rectangle(SIZE - 1, SIZE - 1);
        Rectangle b = new Rectangle(SIZE - 1, SIZE - 1);
        Rectangle c = new Rectangle(SIZE - 1, SIZE - 1);
        Rectangle d = new Rectangle(SIZE - 1, SIZE - 1);
        if (block < 15) {
            a.setX(MIDDLE - SIZE);
            b.setX(MIDDLE);
            c.setX(MIDDLE + SIZE);
            d.setX(MIDDLE + 2 * SIZE);
            name = "i";
            Collections.addAll(distance, 1, 0, 1, 2);
            Collections.addAll(direction, new ArrayList<>(Collections.singleton(Direction.LEFT)),
                    new ArrayList<>(Collections.singleton(Direction.DOWN)),
                    new ArrayList<>(Collections.singleton(Direction.RIGHT)),
                    new ArrayList<>(Collections.singleton(Direction.RIGHT)));
        } else if (block < 30) {
            a.setX(MIDDLE - SIZE);
            b.setX(MIDDLE);
            c.setX(MIDDLE - SIZE);
            c.setY(SIZE);
            d.setX(MIDDLE);
            d.setY(SIZE);
            name = "o";
        } else if (block < 45) {
            a.setX(MIDDLE + SIZE);
            b.setX(MIDDLE);
            c.setX(MIDDLE - SIZE);
            d.setX(MIDDLE);
            d.setY(SIZE);
            name = "t";
            Collections.addAll(distance, 1, 0, 1, 1);
            Collections.addAll(direction, new ArrayList<>(Collections.singleton(Direction.RIGHT)),
                    new ArrayList<>(Collections.singleton(Direction.DOWN)),
                    new ArrayList<>(Collections.singleton(Direction.LEFT)),
                    new ArrayList<>(Collections.singleton(Direction.DOWN)));
        } else if (block < 60) {
            a.setX(MIDDLE);
            b.setX(MIDDLE);
            b.setY(SIZE);
            c.setX(MIDDLE + SIZE);
            c.setY(SIZE);
            d.setX(MIDDLE + 2 * SIZE);
            d.setY(SIZE);
            name = "j";
            Collections.addAll(distance, 1, 0, 1, 2);
            Collections.addAll(direction, new ArrayList<>(Collections.singleton(Direction.UP)),
                    new ArrayList<>(Collections.singleton(Direction.DOWN)),
                    new ArrayList<>(Collections.singleton(Direction.RIGHT)),
                    new ArrayList<>(Collections.singleton(Direction.RIGHT)));
        } else if (block < 75) {
            a.setX(MIDDLE);
            a.setY(SIZE);
            b.setX(MIDDLE);
            c.setX(MIDDLE + SIZE);
            d.setX(MIDDLE + 2 * SIZE);
            name = "l";
            Collections.addAll(distance, 1, 0, 1, 2);
            Collections.addAll(direction, new ArrayList<>(Collections.singleton(Direction.DOWN)),
                    new ArrayList<>(Collections.singleton(Direction.DOWN)),
                    new ArrayList<>(Collections.singleton(Direction.RIGHT)),
                    new ArrayList<>(Collections.singleton(Direction.RIGHT)));
        } else if (block < 90) {
            a.setX(MIDDLE + SIZE);
            b.setX(MIDDLE);
            c.setX(MIDDLE);
            c.setY(SIZE);
            d.setX(MIDDLE - SIZE);
            d.setY(SIZE);
            name = "s";
            Collections.addAll(distance, 1, 0, 1, 1);
            Collections.addAll(direction, new ArrayList<>(Collections.singleton(Direction.RIGHT)),
                    new ArrayList<>(Collections.singleton(Direction.DOWN)),
                    new ArrayList<>(Collections.singleton(Direction.DOWN)));
            ArrayList<Direction> i = new ArrayList<>();
            i.add(Direction.DOWN);
            i.add(Direction.LEFT);
            direction.add(i);
        } else {
            a.setX(MIDDLE - SIZE);
            b.setX(MIDDLE);
            c.setX(MIDDLE);
            c.setY(SIZE);
            d.setX(MIDDLE + SIZE);
            d.setY(SIZE);
            name = "z";
            Collections.addAll(distance, 1, 0, 1, 1);
            Collections.addAll(direction, new ArrayList<>(Collections.singleton(Direction.LEFT)),
                    new ArrayList<>(Collections.singleton(Direction.DOWN)),
                    new ArrayList<>(Collections.singleton(Direction.DOWN)));
            ArrayList<Direction> i = new ArrayList<>();
            i.add(Direction.DOWN);
            i.add(Direction.RIGHT);
            direction.add(i);
        }
        return new Model(a, b, c, d, name, distance, direction);
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
}

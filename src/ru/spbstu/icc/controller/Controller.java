package ru.spbstu.icc.controller;

import javafx.scene.shape.Rectangle;
import ru.spbstu.icc.model.Direction;
import ru.spbstu.icc.model.Model;
import ru.spbstu.icc.view.TetrisApp;

import java.util.ArrayList;
import java.util.Collections;

public class Controller {
    public static final int SIZE = TetrisApp.SIZE;
    public static int XMAX = TetrisApp.XMAX;
    public static int MIDDLE = XMAX / 2;

    public static Model setRectangle() {
        int block = (int) (Math.random() * 7);
        String name;
        ArrayList<Integer> distance = new ArrayList<>();
        ArrayList<ArrayList<Direction>> direction = new ArrayList<>();
        Rectangle a = new Rectangle(SIZE - 1, SIZE - 1);
        a.setArcHeight(10);
        a.setArcWidth(10);
        Rectangle b = new Rectangle(SIZE - 1, SIZE - 1);
        b.setArcHeight(10);
        b.setArcWidth(10);
        Rectangle c = new Rectangle(SIZE - 1, SIZE - 1);
        c.setArcHeight(10);
        c.setArcWidth(10);
        Rectangle d = new Rectangle(SIZE - 1, SIZE - 1);
        d.setArcHeight(10);
        d.setArcWidth(10);
        if (block == 1) {
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
        } else if (block == 2) {
            a.setX(MIDDLE - SIZE);
            b.setX(MIDDLE);
            c.setX(MIDDLE - SIZE);
            c.setY(SIZE);
            d.setX(MIDDLE);
            d.setY(SIZE);
            name = "o";
        } else if (block == 3) {
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
        } else if (block == 4) {
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
        } else if (block == 5) {
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
        } else if (block == 6) {
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

}

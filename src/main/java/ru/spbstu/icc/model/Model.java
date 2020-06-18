package ru.spbstu.icc.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ru.spbstu.icc.controller.Controller;

import java.util.ArrayList;
import java.util.Collections;

public class Model {
    public Rectangle a;
    public Rectangle b;
    public Rectangle c;
    public Rectangle d;
    public ArrayList<Integer> distance;
    public ArrayList<ArrayList<Direction>> direction;
    public Color color;
    private static final int SIZE = Controller.SIZE;
    private static final int XMAX = Controller.XMAX;
    private static final int MIDDLE = XMAX / 2;

    public Model(Rectangle a, Rectangle b, Rectangle c, Rectangle d, Color color, ArrayList<Integer> distance, ArrayList<ArrayList<Direction>> direction) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.distance = distance;
        this.direction = direction;
        this.color = color;
        this.a.setFill(color);
        this.b.setFill(color);
        this.c.setFill(color);
        this.d.setFill(color);
    }

    public static Model setRectangle() {
        int block = (int) (Math.random() * 7);
        ArrayList<Integer> distance = new ArrayList<>();
        ArrayList<ArrayList<Direction>> direction = new ArrayList<>();
        Color color;
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
            color = Color.LIGHTSEAGREEN;
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
            color = Color.GOLD;
        } else if (block == 3) {
            a.setX(MIDDLE + SIZE);
            b.setX(MIDDLE);
            c.setX(MIDDLE - SIZE);
            d.setX(MIDDLE);
            d.setY(SIZE);
            color = Color.PURPLE;
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
            color = Color.BLUE;
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
            color = Color.ORANGE;
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
            color = Color.GREEN;
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
            color = Color.RED;
            b.setX(MIDDLE);
            c.setX(MIDDLE);
            c.setY(SIZE);
            d.setX(MIDDLE + SIZE);
            d.setY(SIZE);
            Collections.addAll(distance, 1, 0, 1, 1);
            Collections.addAll(direction, new ArrayList<>(Collections.singleton(Direction.LEFT)),
                    new ArrayList<>(Collections.singleton(Direction.DOWN)),
                    new ArrayList<>(Collections.singleton(Direction.DOWN)));
            ArrayList<Direction> i = new ArrayList<>();
            i.add(Direction.DOWN);
            i.add(Direction.RIGHT);
            direction.add(i);
        }
        return new Model(a, b, c, d, color, distance, direction);
    }

}

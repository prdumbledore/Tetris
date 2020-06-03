package ru.spbstu.icc.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Model {
    public Rectangle a;
    public Rectangle b;
    public Rectangle c;
    public Rectangle d;
    public ArrayList<Integer> distance;
    public ArrayList<ArrayList<Direction>> direction;
    Color color;
    private final String name;

    public Model(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name, ArrayList<Integer> distance, ArrayList<ArrayList<Direction>> direction) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.name = name;
        this.distance = distance;
        this.direction = direction;
        switch (name) {
            case "i":
                color = Color.LIGHTSEAGREEN;
                break;
            case "o":
                color = Color.GOLD;
                break;
            case "t":
                color = Color.PURPLE;
                break;
            case "j":
                color = Color.BLUE;
                break;
            case "l":
                color = Color.ORANGE;
                break;
            case "s":
                color = Color.GREEN;
                break;
            case "z":
                color = Color.RED;
                break;
        }
        this.a.setFill(color);
        this.b.setFill(color);
        this.c.setFill(color);
        this.d.setFill(color);
    }

    public String getName() {
        return name;
    }

}

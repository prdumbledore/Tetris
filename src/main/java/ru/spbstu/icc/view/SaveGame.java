package ru.spbstu.icc.view;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.*;

public class SaveGame implements Serializable {
    public String username;
    public String score;
    private static final String[] keys = TetrisApp.keys;
    private static final String[] values = TetrisApp.values;

    public SaveGame(String username, String score) {
        this.username = username;
        this.score = score;
    }

    public static void saveGame() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream("src/main/resources/records.xml"));
            objectOutputStream.flush();
            for (int i = 19; i >= 0; i--) {
                SaveGame saveGame;
                if (!keys[i].equals("-")) {
                    saveGame = new SaveGame(keys[i], values[i]);
                } else {
                    saveGame = new SaveGame("-", "0");
                }
                objectOutputStream.writeObject(saveGame);
            }
            objectOutputStream.close();
        } catch (IOException ignored) {
        }

    }

    public static void setTableRecords() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("src/main/resources/records.xml"))) {
            SaveGame saveGame;
            for (int i = 20; i > 0; i--) {
                saveGame = (SaveGame) objectInputStream.readObject();
                String username = saveGame.username;
                String score = saveGame.score;
                keys[i - 1] = username;
                values[i - 1] = score;
                TetrisApp.tableText.add(new Text(i + ". " + username + " - " + score));
                TetrisApp.tableText.get(20 - i).setStyle("-fx-font-size: 14;");
                TetrisApp.tableText.get(20 - i).setStyle("-fx-font-family: 'OCR A Extended'");
                TetrisApp.tableText.get(20 - i).setY(250 + i * 20);
                TetrisApp.tableText.get(20 - i).setX(TetrisApp.XMAX + 10);
                TetrisApp.tableText.get(20 - i).setFill(Color.WHITE);
            }
            TetrisApp.group.getChildren().addAll(TetrisApp.tableText);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}

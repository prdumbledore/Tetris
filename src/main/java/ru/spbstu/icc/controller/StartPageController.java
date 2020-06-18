package ru.spbstu.icc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import ru.spbstu.icc.view.TetrisApp;

public class StartPageController {

    @FXML
    private Button startGame;

    @FXML
    private TextField nickname;

    @FXML
    private void initialize() {
        startGame.setOnAction(e -> {
            if (!nickname.getText().isEmpty() && nickname.getText().split(" ").length == 1 &&
                    nickname.getText().length() >= 3) {
                TetrisApp.window.close();
                TetrisApp.userName = nickname.getText();
                TetrisApp.window.setScene(TetrisApp.getScene());
                TetrisApp.window.setTitle("Tetris");
                TetrisApp.window.getIcons().add(new Image(String.valueOf(getClass().getResource("/design/logo.png"))));
                TetrisApp.window.setResizable(false);
                TetrisApp.window.sizeToScene();
                TetrisApp.window.show();
                new TetrisApp().startGame(TetrisApp.window);
            }
        });
    }
}

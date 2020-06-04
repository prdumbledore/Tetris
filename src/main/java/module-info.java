module Tetris {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    opens ru.spbstu.icc.view to javafx.graphics, javafx.fxml ,javafx.controls;
    opens ru.spbstu.icc.controller to javafx.graphics, javafx.fxml ,javafx.controls;
    opens ru.spbstu.icc.model to javafx.graphics, javafx.fxml ,javafx.controls;
    exports ru.spbstu.icc.view;
    exports ru.spbstu.icc.controller;
    exports ru.spbstu.icc.model;
}
package com.tictaccode.tictactoeai;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AIView {
    @FXML
    private TextArea ta;

    public void makeResizable(Stage stage, Scene scene) {
        stage.widthProperty().addListener(
                (ChangeListener) (observableValue, o, t1) -> ta.setPrefWidth(scene.getWidth()));

        stage.heightProperty().addListener(
                (ChangeListener) (observableValue, o, t1) -> ta.setPrefHeight(scene.getHeight()));
    }

    public void showMessage(String message) {
        ta.appendText(message);
    }
}


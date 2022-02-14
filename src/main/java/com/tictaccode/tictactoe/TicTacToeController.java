package com.tictaccode.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class TicTacToeController {
    @FXML
    Text versionLabel;
    
    public void initialize() {
        versionLabel.setText(TicTacToeApplication.VERSION);
    }
}

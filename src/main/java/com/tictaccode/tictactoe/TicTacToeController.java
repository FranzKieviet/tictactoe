package com.tictaccode.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class TicTacToeController {
    
    @FXML
    Text versionLabel;
    
    @FXML
    Button spot1, spot2, spot3, spot4, spot5, spot6, spot7, spot8, spot9;
    
    private Button[] gameButtons;
    private Board board;
    
    public void initialize() {
        versionLabel.setText(TicTacToeApplication.VERSION);
        
        gameButtons = new Button[9];
        gameButtons[0] = spot1;
        gameButtons[1] = spot2;
        gameButtons[2] = spot3;
        gameButtons[3] = spot4;
        gameButtons[4] = spot5;
        gameButtons[5] = spot6;
        gameButtons[6] = spot7;
        gameButtons[7] = spot8;
        gameButtons[8] = spot9;
        
        board = new Board(this);
    }
    
    public void placeMove(MouseEvent mouseEvent) {
        disableGameButtons();
    
        // TODO: Change the PlayType to an alternating type per player
        PlayType playType = PlayType.X;
        
        Button b = (Button) mouseEvent.getSource();
        String id = b.getId();
        int idNum = Integer.parseInt(id.substring(id.length() - 1));
        
        int x = 0, y = 0;
        for (int i = 0; i < idNum - 1; ++i) {
            if (x < Board.BOARD_SIZE - 1)
                x++;
            else {
                y++;
                x = 0;
            }
        }
        System.out.println("x="+x+" y="+y);
        
        board.setBoardPosition(x, y, PlayType.X);
        
        // TODO: Display the X or O here
        
        enableAvailableGameButtons();
    }
    
    public void disableGameButtons() {
        for (int i = 0; i < gameButtons.length; ++i)
            gameButtons[i].setDisable(true);
    }
    
    public void enableAvailableGameButtons() {
        PlayType[][] boardSpots = board.getBoardSpots();
        
        for (int y = 0; y < Board.BOARD_SIZE; ++y)
            for (int x = 0; x < Board.BOARD_SIZE; ++x)
                if (boardSpots[y][x] == PlayType.NOTHING)
                    gameButtons[x + y * 3].setDisable(false);
    }
}

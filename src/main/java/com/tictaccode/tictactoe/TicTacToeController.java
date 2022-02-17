package com.tictaccode.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * Controller for the tic-tac-toe game UI that handles all interactions with elements in the UI.
 */
public class TicTacToeController {
    
    /** Javafx text element retrieved from the fxml file. */
    @FXML
    Text versionLabel;
    
    /** Javafx button elements retrieved from the fxml file. */
    @FXML
    Button spot1, spot2, spot3, spot4, spot5, spot6, spot7, spot8, spot9;
    
    /** Javafx line elements retrieved from the fxml file. */
    @FXML
    Line x1L, x1R, x2L, x2R, x3L, x3R, x4L, x4R, x5L, x5R, x6L, x6R, x7L, x7R, x8L, x8R, x9L, x9R;
    
    /** Javafx circle elements retrieved from the fxml file. */
    @FXML
    Circle o1, o2, o3, o4, o5, o6, o7, o8, o9;
    
    /** Stores all the game buttons from the ui for ease of use. */
    private Button[] gameButtons;
    
    /** Stores all the X pieces. */
    private XPiece[] xPieces;
    
    /** Stores all the O pieces. */
    private OPiece[] oPieces;
    
    /** A board object that is used to keep track of the positions of pieces on the board and handle game logic. */
    private Board board;
    
    
    /**
     * The initialize() method is automatically called by javafx when the controller class is first called.
     * Initializes all private data members and sets up the initial tic-tac-toe board.
     */
    public void initialize() {
        // sets the text of the version label based on the current version of the application
        versionLabel.setText(TicTacToeApplication.VERSION);
        
        // initializes the gameButtons array
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
        
        // initializes the xPieces array
        xPieces = new XPiece[9];
        xPieces[0] = new XPiece("x1", x1L, x1R);
        xPieces[1] = new XPiece("x2", x2L, x2R);
        xPieces[2] = new XPiece("x3", x3L, x3R);
        xPieces[3] = new XPiece("x4", x4L, x4R);
        xPieces[4] = new XPiece("x5", x5L, x5R);
        xPieces[5] = new XPiece("x6", x6L, x6R);
        xPieces[6] = new XPiece("x7", x7L, x7R);
        xPieces[7] = new XPiece("x8", x8L, x8R);
        xPieces[8] = new XPiece("x9", x9L, x9R);
        
        // initializes the oPieces array
        oPieces = new OPiece[9];
        oPieces[0] = new OPiece("o1", o1);
        oPieces[1] = new OPiece("o2", o2);
        oPieces[2] = new OPiece("o3", o3);
        oPieces[3] = new OPiece("o4", o4);
        oPieces[4] = new OPiece("o5", o5);
        oPieces[5] = new OPiece("o6", o6);
        oPieces[6] = new OPiece("o7", o7);
        oPieces[7] = new OPiece("o8", o8);
        oPieces[8] = new OPiece("o9", o9);
        
        // initializes the board
        board = new Board(this);
    }
    
    
    /**
     * Disables all the game buttons on the tic-tac-toe board.
     */
    public void disableGameButtons() {
        for (Button gameButton : gameButtons)
            gameButton.setDisable(true);
    }
    
    
    /**
     * Enables all game buttons on the tic-tac-toe board that are empty (no X or O).
     */
    public void enableAvailableGameButtons() {
        // the boardSpots array holds whether each spot on the tic-tac-toe board has an O, X, or nothing in it
        PlayType[][] boardSpots = board.getBoardSpots();
        
        // traverses through the boardSpots array and enables all buttons in the spot that has nothing in it
        for (int y = 0; y < Board.BOARD_SIZE; ++y)
            for (int x = 0; x < Board.BOARD_SIZE; ++x)
                if (boardSpots[y][x] == PlayType.NOTHING)
                    gameButtons[x + y * 3].setDisable(false);
    }
    
    
    /**
     * The placeMove() method allows the user to play their move on the tic-tac-toe board and updates all necessary
     * data pertaining to the move.
     *
     * @param mouseEvent holds all the mouse event information when the method is called by the UI.
     */
    public void placeMove(MouseEvent mouseEvent) {
        // disables all game buttons while placing the move
        disableGameButtons();
    
        // TODO: Change the PlayType to an alternating type per player
        // sets what type of piece the user is playing
        PlayType playType = PlayType.X;
        
        // gets the id number of the button in order to get the position of the button on the game board
        Button b = (Button) mouseEvent.getSource();
        String id = b.getId();
        int idNum = Integer.parseInt(id.substring(id.length() - 1));
        
        // gets the x and y position where the move was placed
        int x = 0, y = 0;
        for (int i = 0; i < idNum - 1; ++i) {
            if (x < Board.BOARD_SIZE - 1)
                x++;
            else {
                y++;
                x = 0;
            }
        }
        
        // sets the move on the board
        board.setBoardPosition(x, y, PlayType.X);
        
        // displays the move to the user
        if (playType == PlayType.X) {
            // ---- Retrieves the correct X piece and displays it on the board ----
            xPieces[idNum - 1].displayPiece();
        } else {
            // ---- Retrieves the correct O piece and displays it on the board ----
            oPieces[idNum - 1].displayPiece();
        }
        
        // TODO: Check if someone won the game here
        
        // enables all game buttons in the spots that are empty on the game board
        enableAvailableGameButtons();
    }
}

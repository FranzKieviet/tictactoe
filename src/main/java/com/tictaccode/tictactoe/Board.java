package com.tictaccode.tictactoe;

public class Board {
    public static final int BOARD_SIZE = 3;
    
    private TicTacToeController uiController;
    private PlayType[][] boardSpots;
    
    public Board(TicTacToeController controller) {
        uiController = controller;
        boardSpots = new PlayType[BOARD_SIZE][BOARD_SIZE];
        
        newBoard();
    }
    
    // creates a new board here
    public void newBoard() {
        // sets the board 2d matrix to all 0
        for (int y = 0; y < BOARD_SIZE; ++y)
            for (int x = 0; x < BOARD_SIZE; ++x)
                boardSpots[y][x] = PlayType.NOTHING;
        
        // TODO: eventually reset the board buttons and Xs and Os here
    }
    
    // sets a specific position on the board to a certain play type
    public void setBoardPosition(int x, int y, PlayType playType) {
        boardSpots[y][x] = playType;
    }
    
    public PlayType[][] getBoardSpots() {
        return boardSpots;
    }
}

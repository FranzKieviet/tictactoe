package com.tictaccode.tictactoe;

/**
 * The Board class represents a tic-tac-toe board. The class stores all data and handles all game logic pertaining to a
 * game of tic-tac-toe.
 */
public class Board {
    /** The size of a tic-tac-toe board. */
    public static final int BOARD_SIZE = 3;
    
    /** A reference to the UI controller that handles UI components. */
    private TicTacToeController uiController;
    
    /** Represents the tic-tac-toe game board and holds what pieces are where on the board. */
    private PlayType[][] boardSpots;
    
    
    /**
     * Constructor that creates a new board object and initializes private data members.
     *
     * @param controller is the UI controller that is referenced by the Board class.
     */
    public Board(TicTacToeController controller) {
        uiController = controller;
        boardSpots = new PlayType[BOARD_SIZE][BOARD_SIZE];
        
        // creates a new board
        newBoard();
    }

    public Board(Board board) {

        boardSpots = new PlayType[BOARD_SIZE][BOARD_SIZE];

        for (int y = 0; y < BOARD_SIZE; ++y)
            for (int x = 0; x < BOARD_SIZE; ++x)
            {
                if(board.getBoardSpots()[y][x] == PlayType.X) boardSpots[y][x] = PlayType.X;
                if(board.getBoardSpots()[y][x] == PlayType.O) boardSpots[y][x] = PlayType.O;
                if(board.getBoardSpots()[y][x] == PlayType.NOTHING) boardSpots[y][x] = PlayType.NOTHING;
            }




    }

    
    /**
     * Creates a new board that is empty (PlayType.NOTHING).
     */
    public void newBoard() {
        // sets all the spots in the boardSpots 2d matrix to PlayType.NOTHING
        for (int y = 0; y < BOARD_SIZE; ++y)
            for (int x = 0; x < BOARD_SIZE; ++x)
                boardSpots[y][x] = PlayType.NOTHING;
        
        // TODO: eventually call a method from the controller to reset the board buttons and Xs and Os here
    }
    
    
    public StateType checkState() {
        //row control if complete X or O
        for (int y = 0; y < BOARD_SIZE; ++y) {
            if (boardSpots[y][0] == boardSpots[y][1] && boardSpots[y][1] == boardSpots[y][2]) {
                if (boardSpots[y][0] == PlayType.X)
                    return StateType.X_WINNER;
                else if (boardSpots[y][0] == PlayType.O)
                    return StateType.O_WINNER;
            }
        }
        //column control if complete X or O
        for (int x = 0; x < BOARD_SIZE; ++x) {
            if (boardSpots[0][x] == boardSpots[1][x] && boardSpots[1][x] == boardSpots[2][x]) {
                if (boardSpots[0][x] == PlayType.X)
                    return StateType.X_WINNER;
                else if (boardSpots[0][x] == PlayType.O)
                    return StateType.O_WINNER;
            }
        }
        
        //diagonal control if complete X or O
        if ((boardSpots[0][0] == boardSpots[1][1] && boardSpots[1][1] == boardSpots[2][2]) ||
                (boardSpots[0][2] == boardSpots[1][1] && boardSpots[1][1] == boardSpots[2][0])) {
            if (boardSpots[1][1] == PlayType.X)
                return StateType.X_WINNER;
            else if (boardSpots[1][1] == PlayType.O)
                return StateType.O_WINNER;
        }
        
        return (isComplete() ? StateType.DRAW : StateType.CONTINUE);
    }
    
    
    private boolean isComplete() {
        for (int y = 0; y < BOARD_SIZE; ++y)
            for (int x = 0; x < BOARD_SIZE; ++x)
                if (boardSpots[y][x] == PlayType.NOTHING)
                    return false;
        return true;
    }
    
    
    /**
     * Sets a specific position on the board to a certain play type.
     *
     * @param x is the x position to set on the board.
     * @param y is the y position to set on the board.
     * @param playType is the type of play to set to the position on the board.
     */
    public void setBoardPosition(int x, int y, PlayType playType) {
        if (playType == null)
            throw new IllegalArgumentException("A PlayType must be passed.");
        
        boardSpots[y][x] = playType;
    }
    
    
    /**
     * Gets a reference to the boardSpots 2d matrix that stores where pieces are on the board.
     *
     * @return The boardSpots 2d matrix.
     */
    public PlayType[][] getBoardSpots() {
        return boardSpots;
    }

}

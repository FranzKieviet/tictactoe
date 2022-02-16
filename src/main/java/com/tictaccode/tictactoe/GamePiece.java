package com.tictaccode.tictactoe;

/**
 * The GamePiece class is abstract and stores necessary data members and methods for tic-tac-toe game pieces to
 * implement.
 */
public abstract class GamePiece {
    
    /** A unique id of a game piece. */
    protected String id;
    
    /**
     * Gets the unique id of the game piece.
     *
     * @return The unique id of the game piece.
     */
    public String getId() {
        return id;
    }
    
    
    /**
     * An abstract method for children of the GamePiece class to implement to display the piece on the tic-tac-toe
     * board.
     */
    public abstract void displayPiece();
    
    
    /**
     * An abstract method for children of the GamePiece class to implement to hide the piece on the tic-tac-toe board.
     */
    public abstract void hidePiece();
}

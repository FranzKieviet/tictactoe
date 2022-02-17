package com.tictaccode.tictactoe;

import javafx.scene.shape.Line;

/**
 * The XPiece class is a type of game piece that represents an X on a tic-tac-toe board and handles all data for a
 * single X.
 */
public class XPiece extends GamePiece {

//    private static final int LEFT_END = 40;
//    private static final int RIGHT_END = -40;
    
    /** A UI component that is the left line of the X. */
    private Line leftLine;
    
    /** A UI component that is the right line of the X. */
    private Line rightLine;
    
    
    /**
     * Constructor that creates a new X piece and initializes private data members according to the parameters.
     *
     * @param id is the unique id of the X game piece.
     * @param leftLine is the left line of the X in the UI.
     * @param rightLine is the right line of the X in the UI.
     */
    public XPiece(String id, Line leftLine, Line rightLine) {
        this.id = id;
        this.leftLine = leftLine;
        this.rightLine = rightLine;
    }
    
    
    /**
     * Displays the X on the game board in the UI.
     */
    @Override
    public void displayPiece() {
        leftLine.setVisible(true);
        rightLine.setVisible(true);
    }
    
    
    /**
     * Hides the X on the game board in the UI.
     */
    @Override
    public void hidePiece() {
        leftLine.setVisible(false);
        rightLine.setVisible(false);
    }
    
    
    /**
     * Gets the left line UI component of the X.
     *
     * @return A Line object that represents the left line of the X.
     */
    public Line getLeftLine() {
        return leftLine;
    }
    
    
    /**
     * Gets the right line UI component of the X.
     *
     * @return A Line object that represents the right line of the X.
     */
    public Line getRightLine() {
        return rightLine;
    }
}

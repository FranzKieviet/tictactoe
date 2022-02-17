package com.tictaccode.tictactoe;

import javafx.scene.shape.Circle;

/**
 * The OPiece class is a type of game piece that represents an O on a tic-tac-toe board and handles all data for a
 * single O.
 */
public class OPiece extends GamePiece {

//    private static final int RADIUS = 22;
    
    /** A UI component that displays the O on the tic-tac-toe board. */
    private Circle oShape;
    
    /**
     * Constructor that creates a new O piece and initializes private data members according to the parameters.
     *
     * @param id is the unique id of the O game piece.
     * @param oShape is the O shape in the UI.
     */
    public OPiece(String id, Circle oShape) {
        this.id = id;
        this.oShape = oShape;
    }
    
    
    /**
     * Displays the O on the game board in the UI.
     */
    @Override
    public void displayPiece() {
        oShape.setVisible(true);
    }
    
    
    /**
     * Hides the O on the game board in the UI.
     */
    @Override
    public void hidePiece() {
        oShape.setVisible(false);
    }
    
    
    /**
     * Gets the O shape UI component.
     *
     * @return A Circle object that represents the O shape.
     */
    public Circle getOShape() {
        return oShape;
    }
}

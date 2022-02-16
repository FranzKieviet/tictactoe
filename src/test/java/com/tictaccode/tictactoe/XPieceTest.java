package com.tictaccode.tictactoe;

import javafx.scene.shape.Line;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XPieceTest {
    
    private XPiece xPiece;
    
    @BeforeEach
    public void setup() {
        xPiece = new XPiece("x", new Line(), new Line());
    }
    
    @Test
    @DisplayName("Should Display Piece Correctly")
    public void shouldDisplayPieceCorrectly() {
        xPiece.getLeftLine().setVisible(false);
        xPiece.getRightLine().setVisible(false);
        
        xPiece.displayPiece();
        
        Assertions.assertTrue(xPiece.getLeftLine().isVisible());
        Assertions.assertTrue(xPiece.getRightLine().isVisible());
    }
    
    @Test
    @DisplayName("Should Hide Piece Correctly")
    public void shouldHidePieceCorrectly() {
        xPiece.getLeftLine().setVisible(true);
        xPiece.getRightLine().setVisible(true);
        
        xPiece.hidePiece();
        
        Assertions.assertTrue(!xPiece.getLeftLine().isVisible());
        Assertions.assertTrue(!xPiece.getRightLine().isVisible());
    }
}

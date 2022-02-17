package com.tictaccode.tictactoe;

import javafx.scene.shape.Circle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OPieceTest {
    
    private OPiece oPiece;
    
    @BeforeEach
    public void setup() {
        oPiece = new OPiece("o", new Circle());
    }
    
    @Test
    @DisplayName("Should Display Piece Correctly")
    public void shouldDisplayPieceCorrectly() {
        oPiece.getOShape().setVisible(false);
    
        oPiece.displayPiece();
        
        Assertions.assertTrue(oPiece.getOShape().isVisible());
    }
    
    @Test
    @DisplayName("Should Hide Piece Correctly")
    public void shouldHidePieceCorrectly() {
        oPiece.getOShape().setVisible(true);
    
        oPiece.hidePiece();
        
        Assertions.assertTrue(!oPiece.getOShape().isVisible());
    }
}

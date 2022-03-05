package com.tictaccode.tictactoe;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    
    private Board board;
    
    @BeforeEach
    public void setup() {
        board = new Board(new TicTacToeController());
    }
    
    @Test
    @DisplayName("Should Create New Board")
    void shouldCreateNewBoard() {
        Assertions.assertDoesNotThrow(() -> {
           board.newBoard();
        });
    }
    
    @Test
    @DisplayName("Should Not Set Board Position When X Out Of Bounds")
    void shouldNotSetBoardPositionWhenXOutOfBounds() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
           board.setBoardPosition(-3, 2, PlayType.X);
        });
        
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
           board.setBoardPosition(Board.BOARD_SIZE, 0, PlayType.X);
        });
    }
    
    @Test
    @DisplayName("Should Not Set Board Position When Y Out Of Bounds")
    void shouldNotSetBoardPositionWhenYOutOfBounds() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            board.setBoardPosition(0, -2, PlayType.X);
        });
        
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            board.setBoardPosition(1, Board.BOARD_SIZE, PlayType.X);
        });
    }
    
    @Test
    @DisplayName("Should Not Set Board Position With Invalid Play Type")
    void shouldNotSetBoardPositionWithInvalidPlayType() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            board.setBoardPosition(0, 0, null);
        });
    }
    
    @Test
    @DisplayName("Should Set Board Position With Valid Input")
    void shouldSetBoardPositionWithValidInput() {
        board.setBoardPosition(0, 0, PlayType.X);
        Assertions.assertTrue(board.getBoardSpots()[0][0] == PlayType.X);
        
        board.setBoardPosition(Board.BOARD_SIZE - 1, Board.BOARD_SIZE - 1, PlayType.O);
        Assertions.assertTrue(board.getBoardSpots()[Board.BOARD_SIZE - 1][Board.BOARD_SIZE - 1] == PlayType.O);
        
        board.setBoardPosition(0, 0, PlayType.NOTHING);
        Assertions.assertTrue(board.getBoardSpots()[0][0] == PlayType.NOTHING);
    }
    
    @Test
    @DisplayName("Should Get Board Spots Not Null")
    void shouldGetBoardSpotsNotNull() {
        Assertions.assertTrue(board.getBoardSpots() != null);
    }

}

package com.tictaccode.tictactoeclient;

public class GameOverInfo {
    private StateType stateType;
    
    private LineType lineType;
    
    private GamePiece startPiece, endPiece;
    
    public GameOverInfo(StateType stateType, LineType lineType) {
        this.stateType = stateType;
        this.lineType = lineType;
    }
    
    public GameOverInfo(StateType stateType, LineType lineType, GamePiece startPiece, GamePiece endPiece) {
        this.stateType = stateType;
        this.lineType = lineType;
        this.startPiece = startPiece;
        this.endPiece = endPiece;
    }
    
    public StateType getStateType() {
        return stateType;
    }
    
    public LineType getLineType() {
        return lineType;
    }
    
    public GamePiece getStartPiece() {
        return startPiece;
    }
    
    public GamePiece getEndPiece() {
        return endPiece;
    }
}

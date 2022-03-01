package com.tictaccode.tictactoe;

import javafx.stage.Stage;

public abstract class Controller {
    protected TicTacToeApplication application;
    protected Stage stage;
    
    public abstract void startUI();
    
    public abstract void updateUI();
    
    public abstract void setApplication(TicTacToeApplication application);
    
    public abstract void setStage(Stage stage);
}

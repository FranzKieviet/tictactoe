package com.tictaccode.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TicTacToeApplication extends Application {
    
    public static final String VERSION = "0.1-SNAPSHOT";
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource("tictactoe-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        Application.launch();
    }
}

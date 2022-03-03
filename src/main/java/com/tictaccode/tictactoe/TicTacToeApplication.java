package com.tictaccode.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Creates the window that the tic-tac-toe application runs on and displays the initial scene to the window.
 */
public class TicTacToeApplication extends Application {
    
    /** Stores the current version of the application. */
    public static final String VERSION = "0.5.2-SNAPSHOT";
    
    private Scene welcomeScene;
    
    /**
     * Loads the initial scene, creates the window, then displays the scene on the window.
     *
     * @param primaryStage is the window where the initial scene is displayed.
     * @throws IOException is thrown when the fxml file for the scene is unable to load.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        welcomeScene = Welcome.getScene(primaryStage, this);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(welcomeScene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public void startSinglePlayerGame(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource("tictactoe-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        
        TicTacToeController controller = fxmlLoader.getController();
        controller.setStage(primaryStage);
        controller.setWelcomeScene(welcomeScene);
        controller.setToSinglePlayerMode();
        
        primaryStage.show();
    }
    
    /**
     * Launches the tic-tac-toe application.
     *
     * @param args are the arguments taken into the application.
     */
    public static void main(String[] args) {
        Application.launch();
    }
}

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
    public static final String VERSION = "0.5.5-SNAPSHOT";
    
    public static final int MIN_WIDTH = 600;
    public static final int MIN_HEIGHT = 600;
    
    private boolean isFirst = true;
    
    /**
     * Loads the initial scene, creates the window, then displays the scene on the window.
     *
     * @param primaryStage is the window where the initial scene is displayed.
     * @throws IOException is thrown when the fxml file for the scene is unable to load.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setTitle("Tic Tac Toe");
        
        startWelcomeScreen(primaryStage);
    }
    
    public void startWelcomeScreen(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource("welcome-view.fxml"));
        Scene welcomeScene;
        
        if (isFirst)
            welcomeScene = new Scene(fxmlLoader.load());
        else
            welcomeScene = new Scene(fxmlLoader.load(), primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
    
        WelcomeController welcomeController = fxmlLoader.getController();
        welcomeController.setApplication(this);
        welcomeController.setStage(primaryStage);
    
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
        welcomeController.startUI();
        
        if (isFirst)
            isFirst = false;
        else
            welcomeController.fadeIn();
        
    }

    public void startLocalMultiplayerGame(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource("tictactoe-view.fxml"));
        Scene ticTacToeScene = new Scene(fxmlLoader.load(), primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());

        TicTacToeController ticTacToeController = fxmlLoader.getController();
        ticTacToeController.setApplication(this);
        ticTacToeController.setStage(primaryStage);
        ticTacToeController.setGameType(GameType.LOCAL_MULTIPLAYER);
        
        primaryStage.setScene(ticTacToeScene);
        primaryStage.show();
        ticTacToeController.startUI();
        ticTacToeController.fadeIn();
    }

    public void startSinglePlayerGame(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource("tictactoe-view.fxml"));
        Scene ticTacToeScene = new Scene(fxmlLoader.load(), primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());

        TicTacToeController ticTacToeController = fxmlLoader.getController();
        ticTacToeController.setApplication(this);
        ticTacToeController.setStage(primaryStage);
        ticTacToeController.setGameType(GameType.SINGLEPLAYER);

        primaryStage.setScene(ticTacToeScene);
        primaryStage.show();
        ticTacToeController.startUI();
        ticTacToeController.fadeIn();
    }
    
    public void startResultsScreen(Stage primaryStage, GameOverInfo gameOverInfo, GameType gameType) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource("results-view.fxml"));
        Scene resultsScene = new Scene(fxmlLoader.load(), primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
    
        ResultsController resultsController = fxmlLoader.getController();
        resultsController.setApplication(this);
        resultsController.setStage(primaryStage);
        resultsController.setGameOverInfo(gameOverInfo);
        resultsController.setGameType(gameType);
    
        primaryStage.setScene(resultsScene);
        primaryStage.show();
        resultsController.startUI();
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

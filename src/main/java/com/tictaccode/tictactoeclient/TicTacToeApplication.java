package com.tictaccode.tictactoeclient;

import com.tictaccode.necessities.Connection;
import com.tictaccode.necessities.Message;
import com.tictaccode.necessities.SocketManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

/**
 * Creates the window that the tic-tac-toe application runs on and displays the initial scene to the window.
 */
public class TicTacToeApplication extends Application implements SocketManager {
    
    /** Stores the current version of the application. */
    public static final String VERSION = "0.5.6-SNAPSHOT";
    
    public static final int MIN_WIDTH = 600;
    public static final int MIN_HEIGHT = 600;
    
    public static final String HOST = "localhost";
    public static final int PORT = 8000;
    
    private Connection connection;
    private long clientID, gameID;
    private int playerNumber;
    
    private TicTacToeController ticTacToeController;
    
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
        
        clientID = -1;
        gameID = -1;
        playerNumber = -1;
    
        // creates client socket to connect to the server
        try {
            Socket socket = new Socket(HOST, PORT);
            connection = new Connection(socket, this);
        
            primaryStage.setOnCloseRequest((v) -> exitGame());
        }
        catch (Exception e) {
            // could not connect to the server
            System.err.println("The game could not connect to the server.");
            System.exit(1);
        }
        
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
    
    public void startOnlineMultiplayerGame(Stage primaryStage) {
        if (clientID == -1)
            return;
        
        connection.sendMessage(new Message("CreateGame", Long.toString(clientID)));
        
        new Thread(() -> {
            try {
                while (gameID == -1)
                    Thread.sleep(10);
                Platform.runLater(() -> startGame(primaryStage, GameType.ONLINE_MULTIPLAYER));
            }
            catch (InterruptedException e) {
                System.err.println("Error while waiting for game ID.");
                System.exit(1);
            }
        }).start();
    }
    
    public void startGame(Stage primaryStage, GameType gameType) {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource("tictactoe-view.fxml"));
        Scene ticTacToeScene = null;
        try {
            ticTacToeScene = new Scene(fxmlLoader.load(), primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
        }
        catch (IOException e) {
            System.err.println("Error while loading FXML file for the tic-tac-toe board UI.");
            System.exit(1);
        }
    
        ticTacToeController = fxmlLoader.getController();
        ticTacToeController.setApplication(this);
        ticTacToeController.setStage(primaryStage);
        ticTacToeController.setGameType(gameType);
    
        primaryStage.setScene(ticTacToeScene);
        primaryStage.show();
        ticTacToeController.startUI();
        ticTacToeController.fadeIn();
        
        if (playerNumber == 1)
            ticTacToeController.enableAvailableGameButtons();
    }
    
    public void sendMove(String moveInfo) {
        connection.sendMessage(new Message("game/"+gameID+"/"+clientID, moveInfo));
    }

        // TODO Add local multiplayer game here
//    public void startLocalMultiplayerGame(Stage primaryStage) throws Exception {
//        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource("tictactoe-view.fxml"));
//        Scene ticTacToeScene = new Scene(fxmlLoader.load(), primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
//
//        TicTacToeController ticTacToeController = fxmlLoader.getController();
//        ticTacToeController.setApplication(this);
//        ticTacToeController.setStage(primaryStage);
//        ticTacToeController.setGameType(GameType.LOCAL_MULTIPLAYER);
//
//        primaryStage.setScene(ticTacToeScene);
//        primaryStage.show();
//        ticTacToeController.startUI();
//        ticTacToeController.fadeIn();
//    }
    
    // TODO Add single-player game here once AI component is finished
//    public void startSinglePlayerGame(Stage primaryStage) throws Exception {
//        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource("tictactoe-view.fxml"));
//        Scene ticTacToeScene = new Scene(fxmlLoader.load(), primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
//
//        TicTacToeController ticTacToeController = fxmlLoader.getController();
//        ticTacToeController.setApplication(this);
//        ticTacToeController.setStage(primaryStage);
//        ticTacToeController.setGameType(GameType.SINGLEPLAYER);
//
//        primaryStage.setScene(ticTacToeScene);
//        primaryStage.show();
//        ticTacToeController.startUI();
//        ticTacToeController.fadeIn();
//    }
    
    public void startResultsScreen(Stage primaryStage, StateType stateType, GameType gameType) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource("results-view.fxml"));
        Scene resultsScene = new Scene(fxmlLoader.load(), primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
    
        ResultsController resultsController = fxmlLoader.getController();
        resultsController.setApplication(this);
        resultsController.setStage(primaryStage);
        resultsController.setStateType(stateType);
        resultsController.setGameType(gameType);
    
        primaryStage.setScene(resultsScene);
        primaryStage.show();
        resultsController.startUI();
    }
    
    @Override
    public void handleReceivedMessage(Connection connection, Message message) {
        String channel = message.getChannel();
        String messageText = message.getMessage();
        
        if (channel.length() == 0 || messageText.length() == 0)
            return;
        
        if (channel.equals("ClientID"))
            clientID = Long.parseLong(messageText);
        else if (channel.equals(Long.toString(clientID)) && messageText.startsWith("GameCreated")) {
            playerNumber = Integer.parseInt(messageText.substring(12, 13));
            gameID = Long.parseLong(messageText.substring(14));
            connection.sendMessage(new Message("game/"+gameID+"/"+clientID, "JoinChannel"));
        }
        else if (channel.startsWith("game/"+gameID)) {
            
            if (messageText.charAt(0) == 'O' || messageText.charAt(0) == 'X')
                ticTacToeController.otherPlaceMove(messageText, false);
            else {
                int i = Integer.parseInt(messageText.substring(messageText.length() - 1));
                
                if (messageText.startsWith("Win"))
                    ticTacToeController.doGameOver(StateType.values()[i]);
                else if (messageText.startsWith("Lose")) {
                    ticTacToeController.otherPlaceMove(messageText.substring(5, 10), true);
                    ticTacToeController.doGameOver(StateType.values()[i]);
                }
                else if (messageText.startsWith("Tie")) {
                    
                    if (messageText.charAt(4) == 'X' || messageText.charAt(4) == 'O')
                        ticTacToeController.otherPlaceMove(messageText.substring(5, 10), true);
                    
                    ticTacToeController.doGameOver(StateType.values()[i]);
                }
            }
        }
    }
    
    @Override
    public void handleConnectionClosed(Connection connection) {
        System.out.println("The game has lost connection to the server.");
        System.exit(0);
    }
    
    public void exitGame() {
        connection.closeSocket();
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

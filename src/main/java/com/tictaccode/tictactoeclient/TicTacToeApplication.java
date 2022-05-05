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
    
    private TicTacToeView ticTacToeView;
    
    private boolean isFirst, isLocalMultiplayer;
    
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
    
        // gets whether it is the first time loading the main menu
        isFirst = true;
        
        clientID = -1;
        gameID = -1;
        playerNumber = -1;
        
        // creates client socket to connect to the server
        try {
            Socket socket = new Socket(HOST, PORT);
            connection = new Connection(socket, this);
        }
        catch (Exception e) {
            // could not connect to the server
            System.err.println("The game could not connect to the server.");
            System.exit(1);
        }
    
        primaryStage.setOnCloseRequest((v) -> exitGame());
        
        startWelcomeScreen(primaryStage);
    }
    
    public void startWelcomeScreen(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource("welcome-view.fxml"));
        Scene welcomeScene;
    
        isLocalMultiplayer = false;
        
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
    
    public void startLocalMultiplayerGame(Stage primaryStage) {
        System.out.println(clientID);
        if (clientID == -1)
            return;
    
        isLocalMultiplayer = true;
    
        System.out.println("Sending create game message");
        connection.sendMessage(new Message("CreateLocalGame", Long.toString(clientID)));
        
        new Thread(() -> {
            try {
                System.out.println("Waiting for game to be created");
                while (gameID == -1)
                    Thread.sleep(10);
                Platform.runLater(() -> startGame(primaryStage, GameType.LOCAL_MULTIPLAYER));
            }
            catch (InterruptedException e) {
                System.err.println("Error while waiting for game ID.");
                System.exit(1);
            }
        }).start();
    }
    
    public void startOnlineMultiplayerGame(Stage primaryStage) {
        System.out.println(clientID);
        if (clientID == -1)
            return;
        
        System.out.println("Sending create game message");
        connection.sendMessage(new Message("CreateGame", Long.toString(clientID)));
        
        new Thread(() -> {
            try {
                System.out.println("Waiting for game to be created");
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
        System.out.println(gameID);
        System.out.println("Game created, loading scene");
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource("tictactoe-view.fxml"));
        Scene ticTacToeScene = null;
        try {
            ticTacToeScene = new Scene(fxmlLoader.load(), primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
        }
        catch (IOException e) {
            System.err.println("Error while loading FXML file for the tic-tac-toe board UI.");
            System.exit(1);
        }
        
        ticTacToeView = fxmlLoader.getController();
        ticTacToeView.setApplication(this);
        ticTacToeView.setStage(primaryStage);
        ticTacToeView.setGameType(gameType);
        
        primaryStage.setScene(ticTacToeScene);
        primaryStage.show();
        
        ticTacToeView.startUI();
        ticTacToeView.setPlayerTurn((playerNumber == 1 || playerNumber == 3));
        ticTacToeView.fadeIn();
        
        if (playerNumber == 1 || playerNumber == 3)
            ticTacToeView.enableAvailableGameButtons();
    }
    
    public void sendMove(String moveInfo) {
        connection.sendMessage(new Message("game/"+gameID+"/"+clientID, moveInfo));
    }
    
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
    
    public void startResultsScreen(Stage primaryStage, StateType stateType, GameType gameType,
                                   String whoWon) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource("results-view.fxml"));
        Scene resultsScene = new Scene(fxmlLoader.load(), primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
        
        ResultsController resultsController = fxmlLoader.getController();
        resultsController.setApplication(this);
        resultsController.setStage(primaryStage);
        resultsController.setStateType(stateType);
        resultsController.setGameType(gameType);
        resultsController.setWhoWon(whoWon);
        
        primaryStage.setScene(resultsScene);
        primaryStage.show();
        resultsController.startUI();
    }
    
    @Override
    public synchronized void handleReceivedMessage(Connection connection, Message message) {
        String channel = message.getChannel();
        String messageText = message.getMessage();
        
        System.out.println(channel + ": " + messageText);
        
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
            
            if (isLocalMultiplayer) {
                if (messageText.equals("Continue"))
                    ticTacToeView.enableAvailableGameButtons();
                else if (messageText.startsWith("Win")) {
                    if (messageText.charAt(4) == 'X')
                        Platform.runLater(() -> ticTacToeView.doGameOver(StateType.X_WINNER, "X"));
                    else
                        Platform.runLater(() -> ticTacToeView.doGameOver(StateType.O_WINNER, "O"));
                }
                else if (messageText.equals("Tie"))
                    Platform.runLater(() -> ticTacToeView.doGameOver(StateType.DRAW, "Tie"));
            }
            else {
                if (messageText.charAt(0) == 'O' || messageText.charAt(0) == 'X')
                    ticTacToeView.otherPlaceMove(messageText, false);
                else if (messageText.startsWith("Win"))
                    Platform.runLater(() ->
                            ticTacToeView.doGameOver((playerNumber == 1 ? StateType.X_WINNER : StateType.O_WINNER),
                                    "You"));
                else if (messageText.startsWith("Lose")) {
                    ticTacToeView.otherPlaceMove(messageText.substring(5, 10), true);
                    Platform.runLater(() ->
                            ticTacToeView.doGameOver((playerNumber == 1 ? StateType.O_WINNER : StateType.X_WINNER),
                                    "Opponent"));
                }
                else if (messageText.startsWith("Tie")) {
        
                    if (messageText.length() > 4 && (messageText.charAt(4) == 'X' || messageText.charAt(4) == 'O'))
                        ticTacToeView.otherPlaceMove(messageText.substring(5, 10), true);
        
                    Platform.runLater(() -> ticTacToeView.doGameOver(StateType.DRAW, "Tie"));
                }
            }
        }
    }
    
    @Override
    public synchronized void handleConnectionClosed(Connection connection) {
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

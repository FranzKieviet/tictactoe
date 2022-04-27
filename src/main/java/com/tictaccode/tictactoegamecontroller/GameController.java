package com.tictaccode.tictactoegamecontroller;

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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GameController extends Application implements SocketManager {
    
    public static final String HOST = "localhost";
    public static final int PORT = 8000;
    
    private GameControllerView controller;
    
    private Connection connection;
    private Map<Long, Game> games;
    private long currentGameID, clientID1, clientID2;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        currentGameID = 1;
        clientID1 = -1;
        clientID2 = -1;
        
        // creates and shows the console for the game controller component
        FXMLLoader fxmlLoader = new FXMLLoader(GameController.class.getResource("game-controller-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 200);
        controller = fxmlLoader.getController();
        controller.makeResizable(primaryStage, scene);
        primaryStage.setTitle("Game Controller");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        games = Collections.synchronizedMap(new HashMap<>());
        
        // creates client socket to connect to the server
        try {
            Socket socket = new Socket(HOST, PORT);
            connection = new Connection(socket, this);
            connection.sendMessage(new Message("CreateGame", "JoinChannel"));
            
            primaryStage.setOnCloseRequest((v) -> connection.closeSocket());
        }
        catch (Exception e) {
            // could not connect to the server
            System.err.println("The game could not connect to the server.");
            System.exit(1);
        }
    
        Platform.runLater(() -> controller.showMessage("Game Controller started at " + new Date() + '\n'));
    }
    
    public void sendGameInfo(String channel, String info) {
        connection.sendMessage(new Message(channel, info));
    }
    
    public void gameFinished(long gameID) {
        games.remove(gameID);
    }
    
    @Override
    public void handleReceivedMessage(Connection connection, Message message) {
        String channel = message.getChannel();
        String messageText = message.getMessage();
        
        controller.showMessage(channel + ": " + messageText + '\n');
        
        if (channel.equals("CreateGame")) {
            long clientID = Long.parseLong(messageText);
            
            if (clientID1 == -1)
                clientID1 = clientID;
            else if (clientID2 == -1)
                clientID2 = clientID;
            
            if (clientID1 != -1 && clientID2 != -1) {
                int whoFirst = (int) (Math.random() * 2);
                
                if (whoFirst == 0)
                    games.put(currentGameID, new Game(this, currentGameID, clientID1, clientID2));
                else
                    games.put(currentGameID, new Game(this, currentGameID, clientID2, clientID1));
                
                clientID1 = -1;
                clientID2 = -1;
                currentGameID++;
            }
        }
        else if (channel.startsWith("game/")) {
            String[] params = channel.split("/");
            long gameID = Long.parseLong(params[1]);
            
            if (games.containsKey(gameID)) {
                long clientID = Long.parseLong(params[2]);
                params = messageText.split(" ");
                PlayType playType;
                
                if (params[0].equals("X"))
                    playType = PlayType.X;
                else if (params[0].equals("O"))
                    playType = PlayType.O;
                else
                    return;
                
                games.get(gameID).doMove(Integer.parseInt(params[2]), Integer.parseInt(params[1]), playType,
                        clientID);
            }
        }
    }
    
    @Override
    public void handleConnectionClosed(Connection connection) {
        System.out.println("The Game Controller has lost connection to the server.");
        System.exit(0);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

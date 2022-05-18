package com.tictaccode.tictactoegamecontroller;

import com.tictaccode.necessities.Connection;
import com.tictaccode.necessities.Message;
import com.tictaccode.necessities.SocketManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class GameController extends Application implements SocketManager {
    
    public static final String HOST = "localhost";
    public static final int PORT = 8000;
    
    private GameControllerView controller;
    
    private Connection connection;
    private Map<Long, Game> games;
    private Map<String, Long> availableServers;
    private long currentGameID;

    private AI gameAI;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        currentGameID = 1;
        
        // creates and shows the console for the game controller component
        FXMLLoader fxmlLoader = new FXMLLoader(GameController.class.getResource("game-controller-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 200);
        controller = fxmlLoader.getController();
        controller.makeResizable(primaryStage, scene);
        primaryStage.setTitle("Game Controller");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        games = Collections.synchronizedMap(new HashMap<>());
        availableServers = Collections.synchronizedMap(new HashMap<>());

        gameAI = new AI(PlayType.O);
        
        // creates client socket to connect to the server
        try {
            Socket socket = new Socket(HOST, PORT);
            connection = new Connection(socket, this);
            connection.sendMessage(new Message("CreateServer", "JoinChannel"));
            connection.sendMessage(new Message("JoinServer", "JoinChannel"));
            connection.sendMessage(new Message("CancelServer", "JoinChannel"));
            connection.sendMessage(new Message("GetServers", "JoinChannel"));
            connection.sendMessage(new Message("CreateLocalGame", "JoinChannel"));
            connection.sendMessage(new Message("CreateSinglePlayerGame", "JoinChannel"));
        }
        catch (Exception e) {
            // could not connect to the server
            System.err.println("The game could not connect to the server.");
            System.exit(1);
        }
        
        primaryStage.setOnCloseRequest((v) -> connection.closeSocket());
    
        Platform.runLater(() -> controller.showMessage("Game Controller started at " + new Date() + '\n'));
    }
    
    public void sendGameInfo(String channel, String info) {
        connection.sendMessage(new Message(channel, info));
    }
    
    public void gameFinished(long gameID) {
        games.remove(gameID);
    }
    
    @Override
    public synchronized void handleReceivedMessage(Connection connection, Message message) {
        String channel = message.getChannel();
        String messageText = message.getMessage();
        
        controller.showMessage(channel + ": " + messageText + '\n');
        
        if (channel.equals("CreateServer")) {
            int index = messageText.indexOf(' ');
            
            availableServers.put(messageText.substring(index + 1), Long.valueOf(messageText.substring(0, index)));
            
            connection.sendMessage(new Message("GetServers", "add " + messageText.substring(index + 1)));
        }
        else if (channel.equals("JoinServer")) {
            int index = messageText.indexOf(' ');
            
            long clientID1 = availableServers.get(messageText.substring(index + 1));
            long clientID2 = Long.parseLong(messageText.substring(0, index));
            
            games.put(currentGameID, new Game(this, currentGameID, clientID1, clientID2));
            currentGameID++;
            
            availableServers.remove(messageText.substring(index + 1));
            connection.sendMessage(
                    new Message("GetServers", "remove " + messageText.substring(index + 1)));
        }
        else if (channel.equals("CancelServer")) {
            int index = messageText.indexOf(' ');
            
            if (Objects.equals(availableServers.get(messageText.substring(index + 1)),
                    Long.valueOf(messageText.substring(0, index)))) {
                availableServers.remove(messageText.substring(index + 1));
                connection.sendMessage(
                        new Message("GetServers", "remove " + messageText.substring(index + 1)));
            }
        }
        else if (channel.equals("GetServers")) {
            String[] params = messageText.split(" ");
            
            if (params[0].equals("GetAllServers"))
                availableServers.forEach((k, v) ->
                        connection.sendMessage(new Message(params[1], "AddServer " + k)));
        }
        else if (channel.equals("CreateLocalGame")) {
            long clientID = Long.parseLong(messageText);
            games.put(currentGameID, new Game(this, currentGameID, clientID));
            currentGameID++;
        }
        else if (channel.equals("CreateSinglePlayerGame")) {
            long clientID = Long.parseLong(messageText);
            games.put(currentGameID, new Game(this, currentGameID, clientID));
            currentGameID++;
        }
        else if (channel.startsWith("game/")) {
            String[] params = channel.split("/");
            long gameID = Long.parseLong(params[1]);
            
            if (games.containsKey(gameID)) {
                long clientID = Long.parseLong(params[2]);
                
                if (messageText.equals("LeaveGame"))
                    games.get(gameID).leaveGame(clientID);
                else if (messageText.equals("TryAgain"))
                    games.get(gameID).willTryAgain(clientID);
                else if (messageText.equals("MoveAI")) {
                    Pair<Integer, Integer> move = gameAI.determineMove(games.get(gameID).getBoard());
                    String info = "AI" + move.getValue().toString() + " " + move.getKey().toString();
                    games.get(gameID).doMove(move.getKey(), move.getValue(), PlayType.O,
                            clientID);
                    sendGameInfo("game/"+gameID+"/"+clientID, info);


                }
                else {
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
    }
    
    @Override
    public synchronized void handleConnectionClosed(Connection connection) {
        System.out.println("The Game Controller has lost connection to the server.");
        System.exit(0);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

package com.tictaccode.tictactoeai;

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
import java.util.Date;

public class AI extends Application implements SocketManager {

    public static final String HOST = "localhost";
    public static final int PORT = 8000;

    private AIView controller;

    private Connection connection;
    private long currentGameID, clientID1, clientID2;

    @Override
    public void start(Stage primaryStage) throws IOException {
        currentGameID = 1;

        // creates and shows the console for the game controller component
        FXMLLoader fxmlLoader = new FXMLLoader(AI.class.getResource("ai-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 200);
        controller = fxmlLoader.getController();
        controller.makeResizable(primaryStage, scene);
        primaryStage.setTitle("AI");
        primaryStage.setScene(scene);
        primaryStage.show();


        // creates client socket to connect to the server
        try {
            Socket socket = new Socket(HOST, PORT);
            connection = new Connection(socket, this);
            connection.sendMessage(new Message("CreateSinglePlayerGame", "JoinChannel"));
        }
        catch (Exception e) {
            // could not connect to the server
            System.err.println("The AI could not connect to the server.");
            System.exit(1);
        }

        primaryStage.setOnCloseRequest((v) -> connection.closeSocket());

        Platform.runLater(() -> controller.showMessage("AI started at " + new Date() + '\n'));
    }

    public void sendGameInfo(String channel, String info) {
        connection.sendMessage(new Message(channel, info));
    }

    @Override
    public synchronized void handleReceivedMessage(Connection connection, Message message) {
        String channel = message.getChannel();
        String messageText = message.getMessage();

        controller.showMessage(channel + ": " + messageText + '\n');

    }

    @Override
    public synchronized void handleConnectionClosed(Connection connection) {
        System.out.println("The AI has lost connection to the server.");
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


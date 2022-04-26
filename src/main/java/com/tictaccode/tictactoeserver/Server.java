package com.tictaccode.tictactoeserver;

import com.tictaccode.necessities.Connection;
import com.tictaccode.necessities.Message;
import com.tictaccode.necessities.SocketManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server extends Application implements SocketManager {
    
    public static final int PORT = 8000;
    
    private ServerController controller;
    
    private Set<Connection> connections;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        // creates and shows the console for the server
        FXMLLoader fxmlLoader = new FXMLLoader(Server.class.getResource("server-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 200);
        controller = fxmlLoader.getController();
        controller.makeResizable(primaryStage, scene);
        primaryStage.setTitle("Server");
        primaryStage.show();
        
        connections = Collections.synchronizedSet(new HashSet<>());
        
        new Thread(() -> {
            // create a server socket
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(PORT);
            }
            catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            
            Platform.runLater(() -> controller.showMessage("Server started at " + new Date() + '\n'));
            ServerSocket finalServerSocket = serverSocket;
            
            // closes all connections and the server socket if the server is shutdown
            primaryStage.setOnCloseRequest(e -> {
                for (Connection connection : connections)
                    connection.closeSocket();
                
                try {
                    finalServerSocket.close();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            
            // listen for a connection request while the server is open
            while (!finalServerSocket.isClosed()) {
                try {
                    Socket socket = finalServerSocket.accept();
                    Platform.runLater(() ->
                            controller.showMessage(socket.getInetAddress().getHostAddress() + " has connected.\n"));
                    Connection connection = new Connection(socket, this);
                    connections.add(connection);
                }
                catch (IOException e) {}
            }
        }).start();
    }
    
    @Override
    public void handleReceivedMessage(Connection connection, Message message) {
        String channel = message.getChannel();
        String messageText = message.getMessage();
        
        switch (messageText) {
            case "JoinChannel":
                connection.addChannel(channel);
                break;
            case "ExitChannel":
                if (connection.hasChannel(channel))
                    connection.removeChannel(channel);
                break;
            default:
                for (Connection otherConnection : connections)
                    if (otherConnection.hasChannel(channel) && !otherConnection.equals(connection))
                        otherConnection.sendMessage(message);
        }
    }
    
    @Override
    public void handleConnectionClosed(Connection connection) {
        Platform.runLater(() -> controller.showMessage(
                connection.getSocket().getInetAddress().getHostAddress() + " has disconnected.\n"));
        connections.remove(connection);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

package com.tictaccode.necessities;

public interface SocketManager {
    void handleReceivedMessage(Connection connection, Message message);
    
    void handleConnectionClosed(Connection connection);
}

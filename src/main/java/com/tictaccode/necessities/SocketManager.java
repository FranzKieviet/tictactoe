package com.tictaccode.necessities;

import java.net.Socket;

public interface SocketManager {
    void handleReceivedMessage(Connection connection, Message message);
    
    void handleConnectionClosed(Connection connection);
}

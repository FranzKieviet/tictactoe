package com.tictaccode.necessities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Connection {
    
    private Socket socket;
    
    private SocketManager socketManager;
    
    private Thread listener;
    
    private Set<String> channels;
    
    public Connection(Socket socket, SocketManager socketManager) {
        this.socket = socket;
        this.socketManager = socketManager;
        
        channels = Collections.synchronizedSet(new HashSet<>());
        
        listener = new Thread(() -> {
            try {
                ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream());
    
                while (!this.socket.isClosed()) {
                    Object o = ois.readObject();
                    Message message = (Message) o;
                    this.socketManager.handleReceivedMessage(this, message);
                }
            }
            // thrown when the socket is closed/closing
            catch (IOException e) {
                closeSocket();
            }
            // thrown by readObject() method - invalid data is sent through the stream
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        
        listener.start();
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    public void addChannel(String channel) {
        channels.add(channel);
    }
    
    public void removeChannel(String channel) {
        channels.remove(channel);
    }
    
    public boolean hasChannel(String channel) {
        return channels.contains(channel);
    }
    
    public synchronized void sendMessage(Message message) {
        try {
            new ObjectOutputStream(socket.getOutputStream()).writeObject(message);
        }
        catch (IOException e) {
            closeSocket();
        }
    }
    
    public synchronized void closeSocket() {
        if (!socket.isClosed()) {
            listener.interrupt();
    
            try {
                socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                socketManager.handleConnectionClosed(this);
            }
        }
    }
    
    @Override
    public String toString() {
        return "Connection{" +
                "socket=" + socket +
                ", socketManager=" + socketManager +
                ", listener=" + listener +
                ", channels=" + channels +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return Objects.equals(socket, that.socket) && Objects.equals(socketManager, that.socketManager) &&
                Objects.equals(listener, that.listener) && Objects.equals(channels, that.channels);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(socket, socketManager, listener, channels);
    }
}

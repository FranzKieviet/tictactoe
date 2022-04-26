package com.tictaccode.necessities;

import java.util.Objects;

public class Message {
    private String channel;
    private String message;
    
    public Message(String channel, String message) {
        this.channel = channel;
        this.message = message;
    }
    
    public void setChannel(String channel) {
        this.channel = channel;
    }
    
    public String getChannel() {
        return channel;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
    @Override
    public String toString() {
        return "Message{" +
                "channel='" + channel + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(channel, message1.channel) && Objects.equals(message, message1.message);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(channel, message);
    }
}

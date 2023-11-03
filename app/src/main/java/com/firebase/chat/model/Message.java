package com.firebase.chat.model;

import com.firebase.chat.utils.Utils;

public class Message {
    private String user1, user2, lastMessage;

    public Message() {
    }

    public Message(String user1, String user2, String lastMessage) {
        this.user1 = user1;
        this.user2 = user2;
        this.lastMessage = lastMessage;

        if (!user1.equals(Utils.CURRENT_EMAIL)) {
            String temp = this.user2;
            this.user2 = this.user1;
            this.user1 = temp;
        }
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

}

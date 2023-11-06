package com.firebase.chat.models;

public class Friend {
    private String senderId;
    private String receiverId;
    private String status;

    public Friend() {
    }

    public Friend(String senderId, String receiverId, String status) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
    }
}

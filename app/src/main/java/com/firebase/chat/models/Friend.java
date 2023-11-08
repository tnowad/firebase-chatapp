package com.firebase.chat.models;

import com.google.firebase.firestore.PropertyName;

public class Friend {
    @PropertyName("senderId")
    public String senderId;
    @PropertyName("receiverId")
    public String receiverId;
    @PropertyName("status")
    public String status;

    public Friend() {
    }

    public Friend(String senderId, String receiverId, String status) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
    }
}

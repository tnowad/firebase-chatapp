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

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getFriendUid(String uid) {
        if (senderId == null || senderId == "" || receiverId == null || receiverId == "") {
            return "";
        }
        if (senderId.equals(uid)) {
            return receiverId;
        }
        return senderId;
    }

    public boolean isAccepted() {
        if (status == null || status == "") {
            return false;
        }
        return status.equals("accepted");
    }
}

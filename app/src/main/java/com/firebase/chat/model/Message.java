package com.firebase.chat.model;

import androidx.databinding.ObservableField;

public class Message {
    private String me, myFriend, lastMessage;

    public Message() {
    }

    public Message(String me, String myFriend, String lastMessage) {
        this.me = me;
        this.myFriend = myFriend;
        this.lastMessage = lastMessage;
    }

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }

    public String getMyFriend() {
        return myFriend;
    }

    public void setMyFriend(String myFriend) {
        this.myFriend = myFriend;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}

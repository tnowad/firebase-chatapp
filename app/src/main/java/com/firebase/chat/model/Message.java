package com.firebase.chat.model;

import java.util.List;

public class Message {
    private String me, myFriend, lastMessage;
    private List<Chat> listChat;

    public Message() {
    }

    public Message(String me, String myFriend, String lastMessage, List<Chat> listChat) {
        this.me = me;
        this.myFriend = myFriend;
        this.lastMessage = lastMessage;
        this.listChat = listChat;
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

    public List<Chat> getListChat() {
        return listChat;
    }

    public void setListChat(List<Chat> listChat) {
        this.listChat = listChat;
    }
}

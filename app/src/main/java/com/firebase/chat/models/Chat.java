package com.firebase.chat.models;

public class Chat {
    private String fromUser, toUser, content;

    public Chat() {
    }

    public Chat(String fromUser, String toUser, String content) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.content = content;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

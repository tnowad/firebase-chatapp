package com.firebase.chat.models;

import com.google.firebase.Timestamp;

import org.ocpsoft.prettytime.PrettyTime;

public class Message {
    private String chatId;
    private String senderId;
    private String content;
    private Timestamp timestamp;

    public Message() {
    }

    public Message(String chatId, String senderId, String content, Timestamp timestamp) {
        this.chatId = chatId;
        this.senderId = senderId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLimitLengthContent(int length) {
        if (this.content == null || this.content == "") {
            return "";
        }
        if (this.content.length() > length) {
            return this.content.substring(0, length) + "...";
        }
        return this.content;
    }

    public String getPrettyTimeTimestamp() {
        if (timestamp == null) {
            return "";
        }
        PrettyTime prettyTime = new PrettyTime();
        String timeAgo = prettyTime.format(timestamp.toDate());
        return timeAgo;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

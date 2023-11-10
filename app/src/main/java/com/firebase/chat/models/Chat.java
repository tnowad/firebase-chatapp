package com.firebase.chat.models;

import java.util.List;
import java.util.Map;

public class Chat {
    private String id;
    private List<String> participants;
    private String lastMessageId;
    private Map<String, String> lastMessageSeen;

    public Chat() {
    }

    public Chat(String id, List<String> participants) {
        this.id = id;
        this.participants = participants;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public String getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(String lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

    public Map<String, String> getLastMessageSeen() {
        return lastMessageSeen;
    }

    public void setLastMessageSeen(Map<String, String> lastMessageSeen) {
        this.lastMessageSeen = lastMessageSeen;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id='" + id + '\'' +
                ", participants=" + participants +
                ", lastMessageId='" + lastMessageId + '\'' +
                ", lastMessageSeen=" + lastMessageSeen +
                '}';
    }
}

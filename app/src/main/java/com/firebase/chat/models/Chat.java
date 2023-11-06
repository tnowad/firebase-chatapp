package com.firebase.chat.models;

import java.util.List;

public class Chat {
    private String id;
    private List<String> participants;

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
}

package com.firebase.chat.model;

import java.util.List;

public class User {
    private String id;
    private String email;
    private String username;
    private String bio;
    private Boolean gender;
    private List<String> listFriend;

    public User() {
    }

    public User(String id, String email, String username, String bio, Boolean gender, List<String> listFriend) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.bio = bio;
        this.gender = gender;
        this.listFriend = listFriend;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public List<String> getListFriend() {
        return listFriend;
    }

    public void setListFriend(List<String> listFriend) {
        this.listFriend = listFriend;
    }
}

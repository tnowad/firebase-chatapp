package com.firebase.chat.utils;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.firebase.chat.models.Chat;
import com.firebase.chat.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class Utils {
    public static ObservableArrayList<Chat> LIST_CHAT;
    public static List<User> LIST_USER = new ArrayList<>();
    public static String CURRENT_UID;
    public static Chat SELECTED_CHAT;
    public static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
}

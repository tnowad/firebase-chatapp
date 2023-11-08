package com.firebase.chat.utils;

import androidx.databinding.ObservableArrayList;

import com.firebase.chat.models.Chat;

import java.text.SimpleDateFormat;


public class Utils {
    public static ObservableArrayList<Chat> LIST_CHAT;
    public static String CURRENT_UID;
    public static Chat SELECTED_CHAT;
    public static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
}

package com.firebase.chat.utils;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.firebase.chat.model.Chat;
import com.firebase.chat.model.Message;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class Utils {
    public static ObservableArrayList<Message> LIST_MESSAGE;
    public static ObservableArrayList<Chat> LIST_CHAT;
    public static String CURRENT_EMAIL;
    public static Message SELECTED_MESSAGE;
    public static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
}

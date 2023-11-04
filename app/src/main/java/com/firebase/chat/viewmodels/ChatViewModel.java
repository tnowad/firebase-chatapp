package com.firebase.chat.viewmodels;

import android.annotation.SuppressLint;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.adapters.ChatItem;
import com.firebase.chat.models.Chat;
import com.firebase.chat.services.MessageService;
import com.firebase.chat.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;

public class ChatViewModel {
    private static ChatItem adapter;
    private final MessageService messageService = new MessageService();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public ObservableList<Chat> listChat = new ObservableArrayList<>();
    private String toUser;

    public ChatViewModel() {
        toUser = Utils.SELECTED_MESSAGE.getUser2();
        getListChat();
    }

    @BindingAdapter({"list_chat"})
    public static void loadListChat(RecyclerView recyclerView, ObservableList<Chat> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ChatItem(list);
        recyclerView.setAdapter(adapter);
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public void getListChat() {
        if (listChat != null) {
            listChat.clear();
        } else {
            listChat = new ObservableArrayList<>();
        }

        Utils.LIST_CHAT = new ObservableArrayList<>();
        listChat = Utils.LIST_CHAT;
        Utils.LIST_CHAT.add(new Chat("benlun1201@gmail.com", "benlun99999@gmail.com", "Hello"));
        Utils.LIST_CHAT.add(new Chat("benlun99999@gmail.com", "benlun1201@gmail.com", "Hello con cac"));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void clear() {
        if (listChat != null) {
            listChat.clear();
        }
        adapter.notifyDataSetChanged();
    }

}

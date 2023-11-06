package com.firebase.chat.viewmodels;

import android.annotation.SuppressLint;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.adapters.MessageItemAdapter;
import com.firebase.chat.models.Chat;
import com.firebase.chat.models.Message;
import com.firebase.chat.services.MessageService;
import com.firebase.chat.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;

public class MessageViewModel {
    private static MessageItemAdapter messageItemAdapter;
    private final MessageService messageService = new MessageService();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public ObservableList<Message> listMessage = new ObservableArrayList<>();
    private String selectedUser;

    public MessageViewModel() {
        selectedUser = Utils.SELECTED_CHAT.getId();
        getListMessage();
    }

    @BindingAdapter({"list_chat"})
    public static void loadListChat(RecyclerView recyclerView, ObservableList<Chat> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

//        adapter = new MessageItemAdapter(list);
        recyclerView.setAdapter(messageItemAdapter);
    }

    public String getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void getListMessage() {
        if (listMessage != null) {
            listMessage.clear();
        } else {
            listMessage = new ObservableArrayList<>();
        }

        Utils.LIST_MESSAGE = new ObservableArrayList<>();
        listMessage = Utils.LIST_MESSAGE;
        Utils.LIST_MESSAGE.add(new Message());
        Utils.LIST_MESSAGE.add(new Message());
        Utils.LIST_MESSAGE.add(new Message());
        Utils.LIST_MESSAGE.add(new Message());
        Utils.LIST_MESSAGE.add(new Message());
    }

    @SuppressLint("NotifyDataSetChanged")
    private void clear() {
        if (listMessage != null) {
            listMessage.clear();
        }
        messageItemAdapter.notifyDataSetChanged();
    }

}

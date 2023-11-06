package com.firebase.chat.viewmodels;

import android.annotation.SuppressLint;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.adapters.ListMessageItemAdapter;
import com.firebase.chat.models.Message;
import com.firebase.chat.services.MessageService;
import com.firebase.chat.utils.Utils;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

public class MessageViewModel {
    private static ListMessageItemAdapter listMessageItemAdapter;
    private final MessageService messageService = new MessageService();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public ObservableList<Message> listMessage = new ObservableArrayList<>();
    public String selectedUser;

    public MessageViewModel() {
        selectedUser = Utils.SELECTED_CHAT.getId();
        getListMessage();
    }

    @BindingAdapter({"list_message"})
    public static void loadListMessage(RecyclerView recyclerView, ObservableList<Message> listMessage) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        listMessageItemAdapter = new ListMessageItemAdapter(listMessage);
        recyclerView.setAdapter(listMessageItemAdapter);
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
        Message message = new Message();
        message.setChatId("123");
        message.setContent("Hello, Can I talk with you!");
        message.setTimestamp(Timestamp.now().toDate().toString());
        message.setSenderId("123");
        Utils.LIST_MESSAGE.add(message);
        Utils.LIST_MESSAGE.add(message);
        Utils.LIST_MESSAGE.add(message);
        Utils.LIST_MESSAGE.add(message);
        Utils.LIST_MESSAGE.add(message);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void clear() {
        if (listMessage != null) {
            listMessage.clear();
        }
        listMessageItemAdapter.notifyDataSetChanged();
    }

}

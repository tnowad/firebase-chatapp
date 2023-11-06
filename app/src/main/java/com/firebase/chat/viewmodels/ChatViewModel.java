package com.firebase.chat.viewmodels;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.adapters.ListChatItemAdapter;
import com.firebase.chat.models.Chat;
import com.firebase.chat.services.MessageService;
import com.firebase.chat.utils.Utils;

import java.util.Arrays;

public class ChatViewModel {
    private static final String TAG = ChatViewModel.class.getSimpleName();
    private static Context context;
    private static ListChatItemAdapter listChatItemAdapter;
    private final MessageService messageService = new MessageService();
    public ObservableList<Chat> listChat = new ObservableArrayList<>();

    public ChatViewModel(Context context) {
        ChatViewModel.context = context;
        getListChat();
    }

    @BindingAdapter({"list_chat"})
    public static void loadListMess(RecyclerView recyclerView, ObservableList<Chat> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        listChatItemAdapter = new ListChatItemAdapter(context, list);
        recyclerView.setAdapter(listChatItemAdapter);
    }

    public void getListChat() {
        if (listChat != null) {
            listChat.clear();
        } else {
            listChat = new ObservableArrayList<>();
        }

        Utils.LIST_CHAT = new ObservableArrayList<>();
        listChat = Utils.LIST_CHAT;

        Chat chat = new Chat();
        chat.setId("123");
        chat.setParticipants(Arrays.asList("123", "234"));

        Utils.LIST_CHAT.add(chat);
        Utils.LIST_CHAT.add(chat);
        Utils.LIST_CHAT.add(chat);
        Utils.LIST_CHAT.add(chat);
        Utils.LIST_CHAT.add(chat);
        Utils.LIST_CHAT.add(chat);
        Utils.LIST_CHAT.add(chat);
        Utils.LIST_CHAT.add(chat);

        messageService.getList();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void clear() {
        if (listChat != null) {
            listChat.clear();
        }
        listChatItemAdapter.notifyDataSetChanged();
    }

}

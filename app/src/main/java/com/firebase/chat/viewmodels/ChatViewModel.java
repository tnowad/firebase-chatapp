package com.firebase.chat.viewmodels;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.adapters.ChatItemAdapter;
import com.firebase.chat.models.Chat;
import com.firebase.chat.services.MessageService;
import com.firebase.chat.utils.Utils;

public class ChatViewModel {
    private static Context context;
    private static ChatItemAdapter adapter;
    private final MessageService messageService = new MessageService();
    public ObservableList<Chat> listChat = new ObservableArrayList<>();

    public ChatViewModel(Context context) {
        ChatViewModel.context = context;
        getListChat();
    }

    @BindingAdapter({"list_mess"})
    public static void loadListMess(RecyclerView recyclerView, ObservableList<Chat> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new ChatItemAdapter(context, list);
        recyclerView.setAdapter(adapter);
    }

    public void getListChat() {
        if (listChat != null) {
            listChat.clear();
        } else {
            listChat = new ObservableArrayList<>();
        }

        Utils.LIST_CHAT = new ObservableArrayList<>();
        listChat = Utils.LIST_CHAT;

        Utils.LIST_CHAT.add(new Chat());
        Utils.LIST_CHAT.add(new Chat());
        Utils.LIST_CHAT.add(new Chat());
        Utils.LIST_CHAT.add(new Chat());
        Utils.LIST_CHAT.add(new Chat());
        Utils.LIST_CHAT.add(new Chat());
        Utils.LIST_CHAT.add(new Chat());
        Utils.LIST_CHAT.add(new Chat());
        Utils.LIST_CHAT.add(new Chat());


        messageService.getList();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void clear() {
        if (listChat != null) {
            listChat.clear();
        }
        adapter.notifyDataSetChanged();
    }

}

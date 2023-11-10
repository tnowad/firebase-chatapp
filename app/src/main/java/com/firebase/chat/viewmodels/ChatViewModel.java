package com.firebase.chat.viewmodels;

import android.content.Context;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.adapters.ListChatItemAdapter;
import com.firebase.chat.models.Chat;

public class ChatViewModel {
    private static final String TAG = ChatViewModel.class.getSimpleName();
    private static Context context;
    private static ListChatItemAdapter listChatItemAdapter;
    public ObservableList<Chat> chatObservableList = new ObservableArrayList<>();

    public ChatViewModel(Context context) {
        ChatViewModel.context = context;
    }

    @BindingAdapter({"chatItems"})
    public static void bindChatItems(RecyclerView recyclerView, ObservableList<Chat> chatObservableList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        listChatItemAdapter = new ListChatItemAdapter(context, chatObservableList);
        recyclerView.setAdapter(listChatItemAdapter);
    }

    public void setChatItems(ObservableList<Chat> chatItems) {
        chatObservableList.clear();
        chatObservableList.addAll(chatItems);
        if (listChatItemAdapter != null) {
            listChatItemAdapter.notifyDataSetChanged();
        }
    }
}

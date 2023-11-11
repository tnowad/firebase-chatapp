package com.firebase.chat.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.adapters.ListMessageItemAdapter;
import com.firebase.chat.models.Message;

public class MessageViewModel {

    private static final String TAG = MessageViewModel.class.getSimpleName();
    private static Context context;
    private static ListMessageItemAdapter listMessageItemAdapter;
    public ObservableList<Message> messageObservableList = new ObservableArrayList<>();

    public MessageViewModel(Context context) {
        MessageViewModel.context = context;
    }

    @BindingAdapter({"messageItems"})
    public static void bindMessageItems(RecyclerView recyclerView, ObservableList<Message> messageObservableList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        listMessageItemAdapter = new ListMessageItemAdapter(context, messageObservableList);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setAdapter(listMessageItemAdapter);
    }

    public void setMessageItems(ObservableList<Message> messageItems) {
        messageObservableList.clear();
        messageObservableList.addAll(messageItems);
        if (listMessageItemAdapter != null) {
            listMessageItemAdapter.notifyDataSetChanged();
        }
    }

    public void hideKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void sendMessage(Message message) {
        // Logic to send the message
    }

    public void receiveMessage(Message message) {
        // Logic to receive and display the message
    }

}

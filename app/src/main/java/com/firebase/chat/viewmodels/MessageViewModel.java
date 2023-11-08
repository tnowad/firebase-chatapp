package com.firebase.chat.viewmodels;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.adapters.ListMessageItemAdapter;
import com.firebase.chat.models.Message;
import com.firebase.chat.services.MessageService;
import com.firebase.chat.utils.Utils;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageViewModel {
    private static ListMessageItemAdapter listMessageItemAdapter;
    private final MessageService messageService = new MessageService();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private List<Message> listMessage = new ArrayList<>();
    public String selectedUser;

    public MessageViewModel() {
        selectedUser = Utils.SELECTED_CHAT.getId();
        loadDataListMessage();
    }

    public void setListMessage(List<Message> listMessage) {
        this.listMessage = listMessage;
    }

    public List<Message> getListMessage() {
        return listMessage;
    }

    @BindingAdapter({"list_message"})
    public static void loadListMessage(@NonNull RecyclerView recyclerView, List<Message> listMessage) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        listMessageItemAdapter = new ListMessageItemAdapter(listMessage);
        recyclerView.setAdapter(listMessageItemAdapter);
        recyclerView.scrollToPosition(listMessage.size() - 1);
    }

    public String getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void loadDataListMessage() {
        if (listMessage != null) {
            listMessage.clear();
        } else {
            listMessage = new ArrayList<>();
        }

        Message message = new Message();
        message.setChatId("123");
        message.setContent("Hello, Can I talk with you!");
        message.setTimestamp(Timestamp.now().toDate().toString());
        message.setSenderId("123");
        listMessage.add(message);
        listMessage.add(message);
        listMessage.add(message);
        listMessage.add(message);
        listMessage.add(message);
        listMessage.add(message);
        listMessage.add(message);
        listMessage.add(message);
        listMessage.add(message);
        listMessage.add(message);
        listMessage.add(message);
        listMessage.add(message);
        listMessage.add(message);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void clear() {
        if (listMessage != null) {
            listMessage.clear();
        }
        listMessageItemAdapter.notifyDataSetChanged();
    }

    public void sendMessage(@NonNull RecyclerView recyclerView, TextView textView) {
        String messageContent = textView.getText().toString().trim();
        if (TextUtils.isEmpty(messageContent)) {
            return;
        }
        Message newMessage = new Message();
        newMessage.setChatId("123");
        newMessage.setContent(messageContent);
        newMessage.setTimestamp(Timestamp.now().toDate().toString());
        newMessage.setSenderId(Utils.CURRENT_UID);
        listMessage.add(newMessage);

        textView.setText("");
        recyclerView.scrollToPosition(listMessage.size() - 1);
    }

    public void scrollWhenKeyboardShowed(@NonNull View view, RecyclerView recyclerView) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();

                view.getWindowVisibleDisplayFrame(r);

                int heightDiff = view.getRootView().getHeight() - r.height();
                if (heightDiff > 0.25 * view.getRootView().getHeight()) {
                    if (listMessage.size() > 0) {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener) view);
                        recyclerView.scrollToPosition(listMessage.size() - 1);
                    }
                }
            }
        });
    }

    public void hideKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}

package com.firebase.chat.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.firebase.chat.R;
import com.firebase.chat.databinding.ActivityMessageBinding;
import com.firebase.chat.models.Message;
import com.firebase.chat.services.ChatService;
import com.firebase.chat.services.MessageService;
import com.firebase.chat.viewmodels.MessageViewModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class MessageActivity extends AppCompatActivity {

    private ActivityMessageBinding activityMessageBinding;
    private MessageService messageService;
    private ChatService chatService;
    private MessageViewModel messageViewModel;

    private String chatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        messageService = MessageService.getInstance();
        chatService = ChatService.getInstance();

        activityMessageBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_message);
        messageViewModel = new MessageViewModel(this);

        activityMessageBinding.setMessageViewModel(messageViewModel);

        messageService.getAllMessagesByChatId("R1eJEUgHJjFdQdK0Ya1A", (queryDocumentSnapshots, e) -> {
            if (e != null) {
                Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT);
                return;
            }

            ObservableList<Message> messageObservableList = new ObservableArrayList<>();


            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                handleDocumentChanges(messageObservableList, document);
            }

            messageViewModel.setMessageItems(messageObservableList);
        });

        activityMessageBinding.ChatActivityImageButtonBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(0, 0);
        });

        activityMessageBinding.MessageActivityImageButtonSendMess.setOnClickListener(v -> {
        });

        activityMessageBinding.MessageActivityEditTextInputMess.setOnClickListener(v -> {
            //messageViewModel.scrollWhenKeyboardShowed(activityMessageBinding.getRoot(),activityMessageBinding.MessageActivityRecyclerViewListChat);
        });

        activityMessageBinding.MessageActivityFrameLayout.setOnClickListener(v -> messageViewModel.hideKeyboard(MessageActivity.this, v));
    }

    private void handleDocumentChanges(ObservableList<Message> messageObservableList, QueryDocumentSnapshot document) {
        Message message = document.toObject(Message.class);
        messageObservableList.add(message);

        document.getReference().addSnapshotListener((snapshot, error) -> {
            if (error != null) {
                return;
            }

            if (snapshot != null && snapshot.exists()) {
                Message updatedMessage = snapshot.toObject(Message.class);
                int index = messageObservableList.indexOf(message);
                if (index != -1) {
                    messageObservableList.set(index, updatedMessage);
                }
            }
        });
    }

}
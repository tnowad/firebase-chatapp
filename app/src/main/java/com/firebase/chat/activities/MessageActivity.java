package com.firebase.chat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.firebase.chat.R;
import com.firebase.chat.databinding.ActivityMessageBinding;
import com.firebase.chat.models.Chat;
import com.firebase.chat.models.Message;
import com.firebase.chat.models.User;
import com.firebase.chat.services.AuthService;
import com.firebase.chat.services.ChatService;
import com.firebase.chat.services.MessageService;
import com.firebase.chat.services.UserService;
import com.firebase.chat.viewmodels.MessageViewModel;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MessageActivity extends AppCompatActivity {

    private static final List<MessageActivity> activeMessageActivities = new ArrayList<>();
    private ActivityMessageBinding activityMessageBinding;
    private MessageService messageService;
    private ChatService chatService;
    private MessageViewModel messageViewModel;

    private String chatId;
    private User user;
    private Chat chat;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (activeMessageActivities.size() == 0) {
            activeMessageActivities.add(this);
        } else {
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initializeServices();
        getIntentData();
        setupViews();
        fetchMessages();
        getChatData();
        setClickListeners();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        activeMessageActivities.remove(this);
    }

    private void initializeServices() {
        messageService = MessageService.getInstance();
        chatService = ChatService.getInstance();
        userService = UserService.getInstance();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null && extras.containsKey("chatId")) {
                chatId = extras.getString("chatId");
            }
            if (chatId == null) {
                finish();
            }
        }
    }

    private void setupViews() {
        activityMessageBinding = DataBindingUtil.setContentView(this, R.layout.activity_message);
        messageViewModel = new MessageViewModel(this);
        activityMessageBinding.setMessageViewModel(messageViewModel);
    }

    private void getChatData() {
        chatService.getChatById(chatId, (chatDocument, e) -> {
            if (e != null) {
                Toast.makeText(this, "Failed to fetch chat data", Toast.LENGTH_SHORT).show();
                return;
            }

            if (chatDocument.exists()) {
                chat = chatDocument.toObject(Chat.class);
                chat.setId(chatDocument.getId());
                String userId = chat.getParticipants().stream().filter(id -> !id.equals(AuthService.getInstance().getCurrentUser().getUid())).collect(Collectors.toList()).get(0);
                getUserData(userId);
            } else {
                Toast.makeText(this, "Chat not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserData(String userId) {
        userService.getUserById(userId, (userDocument, e) -> {
            if (e != null) {
                Toast.makeText(this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                return;
            }

            if (userDocument.exists()) {
                user = userDocument.toObject(User.class);

                Picasso.get().load(user.getPhotoUrl()).into(activityMessageBinding.MessageActivityImageViewPhotoUrl);
                activityMessageBinding.MessageActivityTextViewDisplayName.setText(user.getDisplayName());

            } else {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchMessages() {
        messageService.getAllMessagesByChatId(chatId, (queryDocumentSnapshots, e) -> {
            if (e != null) {
                Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                return;
            }

            ObservableList<Message> messages = new ObservableArrayList<>();

            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                handleDocumentChanges(messages, document);
            }

            messageViewModel.setMessageItems(messages);
        });
    }

    private void setClickListeners() {
        activityMessageBinding.ChatActivityImageButtonBack.setOnClickListener(v -> finish());
        activityMessageBinding.MessageActivityImageButtonSendMess.setOnClickListener(v -> sendMessage());
        activityMessageBinding.MessageActivityEditTextInputMess.setOnClickListener(v -> handleKeyboard());
        activityMessageBinding.MessageActivityFrameLayout.setOnClickListener(v -> messageViewModel.hideKeyboard(MessageActivity.this, v));
    }

    private void sendMessage() {
        String content = activityMessageBinding.MessageActivityEditTextInputMess.getText().toString();
        if (content == null || content.equals("")) {
            return;
        }
        String senderId = AuthService.getInstance().getCurrentUser().getUid();

        Message message = new Message();
        message.setSenderId(senderId);
        message.setContent(content);
        message.setTimestamp(Timestamp.now());
        message.setChatId(chatId);

        chatService.sendMessage(chatId, message);

        activityMessageBinding.MessageActivityEditTextInputMess.setText("");
    }

    private void handleKeyboard() {
        // Logic for handling keyboard
    }

    private void handleDocumentChanges(List<Message> messages, QueryDocumentSnapshot document) {
        Message message = document.toObject(Message.class);
        Date newMessageDate = message.getTimestamp().toDate();
        int index = -1;

        for (int i = 0; i < messages.size(); i++) {
            Date currentMessageDate = messages.get(i).getTimestamp().toDate();
            if (currentMessageDate != null && currentMessageDate.after(newMessageDate)) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            messages.add(index, message);
        } else {
            messages.add(message);
        }

        document.getReference().addSnapshotListener((snapshot, error) -> {
            if (error != null) {
                return;
            }

            if (snapshot != null && snapshot.exists()) {
                Message updatedMessage = snapshot.toObject(Message.class);
                Date updatedMessageDate = updatedMessage.getTimestamp().toDate();

                int updatedIndex = -1;
                for (int i = 0; i < messages.size(); i++) {
                    Date currentMessageDate = messages.get(i).getTimestamp().toDate();
                    if (currentMessageDate != null && currentMessageDate.equals(updatedMessageDate)) {
                        updatedIndex = i;
                        break;
                    }
                }

                if (updatedIndex != -1) {
                    messages.set(updatedIndex, updatedMessage);
                }
            }
        });
    }


}

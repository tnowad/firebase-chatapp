package com.firebase.chat.services;

import com.firebase.chat.models.Chat;
import com.firebase.chat.models.Message;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatService {
    public static ChatService instance;
    private final FirebaseFirestore firestore;
    private final CollectionReference chatsRef;
    private final AuthService authService;
    private final MessageService messageService;

    public ChatService() {
        firestore = FirebaseFirestore.getInstance();
        chatsRef = firestore.collection("chats");
        authService = AuthService.getInstance();
        messageService = MessageService.getInstance();
    }

    public static ChatService getInstance() {
        if (instance == null) {
            instance = new ChatService();
        }
        return instance;
    }

    public void getAllChatsForCurrentUser(EventListener<QuerySnapshot> listener) {
        Query query = chatsRef.whereArrayContains("participants", authService.getCurrentUser().getUid());
        query.addSnapshotListener(listener);
    }


    public Task<DocumentReference> createChat(List<String> participants) {

        Query query = chatsRef.whereArrayContains("participants", participants);

        return query.get().continueWithTask(task -> {
            QuerySnapshot querySnapshot = task.getResult();
            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                return Tasks.forResult(querySnapshot.getDocuments().get(0).getReference());
            } else {
                Map<String, Object> newChatData = new HashMap<>();
                newChatData.put("participants", participants);

                return chatsRef.add(newChatData);
            }
        });
    }

    public void findChatByParticipants(String senderId, String receiverId, EventListener<Chat> eventListener) {
        ChatService.getInstance().getAllChatsForCurrentUser((value, error) -> {
            for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                Chat chat = documentSnapshot.toObject(Chat.class);
                chat.setId(documentSnapshot.getId());

                if (chat.getParticipants().containsAll(Arrays.asList(senderId, receiverId))) {
                    eventListener.onEvent(chat, null);
                    return;
                }
            }
            eventListener.onEvent(null, null); // No chat found
        });
    }


    public void getChatById(String chatId, EventListener<DocumentSnapshot> eventListener) {
        DocumentReference chatRef = chatsRef.document(chatId);
        chatRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists()) {
                    eventListener.onEvent(documentSnapshot, null);
                } else {
                    eventListener.onEvent(null, null); // Chat not found
                }
            } else {
                eventListener.onEvent(null, (FirebaseFirestoreException) task.getException());
            }
        });
    }

    public void sendMessage(String chatId, Message message) {
        DocumentReference chatRef = chatsRef.document(chatId);

        Map<String, Object> messageData = new HashMap<>();
        messageData.put("chatId", chatId);
        messageData.put("content", message.getContent());
        messageData.put("senderId", message.getSenderId());
        messageData.put("timestamp", message.getTimestamp());

        messageService.getMessagesRef().add(messageData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                chatRef.update("lastMessageId", task.getResult().getId());
            }
        });

    }

}

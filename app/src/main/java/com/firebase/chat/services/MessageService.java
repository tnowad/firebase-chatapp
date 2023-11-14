package com.firebase.chat.services;

import com.firebase.chat.exceptions.MessageNotFoundException;
import com.firebase.chat.models.Message;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class MessageService {

    private static MessageService instance;
    private final FirebaseFirestore firestore;
    private final CollectionReference messagesRef;

    public MessageService() {
        firestore = FirebaseFirestore.getInstance();
        messagesRef = firestore.collection("messages");
    }

    public static MessageService getInstance() {
        if (instance == null) {
            instance = new MessageService();
        }
        return instance;
    }

    public CollectionReference getMessagesRef() {
        return messagesRef;
    }

    public Task<Message> getMessageById(String messageId) {
        TaskCompletionSource<Message> taskCompletionSource = new TaskCompletionSource<>();

        DocumentReference messageDocRef = messagesRef.document(messageId);
        messageDocRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            Message message = documentSnapshot.toObject(Message.class);
                            taskCompletionSource.setResult(message);
                        } else {
                            taskCompletionSource.setException(new MessageNotFoundException());
                        }
                    } else {
                        taskCompletionSource.setException(task.getException());
                    }
                });

        return taskCompletionSource.getTask();
    }


    public void getAllMessagesByChatId(String chatId, EventListener<QuerySnapshot> listener) {
        Query chatMessagesQuery = messagesRef.whereEqualTo("chatId", chatId).orderBy("timestamp", Query.Direction.ASCENDING);
        chatMessagesQuery.addSnapshotListener(listener);
    }


}

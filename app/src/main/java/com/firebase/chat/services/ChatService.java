package com.firebase.chat.services;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ChatService {
    public static ChatService instance;
    private final FirebaseFirestore firestore;
    private final CollectionReference chatsRef;
    private final AuthService authService;

    public ChatService() {
        firestore = FirebaseFirestore.getInstance();
        chatsRef = firestore.collection("chats");
        authService = AuthService.getInstance();
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

}

package com.firebase.chat.services;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}

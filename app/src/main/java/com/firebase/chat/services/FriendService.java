package com.firebase.chat.services;

import com.firebase.chat.models.Friend;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class FriendService {

    private static FriendService instance;
    FirebaseFirestore firestore;
    CollectionReference friendsRef;

    public FriendService() {
        firestore = FirebaseFirestore.getInstance();
        friendsRef = firestore.collection("friends");
    }

    public static FriendService getInstance() {
        if (instance == null) {
            instance = new FriendService();
        }
        return instance;
    }

    public Task<DocumentReference> sendFriendRequest(String senderId, String receiverId) {
        Friend friend = new Friend(senderId, receiverId, "pending");
        TaskCompletionSource<DocumentReference> taskCompletionSource = new TaskCompletionSource<>();

        friendsRef.add(friend)
                .addOnSuccessListener(documentReference -> {
                    taskCompletionSource.setResult(documentReference);
                })
                .addOnFailureListener(e -> {
                    taskCompletionSource.setException(e);
                });

        return taskCompletionSource.getTask();
    }
}

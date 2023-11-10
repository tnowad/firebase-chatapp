package com.firebase.chat.services;

import com.firebase.chat.models.Friend;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class FriendService {

    private static FriendService instance;
    private final AuthService authService;
    FirebaseFirestore firestore;
    CollectionReference friendsRef;

    public FriendService() {
        firestore = FirebaseFirestore.getInstance();
        authService = AuthService.getInstance();
        friendsRef = firestore.collection("friends");
    }

    public static FriendService getInstance() {
        if (instance == null) {
            instance = new FriendService();
        }
        return instance;
    }

    public void getAllPendingRequestsForCurrentUser(EventListener<QuerySnapshot> listener) {

        String uid = authService.getCurrentUser().getUid();
        Query query = friendsRef.whereEqualTo("status", "pending")
                .where(Filter.and(
                        Filter.or(
                                Filter.equalTo("senderId", uid),
                                Filter.equalTo("receiverId", uid)
                        )
                ));
        query.addSnapshotListener(listener);
    }

    public void getAllAcceptedRequestsForCurrentUser(EventListener<QuerySnapshot> listener) {
        String uid = authService.getCurrentUser().getUid();
        Query query = friendsRef.whereEqualTo("status", "accepted")
                .where(Filter.and(
                        Filter.or(
                                Filter.equalTo("senderId", uid),
                                Filter.equalTo("receiverId", uid)
                        )
                ));
        query.addSnapshotListener(listener);
    }

    public Task<DocumentReference> sendFriendRequest(String senderId, String receiverId) {
        Friend friend = new Friend(senderId, receiverId, "pending");
        TaskCompletionSource<DocumentReference> taskCompletionSource = new TaskCompletionSource<>();

        String documentId = senderId + "_" + receiverId;

        friendsRef.document(documentId).set(friend)
                .addOnSuccessListener(aVoid -> {
                    taskCompletionSource.setResult(friendsRef.document(documentId));
                })
                .addOnFailureListener(e -> {
                    taskCompletionSource.setException(e);
                });

        return taskCompletionSource.getTask();
    }

    public Task<Void> unfriend(String senderId, String receiverId) {
        TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<>();

        String documentId1 = senderId + "_" + receiverId;
        String documentId2 = receiverId + "_" + senderId;

        friendsRef.document(documentId1).delete()
                .addOnSuccessListener(aVoid -> {
                    taskCompletionSource.setResult(null);
                })
                .addOnFailureListener(e -> {
                    friendsRef.document(documentId2).delete()
                            .addOnSuccessListener(aVoid -> {
                                taskCompletionSource.setResult(null);
                            })
                            .addOnFailureListener(e2 -> {
                                taskCompletionSource.setException(e2);
                            });
                });

        return taskCompletionSource.getTask();
    }

    public Task<Void> acceptFriendRequest(String senderId, String receiverId) {
        TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<>();

        String documentId1 = senderId + "_" + receiverId;
        String documentId2 = receiverId + "_" + senderId;


        Map<String, Object> updateData = new HashMap<>();
        updateData.put("status", "accepted");

        friendsRef.document(documentId1).update(updateData)
                .addOnSuccessListener(aVoid -> {
                    taskCompletionSource.setResult(null);
                })
                .addOnFailureListener(e -> {
                    friendsRef.document(documentId2).update(updateData)
                            .addOnSuccessListener(aVoid -> {
                                taskCompletionSource.setResult(null);
                            })
                            .addOnFailureListener(e2 -> {
                                taskCompletionSource.setException(e2);
                            });
                });

        return taskCompletionSource.getTask();
    }
}

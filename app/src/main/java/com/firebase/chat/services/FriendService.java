package com.firebase.chat.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.firebase.chat.exceptions.UserNotFoundException;
import com.firebase.chat.models.Friend;
import com.firebase.chat.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

    public Task<Friend> getUserIsFriend(String ortherUid) {
        TaskCompletionSource<Friend> taskCompletionSource = new TaskCompletionSource<>();

        String uid = authService.getCurrentUser().getUid();
        Query query = friendsRef.whereEqualTo("status", "accepted")
                .where(Filter.and(
                        Filter.or(
                                Filter.equalTo("senderId", uid),
                                Filter.equalTo("receiverId", uid)
                        ),
                        Filter.or(
                                Filter.equalTo("senderId", ortherUid),
                                Filter.equalTo("receiverId", ortherUid)
                        )
                ));
        query.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot.size() == 1) {
                            Friend friend = querySnapshot.getDocuments().get(0).toObject(Friend.class);
                            taskCompletionSource.setResult(friend);
                        } else {
                            taskCompletionSource.setException(new UserNotFoundException());
                        }
                    } else {
                        taskCompletionSource.setException(task.getException());
                    }
                });

        return taskCompletionSource.getTask();
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

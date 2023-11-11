package com.firebase.chat.services;

import com.firebase.chat.exceptions.UserNotFoundException;
import com.firebase.chat.models.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class UserService {
    private static UserService instance;
    FirebaseFirestore firestore;
    CollectionReference usersRef;

    public UserService() {
        firestore = FirebaseFirestore.getInstance();
        usersRef = firestore.collection("users");
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public Task<User> getUserByUid(String uid) {
        TaskCompletionSource<User> taskCompletionSource = new TaskCompletionSource<>();

        DocumentReference userDocRef = usersRef.document(uid);
        userDocRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);
                            taskCompletionSource.setResult(user);
                        } else {
                            taskCompletionSource.setException(new UserNotFoundException());
                        }
                    } else {
                        taskCompletionSource.setException(task.getException());
                    }
                });

        return taskCompletionSource.getTask();
    }

    public void getAllUserByEmail(String email, EventListener<QuerySnapshot> listener) {
        usersRef.whereEqualTo("email", email).addSnapshotListener(listener);
    }

    public void getUserById(String userId, EventListener<DocumentSnapshot> eventListener) {
        // Assuming you have a users collection in Firestore
        CollectionReference usersRef = firestore.collection("users");

        DocumentReference userRef = usersRef.document(userId);
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists()) {
                    eventListener.onEvent(documentSnapshot, null);
                } else {
                    eventListener.onEvent(null, null); // User not found
                }
            } else {
                eventListener.onEvent(null, (FirebaseFirestoreException) task.getException());
            }
        });
    }
}


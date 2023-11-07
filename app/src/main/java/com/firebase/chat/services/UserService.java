package com.firebase.chat.services;

import com.firebase.chat.exceptions.UserNotFoundException;
import com.firebase.chat.models.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
}


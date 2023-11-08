package com.firebase.chat.services;

import android.util.Log;

import com.firebase.chat.exceptions.UserNotFoundException;
import com.firebase.chat.models.User;
import com.firebase.chat.utils.Utils;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public void getUserByEmail(String email) {
        List<User> result = new ArrayList<>();

        Utils.LIST_USER.clear();
        Query userDocRef = usersRef.whereEqualTo("email", email);
        userDocRef.get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    for (DocumentSnapshot snapshot : querySnapshot.getDocuments()) {
                        Log.d("get", snapshot.getData() + "");
                        Map<String, Object> map = snapshot.getData();
                        User user = new User(map.get("uid").toString(), map.get("displayName").toString(), map.get("email").toString(), map.get("photoUrl").toString());
                        Utils.LIST_USER.add(user);
                        Log.d("demo", Utils.LIST_USER.size() + "");
                    }

                } else {
                    Log.d("error", task.getException() + "");
                }
            });
    }
}


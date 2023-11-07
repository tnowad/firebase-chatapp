package com.firebase.chat.services;

import com.firebase.chat.Interfaces.OnUserFetchListener;
import com.firebase.chat.models.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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

    public void getUserByUid(String uid, OnUserFetchListener listener) {
        DocumentReference userDocRef = usersRef.document(uid);
        userDocRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        listener.onUserFetchSuccess(user);
                    } else {
                        listener.onUserNotFound();
                    }
                })
                .addOnFailureListener(e -> {
                    listener.onUserFetchError(e);
                });
    }
}

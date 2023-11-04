package com.firebase.chat.services;

import com.firebase.chat.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthService {
    private static AuthService instance;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore firestore;

    public AuthService() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firestore = FirebaseFirestore.getInstance();
    }

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }


    public Task<Void> createAccountIfNotExists(String uid, String email, String displayName, String photoUrl) {
        DocumentReference userRef = firestore.collection("users").document(uid);

        return userRef.get().continueWithTask(task -> {
            DocumentSnapshot document = task.getResult();
            if (document.exists()) {
                return null;
            } else {
                User newUser = new User(uid, email, displayName, photoUrl);
                return userRef.set(newUser);
            }
        });
    }
}

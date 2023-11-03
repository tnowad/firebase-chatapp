package com.firebase.chat.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.firebase.chat.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;

public class UserService extends BaseService<User> {
    public UserService() {
        super("User");
    }

    public void insert(String documentId, User item) {
        DocumentReference userRef = collection.document(documentId);
        userRef.set(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("test", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("test", "Error writing document", e);
                    }
                });
    }

    public void insertFriend(String collectionId, String friend) {
        DocumentReference washingtonRef = firestore.collection("User").document(collectionId);

        washingtonRef.update("regions", FieldValue.arrayUnion(friend));
    }
}

package com.firebase.chat.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.firebase.chat.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;

public class DAL_User extends Connection {
    public DAL_User() {
        super();
    }

    public void insert(String collectionId, User item) {
        DocumentReference userRef = fStore.collection("User").document(collectionId);
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
        DocumentReference washingtonRef = fStore.collection("User").document(collectionId);

        washingtonRef.update("regions", FieldValue.arrayUnion(friend));
    }
}

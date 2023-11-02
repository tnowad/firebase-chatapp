package com.firebase.chat.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.firebase.chat.model.Chat;
import com.firebase.chat.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;

public class DAL_Chat extends Connection {
    public DAL_Chat() {
        super();
    }

    public void insert(String collectionId, Chat item) {

        DocumentReference washingtonRef = fStore.collection("User").document(collectionId);
        washingtonRef.update("listChat", FieldValue.arrayUnion(item));
    }
}

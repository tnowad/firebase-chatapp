package com.firebase.chat.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.firebase.chat.model.Chat;
import com.firebase.chat.model.Message;
import com.firebase.chat.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;

public class DAL_Message extends Connection {
    public DAL_Message() {
        super();
    }

    public void insert(Message item) {

        fStore.collection("Message")
            .add(item)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d("insert", "DocumentSnapshot written with ID: " + documentReference.getId());
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("insert", "Error adding document", e);
                }
            });
    }


}

package com.firebase.chat.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.firebase.chat.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

public class UserService extends BaseService<User> {
    public UserService() {
        super("users");
    }

    public String getDisplayName(String documentId) {
        final String[] ans = {""};
        collection.document(documentId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("displayname", "DocumentSnapshot data: " + document.getData());
                        ans[0] = document.getData().get("displayName") + "";
                    } else {
                        Log.d("displayname", "No such document");
                    }
                } else {
                    Log.d("error", "get failed with ", task.getException());
                }
            }
        });
        return ans[0];
    }

    public void insertFriend(String collectionId, String friend) {
        DocumentReference washingtonRef = collection.document(collectionId);

        washingtonRef.update("listFriend", FieldValue.arrayUnion(friend));
    }
}

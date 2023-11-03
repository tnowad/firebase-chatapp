package com.firebase.chat.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.firebase.chat.model.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageService extends BaseService {
    public MessageService() {
        super("Message");
    }

    public List<Message> getList(String collectionId) {
        List<Message> result = new ArrayList<>();

        CollectionReference messageRef = firestore.collection("Message");

        messageRef.whereArrayContainsAny("user1", Collections.singletonList(collectionId));
        messageRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("get", document.getId() + " => " + document.getData());
                                Map<String, Object> message = new HashMap<>();
                                message = document.getData();
                                result.add(new Message(message.get("user1") + "", message.get("user2") + "", message.get("lastMess") + ""));

                            }
                        } else {
                            Log.d("get", "Error getting documents: ", task.getException());
                        }
                    }
                });
        messageRef.whereArrayContainsAny("user2", Collections.singletonList(collectionId));
        messageRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("get", document.getId() + " => " + document.getData());
                                Map<String, Object> message = new HashMap<>();
                                message = document.getData();
                                result.add(new Message(message.get("user1") + "", message.get("user2") + "", message.get("lastMess") + ""));

                            }
                        } else {
                            Log.d("get", "Error getting documents: ", task.getException());
                        }
                    }
                });
        Log.d("size", result.size() + "");
        return result;
    }

    public void insert(Message item) {

        firestore.collection("Message")
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

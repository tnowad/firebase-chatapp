package com.firebase.chat.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.firebase.chat.model.Message;
import com.firebase.chat.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageService extends BaseService {
    List<Message> result;

    public MessageService() {
        super("Message");
    }

    public void getList(String email) {
        result = new ArrayList<>();
        collection
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> message = new HashMap<>();
                                message = document.getData();
                                if ((message.get("user1") + "").equals(email) || (message.get("user2") + "").equals(email)) {
                                    //Log.d("success", document.getId() + " => " + document.getData());
                                    String timestamp = message.get("time") + "";
                                    Log.d("success", timestamp.split(", ")[0].replace("Timestamp(seconds=", "") + "");
                                    Message newMess = new Message(message.get("user1") + "", message.get("user2") + "", message.get("lastMess") + "");
                                    Utils.LIST_MESSAGE.add(newMess);
                                }
                            }
                        } else {
                            Log.d("error", "Error getting documents: ", task.getException());
                        }
                    }
                });

        Log.d("size", result.size() + "");
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

package com.firebase.chat.services;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.firebase.chat.models.Message;
import com.firebase.chat.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageService extends BaseService {
    List<Message> result;

    public MessageService() {
        super("Message");
        collection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("error", "listen:error", e);
                    return;
                }

                for (DocumentChange dc : snapshots.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            Log.d("add", "New mess: " + dc.getDocument().getData());
                            break;
                        case MODIFIED:
                            Log.d("mod", "Modified mess: " + dc.getDocument().getData());
                            break;
                        case REMOVED:
                            Log.d("remove", "Removed mess: " + dc.getDocument().getData());
                            break;
                    }
                }

            }
        });

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
                                    //Log.d("success", timestamp.split(", ")[0].replace("Timestamp(seconds=", "") + "");
                                    //Log.d("success", timestamp.split(", ")[1].replace("nanoseconds=", "").replace(")", "") + "");
                                    Date netDate = new Date(Long.parseLong(timestamp.split(", ")[0].replace("Timestamp(seconds=", "")) * 1000);
                                    Message newMess = new Message(message.get("user1") + "", message.get("user2") + "", message.get("lastMess") + "", Utils.TIME_FORMAT.format(netDate));
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

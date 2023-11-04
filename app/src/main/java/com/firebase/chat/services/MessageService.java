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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MessageService extends BaseService {
    private final UserService userService = new UserService();
    private final FirebaseUser firebaseUser = AuthService.getInstance().getCurrentUser();

    public MessageService() {
        super("messages");
        collection.where(Filter.or(Filter.equalTo("user1", Utils.CURRENT_UID), Filter.equalTo("user2", Utils.CURRENT_UID))).orderBy("time", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("error", "listen:error", e);
                    return;
                }
                Utils.LIST_MESSAGE.clear();
                for (DocumentSnapshot snapshot : snapshots.getDocuments()) {
                    Map<String, Object> message = new HashMap<>();
                    message = snapshot.getData();
                    //if ((message.get("user1") + "").equals(Utils.CURRENT_EMAIL) || (message.get("user2") + "").equals(Utils.CURRENT_EMAIL)) {
                    //Log.d("success", document.getId() + " => " + document.getData());
                    String timestamp = message.get("time") + "";
                    //Log.d("success", timestamp.split(", ")[0].replace("Timestamp(seconds=", "") + "");
                    //Log.d("success", timestamp.split(", ")[1].replace("nanoseconds=", "").replace(")", "") + "");
                    Date netDate = new Date(Long.parseLong(timestamp.split(", ")[0].replace("Timestamp(seconds=", "")) * 1000);
                    Message newMess = new Message(message.get("user1") + "", message.get("user2") + "", message.get("lastMess") + "", Utils.TIME_FORMAT.format(netDate), "");
                    newMess.setDisplayName(userService.getDisplayName(newMess.getUser2()));
                    Utils.LIST_MESSAGE.add(newMess);
                    //}
                }
            }
        });
    }


    public void getList() {
        collection.where(Filter.or(Filter.equalTo("user1", Utils.CURRENT_UID), Filter.equalTo("user2", Utils.CURRENT_UID))).orderBy("user1", Query.Direction.ASCENDING).orderBy("time", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> message = new HashMap<>();
                                message = document.getData();
                                //if ((message.get("user1") + "").equals(Utils.CURRENT_EMAIL) || (message.get("user2") + "").equals(Utils.CURRENT_EMAIL)) {
                                //Log.d("success", document.getId() + " => " + document.getData());
                                String timestamp = message.get("time") + "";
                                //Log.d("success", timestamp.split(", ")[0].replace("Timestamp(seconds=", "") + "");
                                //Log.d("success", timestamp.split(", ")[1].replace("nanoseconds=", "").replace(")", "") + "");
                                Date netDate = new Date(Long.parseLong(timestamp.split(", ")[0].replace("Timestamp(seconds=", "")) * 1000);
                                Message newMess = new Message(message.get("user1") + "", message.get("user2") + "", message.get("lastMessage") + "", Utils.TIME_FORMAT.format(netDate), "");
                                newMess.setDisplayName(userService.getDisplayName(newMess.getUser2()));
                                Utils.LIST_MESSAGE.add(newMess);
                                //}
                            }
                        } else {
                            Log.d("error", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void insert(Message item) {
        Map<String, Object> message = new HashMap<>();
        message.put("user1", item.getUser1());
        message.put("user2", item.getUser2());
        message.put("lastMessage", item.getLastMessage());
        message.put("time", FieldValue.serverTimestamp());
        collection.add(item)
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

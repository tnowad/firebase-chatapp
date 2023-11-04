package com.firebase.chat.service;

import com.firebase.chat.model.Chat;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;

public class ChatService extends BaseService {
    public ChatService() {
        super("Chat");
    }

    public void insert(String messageId, Chat item) {

        DocumentReference washingtonRef = firestore.collection("User").document("collectionId");
        washingtonRef.update("listChat", FieldValue.arrayUnion(item));
    }
}

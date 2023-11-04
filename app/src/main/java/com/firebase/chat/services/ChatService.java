package com.firebase.chat.services;

import com.firebase.chat.models.Chat;
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

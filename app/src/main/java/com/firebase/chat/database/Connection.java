package com.firebase.chat.database;

import com.google.firebase.firestore.FirebaseFirestore;

public class Connection {
    protected FirebaseFirestore fStore;

    public Connection() {
        fStore = FirebaseFirestore.getInstance();
    }
}

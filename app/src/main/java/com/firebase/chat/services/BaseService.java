package com.firebase.chat.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public abstract class BaseService<T> {
    protected FirebaseFirestore firestore;
    protected CollectionReference collection;

    public BaseService(String collectionName) {
        firestore = FirebaseFirestore.getInstance();
        collection = firestore.collection(collectionName);
    }

    public Task<DocumentReference> add(T entity) {
        return collection.add(entity);
    }

    public Task<Void> update(String documentId, T entity) {
        return collection.document(documentId).set(entity);
    }

    public Task<Void> delete(String documentId) {
        return collection.document(documentId).delete();
    }

    public Task<T> get(String documentId, Class<T> entityClass) {
        return collection.document(documentId).get().continueWith(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                return task.getResult().toObject(entityClass);
            } else {
                throw new RuntimeException("Document not found");
            }
        });
    }

    public Task<QuerySnapshot> getAll() {
        return collection.get();
    }

    protected CollectionReference getCollection() {
        return collection;
    }
}

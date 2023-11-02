package com.firebase.chat.viewmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.adapter.MessageItem;
import com.firebase.chat.fragment.MessageFragment;
import com.firebase.chat.model.Message;
import com.firebase.chat.model.User;
import com.firebase.chat.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

public class MessageViewModel {
    private Context context;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    public ObservableList<Message> listMessage = new ObservableArrayList<>();

    private static MessageItem adapter;

    public MessageViewModel(Context context) {
        this.context = context;
        getListMessage();
    }

    public void getListMessage() {
        if (listMessage != null) {
            listMessage.clear();
        }
        else {
            listMessage = new ObservableArrayList<>();
        }
//        List<Message> list = new ArrayList<>();
//        list.add(new Message("Ben", "Phat", "Hello"));
//        list.add(new Message("Ben", "Dat", "Hello"));
//        list.add(new Message("Ben", "Tuan", "Hello"));
//        list.add(new Message("Ben", "Dai", "Hello"));
//        listMessage.addAll(list);

        Utils.LIST_MESSAGE = new ObservableArrayList<>();
        listMessage = Utils.LIST_MESSAGE;
        Utils.LIST_MESSAGE.add(new Message("Ben", "Phat", "Hello"));
        Utils.LIST_MESSAGE.add(new Message("Ben", "Dat", "Hello"));
        Utils.LIST_MESSAGE.add(new Message("Ben", "Tuan", "Hello"));
        Utils.LIST_MESSAGE.add(new Message("Ben", "Dai", "Hello"));
        //listMessage.addAll(Utils.LIST_MESSAGE);
    }

    @BindingAdapter({"list_mess"})
    public static void loadListMess(RecyclerView recyclerView, ObservableList<Message> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new MessageItem(list);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void clear() {
        if (listMessage != null) {
            listMessage.clear();
        }
        adapter.notifyDataSetChanged();
    }

    public void release() {
        context = null;
    }

    public void onClickDemo() {
        String email = "benlun99999@gmail.com";
        String pass = "123456";
        fAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser curr_user = fAuth.getCurrentUser();

                        //List<String> listFriend = new ArrayList<>();
//                        final String[] uid = {""};
//                        Query docRef = fStore.collection("User").whereEqualTo("username", "benlun99");
//                        docRef.get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                            Log.d("get", document.getId() + " => " + document.getData());
//                                            uid[0] = document.getId();
//                                            Log.d("get", uid[0]);
//                                        }
//                                    } else {
//                                        Log.d("get", "Error getting documents: ", task.getException());
//                                    }
//                                }
//                            });

                        User newUser = new User(email, "benlun99", "", true, Arrays.asList("h9ulK8y9CtQjpeNOntAUQBd5nu42"));

                        DocumentReference userRef = fStore.collection("User").document(curr_user.getUid());
                        userRef.set(newUser)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("test", "DocumentSnapshot successfully written!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("test", "Error writing document", e);
                                    }
                                });


                    }
                    else {
                        Toast.makeText(context.getApplicationContext(), "Lỗi!!!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        //listMessage.add(new Message("Ben", "Dai", "Hello"));
    }
}

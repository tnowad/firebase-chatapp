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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class MessageViewModel {
    private Context context;
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
        List<Message> list = new ArrayList<>();
        list.add(new Message("Ben", "Phat", "Hello"));
        list.add(new Message("Ben", "Dat", "Hello"));
        list.add(new Message("Ben", "Tuan", "Hello"));
        list.add(new Message("Ben", "Dai", "Hello"));
        listMessage.addAll(list);
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
//        fAuth.createUserWithEmailAndPassword("benlun1201@gmail.com", "123456")
//            .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()) {
//                        FirebaseUser curr_user = fAuth.getCurrentUser();
//                        Log.d("demo", curr_user.getUid());
//                    }
//                    else {
//                        Toast.makeText(context.getApplicationContext(), "Lỗi!!!", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
        listMessage.add(new Message("Ben", "Dai", "Hello"));
    }
}

package com.firebase.chat.viewmodels;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.adapters.MessageItem;
import com.firebase.chat.models.Message;
import com.firebase.chat.services.MessageService;
import com.firebase.chat.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MessageViewModel {
    private static Context context;
    private static MessageItem adapter;
    private final MessageService messageService = new MessageService();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public ObservableList<Message> listMessage = new ObservableArrayList<>();

    public MessageViewModel(Context context) {
        MessageViewModel.context = context;
        getListMessage();
    }

    @BindingAdapter({"list_mess"})
    public static void loadListMess(RecyclerView recyclerView, ObservableList<Message> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new MessageItem(context, list);
        recyclerView.setAdapter(adapter);
    }

    public void getListMessage() {
        if (listMessage != null) {
            listMessage.clear();
        } else {
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
//        Utils.LIST_MESSAGE.add(new Message("Ben", "Phat", "Hello", new ArrayList<Chat>()));
//        Utils.LIST_MESSAGE.add(new Message("Ben", "Dat", "Hello", new ArrayList<Chat>()));
//        Utils.LIST_MESSAGE.add(new Message("Ben", "Tuan", "Hello", new ArrayList<Chat>()));
//        Utils.LIST_MESSAGE.add(new Message("Ben", "Dai", "Hello", new ArrayList<Chat>()));
        //listMessage.addAll(Utils.LIST_MESSAGE);

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            Utils.CURRENT_UID = currentUser.getEmail();
        }


        messageService.getList();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void clear() {
        if (listMessage != null) {
            listMessage.clear();
        }
        adapter.notifyDataSetChanged();
    }

    public void onClickDemo() {
//        String email = "benlun99999@gmail.com";
//        String pass = "123456";
//        fAuth.createUserWithEmailAndPassword(email, pass)
//            .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()) {
//                        FirebaseUser curr_user = fAuth.getCurrentUser();
//
//                        //List<String> listFriend = new ArrayList<>();
////                        final String[] uid = {""};
////                        Query docRef = fStore.collection("User").whereEqualTo("username", "benlun99");
////                        docRef.get()
////                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
////                                @Override
////                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
////                                    if (task.isSuccessful()) {
////                                        for (QueryDocumentSnapshot document : task.getResult()) {
////                                            Log.d("get", document.getId() + " => " + document.getData());
////                                            uid[0] = document.getId();
////                                            Log.d("get", uid[0]);
////                                        }
////                                    } else {
////                                        Log.d("get", "Error getting documents: ", task.getException());
////                                    }
////                                }
////                            });
//
//                        User newUser = new User(email, "benlun99", "", true, Arrays.asList("h9ulK8y9CtQjpeNOntAUQBd5nu42"));
//
//                        dalUser.insert(curr_user.getUid(), newUser);
//
//                    }
//                    else {
//                        Toast.makeText(context.getApplicationContext(), "Lỗi!!!", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
        //listMessage.add(new Message("Ben", "Dai", "Hello"));

        //Message newMessage = new Message("user1", "user2", "lastmess", "");

        //messageService.insert(newMessage);
    }
}

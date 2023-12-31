package com.firebase.chat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;

import com.firebase.chat.activities.ProfileActivity;
import com.firebase.chat.activities.SearchActivity;
import com.firebase.chat.databinding.FragmentChatBinding;
import com.firebase.chat.models.Chat;
import com.firebase.chat.services.AuthService;
import com.firebase.chat.services.ChatService;
import com.firebase.chat.viewmodels.ChatViewModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;


public class ChatFragment extends Fragment {
    private static final String TAG = ChatFragment.class.getSimpleName();

    private ChatViewModel chatViewModel;
    private ChatService chatService;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentChatBinding fragmentChatBinding = FragmentChatBinding.inflate(inflater, container, false);
        initViewModel(fragmentChatBinding);
        initChatService();
        setupListeners(fragmentChatBinding);
        return fragmentChatBinding.getRoot();
    }

    private void initViewModel(FragmentChatBinding fragmentChatBinding) {
        chatViewModel = new ChatViewModel(getActivity());
        fragmentChatBinding.setChatViewModel(chatViewModel);
        fragmentChatBinding.executePendingBindings();
    }

    private void initChatService() {
        chatService = ChatService.getInstance();
        observeChatDataChanges();
    }

    private void observeChatDataChanges() {
        chatService.getAllChatsForCurrentUser((queryDocumentSnapshots, e) -> {
            if (e != null) {
                // Handle errors
                return;
            }

            ObservableList<Chat> chatObservableList = new ObservableArrayList<>();

            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                handleDocumentChanges(chatObservableList, document);
            }

            chatViewModel.setChatItems(chatObservableList);
        });
    }

    private void handleDocumentChanges(ObservableList<Chat> chatObservableList, QueryDocumentSnapshot document) {
        Chat chat = document.toObject(Chat.class);
        chat.setId(document.getId());
        chatObservableList.add(chat);

        document.getReference().addSnapshotListener((snapshot, error) -> {
            if (error != null) {
                return;
            }

            if (snapshot != null && snapshot.exists()) {
                Chat updatedChat = snapshot.toObject(Chat.class);
                updatedChat.setId(snapshot.getId());
                int index = chatObservableList.indexOf(chat);
                if (index != -1) {
                    chatObservableList.set(index, updatedChat);
                }
            }
        });
    }

    private void setupListeners(FragmentChatBinding fragmentChatBinding) {
        fragmentChatBinding.ChatFragmentImageButtonSearch.setOnClickListener(v -> startSearchActivity());
        Picasso.get().load(AuthService.getInstance().getCurrentUser().getPhotoUrl()).into(fragmentChatBinding.ChatFragmentCircleImageViewPhotoUrl);
        fragmentChatBinding.ChatFragmentCircleImageViewPhotoUrl.setOnClickListener(v -> startProfileActivity());
    }

    private void startSearchActivity() {
        Intent searchActivity = new Intent(getActivity(), SearchActivity.class);
        startActivity(searchActivity);
        getActivity().overridePendingTransition(0, 0);
    }

    private void startProfileActivity() {
        Intent profileActivity = new Intent(getActivity(), ProfileActivity.class);
        String uid = AuthService.getInstance().getCurrentUser().getUid();
        profileActivity.putExtra("uid", uid);
        startActivity(profileActivity);
    }
}

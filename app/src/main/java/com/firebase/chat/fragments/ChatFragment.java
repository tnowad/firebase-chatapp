package com.firebase.chat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.firebase.chat.R;
import com.firebase.chat.activities.SearchActivity;
import com.firebase.chat.databinding.FragmentChatBinding;
import com.firebase.chat.viewmodels.ChatViewModel;


public class ChatFragment extends Fragment {
    private ChatViewModel chatViewModel;
    private ImageButton searchImageButton;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentChatBinding fragmentChatBinding = FragmentChatBinding.inflate(inflater, container, false);

        chatViewModel = new ChatViewModel(getActivity());
        fragmentChatBinding.setChatViewModel(chatViewModel);
        fragmentChatBinding.executePendingBindings();

        searchImageButton = fragmentChatBinding.getRoot().findViewById(R.id.ChatFragment_ImageButton_Search);

        searchImageButton.setOnClickListener(v -> {
            startSearchActivity();
        });


        return fragmentChatBinding.getRoot();
    }

    private void startSearchActivity() {
        Intent searchActivity = new Intent(getActivity(), SearchActivity.class);
        startActivity(searchActivity);
        getActivity().overridePendingTransition(0, 0);
    }

}
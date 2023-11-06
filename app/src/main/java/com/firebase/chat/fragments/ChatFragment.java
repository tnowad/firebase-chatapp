package com.firebase.chat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.firebase.chat.databinding.FragmentChatBinding;
import com.firebase.chat.viewmodels.MessageViewModel;


public class ChatFragment extends Fragment {
    private MessageViewModel mMessageViewModel;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentChatBinding fragmentChatBinding = FragmentChatBinding.inflate(inflater, container, false);

        mMessageViewModel = new MessageViewModel(getActivity());
        fragmentChatBinding.setMessageViewModel(mMessageViewModel);
        fragmentChatBinding.executePendingBindings();

        return fragmentChatBinding.getRoot();
    }

}
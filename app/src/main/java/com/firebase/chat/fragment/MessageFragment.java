package com.firebase.chat.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.chat.databinding.FragmentMessageBinding;
import com.firebase.chat.viewmodel.MessageViewModel;


public class MessageFragment extends Fragment {
    private MessageViewModel mMessageViewModel;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMessageBinding fragmentMessageBinding = FragmentMessageBinding.inflate(inflater, container, false);

        mMessageViewModel = new MessageViewModel(getActivity());
        fragmentMessageBinding.setMessageViewModel(mMessageViewModel);
        fragmentMessageBinding.executePendingBindings();

        return fragmentMessageBinding.getRoot();
    }

}
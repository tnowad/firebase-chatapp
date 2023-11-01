package com.firebase.chat.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.chat.R;
import com.firebase.chat.adapter.MessageItem;
import com.firebase.chat.databinding.FragmentMessageBinding;
import com.firebase.chat.model.Message;
import com.firebase.chat.viewmodel.MessageViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;


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

    @Override
    public void onStop() {
        mMessageViewModel.release();
        super.onStop();
    }
}
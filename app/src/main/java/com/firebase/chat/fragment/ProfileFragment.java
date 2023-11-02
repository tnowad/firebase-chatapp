package com.firebase.chat.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.chat.R;
import com.firebase.chat.databinding.FragmentMessageBinding;
import com.firebase.chat.databinding.FragmentProfileBinding;
import com.firebase.chat.viewmodel.MessageViewModel;
import com.firebase.chat.viewmodel.ProfileViewModel;


public class ProfileFragment extends Fragment {
    private ProfileViewModel mProfileViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentProfileBinding fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false);

        mProfileViewModel = new ProfileViewModel();
        fragmentProfileBinding.setProfileViewModel(mProfileViewModel);
        fragmentProfileBinding.executePendingBindings();

        return fragmentProfileBinding.getRoot();
    }
}
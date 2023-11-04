package com.firebase.chat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.firebase.chat.databinding.FragmentProfileBinding;
import com.firebase.chat.viewmodels.ProfileViewModel;


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
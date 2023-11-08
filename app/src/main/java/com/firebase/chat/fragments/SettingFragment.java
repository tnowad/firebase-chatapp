package com.firebase.chat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.firebase.chat.R;
import com.firebase.chat.activities.GenerateQRCodeActivity;
import com.firebase.chat.activities.SplashActivity;
import com.firebase.chat.databinding.FragmentSettingBinding;
import com.google.firebase.auth.FirebaseAuth;


public class SettingFragment extends Fragment {
    FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentSettingBinding fragmentSettingBinding = FragmentSettingBinding.inflate(inflater, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        fragmentSettingBinding.SettingFragmentLayoutSignOut.setOnClickListener(v -> {
            firebaseAuth.signOut();
            Intent splashIntent = new Intent(getActivity(), SplashActivity.class);

            startActivity(splashIntent);
        });

        fragmentSettingBinding.SettingFragmentButtonGenerateQRCode.setOnClickListener(v -> {
            Intent generateQRCodeIntent = new Intent(getActivity(), GenerateQRCodeActivity.class);

            startActivity(generateQRCodeIntent);
        });

        return fragmentSettingBinding.getRoot();
    }
}
package com.firebase.chat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.firebase.chat.R;
import com.firebase.chat.activity.SplashActivity;
import com.google.firebase.auth.FirebaseAuth;


public class SettingFragment extends Fragment {

    Button signOutButton;
    FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        signOutButton = rootView.findViewById(R.id.SettingFragment_Button_SignOut);
        signOutButton.setOnClickListener(v -> {
            firebaseAuth.signOut();
            Intent splashIntent = new Intent(getActivity(), SplashActivity.class);

            startActivity(splashIntent);
        });

        return rootView;
    }
}
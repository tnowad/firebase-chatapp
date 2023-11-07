package com.firebase.chat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.firebase.chat.R;
import com.firebase.chat.activities.SplashActivity;
import com.google.firebase.auth.FirebaseAuth;


public class RequestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_request, container, false);

        return rootView;
    }
}
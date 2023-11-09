package com.firebase.chat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.firebase.chat.activities.GenerateQRCodeActivity;
import com.firebase.chat.activities.ProfileActivity;
import com.firebase.chat.activities.SplashActivity;
import com.firebase.chat.databinding.FragmentSettingBinding;
import com.firebase.chat.models.User;
import com.firebase.chat.services.AuthService;
import com.firebase.chat.services.UserService;
import com.squareup.picasso.Picasso;


public class SettingFragment extends Fragment {
    AuthService authService;
    UserService userService;

    FragmentSettingBinding fragmentSettingBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentSettingBinding = FragmentSettingBinding.inflate(inflater, container, false);

        authService = AuthService.getInstance();
        userService = UserService.getInstance();

        fragmentSettingBinding.SettingFragmentLayoutSignOut.setOnClickListener(v -> {
            authService.signOut();
            Intent splashIntent = new Intent(getActivity(), SplashActivity.class);

            startActivity(splashIntent);
        });

        fragmentSettingBinding.SettingFragmentButtonGenerateQRCode.setOnClickListener(v -> {
            Intent generateQRCodeIntent = new Intent(getActivity(), GenerateQRCodeActivity.class);

            generateQRCodeIntent.putExtra("content", authService.getCurrentUser().getUid());

            startActivity(generateQRCodeIntent);
        });


        fetchUserAndPopulateUI(authService.getCurrentUser().getUid());
        return fragmentSettingBinding.getRoot();
    }


    private void fetchUserAndPopulateUI(String uid) {
        userService.getUserByUid(uid)
                .addOnSuccessListener(user -> {
                    if (user != null) {
                        populateUserProfile(user);

                    }
                });
    }

    private void populateUserProfile(User user) {
        fragmentSettingBinding.SettingFragmentTextViewEmail.setText(user.getEmail());
        fragmentSettingBinding.SettingFragmentCircleImageViewPhotoUrl.setOnClickListener(v -> {
            Intent profileIntent = new Intent(this.getActivity(), ProfileActivity.class);
            profileIntent.putExtra("uid", authService.getCurrentUser().getUid());
            startActivity(profileIntent);
        });
        fragmentSettingBinding.SettingFragmentTextViewDisplayName.setText(user.getDisplayName());
        Picasso.get().load(user.getPhotoUrl()).into(
                fragmentSettingBinding.SettingFragmentCircleImageViewPhotoUrl
        );
    }
}
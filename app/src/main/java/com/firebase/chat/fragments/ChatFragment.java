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
import com.firebase.chat.activities.ProfileActivity;
import com.firebase.chat.activities.SearchActivity;
import com.firebase.chat.databinding.FragmentChatBinding;
import com.firebase.chat.services.AuthService;
import com.firebase.chat.viewmodels.ChatViewModel;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatFragment extends Fragment {
    private ChatViewModel chatViewModel;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentChatBinding fragmentChatBinding = FragmentChatBinding.inflate(inflater, container, false);

        chatViewModel = new ChatViewModel(getActivity());
        fragmentChatBinding.setChatViewModel(chatViewModel);
        fragmentChatBinding.executePendingBindings();

        fragmentChatBinding.ChatFragmentImageButtonSearch.setOnClickListener(v -> {
            startSearchActivity();
        });

        Picasso.get().load(AuthService.getInstance().getCurrentUser().getPhotoUrl()).into(fragmentChatBinding.ChatFragmentCircleImageViewPhotoUrl);

        fragmentChatBinding.ChatFragmentCircleImageViewPhotoUrl.setOnClickListener(v -> {
            startProfileActivity();
        });


        return fragmentChatBinding.getRoot();
    }

    private void startSearchActivity() {
        Intent searchActivity = new Intent(getActivity(), SearchActivity.class);
        startActivity(searchActivity);
        getActivity().overridePendingTransition(0, 0);
    }

    private void startProfileActivity() {
        Intent profileActivity = new Intent(getActivity(), ProfileActivity.class);
        String uid = AuthService.getInstance().getCurrentUser().getUid();
        profileActivity.putExtra("uid", uid);
        startActivity(profileActivity);
    }

}
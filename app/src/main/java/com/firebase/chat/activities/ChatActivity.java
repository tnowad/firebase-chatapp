package com.firebase.chat.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.firebase.chat.R;
import com.firebase.chat.databinding.ActivityChatBinding;
import com.firebase.chat.viewmodels.ChatViewModel;

public class ChatActivity extends AppCompatActivity {

    private ChatViewModel mChatViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ActivityChatBinding activityChatBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        mChatViewModel = new ChatViewModel();
        activityChatBinding.setChatViewMode(mChatViewModel);
        activityChatBinding.ChatActivityImageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
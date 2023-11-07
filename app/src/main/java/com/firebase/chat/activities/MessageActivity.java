package com.firebase.chat.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.firebase.chat.R;
import com.firebase.chat.databinding.ActivityMessageBinding;
import com.firebase.chat.viewmodels.MessageViewModel;

public class MessageActivity extends AppCompatActivity {

    private MessageViewModel messageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ActivityMessageBinding activityMessageBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_message);
        messageViewModel = new MessageViewModel();
        activityMessageBinding.setMessageViewModel(messageViewModel);

        activityMessageBinding.ChatActivityImageButtonBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(0, 0);
        });
    }
}
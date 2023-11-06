package com.firebase.chat.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.firebase.chat.R;
import com.firebase.chat.databinding.ActivityMessageBinding;
import com.firebase.chat.viewmodels.ChatViewModel;

public class MessageActivity extends AppCompatActivity {

    private ChatViewModel messageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ActivityMessageBinding activityMessageBinding = DataBindingUtil.setContentView(this, R.layout.activity_message);
        messageViewModel = new ChatViewModel();
        activityMessageBinding.setChatViewMode(messageViewModel);
        activityMessageBinding.ChatActivityImageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
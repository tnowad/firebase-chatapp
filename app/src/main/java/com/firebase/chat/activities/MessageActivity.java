package com.firebase.chat.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.firebase.chat.R;
import com.firebase.chat.databinding.ActivityMessageBinding;
import com.firebase.chat.utils.Utils;
import com.firebase.chat.viewmodels.MessageViewModel;

public class MessageActivity extends AppCompatActivity {

    private ActivityMessageBinding activityMessageBinding;
    private MessageViewModel messageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        activityMessageBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_message);
        messageViewModel = new MessageViewModel();
        activityMessageBinding.setMessageViewModel(messageViewModel);

        activityMessageBinding.ChatActivityImageButtonBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(0, 0);
        });

        activityMessageBinding.MessageActivityImageButtonSendMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageViewModel.sendMessage(activityMessageBinding.MessageActivityRecyclerViewListChat, activityMessageBinding.MessageActivityEditTextInputMess);
            }
        });

        activityMessageBinding.MessageActivityEditTextInputMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //messageViewModel.scrollWhenKeyboardShowed(activityMessageBinding.getRoot(),activityMessageBinding.MessageActivityRecyclerViewListChat);
            }
        });

        activityMessageBinding.MessageActivityFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageViewModel.hideKeyboard(MessageActivity.this, v);
            }
        });
    }

}
package com.firebase.chat.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.chat.R;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        finish();
    }
}

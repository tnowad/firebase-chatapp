package com.firebase.chat.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.chat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

//        TODO: Implement auth
        if (currentUser != null) {
            Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        } else {
            Intent chatIntent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(chatIntent);
            finish();
        }
    }
}

package com.firebase.chat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.firebase.chat.R;

public class ProfileActivity extends AppCompatActivity {

    ImageButton backImageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        backImageButton = findViewById(R.id.ProfileActivity_ImageButton_Back);
        backImageButton.setOnClickListener(v -> finish());
    }
}
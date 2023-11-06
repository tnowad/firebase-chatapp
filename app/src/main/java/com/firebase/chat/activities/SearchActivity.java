package com.firebase.chat.activities;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.chat.R;

public class SearchActivity extends AppCompatActivity {

    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        backButton = findViewById(R.id.SearchActivity_ImageButton_Back);

        backButton.setOnClickListener(v -> {
            finish();
            overridePendingTransition(0, 0);
        });
    }
}
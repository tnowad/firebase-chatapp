package com.firebase.chat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.chat.R;

public class SearchActivity extends AppCompatActivity {

    private ImageButton backImageButton;
    private ImageButton scanQRImageButton;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        backImageButton = findViewById(R.id.SearchActivity_ImageButton_Back);
        searchEditText = findViewById(R.id.SearchActivity_EditText_Search);
        scanQRImageButton = findViewById(R.id.SearchActivity_ImageButton_ScanQR);
        searchEditText.requestFocus();
        scanQRImageButton.setOnClickListener(v -> {
            Intent scanQRIntent = new Intent(this, QRCodeScannerActivity.class);
            startActivity(scanQRIntent);
        });

        backImageButton.setOnClickListener(v -> {
            finish();
            overridePendingTransition(0, 0);
        });
    }

}
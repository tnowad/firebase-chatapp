package com.firebase.chat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.firebase.chat.R;
import com.firebase.chat.databinding.ActivityMessageBinding;
import com.firebase.chat.databinding.ActivitySearchBinding;
import com.firebase.chat.viewmodels.SearchViewModel;

public class SearchActivity extends AppCompatActivity {
    private SearchViewModel searchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySearchBinding activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        searchViewModel = new SearchViewModel(this);
        activitySearchBinding.setSearchViewModel(searchViewModel);

        activitySearchBinding.SearchActivityEditTextSearch.requestFocus();
        activitySearchBinding.SearchActivityImageButtonScanQR.setOnClickListener(v -> {
            Intent scanQRIntent = new Intent(this, QRCodeScannerActivity.class);
            startActivity(scanQRIntent);
        });

        activitySearchBinding.SearchActivityImageButtonBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(0, 0);
        });

        activitySearchBinding.SearchActivityEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchViewModel.getDataListUser(activitySearchBinding.SearchActivityEditTextSearch.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
package com.firebase.chat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.firebase.chat.R;
import com.firebase.chat.databinding.ActivitySearchBinding;
import com.firebase.chat.models.User;
import com.firebase.chat.services.UserService;
import com.firebase.chat.viewmodels.UserViewModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = SearchActivity.class.getSimpleName();
    private UserService userService;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userService = UserService.getInstance();
        ActivitySearchBinding activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        userViewModel = new UserViewModel(this);
        activitySearchBinding.setUserViewModel(userViewModel);

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
                if (s.toString().contains("@")) {
                    userService.getAllUserByEmail(s.toString(), (queryDocumentSnapshots, e) -> {
                        if (e != null) {
                            Toast.makeText(activitySearchBinding.getRoot().getContext(), "Failed to fetch data", Toast.LENGTH_SHORT);
                            return;
                        }

                        ObservableList<User> userObservableList = new ObservableArrayList<>();


                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            handleDocumentChanges(userObservableList, document);
                        }

                        userViewModel.setUserItems(userObservableList);
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void handleDocumentChanges(ObservableList<User> userObservableList, QueryDocumentSnapshot document) {
        User user = document.toObject(User.class);
        userObservableList.add(user);

        document.getReference().addSnapshotListener((snapshot, error) -> {
            if (error != null) {
                return;
            }

            if (snapshot != null && snapshot.exists()) {
                User updatedUser = snapshot.toObject(User.class);
                int index = userObservableList.indexOf(user);
                if (index != -1) {
                    userObservableList.set(index, updatedUser);
                }
            }
        });
    }


}
package com.firebase.chat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.chat.R;
import com.firebase.chat.models.User;
import com.firebase.chat.services.AuthService;
import com.firebase.chat.services.UserService;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton backImageButton;
    private CircleImageView photoUrlCircleImageView;
    private TextView displayNameTextView;
    private TextView displayNameTextViewDetail;
    private TextView emailTextView;
    private TextView emailTextViewDetail;
    private TextView bioTextViewDetail;
    private UserService userService;
    private AuthService authService;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userService = UserService.getInstance();
        authService = AuthService.getInstance();

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null && extras.containsKey("uid")) {
                uid = extras.getString("uid");
            }
        }

        initializeUI();

        backImageButton.setOnClickListener(v -> finish());

        if (uid != null) {
            fetchUserAndPopulateUI(uid);
        } else {
            finish();
        }
    }

    private void initializeUI() {
        backImageButton = findViewById(R.id.ProfileActivity_ImageButton_Back);
        photoUrlCircleImageView = findViewById(R.id.ProfileActivity_CircleImageView_PhotoUrl);
        displayNameTextView = findViewById(R.id.ProfileActivity_TextView_DisplayName);
        displayNameTextViewDetail = findViewById(R.id.ProfileActivity_TextView_DisplayNameDetail);
        emailTextViewDetail = findViewById(R.id.ProfileActivity_TextView_EmailDetail);
        emailTextView = findViewById(R.id.ProfileActivity_TextView_Email);
        bioTextViewDetail = findViewById(R.id.ProfileActivity_TextView_BioDetail);
    }

    private void fetchUserAndPopulateUI(String uid) {
        userService.getUserByUid(uid)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user = task.getResult();
                        if (user != null) {
                            displayNameTextView.setText(user.getDisplayName());
                            displayNameTextViewDetail.setText(user.getDisplayName());
                            emailTextView.setText(user.getEmail());
                            emailTextViewDetail.setText(user.getEmail());
                            bioTextViewDetail.setText(user.getBio());
                            Picasso.get().load(user.getPhotoUrl()).into(photoUrlCircleImageView);
                        } else {
                            finish();
                        }
                    } else {
                        Exception e = task.getException();
                        finish();
                    }
                });
    }

}

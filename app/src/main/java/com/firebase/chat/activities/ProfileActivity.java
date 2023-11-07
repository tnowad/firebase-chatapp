package com.firebase.chat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private ImageButton sendMessageButton;
    private ImageButton sendFriendRequestButton;
    private ImageButton unfriendButton;
    private ImageButton generateQRCodeButton;
    private ImageButton otherOptionsButton;

    private UserService userService;
    private AuthService authService;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initializeServices();
        initializeUI();
        initializeClickEvent();

        retrieveUserIdFromIntent();

        if (uid != null) {
            fetchUserAndPopulateUI(uid);
        } else {
            finish();
        }
    }

    private void initializeServices() {
        userService = UserService.getInstance();
        authService = AuthService.getInstance();
    }

    private void initializeUI() {
        backImageButton = findViewById(R.id.ProfileActivity_ImageButton_Back);
        photoUrlCircleImageView = findViewById(R.id.ProfileActivity_CircleImageView_PhotoUrl);
        displayNameTextView = findViewById(R.id.ProfileActivity_TextView_DisplayName);
        displayNameTextViewDetail = findViewById(R.id.ProfileActivity_TextView_DisplayNameDetail);
        emailTextView = findViewById(R.id.ProfileActivity_TextView_Email);
        emailTextViewDetail = findViewById(R.id.ProfileActivity_TextView_EmailDetail);
        bioTextViewDetail = findViewById(R.id.ProfileActivity_TextView_BioDetail);
        sendMessageButton = findViewById(R.id.ProfileActivity_ImageButton_SendMessage);
        sendFriendRequestButton = findViewById(R.id.ProfileActivity_ImageButton_SendFriendRequest);
        unfriendButton = findViewById(R.id.ProfileActivity_ImageButton_Unfriend);
        generateQRCodeButton = findViewById(R.id.ProfileActivity_ImageButton_GenerateQRCode);
        otherOptionsButton = findViewById(R.id.ProfileActivity_ImageButton_OtherOption);
    }

    private void initializeClickEvent() {
        backImageButton.setOnClickListener(v -> finish());

        generateQRCodeButton.setOnClickListener(v -> {
            Intent generateQRCodeIntent = new Intent(this, GenerateQRCodeActivity.class);
            generateQRCodeIntent.putExtra("content", uid);
            startActivity(generateQRCodeIntent);
        });
    }

    private void retrieveUserIdFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null && extras.containsKey("uid")) {
                uid = extras.getString("uid");
            }
        }
    }

    private void fetchUserAndPopulateUI(String uid) {
        userService.getUserByUid(uid)
                .addOnSuccessListener(user -> {
                    if (user != null) {
                        populateUserProfile(user);

                        if (uid.equals(authService.getCurrentUser().getUid())) {
                            hideFriendshipButtons();
                        } else if (userIsFriend(user)) {
                            showUnfriendButton();
                        } else {
                            showSendFriendRequestButton();
                        }
                    } else {
                        finish();
                    }
                })
                .addOnFailureListener(e -> finish());
    }

    private boolean userIsFriend(User user) {
        // Add logic to check if the user is a friend.
        // Replace this with your actual implementation.
        return false;
    }

    private void populateUserProfile(User user) {
        displayNameTextView.setText(user.getDisplayName());
        displayNameTextViewDetail.setText(user.getDisplayName());
        emailTextView.setText(user.getEmail());
        emailTextViewDetail.setText(user.getEmail());
        bioTextViewDetail.setText(user.getBio());
        Picasso.get().load(user.getPhotoUrl()).into(photoUrlCircleImageView);
    }

    private void hideFriendshipButtons() {
        sendFriendRequestButton.setVisibility(View.GONE);
        unfriendButton.setVisibility(View.GONE);
    }

    private void showSendFriendRequestButton() {
        sendFriendRequestButton.setVisibility(View.VISIBLE);
        unfriendButton.setVisibility(View.GONE);
    }

    private void showUnfriendButton() {
        sendFriendRequestButton.setVisibility(View.GONE);
        unfriendButton.setVisibility(View.VISIBLE);
    }
}

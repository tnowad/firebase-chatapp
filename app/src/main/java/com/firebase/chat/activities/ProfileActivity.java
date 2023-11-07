package com.firebase.chat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.chat.R;
import com.firebase.chat.models.User;
import com.firebase.chat.services.AuthService;
import com.firebase.chat.services.FriendService;
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

    private FriendService friendService;
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
        friendService = FriendService.getInstance();
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
                            showCurrentUserProfile();
                        } else if (userIsFriend(user)) {
                            showFriendUserProfile();
                        } else {
                            showNonFriendUserProfile();
                        }
                    } else {
                        finish();
                    }
                })
                .addOnFailureListener(e -> finish());
    }

    private boolean userIsFriend(User user) {
        // TODO: Write method check is friend here
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

    private void showCurrentUserProfile() {
        sendFriendRequestButton.setVisibility(View.GONE);
        unfriendButton.setVisibility(View.GONE);

        sendMessageButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void showNonFriendUserProfile() {
        sendFriendRequestButton.setVisibility(View.VISIBLE);
        unfriendButton.setVisibility(View.GONE);

        sendFriendRequestButton.setOnClickListener(v -> {
            String senderId = authService.getCurrentUser().getUid();
            String receiverId = uid;

            friendService.sendFriendRequest(senderId, receiverId)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Friend request sent successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to send friend request: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

    private void showFriendUserProfile() {
        sendFriendRequestButton.setVisibility(View.GONE);
        unfriendButton.setVisibility(View.VISIBLE);

        unfriendButton.setOnClickListener(v -> {
            String senderId = authService.getCurrentUser().getUid();
            String receiverId = uid;

            friendService.unfriend(senderId, receiverId)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Unfriended successfully", Toast.LENGTH_SHORT).show();
                        showNonFriendUserProfile();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to unfriend: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}

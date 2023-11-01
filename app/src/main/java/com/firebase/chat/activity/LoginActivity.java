package com.firebase.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.chat.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 5;
    private Button loginWithGoogleButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, connectionResult -> Log.d("TAG", "You got an Error!"))
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();


        loginWithGoogleButton = findViewById(R.id.LoginActivity_Button_LoginWithGoogle);

        firebaseAuth = FirebaseAuth.getInstance();

        loginWithGoogleButton.setOnClickListener(v -> {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

}
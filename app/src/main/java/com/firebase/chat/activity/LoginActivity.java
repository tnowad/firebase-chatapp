package com.firebase.chat.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.chat.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static int RC_SIGN_IN = 5;
    private Button loginWithGoogleButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginWithGoogleButton = (Button) findViewById(R.id.LoginActivity_Button_LoginWithGoogle);

        firebaseAuth = FirebaseAuth.getInstance();

        loginWithGoogleButton.setOnClickListener(v -> {
            if(firebaseAuth == null ) {
                Log.d(TAG, "firebase is null");
            } else {
                try {
                    Log.d(TAG, firebaseAuth.getApp().getName());
                } catch (Exception e){
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }

}
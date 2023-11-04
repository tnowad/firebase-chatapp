package com.firebase.chat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.chat.R;
import com.firebase.chat.services.AuthService;
import com.firebase.chat.utils.Utils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = SignInActivity.class.getSimpleName();
    private Button loginWithGoogleButton;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            o -> {
                Intent data = o.getData();
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        loginWithGoogleButton = findViewById(R.id.LoginActivity_Button_LoginWithGoogle);

        firebaseAuth = FirebaseAuth.getInstance();

        loginWithGoogleButton.setOnClickListener(v -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            signInLauncher.launch(signInIntent);
        });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (!result.isSuccess()) {
            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
            return;
        }

        GoogleSignInAccount account = result.getSignInAccount();
        if (account == null) {
            onSignInFailure(new Exception("GoogleSignInAccount is null"));
            return;
        }

        String idToken = account.getIdToken();
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser currentUser = task.getResult().getUser();
                        if (currentUser != null) {
                            String uid = currentUser.getUid();
                            String email = currentUser.getEmail();
                            String displayName = currentUser.getDisplayName();
                            String photoUrl = currentUser.getPhotoUrl() != null ? currentUser.getPhotoUrl().toString() : "";

                            AuthService authService = AuthService.getInstance();
                            authService.createAccountIfNotExists(uid, email, displayName, photoUrl)
                                    .addOnCompleteListener(this, createAccountTask -> {
                                        Log.d(TAG, createAccountTask.toString());
                                        if (createAccountTask.isComplete()) {
                                            onSignInSuccess();
                                        } else {
                                            onSignInFailure(createAccountTask.getException());
                                        }
                                    });
                        } else {
                            onSignInFailure(new Exception("Firebase user is null"));
                            firebaseAuth.signOut();
                        }
                    } else {
                        onSignInFailure(task.getException());
                        firebaseAuth.signOut();
                    }
                });
    }

    private void onSignInSuccess() {
        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
        startMainActivity();
    }

    private void onSignInFailure(Exception exception) {
        Log.e(TAG, exception.toString());
        exception.printStackTrace();
        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
    }

    private void startMainActivity() {
        Utils.CURRENT_UID = firebaseAuth.getCurrentUser().getUid();
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
        finish();
    }
}

package com.firebase.chat.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import com.firebase.chat.R;
import com.firebase.chat.adapters.AdapterViewPager;
import com.firebase.chat.databinding.ActivityMainBinding;
import com.firebase.chat.viewmodels.MainViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityMainBinding activityMessageBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainViewModel = new MainViewModel();
        activityMessageBinding.setMainViewModel(mMainViewModel);

        AdapterViewPager adapterViewPager = new AdapterViewPager(this);
        activityMessageBinding.MainActivityViewpg2.setAdapter(adapterViewPager);
        activityMessageBinding.MainActivityViewpg2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        activityMessageBinding.MainActivityBottomnav.setSelectedItemId(R.id.BottomNavManu_Item_Message);
                        break;
                    case 1:
                        activityMessageBinding.MainActivityBottomnav.setSelectedItemId(R.id.BottomNavMenu_Item_Profile);
                        break;
                    case 2:
                        activityMessageBinding.MainActivityBottomnav.setSelectedItemId(R.id.BottomNavMenu_Item_Setting);
                        break;
                }
            }
        });

        activityMessageBinding.MainActivityBottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int message = R.id.BottomNavManu_Item_Message;
                int profile = R.id.BottomNavMenu_Item_Profile;
                int setting = R.id.BottomNavMenu_Item_Setting;
                if (item.getItemId() == message) {
                    activityMessageBinding.MainActivityViewpg2.setCurrentItem(0);
                } else if (item.getItemId() == profile) {
                    activityMessageBinding.MainActivityViewpg2.setCurrentItem(1);
                } else if (item.getItemId() == setting) {
                    activityMessageBinding.MainActivityViewpg2.setCurrentItem(2);
                }
                return true;
            }
        });
        activityMessageBinding.executePendingBindings();

//        UserService userService = new UserService();
//        String email = "benlun99999@gmail.com";
//        String pass = "123456";
//        firebaseAuth.createUserWithEmailAndPassword(email, pass)
//                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            FirebaseUser curr_user = firebaseAuth.getCurrentUser();
//                            User newUser = new User(email, "benlun99", "", true, Arrays.asList("RCfoLiabTQS290XhukHHP5jod4C3"));
//
//                            userService.insert(curr_user.getUid(), newUser);
//
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Lỗi!!!", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });

        loginDemo();
    }

    private void loginDemo() {
        firebaseAuth.signInWithEmailAndPassword("benlun1201@gmail.com", "123456")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        User newUser1 = new User("test1@gmail.com", "test1", "", true, Arrays.asList("RCfoLiabTQS290XhukHHP5jod4C3"));
//                        User newUser2 = new User("test2@gmail.com", "test2", "", true, Arrays.asList("RCfoLiabTQS290XhukHHP5jod4C3"));
//                        User newUser3 = new User("test3@gmail.com", "test3", "", true, Arrays.asList("RCfoLiabTQS290XhukHHP5jod4C3"));
//
//                        UserService userService = new UserService();
//                        userService.insert("1", newUser1);
//                        userService.insert("2", newUser2);
//                        userService.insert("3", newUser3);
                    }

                });
    }
}
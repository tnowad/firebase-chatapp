package com.firebase.chat.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import com.firebase.chat.R;
import com.firebase.chat.adapter.AdapterViewPager;
import com.firebase.chat.databinding.ActivityMainBinding;
import com.firebase.chat.utils.Utils;
import com.firebase.chat.viewmodel.MainViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                        activityMessageBinding.MainActivityBottomnav.setSelectedItemId(R.id.BottomNav_item_message);
                        break;
                    case 1:
                        activityMessageBinding.MainActivityBottomnav.setSelectedItemId(R.id.BottomNav_item_profile);
                        break;
                    case 2:
                        activityMessageBinding.MainActivityBottomnav.setSelectedItemId(R.id.BottomNav_item_setting);
                        break;
                }
            }
        });

        activityMessageBinding.MainActivityBottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int message = R.id.BottomNav_item_message;
                int profile = R.id.BottomNav_item_profile;
                int setting = R.id.BottomNav_item_setting;
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
    }

    private void loginDemo() {
        firebaseAuth.signInWithEmailAndPassword("benlun1201@gmail.com", "123456")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                        Utils.CURRENT_UID = currentUser.getUid();
                    }

                });
    }
}
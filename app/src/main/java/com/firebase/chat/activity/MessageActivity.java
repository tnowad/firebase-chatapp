package com.firebase.chat.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.firebase.chat.R;
import com.firebase.chat.adapter.AdapterViewPager;
import com.firebase.chat.databinding.ActivityMessageBinding;
import com.firebase.chat.fragment.MessageFragment;
import com.firebase.chat.fragment.ProfileFragment;
import com.firebase.chat.fragment.SettingFragment;
import com.firebase.chat.viewmodel.MessageViewModel;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ActivityMessageBinding activityMessageBinding = DataBindingUtil.setContentView(this, R.layout.activity_message);
        MessageViewModel messageViewModel = new MessageViewModel();
        activityMessageBinding.setMessageViewModel(messageViewModel);

        AdapterViewPager adapterViewPager = new AdapterViewPager(this, messageViewModel.getFragmentList());
        activityMessageBinding.MessageActViewpg2.setAdapter(adapterViewPager);
        activityMessageBinding.MessageActViewpg2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0 :
                        activityMessageBinding.MessageActBottomnav.setSelectedItemId(R.id.BottomNav_item_message);
                        break;
                    case 1 :
                        activityMessageBinding.MessageActBottomnav.setSelectedItemId(R.id.BottomNav_item_profile);
                        break;
                    case 2 :
                        activityMessageBinding.MessageActBottomnav.setSelectedItemId(R.id.BottomNav_item_setting);
                        break;
                }
            }
        });

        activityMessageBinding.MessageActBottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int message = R.id.BottomNav_item_message;
                int profile = R.id.BottomNav_item_profile;
                int setting = R.id.BottomNav_item_setting;
                if (item.getItemId() == message) {
                    activityMessageBinding.MessageActViewpg2.setCurrentItem(0);
                }
                else if (item.getItemId() == profile) {
                    activityMessageBinding.MessageActViewpg2.setCurrentItem(1);
                }
                else if (item.getItemId() == setting) {
                    activityMessageBinding.MessageActViewpg2.setCurrentItem(2);
                }
                return true;
            }
        });
    }
}
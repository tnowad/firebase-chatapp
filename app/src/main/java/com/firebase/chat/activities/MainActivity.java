package com.firebase.chat.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import com.firebase.chat.R;
import com.firebase.chat.adapters.AdapterViewPagerAdapter;
import com.firebase.chat.databinding.ActivityMainBinding;
import com.firebase.chat.viewmodels.MainViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel();
        binding.setMainViewModel(mainViewModel);

        setupViewPager(binding);

        setupBottomNavigationBar(binding);
    }

    private void setupViewPager(ActivityMainBinding binding) {
        AdapterViewPagerAdapter adapterViewPagerAdapter = new AdapterViewPagerAdapter(this);
        ViewPager2 viewPager = binding.MainActivityViewPager2;
        viewPager.setAdapter(adapterViewPagerAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                switch (position) {
                    case 0:
                        binding.MainActivityBottomNav.setSelectedItemId(R.id.BottomNavMenu_Item_Message);
                        break;
                    case 1:
                        binding.MainActivityBottomNav.setSelectedItemId(R.id.BottomNavMenu_Item_Profile);
                        break;
                    case 2:
                        binding.MainActivityBottomNav.setSelectedItemId(R.id.BottomNavMenu_Item_Setting);
                        break;
                }
            }
        });
    }

    private void setupBottomNavigationBar(ActivityMainBinding binding) {
        binding.MainActivityBottomNav.setOnItemSelectedListener(item -> {
            int messageItemId = R.id.BottomNavMenu_Item_Message;
            int profileItemId = R.id.BottomNavMenu_Item_Profile;
            int settingItemId = R.id.BottomNavMenu_Item_Setting;

            if (item.getItemId() == messageItemId) {
                binding.MainActivityViewPager2.setCurrentItem(0);
            } else if (item.getItemId() == profileItemId) {
                binding.MainActivityViewPager2.setCurrentItem(1);
            } else if (item.getItemId() == settingItemId) {
                binding.MainActivityViewPager2.setCurrentItem(2);
            }
            return true;
        });
    }
}

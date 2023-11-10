package com.firebase.chat.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import com.firebase.chat.R;
import com.firebase.chat.adapters.ViewPagerAdapter;
import com.firebase.chat.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        setupViewPager(activityMainBinding);

        setupBottomNavigationBar(activityMainBinding);
    }

    private void setupViewPager(ActivityMainBinding binding) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        ViewPager2 viewPager = binding.MainActivityViewPager2;
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        binding.MainActivityBottomNav.setSelectedItemId(R.id.BottomNavMenu_Item_Message);
                        break;
                    case 1:
                        binding.MainActivityBottomNav.setSelectedItemId(R.id.BottomNavMenu_Item_Contact);
                        break;
                    case 2:
                        binding.MainActivityBottomNav.setSelectedItemId(R.id.BottomNavMenu_Item_Request);
                        break;
                    case 3:
                        binding.MainActivityBottomNav.setSelectedItemId(R.id.BottomNavMenu_Item_Setting);
                        break;
                }
            }
        });
    }

    private void setupBottomNavigationBar(ActivityMainBinding binding) {
        binding.MainActivityBottomNav.setOnItemSelectedListener(item -> {
            int messageItemId = R.id.BottomNavMenu_Item_Message;
            int contactItemId = R.id.BottomNavMenu_Item_Contact;
            int requestItemId = R.id.BottomNavMenu_Item_Request;
            int settingItemId = R.id.BottomNavMenu_Item_Setting;

            if (item.getItemId() == messageItemId) {
                binding.MainActivityViewPager2.setCurrentItem(0);
            } else if (item.getItemId() == contactItemId) {
                binding.MainActivityViewPager2.setCurrentItem(1);
            } else if (item.getItemId() == requestItemId) {
                binding.MainActivityViewPager2.setCurrentItem(2);
            } else if (item.getItemId() == settingItemId) {
                binding.MainActivityViewPager2.setCurrentItem(3);
            }
            return true;
        });
    }
}

package com.firebase.chat.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.firebase.chat.fragment.MessageFragment;
import com.firebase.chat.fragment.ProfileFragment;
import com.firebase.chat.fragment.SettingFragment;

import java.util.ArrayList;

public class AdapterViewPager extends FragmentStateAdapter {
    ArrayList<Fragment> list = new ArrayList<>();

    public AdapterViewPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.list.add(new MessageFragment());
        this.list.add(new ProfileFragment());
        this.list.add(new SettingFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

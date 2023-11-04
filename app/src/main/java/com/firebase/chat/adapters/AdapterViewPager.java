package com.firebase.chat.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.firebase.chat.fragments.MessageFragment;
import com.firebase.chat.fragments.ProfileFragment;
import com.firebase.chat.fragments.SettingFragment;

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

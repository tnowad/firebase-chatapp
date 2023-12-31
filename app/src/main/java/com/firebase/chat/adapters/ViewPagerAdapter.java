package com.firebase.chat.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.firebase.chat.fragments.ChatFragment;
import com.firebase.chat.fragments.ContactFragment;
import com.firebase.chat.fragments.RequestFragment;
import com.firebase.chat.fragments.SettingFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStateAdapter {
    ArrayList<Fragment> list = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.list.add(new ChatFragment());
        this.list.add(new ContactFragment());
        this.list.add(new RequestFragment());
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

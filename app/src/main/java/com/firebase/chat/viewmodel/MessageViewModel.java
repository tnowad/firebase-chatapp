package com.firebase.chat.viewmodel;

import androidx.fragment.app.Fragment;

import com.firebase.chat.fragment.MessageFragment;
import com.firebase.chat.fragment.ProfileFragment;
import com.firebase.chat.fragment.SettingFragment;

import java.util.ArrayList;

public class MessageViewModel {
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

    public MessageViewModel() {
        this.fragmentList.add(new MessageFragment());
        this.fragmentList.add(new ProfileFragment());
        this.fragmentList.add(new SettingFragment());
    }

    public ArrayList<Fragment> getFragmentList() {
        return fragmentList;
    }
}

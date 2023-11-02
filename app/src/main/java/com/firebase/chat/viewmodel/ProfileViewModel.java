package com.firebase.chat.viewmodel;

import com.firebase.chat.model.Chat;
import com.firebase.chat.model.Message;
import com.firebase.chat.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewModel {

    public void onCLickUtils() {
        //Utils.LIST_MESSAGE.add(new Message("Ben", "test", "con cac"));
        List<Message> list = new ArrayList<>();
        list.add(new Message("Ben", "test1", "Hello", new ArrayList<Chat>()));
        list.add(new Message("Ben", "test2", "Hello", new ArrayList<Chat>()));
        Utils.LIST_MESSAGE.clear();
        Utils.LIST_MESSAGE.addAll(list);
    }
}

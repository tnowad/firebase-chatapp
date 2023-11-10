package com.firebase.chat.viewmodels;

import android.content.Context;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.adapters.ListUserItemAdapter;
import com.firebase.chat.models.User;

import java.util.List;

public class UserViewModel {
    private static final String TAG = UserViewModel.class.getSimpleName();
    private static Context context;
    private static ListUserItemAdapter listUserItemAdapter;
    public ObservableList<User> userObservableList = new ObservableArrayList<>();

    public UserViewModel(Context context) {
        UserViewModel.context = context;
    }

    @BindingAdapter({"userItems"})
    public static void bindUserItems(RecyclerView recyclerView, List<User> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        listUserItemAdapter = new ListUserItemAdapter(context, list);
        recyclerView.setAdapter(listUserItemAdapter);
    }

    public void setUserItems(ObservableList<User> userItems) {
        userObservableList.clear();
        userObservableList.addAll(userItems);
        if (listUserItemAdapter != null) {
            listUserItemAdapter.notifyDataSetChanged();
        }
    }
}

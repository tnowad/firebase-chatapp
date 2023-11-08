package com.firebase.chat.viewmodels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.adapters.ListChatItemAdapter;
import com.firebase.chat.adapters.ListSearchItemAdapter;
import com.firebase.chat.models.Chat;
import com.firebase.chat.models.User;
import com.firebase.chat.services.MessageService;
import com.firebase.chat.services.UserService;
import com.firebase.chat.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchViewModel {
    private static final String TAG = SearchViewModel.class.getSimpleName();
    private static Context context;
    private static ListSearchItemAdapter listSearchItemAdapter;
    private final UserService userService = new UserService();
    private List<User> listUser = new ArrayList<>();

    public SearchViewModel(Context context) {
        this.context = context;
        this.listUser = Utils.LIST_USER;
    }

    public List<User> getListUser() {
        return listUser;
    }

    public void setListUser(List<User> listUser) {
        this.listUser = listUser;
    }

    @BindingAdapter({"list_user"})
    public static void loadListUser(RecyclerView recyclerView, List<User> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        //recyclerView.addItemDecoration(dividerItemDecoration);

        listSearchItemAdapter = new ListSearchItemAdapter(context, list);
        recyclerView.setAdapter(listSearchItemAdapter);
    }

    public void getDataListUser(String keyword) {
        if (keyword.contains("@gmail.com")) {
            userService.getUserByEmail(keyword);
        }
        else {

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void clear() {
        if (listUser != null) {
            listUser.clear();
        }
        listSearchItemAdapter.notifyDataSetChanged();
    }

}

package com.firebase.chat.viewmodels;

import android.content.Context;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.adapters.ListFriendItemAdapter;
import com.firebase.chat.models.Friend;

import java.util.List;

public class FriendViewModel {
    private static final String TAG = FriendViewModel.class.getSimpleName();
    private static Context context;
    private static ListFriendItemAdapter listFriendItemAdapter;
    public ObservableList<Friend> requestObservableList = new ObservableArrayList<>();

    public FriendViewModel(Context context) {
        FriendViewModel.context = context;
    }

    @BindingAdapter({"friendItems"})
    public static void bindFriendItems(RecyclerView recyclerView, List<Friend> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        listFriendItemAdapter = new ListFriendItemAdapter(context, list);
        recyclerView.setAdapter(listFriendItemAdapter);
    }

    public void setFriendItems(ObservableList<Friend> requestItems) {
        requestObservableList.clear();
        requestObservableList.addAll(requestItems);
        if (listFriendItemAdapter != null) {
            listFriendItemAdapter.notifyDataSetChanged();
        }
    }
}

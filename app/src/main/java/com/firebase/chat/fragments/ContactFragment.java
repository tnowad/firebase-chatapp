package com.firebase.chat.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;

import com.firebase.chat.databinding.FragmentContactBinding;
import com.firebase.chat.models.Friend;
import com.firebase.chat.services.FriendService;
import com.firebase.chat.viewmodels.FriendViewModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;


public class ContactFragment extends Fragment {

    private static final String TAG = ContactFragment.class.getSimpleName();
    private FriendService friendService;
    private FriendViewModel friendViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentContactBinding fragmentContactBinding = FragmentContactBinding.inflate(inflater, container, false);
        initViewModel(fragmentContactBinding);
        initChatService();

        friendService = FriendService.getInstance();
        friendService.getAllAcceptedRequestsForCurrentUser((queryDocumentSnapshots, e) -> {
            if (e != null) {
                Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT);
                return;
            }

            ObservableList<Friend> friendObservableList = new ObservableArrayList<>();

            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                handleDocumentChanges(friendObservableList, document);
            }

            friendViewModel.setFriendItems(friendObservableList);
        });

        return fragmentContactBinding.getRoot();
    }

    private void initViewModel(FragmentContactBinding fragmentContactBinding) {
        friendViewModel = new FriendViewModel(getActivity());
        fragmentContactBinding.setFriendViewModel(friendViewModel);
        fragmentContactBinding.executePendingBindings();
    }

    private void initChatService() {
        friendService = FriendService.getInstance();
    }

    private void handleDocumentChanges(ObservableList<Friend> friendObservableList, QueryDocumentSnapshot document) {
        Friend friend = document.toObject(Friend.class);
        Log.d(TAG, friend.toString());
        friendObservableList.add(friend);

        document.getReference().addSnapshotListener((snapshot, error) -> {
            if (error != null) {
                return;
            }

            if (snapshot != null && snapshot.exists()) {
                Friend updatedFriend = snapshot.toObject(Friend.class);
                int index = friendObservableList.indexOf(friend);
                if (index != -1) {
                    friendObservableList.set(index, updatedFriend);
                }
            }
        });
    }
}
package com.firebase.chat.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.activities.MessageActivity;
import com.firebase.chat.activities.ProfileActivity;
import com.firebase.chat.databinding.ItemFriendBinding;
import com.firebase.chat.models.Friend;
import com.firebase.chat.services.AuthService;
import com.firebase.chat.services.ChatService;
import com.firebase.chat.services.FriendService;
import com.firebase.chat.services.UserService;
import com.squareup.picasso.Picasso;

import java.util.Arrays;


public class ListFriendItemAdapter extends RecyclerView.Adapter<ListFriendItemAdapter.RequestItemViewHolder> {

    private final Context context;
    private final ObservableList<Friend> friendObservableList;

    public ListFriendItemAdapter(Context context, ObservableList<Friend> friendObservableList) {
        this.context = context;
        this.friendObservableList = friendObservableList;
    }

    public RequestItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFriendBinding itemFriendBinding = ItemFriendBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new RequestItemViewHolder(itemFriendBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestItemViewHolder holder, int position) {
        Friend friend = friendObservableList.get(position);
        if (friend == null) {
            return;
        }

        String uid = null;
        if (friend.senderId.equals(AuthService.getInstance().getCurrentUser().getUid())) {
            uid = friend.receiverId;
        } else {
            uid = friend.senderId;
        }

        UserService.getInstance().getUserByUid(uid).addOnSuccessListener(user -> {
            if (user != null && !user.getUid().equals(AuthService.getInstance().getCurrentUser().getUid())) {
                holder.itemRequestBinding.setUser(user);
                Picasso.get().load(user.getPhotoUrl())
                        .into(holder.itemRequestBinding.RequestItemImageViewAvatar);
            }
        });

        String finalUid4 = uid;
        holder.setOnItemClickListener(v -> {
            Intent profileActivity = new Intent(context, ProfileActivity.class);
            profileActivity.putExtra("uid", finalUid4);
            context.startActivity(profileActivity);
            Activity activity = (Activity) context;
            activity.overridePendingTransition(0, 0);
        });

        String finalUid3 = uid;
        holder.setOnDeclineButtonListener(v -> {
            FriendService.getInstance().unfriend(AuthService.getInstance().getCurrentUser().getUid(), finalUid3);
        });

        if (friend.getStatus().equals("accepted") || friend.getSenderId().equals(AuthService.getInstance().getCurrentUser().getUid())) {
            holder.setVisibleAcceptButton();

            holder.setOnItemClickListener(v -> {
                Intent messageActivityIntent = new Intent(context, MessageActivity.class);
                ChatService.getInstance().createChatIfNotExists(
                        Arrays.asList(friend.getReceiverId(), friend.getSenderId())
                ).addOnCompleteListener((Activity) context, task -> {
                    if (task.isComplete()) {
                        messageActivityIntent.putExtra("chatId",
                                task.getResult().getId()
                        );
                        context.startActivity(messageActivityIntent);
                        Activity activity = (Activity) context;
                        activity.overridePendingTransition(0, 0);
                    }
                });


            });


            String finalUid1 = uid;
        } else {
            String finalUid2 = uid;
            holder.setOnAcceptButtonListener(v -> {
                FriendService.getInstance().acceptFriendRequest(AuthService.getInstance().getCurrentUser().getUid(), finalUid2);
            });
        }

        String finalUid = uid;
    }

    @Override
    public int getItemCount() {
        if (friendObservableList == null) {
            return 0;
        }
        return friendObservableList.size();
    }

    public static class RequestItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemFriendBinding itemRequestBinding;


        public RequestItemViewHolder(@NonNull ItemFriendBinding itemFriendBinding) {
            super(itemFriendBinding.getRoot());
            this.itemRequestBinding = itemFriendBinding;
        }

        public void setOnItemClickListener(View.OnClickListener onClickListener) {
            itemRequestBinding.getRoot().setOnClickListener(onClickListener);
        }

        public void setOnAcceptButtonListener(View.OnClickListener onClickListener) {
            itemRequestBinding.RequestItemImageButtonAccept.setOnClickListener(onClickListener);
        }

        public void setOnDeclineButtonListener(View.OnClickListener onClickListener) {
            itemRequestBinding.RequestItemImageButtonDecline.setOnClickListener(onClickListener);
        }

        public void setVisibleAcceptButton() {
            itemRequestBinding.RequestItemImageButtonAccept.setVisibility(View.GONE);
        }

    }
}

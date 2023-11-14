package com.firebase.chat.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.activities.MessageActivity;
import com.firebase.chat.activities.ProfileActivity;
import com.firebase.chat.databinding.ItemFriendBinding;
import com.firebase.chat.models.Friend;
import com.firebase.chat.models.User;
import com.firebase.chat.services.AuthService;
import com.firebase.chat.services.ChatService;
import com.firebase.chat.services.FriendService;
import com.firebase.chat.services.UserService;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class ListFriendItemAdapter extends RecyclerView.Adapter<ListFriendItemAdapter.FriendViewHolder> {

    private final Context context;
    private final ObservableList<Friend> friendObservableList;

    public ListFriendItemAdapter(Context context, ObservableList<Friend> friendObservableList) {
        this.context = context;
        this.friendObservableList = friendObservableList;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFriendBinding itemFriendBinding = ItemFriendBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FriendViewHolder(itemFriendBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        Friend friend = friendObservableList.get(position);
        if (friend == null) {
            return;
        }

        String currentUserUid = AuthService.getInstance().getCurrentUser().getUid();
        String friendUid = friend.getFriendUid(currentUserUid);

        UserService.getInstance().getUserByUid(friendUid).addOnSuccessListener(user -> {
            if (user != null && !user.getUid().equals(currentUserUid)) {
                holder.bindUser(user);
            }
        });

        holder.setOnItemClickListener((v) -> {
            openUserProfile(friendUid);
        });
        holder.setDeclineButtonListener((v) -> FriendService.getInstance().unfriend(currentUserUid, friendUid));

        if (friend.getSenderId().equals(currentUserUid)) {
            holder.setAcceptButtonVisibility(View.GONE);
        }

        if (friend.isAccepted()) {
            holder.setAcceptButtonVisibility(View.GONE);
            holder.setOnItemClickListener((v) -> openMessageActivity(friend));
        } else {
            holder.setAcceptButtonClickListener((v) -> FriendService.getInstance().acceptFriendRequest(currentUserUid, friendUid));
        }
    }

    @Override
    public int getItemCount() {
        return friendObservableList.size();
    }

    private void openUserProfile(String friendUid) {
        Intent profileActivity = new Intent(context, ProfileActivity.class);
        profileActivity.putExtra("uid", friendUid);
        context.startActivity(profileActivity);
        Activity activity = (Activity) context;
        activity.overridePendingTransition(0, 0);
    }

    private void openMessageActivity(Friend friend) {
        Intent messageActivityIntent = new Intent(context, MessageActivity.class);
        ChatService.getInstance().findChatByParticipants(friend.getReceiverId(), friend.getSenderId(), (chat, error) -> {
            if (chat != null) {
                messageActivityIntent.putExtra("chatId", chat.getId());
                context.startActivity(messageActivityIntent);
            } else {
                ChatService.getInstance().createChat(Arrays.asList(friend.getReceiverId(), friend.getSenderId())).addOnCompleteListener((Activity) context, task -> {
                    if (task.isComplete()) {
                        messageActivityIntent.putExtra("chatId", task.getResult().getId());
                        context.startActivity(messageActivityIntent);
                        Activity activity = (Activity) context;
                        activity.overridePendingTransition(0, 0);
                    }
                });
            }
        });
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder {
        private final ItemFriendBinding itemFriendBinding;

        public FriendViewHolder(@NonNull ItemFriendBinding itemFriendBinding) {
            super(itemFriendBinding.getRoot());
            this.itemFriendBinding = itemFriendBinding;
        }

        public void bindUser(User user) {
            itemFriendBinding.setUser(user);
            ImageView avatarImageView = itemFriendBinding.RequestItemImageViewAvatar;
            Picasso.get().load(user.getPhotoUrl()).into(avatarImageView);
        }

        public void setOnItemClickListener(View.OnClickListener onClickListener) {
            itemFriendBinding.getRoot().setOnClickListener(onClickListener);
        }

        public void setAcceptButtonVisibility(int visibility) {
            itemFriendBinding.RequestItemImageButtonAccept.setVisibility(visibility);
        }

        public void setAcceptButtonClickListener(View.OnClickListener onClickListener) {
            itemFriendBinding.RequestItemImageButtonAccept.setOnClickListener(onClickListener);
        }

        public void setDeclineButtonListener(View.OnClickListener onClickListener) {
            itemFriendBinding.RequestItemImageButtonDecline.setOnClickListener(onClickListener);
        }
    }
}

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

import com.firebase.chat.Interfaces.OnItemClickListener;
import com.firebase.chat.activities.ProfileActivity;
import com.firebase.chat.databinding.ItemFriendBinding;
import com.firebase.chat.models.Friend;
import com.firebase.chat.services.AuthService;
import com.firebase.chat.services.FriendService;
import com.firebase.chat.services.UserService;
import com.squareup.picasso.Picasso;


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
        if (friend.senderId != AuthService.getInstance().getCurrentUser().getUid()) {
            uid = friend.receiverId;
        } else {
            uid = friend.senderId;
        }

        if (uid != null && !uid.equals("")) {
            UserService.getInstance().getUserByUid(uid).addOnSuccessListener(user -> {
                if (user != null) {
                    holder.itemRequestBinding.setUser(user);
                    Picasso.get().load(user.getPhotoUrl())
                            .into(holder.itemRequestBinding.RequestItemImageViewAvatar);
                }
            });
        }


        String finalUid3 = uid;
        holder.setOnDeclineButtonListener(v -> {
            FriendService.getInstance().unfriend(AuthService.getInstance().getCurrentUser().getUid(), finalUid3);
        });
        if (friend.getStatus().equals("accepted")) {
            holder.setVisibleAcceptButton();
            String finalUid1 = uid;
        } else {
            String finalUid2 = uid;
            holder.setOnAcceptButtonListener(v -> {
                FriendService.getInstance().acceptFriendRequest(AuthService.getInstance().getCurrentUser().getUid(), finalUid2);
            });
        }


        String finalUid = uid;
        holder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onMessageItem(View view, int pos) {

            }

            @Override
            public void onSearchItem(View view, int pos) {
                Intent profileActivity = new Intent(context, ProfileActivity.class);
                profileActivity.putExtra("uid", finalUid);
                context.startActivity(profileActivity);
                Activity activity = (Activity) context;
                activity.overridePendingTransition(0, 0);
            }

            @Override
            public void onRequestItem(View view, int pos, int type) {
                if (type == 0) {
                } else if (type == 1) {
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (friendObservableList == null) {
            return 0;
        }
        return friendObservableList.size();
    }

    public static class RequestItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemFriendBinding itemRequestBinding;
        private OnItemClickListener onItemClickListener;

        public RequestItemViewHolder(@NonNull ItemFriendBinding itemFriendBinding) {
            super(itemFriendBinding.getRoot());
            this.itemRequestBinding = itemFriendBinding;
            itemFriendBinding.getRoot().setOnClickListener(this);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
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

        @Override
        public void onClick(View v) {
            if (v == itemRequestBinding.RequestItemImageButtonAccept) {
                onItemClickListener.onRequestItem(v, getAdapterPosition(), 1);
            } else if (v == itemRequestBinding.RequestItemImageButtonDecline) {
                onItemClickListener.onRequestItem(v, getAdapterPosition(), 0);
            }
        }
    }
}

package com.firebase.chat.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.Interfaces.OnItemClickListener;
import com.firebase.chat.activities.ProfileActivity;
import com.firebase.chat.databinding.ItemFriendBinding;
import com.firebase.chat.models.Friend;
import com.firebase.chat.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ListFriendItemAdapter extends RecyclerView.Adapter<ListFriendItemAdapter.RequestItemViewHolder> {

    private final Context context;
    private final List<Friend> listRequest;

    public ListFriendItemAdapter(Context context, List<Friend> listRequest) {
        this.context = context;
        this.listRequest = listRequest;
    }

    public RequestItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFriendBinding itemFriendBinding = ItemFriendBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new RequestItemViewHolder(itemFriendBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestItemViewHolder holder, int position) {
        Friend request = listRequest.get(position);
        //request.setStatus("accepted");
        User senderUser = new User("GpwYBAEvmoS56upYsosYSujcODk2", "Sender Email", "Sender Name", "https://lh3.googleusercontent.com/a/ACg8ocLIhqRgdV9L_YlarsQj4iJaSWFP2pQqg3oHdmchRiudZh8=s96-c");
        if (request == null) {
            return;
        }
        holder.itemRequestBinding.setUser(senderUser);
        Picasso.get().load(senderUser.getPhotoUrl())
                .into(holder.itemRequestBinding.RequestItemImageViewAvatar);

        if (request.getStatus().equals("accepted")) {
            holder.setVisibleAcceptButton();
        }

        holder.setOnMessageItemListener(new OnItemClickListener() {
            @Override
            public void onMessageItem(View view, int pos) {

            }

            @Override
            public void onSearchItem(View view, int pos) {
                Intent profileActivity = new Intent(context, ProfileActivity.class);
                profileActivity.putExtra("uid", senderUser.getUid());
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
        if (listRequest == null) {
            return 0;
        }
        return listRequest.size();
    }

    public static class RequestItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemFriendBinding itemRequestBinding;
        private OnItemClickListener onItemClickListener;

        public RequestItemViewHolder(@NonNull ItemFriendBinding itemFriendBinding) {
            super(itemFriendBinding.getRoot());
            this.itemRequestBinding = itemFriendBinding;
            itemFriendBinding.getRoot().setOnClickListener(this);
            itemFriendBinding.RequestItemImageButtonAccept.setOnClickListener(this);
            itemFriendBinding.RequestItemImageButtonDecline.setOnClickListener(this);
        }

        public void setOnMessageItemListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
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

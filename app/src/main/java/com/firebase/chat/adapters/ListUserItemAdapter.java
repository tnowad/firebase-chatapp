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
import com.firebase.chat.databinding.ItemSearchBinding;
import com.firebase.chat.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ListUserItemAdapter extends RecyclerView.Adapter<ListUserItemAdapter.SearchItemViewHolder> {

    private final Context context;
    private final List<User> listUser;

    public ListUserItemAdapter(Context context, List<User> listUser) {
        this.context = context;
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchBinding itemSearchBinding = ItemSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new SearchItemViewHolder(itemSearchBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
        User user = listUser.get(position);
        if (user == null) {
            return;
        }
        holder.itemSearchBinding.setUser(user);
        Picasso.get().load(user.getPhotoUrl())
                .into(holder.itemSearchBinding.SearchItemImageViewAvatar);

        holder.setOnMessageItemListener(new OnItemClickListener() {
            @Override
            public void onMessageItem(View view, int pos) {

            }

            @Override
            public void onSearchItem(View view, int pos) {
                Intent profileActivity = new Intent(context, ProfileActivity.class);
                profileActivity.putExtra("uid", user.getUid());
                context.startActivity(profileActivity);
                Activity activity = (Activity) context;
                activity.overridePendingTransition(0, 0);
            }
        });
        holder.itemSearchBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (listUser == null) {
            return 0;
        }
        return listUser.size();
    }

    public static class SearchItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemSearchBinding itemSearchBinding;
        private OnItemClickListener onItemClickListener;

        public SearchItemViewHolder(@NonNull ItemSearchBinding itemSearchBinding) {
            super(itemSearchBinding.getRoot());
            this.itemSearchBinding = itemSearchBinding;
            itemSearchBinding.getRoot().setOnClickListener(this);
        }

        public void setOnMessageItemListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onSearchItem(v, getAdapterPosition());
        }
    }
}

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
import com.firebase.chat.activities.MessageActivity;
import com.firebase.chat.databinding.ItemChatBinding;
import com.firebase.chat.models.Chat;
import com.firebase.chat.models.Message;
import com.firebase.chat.models.User;
import com.firebase.chat.services.AuthService;
import com.firebase.chat.services.UserService;
import com.firebase.chat.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.stream.Collectors;

public class ListChatItemAdapter extends RecyclerView.Adapter<ListChatItemAdapter.ChatItemViewHolder> {

    private final Context context;
    private final List<Chat> listChat;

    public ListChatItemAdapter(Context context, List<Chat> listChat) {
        this.context = context;
        this.listChat = listChat;
    }

    @NonNull
    @Override
    public ChatItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChatBinding itemChatBinding = ItemChatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ChatItemViewHolder(itemChatBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatItemViewHolder holder, int position) {
        Chat chat = listChat.get(position);
        if (chat == null) {
            return;
        }
        holder.itemMessageBinding.setChat(chat);


        String uid = chat.getParticipants().stream().filter(s -> !s.equals(AuthService.getInstance().getCurrentUser().getUid())).collect(Collectors.toList()).get(0);
        UserService.getInstance().getUserByUid(uid).addOnSuccessListener(user -> {
            if (user != null) {
                holder.itemMessageBinding.setUser(user);
                Picasso.get().load(user.getPhotoUrl())
                        .into(holder.itemMessageBinding.ChatItemImageViewAvatar);
            }
        });


        holder.itemMessageBinding.setMessage(new Message("", "", "Loading", ""));
        holder.itemMessageBinding.setUser(new User("", "", "Loading", ""));


        holder.setOnMessageItemListener(new OnItemClickListener() {
            @Override
            public void onMessageItem(View view, int pos) {
                Intent intent = new Intent(context, MessageActivity.class);
                Utils.SELECTED_CHAT = chat;
                context.startActivity(intent);
                Activity activity = (Activity) context;
                activity.overridePendingTransition(0, 0);
            }

            @Override
            public void onSearchItem(View view, int pos) {

            }

            @Override
            public void onRequestItem(View view, int pos, int type) {

            }
        });
        holder.itemMessageBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (listChat == null) {
            return 0;
        }
        return listChat.size();
    }

    public static class ChatItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemChatBinding itemMessageBinding;
        private OnItemClickListener onItemClickListener;

        public ChatItemViewHolder(@NonNull ItemChatBinding itemMessageBinding) {
            super(itemMessageBinding.getRoot());
            this.itemMessageBinding = itemMessageBinding;
            itemMessageBinding.getRoot().setOnClickListener(this);
        }

        public void setOnMessageItemListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onMessageItem(v, getAdapterPosition());
        }
    }
}

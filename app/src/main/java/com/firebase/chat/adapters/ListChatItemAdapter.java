package com.firebase.chat.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.Interfaces.OnMessageItemListener;
import com.firebase.chat.R;
import com.firebase.chat.activities.MessageActivity;
import com.firebase.chat.databinding.ItemChatBinding;
import com.firebase.chat.models.Chat;
import com.firebase.chat.models.Message;
import com.firebase.chat.models.User;
import com.firebase.chat.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        holder.itemMessageBinding.setMessage(new Message("123", "123", "Hello!", "3 seconds"));
        holder.itemMessageBinding.setUser(new User("123", "123", "John Doe", ""));
        CircleImageView profileCircleImageView = holder.itemMessageBinding.getRoot().findViewById(R.id.ChatItem_ImageView_Avatar);
        Picasso.get().load("https://lh3.googleusercontent.com/a/ACg8ocLIhqRgdV9L_YlarsQj4iJaSWFP2pQqg3oHdmchRiudZh8=s96-c").into(profileCircleImageView);

        holder.setOnMessageItemListener(new OnMessageItemListener() {
            @Override
            public void onMessageItem(View view, int pos) {
                Intent intent = new Intent(context, MessageActivity.class);
                Utils.SELECTED_CHAT = chat;
                context.startActivity(intent);
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
        private OnMessageItemListener onMessageItemListener;

        public ChatItemViewHolder(@NonNull ItemChatBinding itemMessageBinding) {
            super(itemMessageBinding.getRoot());
            this.itemMessageBinding = itemMessageBinding;
            itemMessageBinding.getRoot().setOnClickListener(this);
        }

        public void setOnMessageItemListener(OnMessageItemListener onMessageItemListener) {
            this.onMessageItemListener = onMessageItemListener;
        }

        @Override
        public void onClick(View v) {
            onMessageItemListener.onMessageItem(v, getAdapterPosition());
        }
    }
}

package com.firebase.chat.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.Interfaces.OnMessageItemListener;
import com.firebase.chat.activities.ChatActivity;
import com.firebase.chat.databinding.ItemMessageBinding;
import com.firebase.chat.models.Message;
import com.firebase.chat.utils.Utils;

import java.util.List;

public class MessageItem extends RecyclerView.Adapter<MessageItem.MessageItemViewHolder> {

    private final Context context;
    private final List<Message> listMessage;

    public MessageItem(Context context, List<Message> listMessage) {
        this.context = context;
        this.listMessage = listMessage;
    }

    @NonNull
    @Override
    public MessageItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMessageBinding itemMessageBinding = ItemMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new MessageItemViewHolder(itemMessageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageItemViewHolder holder, int position) {
        Message message = listMessage.get(position);
        if (message == null) {
            return;
        }
        holder.itemMessageBinding.setMessage(message);
        holder.setOnMessageItemListener(new OnMessageItemListener() {
            @Override
            public void onMessageItem(View view, int pos) {
                Intent intent = new Intent(context, ChatActivity.class);
                Utils.SELECTED_MESSAGE = message;
                context.startActivity(intent);
            }
        });
        holder.itemMessageBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (listMessage == null) {
            return 0;
        }
        return listMessage.size();
    }

    public static class MessageItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemMessageBinding itemMessageBinding;
        private OnMessageItemListener onMessageItemListener;

        public MessageItemViewHolder(@NonNull ItemMessageBinding itemMessageBinding) {
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

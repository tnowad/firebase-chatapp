package com.firebase.chat.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.R;
import com.firebase.chat.databinding.ItemMessageBinding;
import com.firebase.chat.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageItem extends RecyclerView.Adapter<MessageItem.MessageItemViewHolder> {

    private List<Message> listMessage;

    public MessageItem(List<Message> listMessage) {
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
        holder.itemMessageBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (listMessage == null) {
            return 0;
        }
        return listMessage.size();
    }

    public static class MessageItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemMessageBinding itemMessageBinding;

        public MessageItemViewHolder(@NonNull ItemMessageBinding itemMessageBinding) {
            super(itemMessageBinding.getRoot());
            this.itemMessageBinding = itemMessageBinding;
        }
    }
}

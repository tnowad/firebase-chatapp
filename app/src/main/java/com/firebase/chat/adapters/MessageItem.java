package com.firebase.chat.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.databinding.ItemReceivedmessBinding;
import com.firebase.chat.databinding.ItemSentmessBinding;
import com.firebase.chat.models.Chat;
import com.firebase.chat.utils.Utils;

import java.util.List;

public class MessageItem extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MESSAGE_TYPE_SENDING = 0;
    private static final int MESSAGE_TYPE_RECEIVING = 1;
    private final List<Chat> listChat;

    public MessageItem(List<Chat> listChat) {
        this.listChat = listChat;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_TYPE_SENDING) {
            ItemSentmessBinding itemSentmessBinding = ItemSentmessBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new SentMessItemViewHolder(itemSentmessBinding);
        } else {
            ItemReceivedmessBinding itemReceivedmessBinding = ItemReceivedmessBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ReceivedMessItemViewHolder(itemReceivedmessBinding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chat chat = listChat.get(position);
        if (chat == null) {
            return;
        }
        if (holder instanceof SentMessItemViewHolder) {
            ((SentMessItemViewHolder) holder).itemSentmessBinding.setChat(chat);
        } else {
            ((ReceivedMessItemViewHolder) holder).itemReceivedmessBinding.setChat(chat);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (listChat.get(position).getFromUser().equals(Utils.CURRENT_UID)) ? MESSAGE_TYPE_SENDING : MESSAGE_TYPE_RECEIVING;
    }

    @Override
    public int getItemCount() {
        if (listChat == null) {
            return 0;
        }
        return listChat.size();
    }

    public class SentMessItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemSentmessBinding itemSentmessBinding;

        public SentMessItemViewHolder(@NonNull ItemSentmessBinding itemSentmessBinding) {
            super(itemSentmessBinding.getRoot());
            this.itemSentmessBinding = itemSentmessBinding;
        }
    }

    public class ReceivedMessItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemReceivedmessBinding itemReceivedmessBinding;

        public ReceivedMessItemViewHolder(@NonNull ItemReceivedmessBinding itemReceivedmessBinding) {
            super(itemReceivedmessBinding.getRoot());
            this.itemReceivedmessBinding = itemReceivedmessBinding;
        }
    }
}

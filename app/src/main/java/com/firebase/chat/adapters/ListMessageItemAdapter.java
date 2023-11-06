package com.firebase.chat.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.databinding.ItemMessageMineBinding;
import com.firebase.chat.databinding.ItemMessageOtherBinding;
import com.firebase.chat.models.Message;

import java.util.List;

public class ListMessageItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MESSAGE_TYPE_SENDING = 0;
    private static final int MESSAGE_TYPE_RECEIVING = 1;
    private final List<Message> listMessage;

    public ListMessageItemAdapter(List<Message> listMessage) {
        this.listMessage = listMessage;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_TYPE_SENDING) {
            ItemMessageMineBinding itemMessageMineBinding = ItemMessageMineBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ItemMessageMineViewHolder(itemMessageMineBinding);
        } else {
            ItemMessageOtherBinding itemMessageOtherBinding = ItemMessageOtherBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ItemMessageOtherViewHolder(itemMessageOtherBinding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = listMessage.get(position);
        if (message == null) {
            return;
        }
        if (holder instanceof ItemMessageMineViewHolder) {
            ((ItemMessageMineViewHolder) holder).itemMessageMineBinding.setMessage(message);
        } else {
            ((ItemMessageOtherViewHolder) holder).itemMessageOtherBinding.setMessage(message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // TODO: check current user from firebase
//        return (listMessage.get(position).getSenderId().equals(Utils.CURRENT_UID)) ? MESSAGE_TYPE_SENDING : MESSAGE_TYPE_RECEIVING;
        return MESSAGE_TYPE_RECEIVING;
    }

    @Override
    public int getItemCount() {
        if (listMessage == null) {
            return 0;
        }
        return listMessage.size();
    }

    public class ItemMessageMineViewHolder extends RecyclerView.ViewHolder {
        private final ItemMessageMineBinding itemMessageMineBinding;

        public ItemMessageMineViewHolder(@NonNull ItemMessageMineBinding itemMessageMineBinding) {
            super(itemMessageMineBinding.getRoot());
            this.itemMessageMineBinding = itemMessageMineBinding;
        }
    }

    public class ItemMessageOtherViewHolder extends RecyclerView.ViewHolder {
        private final ItemMessageOtherBinding itemMessageOtherBinding;

        public ItemMessageOtherViewHolder(@NonNull ItemMessageOtherBinding itemMessageOtherBinding) {
            super(itemMessageOtherBinding.getRoot());
            this.itemMessageOtherBinding = itemMessageOtherBinding;
        }
    }
}

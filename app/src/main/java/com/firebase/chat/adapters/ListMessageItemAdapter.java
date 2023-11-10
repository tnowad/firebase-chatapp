package com.firebase.chat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.databinding.ItemMessageMineBinding;
import com.firebase.chat.databinding.ItemMessageOtherBinding;
import com.firebase.chat.models.Message;
import com.firebase.chat.services.AuthService;

public class ListMessageItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MESSAGE_TYPE_SENDING = 0;
    private static final int MESSAGE_TYPE_RECEIVING = 1;
    private final ObservableList<Message> messageObservableList;

    public ListMessageItemAdapter(Context context, ObservableList<Message> messageObservableList) {
        this.messageObservableList = messageObservableList;
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
        Message message = messageObservableList.get(position);
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
        return (messageObservableList.get(position).getSenderId().equals(AuthService.getInstance().getCurrentUser().getUid())) ? MESSAGE_TYPE_SENDING : MESSAGE_TYPE_RECEIVING;
    }

    @Override
    public int getItemCount() {
        if (messageObservableList == null) {
            return 0;
        }
        return messageObservableList.size();
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

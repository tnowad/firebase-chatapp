package com.firebase.chat.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.chat.R;
import com.firebase.chat.activities.MessageActivity;
import com.firebase.chat.databinding.ItemChatBinding;
import com.firebase.chat.models.Chat;
import com.firebase.chat.models.User;
import com.firebase.chat.services.AuthService;
import com.firebase.chat.services.MessageService;
import com.firebase.chat.services.UserService;
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
        holder.itemChatBinding.setChat(chat);


        String uid = chat.getParticipants().stream().filter(s -> !s.equals(AuthService.getInstance().getCurrentUser().getUid()))
                .collect(Collectors.toList()).get(0);

        UserService.getInstance().getUserByUid(uid).addOnSuccessListener(user -> {
            if (user != null) {
                holder.itemChatBinding.setUser(user);
                Picasso.get().load(user.getPhotoUrl())
                        .into(holder.itemChatBinding.ChatItemImageViewAvatar);
            }
        });

        if (chat.getLastMessageId() != null) {
            MessageService.getInstance().getMessageById(chat.getLastMessageId()).addOnSuccessListener(message -> {
                if (message != null) {
                    holder.itemChatBinding.setMessage(message);
                    if (chat.getLastMessageSeen() != null &&
                            chat.getLastMessageSeen().get(AuthService.getInstance().getCurrentUser().getUid()) != null &&
                            !chat.getLastMessageSeen().get(AuthService.getInstance().getCurrentUser().getUid()).equals(chat.getLastMessageId())) {
                        holder.setContentTextViewBold();
                    }
                }
            });
        }

        holder.itemChatBinding.setUser(new User("", "", "Loading", ""));


        holder.setOnItemListener(v -> {
            Intent intent = new Intent(context, MessageActivity.class);
            intent.putExtra("chatId", chat.getId());
            context.startActivity(intent);
            Activity activity = (Activity) context;
            activity.overridePendingTransition(0, 0);
        });

        holder.itemChatBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (listChat == null) {
            return 0;
        }
        return listChat.size();
    }

    public static class ChatItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemChatBinding itemChatBinding;

        public ChatItemViewHolder(@NonNull ItemChatBinding itemChatBinding) {
            super(itemChatBinding.getRoot());
            this.itemChatBinding = itemChatBinding;
        }

        public void setOnItemListener(View.OnClickListener onClickListener) {
            this.itemChatBinding.getRoot().setOnClickListener(onClickListener);
        }

        public void setContentTextViewBold() {
            itemChatBinding.ChatItemTextViewLastMessageContent.setTypeface(null, Typeface.BOLD);
        }

    }
}

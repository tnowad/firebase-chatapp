package com.firebase.chat.Interfaces;

import android.view.View;

public interface OnItemClickListener {
    void onMessageItem(View view, int pos);
    void onSearchItem(View view, int pos);
}

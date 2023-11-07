package com.firebase.chat.Interfaces;

import com.firebase.chat.models.User;

public interface OnUserFetchListener {
    void onUserFetchSuccess(User user);

    // Called when the user with the given UID is not found in Firestore
    void onUserNotFound();

    // Called when there's an error while fetching the user
    void onUserFetchError(Exception e);
}

package com.example.palindromechecker.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
    }

    public void setText(String input) {
        mText.setValue(input);
    }

    public LiveData<String> getText() {
        return mText;
    }

}
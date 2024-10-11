package com.example.prakt_8_1_drawer_navigation.ui.newGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ViewModel extends androidx.lifecycle.ViewModel {

    private final MutableLiveData<String> mText;

    public ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Создание группы");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
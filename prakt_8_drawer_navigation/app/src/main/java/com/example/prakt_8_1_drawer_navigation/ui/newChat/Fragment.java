package com.example.prakt_8_1_drawer_navigation.ui.newChat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.prakt_8_1_drawer_navigation.databinding.FragmentNewChatBinding;

public class Fragment extends androidx.fragment.app.Fragment {

    private FragmentNewChatBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewModel createChannelViewModel =
                new ViewModelProvider(this).get(ViewModel.class);

        binding = FragmentNewChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNewChat;
        createChannelViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
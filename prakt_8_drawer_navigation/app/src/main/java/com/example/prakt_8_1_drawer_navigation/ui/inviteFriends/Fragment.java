package com.example.prakt_8_1_drawer_navigation.ui.inviteFriends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.prakt_8_1_drawer_navigation.databinding.FragmentInviteFriendsBinding;

public class Fragment extends androidx.fragment.app.Fragment {

    private FragmentInviteFriendsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewModel createChannelViewModel =
                new ViewModelProvider(this).get(ViewModel.class);

        binding = FragmentInviteFriendsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textInviteFriends;
        createChannelViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
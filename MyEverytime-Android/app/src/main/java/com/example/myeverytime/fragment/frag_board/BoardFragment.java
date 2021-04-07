package com.example.myeverytime.fragment.frag_board;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.myeverytime.R;
import com.example.myeverytime.main.boards.freeboard.FreeBoardActivity;

public class BoardFragment extends Fragment {

    private LinearLayout linearLayout_go_freeboard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, container, false);

        linearLayout_go_freeboard = (LinearLayout)view.findViewById(R.id.linear_layout_frag_board_all_go_to_free_board);

        linearLayout_go_freeboard.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), FreeBoardActivity.class);
            startActivity(intent);

        });
        // Inflate the layout for this fragment
        return view;

    }
}
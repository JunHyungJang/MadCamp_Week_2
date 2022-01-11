package com.example.tournament;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;

public class choose_one_winner extends DialogFragment {

    private ImageView winner;
    private Context context;
    private String URL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.choose_one_winner, container, false);
        winner = root.findViewById(R.id.imageView_winner);
        URL = getArguments().getString("URL");
        context = getContext();
        Glide.with(context).load(Uri.parse("http://192.249.18.154/".concat(URL))).into(winner);

        return root;
    }

}

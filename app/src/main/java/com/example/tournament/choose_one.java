package com.example.tournament;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class choose_one extends Fragment {
    Context context;
    int order;
    private ArrayList<String> imagePaths;
    private ImageView image_first;
    private ImageView image_second;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.choose_one, null);
        context = getContext();
        imagePaths = new ArrayList<>();
        image_first = root.findViewById(R.id.imageView_first);
        image_second = root.findViewById(R.id.imageView_second);
        order = getActivity().getIntent().getIntExtra("order",1);

        image_first.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, choose_one.class);
                i.putExtra("order",order+1);
                context.startActivity(i);
            }
        });

        image_second.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, choose_one.class);
                i.putExtra("order",order+1);
                context.startActivity(i);
            }
        });

        return root;
    }


}

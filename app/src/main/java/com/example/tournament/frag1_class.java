package com.example.tournament;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class frag1_class extends Fragment {

    Context context;
    private ArrayList<String> imagePaths;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    int order=1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment1, null);
        context = getContext();
        imagePaths = new ArrayList<>();
        image1 = root.findViewById(R.id.imageView1);
        image2 = root.findViewById(R.id.imageView2);
        image3 = root.findViewById(R.id.imageView3);
        final Button btn_start=root.findViewById(R.id.button);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, choose_one.class);
                i.putExtra("order",order);
                context.startActivity(i);
            }
        });

        return root;
    }



}

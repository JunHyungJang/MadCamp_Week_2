package com.example.tournament;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class choose_one extends Fragment {
    Context context;
    int order;
    int inorder;
    ArrayList<Integer> BitImages;
    private ImageView image_first;
    private ImageView image_second;
    private ProgressBar progressBar;
    int cur_progress;
    int first,second;
    Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.choose_one, container,false);
        context = getContext();
        progressBar = new ProgressBar(context);

        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setMax(80);
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));

        BitImages = new ArrayList<>();

        image_first = root.findViewById(R.id.imageView_first);
        image_second = root.findViewById(R.id.imageView_second);
        order = getArguments().getInt("order");
        BitImages = getArguments().getIntegerArrayList("BitImages");

        if(order<=7){
            cur_progress=0;
            progressBar.setProgress(cur_progress);
            inorder = order;
            first = inorder;
            second = inorder+1;
        }
        else if(order<=11){
            cur_progress=25;
            progressBar.setProgress(cur_progress);
            inorder = order-8;
            first = inorder;
            second = inorder+1;
        }
        else if(order<=13){
            cur_progress=50;
            progressBar.setProgress(cur_progress);
            inorder = order-12;
            first = inorder;
            second = inorder+1;
        }
        else{
            cur_progress=75;
            progressBar.setProgress(cur_progress);
            inorder = order-14;
            first = inorder;
            second = inorder+1;
        }

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), BitImages.get(first));
        image_first.setImageBitmap(bitmap1);

        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), BitImages.get(second));
        image_second.setImageBitmap(bitmap2);



        bundle = new Bundle();


        image_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order == 14) {
                    BitImages.remove(second);
                    bundle.putIntegerArrayList("BitImages",BitImages);
                    Fragment fragment = new tournament_winner();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(), fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    BitImages.remove(second);
                    bundle.putInt("order",order+1);
                    bundle.putIntegerArrayList("BitImages",BitImages);
                    Fragment fragment = new choose_one();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(), fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });

        image_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order == 14) {
                    BitImages.remove(first);
                    bundle.putIntegerArrayList("BitImages",BitImages);
                    Fragment fragment = new tournament_winner();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(), fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    BitImages.remove(first);
                    bundle.putInt("order",order+1);
                    bundle.putIntegerArrayList("BitImages",BitImages);
                    Fragment fragment = new choose_one();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(), fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
        return root;
    }
}
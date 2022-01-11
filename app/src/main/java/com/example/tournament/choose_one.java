package com.example.tournament;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class choose_one extends Fragment {
    Context context;
    int order;
    int inorder;
    int start_round;
    private ArrayList<String> URL_list;
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
        progressBar.setMax(100);
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));

        URL_list = new ArrayList<>();

        image_first = root.findViewById(R.id.imageView_first);
        image_second = root.findViewById(R.id.imageView_second);
        order = getArguments().getInt("order");
        URL_list = getArguments().getStringArrayList("URL_list");

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

        Glide.with(context).load(Uri.parse("http://192.249.18.154/".concat(URL_list.get(first)))).into(image_first);
        Glide.with(context).load(Uri.parse("http://192.249.18.154/".concat(URL_list.get(second)))).into(image_second);
        bundle = new Bundle();

        Bundle d_args = new Bundle();


        image_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order == 14) {
                    d_args.putString("URL",URL_list.get(first));
                    choose_one_winner dialog = new choose_one_winner();
                    dialog.setArguments(d_args);
                    dialog.show(getChildFragmentManager(),"Dialog");


                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            dialog.dismiss();
                            URL_list.remove(second);
                            bundle.putStringArrayList("URL_list",URL_list);
                            Fragment fragment = new tournament_winner();
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(), fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    }, 600);// 0.6초 정도 딜레이를 준 후 시작


                } else {
                    d_args.putString("URL",URL_list.get(first));
                    choose_one_winner dialog = new choose_one_winner();
                    dialog.setArguments(d_args);
                    dialog.show(getChildFragmentManager(),"Dialog");
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            dialog.dismiss();
                            URL_list.remove(second);
                            bundle.putInt("order",order+1);
                            bundle.putStringArrayList("URL_list",URL_list);
                            Fragment fragment = new choose_one();
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(), fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    }, 600);// 0.6초 정도 딜레이를 준 후 시작


                }
            }
        });

        image_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order == 14) {
                    d_args.putString("URL",URL_list.get(second));
                    choose_one_winner dialog = new choose_one_winner();
                    dialog.setArguments(d_args);
                    dialog.show(getChildFragmentManager(),"Dialog");

                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            dialog.dismiss();

                            URL_list.remove(first);
                            bundle.putStringArrayList("URL_list",URL_list);
                            Fragment fragment = new tournament_winner();
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(), fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    }, 600);// 0.6초 정도 딜레이를 준 후 시작

                } else {
                    d_args.putString("URL",URL_list.get(second));
                    choose_one_winner dialog = new choose_one_winner();
                    dialog.setArguments(d_args);
                    dialog.show(getChildFragmentManager(),"Dialog");
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            dialog.dismiss();
                            URL_list.remove(first);
                            bundle.putInt("order",order+1);
                            bundle.putStringArrayList("URL_list",URL_list);
                            Fragment fragment = new choose_one();
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(), fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                        }
                    }, 600);// 0.6초 정도 딜레이를 준 후 시작

                }
            }
        });
        return root;
    }
}
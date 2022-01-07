package com.example.tournament;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class tournament_start extends Fragment {

    Context context;
    private ArrayList<Integer> BitImages;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    int order=0;
    Bundle bundle;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tournament_start, container,false);
        context = getContext();
        BitImages = new ArrayList<>();
        image1 = root.findViewById(R.id.imageView1);
        image2 = root.findViewById(R.id.imageView2);
        image3 = root.findViewById(R.id.imageView3);
        final Button btn_start=root.findViewById(R.id.button);
        BitImages.add(R.drawable.images1);
        BitImages.add(R.drawable.images2);
        BitImages.add(R.drawable.images3);
        BitImages.add(R.drawable.images4);
        BitImages.add(R.drawable.images5);
        BitImages.add(R.drawable.images6);
        BitImages.add(R.drawable.images7);
        BitImages.add(R.drawable.images8);
        BitImages.add(R.drawable.images9);
        BitImages.add(R.drawable.images10);
        BitImages.add(R.drawable.images11);
        BitImages.add(R.drawable.images12);
        BitImages.add(R.drawable.images13);
        BitImages.add(R.drawable.images14);
        BitImages.add(R.drawable.images15);
        BitImages.add(R.drawable.images16);


        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),BitImages.get(0));
        image1.setImageBitmap(bitmap1);

        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),BitImages.get(1));
        image2.setImageBitmap(bitmap2);

        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(),BitImages.get(2));
        image3.setImageBitmap(bitmap3);

        bundle = new Bundle();

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putInt("order",order);
                bundle.putIntegerArrayList("BitImages",BitImages);
                Fragment fragment = new choose_one();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(),fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return root;
    }



}

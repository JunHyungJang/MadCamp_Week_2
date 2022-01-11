package com.example.tournament;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.InputStreamReader;
public class tournament_start extends Fragment implements Dialog.OnInputSelected{

    private static final String TAG = "tournament_start";

    Context context;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private Button start_world_cup;
    private ArrayList<String> URL_list;
    int order = 0;
    Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tournament_start, container, false);
        context = getContext();
        image1 = root.findViewById(R.id.imageView1);
        image2 = root.findViewById(R.id.imageView2);
        image3 = root.findViewById(R.id.imageView3);
        final Button btn_start = root.findViewById(R.id.button);
        start_world_cup = root.findViewById(R.id.button2);

        Glide.with(context).load(Uri.parse("http://192.249.18.154/images1.bmp")).into(image1);
        Glide.with(context).load(Uri.parse("http://192.249.18.154/images2.bmp")).into(image2);
        Glide.with(context).load(Uri.parse("http://192.249.18.154/images3.bmp")).into(image3);


        bundle = new Bundle();
        URL_list = new ArrayList<>();

        start_world_cup.setEnabled(false);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog();
                dialog.show(getChildFragmentManager(),"Dialog");
                start_world_cup.setEnabled(true);
            }
        });


        start_world_cup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                bundle.putInt("order",order);
                bundle.putStringArrayList("URL_list",URL_list);
                Fragment fragment = new choose_one();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(), fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

        });
        return root;
    }

    @Override
    public void sendInput(ArrayList arrayList) {
        URL_list = arrayList;
    }
}

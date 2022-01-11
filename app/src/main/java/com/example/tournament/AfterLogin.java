package com.example.tournament;

import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

public class AfterLogin extends Fragment {

    private String strNick, strProfileImg,strEmail;
    private Context context;
    Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.afterlogin, container,false);
        context = getContext();

        strNick = getArguments().getString("name");
        strProfileImg = getArguments().getString("ProfileImage");
        strEmail = getArguments().getString("email");

        Bundle result = new Bundle();
        result.putString("bundlekey",strEmail);
        getParentFragmentManager().setFragmentResult("key",result);






        TextView tv_nick = root.findViewById(R.id.tv_nickName);
        TextView tv_email = root.findViewById(R.id.tv_email);
        ImageView iv_profile = root.findViewById(R.id.iv_profile);




        //닉네임 set
        tv_nick.setText(strNick);
        //이메일 set
        tv_email.setText(strEmail);
        //프로필 이미지사진 set
        Glide.with(context).load(strProfileImg).into(iv_profile);

        return root;
    }
}

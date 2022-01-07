package com.example.tournament;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class LoginFragment extends Fragment {

    private String strNick, strProfileImg,strEmail;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login, container,false);
        context = getContext();

        strNick = getArguments().getString("name");
        strProfileImg = getArguments().getString("ProfileImage");
        strEmail = getArguments().getString("email");

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

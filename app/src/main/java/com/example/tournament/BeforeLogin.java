package com.example.tournament;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

public class BeforeLogin extends Fragment {
    private ISessionCallback mSessionCallback;
    Context context;
    Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.beforelogin, container,false);
        context = getContext();
        mSessionCallback = new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                //로그인 요청
                UserManagement.getInstance().me(new MeV2ResponseCallback()
                {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        //세션이 닫힘..
                        Toast.makeText(context, "세션이 닫혔습니다... 다시 시도 해주세요", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        // 로그인 실패..
                        Toast.makeText(context, "로그인 도중에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        // 로그인 성공..
                        bundle = new Bundle();
                        bundle.putString("name",result.getKakaoAccount().getProfile().getNickname());
                        bundle.putString("ProfileImage",result.getKakaoAccount().getProfile().getProfileImageUrl());
                        bundle.putString("email",result.getKakaoAccount().getEmail());
                        Fragment fragment = new AfterLogin();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId(),fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                        Toast.makeText(context, "환영합니다 !", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {
                Toast.makeText(context,"onSessionOpenFailed",Toast.LENGTH_SHORT).show();
            }
        };
        Session.getCurrentSession().addCallback(mSessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();


        return root;
    }



}

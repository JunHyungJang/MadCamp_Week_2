package com.example.tournament;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.InputStreamReader;
public class tournament_start extends Fragment {

    Context context;
    private ArrayList<Integer> BitImages;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    int order = 0;

    Bundle bundle;
    Button btn_worldcup;

    String address = "http://172.10.18.154";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tournament_start, container, false);
        context = getContext();
        BitImages = new ArrayList<>();
        image1 = root.findViewById(R.id.imageView1);
        image2 = root.findViewById(R.id.imageView2);
        image3 = root.findViewById(R.id.imageView3);
        btn_worldcup = root.findViewById(R.id.btn_worldcup);
        final Button btn_start = root.findViewById(R.id.button);
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


        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), BitImages.get(0));
        image1.setImageBitmap(bitmap1);

        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), BitImages.get(1));
        image2.setImageBitmap(bitmap2);
        Glide.with(context).load(Uri.parse("http://172.10.18.154/hello")).into(image3);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), BitImages.get(2));

        bundle = new Bundle();





        btn_worldcup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test","button");
                 new JSONTask().execute("http://192.249.18.154/name");

                    }
                });



        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putInt("order", order);
                bundle.putIntegerArrayList("BitImages", BitImages);
                Fragment fragment = new choose_one();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(((ViewGroup) getView().getParent()).getId(), fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return root;
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
//                JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.accumulate("user_id", "androidTest");
//                jsonObject.accumulate("name", "yun");

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(urls[0]);//url을 가져온다.
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();//연결 수행

                    //입력 스트림 생성
                    InputStream stream = con.getInputStream();

                    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.
                    reader = new BufferedReader(new InputStreamReader(stream));

                    //실제 데이터를 받는곳
                    StringBuffer buffer = new StringBuffer();

                    //line별 스트링을 받기 위한 temp 변수
                    String line = "";

                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
                    return buffer.toString();

                    //아래는 예외처리 부분이다.
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //종료가 되면 disconnect메소드를 호출한다.
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        //버퍼를 닫아준다.
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }//finally 부분
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Log.d("test",result);


        }

    }
}

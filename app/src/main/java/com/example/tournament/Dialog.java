package com.example.tournament;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Dialog extends DialogFragment implements AsyncResponse {

    private static final String TAG = "Dialog";

    private Button mActionStart;
    private SeekBar seekBar;
    private int order;
    private Context context;
    private MaterialButtonToggleGroup toggleGroup;
    JSONTask asyncTask =new JSONTask();

    private ArrayList<String> URL_list;
    private String[] file_list;
    private Bundle bundle;

    //
    private String type;
    private String start_round;

    public interface OnInputSelected{
        void sendInput(ArrayList arrayList);
    }

    public OnInputSelected mOnInputSelected;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog, container, false);
        mActionStart = root.findViewById(R.id.start);
        seekBar = root.findViewById(R.id.seekBar);
        toggleGroup = root.findViewById(R.id.toggleButtonGroup);
        context = getContext();
        asyncTask.delegate = this;
        bundle = new Bundle();
        URL_list = new ArrayList<>();
        type = "male";
        start_round = "16";
        order = 0;




        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                    if (checkedId == R.id.male) {
                        type = "male";
                    } else if (checkedId == R.id.female) {
                        type = "female";
                    } else if (checkedId == R.id.cat) {
                        type = "cat";
                    }
            }
        });


        mActionStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String URL = "http://192.249.18.154/worldcup/".concat(type).concat("/").concat(start_round);
                Log.d("test","button");

                asyncTask.execute(URL);
                mOnInputSelected.sendInput(URL_list);
                getDialog().dismiss();
            }

        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (progress == 0) {
                        start_round = "16";
                    } else if (progress == 1) {
                        start_round = "8";
                    } else if (progress == 2) {
                        start_round = "4";
                    } else {
                        start_round = "2";
                    }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Write code to perform some action when touch is started.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Write code to perform some action when touch is stopped.
            }
        });

        return root;
    }


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            mOnInputSelected = (OnInputSelected) getParentFragment();
        }catch(ClassCastException e){
            Log.e(TAG,"onAttach:ClassCastException:" + e.getMessage());
        }
    }

    @Override
    public void processFinish(String output) {
        file_list = output.substring(1,output.length()-1).split(",");
        for(int i=0;i< file_list.length;i++){
            String str = file_list[i].substring(1,file_list[i].length()-1);
            URL_list.add(str);
        }
        bundle.putInt("order", order);
        bundle.putStringArrayList("URL_list", URL_list);
    }

    public class JSONTask extends AsyncTask<String, String, String> {
        public AsyncResponse delegate = null;


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
            delegate.processFinish(result);


        }

    }

}

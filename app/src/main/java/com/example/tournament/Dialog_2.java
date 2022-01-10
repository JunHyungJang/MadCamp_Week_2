package com.example.tournament;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButtonToggleGroup;

public class Dialog_2 extends DialogFragment {
    Context context;
    private MaterialButtonToggleGroup together;
    private Button ActionStart;
    Bundle bundle;
    public OnInputSelected mOnInputSelected;


    String type ;


    public interface OnInputSelected{
        void sendInput(String type);
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.checking, container, false);
        together = root.findViewById(R.id.together);
        ActionStart = root.findViewById(R.id.start_1);

        context = getContext();

        if (getArguments()!=null) {
            String account = getArguments().getString("account");
            Log.d("test",account);
        } Log.d("test","test");


        together.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(checkedId==R.id.male_1){
                    type = "male";
                }
                else if(checkedId==R.id.female_1){
                    type = "female";

                }
                else if(checkedId==R.id.cat_1){
                    type = "cat";

                }
                else{

                }
            }
        });

        ActionStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String type_ = type;
                mOnInputSelected.sendInput(type_);


                getDialog().dismiss();


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
            Log.e("hello","onAttach:ClassCastException:" + e.getMessage());
        }
    }



}


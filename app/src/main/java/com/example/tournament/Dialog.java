package com.example.tournament;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButtonToggleGroup;

public class Dialog extends DialogFragment {

    private Button mActionStart;
    private SeekBar seekBar;
    private int start_round;
    private Context context;
    private MaterialButtonToggleGroup toggleGroup;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog, container, false);
        mActionStart = root.findViewById(R.id.start);
        seekBar = root.findViewById(R.id.seekBar);
        toggleGroup = root.findViewById(R.id.toggleButtonGroup);
        context = getContext();

        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(checkedId==R.id.male){

                }
                else if(checkedId==R.id.female){

                }
                else if(checkedId==R.id.cat){

                }
                else{

                }
            }
        });


        mActionStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getDialog().dismiss();
            }

        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress==0){
                    start_round=16;
                }
                else if(progress==1){
                    start_round=8;
                }
                else if(progress==2){
                    start_round=4;
                }
                else{
                    start_round=2;
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Write code to perform some action when touch is started.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Write code to perform some action when touch is stopped.
                Toast.makeText(context, "Current value is " + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}

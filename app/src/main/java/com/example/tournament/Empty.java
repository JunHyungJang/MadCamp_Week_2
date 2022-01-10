package com.example.tournament;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Empty extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty);
        Intent intent = getIntent();
        String account = intent.getStringExtra("type");

        CameraMain fragment = new CameraMain();
        Bundle bundle = new Bundle();
        bundle.putString("type",account);
        fragment.setArguments(bundle);
        finish();
    }
}

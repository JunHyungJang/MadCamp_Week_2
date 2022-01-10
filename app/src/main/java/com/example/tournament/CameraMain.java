package com.example.tournament;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraMain extends Fragment {
    TextView tvData;
    Button btn_camera, btn_push, btn_gallery;
    String strNick, strProfileImg,strEmail;
    ImageView imageView;
    Bitmap bitmap;
    byte[] bitmapdata;
    FileOutputStream fos = null;
    File sendFile;
    Context context;
    Integer GET_GALLERY_IMAGE = 200;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.camera_main, container,false);
        btn_camera = root.findViewById(R.id.btn);
        btn_push = root.findViewById(R.id.btn2);
        btn_gallery = root.findViewById(R.id.btn_gallery);
        imageView = root.findViewById(R.id.imageview);
        tvData = root.findViewById(R.id.tvData);
        context = getContext();

        Bundle bundle = getArguments();

        if (bundle!=null) {

            strNick = getArguments().getString("name");
            Log.d("test",strNick);
            strProfileImg = getArguments().getString("ProfileImage");
            strEmail = getArguments().getString("email");
        }
//        Log.d("test",strNick);


        fos = null;

        btn_push.setEnabled(false);

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        btn_push.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FileUploadUtils.send2Server(sendFile);
            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 0);
            }
        });

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            Bundle extras = data.getExtras();

            bitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            bitmapdata = bos.toByteArray();
            try {
                sendFile = new File(context.getCacheDir(),"image");
                sendFile.createNewFile();
                fos = new FileOutputStream(sendFile);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            btn_push.setEnabled(true);
            imageView.setImageBitmap(bitmap);
        }

        if (requestCode == GET_GALLERY_IMAGE){
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}}

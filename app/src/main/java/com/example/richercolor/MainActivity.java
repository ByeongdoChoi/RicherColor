package com.example.richercolor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.xw.repo.BubbleSeekBar;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import java.util.Collections;
import java.util.List;

import static android.Manifest.permission.CAMERA;


public class MainActivity extends AppCompatActivity {

    public native void ConvertRGBtoGray(long matAddrInput, long matAddrResult);


    static {
        System.loadLibrary("opencv_java4");
        System.loadLibrary("native-lib");
    }

    String[] permission_list = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    Button takePhotoBtn;
    Button getPhotoBtn;
    Button viewArtBtn;
    Button testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission_list, 0);
        }

        takePhotoBtn = findViewById(R.id.take_photo_btn);
        getPhotoBtn = findViewById(R.id.get_photo_btn);
        viewArtBtn = findViewById(R.id.view_art);
        testBtn = findViewById(R.id.color_test);


        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        getPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoto();
            }
        });

        viewArtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFamous();
            }
        });

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTest();
            }
        });
    }

    private void takePhoto() {
        Intent intent = new Intent(this, Photo.class);
        startActivity(intent);
    }

    private void getPhoto() {
        Intent intent = new Intent(this, Album.class);
        startActivity(intent);
    }

    private void getFamous() {
        Intent intent = new Intent(this, Famous.class);
        startActivity(intent);
    }

    private void getTest(){
        Intent intent = new Intent(this,rgbtest.class);
        startActivity(intent);
    }



}
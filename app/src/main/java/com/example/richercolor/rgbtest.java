package com.example.richercolor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class rgbtest extends AppCompatActivity {

    private ImageView imgtest;

    private SeekBar seekBarred;
    private SeekBar seekBargreen;
    private SeekBar seekBarblue;

    Button button, button2, button3;
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgbtest);

        imgtest = (ImageView)findViewById(R.id.imgtest);
        seekBarred = (SeekBar)findViewById(R.id.seekBarred);
        seekBargreen = (SeekBar)findViewById(R.id.seekBargreen);
        seekBarblue = (SeekBar)findViewById(R.id.seekBarblue);
        button = findViewById(R.id.btn_color_change);
        button2 = findViewById(R.id.btn_color_change2);
        button3 = findViewById(R.id.btn_color_change3);
        button4 = findViewById(R.id.btn_color_change4);
        /*

        seekBarred.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int red = seekBarred.getProgress();
                int green = seekBargreen.getProgress();
                int blue = seekBarblue.getProgress();
                imgtest.setColorFilter(Color.rgb(red,green,blue),PorterDuff.Mode.LIGHTEN);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBargreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int red = seekBarred.getProgress();
                int green = seekBargreen.getProgress();
                int blue = seekBarblue.getProgress();
                imgtest.setColorFilter(Color.rgb(red,green,blue),PorterDuff.Mode.LIGHTEN);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarblue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int red = seekBarred.getProgress();
                int green = seekBargreen.getProgress();
                int blue = seekBarblue.getProgress();
                imgtest.setColorFilter(Color.rgb(red,green,blue),PorterDuff.Mode.LIGHTEN);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorMatrix result = new ColorMatrix(ColorMatrices.SWAP_R_G);
                imgtest.setColorFilter(new ColorMatrixColorFilter(result));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorMatrix result = new ColorMatrix(ColorMatrices.SWAP_R_B);
                imgtest.setColorFilter(new ColorMatrixColorFilter(result));
            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorMatrix result = new ColorMatrix(ColorMatrices.SWAP_G_B);
                imgtest.setColorFilter(new ColorMatrixColorFilter(result));
            }
        });*/

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imgtest.setImageResource(R.drawable.cow);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.cow);

                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                int size = w*h;

                int[] pixels = new int[size];

                bitmap.getPixels(pixels, 0, w, 0, 0, w, h);

                /*
                Log.d("rgbtest", ""+pixels.length);
                for (int i=0; i<pixels.length; i++) {
                    Log.d("rgbtest", "pixels"+i + " " + pixels[i]);
                }*/

                for(int i=0; i<size; i++)
                {
                    int color = pixels[i];

                    int r = (color >> 16) & 0xFF;
                    int g = (color >> 8) & 0xFF;
                    int b = (color) & 0xFF;

                    Log.d("rgbtest", "r"+i + " " + r);
                    Log.d("rgbtest", "g"+i + " " + g);
                    Log.d("rgbtest", "b"+i + " " + b);

                    Log.d("rgbtest", "pixels"+i + " " + color);

                }
                imgtest.setImageBitmap(bitmap);

            }
        });


    }



}
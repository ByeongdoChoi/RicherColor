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
                Bitmap bitmap2 = resizeBitmap(512, bitmap);

                int w = bitmap2.getWidth();
                int h = bitmap2.getHeight();

                int size = w*h;

                int[] pixels = new int[size];

                bitmap2.getPixels(pixels, 0, w, 0, 0, w, h);

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

                    color |= 0x00 << 16;
                    color |= 0xFF << 8;
                    color |= 0x00;

                    int r2 = (color >> 16) & 0xFF;
                    int g2 = (color >> 8) & 0xFF;
                    int b2 = (color) & 0xFF;

                    Log.d("rgbtest", "r2"+i + " " + r2);
                    Log.d("rgbtest", "g2"+i + " " + g2);
                    Log.d("rgbtest", "b2"+i + " " + b2);

                    pixels[i] = color;

                }
                bitmap2.setPixels(pixels, 0, w, 0, 0, w, h);

                imgtest.setImageBitmap(bitmap2);

            }
        });

    }

    public Bitmap resizeBitmap(int targetWidth, Bitmap source) {
        double ratio = (double) targetWidth / (double) source.getWidth();
        int targetHeight = (int) (source.getHeight() * ratio);
        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
        if (result != source)
            source.recycle();
        return result;
    }

}
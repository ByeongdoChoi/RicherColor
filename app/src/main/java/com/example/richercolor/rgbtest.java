package com.example.richercolor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class rgbtest extends AppCompatActivity {

    private ImageView imgtest;

    private SeekBar seekBarred;
    private SeekBar seekBargreen;
    private SeekBar seekBarblue;

    Button button, button2, button3;

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
        });



    }



}
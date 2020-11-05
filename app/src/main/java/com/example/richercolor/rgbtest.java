package com.example.richercolor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.ColorSpace;
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

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;

public class rgbtest extends AppCompatActivity {

    private ImageView imgtest;

    private SeekBar seekBarred;
    private SeekBar seekBargreen;
    private SeekBar seekBarblue;

    Button button, button2, button3;
    Button button4;

    Scalar lower_blue = new Scalar(110, 100, 100);
    Scalar upper_blue = new Scalar(130, 255, 255);
    Scalar lower_green = new Scalar(50, 100, 100);
    Scalar upper_green = new Scalar(70, 255, 255);
    Scalar lower_red = new Scalar(-10, 100, 100);
    Scalar upper_red = new Scalar(10, 255, 255);

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

                // 이미지 크기 조정
                Bitmap bitmap2 = resizeBitmap(512, bitmap);

                /*int w = bitmap2.getWidth();
                int h = bitmap2.getHeight();

                int size = w*h;
                int[] pixels = new int[size];
                bitmap2.getPixels(pixels, 0, w, 0, 0, w, h);*/

                // bitmap2를 Mat 객체로 변환
                Mat original = new Mat (bitmap2.getWidth(), bitmap2.getHeight(), CvType.CV_8UC3);
                Utils.bitmapToMat(bitmap2, original);

                // rgb를 hsv 변환
                Mat hsv_original = new Mat();
                Imgproc.cvtColor(original, hsv_original, Imgproc.COLOR_RGB2HSV);

                // 빨간색 영역을 구하는 마스크 생성
                Mat mask = new Mat();
                Core.inRange(hsv_original, lower_red, upper_red, mask);

                // 빨간색 영역만 구한 사진
                Mat change = new Mat();
                Core.bitwise_and(hsv_original, hsv_original, change, mask);

                // 빨간색 영역을 제외한 사진
                Mat unChange = new Mat();
                Core.bitwise_xor(hsv_original, change, unChange);
                Mat copychange = new Mat();

                // 여기서 오류
                // 빨간색 영역을 다른 색으로 바꾼다.
                /*
                Mat result = new Mat();
                Imgproc.cvtColor(change, change, 40);

                // unchange와 change를 합쳐서 result에 저장
                Core.bitwise_or(change, unChange, result);
                */

                // result 그림을 다시 rgb로 바꿈
                Imgproc.cvtColor(change, change, 55);

                // Mat 객체를 Bitmap 객체로 바꿈
                Utils.matToBitmap(change, bitmap2);

                imgtest.setImageBitmap(bitmap2);
                imgtest.

                /*Mat input = new Mat();
                Bitmap bmp32 = bitmap2.copy(Bitmap.Config.ARGB_8888, true);
                Utils.bitmapToMat(bmp32, input);

                Mat bgrMat = new Mat();

                Imgproc.cvtColor(input, bgrMat, Imgproc.COLOR_RGB2HSV);

                Mat hsvMat = new Mat();

                Imgproc.cvtColor(bgrMat, hsvMat, Imgproc.COLOR_BGR2HSV);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                Utils.matToBitmap(hsvMat, image);*/

                /*
                Log.d("rgbtest", ""+pixels.length);
                for (int i=0; i<pixels.length; i++) {
                    Log.d("rgbtest", "pixels"+i + " " + pixels[i]);
                }*/

                /*for(int i=0; i<size; i++)
                {
                    int color = pixels[i];

                    int r = (color >> 16) & 0xFF;
                    int g = (color >> 8) & 0xFF;
                    int b = (color) & 0xFF;

                    Log.d("rgbtest", "r"+i + " " + r);
                    Log.d("rgbtest", "g"+i + " " + g);
                    Log.d("rgbtest", "b"+i + " " + b);

                    Log.d("rgbtest", "pixels"+i + " " + color);

                }*/


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
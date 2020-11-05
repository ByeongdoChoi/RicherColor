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
                Bitmap bitmap2 = resizeBitmap(512, bitmap); // 이미지 크기 조정

                int w = bitmap2.getWidth(); //원본의 w사이즈
                int h = bitmap2.getHeight(); //원본의 h사이즈
                int size = w * h; //원본 사이즈

                int[] real_pixels = new int[size]; //원본 픽셀
                int[] extract_pixels = new int[size]; //추출된 색상 픽셀
                int[] changed_pixels = new int[size]; //최종적으로 바꿀 픽셀

                bitmap2.getPixels(real_pixels, 0, w, 0, 0, w, h); //real_pixels에 원본 픽셀을 넣음



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

                // result 그림을 다시 rgb로 바꿈
                Imgproc.cvtColor(change, change, 55);

                // Mat 객체를 Bitmap 객체로 바꿈
                Utils.matToBitmap(change, bitmap2);

                bitmap2.getPixels(extract_pixels, 0, w, 0, 0, w, h); //extract_pixels에는 추출된 색상이 저장, 추출되지 않은 색상은 검정색으로

                for(int i=0; i<size; i++)
                {
                    int color = extract_pixels[i];

                    int r = (color >> 16) & 0xFF;
                    int g = (color >> 8) & 0xFF;
                    int b = (color) & 0xFF;

                    if(r==0x00 && g==0x00 && b==0x00) //검정색일때
                    {
                        changed_pixels[i] = real_pixels[i];
                    }

                    else //검은색이 아닐때(추출한 색일때), 일단 이거는 추출색을 초록색으로 바꾼것
                    {
                        int change_color = color;
                        change_color |= 0x00 << 16;
                        change_color |= 0xFF << 8;
                        change_color |= 0x00;
                        changed_pixels[i] = change_color;
                    }
                }

                bitmap2.setPixels(changed_pixels,0, w, 0, 0, w, h);
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
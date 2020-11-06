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


        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imgtest.setImageResource(R.drawable.cow);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.cow);
                Bitmap bitmapa = BitmapFactory.decodeResource(getResources(),R.drawable.cow);
                Bitmap bitmapb = BitmapFactory.decodeResource(getResources(),R.drawable.cow);

                Bitmap bitmap2 = resizeBitmap(1024, bitmap); // 이미지 크기 조정

                Bitmap bitmapA = resizeBitmap(1024, bitmapa); // 색A를 추출할 비트맵
                Bitmap bitmapB = resizeBitmap(1024, bitmapb); // 색B를 추출할 비트맵

                int w = bitmap2.getWidth(); //원본의 w사이즈
                int h = bitmap2.getHeight(); //원본의 h사이즈
                int size = w * h; //원본 사이즈

                int[] real_pixels = new int[size]; //원본 픽셀
                int[] extract_pixelsA = new int[size]; //추출된 색상 픽셀
                int[] extract_pixelsB = new int[size]; //추출된 색상 픽셀
                int[] changed_pixels = new int[size]; //최종적으로 바꿀 픽셀

                bitmap2.getPixels(real_pixels, 0, w, 0, 0, w, h); //real_pixels에 원본 픽셀을 넣음

                // bitmapA를 Mat 객체로 변환
                Mat originalA = new Mat (bitmapA.getWidth(), bitmapA.getHeight(), CvType.CV_8UC3);
                Utils.bitmapToMat(bitmapA, originalA);

                // bitmapB를 Mat 객체로 변환
                Mat originalB = new Mat (bitmapB.getWidth(), bitmapB.getHeight(), CvType.CV_8UC3);
                Utils.bitmapToMat(bitmapB, originalB);

                // rgb를 hsv 변환
                Mat hsv_originalA = new Mat();
                Imgproc.cvtColor(originalA, hsv_originalA, Imgproc.COLOR_RGB2HSV);

                Mat hsv_originalB = new Mat();
                Imgproc.cvtColor(originalB, hsv_originalB, Imgproc.COLOR_RGB2HSV);

                // 빨간색 영역을 구하는 마스크 생성
                Mat mask = new Mat();
                Core.inRange(hsv_originalA, lower_red, upper_red, mask);

                // 초록색 영역을 구하는 마스크 생성
                Mat mask2 = new Mat();
                Core.inRange(hsv_originalB, lower_green, upper_green, mask2);

                // 빨간색 영역만 구한 사진
                Mat changeA = new Mat();
                Core.bitwise_and(hsv_originalA, hsv_originalA, changeA, mask);

                // 초록색 영역만 구한 사진
                Mat changeB = new Mat();
                Core.bitwise_and(hsv_originalB, hsv_originalB, changeB, mask2);

                // 빨간색 영역을 제외한 사진
                Mat unChangeA = new Mat();
                Core.bitwise_xor(hsv_originalA, changeA, unChangeA);

                // 초록색 영역을 제외한 사진
                Mat unChangeB = new Mat();
                Core.bitwise_xor(hsv_originalB, changeB, unChangeB);

                // result 그림을 다시 rgb로 바꿈
                Imgproc.cvtColor(changeA, changeA, Imgproc.COLOR_HSV2RGB);
                Imgproc.cvtColor(changeB, changeB, Imgproc.COLOR_HSV2RGB);

                // Mat 객체를 Bitmap 객체로 바꿈
                Utils.matToBitmap(changeA, bitmapA);
                Utils.matToBitmap(changeB, bitmapB);

                bitmapA.getPixels(extract_pixelsA, 0, w, 0, 0, w, h); //extract_pixels에는 추출된 색상이 저장, 추출되지 않은 색상은 검정색으로
                bitmapB.getPixels(extract_pixelsB, 0, w, 0, 0, w, h); //extract_pixels에는 추출된 색상이 저장, 추출되지 않은 색상은 검정색으로

                for(int i=0; i<size; i++)
                {
                    int colorA = extract_pixelsA[i];

                    int rA = (colorA >> 16) & 0xFF;
                    int gA = (colorA >> 8) & 0xFF;
                    int bA = (colorA) & 0xFF;

                    if(rA==0x00 && gA==0x00 && bA==0x00) //검정색일때
                    {
                        changed_pixels[i] = real_pixels[i];
                    }

                    else //검은색이 아닐때(추출한 색일때), 빨간색의 크기를 키움
                    {
                        int change_color = colorA;
                        change_color |= 0xFF << 16;
                        change_color |= 0x00 << 8;
                        change_color |= 0x00;
                        changed_pixels[i] = change_color;
                    }
                }

                for(int i=0; i<size; i++)
                {
                    int colorB = extract_pixelsB[i];

                    int rB = (colorB >> 16) & 0xFF;
                    int gB = (colorB >> 8) & 0xFF;
                    int bB = (colorB) & 0xFF;

                    if(rB==0x00 && gB==0x00 && bB==0x00) //검정색일때
                    {
                        if(changed_pixels[i] != real_pixels[i]) //빨간색에서 바뀐것일때
                            continue;

                        else
                            changed_pixels[i] = real_pixels[i];
                    }

                    else //검은색이 아닐때(추출한 색일때), 초록색의 크기를 키움
                    {
                        int change_color = colorB;
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
package com.example.richercolor;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.Random;

public class Famous extends AppCompatActivity {
    private int CURRENT_INDEX;
    private ImageView image;
    private int num,prevnum;

    Bitmap albumImage;

    Button art_redgreen, art_yellowblue, art_original;
    Button art_plus, art_minus;

    private int[] img= {R.drawable.art1, R.drawable.art2, R.drawable.art3, R.drawable.art4, R.drawable.art5,
            R.drawable.art6, R.drawable.art7, R.drawable.art8, R.drawable.art9, R.drawable.art10,
            R.drawable.art11, R.drawable.art12, R.drawable.art13, R.drawable.art14, R.drawable.art15,
            R.drawable.art16, R.drawable.art17, R.drawable.art18, R.drawable.art19, R.drawable.art20,
            R.drawable.art21, R.drawable.art22, R.drawable.art23, R.drawable.art24, R.drawable.art25,
            R.drawable.art26, R.drawable.art27, R.drawable.art28, R.drawable.art29, R.drawable.art30,
            R.drawable.art31, R.drawable.art32, R.drawable.art33, R.drawable.art34, R.drawable.art35,
            R.drawable.art36, R.drawable.art37, R.drawable.art38, R.drawable.art39, R.drawable.art40,
    };

    //색 변화
    boolean setPicture = false;

    Bitmap originalPicture; // 원본 사진

    int clicked_button_redgreen = 0;
    int clicked_button_yellowblue = 0;
    int clicked_button_original = 0;
    int plus_cnt = 0;
    int minus_cnt = 0;
    int button_cnt = 0;

    Scalar lower_blue = new Scalar(110, 100, 100);
    Scalar upper_blue = new Scalar(130, 255, 255);
    Scalar lower_green = new Scalar(50, 100, 100);
    Scalar upper_green = new Scalar(70, 255, 255);
    Scalar lower_red = new Scalar(-10, 100, 100);
    Scalar upper_red = new Scalar(10, 255, 255);
    Scalar lower_yellow = new Scalar(20, 20, 100);
    Scalar upper_yellow = new Scalar(32, 255, 255);

    int[] real_pixels;

    int[] extract_pixelsA;
    int[] extract_pixelsB;
    int[] extract_pixelsC;
    int[] extract_pixelsD;

    int[] changed_pixels;

    int[] rangeA;
    int[] rangeB;
    int[] rangeC;
    int[] rangeD;

    Mat originalA;
    Mat originalB;
    Mat originalC;
    Mat originalD;

    Mat hsv_originalA;
    Mat hsv_originalB;
    Mat hsv_originalC;
    Mat hsv_originalD;

    Mat mask;
    Mat mask2;
    Mat mask3;
    Mat mask4;

    Mat changeA;
    Mat changeB;
    Mat changeC;
    Mat changeD;

    Mat unChangeA;
    Mat unChangeB;
    Mat unChangeC;
    Mat unChangeD;

    BitmapDrawable drawable;
    Bitmap bitmap2;

    Bitmap bitmapA;
    Bitmap bitmapB;
    Bitmap bitmapC;
    Bitmap bitmapD;

    int w,h,size;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famous);
        image = findViewById(R.id.artimg);
        init();

        art_redgreen = findViewById(R.id.art_redgreen);
        art_yellowblue = findViewById(R.id.art_yellowblue);
        art_original = findViewById(R.id.art_original);
        art_plus = findViewById(R.id.art_plus);
        art_minus = findViewById(R.id.art_minus);

        art_redgreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clicked_button_redgreen = 1;
                clicked_button_yellowblue = 0;
                clicked_button_original = 0;
                button_cnt = 1;

                if(clicked_button_redgreen == 1)
                {
                    //show_image.setImageResource(R.drawable.cow);
                    drawable = (BitmapDrawable) image.getDrawable();
                    bitmap2 = drawable.getBitmap();
                    bitmapA = drawable.getBitmap();
                    bitmapB = drawable.getBitmap();

                    // Bitmap bitmap2 = resizeBitmap(1024, bitmap); // 이미지 크기 조정

                    w = bitmap2.getWidth(); //원본의 w사이즈
                    h = bitmap2.getHeight(); //원본의 h사이즈
                    size = w * h; //원본 사이즈

                    real_pixels = new int[size]; //원본 픽셀
                    extract_pixelsA = new int[size]; //추출된 색상 픽셀
                    extract_pixelsB = new int[size]; //추출된 색상 픽셀
                    changed_pixels = new int[size]; //최종적으로 바꿀 픽셀
                    rangeA = new int[size]; //A범위
                    rangeB = new int[size];//B범위

                    bitmap2.getPixels(real_pixels, 0, w, 0, 0, w, h); //real_pixels에 원본 픽셀을 넣음

                    // bitmapA를 Mat 객체로 변환
                    originalA = new Mat (bitmapA.getWidth(), bitmapA.getHeight(), CvType.CV_8UC3);
                    Utils.bitmapToMat(bitmapA, originalA);

                    // bitmapB를 Mat 객체로 변환
                    originalB = new Mat (bitmapB.getWidth(), bitmapB.getHeight(), CvType.CV_8UC3);
                    Utils.bitmapToMat(bitmapB, originalB);

                    // rgb를 hsv 변환
                    hsv_originalA = new Mat();
                    Imgproc.cvtColor(originalA, hsv_originalA, Imgproc.COLOR_RGB2HSV);

                    hsv_originalB = new Mat();
                    Imgproc.cvtColor(originalB, hsv_originalB, Imgproc.COLOR_RGB2HSV);

                    // 빨간색 영역을 구하는 마스크 생성
                    mask = new Mat();
                    Core.inRange(hsv_originalA, lower_red, upper_red, mask);

                    // 초록색 영역을 구하는 마스크 생성
                    mask2 = new Mat();
                    Core.inRange(hsv_originalB, lower_green, upper_green, mask2);

                    // 빨간색 영역만 구한 사진
                    changeA = new Mat();
                    Core.bitwise_and(hsv_originalA, hsv_originalA, changeA, mask);

                    // 초록색 영역만 구한 사진
                    changeB = new Mat();
                    Core.bitwise_and(hsv_originalB, hsv_originalB, changeB, mask2);

                    // 빨간색 영역을 제외한 사진
                    unChangeA = new Mat();
                    Core.bitwise_xor(hsv_originalA, changeA, unChangeA);

                    // 초록색 영역을 제외한 사진
                    unChangeB = new Mat();
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

                        int aA = (colorA >> 24) & 0xFF;
                        int rA = (colorA >> 16) & 0xFF;
                        int gA = (colorA >> 8) & 0xFF;
                        int bA = (colorA) & 0xFF;

                        if(rA==0x00 && gA==0x00 && bA==0x00) //검정색일때
                        {
                            changed_pixels[i] = real_pixels[i];
                        }

                        else //검은색이 아닐때(추출한 색일때), 빨간색의 크기를 키움
                        {
                            //change_color |= 255 << 16;

                            int change_red = rA;

                            int change_red_plus_range = 255-change_red;

                            rangeA[i] = (change_red_plus_range/3);

                            change_red = change_red + (rangeA[i]*button_cnt);

                            if(change_red >= 255)
                                change_red = 255;

                            int change_color = 0;
                            change_color |= aA << 24;
                            change_color |= change_red << 16;
                            change_color |= gA << 8;
                            change_color |= bA;

                            changed_pixels[i] = change_color;
                        }
                    }

                    for(int i=0; i<size; i++)
                    {
                        int colorB = extract_pixelsB[i];

                        int aB = (colorB >> 24) & 0xFF;
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
                            int change_green = gB;

                            int change_green_plus_range = 255 - change_green;

                            rangeB[i] = (change_green_plus_range/3);

                            change_green = change_green + (rangeB[i]*button_cnt);

                            if(change_green >= 255)
                                change_green = 255;

                            int change_color = 0;

                            change_color |= aB << 24;
                            change_color |= rB << 16;
                            change_color |= change_green << 8;
                            change_color |= bB;

                            changed_pixels[i] = change_color;
                        }
                    }

                    bitmap2.setPixels(changed_pixels,0, w, 0, 0, w, h);
                    image.setImageBitmap(bitmap2);
                }

            }
        });
    }

    private void init(){
        ImageView image = findViewById(R.id.artimg);
        image.setImageResource(img[0]);

        CURRENT_INDEX = 0;
    }

    public void onClickNext(View view){

        ImageView image = findViewById(R.id.artimg);
        Random ram = new Random();
        num = ram.nextInt(img.length);
        prevnum = num;

        if( ++CURRENT_INDEX > 40) {
            Toast toast = Toast.makeText(Famous.this, "마지막 명화 입니다.", Toast.LENGTH_SHORT );
            toast.show();

            CURRENT_INDEX--;
        }
        else{
            image.setImageResource(img[num]);

        }

    }

    public void onClickPrev(View view){
        ImageView image = findViewById(R.id.artimg);
        Random ram = new Random();
        num = ram.nextInt(img.length);
        prevnum = num;

        if( --CURRENT_INDEX < 0) {
            Toast toast = Toast.makeText(Famous.this, "처음 명화 입니다.", Toast.LENGTH_SHORT );
            toast.show();

            CURRENT_INDEX++;
        }
        else{
            image.setImageResource(img[num]);
        }
    }
}

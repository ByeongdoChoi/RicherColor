package com.example.richercolor;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.xw.repo.BubbleSeekBar;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.Random;

public class Famous extends AppCompatActivity {
    private int CURRENT_INDEX;
    private ImageView image;
    private int num,count;
    float touchX, touchY;   // 사진에서 터치하는 좌표

    Bitmap bitmap123;
    Bitmap bitmap22;

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
    private int prevnum;

    //색 변화
    boolean setPicture = false;

    Bitmap originalPicture; // 원본 사진
    Bitmap Img;

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
    int[] rangeD_A;
    int[] rangeD_B;

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
    Bitmap Result1;
    Bitmap bitmapC;
    Bitmap bitmapD;

    int w,h,size;

    BubbleSeekBar mBubbleSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famous);
        image = findViewById(R.id.artView);

        init();

        mBubbleSeekBar = findViewById(R.id.art_seekBar);

        mBubbleSeekBar.setCustomSectionTextArray(new BubbleSeekBar.CustomSectionTextArray() {
            @NonNull
            @Override
            public SparseArray<String> onCustomize(int sectionCount, @NonNull SparseArray<String> array) {
                array.clear();
                array.put(0, "+3");
                array.put(1, "+2");
                array.put(2, "+1");
                array.put(3, "원본");
                array.put(4, "+1");
                array.put(5, "+2");
                array.put(6, "+3");
                return array;
            }
        });

        mBubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                Toast.makeText(Famous.this, "" + progress, Toast.LENGTH_SHORT).show();
                initset();
                Colorchange(progress);
            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });

    }
    private void initset(){

        Img = BitmapFactory.decodeResource(getResources(), img[CURRENT_INDEX]);
        bitmap22 = resizeBitmap(1024, Img);

        w = bitmap22.getWidth(); //원본의 w사이즈
        h = bitmap22.getHeight(); //원본의 h사이즈
        size = w * h; //원본 사이즈
        Log.d("image","w:"+w+"h:"+h+"size:"+size);

        originalPicture = Bitmap.createScaledBitmap(bitmap22,w,h,true);
        image.setImageBitmap(originalPicture);
        bitmapA = Bitmap.createScaledBitmap(bitmap22,w,h,true);
        bitmapB = Bitmap.createScaledBitmap(bitmap22,w,h,true);
        bitmapC = Bitmap.createScaledBitmap(bitmap22,w,h,true);
        bitmapD = Bitmap.createScaledBitmap(bitmap22,w,h,true);




        real_pixels = new int[size]; //원본 픽셀
        extract_pixelsA = new int[size]; //추출된 색상 픽셀
        extract_pixelsB = new int[size]; //추출된 색상 픽셀
        extract_pixelsC = new int[size];
        extract_pixelsD = new int[size];

        changed_pixels = new int[size]; //최종적으로 바꿀 픽셀

        rangeA = new int[size]; //A범위
        rangeB = new int[size];//B범위
        rangeC = new int[size]; //C범위
        rangeD_A = new int[size]; //D범위
        rangeD_B = new int[size];

        bitmap22.getPixels(real_pixels, 0, w, 0, 0, w, h); //real_pixels에 원본 픽셀을 넣음

        // bitmapA를 Mat 객체로 변환
        originalA = new Mat(bitmapA.getWidth(), bitmapA.getHeight(), CvType.CV_8UC3);
        Utils.bitmapToMat(bitmapA, originalA);

        // bitmapB를 Mat 객체로 변환
        originalB = new Mat(bitmapB.getWidth(), bitmapB.getHeight(), CvType.CV_8UC3);
        Utils.bitmapToMat(bitmapB, originalB);

        // bitmapC를 Mat 객체로 변환
        originalC = new Mat(bitmapC.getWidth(), bitmapC.getHeight(), CvType.CV_8UC3);
        Utils.bitmapToMat(bitmapC, originalC);

        // bitmapD를 Mat 객체로 변환
        originalD = new Mat(bitmapD.getWidth(), bitmapD.getHeight(), CvType.CV_8UC3);
        Utils.bitmapToMat(bitmapD, originalD);

        // rgb를 hsv 변환
        hsv_originalA = new Mat();
        Imgproc.cvtColor(originalA, hsv_originalA, Imgproc.COLOR_RGB2HSV);

        hsv_originalB = new Mat();
        Imgproc.cvtColor(originalB, hsv_originalB, Imgproc.COLOR_RGB2HSV);

        hsv_originalC = new Mat();
        Imgproc.cvtColor(originalC, hsv_originalC, Imgproc.COLOR_RGB2HSV);

        hsv_originalD = new Mat();
        Imgproc.cvtColor(originalD, hsv_originalD, Imgproc.COLOR_RGB2HSV);

        // 빨간색 영역을 구하는 마스크 생성
        mask = new Mat();
        Core.inRange(hsv_originalA, lower_red, upper_red, mask);

        // 초록색 영역을 구하는 마스크 생성
        mask2 = new Mat();
        Core.inRange(hsv_originalB, lower_green, upper_green, mask2);

        // 파란색 영역을 구하는 마스크 생성
        mask3 = new Mat();
        Core.inRange(hsv_originalC, lower_blue, upper_blue, mask3);

        // 노란색 영역을 구하는 마스크 생성
        mask4 = new Mat();
        Core.inRange(hsv_originalD, lower_yellow, upper_yellow, mask4);

        // 빨간색 영역만 구한 사진
        changeA = new Mat();
        Core.bitwise_and(hsv_originalA, hsv_originalA, changeA, mask);

        // 초록색 영역만 구한 사진
        changeB = new Mat();
        Core.bitwise_and(hsv_originalB, hsv_originalB, changeB, mask2);

        // 파란색 영역만 구한 사진
        changeC = new Mat();
        Core.bitwise_and(hsv_originalC, hsv_originalC, changeC, mask3);

        // 노란색 영역만 구한 사진
        changeD = new Mat();
        Core.bitwise_and(hsv_originalD, hsv_originalD, changeD, mask4);

        // 빨간색 영역을 제외한 사진
        unChangeA = new Mat();
        Core.bitwise_xor(hsv_originalA, changeA, unChangeA);

        // 초록색 영역을 제외한 사진
        unChangeB = new Mat();
        Core.bitwise_xor(hsv_originalB, changeB, unChangeB);

        // 파란색 영역을 제외한 사진
        unChangeC = new Mat();
        Core.bitwise_xor(hsv_originalC, changeC, unChangeC);

        // 노란색 영역을 제외한 사진
        unChangeD = new Mat();
        Core.bitwise_xor(hsv_originalD, changeD, unChangeD);

        // result 그림을 다시 rgb로 바꿈
        Imgproc.cvtColor(changeA, changeA, Imgproc.COLOR_HSV2RGB);
        Imgproc.cvtColor(changeB, changeB, Imgproc.COLOR_HSV2RGB);
        Imgproc.cvtColor(changeC, changeC, Imgproc.COLOR_HSV2RGB);
        Imgproc.cvtColor(changeD, changeD, Imgproc.COLOR_HSV2RGB);

        // Mat 객체를 Bitmap 객체로 바꿈
        Utils.matToBitmap(changeA, bitmapA);
        Utils.matToBitmap(changeB, bitmapB);
        Utils.matToBitmap(changeC, bitmapC);
        Utils.matToBitmap(changeD, bitmapD);

        bitmapA.getPixels(extract_pixelsA, 0, w, 0, 0, w, h); //extract_pixels에는 추출된 색상이 저장, 추출되지 않은 색상은 검정색으로
        bitmapB.getPixels(extract_pixelsB, 0, w, 0, 0, w, h); //extract_pixels에는 추출된 색상이 저장, 추출되지 않은 색상은 검정색으로
        bitmapC.getPixels(extract_pixelsC, 0, w, 0, 0, w, h); //extract_pixels에는 추출된 색상이 저장, 추출되지 않은 색상은 검정색으로
        bitmapD.getPixels(extract_pixelsD, 0, w, 0, 0, w, h); //extract_pixels에는 추출된 색상이 저장, 추출되지 않은 색상은 검정색으로

        for (int i = 0; i < size; i++) {
            int colorA = extract_pixelsA[i];

            int aA = (colorA >> 24) & 0xFF;
            int rA = (colorA >> 16) & 0xFF;
            int gA = (colorA >> 8) & 0xFF;
            int bA = (colorA) & 0xFF;


            if (rA == 0x00 && gA == 0x00 && bA == 0x00) //검정색일때
            {
                continue;
            }

            else //검은색이 아닐때(추출한 색일때), 빨간색의 크기를 키움
            {
                //change_color |= 255 << 16;

                int change_red = rA;

                int change_red_plus_range = 255 - change_red;

                rangeA[i] = (change_red_plus_range / 3);
            }
        }

        for (int i = 0; i < size; i++) {
            int colorB = extract_pixelsB[i];

            int aB = (colorB >> 24) & 0xFF;
            int rB = (colorB >> 16) & 0xFF;
            int gB = (colorB >> 8) & 0xFF;
            int bB = (colorB) & 0xFF;


            if (rB == 0x00 && gB == 0x00 && bB == 0x00) //검정색일때
            {
                continue;
            }

            else //검은색이 아닐때(추출한 색일때), 초록색의 크기를 키움
            {
                int change_green = gB;

                int change_green_plus_range = 255 - change_green;

                rangeB[i] = (change_green_plus_range / 3);
            }
        }

        for (int i = 0; i < size; i++) {
            int colorD = extract_pixelsD[i];

            int aD = (colorD >> 24) & 0xFF;
            int rD = (colorD >> 16) & 0xFF;
            int gD = (colorD >> 8) & 0xFF;
            int bD = (colorD) & 0xFF;


            if (rD == 0x00 && gD == 0x00 && bD == 0x00) //검정색일때
            {
                continue;
            }

            //노란색은 빨간+녹색
            else //검은색이 아닐때(추출한 색일때), 노란색의 크기를 키움
            {
                //change_color |= 255 << 16;

                int change_red = rD;
                int change_green = gD;

                int change_red_plus_range = 255 - change_red;
                int change_green_plus_range = 255 - change_green;

                rangeD_A[i] = (change_red_plus_range / 3);
                rangeD_B[i] = (change_green_plus_range / 3);
            }
        }

        //파란색 바꾸기
        for (int i = 0; i < size; i++) {
            int colorC = extract_pixelsC[i];

            int aC = (colorC >> 24) & 0xFF;
            int rC = (colorC >> 16) & 0xFF;
            int gC = (colorC >> 8) & 0xFF;
            int bC = (colorC) & 0xFF;

            if (rC == 0x00 && gC == 0x00 && bC == 0x00) //검정색일때
            {
                continue;
            }

            else //검은색이 아닐때(추출한 색일때), 파란색의 크기를 키움
            {
                int change_blue = gC;

                int change_blue_plus_range = 255 - change_blue;

                rangeC[i] = (change_blue_plus_range / 3);
            }
        }
    }

    private void Colorchange(int rg_yb)
    {
        if (rg_yb >= 0 && rg_yb <= 2) //적록색맹
        {
            int rank_size = 3 - rg_yb;

            for (int i = 0; i < size; i++)
            {
                int colorA = extract_pixelsA[i];

                int aA = (colorA >> 24) & 0xFF;
                int rA = (colorA >> 16) & 0xFF;
                int gA = (colorA >> 8) & 0xFF;
                int bA = (colorA) & 0xFF;

                if (rA == 0x00 && gA == 0x00 && bA == 0x00) //검정색일때
                {
                    changed_pixels[i] = real_pixels[i];
                }

                else //검은색이 아닐때(추출한 색일때), 빨간색의 크기를 키움
                {

                    int change_red = rA;

                    change_red = change_red + rangeA[i] * rank_size;

                    if (change_red >= 0xFF)
                        change_red = 0xFF;

                    int change_color = 0;

                    change_color |= aA << 24;
                    change_color |= change_red << 16;
                    change_color |= gA << 8;
                    change_color |= bA;

                    changed_pixels[i] = change_color;
                }
            }

            for (int i = 0; i < size; i++)
            {
                int colorB = extract_pixelsB[i];

                int aB = (colorB >> 24) & 0xFF;
                int rB = (colorB >> 16) & 0xFF;
                int gB = (colorB >> 8) & 0xFF;
                int bB = (colorB) & 0xFF;

                if (rB == 0x00 && gB == 0x00 && bB == 0x00) //검정색일때
                {
                    if (changed_pixels[i] != real_pixels[i]) //빨간색에서 바뀐것일때
                        continue;

                    else
                        changed_pixels[i] = real_pixels[i];
                }

                else //검은색이 아닐때(추출한 색일때), 초록색의 크기를 키움
                {
                    int change_green = gB;

                    change_green = change_green + (rangeB[i] * rank_size);

                    if (change_green >= 0xFF)
                        change_green = 0xFF;

                    int change_color = 0;

                    change_color |= aB << 24;
                    change_color |= rB << 16;
                    change_color |= change_green << 8;
                    change_color |= bB;

                    changed_pixels[i] = change_color;
                }
            }
            /******************************************/
            Log.d("픽셀확인","change:"+changed_pixels+"w:"+w+"h:"+h);

            bitmap22.setPixels(changed_pixels, 0, w, 0, 0, w, h);
            image.setImageBitmap(bitmap22);
        }

        else if (rg_yb == 3) //원본
        {
            image.setImageBitmap(originalPicture);
            Toast.makeText(Famous.this, "원본 세팅", Toast.LENGTH_SHORT).show();
        }

        else //황청색맹
        {
            int rank_size = rg_yb - 3;

            for (int i = 0; i < size; i++) {
                int colorD = extract_pixelsD[i];

                int aD = (colorD >> 24) & 0xFF;
                int rD = (colorD >> 16) & 0xFF;
                int gD = (colorD >> 8) & 0xFF;
                int bD = (colorD) & 0xFF;

                if (rD == 0x00 && gD == 0x00 && bD == 0x00) //검정색일때
                {
                    changed_pixels[i] = real_pixels[i];
                }

                else //검은색이 아닐때(추출한 색일때), 노란색(빨간+초록)의 크기를 키움
                {

                    int change_red = rD;
                    int change_green = gD;

                    change_red = change_red + rangeD_A[i] * rank_size;
                    change_green = change_green + rangeD_B[i] * rank_size;

                    if (change_red >= 0xFF)
                        change_red = 0xFF;

                    if (change_green >= 0xFF)
                        change_green = 0xFF;

                    int change_color = 0;

                    change_color |= aD << 24;
                    change_color |= change_red << 16;
                    change_color |= change_green << 8;
                    change_color |= bD;

                    changed_pixels[i] = change_color;
                }
            }

            for (int i = 0; i < size; i++) {
                int colorC = extract_pixelsC[i];

                int aC = (colorC >> 24) & 0xFF;
                int rC = (colorC >> 16) & 0xFF;
                int gC = (colorC >> 8) & 0xFF;
                int bC = (colorC) & 0xFF;

                if (rC == 0x00 && gC == 0x00 && bC == 0x00) //검정색일때
                {
                    if (changed_pixels[i] != real_pixels[i]) //노란색에서 바뀐것일때
                        continue;

                    else
                        changed_pixels[i] = real_pixels[i];
                }

                else //검은색이 아닐때(추출한 색일때), 파란색의 크기를 키움
                {
                    int change_blue = gC;

                    change_blue = change_blue + (rangeC[i] * rank_size);

                    if (change_blue >= 0xFF)
                        change_blue = 0xFF;

                    int change_color = 0;

                    change_color |= aC << 24;
                    change_color |= rC << 16;
                    change_color |= gC << 8;
                    change_color |= change_blue;

                    changed_pixels[i] = change_color;
                }
            }

            bitmap22.setPixels(changed_pixels, 0, w, 0, 0, w, h);
            image.setImageBitmap(bitmap22);
        }
    }

    private void setImageViewTouchListener(ImageView v) {
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    touchX = event.getX();  // 좌표를 받아온다
                    touchY = event.getY();

                    if (touchX >= albumImage.getWidth() || touchY >= albumImage.getHeight())
                        return false;

                    // 좌표에 RGB 값을 추출한다.
                    int rgb = albumImage.getPixel((int) touchX, (int) touchY);
                    int R = Color.red(rgb);
                    int G = Color.green(rgb);
                    int B = Color.blue(rgb);

                    // 좌표에 툴팁을 띄운다.
                    String color = "(" + R + "," + G + "," + B + ")";
                    Balloon ballon = new Balloon.Builder(Famous.this)
                            .setArrowVisible(false)
                            .setWidthRatio(0.25f)
                            .setHeight(65)
                            .setText("rgb\n" + color)
                            .setAlpha(0.8f)
                            .setBackgroundColor(Color.rgb(100, 228, 44))
                            .setBalloonAnimation(BalloonAnimation.FADE)
                            .build();
                    ballon.show(v, (int) touchX, (int) touchY - albumImage.getHeight());
                }

                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == RESULT_OK) {
                                // 선택한 이미지를 지칭하는 Uri 객체를 얻어옴
//                Uri uri = data.getData();
//                // Uri 객체를 통해서 컨텐츠 프로바이덜르 통해 이미지 정보를 가져온다.
//                ContentResolver resolver = getContentResolver();
//                Cursor cursor = resolver.query(uri, null, null, null, null);
//                cursor.moveToNext();
//
//                String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
//                Uri newUri = Uri.fromFile(new File(path));
//                Log.d("Album ", " newUri " + newUri.getPath());
//
//                // 사용자가 선택한 이미지 경로를 가져옴
//                int index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
//                String source = cursor.getString(index);
//                Log.d("Album ", " source " + source);
//
//                // 이미지 객체 생성
//                Bitmap bitmap = BitmapFactory.decodeFile(source);
//                Log.d("Album ", " bitmap " + bitmap);
//
//
//                // 이미지 크기 조정
//                Bitmap bitmap2 = resizeBitmap(1024, bitmap);
//
//                // 회전 각도 값 가져옴
//                float degree = getDegree(source);
//                Bitmap bitmap3 = rotateBitmap(bitmap2, degree);
//
//                albumImage = BitmapFactory.decodeResource(getResources(),R.id.image);
//                // originalPicture = bitmap3.copy(Bitmap.Config.ARGB_8888, true);
//                image.setImageBitmap(albumImage);
//
//                Bitmap bitmap11 = BitmapFactory.decodeFile(source);
//                Log.d("Album ", " bitmap " + bitmap);
//
//
//                // 이미지 크기 조정
//                Bitmap bitmap22 = resizeBitmap(1024, bitmap11);
//
//                // 회전 각도 값 가져옴
//                float degree2 = getDegree(source);
//                Bitmap bitmap33 = rotateBitmap(bitmap22, degree2);



                // 이미지뷰 터치했을때
                setImageViewTouchListener(image);

                drawable = (BitmapDrawable) image.getDrawable();
                bitmap123 = drawable.getBitmap();

                Bitmap Img = BitmapFactory.decodeResource(getResources(), img[0]);


                w = Img.getWidth(); //원본의 w사이즈
                h = Img.getHeight(); //원본의 h사이즈
                size = w * h; //원본 사이즈
                Log.d("image","w:"+w+"h:"+h+"size:"+size);

                originalPicture = Bitmap.createScaledBitmap(Img,w,h,true);
                image.setImageBitmap(originalPicture);
                bitmapA = Bitmap.createScaledBitmap(Img,w,h,true);
                bitmapB = Bitmap.createScaledBitmap(Img,w,h,true);
                bitmapC = Bitmap.createScaledBitmap(Img,w,h,true);
                bitmapD = Bitmap.createScaledBitmap(Img,w,h,true);




                real_pixels = new int[size]; //원본 픽셀
                extract_pixelsA = new int[size]; //추출된 색상 픽셀
                extract_pixelsB = new int[size]; //추출된 색상 픽셀
                extract_pixelsC = new int[size];
                extract_pixelsD = new int[size];

                changed_pixels = new int[size]; //최종적으로 바꿀 픽셀

                rangeA = new int[size]; //A범위
                rangeB = new int[size];//B범위
                rangeC = new int[size]; //C범위
                rangeD_A = new int[size]; //D범위
                rangeD_B = new int[size];

                Img.getPixels(real_pixels, 0, w, 0, 0, w, h); //real_pixels에 원본 픽셀을 넣음

                // bitmapA를 Mat 객체로 변환
                originalA = new Mat(bitmapA.getWidth(), bitmapA.getHeight(), CvType.CV_8UC3);
                Utils.bitmapToMat(bitmapA, originalA);

                // bitmapB를 Mat 객체로 변환
                originalB = new Mat(bitmapB.getWidth(), bitmapB.getHeight(), CvType.CV_8UC3);
                Utils.bitmapToMat(bitmapB, originalB);

                // bitmapC를 Mat 객체로 변환
                originalC = new Mat(bitmapC.getWidth(), bitmapC.getHeight(), CvType.CV_8UC3);
                Utils.bitmapToMat(bitmapC, originalC);

                // bitmapD를 Mat 객체로 변환
                originalD = new Mat(bitmapD.getWidth(), bitmapD.getHeight(), CvType.CV_8UC3);
                Utils.bitmapToMat(bitmapD, originalD);

                // rgb를 hsv 변환
                hsv_originalA = new Mat();
                Imgproc.cvtColor(originalA, hsv_originalA, Imgproc.COLOR_RGB2HSV);

                hsv_originalB = new Mat();
                Imgproc.cvtColor(originalB, hsv_originalB, Imgproc.COLOR_RGB2HSV);

                hsv_originalC = new Mat();
                Imgproc.cvtColor(originalC, hsv_originalC, Imgproc.COLOR_RGB2HSV);

                hsv_originalD = new Mat();
                Imgproc.cvtColor(originalD, hsv_originalD, Imgproc.COLOR_RGB2HSV);

                // 빨간색 영역을 구하는 마스크 생성
                mask = new Mat();
                Core.inRange(hsv_originalA, lower_red, upper_red, mask);

                // 초록색 영역을 구하는 마스크 생성
                mask2 = new Mat();
                Core.inRange(hsv_originalB, lower_green, upper_green, mask2);

                // 파란색 영역을 구하는 마스크 생성
                mask3 = new Mat();
                Core.inRange(hsv_originalC, lower_blue, upper_blue, mask3);

                // 노란색 영역을 구하는 마스크 생성
                mask4 = new Mat();
                Core.inRange(hsv_originalD, lower_yellow, upper_yellow, mask4);

                // 빨간색 영역만 구한 사진
                changeA = new Mat();
                Core.bitwise_and(hsv_originalA, hsv_originalA, changeA, mask);

                // 초록색 영역만 구한 사진
                changeB = new Mat();
                Core.bitwise_and(hsv_originalB, hsv_originalB, changeB, mask2);

                // 파란색 영역만 구한 사진
                changeC = new Mat();
                Core.bitwise_and(hsv_originalC, hsv_originalC, changeC, mask3);

                // 노란색 영역만 구한 사진
                changeD = new Mat();
                Core.bitwise_and(hsv_originalD, hsv_originalD, changeD, mask4);

                // 빨간색 영역을 제외한 사진
                unChangeA = new Mat();
                Core.bitwise_xor(hsv_originalA, changeA, unChangeA);

                // 초록색 영역을 제외한 사진
                unChangeB = new Mat();
                Core.bitwise_xor(hsv_originalB, changeB, unChangeB);

                // 파란색 영역을 제외한 사진
                unChangeC = new Mat();
                Core.bitwise_xor(hsv_originalC, changeC, unChangeC);

                // 노란색 영역을 제외한 사진
                unChangeD = new Mat();
                Core.bitwise_xor(hsv_originalD, changeD, unChangeD);

                // result 그림을 다시 rgb로 바꿈
                Imgproc.cvtColor(changeA, changeA, Imgproc.COLOR_HSV2RGB);
                Imgproc.cvtColor(changeB, changeB, Imgproc.COLOR_HSV2RGB);
                Imgproc.cvtColor(changeC, changeC, Imgproc.COLOR_HSV2RGB);
                Imgproc.cvtColor(changeD, changeD, Imgproc.COLOR_HSV2RGB);

                // Mat 객체를 Bitmap 객체로 바꿈
                Utils.matToBitmap(changeA, bitmapA);
                Utils.matToBitmap(changeB, bitmapB);
                Utils.matToBitmap(changeC, bitmapC);
                Utils.matToBitmap(changeD, bitmapD);

                bitmapA.getPixels(extract_pixelsA, 0, w, 0, 0, w, h); //extract_pixels에는 추출된 색상이 저장, 추출되지 않은 색상은 검정색으로
                bitmapB.getPixels(extract_pixelsB, 0, w, 0, 0, w, h); //extract_pixels에는 추출된 색상이 저장, 추출되지 않은 색상은 검정색으로
                bitmapC.getPixels(extract_pixelsC, 0, w, 0, 0, w, h); //extract_pixels에는 추출된 색상이 저장, 추출되지 않은 색상은 검정색으로
                bitmapD.getPixels(extract_pixelsD, 0, w, 0, 0, w, h); //extract_pixels에는 추출된 색상이 저장, 추출되지 않은 색상은 검정색으로

                for (int i = 0; i < size; i++) {
                    int colorA = extract_pixelsA[i];

                    int aA = (colorA >> 24) & 0xFF;
                    int rA = (colorA >> 16) & 0xFF;
                    int gA = (colorA >> 8) & 0xFF;
                    int bA = (colorA) & 0xFF;


                    if (rA == 0x00 && gA == 0x00 && bA == 0x00) //검정색일때
                    {
                        continue;
                    }

                    else //검은색이 아닐때(추출한 색일때), 빨간색의 크기를 키움
                    {
                        //change_color |= 255 << 16;

                        int change_red = rA;

                        int change_red_plus_range = 255 - change_red;

                        rangeA[i] = (change_red_plus_range / 3);
                    }
                }

                for (int i = 0; i < size; i++) {
                    int colorB = extract_pixelsB[i];

                    int aB = (colorB >> 24) & 0xFF;
                    int rB = (colorB >> 16) & 0xFF;
                    int gB = (colorB >> 8) & 0xFF;
                    int bB = (colorB) & 0xFF;


                    if (rB == 0x00 && gB == 0x00 && bB == 0x00) //검정색일때
                    {
                        continue;
                    }

                    else //검은색이 아닐때(추출한 색일때), 초록색의 크기를 키움
                    {
                        int change_green = gB;

                        int change_green_plus_range = 255 - change_green;

                        rangeB[i] = (change_green_plus_range / 3);
                    }
                }

                for (int i = 0; i < size; i++) {
                    int colorD = extract_pixelsD[i];

                    int aD = (colorD >> 24) & 0xFF;
                    int rD = (colorD >> 16) & 0xFF;
                    int gD = (colorD >> 8) & 0xFF;
                    int bD = (colorD) & 0xFF;


                    if (rD == 0x00 && gD == 0x00 && bD == 0x00) //검정색일때
                    {
                        continue;
                    }

                    //노란색은 빨간+녹색
                    else //검은색이 아닐때(추출한 색일때), 노란색의 크기를 키움
                    {
                        //change_color |= 255 << 16;

                        int change_red = rD;
                        int change_green = gD;

                        int change_red_plus_range = 255 - change_red;
                        int change_green_plus_range = 255 - change_green;

                        rangeD_A[i] = (change_red_plus_range / 3);
                        rangeD_B[i] = (change_green_plus_range / 3);
                    }
                }

                //파란색 바꾸기
                for (int i = 0; i < size; i++) {
                    int colorC = extract_pixelsC[i];

                    int aC = (colorC >> 24) & 0xFF;
                    int rC = (colorC >> 16) & 0xFF;
                    int gC = (colorC >> 8) & 0xFF;
                    int bC = (colorC) & 0xFF;

                    if (rC == 0x00 && gC == 0x00 && bC == 0x00) //검정색일때
                    {
                        continue;
                    }

                    else //검은색이 아닐때(추출한 색일때), 파란색의 크기를 키움
                    {
                        int change_blue = gC;

                        int change_blue_plus_range = 255 - change_blue;

                        rangeC[i] = (change_blue_plus_range / 3);
                    }
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Bitmap resizeBitmap(int targetWidth, Bitmap source) {
        double ratio = (double) targetWidth / (double) source.getWidth();
        int targetHeight = (int) (source.getHeight() * ratio);

        targetWidth = 768;
        targetHeight = 1024;

        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
        if (result != source)
            source.recycle();
        return result;
    }

    public float getDegree(String source) {
        try {
            ExifInterface exif = new ExifInterface(source);
            int degree = 0;
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
            return degree;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0f;
    }

    public Bitmap rotateBitmap(Bitmap bitmap, float degree) {
        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Matrix matrix = new Matrix();
            matrix.postRotate(degree);

            Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            //bitmap.recycle();

            return bitmap2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void init(){
        ImageView image = findViewById(R.id.artView);

        CURRENT_INDEX = 0;
        count =0;
        Img = BitmapFactory.decodeResource(getResources(), img[CURRENT_INDEX]);
        bitmap22 = resizeBitmap(1024, Img);
        image.setImageBitmap(bitmap22);
        prevnum = 0;


    }

    public void onClickNext(View view){
        ImageView image = findViewById(R.id.artView);

        if( ++CURRENT_INDEX >= 40) {
            Toast toast = Toast.makeText(Famous.this, "마지막 명화 입니다.", Toast.LENGTH_SHORT );
            toast.show();
            CURRENT_INDEX--;
        }
        else{
            image.setImageResource(img[CURRENT_INDEX]);
            initset();
        }

    }

    public void onClickPrev(View view){
        ImageView image = findViewById(R.id.artView);
        Random ram = new Random();
        num = ram.nextInt(img.length);
        if( --CURRENT_INDEX < 0) {
            Toast toast = Toast.makeText(Famous.this, "첫 번째 명화 입니다.", Toast.LENGTH_SHORT );
            toast.show();

            CURRENT_INDEX++;
        }
        else{
            image.setImageResource(img[CURRENT_INDEX]);
            initset();
        }
    }

}

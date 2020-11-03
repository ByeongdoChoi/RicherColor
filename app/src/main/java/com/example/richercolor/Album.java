package com.example.richercolor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;

import java.io.File;

public class Album extends AppCompatActivity {

    ImageView imageView;    // 이미지뷰
    Bitmap albumImage;      // 앨범에서 불러온 사진을 비트맵 담을 객체
    float touchX, touchY;   // 사진에서 터치하는 좌표

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        imageView = findViewById(R.id.imageView);

        getImage();

        // 이미지뷰 터치했을때
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    touchX = event.getX();  // 좌표를 받아온다
                    touchY = event.getY();

                    if (touchX >= albumImage.getWidth() || touchY >= albumImage.getHeight()) return false;

                    // 좌표에 RGB 값을 추출한다.
                    int rgb = albumImage.getPixel((int)touchX, (int)touchY);
                    int R = Color.red(rgb);
                    int G = Color.green(rgb);
                    int B = Color.blue(rgb);
                    Log.d("Album ", "X " + touchX + " Y " + touchY + " rgb " + rgb);
                    Log.d("Album ", "R " + R + " G " + G + " B " + B);

                    // 좌표에 툴팁을 띄운다.
                    String color = "(" + R + "," + G + "," + B +")";
                    Balloon ballon = new Balloon.Builder(Album.this)
                            .setArrowVisible(false)
                            .setWidthRatio(0.25f)
                            .setHeight(65)
                            .setText("rgb\n"+color)
                            .setAlpha(0.8f)
                            .setBackgroundColor(Color.rgb(100, 228, 44))
                            .setBalloonAnimation(BalloonAnimation.FADE)
                            .build();
                    ballon.show(v, (int)touchX, (0 - (int)touchY));
                }

                return false;
            }
        });
    }

    private void getImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == RESULT_OK) {
                Log.d("Album ", "결과 받아옴");

                // 선택한 이미지를 지칭하는 Uri 객체를 얻어옴
                Uri uri = data.getData();
                // Uri 객체를 통해서 컨텐츠 프로바이덜르 통해 이미지 정보를 가져온다.
                ContentResolver resolver = getContentResolver();
                Cursor cursor = resolver.query(uri, null, null, null, null);
                cursor.moveToNext();

                String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                Uri newUri = Uri.fromFile(new File(path));
                Log.d("Album ", " newUri " + newUri.getPath());

                // 사용자가 선택한 이미지 경로를 가져옴
                int index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                String source = cursor.getString(index);
                Log.d("Album ", " source " + source);

                // 이미지 객체 생성
                Bitmap bitmap = BitmapFactory.decodeFile(source);
                Log.d("Album ", " bitmap " + bitmap);


                // 이미지 크기 조정
                Bitmap bitmap2 = resizeBitmap(1024, bitmap);

                // 회전 각도 값 가져옴
                float degree = getDegree(source);
                Bitmap bitmap3 = rotateBitmap(bitmap2, degree);

                albumImage = bitmap3;
                imageView.setImageBitmap(albumImage);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Bitmap resizeBitmap(int targetWidth, Bitmap source) {
        double ratio = (double) targetWidth / (double) source.getWidth();
        int targetHeight = (int) (source.getHeight() * ratio);
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
            bitmap.recycle();

            return bitmap2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
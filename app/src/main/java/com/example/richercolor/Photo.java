package com.example.richercolor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class Photo extends AppCompatActivity {

    //경로를 가지고 있는 변수
    String dir_path;
    //저장된 이미지에 접근할 수 있는 uri
    Uri contentUri;

    //ImageView image1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        //image1 = findViewById(R.id.photo_imageView);
        init();
    }

    public void init(){
        String temp_path= Environment.getExternalStorageDirectory().getAbsolutePath();
        dir_path=temp_path + "/Android/data" + getPackageName();

        startCamera();
    }

    public void startCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //이름 겹치지 않게 아무거나
        String file_name = "/temp_" + System.currentTimeMillis()+".jpg";
        String pic_path = dir_path + file_name;

        File file = new File(pic_path);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            contentUri= FileProvider.getUriForFile(this,"kr.co.richercolor.file_provider", file);
        }else{
            contentUri=Uri.fromFile(file);
        }

        Log.d("Photo ", contentUri.toString());

        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        startActivityForResult(intent,1);


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Photo ", resultCode+"");

        if(resultCode==RESULT_OK){
//            Log.d("Photo ","이미지 로딩");
//            Bitmap bitmap = BitmapFactory.decodeFile(contentUri.getPath());
//            //image1.setImageBitmap(bitmap);
//
//            // 이미지 크기 조정
//            Bitmap bitmap2 = resizeBitmap(1024, bitmap);
//            image1.setImageBitmap(bitmap2);
//
//            Bitmap bitmap3 = rotateBitmap(bitmap2, 90);


            galleryAddPic();

            Intent intent = new Intent(Photo.this,MainActivity.class);
            startActivity(intent);

            /*Intent intent = new Intent(getApplicationContext(), Album.class);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            intent.putExtra("image", byteArray);
            Log.d("Photo ", "변환 완료");
            startActivity(intent);*/
        }
        else if(resultCode==RESULT_CANCELED){
            Intent intent = new Intent(Photo.this,MainActivity.class);
            startActivity(intent);
        }
    }


    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(contentUri.getPath());
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

//    public Bitmap resizeBitmap(int targetWidth, Bitmap source) {
//        double ratio = (double) targetWidth / (double) source.getWidth();
//        int targetHeight = (int) (source.getHeight() * ratio);
//        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
//        if (result != source)
//            source.recycle();
//        return result;
//    }
//
//    public float getDegree(String source) {
//        try {
//            ExifInterface exif = new ExifInterface(source);
//            int degree = 0;
//            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
//            switch (ori) {
//                case ExifInterface.ORIENTATION_ROTATE_90:
//                    degree = 90;
//                    break;
//                case ExifInterface.ORIENTATION_ROTATE_180:
//                    degree = 180;
//                    break;
//                case ExifInterface.ORIENTATION_ROTATE_270:
//                    degree = 270;
//                    break;
//            }
//            return degree;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0.0f;
//    }
//
//    public Bitmap rotateBitmap(Bitmap bitmap, float degree) {
//        try {
//            int width = bitmap.getWidth();
//            int height = bitmap.getHeight();
//            Matrix matrix = new Matrix();
//            matrix.postRotate(degree);
//
//            Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
//            //bitmap.recycle();
//
//            return bitmap2;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
package com.example.richercolor;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Famous extends AppCompatActivity {
    private int CURRENT_INDEX;
    private ImageView[] imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famous);
        init();
    }

    private void init(){

        ImageView imageView1 = (ImageView)findViewById( R.id.image1);
        ImageView imageView2 = (ImageView)findViewById( R.id.image2);

        imageViews = new ImageView[]{ imageView1, imageView2 };
        imageView1.setVisibility(View.VISIBLE);
        CURRENT_INDEX = 0;
    }

    public void onClickNext(View view){

        if( ++CURRENT_INDEX > 1) {
            Toast toast = Toast.makeText(Famous.this, "마지막 명화 입니다.", Toast.LENGTH_SHORT );
            toast.show();

            CURRENT_INDEX--;
        }
        else{
            for( int i = 0; i < imageViews.length; i++){
                if( i == CURRENT_INDEX){
                    imageViews[i].setVisibility(View.VISIBLE);
                }
                else{
                    imageViews[i].setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    public void onClickPrev(View view){

        if( --CURRENT_INDEX < 0) {
            Toast toast = Toast.makeText(Famous.this, "처음 명화 입니다.", Toast.LENGTH_SHORT );
            toast.show();

            CURRENT_INDEX++;
        }
        else{
            for( int i = 0; i < imageViews.length; i++){
                if( i == CURRENT_INDEX){
                    imageViews[i].setVisibility(View.VISIBLE);
                }
                else{
                    imageViews[i].setVisibility(View.INVISIBLE);
                }
            }
        }
    }
}

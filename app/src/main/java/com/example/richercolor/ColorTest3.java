package com.example.richercolor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class ColorTest3 extends AppCompatActivity {

    private Button btn_next3;
    RadioGroup group5, group6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_test3);

        btn_next3 = findViewById(R.id.next3);
        group5 = (RadioGroup)findViewById(R.id.test5);
        group6 = (RadioGroup)findViewById(R.id.test6);

        btn_next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasend();
                Intent intent = new Intent(ColorTest3.this,ColorTest4.class);
                startActivity(intent);
            }
        });
    }

    public void datasend(){
        int id1 = group5.getCheckedRadioButtonId();
        int id2 = group6.getCheckedRadioButtonId();
        Data data = Data.getData();

        switch (id1){
            case R.id.radio13:
                data.setData("wrong", 4);
                break;
            case R.id.radio14:
                data.setData("normal",4);
                break;
            case R.id.radio15:
                data.setData("wrong",4);
                break;
        }

        switch (id2){
            case R.id.radio16:
                data.setData("red",5);
                break;
            case R.id.radio17:
                data.setData("normal",5);
                break;
            case R.id.radio18:
                data.setData("wrong",5);
                break;
        }
    }
}
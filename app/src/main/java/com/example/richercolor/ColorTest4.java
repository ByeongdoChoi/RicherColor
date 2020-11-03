package com.example.richercolor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class ColorTest4 extends AppCompatActivity {

    private Button btn_next4;
    RadioGroup group7, group8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_test4);

        btn_next4 = findViewById(R.id.next4);
        group7 = (RadioGroup)findViewById(R.id.test7);
        group8 = (RadioGroup)findViewById(R.id.test8);

        btn_next4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasend();
                Intent intent = new Intent(ColorTest4.this, ColorTest5.class);
                startActivity(intent);
            }
        });
    }
    public void datasend(){
        int id1 = group7.getCheckedRadioButtonId();
        int id2 = group8.getCheckedRadioButtonId();
        Data data = Data.getData();

        switch (id1){
            case R.id.radio19:
                data.setData("normal", 6);
                break;
            case R.id.radio20:
                data.setData("normal",6);
                break;
            case R.id.radio21:
                data.setData("wrong",6);
                break;
        }

        switch (id2){
            case R.id.radio22:
                data.setData("red",7);
                break;
            case R.id.radio23:
                data.setData("normal",7);
                break;
            case R.id.radio24:
                data.setData("wrong",7);
                break;
        }
    }
}
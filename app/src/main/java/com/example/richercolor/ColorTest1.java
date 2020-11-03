package com.example.richercolor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class ColorTest1 extends AppCompatActivity {

    private Button btn_next1;
    RadioGroup group1, group2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_test1);

        btn_next1 = findViewById(R.id.next1);
        group1 = (RadioGroup)findViewById(R.id.test1);
        group2 = (RadioGroup)findViewById(R.id.test2);

        btn_next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasend();
                Intent intent = new Intent(ColorTest1.this,ColorTest2.class);
                startActivity(intent);
            }
        });
    }

    public void datasend(){
        int id1 = group1.getCheckedRadioButtonId();
        int id2 = group2.getCheckedRadioButtonId();
        Data data = Data.getData();

        switch (id1){
            case R.id.radio1:
                data.setData("normal", 0);
                break;
            case R.id.radio2:
                data.setData("wrong",0);
                break;
            case R.id.radio3:
                data.setData("wrong",0);
                break;
        }

        switch (id2){
            case R.id.radio4:
                data.setData("green",1);
                break;
            case R.id.radio5:
                data.setData("normal",1);
                break;
            case R.id.radio6:
                data.setData("wrong",1);
                break;
        }
    }
}
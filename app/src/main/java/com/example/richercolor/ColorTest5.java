package com.example.richercolor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class ColorTest5 extends AppCompatActivity {

    private Button btn_done;
    RadioGroup group9,group10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_test5);

        btn_done = findViewById(R.id.done);
        group9 = (RadioGroup)findViewById(R.id.test9);
        group10 = (RadioGroup)findViewById(R.id.test10);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasend();
                Intent intent = new Intent(ColorTest5.this,TestResult.class);
                startActivity(intent);
            }
        });

    }
    public void datasend(){
        int id1 = group9.getCheckedRadioButtonId();
        int id2 = group10.getCheckedRadioButtonId();
        Data data = Data.getData();

        switch (id1){
            case R.id.radio7:
                data.setData("wrong", 8);
                break;
            case R.id.radio8:
                data.setData("wrong",8);
                break;
            case R.id.radio9:
                data.setData("normal",8);
                break;
        }

        switch (id2){
            case R.id.radio10:
                data.setData("normal",9);
                break;
            case R.id.radio11:
                data.setData("blue",9);
                break;
            case R.id.radio12:
                data.setData("wrong",9);
                break;
        }
    }
}
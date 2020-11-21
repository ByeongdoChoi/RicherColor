package com.example.richercolor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ColorTest2 extends AppCompatActivity {

    private Button btn_next2;
    RadioGroup group3,group4;
    private RadioButton rd7, rd8, rd9, rd10, rd11, rd12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_test2);
        btn_next2=findViewById(R.id.next2);
        group3=(RadioGroup)findViewById(R.id.test3);
        group4=(RadioGroup)findViewById(R.id.test4);
        rd7 = (RadioButton)findViewById(R.id.radio7);
        rd8 = (RadioButton)findViewById(R.id.radio8);
        rd9 = (RadioButton)findViewById(R.id.radio9);
        rd10 = (RadioButton)findViewById(R.id.radio10);
        rd11 = (RadioButton)findViewById(R.id.radio11);
        rd12 = (RadioButton)findViewById(R.id.radio12);

        btn_next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rd7.isChecked()==false && rd8.isChecked()==false && rd9.isChecked() ==false){
                    Toast toast = Toast.makeText(ColorTest2.this,"첫번째 항목을 선택해 주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(rd10.isChecked()==false && rd11.isChecked()==false && rd12.isChecked() ==false){
                    Toast toast = Toast.makeText(ColorTest2.this,"두번째 항목을 선택해 주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    datasend();
                    Intent intent = new Intent(ColorTest2.this,ColorTest3.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void datasend(){
        int id1 = group3.getCheckedRadioButtonId();
        int id2 = group4.getCheckedRadioButtonId();
        Data data = Data.getData();

        switch (id1){
            case R.id.radio7:
                data.setData("green", 2);
                break;
            case R.id.radio8:
                data.setData("normal",2);
                break;
            case R.id.radio9:
                data.setData("wrong",2);
                break;
        }

        switch (id2){
            case R.id.radio10:
                data.setData("green",3);
                break;
            case R.id.radio11:
                data.setData("normal",3);
                break;
            case R.id.radio12:
                data.setData("wrong",3);
                break;
        }
    }
}
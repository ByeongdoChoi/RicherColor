package com.example.richercolor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ColorTest5 extends AppCompatActivity {

    private Button btn_done;
    RadioGroup group9,group10;
    private RadioButton rd25, rd26, rd27, rd28, rd29, rd30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_test5);

        btn_done = findViewById(R.id.done);
        group9 = (RadioGroup)findViewById(R.id.test9);
        group10 = (RadioGroup)findViewById(R.id.test10);
        rd25 = (RadioButton)findViewById(R.id.radio25);
        rd26 = (RadioButton)findViewById(R.id.radio26);
        rd27 = (RadioButton)findViewById(R.id.radio27);
        rd28 = (RadioButton)findViewById(R.id.radio28);
        rd29 = (RadioButton)findViewById(R.id.radio29);
        rd30 = (RadioButton)findViewById(R.id.radio30);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rd25.isChecked()==false && rd26.isChecked()==false && rd27.isChecked() ==false){
                    Toast toast = Toast.makeText(ColorTest5.this,"첫번째 항목을 선택해 주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(rd28.isChecked()==false && rd29.isChecked()==false && rd30.isChecked() ==false){
                    Toast toast = Toast.makeText(ColorTest5.this,"두번째 항목을 선택해 주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    datasend();
                    Intent intent = new Intent(ColorTest5.this,TestResult.class);
                    startActivity(intent);
                }
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
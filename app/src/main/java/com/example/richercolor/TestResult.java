package com.example.richercolor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestResult extends AppCompatActivity {

    TextView result;
    private Button done_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        result=(TextView)findViewById(R.id.Result);
        done_Btn=findViewById(R.id.gomain);
        TestResult();
        done_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestResult.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void TestResult(){

        int normal=0,wrong=0,red=0,blue=0,green=0;
        Data data = Data.getData();

        for(int i=0;i<10;i++){
            if(data.datas[i]=="normal"){
                normal++;
            }
            else if(data.datas[i]=="green"){
                green++;
            }
            else if(data.datas[i]=="red"){
                red++;
            }
            else if(data.datas[i]=="blue"){
                blue++;
            }
            else if(data.datas[i]=="wrong"){
                wrong++;
            }
        }
        Log.d("data1","data1:"+green);

        if(blue==1){
            result.setText("귀하는 청색각 이상을 가지고 있을 수 있습니다.");
        }
        else if(red>=1){
            result.setText("귀하는 적색각 이상을 가지고 있을 수 있습니다.");
        }
        else if(green>=2){
            result.setText("귀하는 녹색각 이상을 가지고 있을 수 있습니다.");
        }
        else if(wrong>=5){
            result.setText("귀하는 한가지 이상의 색각 이상을 가지고 있을 수 있습니다.");
        }
        else{
            result.setText("귀하는 정상적인 시력을 가지고 있습니다.");
        }
    }
}
package com.example.count;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private CountDownTimer countDownTimer;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=(TextView)findViewById(R.id.number_textview);
        Log.i("tag", String.valueOf(textView));
        initData();
    }

    public void initData() {

        long time=10000;
        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i("tag",String.valueOf(millisUntilFinished));
                if (!MainActivity.this.isFinishing()) {

                    long day = millisUntilFinished / (1000 * 24 * 60 * 60); //单位天

                    long hour = (millisUntilFinished - day * (1000 * 24 * 60 * 60)) / (1000 * 60 * 60);
                    //单位时
                    long minute = (millisUntilFinished - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60)) / (1000 * 60);
                    //单位分
                    long second = (millisUntilFinished - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;
                    //单位秒
                    textView.setText(hour + "小时" + minute + "分钟" + second + "秒");
                }
            }

            /**
             *倒计时结束后调用的
             */
            @Override
            public void onFinish() {

            }

        };
        countDownTimer.start();
    }
}
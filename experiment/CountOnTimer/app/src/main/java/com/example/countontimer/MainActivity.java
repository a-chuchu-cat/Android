package com.example.countontimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private CountDownTimer timer;
    private TextView time;
    private static SeekBar seekBar;
    private static TextView timerProgress;
    private static TextView timerTotal;
    private TimerService.TimerController timerController;
    private Button startButton,ensureButton;

    private Intent intentService;

    private EditText editTextDay,editTextHour,editTextMinute,editTextSecond;

    long totalTimer=0;//总时长

    private String dayString="",hourString="",minuteString="",secondString="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
    }

    public void initData(){
        seekBar=(SeekBar)findViewById(R.id.seek_bar);
        timerProgress=(TextView)findViewById(R.id.timer_progress);
        timerTotal=(TextView)findViewById(R.id.timer_total);

        editTextDay=(EditText)findViewById(R.id.edit_text_day);
        editTextHour=(EditText)findViewById(R.id.edit_text_hour);
        editTextMinute=(EditText)findViewById(R.id.edit_text_minute);
        editTextSecond=(EditText)findViewById(R.id.edit_text_second);

        startButton=(Button)findViewById(R.id.start_button);
        ensureButton=(Button)findViewById(R.id.ensure_button);

        intentService=new Intent(MainActivity.this,TimerService.class);
        bindService(intentService,new MyServiceConnection(), Service.BIND_AUTO_CREATE);

        ensureButton.setOnClickListener(view -> {
            dayString=editTextDay.getText().toString();
            hourString=editTextHour.getText().toString();
            minuteString=editTextMinute.getText().toString();
            secondString=editTextSecond.getText().toString();

            if(isInteger(dayString)&&isInteger(hourString)&&isInteger(minuteString)&&isInteger(secondString)&&!dayString.equals("")&&!hourString.equals("")&&!minuteString.equals("")&&!secondString.equals("")){
                long longDay=Long.parseLong(dayString);
                long longHour=Long.parseLong(hourString);
                long longMinute=Long.parseLong(minuteString);
                long longSecond=Long.parseLong(secondString);

                totalTimer=longDay*24*60*60*1000+ longHour*60*60*1000+ longMinute*60*1000+ longSecond*1000;//得到倒数器总时长（ms）

                if(totalTimer!=0) {
                    timer = new CountDownTimer(totalTimer, 1000) {
                        @Override
                        public void onTick(long l) {
                            Log.i("tag", "l:"+String.valueOf(l));
                            if (!MainActivity.this.isFinishing()) {
                                long day = l / (1000 * 24 * 60 * 60);
                                long hour = (l - day * (1000 * 24 * 60 * 60)) / (1000 * 60 * 60);
                                long minute = (l - day * (1000 * 24 * 60 * 60) - hour * (60 * 60 * 1000)) / (1000 * 60);
                                long second = (l - day * (1000 * 24 * 60 * 60) - hour * (60 * 60 * 1000) - minute * (1000 * 60)) / 1000;

                                Log.i("tag","countDownTimer:"+day+":"+hour+":"+minute+":"+second);

                                time = (TextView) findViewById(R.id.time_count_down);
                                timerController.setInformation(totalTimer, day, hour, minute, second);
                            }
                        }

                        @Override
                        public void onFinish() {

                        }
                    };


                }
            }
        });

        startButton.setEnabled(true);


        startButton.setOnClickListener(view -> {
            timer.start();
            timerController.start();
            Log.i("tag","start");
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)

            /**
             * 进度条改变时会调用此方法
             * @param seekBar
             * @param i
             * @param b
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //当滑动条到末端时，播放铃声
                if(i==seekBar.getMax()){

                }
            }


            /**
             * 滑动条开始滑动时调用此方法
             *
             * @param seekBar
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            /**
             * 滑动条停止滑动时调用
             * @param seekBar
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * 判断用户输入的是否是整数
     */
    public boolean isInteger(String input){
        char[] array=input.toCharArray();

        for (int i = 0; i < array.length; i++) {
            if(array[i]>='0'&&array[i]<='9') continue;
            else return false;
        }

        return true;
    }


    /**
     * 创建消息处理器对象
     */

    public static Handler handler=new Handler(){
        //接收子线程发来的消息并进行处理
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle=msg.getData();
            long totalLong=bundle.getLong(TimerService.TOTAL);
            long dayLong=bundle.getLong(TimerService.DAY);
            long hourLong=bundle.getLong(TimerService.HOUR);
            long minuteLong=bundle.getLong(TimerService.MINUTE);
            long secondLong=bundle.getLong(TimerService.SECOND);

            String stringTotal=String.valueOf(totalLong);
            int intTotal=Integer.parseInt(stringTotal);
            seekBar.setMax(intTotal);

            String stringDay="";
            String stringHour="";
            String stringMinute="";
            String stringSecond="";

            if(dayLong<10) stringDay="0"+dayLong;
            else stringDay=stringDay+dayLong;

            if(minuteLong<10) stringHour="0"+hourLong;
            else stringHour=stringHour+hourLong;

            if(minuteLong<10) stringMinute="0"+minuteLong;
            else stringMinute=stringMinute+minuteLong;

            if(secondLong<10) stringSecond="0"+secondLong;
            else stringSecond=stringSecond+secondLong;

            String stringProgress=stringDay+":"+stringHour+":"+stringMinute+":"+stringSecond;
            Log.i("tag","handler:"+stringProgress);

            long progress=dayLong*24*60*60*1000+hourLong*60*60*1000+minuteLong*60*1000+secondLong*1000;
            int progressInt=Integer.parseInt(String.valueOf(progress));

            seekBar.setProgress(progressInt);

            timerProgress.setText(stringProgress);

            long dayTotal=totalLong/(1000 * 24 * 60 * 60);
            long hourTotal=(totalLong - dayTotal * (1000 * 24 * 60 * 60)) / (1000 * 60 * 60);
            long minuteTotal=(totalLong - dayTotal * (1000 * 24 * 60 * 60) - hourTotal * (60 * 60 * 1000)) / (1000 * 60);
            long secondTotal=(totalLong - dayTotal * (1000 * 24 * 60 * 60) - hourTotal * (60 * 60 * 1000) - minuteTotal * (1000 * 60)) / 1000;

            String stringDayTotal="";
            String stringHourTotal="";
            String stringMinuteTotal="";
            String stringSecondTotal="";

            if(dayTotal<10) stringDayTotal="0"+dayTotal;
            else stringDayTotal=stringDayTotal+dayTotal;

            if(hourTotal<10) stringHourTotal="0"+hourTotal;
            else stringHourTotal=stringHourTotal+hourTotal;

            if(minuteTotal<10) stringMinuteTotal="0"+minuteTotal;
            else stringMinuteTotal=stringMinuteTotal+minuteTotal;

            if(secondTotal<10) stringSecondTotal="0"+stringSecondTotal;
            else stringSecondTotal=stringSecondTotal+secondTotal;

            String stringTotalTime=stringDayTotal+":"+stringHourTotal+":"+stringMinuteTotal+":"+stringSecondTotal;
            timerTotal.setText(stringTotalTime);

        }
    };

    class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            timerController=(TimerService.TimerController)iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

}
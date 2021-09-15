package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener{
    private static SeekBar seekBar;
    private static TextView songName,songProgress,songTotal;
    private ObjectAnimator animator;

    private MusicService.MusicController controller;
    String name;
    Intent intentReceive,intentSend;

    MyServiceConnection serviceConnection;
    private boolean isUnbind=false;//记录服务是否被解绑



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        intentReceive=getIntent();
        init();
    }

    public void init(){
        songProgress=(TextView) findViewById(R.id.song_progress);
        songTotal=(TextView) findViewById(R.id.song_total);
        songName=(TextView) findViewById(R.id.song_name);
        seekBar=(SeekBar) findViewById(R.id.seek_bar);

        findViewById(R.id.play_button).setOnClickListener(this);
        findViewById(R.id.pause_button).setOnClickListener(this);
        findViewById(R.id.continue_button).setOnClickListener(this);
        findViewById(R.id.exit_button).setOnClickListener(this);

        String name=intentReceive.getStringExtra("name");
        songName.setText(name);

        intentSend=new Intent(this,MusicService.class);//创建意图对象
        serviceConnection=new MyServiceConnection();//创建服务连接对象

        bindService(intentSend,serviceConnection, Service.BIND_AUTO_CREATE);//绑定服务

        //为滑动条添加事件监听
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
                if(i==seekBar.getMax()){//当滑动条到末端时，结束动画
                    animator.pause();//停止播放动画
                }
            }

            /**
             * 滑动条开始滑动时调用此方法
             * @param seekBar
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            /**
             *
             * @param seekBar
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //根据拖动的进度改变音乐播放进度
                int seekProgress=seekBar.getProgress();//获得seekBar的进度
                controller.seekTo(seekProgress);//改变音乐播放进度
            }
        });

        ImageView imageViewMusic=(ImageView)findViewById(R.id.imageview_music);
        String position=intentReceive.getStringExtra("position");
        int i=Integer.parseInt(position);
        imageViewMusic.setImageResource(FragSongList.icons[i]);

        animator= ObjectAnimator.ofFloat(imageViewMusic,"rotation",0f,360.0f);
        animator.setDuration(10000);//动画旋转一周的时间为10s
        animator.setInterpolator(new LinearInterpolator());//匀速
        animator.setRepeatCount(-1);//-1设置动画无限循环

    }

    /**
     * 创建消息处理器对象
     */
    public static Handler handler=new Handler(){
        //在主线程中处理子线程发过来的消息
        @Override
        public void handleMessage(@NonNull Message msg) {
            //获取从子线程发过来的音乐播放进度
            Bundle bundle=msg.getData();
            int duration=bundle.getInt("duration");
            int currentPosition=bundle.getInt("currentPosition");
            seekBar.setMax(duration);
            seekBar.setProgress(currentPosition);

            //歌曲总时长
            int minute=duration/1000/60;
            int second=duration/1000%60;
            String stringMinute=null;
            String stringSecond=null;

            if(minute<10){
                stringMinute="0"+minute;
            }else{
                stringMinute=""+minute;
            }

            if(second<10){
                stringSecond="0"+second;
            }else {
                stringSecond=""+second;
            }

            songTotal.setText(stringMinute+":"+stringSecond);

            //歌曲当前播放时长
            int currentMinute=currentPosition/1000/60;
            int currentSecond=currentPosition/1000%60;
            String stringCurrentMinute=null;
            String stringCurrentSecond=null;

            if(currentMinute<10){
                stringCurrentMinute="0"+currentMinute;
            }else {
                stringCurrentMinute=""+currentMinute;
            }

            if(currentSecond<10){
                stringCurrentSecond="0"+currentSecond;
            }else {
                stringCurrentSecond=""+currentSecond;
            }

            songProgress.setText(stringCurrentMinute+":"+stringCurrentSecond);

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play_button:
                //播放按钮点击事件
                String position=intentReceive.getStringExtra("position");
                int i=Integer.parseInt(position);
                controller.play(i);
                animator.start();
                break;
            case R.id.pause_button:
                //暂停按钮点击事件
                controller.pausePlay();
                animator.pause();
                break;
            case R.id.continue_button:
                //继续播放按钮点击事件
                controller.continuePlay();
                animator.start();
                break;
            case R.id.exit_button:
                //退出按钮点击事件
                unBind(isUnbind);
                isUnbind=true;
                finish();
                break;
        }
    }

    class MyServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            controller=(MusicService.MusicController)iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

    public void unBind(boolean isUnbind){
        if(!isUnbind){
            controller.pausePlay();//暂停播放音乐
            unbindService(serviceConnection);//解绑服务
        }
    }
}
package com.example.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service {
    private MediaPlayer player;
    private Timer timer;
    private static final String TAG="tag";

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MusicController();
    }

    public void onCreate(){
        Log.i(TAG,"onCreate()");
        super.onCreate();
        player=new MediaPlayer(); //create new MediaPlayer
    }

    /**
     * 添加计时器用于设置音乐播放器中的进度条
     */
    public void addTimer(){
        if(timer==null){
            timer=new Timer();//创建计时器对象
            TimerTask task=new TimerTask() {
                @Override
                public void run() {
                    if(player==null)return;
                    int duration=player.getDuration();//获取歌曲总时长
                    int currentPosition=player.getCurrentPosition();//获取播放进度
                    Message message=MusicActivity.handler.obtainMessage();//创建消息对象
                    //将音乐的总时长和播放进度封装到message对象中
                    Bundle bundle=new Bundle();
                    bundle.putInt("duration",duration);
                    bundle.putInt("currentPosition",currentPosition);
                    message.setData(bundle);

                    //将消息发送到主线程的消息队列
                    MusicActivity.handler.sendMessage(message);
                }
            };
            /**
             * Android中Timer是一个普通的类，其中有几个重要的方法；而TimerTask则是一个抽象类，其中含有一个抽象方法run()。
             * 使用Timer类中的schedule()方法可以完成对TimerTask的调度，该方法具有三个参数，其函数声明如下：
             * public void schedule(TimerTask task, long delay, long period)
             * 其中第一个参数为TimerTask的对象，通过实现其中的run()方法可以周期的执行某一个任务；第二个参数表示延迟的时间，即多长时间后开始执行；第三个参数表示执行的周期。
             *
             * 多个TimerTask是可以共用一个Timer的，通过调用Timer的schedule方法可以创建一个线程，并且调用一次schedule后TimerTask是无限的循环下去的，
             * 使用Timer的cancel()停止操作。当同一个Timer执行一次cancle()方法后，所有Timer线程都被终止。
             */
            //开始计时任务后的5毫秒，开始执行第一次任务，以后每500毫秒执行一次
            timer.schedule(task,5,500);
        }
    }

    public void onDestroy(){
        super.onDestroy();
        if(player==null)return;
        if(player.isPlaying())player.stop();//停止播放音乐
        player.release();//释放占用的资源
        player=null;
    }



    class MusicController extends Binder{//Binder是一种跨进程的通信方式
        public MusicController(){}

        public void play(int i){
            //String path
            Uri uri=Uri.parse("android.resource://"+getPackageName()+"/raw/music"+(i+1));

            try{
                player.reset();//音乐播放器处于idle状态
                //加载多媒体文件
                player=MediaPlayer.create(getApplicationContext(),uri);

                /**
                 * getApplicationContext()
                 * 在当前app的任意位置使用这个函数得到的是同一个Context;
                 * 使用getApplicationContext 取得的是当前app所使用的application，这在AndroidManifest中唯一指定
                 */

                /**
                 * start():开始或恢复播放
                 */
                player.start();//播放音乐
                addTimer();//添加计时器
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public void pausePlay(){
            player.pause();//暂停播放音乐
        }

        public void continuePlay(){
            player.start();//继续播放音乐
        }

        public void seekTo(int position){
            player.seekTo(position);//设置音乐的播放位置
        }
    }
}
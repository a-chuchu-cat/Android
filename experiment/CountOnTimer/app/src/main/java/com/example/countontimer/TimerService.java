package com.example.countontimer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service {

    public static final String TOTAL="total";
    public static final String DAY="day";
    public static final String HOUR="hour";
    public static final String MINUTE="minute";
    public static final String SECOND="second";
    private Timer timer;

    public TimerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new TimerController();
    }

    class TimerController extends Binder{
        long total=0;//倒数器总时长
        long day=0;//天
        long hour=0;//小时
        long minute=0;//分钟
        long second=0;//秒

        public TimerController(){}

        //得到天、小时、分钟、秒基本信息，同来添加计时器
        public void setInformation(long total,long day,long hour,long minute,long second){
            this.total=total;
            this.day=day;
            this.hour=hour;
            this.minute=minute;
            this.second=second;
        }

        //添加计时器用来设置倒数器中的进度条
        public void addTimer(){
            timer=new Timer();
            TimerTask timerTask=new TimerTask() {
                @Override
                public void run() {
                    Message message=MainActivity.handler.obtainMessage();
                    Bundle bundle=new Bundle();
                    bundle.putLong(TOTAL,total);
                    bundle.putLong(DAY,day);
                    bundle.putLong(HOUR,hour);
                    bundle.putLong(MINUTE,minute);
                    bundle.putLong(SECOND,second);

                    message.setData(bundle);
                    MainActivity.handler.sendMessage(message);
                }
            };

            /**
             * Android中Timer是一个普通的类，其中有几个重要的方法；而TimerTask则是一个抽象类，其中含有一个抽象方法run()。
             * 使用Timer类中的schedule()方法可以完成对TimerTask的调度，该方法具有三个参数，其函数声明如下：
             * public void schedule(TimerTask task, long delay, long period)
             * 其中第一个参数为TimerTask的对象，通过实现其中的run()方法可以周期的执行某一个任务；第二个参数表示延迟的时间，即多长时间后开始执行；第三个参数表示执行的周期。
             *
             * 多个TimerTask是可以共用一个Timer的，通过调用Timer的schedule方法可以创建一个线程，并且调用一次schedule后TimerTask是无限的循环下去的，
             * 使用Timer的cancel()停止操作。当同一个Timer执行一次cancel()方法后，所有Timer线程都被终止。
             */

            timer.schedule(timerTask,0,1000);
        }

        //开始倒数器
        public void start(){
            addTimer();
        }
    }


}
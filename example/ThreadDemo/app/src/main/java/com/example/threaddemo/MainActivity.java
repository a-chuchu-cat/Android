package com.example.threaddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button startThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Runnable myWorker=new Runnable() {
            @Override
            public void run() {
                long endTime=System.currentTimeMillis()+10*1000;

                if(System.currentTimeMillis()<endTime){
                    synchronized(this){
                        try{
                            wait();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        startThread=(Button) findViewById(R.id.start_thread);

        startThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(null,myWorker,"myWorker").start();
                Log.e("tag","线程开始");
            }
        });

    }
}
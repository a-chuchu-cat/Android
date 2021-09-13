package com.example.broadcastappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button send;
    private MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter=new IntentFilter();
        filter.addAction("android.intent.action.MY_BROADCAST");
        filter.addAction("android.intent.action.BATTERY_CHANGED");
        receiver=new MyReceiver();
        registerReceiver(receiver,filter);

        send=(Button) findViewById(R.id.send_broadcast);
        send.setOnClickListener(view -> {
            Intent intent=new Intent("android.intent.action.MY_BROADCAST");
            intent.putExtra("broadcast_message","test broadcast");
            sendBroadcast(intent);
        });


    }

    protected void onDestroy() {

        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
package com.example.broadcastappdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action=intent.getAction();
        if(action.equals("android.intent.action.MY_BROADCAST")) {
            String message = intent.getStringExtra("broadcast_message");
            Toast.makeText(context, "message:" + message, Toast.LENGTH_SHORT).show();
        }

        if(action.equals("android.intent.action.BATTERY_CHANGED")){
            Bundle bundle=intent.getExtras();
            int level= bundle.getInt("level");
            int scale=bundle.getInt("scale");
            Toast.makeText(context, "当前电量："+level+"\n总电量："+scale, Toast.LENGTH_SHORT).show();
        }
    }
}
package com.example.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button readable;
    private Button writeable;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private GestureDetector detector;
    private MyGestureListener listener;

    //SharedPreferences文件名
    public static final String SharedPreferencesFilename="config";

    //键
    private static final String UserName="UserName";
    private static final String LoginDate="LoginDate";
    private static final String UserType="UserType";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences(SharedPreferencesFilename,MODE_PRIVATE);
        editor=sharedPreferences.edit();

        readable=(Button) findViewById(R.id.readable);
        writeable=(Button)findViewById(R.id.writeable);

        listener=new MyGestureListener();
        detector=new GestureDetector(this,listener);

        writeable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
                String loginDate=simple.format(new Date());

                //写入键值对
                editor.putString(UserName,"鹿星荷");
                editor.putString(LoginDate,loginDate);
                editor.putInt(UserType,1);

                //提交修改
                editor.apply();
            }
        });

        readable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName=sharedPreferences.getString(UserName,"");
                String loginDate=sharedPreferences.getString(LoginDate,"");
                int userType=sharedPreferences.getInt(UserType,0);

                Toast.makeText(MainActivity.this, "UserName:"+userName+"  LoginDate:"+loginDate+"  UserType:"+userType, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean onTouchEvent(MotionEvent event){
        return detector.onTouchEvent(event);
    }

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        public boolean onFling(MotionEvent e1,MotionEvent e2,float v1,float v2){
            if(e1.getX()-e2.getX()>0){
                Intent intent=new Intent(MainActivity.this,InformationActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "通过手势启动InformationActivity", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    }
}
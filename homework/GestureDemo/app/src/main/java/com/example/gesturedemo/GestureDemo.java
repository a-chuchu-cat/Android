package com.example.gesturedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class GestureDemo extends AppCompatActivity {

    public static final int MOVE_MIN=500;
    private GestureDetector detector;
    private MyGestureDemoListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_demo);

        listener=new MyGestureDemoListener();
        detector=new GestureDetector(this,listener);
    }

    public boolean onTouchEvent(MotionEvent event){
        return detector.onTouchEvent(event);
    }

    public class MyGestureDemoListener extends GestureDetector.SimpleOnGestureListener{
        public boolean onFling(MotionEvent e1,MotionEvent e2,float v1,float v2){
            if(e1.getY()-e2.getY()>MOVE_MIN){
                startActivity(new Intent(GestureDemo.this,GestureDemo.class));
                Toast.makeText(GestureDemo.this, "通过手势启动Activity", Toast.LENGTH_SHORT).show();
            }else{
                finish();
                Toast.makeText(GestureDemo.this, "通过手势关闭Activity", Toast.LENGTH_SHORT).show();
            }

            return true;
        }
    }
}
package com.example.gesturedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private GestureDetector detector;
    private MyGestureListener gestureListener;
    private Button buttonChangeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureListener=new MyGestureListener();
        detector=new GestureDetector(this,gestureListener);

        buttonChangeActivity=(Button) findViewById(R.id.button_change_activity);
        buttonChangeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,GestureDemo.class);
                startActivity(intent);
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event){
        return detector.onTouchEvent(event);
    }

    public class MyGestureListener implements GestureDetector.OnGestureListener{
        private static final String TAG="MyGesture";

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            Log.d(TAG,"onDown:按下");
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {
            Log.d(TAG,"onShowPress:手指按下一段时间，但还没有到长按");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            Log.d(TAG,"onSingleTapUp:手指离开屏幕一瞬间");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            Log.d(TAG,"onScroll:在触摸屏上滑动");
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            Log.d(TAG,"onLongPress:长按且没有松动");
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            Log.d(TAG,"onFling:迅速滑动，并松开");
            return false;
        }
    }

}
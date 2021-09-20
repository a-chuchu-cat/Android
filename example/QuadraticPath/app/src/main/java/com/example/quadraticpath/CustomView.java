package com.example.quadraticpath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomView extends View {
    List<Path> pathStrokes=new ArrayList<>();
    Path pathStroke;
    Bitmap bitmap;
    Paint paint;
    Canvas canvas;
    boolean isOnTouch=false;

    //上一个点
    float oldX;
    float oldY;

    public CustomView(Context context){
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    void drawStrokes(){
        if(canvas==null){
            //缓冲位图
            bitmap=Bitmap.createBitmap(getWidth(),getHeight(),Bitmap.Config.ARGB_8888);
            canvas=new Canvas();//缓冲画布
            canvas.setBitmap(bitmap);//为画布设置位图，实际上图片保存在位图中
            paint=new Paint();//画笔
            paint.setAntiAlias(true);//抗锯齿
            paint.setColor(Color.YELLOW);//画笔颜色
            paint.setStyle(Paint.Style.STROKE);//设置填充类型
            paint.setStrokeWidth(5);//设置画笔宽度
        }

        for(Path path:pathStrokes){
            canvas.drawPath(path,paint);
        }

        invalidate();//刷新屏幕
    }

    public void onDraw(Canvas canvas){
        Paint paint1=new Paint();
        if(bitmap!=null){
            canvas.drawBitmap(bitmap,0,0,paint1);
        }

    }

    public boolean onTouchEvent(MotionEvent event){
        float x=event.getX();
        float y=event.getY();

        //每次落下-抬起之间经过的点为一个笔画
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN://落下
                pathStroke=new Path();
                oldX=x;
                oldY=y;
                pathStroke.moveTo(x,y);
                pathStrokes.add(pathStroke);
                isOnTouch=true;
                break;
            case MotionEvent.ACTION_MOVE://移动
                //Add a quadratic bizer from the last point, approaching to control point(x1,y1), and ending at point(x2,y2)
                if(isOnTouch) {
                    pathStroke.quadTo(oldX, oldY, x, y);
                    oldX = x;
                    oldY = y;
                    drawStrokes();
                }
                break;
            case MotionEvent.ACTION_UP://抬起
                if(isOnTouch){
                    pathStroke.quadTo(oldX,oldY,x,y);
                    drawStrokes();
                    isOnTouch=false;
                }
                break;
        }

        return true;
    }
}

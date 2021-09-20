package com.example.xml;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView=findViewById(R.id.image_view);
        transition=findViewById(R.id.transition);

        Resources resource=this.getResources();
        TransitionDrawable transitionDrawable=(TransitionDrawable) resource.getDrawable(R.drawable.transition_demo);

        imageView.setImageDrawable(transitionDrawable);

        transition.setOnClickListener(view ->{
            //开始过渡，过渡时间为1000ms
            transitionDrawable.startTransition(1000);
        });

    }
}
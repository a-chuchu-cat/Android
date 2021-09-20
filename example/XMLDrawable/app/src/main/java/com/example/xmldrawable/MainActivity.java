package com.example.xmldrawable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Create the linearlayout in which to add the image
        LinearLayout linearLayout=new LinearLayout(this);
        ImageView imageView=new ImageView(this);
        imageView.setImageResource(R.drawable.image);
        imageView.setAdjustViewBounds(true);//Set the image bounds to match the drawable dimensions

        imageView.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //Add the imageView to the layout and set the layout as the content view
        linearLayout.addView(imageView);

        setContentView(linearLayout);
    }
}
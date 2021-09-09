package com.example.activityhomework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnotherActivity extends AppCompatActivity {

    public static final int MAINACTIVITY_RESULTCODE=1;
    private Button backContentButton;
    private TextView backContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        backContent=(TextView) findViewById(R.id.back_content);
        backContentButton=(Button)findViewById(R.id.back_content_button);

        Intent intent=getIntent();
        String content=intent.getStringExtra("content");
        backContent.setText("从MainActivity接收的数据："+content);

        backContentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String processData="经过AnotherActivity处理的数据："+content;
                intent.putExtra("processData",processData);

                setResult(MAINACTIVITY_RESULTCODE,intent);
                finish();
            }
        });
    }
}
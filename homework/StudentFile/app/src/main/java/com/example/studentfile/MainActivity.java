package com.example.studentfile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private Button readable;
    private Button writeable;
    private static final String StudentFileName="student.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readable=(Button) findViewById(R.id.readable);
        writeable=(Button) findViewById(R.id.writeable);


        writeable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream output = openFileOutput(StudentFileName, MODE_PRIVATE);
                    String information = "学号：" + 2019011163 + "  姓名：梁矗";
                    BufferedOutputStream outputStream=new BufferedOutputStream(output);
                    outputStream.write(information.getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                    outputStream.close();
                    Toast.makeText(MainActivity.this, "成功向"+StudentFileName+"写入"+information, Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        readable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    FileInputStream input=openFileInput(StudentFileName);
                    BufferedInputStream inputStream=new BufferedInputStream(input);
                    byte[] content=new byte[1024];
                    int length=inputStream.read(content);
                    String information=new String(content,0,content.length);
                    Toast.makeText(MainActivity.this, "从文件"+StudentFileName+"成功读取"+information, Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
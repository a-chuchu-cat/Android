package com.example.studentsharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {
    private Button readable;
    private Button writeable;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    //SharedPreferences文件名
    private static final String SharedPreferencesFileName="student";

    private static final String ID="id";
    private static final String NAME="name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences(SharedPreferencesFileName,MODE_PRIVATE);
        editor=sharedPreferences.edit();

        readable=(Button) findViewById(R.id.readable);
        writeable=(Button) findViewById(R.id.writeable);

        writeable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(ID,"2019011163");
                editor.putString(NAME,"梁矗");

                editor.apply();
            }
        });

        readable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=sharedPreferences.getString(ID,"");
                String name=sharedPreferences.getString(NAME,"");
                Toast.makeText(MainActivity.this, "学号："+id+"  姓名："+name, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
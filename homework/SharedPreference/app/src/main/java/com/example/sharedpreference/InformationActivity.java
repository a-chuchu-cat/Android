package com.example.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    TextView information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        //获取其他应用程序的context
        try {
            context=createPackageContext("com.example.sharedpreference",CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        sharedPreferences=context.getSharedPreferences(MainActivity.SharedPreferencesFilename,MODE_WORLD_READABLE);
        String userName=sharedPreferences.getString("UserName","");

        information=(TextView) findViewById(R.id.information);

        information.setText("UserName:"+userName);

    }
}
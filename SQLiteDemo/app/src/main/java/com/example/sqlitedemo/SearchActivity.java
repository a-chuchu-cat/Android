package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    private MyGestureListener listener;
    private GestureDetector detector;
    private ListView search_list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listener=new MyGestureListener();
        detector=new GestureDetector(this,listener);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        ArrayList<Map<String,String>> items= (ArrayList<Map<String, String>>) bundle.getSerializable("result");

        search_list_view=(ListView) findViewById(R.id.search_word_listview);

        SimpleAdapter simple=new SimpleAdapter(this,items,R.layout.word_layout,
                new String[]{Words.Word._ID,Words.Word.TABLE_COLUMN_WORD,Words.Word.TABLE_COLUMN_MEANING,Words.Word.TABLE_COLUMN_SAMPLE},
                new int[]{R.id.word_id,R.id.word,R.id.meaning,R.id.sample});

        search_list_view.setAdapter(simple);

    }

    public boolean onTouchEvent(MotionEvent event){
        return detector.onTouchEvent(event);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        public boolean onFling(MotionEvent e1,MotionEvent e2,float v1,float v2){
            if(e2.getX()-e1.getX()>0){
                Intent intent=new Intent(SearchActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
                return true;
            }else {
                return false;
            }
        }
    }
}
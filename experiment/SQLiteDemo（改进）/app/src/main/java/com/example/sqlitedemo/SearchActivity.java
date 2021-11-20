package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    private MyGestureListener listener;
    private GestureDetector detector;

    private ListView search_list_view;

    private RightFragment rightFragment;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    private WordDBHelper wordDBHelper;

    Intent intent;
    Bundle bundle;
    ArrayList<Map<String,String>> items;

    public void onConfigurationChanged(Configuration configuration){
        super.onConfigurationChanged(configuration);

        if(Configuration.ORIENTATION_LANDSCAPE==configuration.orientation){
            setContentView(R.layout.landsacpe_layout);
        }else {
            setContentView(R.layout.search_layout);
        }

        init();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();

    }

    public void init(){
        listener=new MyGestureListener();
        detector=new GestureDetector(this,listener);

        intent=getIntent();
        bundle=intent.getExtras();
        items= (ArrayList<Map<String, String>>) bundle.getSerializable("result");

        search_list_view=(ListView) findViewById(R.id.word_listview);

        SimpleAdapter simple=new SimpleAdapter(this,items,R.layout.word_layout,
                new String[]{Words.Word._ID,Words.Word.TABLE_COLUMN_WORD,Words.Word.TABLE_COLUMN_MEANING},
                new int[]{R.id.word_id,R.id.word,R.id.meaning});

        search_list_view.setAdapter(simple);

        registerForContextMenu(search_list_view);

        wordDBHelper=new WordDBHelper(this);

        rightFragment=new RightFragment();

        manager=getFragmentManager();
        transaction=manager.beginTransaction();

        transaction.replace(R.id.right_fragment,rightFragment).commit();
    }

    //Inflate the context menu
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,view,menuInfo);
        getMenuInflater().inflate(R.menu.search_context_menu,menu);
    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info;
        TextView word;
        View itemView;

        switch(item.getItemId()){
            case R.id.search_sample:
                info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                itemView=info.targetView;
                word=(TextView) itemView.findViewById(R.id.word);
                if(word!=null){
                    String word_word=word.getText().toString();
                    String word_sample=searchSample(word_word);
                    rightFragment.getTextView(new RightFragment.Callback() {
                        @Override
                        public void getSample(TextView textView) {
                            textView.setText(word_sample);
                        }
                    });
                }
                break;
        }

        return true;
    }

    //查找Word对应的sample
    public String searchSample(String word){
        SQLiteDatabase database=wordDBHelper.getReadableDatabase();

        String[] projection={
                Words.Word.TABLE_COLUMN_SAMPLE
        };

        String selection=Words.Word.TABLE_COLUMN_WORD+"=?";
        String[] selectionArgs={word};

        String sample="";

        Cursor cursor=database.query(Words.Word.TABLE_NAME,projection,selection,selectionArgs,null,null,null);

        if(cursor!=null){
            while(cursor.moveToNext()){
                sample=cursor.getString(0);
            }
        }

        return sample;

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
                Intent intent=new Intent(SearchActivity.this,ContentProviderActivity.class);
                startActivity(intent);
                return true;
            }
        }
    }
}
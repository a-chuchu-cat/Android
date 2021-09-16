package com.example.sqlitedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContentProviderActivity extends AppCompatActivity {
    private static final String TAG="tag";
    private ContentResolver contentResolver;
    private Cursor cursor;

    ListView wordListview;
    private static final String WORD_ID="word_id";
    private static final String WORD="word";
    private static final String MEANING="meaning";
    private static final String SAMPLE="sample";

    private MyGestureListener listener;
    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        contentResolver=getContentResolver();

        wordListview = (ListView) findViewById(R.id.word_listview);

        ArrayList<Map<String,String>> word_list=getAll();

        setAdapter(wordListview,word_list);

        registerForContextMenu(wordListview);

        listener=new MyGestureListener();
        detector=new GestureDetector(this,listener);
    }

    public boolean onTouchEvent(MotionEvent event){
        return detector.onTouchEvent(event);
    }

    //Inflate the menu
    /**
     * 此方法用于初始化菜单，其中menu参数就是即将要显示的Menu实例。 返回true则显示该menu,false 则不显示;
     * (只会在第一次初始化菜单时调用) Inflate the menu; this adds items to the action bar
     * if it is present.
     **/
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    //Handle action bar items click here
    /**
     * 菜单项被点击时调用，也就是菜单项的监听方法。
     * 通过这几个方法，可以得知，对于Activity，同一时间只能显示和监听一个Menu 对象。
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();

        switch(id){
            case R.id.search_word:
                searchDialog();
                break;
            case R.id.insert_word:
                insertDialog();
                break;
            case R.id.help:
                Toast.makeText(ContentProviderActivity.this, "这是帮助文档", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    //新增对话框
    public void insertDialog(){
        final TableLayout tableLayout=(TableLayout) getLayoutInflater().inflate(R.layout.insert_layout,null);
        new AlertDialog.Builder(this)
                .setTitle("新增单词")
                .setView(tableLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String word=((EditText)tableLayout.findViewById(R.id.word_edit_text)).getText().toString();
                        String meaning=((EditText)tableLayout.findViewById(R.id.meaning_edit_text)).getText().toString();
                        String sample=((EditText)tableLayout.findViewById(R.id.sample_edit_text)).getText().toString();

                        insert(word,meaning,sample);
                        ArrayList<Map<String,String>> items=getAll();

                        setAdapter(wordListview,items);

                    }
                })
                .setNegativeButton("取消",(dialogInterface, i) -> {
                    ArrayList<Map<String,String>> items=getAll();
                    setAdapter(wordListview,items);
                })
        .create()
        .show();
    }

    //新增对话框
    public void searchDialog(){
        final LinearLayout linearLayout=(LinearLayout) getLayoutInflater().inflate(R.layout.search_layout,null);
        EditText editText=(EditText)linearLayout.findViewById(R.id.search_edit_text);
        new AlertDialog.Builder(this)
                .setTitle("查询单词")
                .setView(linearLayout)
                .setPositiveButton("确定",((dialogInterface, i) -> {
                    if(editText!=null){
                        String searchWord=editText.getText().toString();
                        ArrayList<Map<String,String>> items=search(searchWord);

                        if(items.size()>0){
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("result",items);
                            Toast.makeText(ContentProviderActivity.this, "查找的单词："+editText.getText().toString(), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(ContentProviderActivity.this,SearchActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else{
                            Toast.makeText(ContentProviderActivity.this, "没有找到", Toast.LENGTH_SHORT).show();
                        }
                    }
                }))
                .setNegativeButton("取消",((dialogInterface, i) -> {
                    ArrayList<Map<String,String>> items=getAll();
                    setAdapter(wordListview,items);
                }))
                .create()
                .show();
    }

    public void insert(String word,String meaning,String sample){
        //create a new map of values,where column names are the key
        ContentValues values=new ContentValues();

        values.put(WORD,word);
        values.put(MEANING,meaning);
        values.put(SAMPLE,sample);

        contentResolver.insert(Constant.CONTENT_URI,values);
    }

    public ArrayList<Map<String, String>> search(String wordSearch){
        String[] projection={
                Words.Word._ID,
                Words.Word.TABLE_COLUMN_WORD,
                Words.Word.TABLE_COLUMN_MEANING,
                Words.Word.TABLE_COLUMN_SAMPLE
        };

        String order=Words.Word.TABLE_COLUMN_WORD+" desc";
        String selection=Words.Word.TABLE_COLUMN_WORD+" like ?";
        String[] selectionArgs={"%"+wordSearch+"%"};

        Cursor cursor=contentResolver.query(Constant.CONTENT_URI,projection,selection,selectionArgs,order);

        return convertToList(cursor);
    }

    //cursor to ArrayList
    public ArrayList<Map<String,String>> convertToList(Cursor cursor){
        ArrayList<Map<String,String>> items = new ArrayList<>();
        if(cursor!=null){
            while(cursor.moveToNext()){
                Map<String,String> item=new HashMap<>();
                item.put(Words.Word._ID,cursor.getString(0));
                item.put(Words.Word.TABLE_COLUMN_WORD,cursor.getString(1));
                item.put(Words.Word.TABLE_COLUMN_MEANING,cursor.getString(2));
                item.put(Words.Word.TABLE_COLUMN_SAMPLE,cursor.getString(3));

                items.add(item);
            }
        }

        return items;
    }

    //从数据库中读入内容
    public ArrayList<Map<String, String>> getAll() {
        ArrayList<Map<String, String>> list = new ArrayList<>();

        /**
         * 主要区别是rawQuery是直接使用SQL语句进行查询的，也就是第一个参数字符串，在字符串内的“？”会被后面的String[]数组逐一对换掉；而query函数是Android自己封装的查询API
         */

        String[] projections={Constant.Word._ID,Constant.Word.TABLE_COLUMN_WORD,Constant.Word.TABLE_COLUMN_MEANING,Constant.Word.TABLE_COLUMN_SAMPLE};

        Cursor cursor = contentResolver.query(Constant.CONTENT_URI,projections,null,null,null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Map<String, String> words = new HashMap<>();
                words.put(Words.Word._ID, cursor.getString(0));
                words.put(Words.Word.TABLE_COLUMN_WORD, cursor.getString(1));
                words.put(Words.Word.TABLE_COLUMN_MEANING, cursor.getString(2));
                words.put(Words.Word.TABLE_COLUMN_SAMPLE, cursor.getString(3));
                list.add(words);
            }
        }
        return list;
    }

    //增加适配器，在列表中显示单词
    public void setAdapter(ListView wordListview, ArrayList<Map<String, String>> item) {
        SimpleAdapter simple = new SimpleAdapter(ContentProviderActivity.this, item, R.layout.word_layout,
                new String[]{Words.Word._ID, Words.Word.TABLE_COLUMN_WORD, Words.Word.TABLE_COLUMN_MEANING, Words.Word.TABLE_COLUMN_SAMPLE},
                new int[]{R.id.word_id, R.id.word, R.id.meaning, R.id.sample});

        wordListview.setAdapter(simple);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean onFling(MotionEvent e1, MotionEvent e2, float v1, float v2){
            if((e2.getX()-e1.getX())>0){
                Intent intent=new Intent(ContentProviderActivity.this,MainActivity.class);
                overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
                startActivity(intent);
            }
            return true;
        }
    }

    //Inflate the context menu
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo){
        super.onCreateContextMenu(menu,view,contextMenuInfo);
        getMenuInflater().inflate(R.menu.context_menu,menu);
    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info;
        TextView id;
        TextView word;
        TextView meaning;
        TextView sample;
        View itemView;

        switch (item.getItemId()){
            case R.id.delete:
                //删除单词
                info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                itemView=info.targetView;

                id=(TextView) itemView.findViewById(R.id.word_id);

                if(id!=null){
                    String word_id=id.getText().toString();
                    deleteDialog(word_id);
                }

                break;

            case R.id.update:
                info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                itemView=info.targetView;

                id=(TextView) itemView.findViewById(R.id.word_id);
                word=(TextView) itemView.findViewById(R.id.word);
                meaning=(TextView) itemView.findViewById(R.id.meaning);
                sample=(TextView) itemView.findViewById(R.id.sample);

                if(id!=null&&word!=null&&meaning!=null){
                    String word_id=id.getText().toString();
                    String word_word=word.getText().toString();
                    String word_meaning=meaning.getText().toString();
                    String word_sample=sample.getText().toString();

                    Toast.makeText(ContentProviderActivity.this,
                            "id:"+word_id+"word:"+word_word+"meaning:"+word_meaning+"sample:"+word_sample, Toast.LENGTH_SHORT).show();

                    updateDialog(word_id,word_word,word_meaning,word_sample);
                }
                break;
        }

        return true;
    }

    //删除单词
    public void delete(String word_id){
        //定义where子句
        String selection=Constant.Word._ID+"=?";
        //指定占位符实际对应的args
        String[] selectionArgs={word_id};

        //Issue the SQL
        contentResolver.delete(Constant.CONTENT_URI,selection,selectionArgs);
    }

    //删除对话框
    public void deleteDialog(String word_id){
        final TableLayout delete_layout= (TableLayout) getLayoutInflater().inflate(R.layout.delete_layout,null);
        Toast.makeText(ContentProviderActivity.this, "word_id:"+word_id, Toast.LENGTH_SHORT).show();
        new android.app.AlertDialog.Builder(ContentProviderActivity.this)
                .setTitle("删除单词")
                .setView(delete_layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(word_id);
                        ArrayList<Map<String, String>> items = getAll();
                        setAdapter(wordListview, items);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ArrayList<Map<String, String>> items = getAll();
                        setAdapter(wordListview, items);
                    }
                }).create().show();
    }

    //更新单词
    public void update(String word_id,String word,String meaning,String sample){
        ContentValues values=new ContentValues();

        values.put(Constant.Word.TABLE_COLUMN_WORD,word);
        values.put(Constant.Word.TABLE_COLUMN_MEANING,meaning);
        values.put(Constant.Word.TABLE_COLUMN_SAMPLE,sample);

        String selection=Constant.Word._ID+"=?";
        String[] selectionArgs={word_id};

        contentResolver.update(Constant.CONTENT_URI,values,selection,selectionArgs);
    }

    //更新对话框
    public void updateDialog(String word_id,String word,String meaning,String sample){
        final TableLayout updateLayout=(TableLayout)getLayoutInflater().inflate(R.layout.update_layout,null);
        EditText wordEditText=(EditText)updateLayout.findViewById(R.id.update_word_edit_text);
        EditText meaningEditText=(EditText)updateLayout.findViewById(R.id.update_meaning_edit_text);
        EditText sampleEditText=(EditText)updateLayout.findViewById(R.id.update_sample_edit_text);

        wordEditText.setText(word);
        meaningEditText.setText(meaning);
        sampleEditText.setText(sample);

        Toast.makeText(this, "word_id:"+word_id, Toast.LENGTH_SHORT).show();

        new android.app.AlertDialog.Builder(ContentProviderActivity.this)
                .setTitle("更新单词")
                .setView(updateLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newWord=wordEditText.getText().toString();
                        String newMeaning=meaningEditText.getText().toString();
                        String newSample=sampleEditText.getText().toString();

                        update(word_id,newWord,newMeaning,newSample);
                        ArrayList<Map<String,String>> list=getAll();
                        setAdapter(wordListview,list);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ArrayList<Map<String,String>> list=getAll();
                        setAdapter(wordListview,list);
                    }
                }).create().show();
    }

}
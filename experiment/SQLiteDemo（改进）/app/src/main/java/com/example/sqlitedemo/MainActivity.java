package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String WORD_ID="word_id";
    private static final String WORD="word";
    private static final String MEANING="meaning";
    private static final String SAMPLE="sample";

    WordDBHelper wordDBHelper;
    ListView wordListview;

    private MyGestureListener listener;
    private GestureDetector detector;

    private RightFragment rightFragment;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    public void onConfigurationChanged(Configuration newConfiguration){
        super.onConfigurationChanged(newConfiguration);
        if(Configuration.ORIENTATION_LANDSCAPE==newConfiguration.orientation){
            setContentView(R.layout.landsacpe_layout);
        }else{
            setContentView(R.layout.activity_main);
        }
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    public void init(){
        wordListview = (ListView) findViewById(R.id.word_listview);
        wordDBHelper=new WordDBHelper(this);

        listener=new MyGestureListener();
        detector=new GestureDetector(this,listener);

        ArrayList<Map<String,String>> word_list=getAll();

        setAdapter(wordListview,word_list);

        registerForContextMenu(wordListview);

        rightFragment=new RightFragment();

        manager=getFragmentManager();
        transaction=manager.beginTransaction();

        transaction.replace(R.id.right_fragment,rightFragment).commit();
    }

    public boolean onTouchEvent(MotionEvent event){
        return detector.onTouchEvent(event);
    }

    //从数据库中读入内容
    public ArrayList<Map<String, String>> getAll() {
        ArrayList<Map<String, String>> list = new ArrayList<>();

        SQLiteDatabase database=wordDBHelper.getReadableDatabase();

        /**
         * 主要区别是rawQuery是直接使用SQL语句进行查询的，也就是第一个参数字符串，在字符串内的“？”会被后面的String[]数组逐一对换掉；而query函数是Android自己封装的查询API
         */
        Cursor cursor=database.rawQuery("select * from "+ Words.Word.TABLE_NAME,null);

        if (cursor!=null){
            while(cursor.moveToNext()){
                Map<String,String> words=new HashMap<>();
                words.put(Words.Word._ID,cursor.getString(0));
                words.put(Words.Word.TABLE_COLUMN_WORD,cursor.getString(1));
                words.put(Words.Word.TABLE_COLUMN_MEANING,cursor.getString(2));
                words.put(Words.Word.TABLE_COLUMN_SAMPLE,cursor.getString(3));
                list.add(words);
            }
        }
        return list;
    }

    //增加适配器，在列表中显示单词
    public void setAdapter(ListView wordListview, ArrayList<Map<String, String>> item) {
        SimpleAdapter simple = new SimpleAdapter(MainActivity.this, item, R.layout.word_layout,
                new String[]{Words.Word._ID, Words.Word.TABLE_COLUMN_WORD, Words.Word.TABLE_COLUMN_MEANING},
        new int[]{R.id.word_id, R.id.word, R.id.meaning});

        wordListview.setAdapter(simple);
    }

    public void onDestroy(){
        super.onDestroy();
        wordDBHelper.close();
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
                Toast.makeText(MainActivity.this, "这是帮助文档", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //使用insert方法增加单词
    public void insert(String word,String meaning,String sample){
        //get the data repository in write mode
        SQLiteDatabase database = wordDBHelper.getWritableDatabase();
        /**
         * getWritableDatabase() 方法以读写方式打开数据库，一旦数据库的磁盘空间满了，数据库就只能读而不能写，倘若使用的是getWritableDatabase() 方法就会出错。
         * getReadableDatabase()方法则是先以读写方式打开数据库，如果数据库的磁盘空间满了，就会打开失败，当打开失败后会继续尝试以只读方式打开数据库。如果该问题成功解决，则只读数据库对象就会关闭，然后返回一个可读写的数据库对象。
         */

        //create a new map of values,where column names are the key
        ContentValues values=new ContentValues();

        values.put(Words.Word.TABLE_COLUMN_WORD,word);
        values.put(Words.Word.TABLE_COLUMN_MEANING,meaning);
        values.put(Words.Word.TABLE_COLUMN_SAMPLE,sample);

        //Insert the new row, return the primary key of the new row
        /**
         * long	insert(String table, String nullColumnHack, ContentValues values)：向数据库指定表中插入数据，其中table为表的名字，values为插入的数据。
         */
        long newRowId=database.insert(Words.Word.TABLE_NAME,null,values);
    }

    //新增对话框
    public void insertDialog(){
        final TableLayout tableLayout=(TableLayout) getLayoutInflater().inflate(R.layout.insert_layout,null);
        new AlertDialog.Builder(this)
                .setTitle("新增单词")//标题
                .setView(tableLayout)//视图
                .setPositiveButton("insert", new DialogInterface.OnClickListener() {
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
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ArrayList<Map<String,String>> items=getAll();
                        setAdapter(wordListview,items);
                    }
                })
                .create()//创建对话框
                .show();//显示对话框
    }

    //Inflate the context menu
    public void onCreateContextMenu(ContextMenu menu, View view,ContextMenu.ContextMenuInfo contextMenuInfo){
        super.onCreateContextMenu(menu,view,contextMenuInfo);
        getMenuInflater().inflate(R.menu.context_menu,menu);
    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info;
        TextView id;
        TextView word;
        TextView meaning;
        View itemView;

        switch(item.getItemId()){
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

                if(id!=null&&word!=null&&meaning!=null){
                    String word_id=id.getText().toString();
                    String word_word=word.getText().toString();
                    String word_meaning=meaning.getText().toString();
                    String word_sample=searchSample(word_word);

                    Toast.makeText(MainActivity.this,
                            "id:"+word_id+"word:"+word_word+"meaning:"+word_meaning+"sample:"+word_sample, Toast.LENGTH_SHORT).show();

                    updateDialog(word_id,word_word,word_meaning,word_sample);
                }
                break;

            case R.id.sample:
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

    //删除单词
    public void delete(String word_id){
        SQLiteDatabase database=wordDBHelper.getReadableDatabase();
        //定义where子句
        String selection=Words.Word._ID+"=?";
        //指定占位符实际对应的args
        String[] selectionArgs={word_id};

        //Issue SQL statement
        database.delete(Words.Word.TABLE_NAME,selection,selectionArgs);
    }

    //删除对话框
    public void deleteDialog(String word_id){
        final TableLayout delete_layout= (TableLayout) getLayoutInflater().inflate(R.layout.delete_layout,null);
        Toast.makeText(MainActivity.this, "word_id:"+word_id, Toast.LENGTH_SHORT).show();
        new AlertDialog.Builder(MainActivity.this)
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

        values.put(Words.Word.TABLE_COLUMN_WORD,word);
        values.put(Words.Word.TABLE_COLUMN_MEANING,meaning);
        values.put(Words.Word.TABLE_COLUMN_SAMPLE,sample);


        String selection=Words.Word._ID+"=?";

        String[] selectionArgs={word_id};

        SQLiteDatabase database=wordDBHelper.getReadableDatabase();

        database.update(Words.Word.TABLE_NAME,values,selection,selectionArgs);
    }

    //更新对话框
    public void updateDialog(String word_id,String word,String meaning,String sample){
        final TableLayout updateLayout=(TableLayout)getLayoutInflater().inflate(R.layout.update_layout,null);
        EditText wordEditText=(EditText)updateLayout.findViewById(R.id.update_word_edit_text);
        EditText meaningEditText=(EditText)updateLayout.findViewById(R.id.update_meaning_edit_text);
        EditText sampleEditText = (EditText)updateLayout.findViewById(R.id.update_sample_edit_text);

        wordEditText.setText(word);
        meaningEditText.setText(meaning);
        sampleEditText.setText(sample);

        Toast.makeText(this, "word_id:"+word_id, Toast.LENGTH_SHORT).show();

        new AlertDialog.Builder(MainActivity.this)
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

    //模糊查询
    public ArrayList<Map<String,String>> search(String wordSearch){
        SQLiteDatabase database=wordDBHelper.getReadableDatabase();

        String[] projection={
                Words.Word._ID,
                Words.Word.TABLE_COLUMN_WORD,
                Words.Word.TABLE_COLUMN_MEANING,
                Words.Word.TABLE_COLUMN_SAMPLE
        };

        String order=Words.Word.TABLE_COLUMN_WORD+" desc";
        String selection=Words.Word.TABLE_COLUMN_WORD+" like ?";
        String[] selectionArgs={"%"+wordSearch+"%"};

        Cursor cursor = database.query(Words.Word.TABLE_NAME, projection, selection, selectionArgs, null, null, order);

        return convertToList(cursor);
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

    //查找对话框
    public void searchDialog(){
        final LinearLayout linearLayout=(LinearLayout) getLayoutInflater().inflate(R.layout.search_layout,null);
        EditText editText=(EditText)linearLayout.findViewById(R.id.search_edit_text);

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("查询单词")
                .setView(linearLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(editText!=null){
                            String search_word=editText.getText().toString();
                            ArrayList<Map<String,String>> items=search(search_word);

                            if(items.size()>0){
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("result",items);
                                Toast.makeText(MainActivity.this, "查找的单词："+editText.getText().toString(), Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MainActivity.this,SearchActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "没有找到", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ArrayList<Map<String,String>> items=getAll();
                        setAdapter(wordListview,items);
                    }
                }).create().show();
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean onFling(MotionEvent e1,MotionEvent e2,float v1,float v2){
            if((e1.getX()-e2.getX())>0){
                Intent intent=new Intent(MainActivity.this,ContentProviderActivity.class);
                startActivity(intent);
            }
            return true;
        }
    }
}
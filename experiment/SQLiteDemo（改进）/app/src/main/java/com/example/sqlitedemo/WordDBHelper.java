package com.example.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WordDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="WordsDB";
    private static final int DATABASE_VERSION=1;

    //建表SQL
    private static final String SQL_CREATE_DATABASE="create table "+Words.Word.TABLE_NAME+"("+
            Words.Word._ID+ " integer primary key autoincrement,"+ Words.Word.TABLE_COLUMN_WORD+" text,"+
            Words.Word.TABLE_COLUMN_MEANING+" text,"+Words.Word.TABLE_COLUMN_SAMPLE+" text)";

    //删表SQL
    private static final String SQL_DELETE_DATABASE="drop table if exists"+Words.Word.TABLE_NAME;

    public WordDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据库
        sqLiteDatabase.execSQL(SQL_CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //当数据库升级时被调用，首先删除旧表，然后调用OnCreate()创建新表
        sqLiteDatabase.execSQL(SQL_DELETE_DATABASE);
        onCreate(sqLiteDatabase);
    }
}

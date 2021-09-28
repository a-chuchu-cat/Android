package com.example.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class WordDataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="WordDataBase";
    public static final int DATABASE_VERSION=1;

    //建表SQL
    private static final String SQL_CREATE_DATABASE="create table "+Constant.Word.TABLE_NAME+"("+
            Constant.Word._ID+ " integer primary key autoincrement,"+ Constant.Word.TABLE_COLUMN_WORD+" text,"+
            Constant.Word.TABLE_COLUMN_MEANING+" text,"+Constant.Word.TABLE_COLUMN_SAMPLE+" text)";

    //删表SQL
    private static final String SQL_DELETE_DATABASE="drop table if exists"+Constant.Word.TABLE_NAME;

    public WordDataBaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_DATABASE);
        onCreate(sqLiteDatabase);
    }
}

package com.example.sqlitedemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyProvider extends ContentProvider {
    WordDataBaseHelper dbHelper=null;
    SQLiteDatabase db=null;
    private static UriMatcher matcher;

    static{
        matcher=new UriMatcher(UriMatcher.NO_MATCH);
        //注册Uri
        matcher.addURI(Constant.AUTHORITY,Constant.Word.TABLE_NAME,Constant.ITEM);
        matcher.addURI(Constant.AUTHORITY,Constant.Word.TABLE_NAME+"/#",Constant.ITEM_ID);
    }
    @Override
    public boolean onCreate() {
        dbHelper=new WordDataBaseHelper(getContext());
        db=dbHelper.getReadableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor=null;

        switch (matcher.match(uri)){
            case Constant.ITEM:
            case Constant.ITEM_ID:
                cursor=db.query(Constant.Word.TABLE_NAME,strings,s,strings1,null,null,s1);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI"+uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }


    /**
     * 根据匹配规则返回对应的类型
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (matcher.match(uri)){
            case Constant.ITEM:
                //Constant.CONTENT_TYPE:集合记录
                return Constant.CONTENT_TYPE;
            case Constant.ITEM_ID:
                //Constant.CONTENT_ITEM_TYPE:单条记录
                return Constant.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI"+uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
       long rowId;

       if(matcher.match(uri)!=Constant.ITEM){
           throw new IllegalArgumentException("Unknown URI"+uri);
       }

       rowId=db.insert(Constant.Word.TABLE_NAME,null,contentValues);

       if(rowId>0){
           Uri noteUri= ContentUris.withAppendedId(uri,rowId);
           getContext().getContentResolver().notifyChange(noteUri,null);
           return noteUri;
       }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        return db.delete(Constant.Word.TABLE_NAME, s, strings);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {

        return db.update(Constant.Word.TABLE_NAME,contentValues,s,strings);
    }
}

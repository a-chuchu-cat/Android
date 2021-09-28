package com.example.sqlitedemo;

import android.net.Uri;
import android.provider.BaseColumns;

public class Constant {
    public Constant(){}
    public static abstract class Word implements BaseColumns {
        public static final String TABLE_NAME="word";
        public static final String TABLE_COLUMN_WORD="word";//列：单词
        public static final String TABLE_COLUMN_MEANING="meaning";//列：单词含义
        public static final String TABLE_COLUMN_SAMPLE="sample";//单词示例
    }
    public static final String AUTHORITY="com.example.sqlitedemo";

    public static final Uri CONTENT_URI= Uri.parse("content://"+AUTHORITY+"/word");

    public static final int ITEM = 1;
    public static final int ITEM_ID = 2;

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/user";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/user";
}

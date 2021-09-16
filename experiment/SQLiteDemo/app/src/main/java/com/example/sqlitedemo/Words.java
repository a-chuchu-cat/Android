package com.example.sqlitedemo;

import android.provider.BaseColumns;

public class Words {
    public Words(){}

    public static abstract class Word implements BaseColumns{
        public static final String TABLE_NAME="words";
        public static final String TABLE_COLUMN_WORD="word";//列：单词
        public static final String TABLE_COLUMN_MEANING="meaning";//列：单词含义
        public static final String TABLE_COLUMN_SAMPLE="sample";//单词示例
    }
}

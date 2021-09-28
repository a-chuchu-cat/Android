package com.example.sqlitedemo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RightFragment extends Fragment{
    private TextView wordSample;
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.right_fragment,group,false);
        wordSample=view.findViewById(R.id.word_sample);
        return view;
    }

    public void getTextView(Callback callback){
        callback.getSample(wordSample);
    }

    public interface Callback{
        //回调方法
        public void getSample(TextView textView);
    }
}

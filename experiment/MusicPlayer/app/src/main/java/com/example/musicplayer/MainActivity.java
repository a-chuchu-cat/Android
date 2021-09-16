package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout content;
    private TextView menu_song;
    private TextView menu_album;

    private FragmentManager manager;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content=(FrameLayout) findViewById(R.id.content_frame);
        menu_song = (TextView) findViewById(R.id.menu_song);
        menu_album = (TextView) findViewById(R.id.menu_album);

//        addSong = (Button) findViewById(R.id.add_song);
//        deleteSong = (Button) findViewById(R.id.delete_song);

        menu_song.setOnClickListener(this);
        menu_album.setOnClickListener(this);

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        transaction.replace(R.id.content_frame, new FragSongList());
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        transaction=manager.beginTransaction();
        switch (view.getId()) {
            case R.id.menu_song:
                transaction.replace(R.id.content_frame, new FragSongList());
                break;
            case R.id.menu_album:
                transaction.replace(R.id.content_frame, new FragSongAlbum());
                break;

        }
        transaction.commit();
    }
}
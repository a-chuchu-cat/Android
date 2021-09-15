package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
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
        menu_song=(TextView) findViewById(R.id.menu_song);
        menu_album=(TextView) findViewById(R.id.menu_album);

        menu_song.setOnClickListener(this);
        menu_album.setOnClickListener(this);

        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();

        transaction.replace(R.id.content_frame,new FragSongList());
        transaction.commit();

    }

    @Override
    public void onClick(View view) {
        transaction=manager.beginTransaction();

        switch(view.getId()){
            case R.id.menu_song:
                transaction.replace(R.id.content_frame,new FragSongList());
                break;
            case R.id.menu_album:
                transaction.replace(R.id.content_frame,new FragSongAlbum());
                break;
            default:
                break;
        }
        transaction.commit();
    }
}
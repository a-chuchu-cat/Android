package com.example.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示歌曲列表
 */
public class FragSongList extends Fragment {
    private View view;
    public static String[] name={"LoveStory","月夜","有点甜"};
    public static int[] icons={R.drawable.music1,R.drawable.music2,R.drawable.music3};

//    public List<String> name=new ArrayList();

    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState){
        view=inflater.inflate(R.layout.music_list,group,false);
        MyBaseAdapter baseAdapter=new MyBaseAdapter();
        ListView songList=(ListView) view.findViewById(R.id.song_list);


        songList.setAdapter(baseAdapter);
        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(FragSongList.this.getContext(),MusicActivity.class);//创建Intent对象
                //将数据存入Intent对象
                intent.putExtra("name",name[i]);
                intent.putExtra("position",String.valueOf(i));
                startActivity(intent);
            }
        });
        return view;
    }

    /**
     * Android中Adapter类其实就是把数据源绑定到指定的View上，然后再返回该View，而返回来的这个View就是ListView中的某一 行item。
     * 这里返回来的View正是由我们的Adapter中的getView方法返回的。
     */
    class MyBaseAdapter extends BaseAdapter{

        //适配器中数据集的数据个数
        @Override
        public int getCount() {
            return name.length;
        }

        //获取数据集中与索引对应的数据项
        @Override
        public Object getItem(int i) {
            return name[i];
        }

        //获取指定行对应的ID
        @Override
        public long getItemId(int i) {
            return i;
        }

        //获取每一行item的显示内容
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view_item=View.inflate(FragSongList.this.getContext(),R.layout.item_layout,null);
            TextView itemName=(TextView) view_item.findViewById(R.id.item_name);
            ImageView imageView=(ImageView) view_item.findViewById(R.id.iv);

            itemName.setText(name[i]);
            imageView.setImageResource(icons[i]);
            return view_item;
        }
    }
}

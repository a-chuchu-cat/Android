package com.example.uridemo;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button5();
    }

    private void button5() {
        String uri = "http://www.zpan.com:8080/lujing/path.htm?id=10&name=zhangsan&old=24#zuihoude";
        Uri mUri = Uri.parse(uri);

        // 协议
        String scheme = mUri.getScheme();
        // 域名+端口号+路径+参数
        String scheme_specific_part = mUri.getSchemeSpecificPart();
        // 域名+端口号
        String authority = mUri.getAuthority();
        // fragment
        String fragment = mUri.getFragment();
        // 域名
        String host = mUri.getHost();
        // 端口号
        int port = mUri.getPort();
        // 路径
        String path = mUri.getPath();
        // 参数
        String query = mUri.getQuery();

        Log.e("zpan", "======协议===scheme ==" + scheme);
        Log.e("zpan", "======域名+端口号+路径+参数==scheme_specific_part ===" + scheme_specific_part);
        Log.e("zpan", "======域名+端口号===authority ==" + authority);
        Log.e("zpan", "======fragment===fragment ==" + fragment);
        Log.e("zpan", "======域名===host ==" + host);
        Log.e("zpan", "======端口号===port ==" + port);
        Log.e("zpan", "======路径===path ==" + path);
        Log.e("zpan", "======参数===query ==" + query);

        // 依次提取出Path的各个部分的字符串，以字符串数组的形式输出
        List<String> pathSegments = mUri.getPathSegments();
        for (String str : pathSegments) {
            Log.e("zpan", "======路径拆分====path ==" + str);
        }
        // 获得所有参数 key
        Set<String> params = mUri.getQueryParameterNames();
        for(String param: params) {
            Log.e("zpan","=====params=====" + param);
        }

        // 根据参数的 key，取出相应的值
        String id = mUri.getQueryParameter("id");
        String name = mUri.getQueryParameter("name");
        String old = mUri.getQueryParameter("old");

        Log.e("zpan", "======参数===id ==" + id);
        Log.e("zpan", "======参数===name ==" + name);
        Log.e("zpan", "======参数===old ==" + old);
    }
}
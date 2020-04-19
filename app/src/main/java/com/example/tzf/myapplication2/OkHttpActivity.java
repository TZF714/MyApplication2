package com.example.tzf.myapplication2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Request;

public class OkHttpActivity extends AppCompatActivity {
    private RecyclerView recyclrview;
    private Bean bean;
    private MyAdapter myAdapter;
    private String path = "https://www.wanandroid.com/article/top/json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        getSupportActionBar().hide();
        setOkHttp();
    }
    public void setRecyclerView() {
        recyclrview = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclrview.setLayoutManager(linearLayoutManager);
        myAdapter = new MyAdapter(OkHttpActivity.this, bean);
        recyclrview.setAdapter(myAdapter);
    }

    public void setOkHttp() {
        OkHttp.getAsync(path, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                bean = gson.fromJson(result, Bean.class);
                setRecyclerView();
            }
        });
    }
}

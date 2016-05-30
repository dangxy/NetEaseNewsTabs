package com.example.dangxueyi.neteasenewstabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dangxueyi.neteasenewstabs.adapter.ChoosedAdapter;
import com.example.dangxueyi.neteasenewstabs.adapter.UnChoosedAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView choosedRecycler;
    private RecyclerView unChoosedRecycler;

    private ArrayList<String> choosedList;
    private ArrayList<String> unChoosedList;
    private Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        mcontext=this;

        choosedRecycler.setAdapter(new ChoosedAdapter(mcontext,choosedList));
        choosedRecycler.setLayoutManager(new GridLayoutManager(mcontext,4));
        unChoosedRecycler.setAdapter(new UnChoosedAdapter(mcontext,unChoosedList));
        unChoosedRecycler.setLayoutManager(new GridLayoutManager(mcontext,4));

    }

    private void initView() {

        choosedRecycler = (RecyclerView) findViewById(R.id.rv_choosed_recycler_view);
        unChoosedRecycler = (RecyclerView) findViewById(R.id.rv_unchoosed_recycler_view);


    }

    private void initData() {

        choosedList = new ArrayList<>();
        unChoosedList = new ArrayList<>();
        choosedList.add("头条");
        choosedList.add("科技");
        choosedList.add("热点");
        choosedList.add("政务");
        choosedList.add("移动互联");
        choosedList.add("军事");
        choosedList.add("历史");
        choosedList.add("社会");
        choosedList.add("财经");
        choosedList.add("娱乐");


        unChoosedList.add("体育");
        unChoosedList.add("时尚");
        unChoosedList.add("房产");
        unChoosedList.add("论坛");
        unChoosedList.add("博客");
        unChoosedList.add("健康");
        unChoosedList.add("轻松一刻");
        unChoosedList.add("直播");
        unChoosedList.add("段子");
        unChoosedList.add("彩票");
        unChoosedList.add("直播");
        unChoosedList.add("段子");
        unChoosedList.add("彩票");
        unChoosedList.add("直播");
        unChoosedList.add("段子");
        unChoosedList.add("彩票");

    }
}

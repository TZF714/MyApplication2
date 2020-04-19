package com.example.tzf.myapplication2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView wechat,tongxunlu,find,me;
    private ViewPager vp;
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
    private FouthFragment fouthFragment;
    private List<Fragment>mFragmentList=new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        getSupportActionBar().hide();
        initViews();
        mFragmentAdapter=new FragmentAdapter(this.getSupportFragmentManager(),mFragmentList);
        vp.setOffscreenPageLimit(4);
        vp.setAdapter(mFragmentAdapter);
        vp.setCurrentItem(0);
        wechat.setTextColor(Color.parseColor("#FFAEB9"));
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
}





    private void changeTextColor(int position) {
        if (position==0){
            wechat.setTextColor(Color.parseColor("#FFAEB9"));
            tongxunlu.setTextColor(Color.parseColor("#000000"));
            find.setTextColor(Color.parseColor("#000000"));
            me.setTextColor(Color.parseColor("#000000"));
        }else if (position==1){
            wechat.setTextColor(Color.parseColor("#000000"));
            tongxunlu.setTextColor(Color.parseColor("#FFAEB9"));
            find.setTextColor(Color.parseColor("#000000"));
            me.setTextColor(Color.parseColor("#000000"));
        }else if (position==2){
            wechat.setTextColor(Color.parseColor("#000000"));
            tongxunlu.setTextColor(Color.parseColor("#000000"));
            find.setTextColor(Color.parseColor("#FFAEB9"));
            me.setTextColor(Color.parseColor("#000000"));
        }else if (position==3){
            wechat.setTextColor(Color.parseColor("#000000"));
            tongxunlu.setTextColor(Color.parseColor("#000000"));
            find.setTextColor(Color.parseColor("#000000"));
            me.setTextColor(Color.parseColor("#FFAEB9"));
        }
    }

    private void initViews() {
        wechat= (TextView) findViewById(R.id.item_wechat);
        tongxunlu= (TextView) findViewById(R.id.item_tongxunlu);
        find= (TextView) findViewById(R.id.item_find);
        me= (TextView) findViewById(R.id.item_me);
        wechat.setOnClickListener(this);
        tongxunlu.setOnClickListener(this);
        find.setOnClickListener(this);
        me.setOnClickListener(this);
        vp= (ViewPager) findViewById(R.id.mainViewPager);
        oneFragment=new OneFragment();
        twoFragment=new TwoFragment();
        threeFragment=new ThreeFragment();
        fouthFragment=new FouthFragment();
        mFragmentList.add(oneFragment);
        mFragmentList.add(twoFragment);
        mFragmentList.add(threeFragment);
        mFragmentList.add(fouthFragment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_wechat:
                vp.setCurrentItem(0,true);
                break;
            case R.id.item_tongxunlu:
                vp.setCurrentItem(1,true);
                break;
            case R.id.item_find:
                vp.setCurrentItem(2,true);
                break;
            case R.id.item_me:
                vp.setCurrentItem(3,true);
                break;
        }
    }
    public class FragmentAdapter extends FragmentPagerAdapter{
        List<Fragment>fragmentList=new ArrayList<Fragment>();
        public FragmentAdapter(FragmentManager fm,List<Fragment>fragmentList){
            super(fm);
            this.fragmentList=fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}




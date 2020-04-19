package com.example.tzf.myapplication2;

/**
 * Created by TZF on 2020/4/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/5/25.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private Context context;
    private Bean bean;

    public MyAdapter(Context context, Bean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder holder = new MyHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.texts.setText(bean.getData().get(position).getEfficacy());
    }

    @Override
    public int getItemCount() {
        return bean.getData().size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView texts;

        public MyHolder(View itemView) {
            super(itemView);
            texts = (TextView) itemView.findViewById(R.id.texts);
        }
    }
}


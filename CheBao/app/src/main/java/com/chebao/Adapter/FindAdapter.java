package com.chebao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chebao.R;
import com.chebao.bean.FindBean;
import com.chebao.net.NetService;
import com.chebao.ui.activity.WebActivity;
import com.chebao.ui.activity.WebActivityJS;
import com.chebao.ui.activity.login2register.LoginActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建日期：2018/5/11 on 21:04
 * 描述:
 * 作者:jackson Administrator
 */
public class FindAdapter extends RecyclerView.Adapter<FindAdapter.ViewHolder> {
    private List<FindBean.DataBean> datas;
    private Context context;


    public FindAdapter(List<FindBean.DataBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public FindAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_list, parent, false);
        return new FindAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FindAdapter.ViewHolder holder, int position) {
        final FindBean.DataBean dataBean=datas.get(position);
        holder.title.setText(dataBean.getActivityName()+"");
        String url= NetService.API_SERVER_Photo+dataBean.getActivityImg()+"";


        Glide.with(context).load(url)
                .error(R.mipmap.error).placeholder(R.mipmap.error).
                into(holder.imageView);
        holder.Head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, WebActivity.class);
                intent.putExtra("url",dataBean.getActivityWapSrc());
                context.startActivity(intent);
            }
        });


        //  holder.circleProgressbar.setProgressNotInUiThread(80);


    }

    @Override
    public int getItemCount() {

        return datas.size();

    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.find_item_title)
        TextView title;
        @Bind(R.id.find_item_bg)
        ImageView imageView;
        @Bind(R.id.biao_head)
        CardView Head;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
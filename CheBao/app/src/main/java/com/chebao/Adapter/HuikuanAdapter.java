package com.chebao.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chebao.R;
import com.chebao.bean.HuiKuanBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建日期：2018/5/14 on 16:57
 * 描述:
 * 作者:jackson Administrator
 */
public class HuikuanAdapter extends RecyclerView.Adapter<HuikuanAdapter.ViewHolder> {
    private List<HuiKuanBean.DataBean> datas;
    private Context context;


    public HuikuanAdapter(List<HuiKuanBean.DataBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public HuikuanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction_list, parent, false);
        return new HuikuanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HuikuanAdapter.ViewHolder holder, int position) {

        holder.money.setText("333333333");

        //  holder.circleProgressbar.setProgressNotInUiThread(80);


    }

    @Override
    public int getItemCount() {

        return datas.size();

    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.type)
        TextView type;
        @Bind(R.id.num)
        TextView num;
        @Bind(R.id.money)
        TextView money;
        @Bind(R.id.date)
        TextView date;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
package com.chebao.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chebao.R;
import com.chebao.bean.InvestmentBean;
import com.chebao.bean.RandomListBean;
import com.pvj.xlibrary.utils.DateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建日期：2018/5/31 on 11:45
 * 描述:
 * 作者:jackson Administrator
 */
public class RandomListAdapter extends RecyclerView.Adapter<RandomListAdapter.ViewHolder> {
    private List<RandomListBean.DataBean> datas;
    private Context context;


    public RandomListAdapter(List<RandomListBean.DataBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_investment_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RandomListBean.DataBean dataBean = datas.get(position);
        holder.name.setText( "滴滴宝");
        holder.date.setText(DateUtils.getStrTime3(dataBean.getCreateTime()+ ""));
        holder.num.setText(dataBean.getShmoney()+ "");
        switch (dataBean.getApplyStatus()){
            case 0:
                holder.type.setText("申请中");
                break;case 1:
                holder.type.setText("赎回成功");
                break;case 2:
                holder.type.setText("赎回失败");
                break;
        }
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
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.date)
        TextView date;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
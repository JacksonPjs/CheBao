package com.chebao.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chebao.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建日期：2018/5/7 on 16:38
 * 描述: 投资记录适配器
 * 作者:jackson Administrator
 */
public class InvestmentAdapter  extends RecyclerView.Adapter<InvestmentAdapter.ViewHolder> {
    private List<String> datas;
    private Context context;


    public InvestmentAdapter(List<String> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public InvestmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_investment_list, parent, false);
        return new InvestmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InvestmentAdapter.ViewHolder holder, int position) {


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

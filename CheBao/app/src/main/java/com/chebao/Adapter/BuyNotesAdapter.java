package com.chebao.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chebao.R;
import com.chebao.bean.InvestmentBean;
import com.pvj.xlibrary.utils.DateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建日期：2018/5/11 on 18:12
 * 描述:购买记录
 * 作者:jackson Administrator
 */
public class BuyNotesAdapter extends RecyclerView.Adapter<BuyNotesAdapter.ViewHolder> {
    private List<InvestmentBean.DataBean> datas;
    private Context context;


    public BuyNotesAdapter(List<InvestmentBean.DataBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public BuyNotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buynotes_list, parent, false);
        return new BuyNotesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BuyNotesAdapter.ViewHolder holder, int position) {
        InvestmentBean.DataBean b = datas.get(position);
        holder.num.setText(b.getInvestAmount() + "元");
        if(b.getUserName().length()==11){
            String str = b.getUserName();
            String ss = str.substring(0,str.length()-(str.substring(3)).length())+"*********"+str.substring(7);
            holder.name.setText("用户名:"+ss);
        }else{
            holder.name.setText("用户名:"+b.getUserName());
        }

        holder.date.setText(DateUtils.getStrTime2(b.getInvestTime() + ""));

        //  holder.circleProgressbar.setProgressNotInUiThread(80);


    }

    @Override
    public int getItemCount() {

        return datas.size();

    }

    static class ViewHolder extends RecyclerView.ViewHolder {



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
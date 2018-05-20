package com.chebao.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chebao.R;
import com.chebao.bean.HuiKuanBean;
import com.pvj.xlibrary.utils.DateUtils;

import java.math.BigDecimal;
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
        HuiKuanBean.DataBean b=datas.get(position);
        if (b.getRepayStatus()==1){
            holder.date.setText(DateUtils.getStrTime2(b.getRepayDate() + ""));

        }else {
            holder.date.setText(DateUtils.getStrTime2(b.getRealRepayTime() + ""));

        }
        holder.num.setText("本:"+b.getCapitalAmount()+" 息:"+b.getFeeAmount());
        BigDecimal s=b.getCapitalAmount().add(b.getFeeAmount());
        holder.type.setText(b.getBorrowTitle()+"");
        holder.money.setText(s+"");

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
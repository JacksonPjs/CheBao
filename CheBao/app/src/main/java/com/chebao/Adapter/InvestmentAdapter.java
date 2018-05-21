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
 * 创建日期：2018/5/7 on 16:38
 * 描述: 投资记录适配器
 * 作者:jackson Administrator
 */
public class InvestmentAdapter extends RecyclerView.Adapter<InvestmentAdapter.ViewHolder> {
    private List<InvestmentBean.DataBean> datas;
    private Context context;


    public InvestmentAdapter(List<InvestmentBean.DataBean> datas, Context context) {
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
        InvestmentBean.DataBean dataBean = datas.get(position);
        holder.name.setText(dataBean.getBorrowTitle() + "");
        holder.date.setText(DateUtils.getStrTime3(dataBean.getInvestTime() + ""));
        holder.num.setText(dataBean.getInvestAmount() + "");
        switch (dataBean.getBorrowStatus()) {
            case 1:
                holder.type.setText("申请中");
                break;
            case 2:
                holder.type.setText("初审通过");

                break;
            case 3:
                holder.type.setText("招标中");
                break;
            case 4:
                holder.type.setText("复审中");

                break;
            case 6:
                holder.type.setText("已还款");
                break;
            case 5:
                holder.type.setText("还款中");
                break;
            case 7:
                holder.type.setText("借款失败(初审)");

                break;
            case 8:
                holder.type.setText("复审失败");

                break;
            case 9:
                holder.type.setText("流标");

                break;
            case 10:
                holder.type.setText("复审处理中");

                break;
            case 11:
                holder.type.setText("流标或复审不通过处理中");

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

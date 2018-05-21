package com.chebao.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chebao.R;
import com.chebao.bean.CaseFlowBean;
import com.pvj.xlibrary.utils.DateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建日期：2018/5/7 on 14:35
 * 描述:
 * 作者:jackson Administrator
 */
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<CaseFlowBean.DataBean> datas;
    private Context context;


    public TransactionAdapter(List<CaseFlowBean.DataBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction_list, parent, false);
        return new TransactionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionAdapter.ViewHolder holder, int position) {
        CaseFlowBean.DataBean b = datas.get(position);
        holder.date.setText(DateUtils.getStrTime2(b.getCreateTime() + ""));
        holder.num.setText(b.getUsableAmount() + "");

        //1（收入+）2（冻结-）3.（解冻+）4.（支出-）
        holder.money.setText("" + b.getOperAmount());
        holder.type.setText(""+b.getFundMode());
        if (b.getOperType() == 1|b.getOperType()==3) {
//            holder.type.setText("充值成功");
            holder.money.setText("+" + b.getOperAmount());
            holder.money.setTextColor(context.getResources().getColor(R.color.text_org));
        } else if (b.getOperType() == 4|b.getOperType()==2) {
//            holder.type.setText("提现成功");

            holder.money.setText("-" + b.getOperAmount());
            holder.money.setTextColor(context.getResources().getColor(R.color.status4));
        }


//        if(b.getFundType()==1){
//            holder.type.setTextColor(Color.parseColor("#1Ad46e"));
//        }else  if(b.getFundType()==4){
//            holder.type.setTextColor(Utils.getColor(context,R.color.colorPrimary));
//        }else{
//            holder.type.setTextColor(Color.parseColor("#555555"));
//        }
//
//        holder.type.setText(str2[b.getFundType()-1]);

//        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
//        nf.setGroupingUsed(false);
//        holder.usebMoney.setText( nf.format(b.getUsableAmount() )+ "");

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



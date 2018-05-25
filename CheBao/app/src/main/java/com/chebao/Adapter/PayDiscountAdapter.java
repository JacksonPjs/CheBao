package com.chebao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chebao.MainActivity;
import com.chebao.R;
import com.chebao.bean.DiscountListBean;
import com.pvj.xlibrary.utils.DateUtils;

import java.util.List;

/**
 * 创建日期：2018/5/18 on 11:31
 * 描述:
 * 作者:jackson Administrator
 */
public class PayDiscountAdapter extends RecyclerView.Adapter<PayDiscountAdapter.ViewHolder> {
    private List<DiscountListBean.DisData> datas;
    private Context context;
    int type = 0;


    public PayDiscountAdapter(List<DiscountListBean.DisData> datas, Context context, onItemClickListener onItemClickListener) {
        this.datas = datas;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public PayDiscountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disount_list, parent, false);
        return new PayDiscountAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PayDiscountAdapter.ViewHolder holder, final int position) {
        DiscountListBean.DisData data = datas.get(position);
        holder.money.setText("￥" + data.getCouponAmount());
        holder.gz1.setText("满" + data.getUseMinMoney() + "元使用");
        holder.gz2.setText("出借" + data.getUseqx() + "天以上产品");
        holder.gz3.setText(DateUtils.getStrTime3(data.getExpirationDate() + "")+"前使用");
        holder.rl_left.setBackgroundResource(R.mipmap.hongbao_1);
        switch (data.getCouponType()){
            case 1:
                holder.money_type.setText("红包");
                break;
            case 2:
                holder.money_type.setText("推荐奖励");
                break;
        }
        switch (type) {
            case 1:

                holder.type.setText("立即使用");
                holder.type.setTextColor(context.getResources().getColor(R.color.red_hongbao));

                break;

            case 3:
                holder.type.setText("已使用");
                holder.type.setTextColor(context.getResources().getColor(R.color.black_home_four_small));
                holder.rl_left.setBackgroundResource(R.mipmap.hongbao_hui);

                break;

            case 5:
                holder.type.setText("已过期");
                holder.rl_left.setBackgroundResource(R.mipmap.hongbao_hui);
                holder.type.setTextColor(context.getResources().getColor(R.color.black_home_four_small));

                break;
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        if (onItemClickListener!=null){
            onItemClickListener.itemClick(position);
        }

            }
        });


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView money;
        TextView gz1, gz2, gz3, type,money_type;
        LinearLayout rl_left,item;

        //
        public ViewHolder(View view) {
            super(view);
            money = (TextView) view.findViewById(R.id.money);
            gz1 = (TextView) view.findViewById(R.id.gz1);
            gz2 = (TextView) view.findViewById(R.id.gz2);
            gz3 = (TextView) view.findViewById(R.id.gz3);
            type = (TextView) view.findViewById(R.id.type);
            money_type = (TextView) view.findViewById(R.id.money_type);
            rl_left = (LinearLayout) view.findViewById(R.id.rl_left);
            item = (LinearLayout) view.findViewById(R.id.item);


        }
    }
    onItemClickListener onItemClickListener;

    public interface  onItemClickListener{
        void itemClick(int position);
    }

}

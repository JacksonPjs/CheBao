package com.chebao.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chebao.R;
import com.chebao.bean.DiscountListBean;
import com.pvj.xlibrary.utils.DateUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */

public class DiscountListAdapter extends RecyclerView.Adapter<DiscountListAdapter.ViewHolder> {
    private List<String> datas;
    private Context context;
    int type = 0;


    public DiscountListAdapter(List<String> datas, Context context, int i) {
        this.datas = datas;
        this.context = context;
        type = i;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disount_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        DiscountListBean.DisData data = datas.get(position);
//        holder.countdownTextView.setText("有效期至" +
//                DateUtils.getStrTime3(datas.get(position).getExpirationDate() + ""));


//
//        if (type == 1) {
//            holder.countdownTextView.setTextColor(context.getResources().getColor(R.color.season));
//            switch (data.getCouponType()) {
//                case 0:
//                    holder.img.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_discount_jx));
//                    holder.gz1.setText(context.getResources().getString(R.string.discount_jx_item1));
//                    holder.gz2.setText(context.getResources().getString(R.string.discount_jx_item2));
//                    holder.gz3.setVisibility(View.GONE);
//                    holder.money.setText(data.getCouponAmount() + "%");
//                    break;
//                case 1:
//                    holder.money.setText("￥" + data.getCouponAmount() + "");
//                    holder.img.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_discount_tiyan));
//                    holder.gz1.setText(context.getResources().getString(R.string.discount_jx_item1));
//                    holder.gz2.setText(context.getResources().getString(R.string.discount_jx_item2));
//                    holder.gz3.setVisibility(View.GONE);
//                    break;
//                case 2:
//                    holder.money.setText("￥" + data.getCouponAmount() + "");
//                    holder.img.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_discount_hb));
//                    holder.gz1.setText(context.getResources().getString(R.string.discount_dj_item1));
//                    holder.gz2.setText(context.getResources().getString(R.string.discount_dj_item2));
//                    holder.gz3.setText(context.getResources().getString(R.string.discount_dj_item3));
//                    holder.gz3.setVisibility(View.VISIBLE);
//                    break;
//                case 3:
//                    holder.money.setText("￥" + data.getCouponAmount() + "");
//                    holder.img.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_discount_tx));
//                    holder.gz1.setText(context.getResources().getString(R.string.discount_dj_item1));
//                    holder.gz2.setText(context.getResources().getString(R.string.discount_dj_item2));
//                    holder.gz3.setText(context.getResources().getString(R.string.discount_dj_item3));
//                    holder.gz3.setVisibility(View.VISIBLE);
//
//                    break;
//                case 4:
//                    holder.money.setText("￥" + data.getCouponAmount() + "");
//                    holder.img.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_discount_dj));
//                    holder.gz1.setText(context.getResources().getString(R.string.discount_dj_item1));
//                    holder.gz2.setText(context.getResources().getString(R.string.discount_dj_item2));
//                    holder.gz3.setText(context.getResources().getString(R.string.discount_dj_item3));
//                    holder.gz3.setVisibility(View.VISIBLE);
//                    break;
//            }
//
//        } else {
//            holder.countdownTextView.setTextColor(context.getResources().getColor(R.color.text_huise));
//            switch (data.getCouponType()) {
//                case 0:
//
//                    holder.img.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_discount_jx_no));
//                    holder.gz1.setText(context.getResources().getString(R.string.discount_jx_item1));
//                    holder.gz2.setText(context.getResources().getString(R.string.discount_jx_item2));
//                    holder.gz3.setVisibility(View.GONE);
//                    holder.money.setText(data.getCouponAmount() + "%");
//                    break;
//                case 1:
//                    holder.money.setText("￥" + data.getCouponAmount() + "");
//                    holder.img.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_discount_tiyan_no));
//                    holder.gz1.setText(context.getResources().getString(R.string.discount_jx_item1));
//                    holder.gz2.setText(context.getResources().getString(R.string.discount_jx_item2));
//                    holder.gz3.setVisibility(View.GONE);
//                    break;
//                case 2:
//                    holder.money.setText("￥" + data.getCouponAmount() + "");
//                    holder.img.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_discount_hb_no));
//                    holder.gz1.setText(context.getResources().getString(R.string.discount_dj_item1));
//                    holder.gz2.setText(context.getResources().getString(R.string.discount_dj_item2));
//                    holder.gz3.setText(context.getResources().getString(R.string.discount_dj_item3));
//                    holder.gz3.setVisibility(View.VISIBLE);
//                    break;
//                case 3:
//                    holder.money.setText("￥" + data.getCouponAmount() + "");
//                    holder.img.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_discount_tx_no));
//                    holder.gz1.setText(context.getResources().getString(R.string.discount_dj_item1));
//                    holder.gz2.setText(context.getResources().getString(R.string.discount_dj_item2));
//                    holder.gz3.setText(context.getResources().getString(R.string.discount_dj_item3));
//                    holder.gz3.setVisibility(View.VISIBLE);
//
//                    break;
//                case 4:
//                    holder.money.setText("￥" + data.getCouponAmount() + "");
//                    holder.img.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_discount_dj_no));
//                    holder.gz1.setText(context.getResources().getString(R.string.discount_dj_item1));
//                    holder.gz2.setText(context.getResources().getString(R.string.discount_dj_item2));
//                    holder.gz3.setText(context.getResources().getString(R.string.discount_dj_item3));
//                    holder.gz3.setVisibility(View.VISIBLE);
//                    break;
//            }


//        }


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView money;
        TextView gz1, gz2, gz3;
//
        public ViewHolder(View view) {
            super(view);
            money = (TextView) view.findViewById(R.id.money);
            gz1 = (TextView) view.findViewById(R.id.gz1);
            gz2 = (TextView) view.findViewById(R.id.gz2);
            gz3 = (TextView) view.findViewById(R.id.gz3);


        }
    }

}


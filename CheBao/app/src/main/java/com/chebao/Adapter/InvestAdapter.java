package com.chebao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chebao.App.Constant;
import com.chebao.R;
import com.chebao.bean.BiaoBean;
import com.chebao.ui.activity.invest.DetailsRegularActivity;
import com.chebao.utils.T1changerString;
import com.chebao.widget.GoodProgressView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InvestAdapter extends RecyclerView.Adapter<InvestAdapter.ViewHolder> {
    private List<BiaoBean.DataBean> datas;
    private Context context;
    private int flag = -1;

    public InvestAdapter(List<BiaoBean.DataBean> datas, Context context, int investing) {
        this.datas = datas;
        this.context = context;
        flag = investing;
    }


//    public  void upData(List<BiaoBean.DataBean> datas){
//        this.datas=datas;
//        notifyDataSetChanged();
//    }

    @Override
    public InvestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_list, parent, false);
        return new InvestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InvestAdapter.ViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final BiaoBean.DataBean d = datas.get(position);

        holder.Head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件
                Intent intent = new Intent(context, DetailsRegularActivity.class);
                intent.putExtra("id", d.getId() + "");
                context.startActivity(intent);
            }
        });

//        switch (flag) {
//            case Constant.INVESTING:
        holder.xianshi.setBackgroundResource(R.drawable.rect_rounded_left_right_arc);
        holder.progressBar.setProgressValue((int) (d.getProgress() * 100));

        holder.tuijiandate.setText("出借期限:" + T1changerString.t2chager(d.getDeadline(), d.getDeadlineType()));
        String str1 = "出借期限:";
        String str2 = T1changerString.t2chager(d.getDeadline(), d.getDeadlineType());

        SpannableStringBuilder builder = new SpannableStringBuilder(str1 + str2);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#fb500a")),
                str1.length(), (str1 + str2).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        holder.browamount.setText(context.getString(R.string.borrowamount) +":"+ T1changerString.t3chager(d.getBorrowAmount()));
        holder.shengyu.setText("剩余金额:" + T1changerString.t3chager(d.getBorrowAmount() - d.getHasBorrowAmount()));
        holder.tuijiandate.setText(builder);
        holder.title.setText("" + d.getBorrowTitle());
//        holder.tuijiandate.setText("出借期限:" + T1changerString.t2chager(d.getDeadline(), d.getDeadlineType()));
        if (d.getBorrowType() == 5) {
            holder.tuijianlilv.setText((d.getAnnualRate() - 3) + "%+3%");
            holder.xianshi.setText("限时加息3%");
            holder.titletype.setText("新手标");
        } else {
            holder.xianshi.setText("限时加息1%");
            holder.titletype.setText("车车宝");

            holder.tuijianlilv.setText((d.getAnnualRate() - 1) + "%+1%");

        }

        holder.tuijianfangshi.setText( T1changerString.t4chager(d.getRepayType()));

        if (flag == Constant.INVESTING) {
            holder.invest_item_rl.setBackground(context.getResources().getDrawable(R.mipmap.bg_invest));
        } else {
            holder.invest_item_rl.setBackground(context.getResources().getDrawable(R.drawable.elevation_home_bg));

//            holder.invest_item_rl.setBackground(context.getResources().getDrawable(R.mipmap.sellout_bk));

        }

//        holder.title.setText("春眠不觉晓");


        //  holder.circleProgressbar.setProgressNotInUiThread(80);


    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.invest_head)
        View Head;
        @Bind(R.id.xinshou)
        TextView title;
        @Bind(R.id.xianshi)
        TextView xianshi;
        @Bind(R.id.tuijian_date)
        TextView tuijiandate;
        @Bind(R.id.tuijian_fangshi)
        TextView tuijianfangshi;
        @Bind(R.id.tuijian_lilv)
        TextView tuijianlilv;
        @Bind(R.id.browamount)
        TextView browamount;
        @Bind(R.id.shengyu)
        TextView shengyu;
        @Bind(R.id.titletype)
        TextView titletype;
        @Bind(R.id.progressBar)
        GoodProgressView progressBar;
        @Bind(R.id.invest_item_rl)
        RelativeLayout invest_item_rl;
//        @Bind(R.id.find_item_bg)
//        ImageView imageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}

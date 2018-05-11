package com.chebao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chebao.App.Constant;
import com.chebao.R;
import com.chebao.bean.BiaoBean;
import com.chebao.ui.activity.DetailsRegularActivity;
import com.chebao.utils.T1changerString;
import com.chebao.widget.GoodProgressView;
import com.chebao.widget.ProgressSeek;
import com.pvj.xlibrary.loadinglayout.Utils;

import java.util.List;
import java.util.Random;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invest_list, parent, false);
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
                Intent intent = new Intent(context, DetailsRegularActivity.class);
                intent.putExtra("id",d.getId()+"");
                context.startActivity(intent);
            }
        });

//        switch (flag) {
//            case Constant.INVESTING:
        holder.xianshi.setBackgroundResource(R.drawable.rect_rounded_left_right_arc);
        holder.progressBar.setProgressValue(d.getProgress()*100);
//        holder.progressBar.setColors(randomColors());
//
//                break;

//            case Constant.INVEST_SELL_OUT:
//                holder.progressBar.setBP(Constant.INVEST_SELL_OUT);
//                holder.progressBar.init(100);
//                holder.xianshi.setBackgroundResource(R.drawable.rect_rounded_left_black);
//                break;
//        }
        holder.title.setText("" + d.getBorrowTitle());
        holder.tuijiandate.setText("投资期限:" + T1changerString.t2chager(d.getDeadline(), d.getDeadlineType()));
        holder.tuijianlilv.setText((d.getAnnualRate() - 2) + "%+2%");
        holder.tuijianfangshi.setText("收益方式:" + T1changerString.t4chager(d.getRepayType()));

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
        @Bind(R.id.progressBar)
        GoodProgressView progressBar;
//        @Bind(R.id.find_item_bg)
//        ImageView imageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}

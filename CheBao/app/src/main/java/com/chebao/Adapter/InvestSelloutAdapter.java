package com.chebao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建日期：2018/5/11 on 14:41
 * 描述:
 * 作者:jackson Administrator
 */
public class InvestSelloutAdapter extends RecyclerView.Adapter<InvestSelloutAdapter.ViewHolder> {
    private List<BiaoBean.DataBean> datas;
    private Context context;
    private int flag = -1;

    public InvestSelloutAdapter(List<BiaoBean.DataBean> datas, Context context, int investing) {
        this.datas = datas;
        this.context = context;
        flag = investing;
    }

//    public  void upData(List<BiaoBean.DataBean> datas){
//        this.datas=datas;
//        notifyDataSetChanged();
//    }

    @Override
    public InvestSelloutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invest_list, parent, false);
        return new InvestSelloutAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InvestSelloutAdapter.ViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        holder.Head.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, DetailsRegularActivity.class);
//                context.startActivity(intent);
//            }
//        });
        BiaoBean.DataBean d = datas.get(position);


        holder.xianshi.setBackgroundResource(R.drawable.rect_rounded_left_black);
        holder.title.setText("" + d.getBorrowTitle());
        holder.tuijiandate.setText("投资期限:" + T1changerString.t2chager(d.getDeadline(), d.getDeadlineType()));
        if (d.getBorrowType()==5){
            holder.tuijianlilv.setText((d.getAnnualRate() - 3) + "%+3%");
            holder.xianshi.setText("限时加息3%");

        }else {
            holder.tuijianlilv.setText((d.getAnnualRate() - 1) + "%+1%");
            holder.xianshi.setText("限时加息1%");

        }
        holder.tuijianfangshi.setText("收益方式:" + T1changerString.t4chager(d.getRepayType()));
        holder.progressBar.setColors(randomColors());
        holder.progressBar.setPointColors(Utils.getColor(context,R.color.bg_huise));
        holder.progressBar.setProgressValue(100);

        if (flag==Constant.INVESTING){
            holder.invest_item_rl.setBackground(context.getResources().getDrawable(R.mipmap.sell_bg));
        }else {
            holder.invest_item_rl.setBackground(context.getResources().getDrawable(R.mipmap.sellout_bk));

        }
        //  holder.circleProgressbar.setProgressNotInUiThread(80);


    }
    private int[] randomColors() {
        int[] colors=new int[2];
        colors[0]= Utils.getColor(context,R.color.bg_huise);
        colors[1]= Utils.getColor(context,R.color.bg_huise);
//        Random random = new Random();
//        int r,g,b;
//        for(int i=0;i<2;i++){
//            r=random.nextInt(256);
//            g=random.nextInt(256);
//            b=random.nextInt(256);
//            colors[i]= Color.argb(255, r, g, b);
//            Log.i("customView","log: colors["+i+"]="+Integer.toHexString(colors[i]));
//        }

        return colors;
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
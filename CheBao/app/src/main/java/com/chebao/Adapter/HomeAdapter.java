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

import com.chebao.R;
import com.chebao.bean.OneBean;
import com.chebao.ui.activity.invest.DetailsRegularActivity;
import com.chebao.utils.T1changerString;
import com.chebao.widget.GoodProgressView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建日期：2018/6/22 on 16:38
 * 描述:
 * 作者:jackson Administrator
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OneBean.Data2Bean> datas;
    private Context context;
    private int flag = -1;
    private final int TYPE_HEADER = 0xa00;
    View VIEW_HEADER;

    //    public HomeAdapter(List<BiaoBean.DataBean> datas, Context context, int investing) {
//        this.datas = datas;
//        this.context = context;
//        flag = investing;
//    }
    public HomeAdapter(List<OneBean.Data2Bean> biaoBeenList, Context context) {
        this.datas = biaoBeenList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        int count=datas.size();
        if (VIEW_HEADER != null) {
            count++;
        }

        return count;
    }

    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            VIEW_HEADER = headerView;
            notifyItemInserted(0);
        }

    }


    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else {
            return super.getItemViewType(position);

        }


    }

    private boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }
    private boolean haveHeaderView() {
        return VIEW_HEADER != null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType==TYPE_HEADER){
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home, parent, false);
            view=VIEW_HEADER;
            return new HeaderVh(view);

        }else {
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_list, parent, false);
            return new Holder(view);

        }
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder ViewHolder, int position) {
        ViewGroup.LayoutParams layoutParams = ViewHolder.itemView.getLayoutParams();
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;

        if (!(getItemViewType(position)==TYPE_HEADER)) {
            if (haveHeaderView()){
                position--;
            }
            Holder holder= (Holder) ViewHolder;

            final OneBean.Data2Bean d = datas.get(position);


            holder.Head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailsRegularActivity.class);
                    intent.putExtra("id", d.getId() + "");
                    context.startActivity(intent);
                }
            });

//        switch (flag) {
//            case Constant.INVESTING:
            holder.xianshi.setBackgroundResource(R.drawable.rect_rounded_left_right_arc);
            holder.progressBar.setProgressValue((int) (d.getProgress() * 100));

            holder.browamount.setText(context.getString(R.string.borrowamount) + ":" + T1changerString.t3chager(d.getBorrowAmount()));
            holder.shengyu.setText("剩余金额:" + T1changerString.t3chager(d.getBorrowAmount() - d.getHasBorrowAmount()));
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
            holder.tuijiandate.setText("出借期限:" + T1changerString.t2chager(d.getDeadline(), d.getDeadlineType()));
            String str1 = "出借期限:";
            String str2 = T1changerString.t2chager(d.getDeadline(), d.getDeadlineType());

            SpannableStringBuilder builder = new SpannableStringBuilder(str1 + str2);
            builder.setSpan(new ForegroundColorSpan(Color.parseColor("#fb500a")),
                    str1.length(), (str1 + str2).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//                    builder.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffa200")),
//                            (str1 + str2 + str3).length(), (str1 + str2 + str3 ).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

            holder.tuijiandate.setText(builder);
            if (d.getBorrowType() == 5) {
                holder.tuijianlilv.setText((d.getAnnualRate() - 3) + "%+3%");
                holder.xianshi.setText("限时加息3%");
                holder.titletype.setText("新手标");
            } else {
                holder.xianshi.setText("限时加息1%");
                holder.tuijianlilv.setText((d.getAnnualRate() - 1) + "%+1%");
                holder.titletype.setText("车车宝");
            }

            holder.tuijianfangshi.setText(T1changerString.t4chager(d.getRepayType()));

            holder.invest_item_rl.setBackground(context.getResources().getDrawable(R.mipmap.bg_invest));


//        holder.title.setText("春眠不觉晓");


            //  holder.circleProgressbar.setProgressNotInUiThread(80);
        }

    }




    static class Holder extends RecyclerView.ViewHolder {

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
        @Bind(R.id.titletype)
        TextView titletype;
        @Bind(R.id.browamount)
        TextView browamount;
        @Bind(R.id.shengyu)
        TextView shengyu;
        @Bind(R.id.progressBar)
        GoodProgressView progressBar;
        @Bind(R.id.invest_item_rl)
        RelativeLayout invest_item_rl;
//        @Bind(R.id.find_item_bg)
//        ImageView imageView;

        Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class HeaderVh extends RecyclerView.ViewHolder{

        public HeaderVh(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}


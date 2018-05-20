package com.chebao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chebao.R;
import com.chebao.bean.AnnouncementBean;
import com.chebao.bean.ConsultationBean;
import com.chebao.ui.activity.AnndatilsActivity;
import com.pvj.xlibrary.utils.DateUtils;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建日期：2018/5/17 on 18:27
 * 描述:
 * 作者:jackson Administrator
 */
public class ConsultationAdapter extends RecyclerView.Adapter<ConsultationAdapter.ViewHolder> {


    private List<ConsultationBean.DataBean> datas;
    private Context context;

    public ConsultationAdapter(List<ConsultationBean.DataBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public ConsultationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_announ_list, parent, false);
        return new ConsultationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConsultationAdapter.ViewHolder holder, int position) {
        final     ConsultationBean.DataBean b = datas.get(position);

        holder.biaoti.setText(b.getTitle());

        //'公告类型（0、通知类公告 1、活动公告 2、产品公告）',
//        if (b.getNoticetype()==0){
//            holder.type.setText("通知类公告");
//        }else if(b.getNoticetype()==1){
//            holder.type.setText("活动公告");
//        }else if(b.getNoticetype()==2){
//            holder.type.setText("产品公告");
//        }


        holder.dsadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnndatilsActivity.class);
                intent.putExtra("id", (Serializable) b.getId());
                intent.putExtra("type", 1);
                intent.putExtra("data", (Serializable) b);
                context.startActivity(intent);
            }
        });

        holder.time.setText(DateUtils.getStrTime2(b.getCreateTime() + ""));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.biaoti)
        TextView biaoti;
        @Bind(R.id.dsadas)
        View dsadas;
        @Bind(R.id.time)
        TextView time;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
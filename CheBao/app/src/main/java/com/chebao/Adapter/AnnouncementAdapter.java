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
import com.chebao.net.NetService;
import com.chebao.ui.activity.web.WebNoTitileActivity;
import com.pvj.xlibrary.utils.DateUtils;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/26.
 */

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {


    private List<AnnouncementBean.DataBean> datas;
    private Context context;

    public AnnouncementAdapter(List<AnnouncementBean.DataBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_announ_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    final     AnnouncementBean.DataBean b = datas.get(position);

        holder.biaoti.setText(b.getNoticeTitle());

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
//                Intent intent = new Intent(context, AnndatilsActivity.class);
//                intent.putExtra("id", (Serializable) b.getId());
//                intent.putExtra("type", 0);
//                intent.putExtra("data",(Serializable)  b);
                Intent intent = new Intent(context, WebNoTitileActivity.class);
                intent.putExtra("url", NetService.API_SERVER_Url+"wechat/noticemsg.html?id="+(Serializable) b.getId());


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

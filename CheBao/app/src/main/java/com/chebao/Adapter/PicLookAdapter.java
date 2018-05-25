package com.chebao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chebao.R;
import com.chebao.bean.ProductDetialBean;
import com.chebao.net.NetService;
import com.chebao.ui.activity.PicturesLookNetActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建日期：2018/5/25 on 15:58
 * 描述:
 * 作者:jackson Administrator
 */
public class PicLookAdapter extends RecyclerView.Adapter<PicLookAdapter.ViewHolder> {
    private List<ProductDetialBean.DataBean> datas;
    private Context context;


    public PicLookAdapter(List<ProductDetialBean.DataBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public PicLookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gridview, parent, false);
        return new PicLookAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final    ProductDetialBean.DataBean d   = datas.get(position);





        Glide.with(context).load(NetService.API_SERVER_Photo+d.getAttrPath())
                .error(R.mipmap.bg_defult)
                .placeholder(R.mipmap.bg_defult)
                .into(holder.imgGv);

        holder.imgGv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PicturesLookNetActivity.class);
                intent.putExtra("url",NetService.API_SERVER_Photo+d.getAttrPath());
                context.startActivity(intent);
            }
        });

        holder.tvGv.setText(d.getAttrName());

    }


    @Override
    public int getItemCount() {

        return datas.size();

    }

    static class ViewHolder extends RecyclerView.ViewHolder {


          @Bind(R.id.img_gv)
        ImageView imgGv;
           @Bind(R.id.tv_gv)
        TextView tvGv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

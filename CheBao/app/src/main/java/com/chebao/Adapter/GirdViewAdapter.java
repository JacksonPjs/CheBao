package com.chebao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chebao.R;
import com.chebao.bean.ProductDetialBean;
import com.chebao.net.NetService;
import com.chebao.ui.activity.invest.PicturesLookNetActivity;


import java.util.List;

/**
 * Created by pvj on 2016/12/30.
 */

public class GirdViewAdapter extends BaseAdapter {
    List<ProductDetialBean.DataBean> datas;
    Context context;

    public GirdViewAdapter(List<ProductDetialBean.DataBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public ProductDetialBean.DataBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
     final    ProductDetialBean.DataBean d   = datas.get(position);

        ViewHolder holder =null ;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview, null);

            holder =   new ViewHolder();
            holder.imgGv = (ImageView) convertView.findViewById(R.id.img_gv);
            holder.tvGv = (TextView) convertView.findViewById(R.id.tv_gv);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }



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

        return convertView;
    }

     class ViewHolder {
      //  @Bind(R.id.img_gv)
        ImageView imgGv;
     //   @Bind(R.id.tv_gv)
        TextView tvGv;


    }
}

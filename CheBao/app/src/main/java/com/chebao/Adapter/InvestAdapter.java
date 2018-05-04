package com.chebao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chebao.R;
import com.chebao.ui.activity.DetailsActivity;
import com.chebao.ui.activity.DetailsProductActivity;
import com.chebao.ui.activity.DetailsRegularActivity;

import java.util.List;
import java.util.Queue;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InvestAdapter extends RecyclerView.Adapter<InvestAdapter.ViewHolder> {
    private List<String> datas;
    private Context context;


    public InvestAdapter(List<String> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public InvestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invest_list, parent, false);
        return new InvestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InvestAdapter.ViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        holder.Head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsRegularActivity.class);
                context.startActivity(intent);
            }
        });

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
//        @Bind(R.id.find_item_title)
//        TextView title;
//        @Bind(R.id.find_item_bg)
//        ImageView imageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}

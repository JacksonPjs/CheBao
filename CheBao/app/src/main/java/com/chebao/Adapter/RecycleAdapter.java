package com.chebao.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chebao.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建日期：2018/6/25 on 10:03
 * 描述:
 * 作者:jackson Administrator
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    private List<String> mDatas;
    private List<String> explain;
    private List<Integer> drawbles;
    private Context mContext;
    private LayoutInflater inflater;
    private OnItemClickLitener mOnItemClickListener = null;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickListener = mOnItemClickLitener;
    }


    public RecycleAdapter(Context context, List<String> datas, List<String> explain, List<Integer> drawbles) {
        this.mContext = context;
        this.mDatas = datas;
        this.explain = explain;
        this.drawbles = drawbles;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {

        return mDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        holder.tv.setText(mDatas.get(position));
        holder.tv_explain.setText(explain.get(position));
        holder.imageView.setImageResource(drawbles.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取数据
                    mOnItemClickListener.onItemClick(v, position);
                }
            }
        });
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycle_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView tv;
        @Bind(R.id.tv_explain)
        TextView tv_explain;
        @Bind(R.id.image)
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

}
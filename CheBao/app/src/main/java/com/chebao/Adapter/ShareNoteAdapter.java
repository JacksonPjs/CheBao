package com.chebao.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chebao.R;
import com.chebao.bean.ShareNoteBean;
import com.pvj.xlibrary.utils.DateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建日期：2018/6/28 on 10:19
 * 描述:
 * 作者:jackson Administrator
 */
public class ShareNoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ShareNoteBean.DataBean> datas;
    private Context context;
    private int flag = -1;
    private static final int TYPE_NORMAL = 0x11;
    private static final int TYPE_BOTTOM = 0x12;
    private static final int TYPE_HEADER = 0x13;
    View VIEW_HEADER;
    View VIEW_FOOTER;


    public ShareNoteAdapter(List<ShareNoteBean.DataBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        int count = datas.size();
        if (VIEW_HEADER != null) {
            count++;
        }
        if (VIEW_FOOTER != null) {
            count++;
        }

        return count;
    }

//    public void AddHeaderItem(List<String> items) {
//        datas.addAll(0, items);
//        notifyDataSetChanged();
//    }
//
//    public void AddFooterItem(List<String> items) {
//        datas.addAll(items);
//        notifyDataSetChanged();
//    }

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

    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_FOOTER = footerView;
            notifyItemInserted(getItemCount() - 1);
        }
    }

    public boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    private boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else if (isFooterView(position)) {
            return TYPE_BOTTOM;
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
        if (viewType == TYPE_HEADER) {
            view = VIEW_HEADER;
            return new HeaderVh(view);

        } else if (viewType == TYPE_BOTTOM) {
            view = VIEW_FOOTER;
            return new HeaderVh(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share_note, parent, false);
            return new Holder(view);

        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder ViewHolder, int position) {
        ViewGroup.LayoutParams layoutParams = ViewHolder.itemView.getLayoutParams();
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;

        if (!(getItemViewType(position) == TYPE_HEADER)) {
            if (!isHeaderView(position) && !isFooterView(position)) {
                if (haveHeaderView()) position--;
                Holder holder = (Holder) ViewHolder;
//                holder.phoneNuber.setText(datas.get(position));

                final ShareNoteBean.DataBean d = datas.get(position);
                holder.phoneNuber.setText(d.getCellPhone());
                holder.date.setText(""+ DateUtils.getStrTime3(d.getCreateTime()+""));
                holder.money.setText(d.getAwardAmount()+"");


//            holder.Head.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, DetailsRegularActivity.class);
//                    intent.putExtra("id", d.getId() + "");
//                    context.startActivity(intent);
//                }
//            });

            }
        }


    }


    static class Holder extends RecyclerView.ViewHolder {
        @Bind(R.id.phonenumber)
        TextView phoneNuber;
        @Bind(R.id.money)
        TextView money;
        @Bind(R.id.date)
        TextView date;


        Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class HeaderVh extends RecyclerView.ViewHolder {

        public HeaderVh(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}

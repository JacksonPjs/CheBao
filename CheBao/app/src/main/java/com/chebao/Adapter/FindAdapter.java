package com.chebao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.chebao.R;
import com.chebao.bean.FindBean;
import com.chebao.bean.LoginBean;
import com.chebao.net.NetService;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.web.WebNoTitileActivity;
import com.chebao.ui.activity.web.WebNotitleHtmlActivity;
import com.chebao.ui.activity.login2register.LoginActivity;
import com.chebao.utils.SharedPreferencesUtils;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

import static com.chebao.utils.edncodeUtils.getCookie;

/**
 * 创建日期：2018/5/11 on 21:04
 * 描述:
 * 作者:jackson Administrator
 */
public class FindAdapter extends RecyclerView.Adapter<FindAdapter.ViewHolder> {
    private List<FindBean.DataBean> datas;
    private Context context;
    private int type = 0;

    public FindAdapter(List<FindBean.DataBean> datas, Context context, int i) {
        this.datas = datas;
        this.context = context;
        type = i;
    }

    @Override
    public FindAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_list, parent, false);
        return new FindAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final FindAdapter.ViewHolder holder, int position) {
        final FindBean.DataBean dataBean = datas.get(position);
        holder.title.setText(dataBean.getActivityName() + "");
        String url = NetService.API_SERVER_Photo + dataBean.getActivityImg() + "";
        if (type == 0) {
            holder.title.setBackgroundResource(R.drawable.shape_left_round);
        } else {
            holder.title.setBackgroundResource(R.drawable.shape_find_view);

        }


        Glide.with(context).
                load(url)
//                .error(R.mipmap.error)
//                .placeholder(R.mipmap.error)
                .crossFade()
                .into(new ViewTarget<View, GlideDrawable>(holder.Bg) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            holder.Bg.setBackground(resource);
                    }
                });

        Glide.with(context).
                load(url)
//                .error(R.mipmap.error)
//                .placeholder(R.mipmap.error)
                .crossFade()
                .into(holder.imageView);
        holder.Head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (type == 0) {
                    String url = dataBean.getActivitySrc();

                    if (url != null && url.contains("wechat/duanwuActive.html")) {
                        netLogin(url);


//
                    } else {
                        intent = new Intent(context, WebNoTitileActivity.class);
                        intent.putExtra("url", dataBean.getActivityWapSrc());
                        context.startActivity(intent);
                    }

                } else {
                    T.ShowToastForLong(context, "活动已结束");
                }

            }
        });


    }

    @Override
    public int getItemCount() {

        return datas.size();

    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.find_item_title)
        TextView title;
        @Bind(R.id.find_item_bg)
        ImageView imageView;
        @Bind(R.id.biao_head)
        CardView Head; @Bind(R.id.find_bg)
        View Bg;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void netLogin(final String url) {
        String name = SharedPreferencesUtils.getUserName(context);
        String psw = SharedPreferencesUtils.getPassword(context);


        NetWorks.login(name, psw, new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        Logger.json(e.toString());
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean.getState().getStatus() == 0) {
                            SharedPreferencesUtils.savaUser(context, loginBean, SharedPreferencesUtils.getPassword(context));
                            OkHttpUtils
                                    .post()
                                    .url(url)
                                    .addHeader("Cookie", getCookie())
                                    .addParams("", "")
                                    .build()
                                    .execute(new com.zhy.http.okhttp.callback.Callback<String>() {

                                        @Override
                                        public String parseNetworkResponse(com.squareup.okhttp.Response response) throws IOException {
                                            return response.body().string();
                                        }

                                        @Override
                                        public void onError(Request request, Exception e) {
                                            String s = e.toString();

                                        }

                                        @Override
                                        public void onResponse(String o) {
                                            Logger.json(o);
                                            Intent intent = new Intent(context, WebNotitleHtmlActivity.class);
                                            intent.putExtra("url", "" + o);
                                            context.startActivity(intent);
//

                                        }


                                    });

                        } else {
                            SharedPreferencesUtils.setParam(context, "islogin", false);
                            Intent intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                        }
                    }
                }
        );
    }
}

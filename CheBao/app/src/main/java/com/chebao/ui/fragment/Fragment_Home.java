package com.chebao.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.chebao.R;
import com.chebao.ui.activity.WebActivity;
import com.chebao.bean.AnnouncementBean;
import com.chebao.bean.OneBean;
import com.chebao.net.NetService;
import com.chebao.widget.ProgressSeek;
import com.pvj.xlibrary.banner.Banner;
import com.pvj.xlibrary.banner.BannerIndicator;
import com.pvj.xlibrary.loadinglayout.Utils;
import com.pvj.xlibrary.log.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/21.
 * 首页
 */

public class Fragment_Home extends BaseFragment {


    @Bind(R.id.textview_auto_roll)
    TextSwitcher textSwitcher;
    @Bind(R.id.main_banner)
    Banner banner;
    @Bind(R.id.indicator)
    BannerIndicator bannerIndicator;
    @Bind(R.id.gonggao)
    View gonggao;
    @Bind(R.id.progressBar)
    ProgressSeek progressSeek;
    private BitHandler bitHandler;

    List<OneBean.BannersBean> drawables;
    // 公告列表
    List<OneBean.Data4Bean> data4Beens;

    private int index = 0;

    //公告handler
    class BitHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (textSwitcher != null) {
                textSwitcher.setText(data4Beens.get(index).getNoticeTitle());
                index++;
                if (index == data4Beens.size()) {
                    index = 0;
                }
            }

        }
    }


    /**
     * find view from layout and set listener
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,
                null);
        ButterKnife.bind(this, rootView);


        return rootView;
    }

    @Override
    public void initData() {
        progressSeek.init(50);
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getContext());
                textView.setSingleLine();
                //  textView.setTextSize(Utils.sp2px(getContext(),12));
                textView.setTextSize(12);
                textView.setTextColor(Color.parseColor("#333231"));
                textView.setEllipsize(TextUtils.TruncateAt.END);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                );
                lp.gravity = Gravity.CENTER_VERTICAL;
                textView.setLayoutParams(lp);
                return textView;
            }
        });

        bitHandler = new BitHandler();


        //----------------------banner start------------------------------
        drawables = new ArrayList<>();
        data4Beens = new ArrayList<>();
//        drawables.add("1");
//        drawables.add("2");
//        drawables.add("3");
//        WindowManager wm = (WindowManager) getContext()
//                .getSystemService(Context.WINDOW_SERVICE);
//        int width = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
//        ViewGroup.LayoutParams params = banner.getLayoutParams();
//        params.width=width;
//        params.height=width*2/5;
//        banner.setLayoutParams(params);

//        banner.setInterval(5000);
//        banner.setPageChangeDuration(500);

        List imageViewList = new ArrayList<>();
        imageViewList.add(this.getResources().getDrawable(R.mipmap.banner));
        imageViewList.add(this.getResources().getDrawable(R.mipmap.banner_discount));
        banner.setBannerDataInit(new Banner.BannerDataInit() {
            @Override
            public ImageView initImageView() {
                return (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.imageview, null);
            }

            @Override
            public void initImgData(ImageView imageView, Object imgPath) {
                imageView.setImageDrawable((Drawable) imgPath);
//                Logger.d("initImgData" + NetService.API_SERVER_Url + ((OneBean.BannersBean) imgPath).getImgPath());
//                Glide.with(Fragment_Home.this)
//                        .load(NetService.API_SERVER_Url + ((OneBean.BannersBean) imgPath).getImgPath())
//                        .error(R.mipmap.banner)
//                        .into(imageView);
            }
        });


        bannerIndicator.setIndicatorSource(
                ContextCompat.getDrawable(getActivity(), R.mipmap.zuobiao_dangqian_banner),//select
                ContextCompat.getDrawable(getActivity(), R.mipmap.baisezuobiao_banner),//unselect
                Utils.dp2px(getActivity(), 14)//widthAndHeight
        );
        banner.attachIndicator(bannerIndicator);
        banner.setDataSource(imageViewList);


        banner.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", drawables.get(position).getUrl());
                intent.putExtra("title", drawables.get(position).getBannerName());
                startActivity(intent);

                //    T.ShowToastForShort(getActivity(), "第" + position + "图片被点击了");
            }
        });

    }

    /**
     * init data
     */
    @Override
    public void fillDate() {

    }

    @Override
    public void requestData() {

    }


    private class myThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (index < data4Beens.size()) {
                try {
                    synchronized (this) {
                        bitHandler.sendEmptyMessage(0);
                        this.sleep(2000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onPause() {
        banner.pauseScroll();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.resumeScroll();
    }


    @OnClick({R.id.gonggao})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.gonggao:

                if (data4Beens == null) {
                    return;
                }


                int i = index - 1;
                if (i < 0) {
                    i = data4Beens.size() - 1;
                }


                Logger.d("i=" + i + "lengh=" + data4Beens.size());
                OneBean.Data4Bean data4Bean = data4Beens.get(i);

                AnnouncementBean.DataBean b = new AnnouncementBean.DataBean();
                b.setCreateTime(data4Bean.getCreateTime());
                b.setNoticeContent(data4Bean.getNoticeContent());
                b.setNoticeTitle(data4Bean.getNoticeTitle());


//                Intent intent = new Intent(getContext(), AnndatilsActivity.class);
//                intent.putExtra("data", (Serializable) b);
//                getActivity().startActivity(intent);

                break;


        }
    }


}
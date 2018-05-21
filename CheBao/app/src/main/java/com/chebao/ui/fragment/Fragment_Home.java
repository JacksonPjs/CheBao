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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.chebao.R;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.AboutActivity;
import com.chebao.ui.activity.AnndatilsActivity;
import com.chebao.ui.activity.AnnouncementListActivity;
import com.chebao.ui.activity.DetailsActivity;
import com.chebao.ui.activity.DetailsRegularActivity;
import com.chebao.ui.activity.WebActivity;
import com.chebao.bean.AnnouncementBean;
import com.chebao.bean.OneBean;
import com.chebao.net.NetService;
import com.chebao.ui.activity.WebActivityJS;
import com.chebao.ui.activity.WebNoTitileActivity;
import com.chebao.utils.T1changerString;
import com.chebao.widget.GoodProgressView;
import com.chebao.widget.ProgressSeek;
import com.pvj.xlibrary.banner.Banner;
import com.pvj.xlibrary.banner.BannerIndicator;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.pvj.xlibrary.loadinglayout.Utils;
import com.pvj.xlibrary.log.Logger;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

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
    GoodProgressView progressSeek;

    @Bind(R.id.xinshou)
    TextView xinshou;
    @Bind(R.id.tuijian_lilv)
    TextView lilvTv;
    @Bind(R.id.didibao_lilv)
    TextView didibaolilv;
    @Bind(R.id.tuijian_date)
    TextView tuijiandate;
    @Bind(R.id.tuijian_fangshi)
    TextView tuijianfangshi;
    @Bind(R.id.yonghu)
    TextView yonghu;

    @Bind(R.id.yonghu_date)
    TextView yonghudate;


    @Bind(R.id.yonghu_lilv)
    TextView yonghulilv;
    @Bind(R.id.yonghu_fangshi)
    TextView yonghufangshi;
    @Bind(R.id.progressBar_yonghu)
    GoodProgressView progressBaryonghu;
    @Bind(R.id.home_six_rl)
    RelativeLayout home_six_rl;
    @Bind(R.id.home_five_rl)
    RelativeLayout home_five_rl;

    private BitHandler bitHandler;

    List<OneBean.BannersBean> drawables;
    // 公告列表
    List<OneBean.Data4Bean> data4Beens;

    private int index = 0;

    OneBean.Data1Bean data1Bean;
    OneBean.Data2Bean data2Bean;

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
        initBanner();
    }


    private void initBanner() {
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

        banner.setInterval(5000);
        banner.setPageChangeDuration(500);

//        List imageViewList = new ArrayList<>();
//        imageViewList.add(this.getResources().getDrawable(R.mipmap.banner));
//        imageViewList.add(this.getResources().getDrawable(R.mipmap.banner_discount));
        banner.setBannerDataInit(new Banner.BannerDataInit() {
            @Override
            public ImageView initImageView() {
                return (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.imageview, null);
            }

            @Override
            public void initImgData(ImageView imageView, Object imgPath) {
//                imageView.setImageDrawable((Drawable) imgPath);
                Logger.d("initImgData" + NetService.API_SERVER_Url + ((OneBean.BannersBean) imgPath).getImgPath());
                Glide.with(Fragment_Home.this)
                        .load(NetService.API_SERVER_Url + ((OneBean.BannersBean) imgPath).getImgPath())
                        .error(R.mipmap.banner)
                        .into(imageView);
            }
        });


        bannerIndicator.setIndicatorSource(
                ContextCompat.getDrawable(getActivity(), R.mipmap.zuobiao_dangqian_banner),//select
                ContextCompat.getDrawable(getActivity(), R.mipmap.baisezuobiao_banner),//unselect
                Utils.dp2px(getActivity(), 14)//widthAndHeight
        );
        banner.attachIndicator(bannerIndicator);
//        banner.setDataSource(imageViewList);


        banner.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(getActivity(), WebNoTitileActivity.class);
                intent.putExtra("url", NetService.API_SERVER_Url + drawables.get(position).getUrl());
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
        NetWorks.index(new Subscriber<OneBean>() {
            @Override
            public void onStart() {
//                layoutContiant.setStatus(LoadingLayout.Loading);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {


                Logger.e(e.toString());
//                layoutContiant.setStatus(LoadingLayout.Error);
            }

            @Override
            public void onNext(OneBean oneBean) {

                if (oneBean.getState().getStatus() == 0) {
                    //广告
//                    layoutContiant.setStatus(LoadingLayout.Success);
                    drawables.clear();
                    drawables.addAll(oneBean.getBanners());
                    banner.setDataSource(drawables);

                    // 设置 广播消息
                    data4Beens.clear();
                    data4Beens.addAll(oneBean.getData4());

                    new myThread().start();

                    didibaolilv.setText(oneBean.getBorrowhqll() + "%+2%");

                    data1Bean = oneBean.getData1();
                    if (data1Bean.getBorrowTitle() == null) {
                        home_five_rl.setVisibility(View.GONE);
                    }else {
                        if (data1Bean.getBorrowType()!=5){
                            home_five_rl.setVisibility(View.GONE);

                        }else {
                            //新手标数据
                            xinshou.setText(data1Bean.getBorrowTitle());
                            tuijiandate.setText("投资期限:" + T1changerString.t2chager(data1Bean.getDeadline(), data1Bean.getDeadlineType()));
                            lilvTv.setText((data1Bean.getAnnualRate() - 3) + "%+3%");
                            tuijianfangshi.setText("收益方式:" + T1changerString.t4chager(data1Bean.getRepayType()));
                        }

                    }

                    data2Bean = oneBean.getData2();
                    if (data2Bean == null) {
                        home_six_rl.setVisibility(View.GONE);
                    } else {
                        if (data2Bean.getBorrowType()!=5){
                            //用户标
                            yonghu.setText(data2Bean.getBorrowTitle());
                            yonghudate.setText("投资期限:" + T1changerString.t2chager(data2Bean.getDeadline(), data2Bean.getDeadlineType()));
                            yonghulilv.setText((data2Bean.getAnnualRate() - 1) + "%+1%");
                            yonghufangshi.setText("收益方式:" + T1changerString.t4chager(data2Bean.getRepayType()));
                            handler.sendEmptyMessage(1);
                        }else {
                            home_six_rl.setVisibility(View.GONE);

                        }

                    }


                } else {
//                    layoutContiant.setStatus(LoadingLayout.Error);
                }


            }
        });
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {

                //通知view，进度值有变化
                progressSeek.setProgressValue((int) (data1Bean.getProgress() * 100));
                progressSeek.postInvalidate();
                //通知view，进度值有变化
                progressSeek.setProgressValue((int) (data1Bean.getProgress() * 100));
                progressSeek.postInvalidate();
                double progress = data2Bean.getProgress() * 100;
                progressBaryonghu.setProgressValue((int) progress);

                progressBaryonghu.postInvalidate();

            }
            super.handleMessage(msg);
        }

        ;
    };

    private void initNetData() {

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


    @OnClick({R.id.gonggao, R.id.home_four_rl, R.id.home_five_rl, R.id.home_six_rl, R.id.re_weixin1, R.id.re_contact_list1, R.id.re_find1
            , R.id.re_four})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.re_four:
                intent = new Intent(getActivity(), WebNoTitileActivity.class);
                intent.putExtra("url", NetService.API_SERVER_Url + "wechat/recommend.html");
                startActivity(intent);
                break;
            case R.id.re_find1:
                intent = new Intent(getActivity(), WebNoTitileActivity.class);
                intent.putExtra("url", NetService.API_SERVER_Url + "wechat/safe.html");
                startActivity(intent);
                break;
            case R.id.re_contact_list1:
//                intent =new Intent(getActivity(), WebActivity.class);
//                intent.putExtra("url","http://www.jq22.com/");
//                startActivity(intent);
                intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.re_weixin1:
                intent = new Intent(getActivity(), WebNoTitileActivity.class);
                intent.putExtra("url", NetService.API_SERVER_Url + "wechat/yungift.html");
                startActivity(intent);

                break;
            case R.id.home_five_rl:
                intent = new Intent(getActivity(), DetailsRegularActivity.class);
                if (data1Bean != null)
                    intent.putExtra("id", data1Bean.getId() + "");
                startActivity(intent);
                break;
            case R.id.home_six_rl:
                intent = new Intent(getActivity(), DetailsRegularActivity.class);
                intent.putExtra("id", data2Bean.getId() + "");
                startActivity(intent);
                break;
            case R.id.home_four_rl:
                intent = new Intent(getActivity(), DetailsActivity.class);
                startActivity(intent);
                break;
            case R.id.gonggao:

//                if (data4Beens == null) {
//                    return;
//                }
//
//
//                int i = index - 1;
//                if (i < 0) {
//                    i = data4Beens.size() - 1;
//                }
//
//
//                Logger.d("i=" + i + "lengh=" + data4Beens.size());
//                OneBean.Data4Bean data4Bean = data4Beens.get(i);
//
//                AnnouncementBean.DataBean b = new AnnouncementBean.DataBean();
//                b.setCreateTime(data4Bean.getCreateTime());
//                b.setNoticeContent(data4Bean.getNoticeContent());
//                b.setNoticeTitle(data4Bean.getNoticeTitle());


                intent = new Intent(getContext(), AnnouncementListActivity.class);
//                intent.putExtra("data", (Serializable) b);
                getActivity().startActivity(intent);

                break;


        }
    }


}
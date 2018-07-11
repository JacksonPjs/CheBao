package com.chebao.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.chebao.Adapter.HomeAdapter;
import com.chebao.MainActivity;
import com.chebao.R;
import com.chebao.bean.LoginBean;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.security.AddBankActivity;
import com.chebao.ui.activity.web.AboutActivity;
import com.chebao.ui.activity.mine.AnnouncementListActivity;
import com.chebao.bean.OneBean;
import com.chebao.net.NetService;
import com.chebao.ui.activity.web.WebNoTitileActivity;
import com.chebao.ui.activity.web.WebNotitleHtmlActivity;
import com.chebao.ui.activity.login2register.LoginActivity;
import com.chebao.utils.SharedPreferencesUtils;
import com.chebao.utils.onclick.AntiShake;
import com.pvj.xlibrary.banner.Banner;
import com.pvj.xlibrary.banner.BannerIndicator;
import com.pvj.xlibrary.loadinglayout.Utils;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;
import com.squareup.okhttp.Request;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import static com.chebao.utils.edncodeUtils.getCookie;

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


    private BitHandler bitHandler;

    List<OneBean.BannersBean> drawables;
    // 公告列表
    List<OneBean.Data4Bean> data4Beens;

    private int index = 0;


    List<OneBean.Data2Bean> biaoBeenList;

    @Bind(R.id.public_listview)
    RecyclerView publicLv;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    HomeAdapter homeAdapter;
    int page = 0;


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
                    bitHandler.sendEmptyMessageDelayed(0, 2000);

                } else if (index < data4Beens.size()) {
                    bitHandler.sendEmptyMessageDelayed(0, 2000);
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


        biaoBeenList = new ArrayList<>();
        homeAdapter = new HomeAdapter(biaoBeenList, getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        publicLv.setLayoutManager(manager);

//        publicLv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        publicLv.setAdapter(homeAdapter);


        publicLv.setNestedScrollingEnabled(false);
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

        OneBean oneBean = SharedPreferencesUtils.getHome(getActivity());

        bitHandler = new BitHandler();
        swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorPrimary});
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
//                swipeRefreshLayout.setRefreshing(false);
            }
        });


        //----------------------banner start------------------------------
        initBanner();
        if (oneBean!=null)
            setDate(oneBean);
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

                if (AntiShake.check(banner)) {    //判断是否多次点击
                    return;
                }
//                intent.putExtra("url", NetService.API_SERVER_Url + drawables.get(position).getUrl());
//                intent.putExtra("title", drawables.get(position).getBannerName());
                //wechat/index.html
                String url=drawables.get(position).getUrl();
                if (url.endsWith("wechat/index.html")) {

                }else {
                    Intent intent = new Intent(getActivity(), WebNoTitileActivity.class);

                    intent.putExtra("url", NetService.API_SERVER_Url + drawables.get(position).getUrl());
                    intent.putExtra("title", drawables.get(position).getBannerName());
                    startActivity(intent);

                }

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
                swipeRefreshLayout.setRefreshing(false);

//                layoutContiant.setStatus(LoadingLayout.Error);
            }

            @Override
            public void onNext(OneBean oneBean) {

                if (oneBean.getState().getStatus() == 0) {
                    SharedPreferencesUtils.savaHome(getActivity(), oneBean);
                    setDate(oneBean);
                }


            }
        });
    }


    private void setDate(OneBean oneBean) {
        drawables.clear();
        drawables.addAll(oneBean.getBanners());
        banner.setDataSource(drawables);

        // 设置 广播消息
        data4Beens.clear();
        data4Beens.addAll(oneBean.getData4());

//                    new myThread().start();
        bitHandler.removeMessages(0);
        bitHandler.sendEmptyMessage(0);
        if (oneBean.getData2().size() > 0) {
            biaoBeenList.clear();
            biaoBeenList.addAll(oneBean.getData2());
        } else {
            publicLv.setVisibility(View.GONE);


        }
        homeAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
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
        MobclickAgent.onPause(getActivity()); //统计时长

    }

    @Override
    public void onResume() {
        super.onResume();
        banner.resumeScroll();
        MobclickAgent.onResume(getActivity()); //统计时长

    }


    @OnClick({R.id.gonggao, R.id.re_weixin1, R.id.re_contact_list1, R.id.re_find1
            , R.id.re_four, R.id.more_invest})
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        Intent intent = null;
        switch (view.getId()) {
            case R.id.more_invest:
                intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("index", 1);
                startActivity(intent);
                break;
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
                intent.putExtra("url", NetService.API_SERVER_Url + "wechat/aboutUs.html");
                startActivity(intent);
//                intent = new Intent(getActivity(), AboutActivity.class);
//                startActivity(intent);
                break;
            case R.id.re_weixin1:
                intent = new Intent(getActivity(), WebNoTitileActivity.class);
                intent.putExtra("url", NetService.API_SERVER_Url + "wechat/yungift.html");
                startActivity(intent);

                break;


            case R.id.gonggao:


                intent = new Intent(getContext(), AnnouncementListActivity.class);
//                intent.putExtra("data", (Serializable) b);
                getActivity().startActivity(intent);

                break;


        }
    }


}
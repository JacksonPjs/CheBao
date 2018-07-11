package com.chebao.ui.activity.mine;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chebao.Adapter.ShareNoteAdapter;
import com.chebao.App.Constant;
import com.chebao.R;
import com.chebao.bean.ShareNoteBean;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.BaseActivity;
import com.chebao.utils.T1changerString;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.recyclerview.LoadingMoreFooter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

import static com.chebao.utils.edncodeUtils.getCookie;

/**
 * 创建日期：2018/6/28 on 9:42
 * 描述:
 * 作者:jackson Administrator
 */
public class ShareNoteActivity extends BaseActivity {
    Activity activity;

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_count)
    TextView tvCount;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    ShareNoteAdapter adapter;
    List<ShareNoteBean.DataBean> list = new ArrayList<>();
    int page = 1;
    LoadingMoreFooter footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_note);
        ButterKnife.bind(this);
        activity = this;
        title.setText("邀请记录");
        init();
        net(0, 0);

    }

    public void init() {

        adapter = new ShareNoteAdapter(list, activity);
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.addHeaderView(LayoutInflater.from(activity).inflate(R.layout.item_share_note_header, null));
        recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));

        footer = new LoadingMoreFooter(activity);
        adapter.addFooterView(footer);
//        recyclerView.setNestedScrollingEnabled(false);
//        footer.setVisibility(View.VISIBLE);

        swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorPrimary});
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                net(0, 0);
                footer.setTextInit();

//                adapter.AddHeaderItem(list);
//                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

                if (layoutManager.getChildCount() > 0
                        && lastVisibleItemPosition >= layoutManager.getItemCount() - 1
                        && layoutManager.getItemCount() >= layoutManager.getChildCount()
                        ) {
                    footer.setTextInit();
                } else {
                    footer.setTextGone();

                }
//判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {

                    page++;
                    net(1, 0);


                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //最后一个可见的ITEM
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();

            }
        });

    }

    private void net(final int stype, final int inrefresh) {
        NetWorks.selectReferee(getCookie(), page + "", new Subscriber<ShareNoteBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

                swipeRefreshLayout.setRefreshing(false);
                footer.setTextEnd();
                Logger.e(e.toString());
            }

            @Override
            public void onNext(ShareNoteBean bean) {

                if (stype == 0) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (bean.getState().getStatus().equals(Constant.STATUS_SUCCESS)) {
                        footer.setTextGone();
                        tvCount.setText("已邀请"+bean.getCount()+"位好友");
                        tvMoney.setText(bean.getSumtjjl()+"");
                        list.clear();
                        list.addAll(bean.getData());
                    } else {
                        footer.setTextEnd();
                    }

                } else if (stype == 1) {

                    if (bean.getState().getStatus().equals(Constant.STATUS_SUCCESS)) {
                        footer.setTextGone();
                        list.addAll(bean.getData());
                    } else {
                        footer.setTextEnd();
                    }

                }
                adapter.notifyDataSetChanged();
            }

        });

    }

}

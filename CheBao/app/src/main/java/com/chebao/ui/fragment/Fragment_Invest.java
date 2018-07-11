package com.chebao.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chebao.Adapter.InvestAdapter;
import com.chebao.App.Constant;
import com.chebao.R;
import com.chebao.bean.BiaoBean;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.invest.InvestSellOutActivity;
import com.chebao.utils.onclick.AntiShake;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.pvj.xlibrary.loadinglayout.Utils;
import com.pvj.xlibrary.loadingrecyclerview.LoadMoreRecyclerLoadingLayout;
import com.pvj.xlibrary.log.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class Fragment_Invest extends Fragment implements LoadingLayout.OnReloadListener,
        LoadMoreRecyclerLoadingLayout.OnRefreshAndLoadMoreListener {
    List<BiaoBean.DataBean> biaoBeenList;
    InvestAdapter adapter;
    @Bind(R.id.title)
    TextView tiltle;
    @Bind(R.id.sellout)
    TextView sellout;

    int page = 1;
    int pagesize = 10;



    @Bind(R.id.public_listview)
    LoadMoreRecyclerLoadingLayout publicLv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_invest, null);
        ButterKnife.bind(this, rootView);
        initView();
        tiltle.setText("出借");
        net(0, 0);
        return rootView;
    }

    private void initView() {
        biaoBeenList = new ArrayList<>();
        adapter = new InvestAdapter(biaoBeenList, getActivity(), Constant.INVESTING);
//        publicLv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        publicLv.verticalLayoutManager(getContext())
                .setAdapter(adapter)
                .setOnReloadListener(this)
                .setRecycleViewBackgroundColor(Utils.getColor(getActivity(), R.color.bg_huise))
                .setOnRefreshAndLoadMoreListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({ R.id.sellout})
    public void onClick(View v) {
        if (AntiShake.check(v.getId())) {    //判断是否多次点击
            return;
        }
        Intent intent = null;
        switch (v.getId()) {

            case R.id.sellout:

                intent = new Intent(getActivity(), InvestSellOutActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * @param stype     是刷新 还是加载  0是刷新  1是加载
     * @param inrefresh 第几次刷新下的加载
     */
    private void net(final int stype, final int inrefresh) {
        NetWorks.selectBorrowListApp(page + "", pagesize + "", new Subscriber<BiaoBean>() {
            @Override
            public void onCompleted() {
                publicLv.setRefreshing(false);
                if (stype == 0) {
                    publicLv.setRefreshing(false);
                } else {
                    publicLv.loadMoreComplete();
                }

            }

            @Override
            public void onError(Throwable e) {
                if (stype == 0) {
                    publicLv.setRefreshing(false);
                } else {
                    publicLv.loadMoreComplete();
                }
                if (page == 1) {
                    publicLv.setStatus(LoadingLayout.Error);
                } else {
                    publicLv.setTextEnd();
                }

                Logger.e(e.toString());
            }

            @Override
            public void onNext(BiaoBean biaoBean) {

                if (stype == 0) {
                    if (biaoBean.getState().getStatus() == 0) {
                        biaoBeenList.clear();
                        biaoBeenList.addAll(biaoBean.getData());
                        publicLv.setStatus(LoadingLayout.Success);
//                       adapter.upData();
                    } else {
                        publicLv.setStatus(LoadingLayout.Empty);
                    }

                }
                else if (stype == 1) {
                    if (publicLv.getRefreshCount() == inrefresh) {

                        if (biaoBean.getState().getStatus() == 0) {
                            biaoBeenList.addAll(biaoBean.getData());
                        } else {
                            publicLv.setTextEnd();
                        }

                    }
                }
                adapter.notifyDataSetChanged();
            }

        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onReload(View v) {
        page = 1;
        publicLv.setStatus(LoadingLayout.Loading);
        net(0, 0);
    }

    @Override
    public void onRefresh() {
        page = 1;
        net(0, 0);
        publicLv.setTextStart();
    }

    @Override
    public void onLoadMore(int inrefresh) {
        page++;
        net(1, inrefresh);
    }


}
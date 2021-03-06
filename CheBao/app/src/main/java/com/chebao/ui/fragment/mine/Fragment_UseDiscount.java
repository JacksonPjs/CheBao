package com.chebao.ui.fragment.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.chebao.Adapter.DiscountListAdapter;
import com.chebao.R;
import com.chebao.bean.DiscountListBean;
import com.chebao.net.NetWorks;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.pvj.xlibrary.loadinglayout.Utils;
import com.pvj.xlibrary.loadingrecyclerview.LoadMoreRecyclerLoadingLayout;
import com.pvj.xlibrary.log.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

import static com.chebao.utils.edncodeUtils.getCookie;

/**
 * Created by Administrator on 2017/5/9.
 * 优惠券已使用
 */

public class Fragment_UseDiscount extends Fragment implements LoadingLayout.OnReloadListener, LoadMoreRecyclerLoadingLayout.OnRefreshAndLoadMoreListener {
    List<DiscountListBean.DisData> biaoBeenList;
    DiscountListAdapter adapter;


    int page = 1;
    int pagesize = 10;

    @Bind(R.id.public_listview)
    LoadMoreRecyclerLoadingLayout publicLv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_commonly, null);
        ButterKnife.bind(this, rootView);
        initView();

        net(0, 0);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        page=1;
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {

            //隐藏时所作的事情
            page=1;

        } else {
            //显示时所作的事情

        }
    }
    /**
     * @param stype     是刷新 还是加载  0是刷新  1是加载
     * @param inrefresh 第几次刷新下的加载
     */
    private void net(final int stype, final int inrefresh) {


        NetWorks.CouponAndJxList(getCookie(), "2", page + "", new Subscriber<DiscountListBean>() {
            @Override
            public void onCompleted() {
                publicLv.setRefreshing(false);
                //    publicLv.setStatus(LoadingLayout.Success);
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
            public void onNext(DiscountListBean biaoBean) {

                if (stype == 0) {
                    if (biaoBean.getState().getStatus() == 0) {
                        biaoBeenList.clear();
                        biaoBeenList.addAll(biaoBean.getData());
                        publicLv.setStatus(LoadingLayout.Success);
                    } else {
                        publicLv.setStatus(LoadingLayout.Empty);
                    }

                } else if (stype == 1) {
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

    private void initView() {
        biaoBeenList = new ArrayList<>();
        adapter = new DiscountListAdapter(biaoBeenList, getActivity(), 3);

        publicLv.verticalLayoutManager(getContext())
                .setAdapter(adapter)
                .setOnReloadListener(this)
                .setRecycleViewBackgroundColor(Utils.getColor(getActivity(), R.color.bg_huise))
                .setOnRefreshAndLoadMoreListener(this);
//        LinearLayoutManager linearLayoutManager;
//        linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        publicLv.setLayoutManager(linearLayoutManager);
//        publicLv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
//
//        publicLv.setAdapter(adapter);
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
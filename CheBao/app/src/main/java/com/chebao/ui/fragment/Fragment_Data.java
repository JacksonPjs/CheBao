package com.chebao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


import com.chebao.Adapter.GirdViewAdapter;
import com.chebao.Adapter.PicLookAdapter;
import com.chebao.R;
import com.chebao.bean.ProductDetialBean;
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

/**
 * Created by Administrator on 2017/3/14.
 * 产品资源
 */

public class Fragment_Data extends Fragment implements  LoadingLayout.OnReloadListener, LoadMoreRecyclerLoadingLayout.OnRefreshAndLoadMoreListener {

    String id;

    @Bind(R.id.public_listview)
    LoadMoreRecyclerLoadingLayout publicLv;
    PicLookAdapter adapter ;
    List<ProductDetialBean.DataBean> datas;
    int page = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        id = bundle.getString("id");

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_commonly, null);
        ButterKnife.bind(this, rootView);
        initView();
        net(0, 0);
        return rootView;
    }



    private void net(final int stype, final int inrefresh) {
        NetWorks.queryBorrowData(id, new Subscriber<ProductDetialBean>() {
            @Override
            public void onStart() {

            }

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
            public void onNext(ProductDetialBean biaoBean) {
                if (stype == 0) {
                    if (biaoBean.getState().getStatus() == 0) {
                        datas.clear();
                        datas.addAll(biaoBean.getData());
                        publicLv.setStatus(LoadingLayout.Success);
//                       adapter.upData();
                    } else {
                        publicLv.setStatus(LoadingLayout.Empty);
                    }

                }
//                else if (stype == 1) {
//                    if (publicLv.getRefreshCount() == inrefresh) {
//
//                        if (biaoBean.getState().getStatus() == 0) {
//                            datas.addAll(biaoBean.getData());
//                        } else {
//                            publicLv.setTextEnd();
//                        }
//
//                    }
//                }
                adapter.notifyDataSetChanged();

            }
        });

    }

    private void initView() {
        datas =  new ArrayList<>();
        adapter = new PicLookAdapter(datas,getActivity());
        publicLv.verticalLayoutManager(getContext())
                .setAdapter(adapter)
                .setOnReloadListener(this)
                .setRecycleViewBackgroundColor(Utils.getColor(getActivity(), R.color.bg_huise))
                .setOnRefreshAndLoadMoreListener(this);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
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
package com.chebao.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chebao.Adapter.BiaoAdapter;
import com.chebao.Adapter.InvestAdapter;
import com.chebao.R;
import com.chebao.ui.activity.DetailsActivity;
import com.chebao.widget.DividerItemDecoration;
import com.chebao.widget.ScrollLinearLayoutManager;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.pvj.xlibrary.loadingrecyclerview.LoadMoreRecyclerLoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Fragment_Invest extends Fragment implements LoadingLayout.OnReloadListener,
        LoadMoreRecyclerLoadingLayout.OnRefreshAndLoadMoreListener {
    List<String> biaoBeenList;
    InvestAdapter adapter;
    @Bind(R.id.title)
    TextView tiltle;

    int page = 1;
    int pagesize = 10;
    @Bind(R.id.didibao_lilv)
    TextView lilv;
    @Bind(R.id.invest_item1)
    RelativeLayout item;

    @Bind(R.id.public_listview)
//    LoadMoreRecyclerLoadingLayout publicLv;
            RecyclerView publicLv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_invest, null);
        ButterKnife.bind(this, rootView);
        initView();
        tiltle.setText("投资");
        net(0, 0);
        return rootView;
    }


    /**
     * @param stype     是刷新 还是加载  0是刷新  1是加载
     * @param inrefresh 第几次刷新下的加载
     */
    private void net(final int stype, final int inrefresh) {
//        NetWorks.selectBorrowListApp(page + "", pagesize + "","1", new Subscriber<BiaoBean>() {
//            @Override
//            public void onCompleted() {
//                publicLv.setRefreshing(false);
//                //    publicLv.setStatus(LoadingLayout.Success);
//                if (stype==0){
//                    publicLv.setRefreshing(false);
//                }else{
//                    publicLv.loadMoreComplete();
//                }
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                if (stype==0){
//                    publicLv.setRefreshing(false);
//                }else{
//                    publicLv.loadMoreComplete();
//                }
//                if (page==1){
//                    publicLv.setStatus(LoadingLayout.Error);
//                }else{
//                    publicLv.setTextEnd();
//                }
//
//                Logger.e(e.toString());
//            }
//
//            @Override
//            public void onNext(BiaoBean biaoBean) {
//
//                if (stype == 0) {
//                    if (biaoBean.getState().getStatus() == 0) {
//                        biaoBeenList.clear();
//                        biaoBeenList.addAll(biaoBean.getData());
//                        publicLv.setStatus(LoadingLayout.Success);
//                    } else {
//                        publicLv.setStatus(LoadingLayout.Empty);
//                    }
//
//                } else if (stype == 1) {
//                    if (publicLv.getRefreshCount() == inrefresh) {
//
//                        if (biaoBean.getState().getStatus() == 0) {
//                            biaoBeenList.addAll(biaoBean.getData());
//                        } else {
//                            publicLv.setTextEnd();
//                        }
//
//                    }
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//        });

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initView() {
        biaoBeenList = new ArrayList<>();
        biaoBeenList.add("1111");
        biaoBeenList.add("1111");
        biaoBeenList.add("1111");
        biaoBeenList.add("1111");
        biaoBeenList.add("1111");


        adapter = new InvestAdapter(biaoBeenList, getActivity());
//        publicLv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
//        publicLv.verticalLayoutManager(getContext())
//                .setAdapter(adapter)
//                .setOnReloadListener(this)
//                .setRecycleViewBackgroundColor(Utils.getColor(getActivity(), R.color.bg_huise))
//                .setOnRefreshAndLoadMoreListener(this);
//        LinearLayoutManager linearLayoutManager;
//        linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ScrollLinearLayoutManager scrollLinearLayoutManager = new ScrollLinearLayoutManager(getActivity());
        scrollLinearLayoutManager.setScrollEnabled(false);
        publicLv.setLayoutManager(scrollLinearLayoutManager);
//        publicLv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        publicLv.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onReload(View v) {
        page = 1;
//        publicLv.setStatus(LoadingLayout.Loading);
        net(0, 0);
    }

    @Override
    public void onRefresh() {
        page = 1;
        net(0, 0);
//        publicLv.setTextStart();
    }

    @Override
    public void onLoadMore(int inrefresh) {
        page++;
        net(1, inrefresh);
    }


}
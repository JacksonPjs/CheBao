package com.chebao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chebao.Adapter.AnnouncementAdapter;
import com.chebao.Adapter.FindAdapter;
import com.chebao.R;
import com.chebao.bean.AnnouncementBean;
import com.chebao.bean.FindBean;
import com.chebao.net.NetWorks;
import com.chebao.widget.DividerItemDecoration;
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
 * 创建日期：2018/5/17 on 18:09
 * 描述:
 * 作者:jackson Administrator
 */
public class NoticeListFragment extends Fragment implements LoadingLayout.OnReloadListener,
        LoadMoreRecyclerLoadingLayout.OnRefreshAndLoadMoreListener {
    List<AnnouncementBean.DataBean> biaoBeenList;
    AnnouncementAdapter adapter;


    int page = 1;
    int pagesize = 10;

    @Bind(R.id.public_listview)
    LoadMoreRecyclerLoadingLayout publicLv;
//            RecyclerView publicLv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_commonly, null);
        ButterKnife.bind(this, rootView);
        initView();

        net(0, 0);
        return rootView;
    }


    /**
     * @param stype     是刷新 还是加载  0是刷新  1是加载
     * @param inrefresh 第几次刷新下的加载
     */
    private void net(final int stype, final int inrefresh) {
        NetWorks.noticeList( new Subscriber<AnnouncementBean>() {
            @Override
            public void onCompleted() {
                publicLv.setRefreshing(false);

                if (stype==0){
                    publicLv.setRefreshing(false);
                }else{
                    publicLv.loadMoreComplete();
                }

            }

            @Override
            public void onError(Throwable e) {
                if (stype==0){
                    publicLv.setRefreshing(false);
                }else{
                    publicLv.loadMoreComplete();
                }
                if (page==1){
                    publicLv.setStatus(LoadingLayout.Error);
                }else{
                    publicLv.setTextEnd();
                }

                Logger.e(e.toString());
            }

            @Override
            public void onNext(AnnouncementBean biaoBean) {

                if (stype == 0) {
                    if (biaoBean.getState().getStatus() == 0) {
                        biaoBeenList.clear();
                        biaoBeenList.addAll(biaoBean.getData());
                        publicLv.setStatus(LoadingLayout.Success);
                    } else {
                        publicLv.setStatus(LoadingLayout.Empty);
                    }

                }
//                else if (stype == 1) {
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
                adapter.notifyDataSetChanged();
            }

        });

    }

    private void initView() {
        biaoBeenList = new ArrayList<>();


        adapter = new AnnouncementAdapter(biaoBeenList, getActivity());
//        publicLv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        publicLv.verticalLayoutManager(getContext())
                .setAdapter(adapter)
                .setOnReloadListener(this)
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
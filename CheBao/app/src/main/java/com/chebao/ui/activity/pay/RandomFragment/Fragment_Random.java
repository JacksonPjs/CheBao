package com.chebao.ui.activity.pay.RandomFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chebao.Adapter.FindAdapter;
import com.chebao.Adapter.RandomListAdapter;
import com.chebao.App.Constant;
import com.chebao.R;
import com.chebao.bean.FindBean;
import com.chebao.bean.InfoBean;
import com.chebao.bean.RandomListBean;
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

import static com.chebao.utils.edncodeUtils.getCookie;

/**
 * 创建日期：2018/5/30 on 16:09
 * 描述:
 * 作者:jackson Administrator
 */
public class Fragment_Random extends Fragment implements LoadingLayout.OnReloadListener,
        LoadMoreRecyclerLoadingLayout.OnRefreshAndLoadMoreListener {
    List<RandomListBean.DataBean> biaoBeenList;
    RandomListAdapter adapter;


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
        NetWorks.appRedemptionRecord(getCookie(),""+page,new Subscriber<RandomListBean>(){

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
            public void onNext(RandomListBean findBean) {
                if (stype == 0) {

                    if (findBean.getState().getStatus().equals(Constant.STATUS_SUCCESS)) {
                        biaoBeenList.clear();
                        biaoBeenList.addAll(findBean.getData());
                        if (findBean.getData().size()==0){
                            publicLv.setStatus(LoadingLayout.Empty);
                        }else {
                            publicLv.setStatus(LoadingLayout.Success);

                        }
                    } else {
                        publicLv.setStatus(LoadingLayout.Empty);
                    }

                }
                else if (stype == 1) {
                    if (publicLv.getRefreshCount() == inrefresh) {

                        if (findBean.getState().getStatus().equals(Constant.STATUS_SUCCESS)) {


                            biaoBeenList.addAll(findBean.getData());


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


        adapter = new RandomListAdapter(biaoBeenList, getActivity());
//        publicLv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
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
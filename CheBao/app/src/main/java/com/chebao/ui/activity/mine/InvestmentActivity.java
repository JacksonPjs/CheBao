package com.chebao.ui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chebao.Adapter.InvestmentAdapter;
import com.chebao.R;
import com.chebao.bean.InvestmentBean;
import com.chebao.bean.LoginBean;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.BaseActivity;
import com.chebao.utils.SharedPreferencesUtils;
import com.chebao.utils.onclick.AntiShake;
import com.chebao.widget.dialog.InvestmentDialog;
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

/**
 * 创建日期：2018/5/7 on 16:37
 * 描述:投资记录
 * 作者:jackson Administrator
 */
public class InvestmentActivity extends BaseActivity implements LoadingLayout.OnReloadListener, LoadMoreRecyclerLoadingLayout.OnRefreshAndLoadMoreListener {
    @Bind(R.id.type_select)
    TextView select;

    @Bind(R.id.title)
    TextView title;

    InvestmentAdapter adapter;

    List<InvestmentBean.DataBean> biaoBeenList;

    int type=-1;
    int page = 1;
    int pagesize = 10;
    @Bind(R.id.public_listview)
    LoadMoreRecyclerLoadingLayout publicLv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        ButterKnife.bind(this);
        title.setText("出借记录");
        initView();
        net(0, 0,type);

    }


    /**
     * @param stype     是刷新 还是加载  0是刷新  1是加载
     * @param inrefresh 第几次刷新下的加载
     */
    private void net(final int stype, final int inrefresh,int funtype) {
        if (type==-1){
            NetWorks.selectInvestListingAll(page + "", new Subscriber<InvestmentBean>() {
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
                public void onNext(InvestmentBean biaoBean) {


                    if (stype == 0) {
                        if (biaoBean.getState().getStatus() == 0) {
                            biaoBeenList.clear();

                            biaoBeenList.addAll(biaoBean.getData());
                            publicLv.setStatus(LoadingLayout.Success);
                        } else if(biaoBean.getState().getStatus() == 99){
                            netLogin();
                        }else {
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
        }else {
            NetWorks.selectInvestListing(page + "", funtype + "", new Subscriber<InvestmentBean>() {
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
                public void onNext(InvestmentBean biaoBean) {


                    if (stype == 0) {
                        if (biaoBean.getState().getStatus() == 0) {
                            biaoBeenList.clear();
                            biaoBeenList.addAll(biaoBean.getData());
                            publicLv.setStatus(LoadingLayout.Success);
                        } else if(biaoBean.getState().getStatus() == 99){
                            netLogin();
                        }else {
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


    }

    private void initView() {
        biaoBeenList = new ArrayList<>();
        adapter = new InvestmentAdapter(biaoBeenList, this);

        publicLv.verticalLayoutManager(this)
                .setAdapter(adapter)
                .setOnReloadListener(this)
                .setRecycleViewBackgroundColor(Utils.getColor(this, R.color.bg_huise))
                .setOnRefreshAndLoadMoreListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onReload(View v) {
        page = 1;
        publicLv.setStatus(LoadingLayout.Loading);
        net(0, 0,type);
    }

    @Override
    public void onRefresh() {
        page = 1;
        net(0, 0,type);
        publicLv.setTextStart();
    }

    @Override
    public void onLoadMore(int inrefresh) {
        page++;
        net(1, inrefresh,type);
    }


    @OnClick({R.id.type_select})
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        switch (view.getId()) {

            case R.id.type_select:

                InvestmentDialog dialog=new InvestmentDialog(this, new InvestmentDialog.ItemSelectListener() {
                    @Override
                    public void selectAll() {
                        type=-1;
                        page = 1;
                        net(0, 0,type);

                    }

                    @Override
                    public void selectMeet() {
                        //已还清
                        type=6;
                        page = 1;
                        net(0, 0,type);


                    }

                    @Override
                    public void selectInvite() {
                        //招标中
                        type=3;
                        page = 1;
                        net(0, 0,type);

                    }

                    @Override
                    public void selectRepayment() {
                        //还款中
                        type=5;
                        page = 1;
                        net(0, 0,type);

                    }
                });

                dialog.show();
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void netLogin() {

        NetWorks.login(SharedPreferencesUtils.getUserName(this),
                SharedPreferencesUtils.getPassword(this), new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        publicLv.setStatus(LoadingLayout.Error);
                        Logger.json(e.toString());
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean.getState().getStatus() == 0) {
                            net(0,0,type);
                        } else {
                            SharedPreferencesUtils.setIsLogin(InvestmentActivity.this,false);
                        }
                    }
                }
        );
    }

}
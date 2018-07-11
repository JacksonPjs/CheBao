package com.chebao.ui.activity.invest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chebao.Adapter.PayDiscountAdapter;
import com.chebao.R;
import com.chebao.bean.DiscountListBean;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.BaseActivity;
import com.chebao.ui.activity.login2register.LoginActivity;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.pvj.xlibrary.loadinglayout.Utils;
import com.pvj.xlibrary.loadingrecyclerview.LoadMoreRecyclerLoadingLayout;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * 创建日期：2018/5/18 on 11:11
 * 描述:我的卡券
 * 作者:jackson Administrator
 */
public class PayDiscountActivity extends BaseActivity implements LoadingLayout.OnReloadListener, LoadMoreRecyclerLoadingLayout.OnRefreshAndLoadMoreListener {
    @Bind(R.id.type_select)
    TextView select;
    @Bind(R.id.public_listview)
    LoadMoreRecyclerLoadingLayout publicLv;
    @Bind(R.id.title)
    TextView title;

    PayDiscountAdapter adapter;
    List<DiscountListBean.DisData> biaoBeenList;

    int type = 0;
    int page = 1;
    int pagesize = 10;

    String couponAmount;
    String Deadline;
    String DeadlineType;

// intent.putExtra("couponAmount",""+money);
//                    intent.putExtra("Deadline",bean.getData().getDeadline());
//                    intent.putExtra("DeadlineType",""+bean.getData().getDeadlineType());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        ButterKnife.bind(this);
        title.setText("我的卡券");
        initView();

        Intent intent=getIntent();
        couponAmount=intent.getStringExtra("couponAmount");
        Deadline=intent.getStringExtra("Deadline");
        DeadlineType=intent.getStringExtra("DeadlineType");
        net(0, 0, type);

    }


    /**
     * @param stype     是刷新 还是加载  0是刷新  1是加载
     * @param inrefresh 第几次刷新下的加载
     */
    private void net(final int stype, final int inrefresh, int funtype) {
        NetWorks.ajaxgetuseryhq(""+couponAmount + "", Deadline + "", DeadlineType+"",new Subscriber<DiscountListBean>() {
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
                    } else if (biaoBean.getState().getStatus() == 99){
                        Intent intent = new Intent(PayDiscountActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        T.ShowToastForLong(PayDiscountActivity.this,"未登录");

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
        select.setVisibility(View.GONE);
        biaoBeenList = new ArrayList<>();
        adapter = new PayDiscountAdapter(biaoBeenList, this, new PayDiscountAdapter.onItemClickListener() {
            @Override
            public void itemClick(int position) {
              Intent  intent=new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("disbean", (Serializable) biaoBeenList.get(position));
                bundle.putString("type",biaoBeenList.get(position).getUseqx()+"");
                bundle.putString("id",biaoBeenList.get(position).getId()+"");
                intent.putExtras(bundle);
                setResult(200,intent);
                finish();
            }
        });

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
        net(0, 0, type);
    }

    @Override
    public void onRefresh() {
        page = 1;
        net(0, 0, type);
        publicLv.setTextStart();
    }

    @Override
    public void onLoadMore(int inrefresh) {
        page++;
        net(1, inrefresh, type);
    }


    @OnClick({R.id.type_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.type_select:


                break;
        }
    }

}

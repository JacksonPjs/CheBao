package com.chebao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.bean.AconntBean;
import com.chebao.bean.DidibaoBean;
import com.chebao.bean.LoginBean;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.login2register.LoginActivity;
import com.chebao.ui.activity.pay.RansomListActivity;
import com.chebao.utils.LoginRegisterUtils;
import com.chebao.utils.SharedPreferencesUtils;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import static com.chebao.utils.edncodeUtils.encodeHeadInfo;
import static com.chebao.utils.edncodeUtils.getCookie;

/**
 * 创建日期：2018/5/7 on 18:26
 * 描述:我的滴滴宝
 * 作者:jackson Administrator
 */
public class MyActivtity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.zrsy)
    TextView zrsy;
    @Bind(R.id.ljsy)
    TextView ljsy;
    @Bind(R.id.gmze)
    TextView gmze;
    @Bind(R.id.loadinglayout)
    LoadingLayout layoutContiantZl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.bind(this);
        title.setText("我的滴滴宝");
        init();
    }

    private void init() {
        net();
    }

    @Override
    protected void onResume() {
        super.onResume();
        net();
    }

    @OnClick({R.id.chager, R.id.withdraw, R.id.ransomlist, R.id.back})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ransomlist:
                intent = new Intent(this, RansomListActivity.class);
                startActivity(intent);
                break;
            case R.id.chager:
                intent = new Intent(this, RansomActivity.class);
                startActivity(intent);
                finish();

                break;

            case R.id.withdraw:
                intent = new Intent(this, DepositActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void net() {


        NetWorks.userdidibao(getCookie() + "", new Subscriber<DidibaoBean>() {
            @Override
            public void onStart() {

                layoutContiantZl.setStatus(LoadingLayout.Loading);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                layoutContiantZl.setStatus(LoadingLayout.Error);
                Logger.json(e.toString());
            }

            @Override
            public void onNext(DidibaoBean s) {

                if (s.getState().getStatus() == 0) {
                    java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
                    nf.setGroupingUsed(false);


                    layoutContiantZl.setStatus(LoadingLayout.Success);
                    gmze.setText(s.getGmze());
                    zrsy.setText(s.getZrsy());
                    ljsy.setText(s.getLjsy());
//                    double usa=s.getTAccount().getUsableAmount();
//                    double allamount=s.getTAccount().getUsableAmount()+s.getTAccount().getFrozenAmount();
//                    if (allamount==0){
//                    }else{
//                        double max=usa/allamount;
//
//                    }
                } else if (s.getState().getStatus() == 99) {
                    netLogin();
                }


            }
        });
    }

    private void netLogin() {

        NetWorks.login(SharedPreferencesUtils.getUserName(this),
                SharedPreferencesUtils.getPassword(this), new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        layoutContiantZl.setStatus(LoadingLayout.Error);
                        Logger.json(e.toString());
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean.getState().getStatus() == 0) {
                            net();
                        } else {
                            Intent intent = new Intent(MyActivtity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                }
        );
    }

}

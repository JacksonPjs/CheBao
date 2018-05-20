package com.chebao.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.chebao.R;
import com.chebao.bean.BankListBean;
import com.chebao.bean.InfoBean;
import com.chebao.bean.LoginBean;
import com.chebao.net.NetWorks;
import com.chebao.utils.DialogUtils;
import com.chebao.utils.SharedPreferencesUtils;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/12/27.
 * 我的银行卡
 */

public class MyBankActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;


    @Bind(R.id.bank_contair)
    View bankContair;
    @Bind(R.id.bankname)
    TextView bankname;
    @Bind(R.id.banknum)
    TextView banknum;

    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.layout_contiant)
    LoadingLayout layoutContiant;

    BankListBean bak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybank);
        ButterKnife.bind(this);
        title.setText("我的银行卡");
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (SharedPreferencesUtils.getIsBank(this)) {
            bankContair.setVisibility(View.VISIBLE);

            netList();
        } else {
            layoutContiant.setStatus(LoadingLayout.Success);
            bankContair.setVisibility(View.GONE);
        }

    }

    // 获取银行卡列表
    private void netList() {
        NetWorks.selectBankCard(new Subscriber<BankListBean>() {

            @Override
            public void onStart() {
                layoutContiant.setStatus(LoadingLayout.Loading);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Logger.json(e.toString());
                T.ShowToastForShort(MyBankActivity.this, "网络异常");
                layoutContiant.setStatus(LoadingLayout.Error);
            }

            @Override
            public void onNext(BankListBean bean) {
                if (bean.getState().getStatus() == 0) {

                    bak = bean;
                    bankname.setText(bean.getData().get(0).getBankName());
                    name.setText("持卡人："+bean.getRealName()+"");


                    String aa = bean.getData().get(0).getBankCardNo();
                    int n = 4;

                    if (aa.length() > 4) {
                        String b = aa.substring(aa.length() - n, aa.length());
                        String s=aa.substring(0,4);
                        banknum.setText(s+" **** **** **** " + b);
                    } else {
                        banknum.setText("**** **** **** " + aa);
                    }

                    layoutContiant.setStatus(LoadingLayout.Success);
                } else if (bean.getState().getStatus() == 99) {
                    netLogin(0);
                } else {
                    layoutContiant.setStatus(LoadingLayout.Error);
                    T.ShowToastForShort(MyBankActivity.this, bean.getState().getInfo());
                }

            }
        });
    }



    private void netLogin(final int sytle) {

        NetWorks.login(SharedPreferencesUtils.getUserName(this),
                SharedPreferencesUtils.getPassword(this), new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (sytle == 0) {
                            layoutContiant.setStatus(LoadingLayout.Error);
                        } else if (sytle == 1) {
                            dialog.dismiss();
                        }

                        Logger.json(e.toString());
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean.getState().getStatus() == 0) {
                            if (sytle == 0) {
                                netList();
                            } else if (sytle == 1) {
//                                deleteBankCard(bak.getData().get(0).getId());
                            }

                        } else {
                            if (sytle == 1) {
                                dialog.dismiss();
                            }

                            SharedPreferencesUtils.setIsLogin(MyBankActivity.this, false);
                            T.ShowToastForShort(MyBankActivity.this, "账号存在异常，需重新登录！！");
//                            Intent intent = new Intent(MyBankActivity.this, LoginActivity.class);
//                            startActivity(intent);
                        }
                    }
                }
        );
    }

    Dialog dialog;


}
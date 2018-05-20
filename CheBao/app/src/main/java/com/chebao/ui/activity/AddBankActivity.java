package com.chebao.ui.activity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.bean.Bank;
import com.chebao.bean.CertificationBean;
import com.chebao.bean.LoginBean;
import com.chebao.net.NetService;
import com.chebao.net.NetWorks;
import com.chebao.net.OkHttpUtils;
import com.chebao.utils.BankDialog;
import com.chebao.utils.DialogUtils;
import com.chebao.utils.LoginRegisterUtils;
import com.chebao.utils.SharedPreferencesUtils;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;
import com.squareup.okhttp.Request;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/12/27.
 */

public class AddBankActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.card)
    EditText card;
    @Bind(R.id.zhihang)
    EditText zhihang;

    @Bind(R.id.login_go)
    Button loginGo;


    @Bind(R.id.layout_contiant_bank)
    LoadingLayout layoutContiant;


    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbank);
        ButterKnife.bind(this);
        title.setText("我的银行卡");

    }

    @Override
    protected void onResume() {
        super.onResume();
        net();
    }


    @OnClick({R.id.login_go})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.login_go:
                if (LoginRegisterUtils.isNullOrEmpty(zhihang)) {
                    T.ShowToastForShort(this, "手机号码未输入");
                    return;
                }

                if (!LoginRegisterUtils.isPhone(zhihang)) {
                    T.ShowToastForShort(this, "手机号码不正确");
                    return;
                }


                if (LoginRegisterUtils.isNullOrEmpty(card)) {
                    T.ShowToastForShort(AddBankActivity.this, "请输入银行卡号");
                    return;
                }


                if (card.getText().length() < 16) {
                    T.ShowToastForShort(AddBankActivity.this, "银行卡号输入不正确");
                    return;
                }

                bankNet(card.getText().toString());

                break;
        }
    }


    // 获取身份信息
    private void net() {
        NetWorks.userPerson(new Subscriber<CertificationBean>() {
            @Override
            public void onStart() {

                layoutContiant.setStatus(LoadingLayout.Loading);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.toString());
                layoutContiant.setStatus(LoadingLayout.Error);
                T.ShowToastForShort(AddBankActivity.this, "网络异常");
            }

            @Override
            public void onNext(CertificationBean s) {
                if (s.getState().getStatus() == 0) {
                    name.setText(s.getTPerson().getRealName());
                    layoutContiant.setStatus(LoadingLayout.Success);
                } else  if (s.getState().getStatus()==3){
                    T.ShowToastForShort(AddBankActivity.this, "绑定银行卡请先实名");

                }else if (s.getState().getStatus() == 99) {
                    netLogin(0);
                } else {
                    T.ShowToastForShort(AddBankActivity.this, s.getState().getInfo());
                    layoutContiant.setStatus(LoadingLayout.Error);
                }
            }
        });
    }

    // 请求绑定银行卡
    private void bankNet(final String carno) {
        StringBuilder sb = new StringBuilder();
        sb.append(" _ed_token_");
        sb.append("=");
        sb.append((String) SharedPreferencesUtils.getParam(MyApplication.context, "token", ""));
        sb.append(";");

        sb.append(" _ed_username_");
        sb.append("=");
        sb.append((String) SharedPreferencesUtils.getParam(MyApplication.context, "name", ""));
        sb.append(";");

        sb.append(" _ed_cellphone_");
        sb.append("=");
        sb.append((String) SharedPreferencesUtils.getParam(MyApplication.context, "phone", ""));
        sb.append(";");

        if (dialog == null) {
            dialog = DialogUtils.createProgressDialog(AddBankActivity.this, "添加银行卡中...");
        } else {
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }
        com.zhy.http.okhttp.OkHttpUtils
                .post()
                .url(NetService.API_SERVER_Url + "bandBank.html")
                .addHeader("Cookie", sb.toString())
                .addParams("phone", zhihang.getText().toString() + "")
                .addParams("bankCardNo", carno)
                .build()
                .execute(new com.zhy.http.okhttp.callback.Callback<String>() {

                    @Override
                    public String parseNetworkResponse(com.squareup.okhttp.Response response) throws IOException {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String o) {
                        Logger.json(o);

                        dialog.dismiss();
                        try {
                            JSONObject json = new JSONObject((String) o.toString());

                            JSONObject sata = json.getJSONObject("state");

                            int s = sata.getInt("status");
                            if (s == 0) {
                                SharedPreferencesUtils.setParam(AddBankActivity.this, "tBankCardlist", true);
                                SharedPreferencesUtils.setBankNUm(AddBankActivity.this,carno);

                                dialog.dismiss();
                                finish();
                            } else if (s == 99) {
                                netLogin(1);
                            } else {
                                dialog.dismiss();
                                T.ShowToastForShort(AddBankActivity.this, sata.getString("info"));
                            }
                        } catch (JSONException e) {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            T.ShowToastForShort(AddBankActivity.this, "数据异常!");
                            //   e.printStackTrace();
                        }

                    }


                });
    }


    /**
     * @param style 0  是 请求实名      1 ， 是请求插入银行卡
     */
    private void netLogin(final int style) {

        NetWorks.login(SharedPreferencesUtils.getUserName(this),
                SharedPreferencesUtils.getPassword(this), new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }

                        if (style == 0) {
                            layoutContiant.setStatus(LoadingLayout.Error);
                        }
                        //  layoutContiantZl.setStatus(LoadingLayout.Error);
                        Logger.json(e.toString());
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean.getState().getStatus() == 0) {
                            if (style == 0) {
                                net();
                            } else if (style == 1) {
                                bankNet(card.getText().toString());
                            }

                        } else {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            Intent intent = new Intent(AddBankActivity.this, AddBankActivity.class);
                            startActivity(intent);
                        }
                    }
                }
        );
    }
}

package com.chebao.ui.activity.security;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.bean.CertificationBean;
import com.chebao.bean.LoginBean;
import com.chebao.net.NetService;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.BaseActivity;
import com.chebao.utils.DialogUtils;
import com.chebao.utils.LoginRegisterUtils;
import com.chebao.utils.SharedPreferencesUtils;
import com.chebao.utils.onclick.AntiShake;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.pvj.xlibrary.loadinglayout.Utils;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import rx.Subscriber;

import static com.chebao.utils.edncodeUtils.getCookie;

/**
 * 实名认证
 * Created by pvj on 2016/12/27.
 */

public class CertificationActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.user_name)
    EditText userName;
    @Bind(R.id.sfz)
    EditText sfz;
    @Bind(R.id.rez_go)
    Button rezGo;
    @Bind(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        ButterKnife.bind(this);
        title.setText("实名认证");
        net();

        if ((Boolean) SharedPreferencesUtils.getParam(this, "tPerson", false)) {

//            rezGo.setBackground(Utils.getDrawble(this, R.drawable.button_border_hui));
            rezGo.setEnabled(false);
            rezGo.setText("已认证");
        } else {
            loadinglayout.setStatus(LoadingLayout.Success);
        }


    }


    @OnClick(R.id.rez_go)
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        if (LoginRegisterUtils.isNullOrEmpty(userName)) {
            T.ShowToastForShort(this, "请填写姓名");
            return;
        }

        if (LoginRegisterUtils.isNullOrEmpty(sfz)) {
            T.ShowToastForShort(this, "请填写身份证");
            return;
        }


        goShiMin(userName.getText().toString(), sfz.getText().toString());

    }


    public void goShiMin(final String name, String no) {
//        NetWorks.fysmrz(getCookie(),name,no,new Subscriber<CertificationBean>() {
//            @Override
//            public void onStart() {
//                loadinglayout.setStatus(LoadingLayout.Loading);
//            }
//
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Logger.e(e.toString());
//                loadinglayout.setStatus(LoadingLayout.Error);
//                T.ShowToastForShort(CertificationActivity.this, "网络异常");
//            }
//
//            @Override
//            public void onNext(CertificationBean s) {
//                if (s.getState().getStatus() == 0 | s.getState().getStatus() == 31) {
//                    userName.setText(s.getTPerson().getRealName());
//                    userName.setKeyListener(null);
//                    sfz.setText(s.getTPerson().getCardNo());
//                    sfz.setKeyListener(null);
//                    loadinglayout.setStatus(LoadingLayout.Success);
//
//                } else if (s.getState().getStatus() == 99) {
//                    netLogin(0);
//                } else {
//                    T.ShowToastForShort(CertificationActivity.this, s.getState().getInfo());
//                }
//            }
//        });
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
            dialog = DialogUtils.createProgressDialog(CertificationActivity.this, "实名中...");
        } else {
            dialog.show();
        }
        OkHttpUtils
                .post()
                .url(NetService.API_SERVER_Url + "center/smrz2.html")
                .addHeader("Cookie", sb.toString())
                .addParams("name", name)
                .addParams("idCard", no)
                .build()
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response) throws IOException {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        Logger.e(e.toString());
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String o) {

                        Logger.json(o);
                        String info = null;
                        JSONObject sata = null;
                        try {
                            JSONObject json = new JSONObject((String) o.toString());

//                            sata = json.getJSONObject("state");
                           String s=json.getString("status");
                           info =json.getString("info");
//                            int s = sata.getInt("status");
//                            info=sata.getString("info");
                            if (s.equals("y")) {
                                SharedPreferencesUtils.setParam(CertificationActivity.this, "tPerson", true);
                                SharedPreferencesUtils.setParam(CertificationActivity.this,"realname",name);
                                T.ShowToastForShort(CertificationActivity.this, info);
                                dialog.dismiss();
                                finish();
                            }  else {
                                dialog.dismiss();
                                T.ShowToastForShort(CertificationActivity.this, info);
                            }
                        } catch (JSONException e) {
                            //  e.printStackTrace();
                            dialog.dismiss();
                            T.ShowToastForShort(CertificationActivity.this, info);
                        }

                    }
                });

    }

    //  0 是 获取   1是设置
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
                        T.ShowToastForShort(CertificationActivity.this, "网络异常");
                        Logger.e(e.toString());
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean.getState().getStatus() == 0) {
                            if (style == 0) {
                                net();
                            } else {
                                goShiMin(userName.getText().toString(), sfz.getText().toString());
                            }
                        } else {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            SharedPreferencesUtils.setIsLogin(CertificationActivity.this, false);
                        }
                    }
                }
        );
    }


    private void net() {
        NetWorks.userPerson(new Subscriber<CertificationBean>() {
            @Override
            public void onStart() {
                loadinglayout.setStatus(LoadingLayout.Loading);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.toString());
                loadinglayout.setStatus(LoadingLayout.Error);
                T.ShowToastForShort(CertificationActivity.this, "网络异常");
            }

            @Override
            public void onNext(CertificationBean s) {
                if (s.getState().getStatus() == 0 | s.getState().getStatus() == 31) {
                    String realName=s.getTPerson().getRealName();
                    String rn=realName.substring(0,1);
                    String aa = s.getTPerson().getCardNo();
                    int n = 4;

                    if (aa.length() > 4) {
                        String b = aa.substring(aa.length() - n, aa.length());
                        String s1=aa.substring(0,4);
                        sfz.setText(s1+" **** **** **** " + b);
                    } else {
                        sfz.setText("**** **** **** " + aa);
                    }
                    userName.setText(rn+"**");
                    userName.setKeyListener(null);
//                    sfz.setText(s.getTPerson().getCardNo());
                    sfz.setKeyListener(null);
                    loadinglayout.setStatus(LoadingLayout.Success);

                } else if (s.getState().getStatus() == 99) {
                    netLogin(0);
                } else {
                    T.ShowToastForShort(CertificationActivity.this, s.getState().getInfo());
                }
            }
        });
    }


}

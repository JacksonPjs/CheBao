package com.chebao.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.bean.BankListBean;
import com.chebao.bean.ChagerBean;
import com.chebao.bean.LoginBean;
import com.chebao.net.NetService;
import com.chebao.net.NetWorks;
import com.chebao.utils.DialogUtils;
import com.chebao.utils.LoginRegisterUtils;
import com.chebao.utils.SharedPreferencesUtils;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.pvj.xlibrary.loadinglayout.Utils;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import static com.chebao.utils.edncodeUtils.getCookie;

/**
 * 创建日期：2018/5/14 on 21:28
 * 描述:充值
 * 作者:jackson Administrator
 */
public class ChagerActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.t_moeny)
    EditText tMoeny;


    @Bind(R.id.calculator_go)
    Button calculatorGo;


    @Bind(R.id.layout_contiant)
    LoadingLayout layoutContiant;


    @Bind(R.id.bankpay)
    RelativeLayout bankpay;
    @Bind(R.id.bank)
    TextView bank;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chager);
        ButterKnife.bind(this);
        title.setText("充值");

        MyApplication.instance.addActivity(this);

//        String cardno = (String) SharedPreferencesUtils.getBankNUm(this);
//        bank.setText(cardno);
        netList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onPageStart(this, "充值界面");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPageEnd(this, "充值界面");
    }

    @OnClick({R.id.calculator_go})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.calculator_go:

                if (LoginRegisterUtils.isNullOrEmpty(tMoeny)) {
                    T.ShowToastForShort(ChagerActivity.this, "请输入充值金额");
                    return;
                }
                double money = Double.parseDouble(tMoeny.getText().toString());

                if (money < 100) {
                    T.ShowToastForShort(ChagerActivity.this, "最低充值:100元");
                    return;
                }

                net();
                break;
        }
    }


    private void net() {
        if (dialog == null) {
            dialog = DialogUtils.createProgressDialog(ChagerActivity.this, "充值请求中...");
        } else {
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }

        OkHttpUtils
                .post()
                .url(NetService.API_SERVER_Url + "fyPay.html")
                .addHeader("Cookie", getCookie())
                .addParams("rechargeAmount", tMoeny.getText().toString() + "")
//                .addParams("bankCardNo", carno)
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
                        Intent intent = new Intent(ChagerActivity.this, WebActivityJS.class);
                        intent.putExtra("url", "" + o);
                        startActivity(intent);
                        dialog.dismiss();
                        ChagerActivity.this.finish();
//                        try {
//                            JSONObject json = new JSONObject((String) o.toString());
//
//                            JSONObject sata = json.getJSONObject("state");
//
//                            int s = sata.getInt("status");
//                            if (s == 0) {
//                                SharedPreferencesUtils.setParam(ChagerActivity.this, "tBankCardlist", true);
//                                dialog.dismiss();
//                                finish();
//                            }  else {
//                                dialog.dismiss();
//                                T.ShowToastForShort(ChagerActivity.this, sata.getString("info"));
//                            }
//                        } catch (JSONException e) {
//                            if (dialog != null && dialog.isShowing()) {
//                                dialog.dismiss();
//                            }
//                            T.ShowToastForShort(ChagerActivity.this, "数据异常!");
//                            //   e.printStackTrace();
//                        }

                    }


                });

//        NetWorks.wxpay(payway,tMoeny.getText().toString(), new Subscriber<ChagerBean>() {
//            @Override
//            public void onStart() {
//
//                if (dialog ==null){
//                    dialog = DialogUtils.createProgressDialog(ChagerActivity.this,"充值请求中");
//                }else {
//                    dialog.show();
//                }
//
//            }
//
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                dialog.dismiss();
//                T.ShowToastForShort(ChagerActivity.this,"网络异常");
//                Logger.e(e.toString());
//            }
//
//            @Override
//            public void onNext(ChagerBean s) {
//                if (s.getState().getStatus()==66){
//                    dialog.dismiss();
//                    Intent intent = new Intent(ChagerActivity.this, WebActivityJS.class);
//                    intent.putExtra("url", s.getData());
//                    intent.putExtra("title", "快捷支付");
//                    startActivity(intent);
//                }else if(s.getState().getStatus()==99){
//                    netLogin();
//                }else if(s.getState().getStatus()==0){
//                    T.ShowToastForShort(ChagerActivity.this,"后台还未通");
//                }else {
//                    T.ShowToastForShort(ChagerActivity.this,s.getState().getInfo());
//                }
//
//            }
//        });
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
                T.ShowToastForShort(ChagerActivity.this, "网络异常");
                layoutContiant.setStatus(LoadingLayout.Error);
            }

            @Override
            public void onNext(BankListBean bean) {
                if (bean.getState().getStatus() == 0) {


                    String aa = bean.getData().get(0).getBankCardNo();
                    int n = 4;

                    if (aa.length() > 4) {
                        String b = aa.substring(aa.length() - n, aa.length());
                        if (bean.getData().get(0).getBankName()==null){
                            bank.setText(""+ "(尾号" + b + ")");

                        }else {
                            bank.setText(bean.getData().get(0).getBankName() + "(尾号" + b + ")");

                        }
                    } else {
                        if (bean.getData().get(0).getBankName()==null){
                            bank.setText(""+ "(尾号" + aa + ")");

                        }else {
                            bank.setText(bean.getData().get(0).getBankName() + "(尾号" + aa + ")");

                        }
                    }
                    layoutContiant.setStatus(LoadingLayout.Success);
                } else if (bean.getState().getStatus() == 99) {
                    netLogin();
                } else {
                    layoutContiant.setStatus(LoadingLayout.Error);
                    T.ShowToastForShort(ChagerActivity.this, bean.getState().getInfo());
                }

            }
        });
    }

    //  0 是 获取   1是设置
    private void netLogin() {

        NetWorks.login(SharedPreferencesUtils.getUserName(this),
                SharedPreferencesUtils.getPassword(this), new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }


                        Logger.json(e.toString());
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean.getState().getStatus() == 0) {
                            net();
                        } else {
                            if (dialog != null) {
                                dialog.dismiss();
                            }

                            SharedPreferencesUtils.setIsLogin(ChagerActivity.this, false);
                        }
                    }
                }
        );
    }


}
package com.chebao.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.bean.BankListBean;
import com.chebao.bean.InfoBean;
import com.chebao.bean.RansomBean;
import com.chebao.net.NetService;
import com.chebao.net.NetWorks;
import com.chebao.utils.DialogUtils;
import com.chebao.utils.LoginRegisterUtils;
import com.chebao.utils.SharedPreferencesUtils;
import com.chebao.widget.dialog.ToastDialog;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
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
 * 创建日期：2018/5/17 on 15:59
 * 描述:赎回
 * 作者:jackson Administrator
 */
public class RansomActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.t_moeny)
    EditText tMoeny;


    @Bind(R.id.calculator_go)
    Button calculatorGo;


    @Bind(R.id.layout_contiant)
    LoadingLayout layoutContiant;


    @Bind(R.id.t_shouyi)
    EditText tShouyi;
    Dialog dialog;

    double maxMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ransom);
        ButterKnife.bind(this);
        title.setText("赎回");
        netList();
        MyApplication.instance.addActivity(this);

    }

    private void netList() {
        NetWorks.didiRedeemInfo(new Subscriber<RansomBean>() {

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
                T.ShowToastForShort(RansomActivity.this, "网络异常");
                layoutContiant.setStatus(LoadingLayout.Error);
            }

            @Override
            public void onNext(RansomBean bean) {
                if (bean.getState().getStatus() == 0) {
                    maxMoney = bean.getKshed();


                    layoutContiant.setStatus(LoadingLayout.Success);
                } else {
                    layoutContiant.setStatus(LoadingLayout.Error);
                    T.ShowToastForShort(RansomActivity.this, bean.getState().getInfo());
                }

            }
        });


    }

    @OnClick({R.id.calculator_go})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.calculator_go:

                if (LoginRegisterUtils.isNullOrEmpty(tMoeny)) {
                    T.ShowToastForShort(RansomActivity.this, "请输入赎回金额");
                    return;
                }
//              double  money = Double.parseDouble(tMoeny.getText().toString());
//
//                if (money>maxMoney) {
//                    T.ShowToastForShort(RansomActivity.this, "赎回金额不能大于可赎回金额");
//                    return;
//                }
                if (LoginRegisterUtils.isNullOrEmpty(tShouyi)) {
                    T.ShowToastForShort(RansomActivity.this, "交易密码未输入");
                    return;
                }


                net(tMoeny, tShouyi);
                break;
        }
    }

    private void net(EditText tMoeny, EditText tShouyi) {
        if (dialog == null) {
            dialog = DialogUtils.createProgressDialog(RansomActivity.this, "" +
                    "赎回请求中...");
        } else {
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }

        OkHttpUtils
                .post()
                .url(NetService.API_SERVER + "applyRedeem.html")
                .addHeader("Cookie", getCookie())
                .addParams("shmoney", tMoeny.getText().toString() + "")
                .addParams("tradingPassword", tShouyi.getText().toString() + "")
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
                        ToastDialog.Builder builder=new ToastDialog.Builder(RansomActivity.this);

                        dialog.dismiss();
                        try {
                            JSONObject json = new JSONObject((String) o.toString());

                            JSONObject sata = json.getJSONObject("state");

                            String s = sata.getString("status");
                            if (s .equals("y")) {

                                builder.setMessage(""+sata.getString("info"));
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                });
//                                T.ShowToastForShort(RansomActivity.this, sata.getString("info"));
//                                dialog.dismiss();
//                                finish();
                            } else {
                                builder.setMessage(""+sata.getString("info"));
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
//                                        finish();
                                    }
                                });
//                                T.ShowToastForShort(RansomActivity.this, sata.getString("info"));
                            }
                        } catch (JSONException e) {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            builder.setMessage("数据异常");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
//                                        finish();
                                }
                            });
//                            T.ShowToastForShort(RansomActivity.this, "数据异常!");
                            //   e.printStackTrace();
                        }
                        builder.create().show();

                    }


                });
    }
}

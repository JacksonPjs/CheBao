package com.chebao.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chebao.R;
import com.chebao.bean.BorrowDetailBean;
import com.chebao.bean.DepositBean;
import com.chebao.bean.DiscountListBean;
import com.chebao.bean.InfoBean;
import com.chebao.bean.InfoMsg;
import com.chebao.bean.PayBean;
import com.chebao.net.NetService;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.login2register.LoginActivity;
import com.chebao.utils.LoginRegisterUtils;
import com.chebao.utils.P2pUtils;
import com.chebao.utils.PayWordsUtils;
import com.chebao.utils.SharedPreferencesUtils;
import com.chebao.utils.T1changerString;
import com.chebao.widget.dialog.PayDialog;
import com.chebao.widget.dialog.ToastDialog;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import static com.chebao.utils.edncodeUtils.getCookie;

/**
 * 创建日期：2018/5/19 on 11:30
 * 描述:  活期购买页
 * 作者:jackson Administrator
 */
public class DepositActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.percent)
    TextView percent;


    @Bind(R.id.tv_money)
    TextView tv_money;
    @Bind(R.id.ed_money)
    EditText editText;
    @Bind(R.id.earnings)
    TextView earnings;

    String id;
    Dialog dialogPay;
    BorrowDetailBean bean;
    boolean isSelect = false;

    String amout;
    int coupontype = -1;
    String couponId = null;
    double anbleM = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        ButterKnife.bind(this);
        init();
        net();

    }

    private void init() {
        title.setText("购买");
        editText.addTextChangedListener(new DepositActivity.EditTextChangeListener());
    }


    public class EditTextChangeListener implements TextWatcher {

        /**
         * 编辑框的内容发生改变之前的回调方法
         */
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        /**
         * 编辑框的内容正在发生改变时的回调方法 >>用户正在输入
         * 我们可以在这里实时地 通过搜索匹配用户的输入
         */
        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {
            double m = 0;
            if (s.toString() == null || s.toString().trim().length() == 0) {
                m = 0;
            } else {
                m = Double.parseDouble(s.toString());
            }


//            if (bean.getData().getDeadlineType() == 1) {
            Logger.d("bean.getData().getDeadlineType() == 1");
            earnings.setText("" + P2pUtils.calculator(m, 9.8, 1));
//            } else if (bean.getData().getDeadlineType() == 2) {
//                Logger.d("bean.getData().getDeadlineType() == 2");
//
//                earnings.setText("" + P2pUtils.calculator2(m, bean.getData().getAnnualRate(), bean.getData().getDeadline()));
//            }


        }

        /**
         * 编辑框的内容改变以后,用户没有继续输入时 的回调方法
         */
        @Override
        public void afterTextChanged(Editable editable) {

            if (isSelect) {
                if (!amout.equals(editable.toString() + "")) {
                }
            }
        }
    }


    @OnClick({R.id.fuwutiaolie, R.id.gotopay, R.id.buy})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.gotopay:
                editText.setText(anbleM + "");

                break;
            case R.id.fuwutiaolie:
                intent = new Intent(this, WebActivity.class);
                intent.putExtra("url", NetService.API_SERVER_Url + "wechat/showAgreementAPP.html");
                intent.putExtra("title", "认购协议");
                startActivity(intent);

                break;


            case R.id.buy:

                final PayDialog payDialog = new PayDialog(this);
                payDialog.setMsg(editText.getText().toString() + "");
                payDialog.show();
                payDialog.setOnTextFinishListener(new PayDialog.onEditorFinishListener() {
                    @Override
                    public void onFinish(String str) {
                        payDialog.dismiss();
//                        Toast.makeText(PayActivity.this, str, Toast.LENGTH_SHORT).show();
//                        KeyBoardUtils.hideInputForce(PayActivity.this);
                        isSelect = false;

                        double money = 0;
                        if (LoginRegisterUtils.isNullOrEmpty(editText)) {

                        } else {
                            money = Double.parseDouble(editText.getText().toString());
                        }

//                            if (money < bean.getData().getMinInvestAmount()) {
//                                T.ShowToastForShort(PayActivity.this, "最低投资:" + bean.getData().getMinInvestAmount());
//                                return;
//                            }

//                        double able = (anbleM - money);
//
//                        if (money > able) {
//                            T.ShowToastForShort(DepositActivity.this, "投资金额不能大于剩余额度.");
//                            return;
//                        }
                        netPay(str);
//

                    }
                });

                break;

        }


    }

    private void net() {

        NetWorks.didiPurchaseInfo(new Subscriber<InfoBean>() {
            @Override
            public void onCompleted() {
//                    LoodDialog = DialogUtils.createProgressDialog(PayActivity.this, "购买中...");
//                    if (!LoodDialog.isShowing()) {
//                        LoodDialog.show();
//                    }
            }

            @Override
            public void onError(Throwable e) {
//                    LoodDialog.dismiss();
            }

            @Override
            public void onNext(InfoBean o) {
//                    LoodDialog.dismiss();
//                    LoodDialog.cancel();
                if (o.getState().getStatus() == 0) {
                    anbleM = o.getUsermoney();
                    tv_money.setText(anbleM + "元");
                } else {
                    T.ShowToastForShort(DepositActivity.this, o.getState().getInfo());
                }


            }
        });


    }

    private void netPay(String p) {

//        OkHttpUtils
//                .post()
//                .url(NetService.API_SERVER_Url + "wechat/applicationForm.html")
//                .addHeader("investmoney", editText.getText().toString()+"")
//                .addParams("tradingPassword", p+ "")
////                .addParams("bankCardNo", carno)
//                .build()
//                .execute(new com.zhy.http.okhttp.callback.Callback<String>() {
//
//                    @Override
//                    public String parseNetworkResponse(com.squareup.okhttp.Response response) throws IOException {
//                        return response.body().string();
//                    }
//
//                    @Override
//                    public void onError(Request request, Exception e) {
//                    }
//
//                    @Override
//                    public void onResponse(String o) {
//                        Logger.json(o);
//
////                        try {
////                            JSONObject json = new JSONObject((String) o.toString());
////
////                            JSONObject sata = json.getJSONObject("state");
////
////                            int s = sata.getInt("status");
////                            if (s == 0) {
////                                SharedPreferencesUtils.setParam(ChagerActivity.this, "tBankCardlist", true);
////                                dialog.dismiss();
////                                finish();
////                            }  else {
////                                dialog.dismiss();
////                                T.ShowToastForShort(ChagerActivity.this, sata.getString("info"));
////                            }
////                        } catch (JSONException e) {
////                            if (dialog != null && dialog.isShowing()) {
////                                dialog.dismiss();
////                            }
////                            T.ShowToastForShort(ChagerActivity.this, "数据异常!");
////                            //   e.printStackTrace();
////                        }
//
//                    }
//
//
//                });

        NetWorks.applicationForm(editText.getText().toString(), p, new Subscriber<DepositBean>() {
            @Override
            public void onCompleted() {
//                    LoodDialog = DialogUtils.createProgressDialog(PayActivity.this, "购买中...");
//                    if (!LoodDialog.isShowing()) {
//                        LoodDialog.show();
//                    }
            }

            @Override
            public void onError(Throwable e) {
//                    LoodDialog.dismiss();
            }

            @Override
            public void onNext(DepositBean o) {
                ToastDialog.Builder builder=new ToastDialog.Builder(DepositActivity.this);
//                    LoodDialog.dismiss();
//                    LoodDialog.cancel();
                if (o.getState().getStatus().equals("y")) {
                    T.ShowToastForShort(DepositActivity.this, o.getState().getInfo());
                    builder.setMessage(""+o.getState().getInfo());
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tv_money.setText("" + SharedPreferencesUtils.getParam(DepositActivity.this, "usableAmount", "0")+"元");
                            editText.setText("");
                            dialog.dismiss();
                            finish();
                        }
                    });
                } else {
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tv_money.setText("" + SharedPreferencesUtils.getParam(DepositActivity.this, "usableAmount", "0")+"元");
                            editText.setText("");
                            dialog.dismiss();
                            finish();
                        }
                    });
                    builder.setMessage(""+o.getState().getInfo());
//                        T.ShowToastForShort(PayActivity.this, o.getInfo());
                }
                builder.create().show();



            }
        });


    }


}
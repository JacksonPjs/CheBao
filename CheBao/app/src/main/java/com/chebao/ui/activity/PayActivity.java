package com.chebao.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.bean.BorrowDetailBean;
import com.chebao.net.NetService;
import com.chebao.utils.DialogUtils;
import com.chebao.utils.LoginRegisterUtils;
import com.chebao.utils.P2pUtils;
import com.chebao.utils.PayWordsUtils;
import com.chebao.utils.SharedPreferencesUtils;
import com.chebao.utils.T1changerString;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by Administrator on 2017/3/16.
 */

public class PayActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.restext)
    TextView restext;
    @Bind(R.id.percent)
    TextView percent;
    @Bind(R.id.maxAmount)
    TextView maxAmount;
    @Bind(R.id.data)
    TextView data;
    @Bind(R.id.tv_money)
    TextView tv_money;
    @Bind(R.id.ed_money)
    EditText editText;
    @Bind(R.id.earnings)
    TextView earnings;
    Dialog LoodDialog;

    String id;
    String result = "";
    Dialog dialogPay;
    String word;
    String test;
    BorrowDetailBean bean;
    boolean isSelect=false;

    String amout;
    int borrowtype=-1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        Bundle bundle = getIntent().getExtras();
        bean= (BorrowDetailBean) bundle.getSerializable("bean");
        borrowtype=bean.getData().getBorrowType();
        init();
    }

    private void init() {
        title.setText("支付确认");
        editText.addTextChangedListener(new EditTextChangeListener());
        setData();

    }
 private void setData() {
        if (bean!=null){
            DecimalFormat df = new DecimalFormat("######0.00");
            BorrowDetailBean.DataBean d = bean.getData();
            percent.setText(df.format(d.getAnnualRate()) + "%");
            String deadliness = T1changerString.t2chager(d.getDeadline(), d.getDeadlineType());
            data.setText(deadliness);
            maxAmount.setText("剩余额度：" + T1changerString.t3chager(d.getBorrowAmount() - d.getHasBorrowAmount()));
            if ((Boolean) SharedPreferencesUtils.getParam(this, "islogin", false)) {  //登陆
                tv_money.setText("" + (String) SharedPreferencesUtils.getParam(this, "usableAmount", "0")+"元");

            } else {
            }

        }

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


            if (bean.getData().getDeadlineType() == 1) {
                Logger.d("bean.getData().getDeadlineType() == 1");
                earnings.setText("" + P2pUtils.calculator(m, bean.getData().getAnnualRate(), bean.getData().getDeadline()));
            } else if (bean.getData().getDeadlineType() == 2) {
                Logger.d("bean.getData().getDeadlineType() == 2");

                earnings.setText("" + P2pUtils.calculator2(m, bean.getData().getAnnualRate(), bean.getData().getDeadline()));
            }


        }

        /**
         * 编辑框的内容改变以后,用户没有继续输入时 的回调方法
         */
        @Override
        public void afterTextChanged(Editable editable) {

            if (isSelect){
                if (!amout.equals(editable.toString()+"")){
                    showDialog();
                }
            }
        }
    }
    private void showDialog() {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("").setIcon(android.R.drawable.stat_notify_error);
        builder.setMessage("修改金额，已选优惠券将无效");
        builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                result="";
                isSelect=false;
                restext.setText("请选择优惠券");

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editText.setText(amout);
                editText.setSelection(editText.getText().length());//光标
            }
        });
        dialog = builder.create();
        dialog.show();
    }
    @OnClick({R.id.fuwutiaolie, R.id.gotopay, R.id.rl_discount, R.id.buy})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.fuwutiaolie:
                intent = new Intent(this, WebActivity.class);
                intent.putExtra("url", NetService.API_SERVER_Url + "wechat/showAgreementAPP.html");
                intent.putExtra("title", "认购协议");
                startActivity(intent);

                break;

            case R.id.rl_discount:
                if (!editText.getText().toString().isEmpty()) {
                    amout = editText.getText().toString() + "";
                    int money = Double.valueOf(amout).intValue();

                }
                break;
            case R.id.buy:
                if (dialogPay == null) {
                    PayWordsUtils payWordsUtils = new PayWordsUtils();
                    dialogPay = payWordsUtils.createProgressDialog(this);
                    payWordsUtils.setPaylistenr(new PayWordsUtils.Paylistenr() {
                        @Override
                        public void onOk(String bank) {
                            //   T.ShowToastForShort(MouthMouthAcitivity.this,bank);
                            isSelect=false;
                            restext.setText("请选择优惠券");
                            word = bank;


                            double money = 0;
                            if (LoginRegisterUtils.isNullOrEmpty(editText)) {

                            } else {
                                money = Double.parseDouble(editText.getText().toString());
                            }

                            if (money < bean.getData().getMinInvestAmount()) {
                                T.ShowToastForShort(PayActivity.this, "最低投资:" + bean.getData().getMinInvestAmount());
                                return;
                            }

                            double anbleM = Double.parseDouble((String) SharedPreferencesUtils.getParam(PayActivity.this, "usableAmount", "0"));
                            if (money > anbleM) {
                                T.ShowToastForShort(PayActivity.this, "可用余额少于投资金额.请先充值");
                                return;
                            }

                            double able = (bean.getData().getBorrowAmount() - bean.getData().getHasBorrowAmount());

                            if (money > able) {
                                T.ShowToastForShort(PayActivity.this, "投资金额不能大于剩余额度.");
                                return;
                            }
                            netPay(bank);
                        }
                    });
                } else {
                    dialogPay.show();
                }

                break;

        }


    }

    private void netPay(String p) {
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
        if (LoodDialog == null) {
            LoodDialog = DialogUtils.createProgressDialog(PayActivity.this, "购买中...");
        } else {
            if (!LoodDialog.isShowing()) {
                LoodDialog.show();
            }
        }

        OkHttpUtils
                .post()
                .url(NetService.API_SERVER_Url+"bfpay/investAjaxBorrow.html")
                .addHeader("Cookie", encodeHeadInfo(sb.toString()))
                .addParams("tradingPassword", p)//交易密码
                .addParams("investAmount", editText.getText().toString())//投资金额
                .addParams("borrowId", id)//产品id
//                .addParams("couponId", result)//使用现金券json
                .build()
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response) throws IOException {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        LoodDialog.dismiss();
                    }

//                    @Override
//                    public void onResponse(String response) {
//
//                    }

                    @Override
                    public void onResponse(String o) {
                        Logger.json(o);
                        LoodDialog.dismiss();



                    }


                });
    }

    private static String encodeHeadInfo(String headInfo) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0, length = headInfo.length(); i < length; i++) {
            char c = headInfo.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                stringBuffer.append(String.format("\\u%04x", (int) c));
            } else {
                stringBuffer.append(c);
            }
        }
        return stringBuffer.toString();
    }
}

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
import android.widget.Toast;


import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.bean.BorrowDetailBean;
import com.chebao.bean.DiscountListBean;
import com.chebao.bean.InfoMsg;
import com.chebao.bean.PayBean;
import com.chebao.net.NetService;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.login2register.LoginActivity;
import com.chebao.utils.DialogUtils;
import com.chebao.utils.KeyBoardUtils;
import com.chebao.utils.LoginRegisterUtils;
import com.chebao.utils.P2pUtils;
import com.chebao.utils.PayWordsUtils;
import com.chebao.utils.SharedPreferencesUtils;
import com.chebao.utils.T1changerString;
import com.chebao.widget.dialog.PayDialog;
import com.chebao.widget.dialog.ToastDialog;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import static com.chebao.utils.edncodeUtils.getCookie;

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
    @Bind(R.id.yonghu)
    TextView yonghu;
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
    boolean isSelect = false;

    String amout;
    int borrowtype = -1;
    DiscountListBean.DisData disData;
    int coupontype = -1;
    String couponId = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        Bundle bundle = getIntent().getExtras();
        bean = (BorrowDetailBean) bundle.getSerializable("bean");
        borrowtype = bean.getData().getBorrowType();
        init();
    }

    private void init() {
        title.setText("支付确认");
        editText.addTextChangedListener(new EditTextChangeListener());
        setData();

    }

    private void setData() {
        if (bean != null) {
            DecimalFormat df = new DecimalFormat("######0.00");
            BorrowDetailBean.DataBean d = bean.getData();
            percent.setText(df.format(d.getAnnualRate()) + "%");
            String deadliness = T1changerString.t2chager(d.getDeadline(), d.getDeadlineType());
            data.setText(deadliness);
            yonghu.setText(bean.getData().getBorrowTitle()+"");
            maxAmount.setText("剩余额度：" + T1changerString.t3chager(d.getBorrowAmount() - d.getHasBorrowAmount()));
            if ((Boolean) SharedPreferencesUtils.getParam(this, "islogin", false)) {  //登录
                tv_money.setText("" + (String) SharedPreferencesUtils.getParam(this, "usableAmount", "0") );

            } else {
                Intent intent = new Intent(PayActivity.this, LoginActivity.class);
                startActivity(intent);
                T.ShowToastForLong(PayActivity.this, "未登录");

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

            if (isSelect) {
                if (!amout.equals(editable.toString() + "")) {
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
                result = "";
                isSelect = false;
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
        Intent intent = null;
        switch (view.getId()) {
            case R.id.gotopay:
                editText.setText(tv_money.getText().toString()+"");

                break;
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
                    intent = new Intent(this, PayDiscountActivity.class);
                    intent.putExtra("couponAmount", "" + money);
                    intent.putExtra("Deadline", bean.getData().getDeadline() + "");
                    intent.putExtra("DeadlineType", "" + bean.getData().getDeadlineType());
                    startActivityForResult(intent, 1);

                } else {
                    T.ShowToastForLong(PayActivity.this, "请输入投资金额");

                }
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
                        restext.setText("请选择优惠券");
                        word = str;


                        double money = 0;
                        if (LoginRegisterUtils.isNullOrEmpty(editText)) {

                        } else {
                            money = Double.parseDouble(editText.getText().toString());
                        }

//                            if (money < bean.getData().getMinInvestAmount()) {
//                                T.ShowToastForShort(PayActivity.this, "最低投资:" + bean.getData().getMinInvestAmount());
//                                return;
//                            }

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
                        netPay(str);
//                        //动态关软键盘
//                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
//                        if (imm.isActive()) {//如果开启
//                            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
//                        }

                    }
                });

//                if (dialogPay == null) {
//                    PayWordsUtils payWordsUtils = new PayWordsUtils();
//                    dialogPay = payWordsUtils.createProgressDialog(this);
//                    payWordsUtils.setPaylistenr(new PayWordsUtils.Paylistenr() {
//                        @Override
//                        public void onOk(String bank) {
//                            //   T.ShowToastForShort(MouthMouthAcitivity.this,bank);
//                            isSelect = false;
//                            restext.setText("请选择优惠券");
//                            word = bank;
//
//
//                            double money = 0;
//                            if (LoginRegisterUtils.isNullOrEmpty(editText)) {
//
//                            } else {
//                                money = Double.parseDouble(editText.getText().toString());
//                            }
//
////                            if (money < bean.getData().getMinInvestAmount()) {
////                                T.ShowToastForShort(PayActivity.this, "最低投资:" + bean.getData().getMinInvestAmount());
////                                return;
////                            }
//
//                            double anbleM = Double.parseDouble((String) SharedPreferencesUtils.getParam(PayActivity.this, "usableAmount", "0"));
//                            if (money > anbleM) {
//                                T.ShowToastForShort(PayActivity.this, "可用余额少于投资金额.请先充值");
//                                return;
//                            }
//
//                            double able = (bean.getData().getBorrowAmount() - bean.getData().getHasBorrowAmount());
//
//                            if (money > able) {
//                                T.ShowToastForShort(PayActivity.this, "投资金额不能大于剩余额度.");
//                                return;
//                            }
//                            netPay(bank);
//                        }
//                    });
//                } else {
//                    dialogPay.show();
//                }

                break;

        }


    }

    private void netPay(String p) {
        if (coupontype == -1) {
            NetWorks.investAjaxBorrow2(getCookie(), p, editText.getText().toString(), id, new Subscriber<PayBean>() {
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
                public void onNext(PayBean o) {
//                    LoodDialog.dismiss();
//                    LoodDialog.cancel();
                    ToastDialog.Builder builder=new ToastDialog.Builder(PayActivity.this);

                    if (o.getStatus().equals("y")) {
                        SharedPreferencesUtils.setParam(PayActivity.this, "usableAmount",o.getUsableAmount()+"");

//                        T.ShowToastForShort(PayActivity.this, o.getState().getInfo());
                        builder.setMessage(""+o.getInfo());
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv_money.setText("" + SharedPreferencesUtils.getParam(PayActivity.this, "usableAmount", "0"));
                                editText.setText("");
                                dialog.dismiss();
                                finish();

                            }
                        });
                    } else {
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv_money.setText("" + SharedPreferencesUtils.getParam(PayActivity.this, "usableAmount", "0"));
                                editText.setText("");
                                dialog.dismiss();
                            }
                        });
                        builder.setMessage(""+o.getInfo());
//                        T.ShowToastForShort(PayActivity.this, o.getInfo());
                    }
                    builder.create().show();



                }
            });
        } else {
            NetWorks.investAjaxBorrow(getCookie(), p, editText.getText().toString(), id, coupontype, couponId + "", new Subscriber<PayBean>() {
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
                public void onNext(PayBean o) {
//                    LoodDialog.dismiss();
//                    LoodDialog.cancel();
                    ToastDialog.Builder builder=new ToastDialog.Builder(PayActivity.this);
                    if (o.getStatus().equals("y")) {
                        SharedPreferencesUtils.setParam(PayActivity.this, "usableAmount",o.getUsableAmount()+"");

                        T.ShowToastForShort(PayActivity.this, o.getState().getInfo());

                        builder.setMessage("投标成功");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv_money.setText("" + SharedPreferencesUtils.getParam(PayActivity.this, "usableAmount", "0"));
                                editText.setText("");
                                dialog.dismiss();
                                finish();

                            }
                        });

                    } else {
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv_money.setText("" + SharedPreferencesUtils.getParam(PayActivity.this, "usableAmount", "0"));
                                editText.setText("");
                                dialog.dismiss();
                            }
                        });
                        builder.setMessage(""+o.getInfo());
//                        T.ShowToastForShort(PayActivity.this, o.getInfo());
                    }

                    builder.create().show();
                }
            });
        }


//
//        StringBuilder sb = new StringBuilder();
//        sb.append(" _ed_token_");
//        sb.append("=");
//        sb.append((String) SharedPreferencesUtils.getParam(MyApplication.context, "token", ""));
//        sb.append(";");
//
//        sb.append(" _ed_username_");
//        sb.append("=");
//        sb.append((String) SharedPreferencesUtils.getParam(MyApplication.context, "name", ""));
//        sb.append(";");
//
//        sb.append(" _ed_cellphone_");
//        sb.append("=");
//        sb.append((String) SharedPreferencesUtils.getParam(MyApplication.context, "phone", ""));
//        sb.append(";");
//        if (LoodDialog == null) {
//            LoodDialog = DialogUtils.createProgressDialog(PayActivity.this, "购买中...");
//        } else {
//            if (!LoodDialog.isShowing()) {
//                LoodDialog.show();
//            }
//        }
//        OkHttpUtils
//                .post()
//                .url(NetService.API_SERVER + "bfpay/investAjaxBorrow.html")
//                .addHeader("Cookie", encodeHeadInfo(sb.toString()))
//                .addParams("tradingPassword", p)//交易密码
//                .addParams("investAmount", editText.getText().toString())//投资金额
//                .addParams("borrowId", id)//产品id
//                .addParams("coupontype", coupontype + "")
//                .addParams("couponId", couponId + "")
////                .addParams("couponId", result)//使用现金券json
//                .build()
//                .execute(new Callback<String>() {
//
//                    @Override
//                    public String parseNetworkResponse(Response response) throws IOException {
//                        return response.body().string();
//                    }
//
//                    @Override
//                    public void onError(Request request, Exception e) {
//                        LoodDialog.dismiss();
//                    }
//
////                    @Override
////                    public void onResponse(String response) {
////
////                    }
//
//                    @Override
//                    public void onResponse(String o) {
//                        Logger.json(o);
//                        LoodDialog.dismiss();
//
//
//                    }
//
//
//                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 200) {
                disData = (DiscountListBean.DisData) data.getSerializableExtra("disbean");
                couponId = disData.getId() + "";
                coupontype = disData.getCouponType();
                restext.setText(disData.getCouponAmount() + "元卡券");
                restext.setTextColor(getResources().getColor(R.color.status4));

            } else {
                coupontype=-1;
                restext.setText("暂无卡券");
            }
        }
    }
}

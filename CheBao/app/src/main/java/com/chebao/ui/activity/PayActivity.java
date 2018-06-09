package com.chebao.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.chebao.App.Constant;
import com.chebao.MainActivity;
import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.bean.BorrowDetailBean;
import com.chebao.bean.DiscountListBean;
import com.chebao.bean.InfoBean;
import com.chebao.bean.InfoMsg;
import com.chebao.bean.PayBean;
import com.chebao.net.NetService;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.login2register.LoginActivity;
import com.chebao.ui.activity.security.AddBankActivity;
import com.chebao.utils.DialogUtils;
import com.chebao.utils.IntentUtils;
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
    @Bind(R.id.cbox)
    CheckBox checkBox;

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

    boolean isCheck = false;
    int STATUS_INTENT_TIME = 1000;//默认跳转等待时间
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        activity=this;
        id = getIntent().getStringExtra("id");
        Bundle bundle = getIntent().getExtras();
        bean = (BorrowDetailBean) bundle.getSerializable("bean");
        borrowtype = bean.getData().getBorrowType();
        init();
        net();
    }

    private void init() {
        title.setText("支付确认");
        editText.addTextChangedListener(new EditTextChangeListener());
        setData();

    }

    private void setData() {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheck = isChecked;
            }
        });
        if (bean != null) {
            DecimalFormat df = new DecimalFormat("######0.00");
            BorrowDetailBean.DataBean d = bean.getData();
            if (d.getBorrowType()==5){
                percent.setText(df.format(d.getAnnualRate()-3)+ "%+3%");

            }else if (d.getBorrowType()==1){
                percent.setText(df.format(d.getAnnualRate()-1) + "%+1%");

            }
            String deadliness = T1changerString.t2chager(d.getDeadline(), d.getDeadlineType());
            data.setText("出借期限:"+deadliness);
            yonghu.setText(bean.getData().getBorrowTitle() + "");
            maxAmount.setText("剩余额度：" + T1changerString.t3chager(d.getBorrowAmount() - d.getHasBorrowAmount()));
//            if ((Boolean) SharedPreferencesUtils.getParam(this, "islogin", false)) {  //登录
//                tv_money.setText("" + (String) SharedPreferencesUtils.getParam(this, "usableAmount", "0"));
//
//            } else {
//                Intent intent = new Intent(PayActivity.this, LoginActivity.class);
//                startActivity(intent);
//                T.ShowToastForLong(PayActivity.this, "未登录");
//
//            }

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
                   double anbleM = o.getUsermoney();
                    tv_money.setText(anbleM +"");
                } else if (o.getState().getStatus()==99){
                    T.ShowToastForShort(activity, o.getState().getInfo());
                   Intent intent = new Intent(activity, LoginActivity.class);
                    startActivity(intent);
                }else {
                    T.ShowToastForShort(activity, o.getState().getInfo());
                }


            }
        });


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
        builder.setMessage("修改金额，已选卡券将无效");
        builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                result = "";
                isSelect = false;
                coupontype = -1;
                restext.setText("请选择卡券");

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
                editText.setText(tv_money.getText().toString() + "");

                break;
            case R.id.fuwutiaolie:
                intent = new Intent(this, WebNoTitileActivity.class);
                intent.putExtra("url", NetService.API_SERVER_Url + "wechat/showAgreementAPP.html");
//                intent.putExtra("title", "认购协议");
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
                    T.ShowToastForLong(PayActivity.this, "请输入出借金额");

                }
                break;
            case R.id.buy:
                double money = 0;
                if (LoginRegisterUtils.isNullOrEmpty(editText)) {
                    T.ShowToastForShort(PayActivity.this, "请输入出借金额");

                } else {
                    money = Double.parseDouble(editText.getText().toString());
                }

                if (money < bean.getData().getMinInvestAmount()) {
                    T.ShowToastForShort(PayActivity.this, "最低出借:" + bean.getData().getMinInvestAmount());
                    return;
                }
//                if (!(Boolean) SharedPreferencesUtils.getParam(this, "payPwd", false)) {
//                T.ShowToastForShort(activity, "请先设置交易密码");
//                IntentUtils.ToastIntent(activity, Constant.PAY_NO_PAYPSW);
//                return;
//            }
//                if (!SharedPreferencesUtils.getIsBank(activity)) {
//                    T.ShowToastForShort(activity, "交易需要先绑定银行卡");
//                    IntentUtils.ToastIntent(activity,Constant.PAY_NO_BANK);
//                    return;
//
//                }
                double anbleM = Double.parseDouble((String) SharedPreferencesUtils.getParam(PayActivity.this, "usableAmount", "0"));
                if (money > anbleM) {
                    T.ShowToastForShort(PayActivity.this, "可用余额少于出借金额.请先充值");
                    IntentUtils.ToastIntent(activity,Constant.PAY_NO_MONEY);
                    return;
                }

                double able = (bean.getData().getBorrowAmount() - bean.getData().getHasBorrowAmount());

                if (money > able) {
                    T.ShowToastForShort(PayActivity.this, "出借金额不能大于剩余额度.");
                    return;
                }
                if (!isCheck) {
                    T.ShowToastForShort(this, "尚未阅读或同意《车宝金融注册服务协议》");
                    return;
                }

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


                        netPay(str);

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
                public void onNext(final PayBean o) {
//                    LoodDialog.dismiss();
//                    LoodDialog.cancel();
                    ToastDialog.Builder builder = new ToastDialog.Builder(PayActivity.this);

                    if (o.getState().getStatus().equals("0")) {
                        SharedPreferencesUtils.setParam(PayActivity.this, "usableAmount", o.getUsableAmount() + "");

//                        T.ShowToastForShort(PayActivity.this, o.getState().getInfo());
                        builder.setMessage("" + o.getInfo());
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

                                switch (o.getState().getStatus()) {
                                    case Constant.PAY_NO_PAYPSW:
                                        IntentUtils.ToastIntent(activity,Constant.PAY_NO_PAYPSW);

                                        break;
                                    case Constant.PAY_NO_SHIMING:
                                        IntentUtils.ToastIntent(activity,Constant.PAY_NO_SHIMING);
                                        break;
                                    case Constant.PAY_NO_MONEY:

                                        IntentUtils.ToastIntent(activity,Constant.PAY_NO_MONEY);
                                        break;



                                }

                            }
                        });
                        builder.setMessage("" + o.getState().getInfo());
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
                public void onNext(final PayBean o) {
//                    LoodDialog.dismiss();
//                    LoodDialog.cancel();
                    ToastDialog.Builder builder = new ToastDialog.Builder(PayActivity.this);
                    if (o.getState().getStatus().equals("0")) {
                        SharedPreferencesUtils.setParam(PayActivity.this, "usableAmount", o.getUsableAmount() + "");

                        T.ShowToastForShort(PayActivity.this, o.getState().getInfo());

                        builder.setMessage("出借成功");
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
                                switch (o.getState().getStatus()) {
                                    case Constant.PAY_NO_PAYPSW:
                                        IntentUtils.ToastIntent(activity,Constant.PAY_NO_PAYPSW);

                                        break;
                                    case Constant.PAY_NO_SHIMING:
                                        IntentUtils.ToastIntent(activity,Constant.PAY_NO_SHIMING);
                                        break;
                                    case Constant.PAY_NO_MONEY:
                                        if (SharedPreferencesUtils.getIsBank(activity)) {
                                            IntentUtils.ToastIntent(activity,Constant.PAY_NO_BANK);

                                        }else
                                            IntentUtils.ToastIntent(activity,Constant.PAY_NO_MONEY);
                                        break;



                                }
                            }
                        });
                        builder.setMessage("" + o.getState().getInfo());
                    }

                    builder.create().show();
                }
            });
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 200) {
                isSelect = true;
                disData = (DiscountListBean.DisData) data.getSerializableExtra("disbean");
                couponId = disData.getId() + "";
                coupontype = disData.getCouponType();
                restext.setText(disData.getCouponAmount() + "元卡券");
                restext.setTextColor(getResources().getColor(R.color.status4));

            } else {
                coupontype = -1;
                restext.setText("暂无卡券");
            }
        }
    }
}

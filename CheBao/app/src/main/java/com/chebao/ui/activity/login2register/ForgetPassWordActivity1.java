package com.chebao.ui.activity.login2register;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chebao.R;
import com.chebao.bean.ForgetPassBean;
import com.chebao.bean.InfoBean;
import com.chebao.geetest_sdk.SdkUtils2;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.BaseActivity;
import com.chebao.utils.DialogUtils;
import com.chebao.utils.LoginRegisterUtils;
import com.chebao.utils.TimeUtils;
import com.chebao.utils.onclick.AntiShake;
import com.pvj.xlibrary.utils.CountDownButtonHelper;
import com.pvj.xlibrary.utils.T;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/*
 * 忘记密码
 * */

public class ForgetPassWordActivity1 extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.login_phone)
    EditText loginPhone;
    @Bind(R.id.yzm)
    EditText yzm;
    @Bind(R.id.login_password)
    EditText loginPassword;
    @Bind(R.id.login_go)
    Button loginGo;
    @Bind(R.id.getcode)
    TextView getcode;
    SdkUtils2 sdkUtils;

    Dialog dialog;

    private CountDownButtonHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_word1);
        ButterKnife.bind(this);

        initView();


    }

    private void initView() {
        title.setText("找回密码");
    }


    @OnClick({R.id.getcode, R.id.login_go})
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        switch (view.getId()) {
            case R.id.getcode:
                TimeUtils.setCountDownTimerListener(new TimeUtils.CountDownTimerlistener() {
                    @Override
                    public void onTick(String time) {
                        getcode.setEnabled(false);
                        getcode.setText(time);
                    }

                    @Override
                    public void onFinish() {
                        getcode.setEnabled(true);
                        getcode.setText(getResources().getString(R.string.done));
                        TimeUtils.timerCancel();
                    }
                });


                verificationPhone(ForgetPassWordActivity1.this, ForgetPassWordActivity1.this, loginPhone);


                break;
            case R.id.login_go:

//                if (LoginRegisterUtils.isNullOrEmpty(loginPhone)) {
//                    T.ShowToastForShort(this, "手机号码未输入");
//                    return;
//                }
//
//                if (!LoginRegisterUtils.isPhone(loginPhone)) {
//                    T.ShowToastForShort(this, "手机号码不正确");
//                    return;
//                }

                if (LoginRegisterUtils.isNullOrEmpty(loginPassword)) {
                    T.ShowToastForShort(this, "用户密码未输入");
                    return;
                }

                if (!LoginRegisterUtils.isPassWord(loginPassword)) {
                    T.ShowToastForShort(this, "用户密码格式不合法");
                    return;
                }


//                if (!LoginRegisterUtils.equals(loginPassword, password2)) {
//                    T.ShowToastForShort(this, "二次手机密码不一致");
//                    return;
//                }

                if (LoginRegisterUtils.isNullOrEmpty(yzm)) {
                    T.ShowToastForShort(this, "手机验证未输入");
                    return;
                }
//                reFormforGetPassCode(ForgetPassWordActivity1.this, ForgetPassWordActivity1.this, loginPhone, yzm);


                net(loginPhone.getText().toString(), yzm.getText().toString(), loginPassword.getText().toString());
                break;
        }
    }


    /**
     * 验证手机号码
     *
     * @param
     */
    public void verificationPhone(final Context context, final Activity activity, final EditText phoneEdit) {
        //   this.noncestr = UUIDs.uuid();

        if (LoginRegisterUtils.isNullOrEmpty(phoneEdit)) {
            T.ShowToastForShort(this, "手机号码未输入");
            return;
        }

        if (!LoginRegisterUtils.isPhone(phoneEdit)) {
            T.ShowToastForShort(this, "手机号码不正确");
            return;
        }


        NetWorks.forGetPassPhone(phoneEdit.getText().toString(), new Subscriber<InfoBean>() {
            @Override
            public void onStart() {
                if (!activity.isFinishing()) {
                    if (dialog == null) {
                        dialog = DialogUtils.createProgressDialog(activity, "请求中");
                    } else {
                        dialog.show();
                    }
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e)

            {
                dialog.dismiss();
                T.ShowToastForShort(activity, "网络异常");
            }

            @Override
            public void onNext(InfoBean s) {

                if (s.getState().getStatus() == 0) {
                    //设置不可编辑
                    phoneEdit.setFocusable(false);
                    phoneEdit.setFocusableInTouchMode(false);
                    TimeUtils.timerStart();
                    getPhoneCode(context, activity, phoneEdit);
                } else {
                    dialog.dismiss();

                    T.ShowToastForShort(activity, s.getState().getInfo());
                }

            }
        });
    }

    /**
     * 请求发送验证码
     *
     * @param
     */
    public void getPhoneCode(final Context context, final Activity activity, final EditText phoneEdit) {
        //   this.noncestr = UUIDs.uuid();


        NetWorks.getPhoneCode(phoneEdit.getText().toString(), new Subscriber<ForgetPassBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e)

            {
                dialog.dismiss();
                T.ShowToastForShort(activity, "网络异常");
            }

            @Override
            public void onNext(ForgetPassBean s) {

                if (s.getState().getStatus() .equals("y")) {
                    dialog.dismiss();

                } else {
                    dialog.dismiss();
                    T.ShowToastForShort(activity, s.getState().getInfo());
                }

            }
        });
    }



    private void net(String phone, String telcode, String pass) {
        NetWorks.updateforGetPass(phone, telcode, pass, new Subscriber<InfoBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onNext(InfoBean b) {
                if (b.getState().getStatus() == 0) {
                    T.ShowToastForShort(ForgetPassWordActivity1.this, b.getState().getInfo());
                    finish();
                } else {
                    T.ShowToastForShort(ForgetPassWordActivity1.this, b.getState().getInfo());
                }

            }
        });
    }


}

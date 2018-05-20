package com.chebao.ui.activity.login2register;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chebao.MainActivity;
import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.bean.LoginBean;
import com.chebao.ui.activity.BaseActivity;
import com.chebao.bean.InfoBean;
import com.chebao.net.NetWorks;
import com.chebao.utils.DialogUtils;
import com.chebao.utils.LoginRegisterUtils;
import com.chebao.utils.SharedPreferencesUtils;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
//import rx.Subscriber;

public class LoginActivity extends BaseActivity {
    @Bind(R.id.login_phone)
    EditText loginPhone;
    @Bind(R.id.psw_phone)
    EditText loginPassword;
    @Bind(R.id.regist)
    TextView regist;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        MyApplication.instance.addActivity(this);
    }

    @OnClick({R.id.login_go, R.id.regist, R.id.forget, R.id.back})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.regist:
                //注册
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.forget:
                //忘记密码
                intent = new Intent(this, ForgetPassWordActivity1.class);
                startActivity(intent);

                break;


            case R.id.login_go:
                //登录
                if (LoginRegisterUtils.isNullOrEmpty(loginPhone)) {
                    T.ShowToastForShort(this, "手机号码未输入");
                    return;
                }

                if (!LoginRegisterUtils.isPhone(loginPhone)) {
                    T.ShowToastForShort(this, "手机号码不正确");
                    return;
                }
                if (LoginRegisterUtils.isNullOrEmpty(loginPassword)) {
                    T.ShowToastForShort(this, "用户密码未输入");
                    return;
                }

                if (!LoginRegisterUtils.isPassWord(loginPassword)) {
                    T.ShowToastForShort(this, "用户密码格式不合法");
                    return;
                }
//
//                intent = new Intent(this, MainActivity.class);
//                startActivity(intent);

//                IsRegister(loginPhone.getText().toString());

                login(loginPhone.getText().toString(), loginPassword.getText().toString());
                break;
            case R.id.back:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;


        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    public void login(String name, final String passoword) {
        NetWorks.login(name, passoword, new Subscriber<LoginBean>() {
            @Override
            public void onStart() {
                if (dialog == null) {
                    dialog = DialogUtils.createProgressDialog(LoginActivity.this, "登录中...");
                } else {
                    dialog.show();
                }
            }

            @Override
            public void onCompleted() {
                dialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                T.ShowToastForLong(LoginActivity.this, "网络异常");
                dialog.dismiss();
                Logger.e(e.toString());
            }

            @Override
            public void onNext(LoginBean s) {
                if (s.getState().getStatus() == 0) {

                    SharedPreferencesUtils.savaUser(LoginActivity.this, s, passoword);
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    T.ShowToastForLong(LoginActivity.this, "登录成功");
                } else {
                    T.ShowToastForLong(LoginActivity.this, s.getState().getInfo());
                }


            }
        });
    }


}


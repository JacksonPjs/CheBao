package com.chebao.ui.activity.security;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chebao.R;
import com.chebao.ui.activity.BaseActivity;
import com.chebao.utils.LoginRegisterUtils;
import com.chebao.utils.SharedPreferencesUtils;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.CountDownButtonHelper;
import com.pvj.xlibrary.utils.T;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;


/**
 *  修改支付密码
 */
public class ChangerPayPassWordActivity extends BaseActivity  {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.login_phone)
    EditText loginPhone;
    @Bind(R.id.yzm)
    EditText yzm;
    @Bind(R.id.login_password)
    EditText loginPassword;
//    @Bind(R.id.password2)
//    EditText password2;
    @Bind(R.id.login_go)
    Button loginGo;
    @Bind(R.id.getcode)
    TextView getcode;

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
        title.setText("修改支付密码");
        loginGo.setText("修改");


        loginPhone.setText(SharedPreferencesUtils.getUserName(this));
        loginPhone.setEnabled(false);
    }


    @OnClick({R.id.getcode, R.id.login_go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getcode:
//                sdkUtils.verificationNewUserPhone(ChangerPayPassWordActivity.this, ChangerPayPassWordActivity.this, loginPhone);
                break;
            case R.id.login_go:

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
//                if (!LoginRegisterUtils.equals(loginPassword, password2)) {
//                    T.ShowToastForShort(this, "二次手机密码不一致");
//                    return;
//                }

                if (LoginRegisterUtils.isNullOrEmpty(yzm)) {
                    T.ShowToastForShort(this, "手机验证未输入");
                    return;
                }


//                net(loginPhone.getText().toString(), yzm.getText().toString(), loginPassword.getText().toString());

                break;
        }
    }




}

package com.chebao.ui.activity.login2register;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chebao.MainActivity;
import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.ui.activity.BaseActivity;
import com.chebao.bean.InfoBean;
import com.chebao.net.NetWorks;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
//import rx.Subscriber;

public class LoginActivity extends BaseActivity {
    @Bind(R.id.login_phone)
    EditText loginPhone;
    @Bind(R.id.title)
    TextView title;

    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        MyApplication.instance.addActivity(this);
        title.setText("登录");
    }

    @OnClick({R.id.login_go})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
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



                intent=new Intent(this, MainActivity.class);
                startActivity(intent);

//                IsRegister(loginPhone.getText().toString());


                break;


        }
    }


    public void IsRegister(String num){
        NetWorks.verificationNewUserPhone(num, new Subscriber<InfoBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(InfoBean infoBean) {

            }
        });
    }



}


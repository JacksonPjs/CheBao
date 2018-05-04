package com.chebao.ui.activity.security;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chebao.R;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.BaseActivity;
import com.chebao.utils.LoginRegisterUtils;
import com.chebao.utils.SharedPreferencesUtils;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * 设置支付密码
 * Created by pvj on 2016/12/27.
 */

public class SetPayPasswordActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.fword)
    EditText fword;
    @Bind(R.id.firt_word)
    LinearLayout firtWord;
    @Bind(R.id.tword)
    EditText tword;
    @Bind(R.id.sword)
    EditText sword;
    @Bind(R.id.chagerloginpwd_go)
    Button chagerloginpwdGo;

    Dialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeoginpassword);
        ButterKnife.bind(this);

        if((Boolean) SharedPreferencesUtils.getParam(this,"payPwd",false)){
            title.setText("修改交易密码");
        }else{
            title.setText("设置交易密码");
            firtWord.setVisibility(View.GONE);
        }
        title.setText("设置交易密码");
    }

    @OnClick(R.id.chagerloginpwd_go)
    public void onClick() {
        if(LoginRegisterUtils.isNullOrEmpty(tword)){
            T.ShowToastForShort(this,"密码不能为空");
            return;
        }

        if(!LoginRegisterUtils.equals(tword,sword)){
            T.ShowToastForShort(this,"二次密码不一致");
            return;
        }

    }




}

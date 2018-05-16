package com.chebao.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


import com.chebao.R;
import com.chebao.ui.activity.security.CertificationActivity;
import com.chebao.ui.activity.security.ChangeLoginPasswordActivity;
import com.chebao.ui.activity.security.ChangerPayPassWordActivity;
import com.chebao.ui.activity.security.GestureEditActivity;
import com.chebao.ui.activity.security.GestureVerifyActivity;
import com.chebao.ui.activity.security.SetPayPasswordActivity;
import com.chebao.utils.SharedPreferencesUtils;
import com.pvj.xlibrary.loadinglayout.Utils;

import java.security.Security;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 安全中心
 * Created by pvj on 2016/12/27.
 */

public class SecurityActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
//

    @Bind(R.id.certification_tip)
    TextView certificationTip;
    @Bind(R.id.bank_tip)
    TextView bankTip;
    @Bind(R.id.chager_payword_to_tip)
    TextView chager_payword_to_tip;

    @Bind(R.id.gesture_switch)
    Switch gesture_switch;
//
//
//    @Bind(R.id.changer_pwd_to)
//    TextView changerPwdTo;
//
//    @Bind(R.id.gesture)
//    TextView gesture;

//    @Bind(R.id.certification_go)
//    ImageView certificationGo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        title.setText("安全设置");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Drawable drawable = Utils.getDrawble(this, R.mipmap.icon_certification);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth() / 2, drawable.getMinimumHeight() / 2);


//        phoneTo.setCompoundDrawables(null, null, drawable, null);

        certificationTip.setText((String) SharedPreferencesUtils.getParam(this, "realname", ""));
//
//        if ((Boolean) SharedPreferencesUtils.getParam(this, "tPerson", false)) {
//            phoneGo.setImageDrawable(drawable);
//            phoneTip.setText((String) SharedPreferencesUtils.getParam(this, "phone", ""));
//
//        }


        if ((Boolean) SharedPreferencesUtils.getParam(this, "tBankCardlist", false)) {

//            bankGo.setImageDrawable(drawable);
            String aa = (String) SharedPreferencesUtils.getParam(this, "bankcardno", "");
            int n = 4;

            if (aa.length() > 4) {
                String b = aa.substring(aa.length() - n, aa.length());

                bankTip.setText("**** **** **** " + b);
            } else {
                bankTip.setText("**** **** **** " + aa);
            }
            bankTip.setText((String) SharedPreferencesUtils.getParam(this, "bankcardno", ""));
        }

//        if ((Boolean) SharedPreferencesUtils.getParam(this, "tPerson", false)) {
//            certificationGo.setImageDrawable(drawable);
//
//        }

        if ((Boolean) SharedPreferencesUtils.getParam(this, "payPwd", false)) {
            chager_payword_to_tip.setText("去修改");
            chager_payword_to_tip.setTextColor(getResources().getColor(R.color.org_home));


        } else {
            chager_payword_to_tip.setText("去设置");
            chager_payword_to_tip.setTextColor(getResources().getColor(R.color.black_home_four_midle));

        }
        boolean isCheck=SharedPreferencesUtils.getIsGesture(this);
        gesture_switch.setChecked(isCheck);
        gesture_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SharedPreferencesUtils.setIsGesture(SecurityActivity.this,true);
                }else {
                    SharedPreferencesUtils.setIsGesture(SecurityActivity.this,false);

                }
            }
        });


    }

    @OnClick({R.id.certification_rl, R.id.bank_rl, R.id.changer_pwd_to_rl,
            R.id.chager_payword_to_rl, R.id.exit, R.id.gesture_rl, R.id.gesture_verify})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {

            case R.id.certification_rl:
                //实名认证
                intent = new Intent(this, CertificationActivity.class);
                startActivity(intent);
                break;

            case R.id.bank_rl:
                //银行卡认证

                if ((Boolean) SharedPreferencesUtils.getParam(this, "tBankCardlist", false)) {
                    intent = new Intent(this, MyBankActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(this, AddBankActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.changer_pwd_to_rl:
                intent = new Intent(this, ChangeLoginPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.chager_payword_to_rl:
                //交易密码
                if ((Boolean) SharedPreferencesUtils.getParam(this, "payPwd", false)) {
                    intent = new Intent(this, ChangerPayPassWordActivity.class);
                    startActivity(intent);

                } else {
                    intent = new Intent(this, SetPayPasswordActivity.class);
                    startActivity(intent);
                }
                break;
//
            case R.id.gesture_rl:
                intent = new Intent(this, GestureEditActivity.class);
                startActivity(intent);
                break;
            case R.id.gesture_verify:
                intent = new Intent(this, GestureVerifyActivity.class);
                startActivity(intent);
                break;


            case R.id.exit:
                SharedPreferencesUtils.clearAll(this);
                finish();
                break;
        }
    }


}

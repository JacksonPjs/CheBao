package com.chebao.ui.activity.security;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chebao.MainActivity;
import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.bean.LoginBean;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.login2register.LoginActivity;
import com.chebao.ui.activity.login2register.RegisterActivity;
import com.chebao.utils.DialogUtils;
import com.chebao.utils.PermissionsManager;
import com.chebao.utils.SharedPreferencesUtils;
import com.chebao.widget.gesture.GestureContentView;
import com.chebao.widget.gesture.GestureDrawline;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;

import rx.Subscriber;


/**
 * 手势绘制/校验界面
 */
public class GestureVerifyActivity extends Activity implements android.view.View.OnClickListener {
    /**
     * 手机号码
     */
    public static final String PARAM_PHONE_NUMBER = "PARAM_PHONE_NUMBER";
    /**
     * 意图
     */
    public static final String PARAM_INTENT_CODE = "PARAM_INTENT_CODE";
    private TextView mTextTitle;
    private TextView mTextCancel;
    private ImageView mImgUserLogo;
    private TextView mTextPhoneNumber;
    private TextView mTextTip;
    private FrameLayout mGestureContainer;
    private GestureContentView mGestureContentView;
    private TextView mTextOther;
    private String mParamPhoneNumber;
    private long mExitTime = 0;
    private int mParamIntentCode;
    int index = 0;
    Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_verify);
        MyApplication.instance.addActivity(this);
        activity = this;
        ObtainExtraData();
        setUpViews();
        setUpListeners();
    }

    private void ObtainExtraData() {
        mParamPhoneNumber = getIntent().getStringExtra(PARAM_PHONE_NUMBER);
        mParamIntentCode = getIntent().getIntExtra(PARAM_INTENT_CODE, 0);
    }

    private void setUpViews() {
        mTextTitle = (TextView) findViewById(R.id.text_title);
        mTextCancel = (TextView) findViewById(R.id.text_cancel);
        mTextPhoneNumber = (TextView) findViewById(R.id.text_phone_number);
        mTextTip = (TextView) findViewById(R.id.text_tip);
        mGestureContainer = (FrameLayout) findViewById(R.id.gesture_container);
        mTextOther = (TextView) findViewById(R.id.text_other_account);
        String str = SharedPreferencesUtils.getUserName(this);
        if (str.equals("")) {
            Intent intent = new Intent(GestureVerifyActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        } else {
            String bb = str.substring(3, 7);
            //字符串替换
            String cc = str.replace(bb, "****");
            mTextPhoneNumber.setText(cc);
        }
        //字符串截取

        // 初始化一个显示各个点的viewGroup
        mGestureContentView = new GestureContentView(this, true, SharedPreferencesUtils.getGesturePsw(this),
                new GestureDrawline.GestureCallBack() {

                    @Override
                    public void onGestureCodeInput(String inputCode) {

                    }

                    @Override
                    public void checkedSuccess() {
                        mGestureContentView.clearDrawlineState(0L);
                        login();
                    }

                    @Override
                    public void checkedFail() {
                        index++;
                        if (index >= 5) {
                            Intent intent = new Intent(GestureVerifyActivity.this, LoginActivity.class);
                            startActivity(intent);
                            T.ShowToastForLong(GestureVerifyActivity.this, "手势密码错误超过5次,请使用用户名登录");
                            GestureVerifyActivity.this.finish();
                        }
                        mGestureContentView.clearDrawlineState(1300L);
                        mTextTip.setVisibility(View.VISIBLE);
                        mTextTip.setText(Html
                                .fromHtml("<font color='#c70c1e'>密码错误</font>"));
                        // 左右移动动画
                        Animation shakeAnimation = AnimationUtils.loadAnimation(GestureVerifyActivity.this, R.anim.shake);
                        mTextTip.startAnimation(shakeAnimation);
                    }
                });
        // 设置手势解锁显示到哪个布局里面
        mGestureContentView.setParentView(mGestureContainer);
    }

    private void setUpListeners() {
        mTextCancel.setOnClickListener(this);
        mTextOther.setOnClickListener(this);
    }

    private String getProtectedMobile(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 11) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(phoneNumber.subSequence(0, 3));
        builder.append("****");
        builder.append(phoneNumber.subSequence(7, 11));
        return builder.toString();
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.text_cancel:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);

                this.finish();
                break;
            case R.id.text_other_account:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);

                this.finish();
                break;
            default:
                break;
        }
    }

    public void login() {
        String name=SharedPreferencesUtils.getUserName(activity)+"";
        final String passoword=SharedPreferencesUtils.getPassword(activity);
        NetWorks.login(name, passoword, new Subscriber<LoginBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {
                ;
            }

            @Override
            public void onError(Throwable e) {
//                T.ShowToastForLong(
//                        activity, "网络异常");
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
                Logger.e(e.toString());
                finish();
            }

            @Override
            public void onNext(LoginBean s) {
                if (s.getState().getStatus() == 0) {

                    SharedPreferencesUtils.savaUser(activity, s, passoword);

                    T.ShowToastForLong(activity, "登录成功");
                } else {
                    T.ShowToastForLong(activity, s.getState().getInfo());
                }

                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }


}

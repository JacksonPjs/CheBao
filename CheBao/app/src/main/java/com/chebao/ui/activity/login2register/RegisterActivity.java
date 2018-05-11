package com.chebao.ui.activity.login2register;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.bean.ImageCodeBean;
import com.chebao.bean.InfoBean;
import com.chebao.bean.LoginBean;
import com.chebao.geetest_sdk.SdkUtilsNEW;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.BaseActivity;
import com.chebao.utils.CodeUtils;
import com.chebao.utils.DialogUtils;
import com.chebao.utils.LoginRegisterUtils;
import com.chebao.utils.SharedPreferencesUtils;
import com.chebao.utils.UUIDs;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.CountDownButtonHelper;
import com.pvj.xlibrary.utils.T;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.internal.util.ObserverSubscriber;

public class RegisterActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.get_regist)
    TextView getRegist;


    private CountDownButtonHelper helper;
    SdkUtilsNEW sdkUtils;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.yzm)
    EditText yzm;

    @Bind(R.id.regist_go)
    Button registGo;
    @Bind(R.id.code)
    EditText code;
    @Bind(R.id.code_img)
    ImageView codeImageview;

    Dialog dialog;


    Dialog dialog2;
    CodeUtils codeUtils;
    Bitmap bitmap;

    String noncestr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        title.setText("注册");

        codeUtils=CodeUtils.getInstance();
        MyApplication.instance.addActivity(this);
        getImgCode();


    }


    @OnClick({R.id.get_regist, R.id.regist_go, R.id.code_again})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.code_again:
                getImgCode();
//                bitmap = codeUtils.createBitmap();
//                codeImageview.setImageBitmap(bitmap);

                break;

            case R.id.get_regist:
//                if (!code.getText().toString().equals(codeUtils.getCode())) {
//                    DialogUtils.createProgressDialog(this, "图形验证码错误");
//                    return;
//                }
                //                    getPhoneCodeByImageCode(phoneNum,noncestr,code.getText().toString());

                verificationNewUserPhone(RegisterActivity.this, RegisterActivity.this, phone);
                break;
            case R.id.regist_go:

                if (LoginRegisterUtils.isNullOrEmpty(phone)) {
                    T.ShowToastForShort(this, "手机号码未输入");
                    return;
                }

                if (!LoginRegisterUtils.isPhone(phone)) {
                    T.ShowToastForShort(this, "手机号码不正确");
                    return;
                }

                if (LoginRegisterUtils.isNullOrEmpty(password)) {
                    T.ShowToastForShort(this, "用户密码未输入");
                    return;
                }

                if (!LoginRegisterUtils.isPassWord(password)) {
                    T.ShowToastForShort(this, "用户密码格式不合法");
                    return;
                }

                if (LoginRegisterUtils.isNullOrEmpty(yzm)) {
                    T.ShowToastForShort(this, "手机验证码未输入");
                    return;
                }
                regist(phone.getText().toString(),password.getText().toString(),yzm.getText().toString(),"");


//

                break;
        }
    }

    /**
     * 验证手机号码能否注册
     *
     * @param
     */
    public void verificationNewUserPhone(final Context context, final Activity activity, final EditText phoneEdit) {
        //   this.noncestr = UUIDs.uuid();

        if (LoginRegisterUtils.isNullOrEmpty(phoneEdit)) {
            T.ShowToastForShort(this, "手机号码未输入");
            //   T.ShowToastForShort(this, "手机号码未输入");
            return;
        }

        if (!LoginRegisterUtils.isPhone(phoneEdit)) {
            T.ShowToastForShort(this, "手机号码不正确");
            //    T.ShowToastForShort(this, "手机号码不正确");
            return;
        }


        NetWorks.verificationNewUserPhone(phoneEdit.getText().toString(), new Subscriber<InfoBean>() {
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
                T.ShowToastForShort(RegisterActivity.this, "网络异常");
                //  T.ShowToastForShort(activity,"网络异常");
            }

            @Override
            public void onNext(InfoBean s) {
                //  {"info":"可用的手机号码","status":"0"}

                if (s.getState().getStatus() == 0) {
                    dialog.dismiss();
                    getPhoneCodeByImageCode(phoneEdit.getText().toString(),noncestr,code.getText().toString());
                } else {
                    dialog.dismiss();
                    T.ShowToastForShort(RegisterActivity.this, s.getState().getInfo());
                }

            }
        });
    }

    private void getImgCode(){
        noncestr = UUIDs.uuid();
        NetWorks.getImageCode(noncestr, new Subscriber<ImageCodeBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ImageCodeBean infoBean) {
                String string=infoBean.getCheckcode();
                codeUtils.setCode(string);
                bitmap = codeUtils.createBitmap();
                codeImageview.setImageBitmap(bitmap);

            }
        });
    }


    private void getPhoneCodeByImageCode(String num, String noncestr, String codeNum){
            NetWorks.getPhoneCodeByImageCode( num,noncestr,codeNum, new Subscriber<InfoBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(InfoBean infoBean) {
                    int o=infoBean.getState().getStatus();

                }
            });

    }


    private void regist(final String cellPhone, final String pwd, String regCode, String regReferee) {

        NetWorks.regist(cellPhone, pwd, regCode, regReferee, new Subscriber<InfoBean>() {
            @Override
            public void onStart() {
                if (dialog == null) {
                    dialog = DialogUtils.createProgressDialog(RegisterActivity.this, "注册中...");
                } else {
                    if (dialog != null && !dialog.isShowing()) {
                        dialog.show();
                    }

                }
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
                    T.ShowToastForShort(RegisterActivity.this, "注册成功");
                    login(cellPhone,pwd);
                    //     finish();
                } else {
                    T.ShowToastForShort(RegisterActivity.this, b.getState().getInfo());
                }

            }
        });

    }


    public void login(String name ,final String  passoword) {
        NetWorks.login(name, passoword, new Subscriber<LoginBean>() {
            @Override
            public void onStart() {
                if (dialog2 == null) {
                    dialog2 = DialogUtils.createProgressDialog(RegisterActivity.this, "登陆中...");
                } else {
                    dialog2.show();
                }
            }

            @Override
            public void onCompleted() {
                dialog2.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                T.ShowToastForLong(RegisterActivity.this,"网络异常");
                dialog2.dismiss();
                Logger.e(e.toString());
            }

            @Override
            public void onNext(LoginBean s) {
                if (s.getState().getStatus()==0){

                    SharedPreferencesUtils.savaUser(RegisterActivity.this,s,passoword);
                    //   finish();
                    MyApplication.instance.Allfinlish();
                    //  T.ShowToastForLong(RegisterActivity.this,"登陆成功");
                }else{
                    T.ShowToastForLong(RegisterActivity.this,s.getState().getInfo());
                }


            }
        });
    }


}
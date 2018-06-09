package com.chebao.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.chebao.App.Constant;
import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.bean.InfoMsg;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.login2register.LoginActivity;
import com.chebao.ui.activity.security.AddBankActivity;
import com.chebao.ui.activity.security.CertificationActivity;
import com.chebao.ui.activity.security.ChangeLoginPasswordActivity;
import com.chebao.ui.activity.security.ChangerPayPassWordActivity;
import com.chebao.ui.activity.security.GestureEditActivity;
import com.chebao.ui.activity.security.MyBankActivity;
import com.chebao.ui.activity.security.SetPayPasswordActivity;
import com.chebao.utils.FileSizeUtil;
import com.chebao.utils.FileUtil;
import com.chebao.utils.SharedPreferencesUtils;
import com.chebao.widget.dialog.ToastDialog;
import com.pvj.xlibrary.loadinglayout.Utils;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

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

    @Bind(R.id.chager_payword_to_tip)
    TextView chager_payword_to_tip;
    @Bind(R.id.gestureo_tip)
    TextView gestureo_tip;

    @Bind(R.id.gesture_switch)
    Switch gesture_switch;

    @Bind(R.id.clearmmmm)
    View clearmmmm;

    @Bind(R.id.number_size)
    TextView number_size;
    @Bind(R.id.version)
    TextView version;

//
//
//    @Bind(R.id.changer_pwd_to)
//    TextView changerPwdTo;
//
//    @Bind(R.id.gesture)
//    TextView gesture;

//    @Bind(R.id.certification_go)
//    ImageView certificationGo;

    String realname = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        title.setText("安全设置");

        long cd = FileUtil.getFolderSize(getExternalCacheDir());
        double fileSizeLong = FileSizeUtil.FormetFileSize(cd, 3);
        number_size.setText(fileSizeLong + "M");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Drawable drawable = Utils.getDrawble(this, R.mipmap.icon_certification);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth() / 2, drawable.getMinimumHeight() / 2);


//        phoneTo.setCompoundDrawables(null, null, drawable, null);
        realname = (String) SharedPreferencesUtils.getParam(this, "realname", "");
        if (realname == null || realname.equals("")) {

            certificationTip.setText("未认证");
            certificationTip.setTextColor(getResources().getColor(R.color.org_home));
        } else {
            certificationTip.setText("已认证");
            certificationTip.setTextColor(getResources().getColor(R.color.season_btn_bg));

        }

//
//        if ((Boolean) SharedPreferencesUtils.getParam(this, "tPerson", false)) {
//            phoneGo.setImageDrawable(drawable);
//            phoneTip.setText((String) SharedPreferencesUtils.getParam(this, "phone", ""));
//
//        }


//        if ((Boolean) SharedPreferencesUtils.getParam(this, "tPerson", false)) {
//            certificationGo.setImageDrawable(drawable);
//
//        }

        if ((Boolean) SharedPreferencesUtils.getParam(this, "payPwd", false)) {
            chager_payword_to_tip.setText("去修改");
            chager_payword_to_tip.setTextColor(getResources().getColor(R.color.black_home_four_midle));


        } else {
            chager_payword_to_tip.setText("去设置");
            chager_payword_to_tip.setTextColor(getResources().getColor(R.color.org_home));

        }
        boolean isCheck = SharedPreferencesUtils.getIsGesture(this);
        gesture_switch.setChecked(isCheck);
        gesture_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {


                    SharedPreferencesUtils.setIsGesture(SecurityActivity.this, true);
                } else {
                    SharedPreferencesUtils.setIsGesture(SecurityActivity.this, false);

                }
            }
        });
        String gest = SharedPreferencesUtils.getGesturePsw(this);
        if (gest != null) {
            gestureo_tip.setText("去修改");

            gestureo_tip.setTextColor(getResources().getColor(R.color.black_home_four_midle));

        } else {
            gestureo_tip.setText("去设置");

            gestureo_tip.setTextColor(getResources().getColor(R.color.black_home_four_midle));

        }
        try {
            version.setText(getVersionName() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @OnClick({R.id.certification_rl, R.id.bank_rl, R.id.changer_pwd_to_rl,
            R.id.chager_payword_to_rl, R.id.exit, R.id.gesture_rl, R.id.clearmmmm})
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
                if (realname == null || realname.equals("")) {
                    ToastDialog.Builder builder = new ToastDialog.Builder(SecurityActivity.this);

                    builder.setPositiveButton("跳转", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(SecurityActivity.this, CertificationActivity.class);
                            startActivity(intent);
                            dialog.dismiss();


                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.setMessage("未实名");
                    builder.create().show();

                } else if ((Boolean) SharedPreferencesUtils.getParam(this, "tBankCardlist", false)) {
                    intent = new Intent(this, MyBankActivity.class);
                    startActivity(intent);
                } else {
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

            case R.id.clearmmmm:
                FileUtil.cleanInternalCache(SecurityActivity.this);
//                Glide.get(this).clearMemory();//清理内存中的缓存。
//                Glide.get(this).clearDiskCache();//清理硬盘中的缓存。
                long cd = FileUtil.getFolderSize(getExternalCacheDir());
                double fileSizeLong = FileSizeUtil.FormetFileSize(cd, 3);
                number_size.setText(fileSizeLong + "M");
                break;
            case R.id.exit:
                LoginOut();

                break;
        }
    }

    private void LoginOut() {
        String token = (String) SharedPreferencesUtils.getParam(MyApplication.context, "token", "");
        NetWorks.loginOut(token, SharedPreferencesUtils.getUserName(this),
                new Subscriber<InfoMsg>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.json(e.toString());
                    }

                    @Override
                    public void onNext(InfoMsg loginBean) {
                        if (loginBean.getStatus().equals("y")) {
                            SharedPreferencesUtils.clearAll(SecurityActivity.this);
                            Intent intent = new Intent(SecurityActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            T.ShowToastForShort(SecurityActivity.this, loginBean.getInfo() + "");
                        }
                    }
                }
        );
    }

    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

}

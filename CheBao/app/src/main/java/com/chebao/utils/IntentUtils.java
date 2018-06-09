package com.chebao.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.chebao.App.Constant;
import com.chebao.ui.activity.mine.ChagerActivity;
import com.chebao.ui.activity.security.AddBankActivity;
import com.chebao.ui.activity.security.CertificationActivity;
import com.chebao.ui.activity.security.SetPayPasswordActivity;

/**
 * 创建日期：2018/5/29 on 10:11
 * 描述:
 * 作者:jackson Administrator
 */
public class IntentUtils {

    public static void ToastIntent(final Activity activity, String flag) {


        Handler mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Intent intent = null;
                switch (msg.what) {
                    case Constant.PAY_NO_MONEY_INTENT:
                        intent = new Intent(activity, ChagerActivity.class);
                        activity.startActivity(intent);
                        break;
                    case Constant.PAY_NO_PAYPSW_INTENT:
                        intent = new Intent(activity, SetPayPasswordActivity.class);
                        activity.startActivity(intent);
                        break;

                    case Constant.PAY_NO_BANK_INTENT:
                        intent = new Intent(activity, AddBankActivity.class);
                        activity.startActivity(intent);
                        break;
                    case Constant.PAY_NO_SHIMING_INTENT:
                        intent = new Intent(activity, CertificationActivity.class);
                        activity.startActivity(intent);
                        break;

                }
            }
        };

        switch (flag) {
            case Constant.PAY_NO_PAYPSW:
                mHandler.sendEmptyMessageDelayed(Constant.PAY_NO_PAYPSW_INTENT, Constant.STATUS_INTENT_TIME);
                break;
            case Constant.PAY_NO_MONEY:
                mHandler.sendEmptyMessageDelayed(Constant.PAY_NO_MONEY_INTENT, Constant.STATUS_INTENT_TIME);

                break;
            case Constant.PAY_NO_SHIMING:
                mHandler.sendEmptyMessageDelayed(Constant.PAY_NO_SHIMING_INTENT, Constant.STATUS_INTENT_TIME);

                break;
            case Constant.PAY_NO_BANK:
                mHandler.sendEmptyMessageDelayed(Constant.PAY_NO_BANK_INTENT, Constant.STATUS_INTENT_TIME);

                break;


        }


    }

}

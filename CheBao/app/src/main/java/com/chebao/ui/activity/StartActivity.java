package com.chebao.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.chebao.MainActivity;
import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.ui.activity.security.GestureVerifyActivity;
import com.chebao.utils.SharedPreferencesUtils;

public class StartActivity extends BaseActivity {

    Handler mHandler = new Handler() {
        Intent intent = null;

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 1:
                    intent = new Intent(StartActivity.this, GestureVerifyActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }

        }

        ;
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startactivty);
        String gest = SharedPreferencesUtils.getGesturePsw(this);
        boolean isGest=SharedPreferencesUtils.getIsGesture(this);
        if (gest != null&&isGest) {
            mHandler.sendEmptyMessageDelayed(0, 2000);

        } else {
            mHandler.sendEmptyMessageDelayed(0, 2000);

        }
    }

}

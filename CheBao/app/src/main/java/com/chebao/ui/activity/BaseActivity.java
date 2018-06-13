package com.chebao.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.chebao.MyApplication;
import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        MyApplication.instance.addActivity(this);
//        if (getSupportActionBar() != null){
//            getSupportActionBar().hide();
//        }
    }


    public void onBack(View view) {

        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EToast.reset();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}

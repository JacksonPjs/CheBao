package com.chebao.ui.activity;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.chebao.MainActivity;
import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.ui.activity.security.GestureVerifyActivity;
import com.chebao.utils.PermissionsManager;
import com.chebao.utils.SharedPreferencesUtils;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;

public class StartActivity extends Activity {
    private final int REQUEST_VIDEO_PERMISSION = 1;
    private final static String TAG = "StartActivity";


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

//        while (true){
//            String[] permissions = PermissionsManager.haveNoPermissions(this, PERMISSIONS);
//            if (permissions == null || permissions.length < 1) {
//                startMain();
//                break;
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null && permissions.length > 0) {
//                ActivityCompat.requestPermissions(this, permissions, REQUEST_VIDEO_PERMISSION);
//            }
//        }
        startMain();

    }
    public void startMain(){
        String gest = SharedPreferencesUtils.getGesturePsw(this);

        boolean isGest=SharedPreferencesUtils.getIsGesture(this);
        if (gest != null&&isGest) {
            mHandler.sendEmptyMessageDelayed(1, 2000);

        } else {
            mHandler.sendEmptyMessageDelayed(0, 2000);

        }
    }

    private final String[] PERMISSIONS = {
            Manifest.permission.CALL_PHONE,
           };
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean inquiry = true;
        boolean isGrant = true;

        if (grantResults != null && grantResults.length > 0) {
            //有权限获取失败
            for (int i = 0; i < grantResults.length; i++) {
                //是否为设置了【不再询问】
                if (grantResults[i] == -1) {
                    isGrant = false;
                }
                inquiry = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
                Log.i(TAG, "onRequestPermissionsResult:" + "permissions=" + permissions[i] + ",grantResults=" + grantResults[i] + ",show=" + inquiry);
            }

            if (!isGrant) {
                Logger.i(TAG, "权限获取异常");
                T.ShowToastForLong(this,"权限获取异常");
                return;
            }
        }
        Log.i(TAG, "权限获取正常");
        finish();
        //彻底退出进程，防止出现授权后 仍然无法搜索蓝牙设备的问题
        System.exit(0);
    }

}

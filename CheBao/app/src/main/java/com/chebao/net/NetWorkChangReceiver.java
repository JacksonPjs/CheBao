package com.chebao.net;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.chebao.ui.activity.pay.PayActivity;
import com.chebao.utils.SharedPreferencesUtils;
import com.chebao.widget.dialog.CustomDialog;
import com.chebao.widget.dialog.NetDialog;
import com.chebao.widget.dialog.ToastDialog;
import com.pvj.xlibrary.utils.T;

import java.util.logging.Handler;

/**
 * 创建日期：2018/7/6 on 14:25
 * 描述:
 * 作者:jackson Administrator
 */
public class NetWorkChangReceiver extends BroadcastReceiver {

    /**
     * 获取连接类型
     *
     * @param type
     * @return
     */
    private String getConnectionType(int type) {
        String connType = "";
        if (type == ConnectivityManager.TYPE_MOBILE) {
            connType = "3G网络数据";
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            connType = "WIFI网络";
        }
        return connType;
    }

     ToastDialog.Builder builder = null;
    NetDialog netDialog=null;



    @Override
    public void onReceive(final Context context, Intent intent) {
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {// 监听wifi的打开与关闭，与wifi的连接无关
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            Log.e("TAG", "wifiState:" + wifiState);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    break;
            }
        }
        // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            //获取联网状态的NetworkInfo对象
            NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (info != null) {
                //如果当前的网络连接成功并且网络连接可用
                if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()) {
                    if (info.getType() == ConnectivityManager.TYPE_WIFI || info.getType() == ConnectivityManager.TYPE_MOBILE) {
//                        if (builder != null) {
//                            builder.create().dismiss();
//                        }
                        if (netDialog!=null&&netDialog.isShowing()){
                            netDialog.dismiss();
                        }
                        Log.i("TAG", getConnectionType(info.getType()) + "连上");
                    }
                } else {
                    Log.i("TAG", getConnectionType(info.getType()) + "断开");
                    Log.e("context", getRunningActivityName(context));
                    final String activityName = getRunningActivityName(context);

//                    builder=new ToastDialog.Builder(context);
//                    builder.setMessage("" + "网络已断开");
//                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if (activityName.equals("PayActivity")) {
//                                Activity activity = (Activity) context;
//                                activity.finish();
//                            }
//                            dialog.dismiss();
//                        }
//
//                    });
//                    builder.create().show();
                    netDialog=new NetDialog(context);
                    netDialog.setOnClickNetListener(new NetDialog.OnClickNetListener() {
                        @Override
                        public void onFinish() {
                            if (activityName.equals("PayActivity")) {
                                Activity activity = (Activity) context;
                                activity.finish();
                            }
                        }
                    });
                    netDialog.show();
                }
            }
        }
    }



    private String getRunningActivityName(Context context) {
        String contextString = context.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
    }
}


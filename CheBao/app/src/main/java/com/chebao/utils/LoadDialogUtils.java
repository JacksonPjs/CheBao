package com.chebao.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chebao.R;

/**
 * 创建日期：2018/5/28 on 11:35
 * 描述:
 * 作者:jackson Administrator
 */
public class LoadDialogUtils {
    static AlertDialog dialog = null;
    static Activity activity = null;

    /**
     * 普通等待提示框
     */
    public static Dialog createProgressDialog(Activity activity1, String msg1) {


        AlertDialog.Builder builder = new AlertDialog.Builder(activity1);
        dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        activity=activity1;
        //  dialog.setTitle("正在登录，请稍后");


        return dialog;
    }

    public static void DialogShow(String msg) {
        if (dialog != null){
            dialog.show();
            //这些设置必须放在 dialog.show();后面才能有效果
            Window window = dialog.getWindow();
            window.setContentView(R.layout.loading1);
            RelativeLayout relayout = (RelativeLayout) window.findViewById(R.id.relayout);
            Display display = activity.getWindowManager().getDefaultDisplay();
            int minHeight = (int) (display.getHeight() * 0.3);              //使用这种方式更改了dialog的框高
            relayout.setMinimumHeight(minHeight);
//
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = (int) (display.getWidth() * 0.5);
            params.height = (int) (display.getWidth() * 0.5);    //使用这种方式更改了dialog的框宽
            window.setAttributes(params);

            TextView tv = (TextView) dialog.findViewById(R.id.content);
            tv.setText(msg);
        }


    }


    public static void Dialogdismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
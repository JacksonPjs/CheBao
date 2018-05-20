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
 * Created by Administrator on 2016/12/31.
 */

public class DialogUtils {
    static AlertDialog dialog = null;

    /**
     * 普通等待提示框
     */
    public static Dialog createProgressDialog(Activity activity, String msg) {


        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        //  dialog.setTitle("正在登录，请稍后");
        dialog.show();
//这些设置必须放在 dialog.show();后面才能有效果
        Window window = dialog.getWindow();
        window.setContentView(R.layout.loading1);
        RelativeLayout relayout = (RelativeLayout) window.findViewById(R.id.relayout);
        Display display = activity.getWindowManager().getDefaultDisplay();
        int minHeight = (int) (display.getHeight() * 0.3);              //使用这种方式更改了dialog的框高
// int minWidth = (int) (display.getWidth()*0.4);             //没有效果
        relayout.setMinimumHeight(minHeight);

        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (display.getWidth() * 0.5);
        params.height = (int) (display.getWidth() * 0.5);    //使用这种方式更改了dialog的框宽
        window.setAttributes(params);

        TextView tv = (TextView) window.findViewById(R.id.content);
        tv.setText(msg);

        return dialog;
    }


    public void Dialogdismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}

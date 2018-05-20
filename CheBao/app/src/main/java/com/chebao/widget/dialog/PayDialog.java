package com.chebao.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.chebao.R;
import com.chebao.widget.pay.PayPwdEditText;


/**
 * Created by ywl on 2017/2/28.
 */

public class PayDialog extends BaseDialog {

    private PayPwdEditText payPwdEditText;
    TextView textView;
    String string=null;
    String msg=null;

    public PayDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_dialog_lyaout);
        payPwdEditText = (PayPwdEditText) findViewById(R.id.ppet);
        textView = (TextView) findViewById(R.id.tv_msg);
        if (msg!=null){
            textView.setText(msg);
        }else {
            textView.setVisibility(View.GONE);
        }

//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onEditorFinishListener != null) {
//                    onEditorFinishListener.onFinish(string);
//                }
//            }
//        });

        payPwdEditText.initStyle(R.drawable.ll_selector, 6, 0.33f, R.color.org_home, R.color.colorAccent, 20);
        payPwdEditText.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
//
                if (onEditorFinishListener != null) {
                    onEditorFinishListener.onFinish(str);
                    string=str;
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                payPwdEditText.setFocus();
            }
        }, 100);

    }

    private onEditorFinishListener onEditorFinishListener;

    public interface onEditorFinishListener {
        void onFinish(String str);
    }

    public void setOnTextFinishListener(onEditorFinishListener onEditorFinishListener) {
        this.onEditorFinishListener = onEditorFinishListener;
    }
    public void setMsg(String msg){
        this.msg=msg;
    }

    @Override
    public void dismiss() {
        View view = getCurrentFocus();
        if (view instanceof TextView) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
        super.dismiss();
    }

}

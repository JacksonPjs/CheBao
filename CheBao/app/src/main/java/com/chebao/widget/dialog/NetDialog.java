package com.chebao.widget.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chebao.R;

/**
 * 创建日期：2018/7/6 on 18:21
 * 描述:
 * 作者:jackson Administrator
 */
public class NetDialog extends BaseDialog {
    Bitmap bitmap=null;
    TextView msg;
    ImageView del;


    public NetDialog(Context context) {
        super(context);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_netchange);

        init();
    }
    public void init(){
        msg=findViewById(R.id.msg);

        del=findViewById(R.id.del);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickNetListener!=null){
                    onClickNetListener.onFinish();
                }
                dismiss();
            }
        });


    }

    private OnLongClickShareListener OnLongClickShareListener;

    public interface OnLongClickShareListener {
        void onFinish(String str);
    }

    public void setOnLongClickNetListener(OnLongClickShareListener OnLongClickShareListener) {
        this.OnLongClickShareListener = OnLongClickShareListener;
    }

    private OnClickNetListener onClickNetListener;

    public interface OnClickNetListener {
        void onFinish();
    }

    public void setOnClickNetListener(OnClickNetListener onClickNetListener) {
        this.onClickNetListener = onClickNetListener;
    }

}
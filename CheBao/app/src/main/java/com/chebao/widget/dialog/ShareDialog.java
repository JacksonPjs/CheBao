package com.chebao.widget.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.chebao.R;
import com.chebao.utils.ImageUtil;

/**
 * 创建日期：2018/6/7 on 17:15
 * 描述:
 * 作者:jackson Administrator
 */
public class ShareDialog extends BaseDialog {
    ImageView iv_qrcode;
    ImageView del;
    Bitmap bitmap=null;


    public ShareDialog(Context context) {
        super(context);
    }

    public ImageView getIv_qrcode() {
        return iv_qrcode;
    }

    public void setIv_qrcode(ImageView iv_qrcode) {
        this.iv_qrcode = iv_qrcode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share_qrcode);

        init();
    }
    public void init(){
        iv_qrcode=findViewById(R.id.iv_qrcode);
        del=findViewById(R.id.del);

        if (bitmap!=null){
            iv_qrcode.setImageBitmap(bitmap);
        }

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        iv_qrcode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (OnLongClickShareListener!=null){

                    OnLongClickShareListener.onFinish("");
                }
                return true;
            }
        });

    }

    private OnLongClickShareListener OnLongClickShareListener;

    public interface OnLongClickShareListener {
        void onFinish(String str);
    }

    public void setOnLongClickShareListener(OnLongClickShareListener OnLongClickShareListener) {
        this.OnLongClickShareListener = OnLongClickShareListener;
    }
    public void setBitmap(Bitmap bitmap){
        this.bitmap=bitmap;
    }

}

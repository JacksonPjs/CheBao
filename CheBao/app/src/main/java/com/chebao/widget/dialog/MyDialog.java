package com.chebao.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.chebao.R;
import com.pvj.xlibrary.utils.Toast;

/**
 * Created by Administrator on 2017/3/16.
 */

public class MyDialog extends Dialog implements View.OnClickListener{

    private Button cancel;
    Context context;
    View localView;
    private RelativeLayout clearallpan;

    public MyDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 这句代码换掉dialog默认背景，否则dialog的边缘发虚透明而且很宽
        // 总之达不到想要的效果
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater inflater = getLayoutInflater();


        localView = inflater.inflate(R.layout.animclearpan, null);
        localView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.score_business_query_enter));
        setContentView(localView);
        // 这句话起全屏的作用
        getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);

        initView();
        initListener();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.dismiss();
        return super.onTouchEvent(event);
    }

    private void initListener() {
        cancel.setOnClickListener(this);
        clearallpan.setOnClickListener(this);
    }

    private void initView() {
        cancel = (Button) findViewById(R.id.cancel);
        clearallpan = (RelativeLayout) findViewById(R.id.clearallpan);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                this.dismiss();
                break;
            case R.id.clearallpan:
                Toast.makeText(context, "请在该区域之外点击", 0).show();
                break;
        }
    }
}

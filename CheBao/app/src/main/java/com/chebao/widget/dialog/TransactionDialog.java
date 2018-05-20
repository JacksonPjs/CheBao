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
import android.widget.TextView;

import com.chebao.R;
import com.pvj.xlibrary.loadinglayout.Utils;

/**
 * 创建日期：2018/5/7 on 15:06
 * 描述:
 * 作者:jackson Administrator
 */
public class TransactionDialog extends Dialog implements View.OnClickListener {

    private TextView cancel, all, recharge, withdraw,hongbao,shou,huikuai;
    Context context;
    View localView;
    private ItemSelectListener itemSelectListener;

    public TransactionDialog(Context context, ItemSelectListener itemSelectListener) {
        super(context);
        this.context = context;
        this.itemSelectListener = itemSelectListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 这句代码换掉dialog默认背景，否则dialog的边缘发虚透明而且很宽
        // 总之达不到想要的效果
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater inflater = getLayoutInflater();


        localView = inflater.inflate(R.layout.dialog_transaction, null);
        localView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.score_business_query_enter));
        setContentView(localView);
        // 这句话起全屏的作用
        getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);

        initView();
        initListener();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        this.dismiss();
        return super.onTouchEvent(event);
    }

    private void initListener() {
        cancel.setOnClickListener(this);
        all.setOnClickListener(this);
        recharge.setOnClickListener(this);
        withdraw.setOnClickListener(this);
        hongbao.setOnClickListener(this);
        shou.setOnClickListener(this);
        huikuai.setOnClickListener(this);
    }

    private void initView() {
        cancel = (TextView) findViewById(R.id.cancel);
        all = (TextView) findViewById(R.id.all);
        recharge = (TextView) findViewById(R.id.recharge);
        withdraw = (TextView) findViewById(R.id.withdraw);
        hongbao = (TextView) findViewById(R.id.hongbao);
        shou = (TextView) findViewById(R.id.shou);
        huikuai = (TextView) findViewById(R.id.huikuai);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                this.dismiss();
                break;
            case R.id.all:
                if (itemSelectListener != null) {
                    itemSelectListener.selectAll();
                }

                break;
            case R.id.recharge:
                if (itemSelectListener != null) {
                    itemSelectListener.selectRecharge();
                }
                break;
            case R.id.withdraw:
                if (itemSelectListener != null) {
                    itemSelectListener.selectWithdraw();
                }
                break;
            case R.id.huikuai:
                if (itemSelectListener != null) {
                    itemSelectListener.selectHuiKuan();
                }
                break;
                case R.id.shou:
                if (itemSelectListener != null) {
                    itemSelectListener.selectShou();
                }
                break;
                case R.id.hongbao:
                if (itemSelectListener != null) {
                    itemSelectListener.selectHongBao();
                }
                break;

        }
        this.dismiss();

    }

    public interface ItemSelectListener {
        void selectAll();

        void selectRecharge();

        void selectWithdraw();

        void selectHuiKuan();

        void selectShou();

        void selectHongBao();

    }
}


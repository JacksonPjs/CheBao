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
import android.widget.TextView;

import com.chebao.R;

/**
 * 创建日期：2018/5/7 on 17:37
 * 描述:
 * 作者:jackson Administrator
 */
public class InvestmentDialog extends Dialog implements View.OnClickListener {

    private TextView cancel, all, repayment, meet, invite;
    Context context;
    View localView;
    private ItemSelectListener itemSelectListener;

    public InvestmentDialog(Context context, ItemSelectListener itemSelectListener) {
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


        localView = inflater.inflate(R.layout.dialog_investment, null);
        localView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.score_business_query_enter));
        setContentView(localView);
        // 这句话起全屏的作用
        getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);

        initView();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        this.dismiss();
        return super.onTouchEvent(event);
    }

    private void initListener() {
        cancel.setOnClickListener(this);
        all.setOnClickListener(this);
        invite.setOnClickListener(this);
        repayment.setOnClickListener(this);
        meet.setOnClickListener(this);
    }

    private void initView() {
        cancel = (TextView) findViewById(R.id.cancel);
        all = (TextView) findViewById(R.id.all);
        invite = (TextView) findViewById(R.id.invite);
        repayment = (TextView) findViewById(R.id.repayment);
        meet = (TextView) findViewById(R.id.meet);
        initListener();


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                this.dismiss();
                break;
            case R.id.all:
                //
                if (itemSelectListener != null) {
                    itemSelectListener.selectAll();
                }
            case R.id.meet:
                //已还清
                if (itemSelectListener != null) {
                    itemSelectListener.selectMeet();
                }
                break;
            case R.id.invite:
                //招标中
                if (itemSelectListener != null) {
                    itemSelectListener.selectInvite();
                }
                break;
            case R.id.repayment:
                //还款中
                if (itemSelectListener != null) {
                    itemSelectListener.selectRepayment();
                }
                break;


        }
        this.dismiss();

    }

    public interface ItemSelectListener {
        void selectAll();

        void selectMeet();

        void selectInvite();
        void selectRepayment();

    }
}


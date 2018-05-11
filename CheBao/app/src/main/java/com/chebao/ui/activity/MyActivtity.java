package com.chebao.ui.activity;

import android.os.Bundle;

import com.chebao.R;

import butterknife.ButterKnife;

/**
 * 创建日期：2018/5/7 on 18:26
 * 描述:我的滴滴宝
 * 作者:jackson Administrator
 */
public class MyActivtity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_my);
    }
}

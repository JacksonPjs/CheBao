package com.chebao.ui.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.chebao.Adapter.ViewPagerFramentAdapter;
import com.chebao.R;
import com.chebao.bean.BorrowDetailBean;
import com.chebao.ui.fragment.Fragemt_Explain;
import com.chebao.ui.fragment.Fragemt_Notes;
import com.chebao.ui.fragment.Fragment_Data;
import com.chebao.widget.CircleProgress;
import com.chebao.widget.MyScrollView;
import com.pvj.xlibrary.loadinglayout.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/10.
 * 产品详情(活期)
 */

public class DetailsActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.buy)
    TextView buy;
    BorrowDetailBean bean;
    String id;
    int borrowStatus;
    boolean flag;

    List<Fragment> fragmentList;
    ViewPagerFramentAdapter viewPagerFramentAdapter;
    Handler handler = new Handler();
    @Bind(R.id.circleprogress)
    CircleProgress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        init();

    }

    private void init() {

        progress.setValue(100f);
        Intent intent = getIntent();
//        id = intent.getStringExtra("id");
//        borrowStatus = intent.getIntExtra("borrowStatus",-1);



    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @OnClick({R.id.buy,R.id.back})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;



        }
    }






}

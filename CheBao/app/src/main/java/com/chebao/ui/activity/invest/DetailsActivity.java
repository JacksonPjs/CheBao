package com.chebao.ui.activity.invest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import android.view.View;
import android.widget.TextView;


import com.chebao.Adapter.ViewPagerFramentAdapter;
import com.chebao.R;
import com.chebao.bean.BorrowDetailBean;
import com.chebao.ui.activity.BaseActivity;
import com.chebao.ui.activity.login2register.LoginActivity;
import com.chebao.ui.activity.pay.DepositActivity;
import com.chebao.utils.SharedPreferencesUtils;
import com.chebao.utils.onclick.AntiShake;
import com.chebao.widget.CircleProgress;
import com.pvj.xlibrary.utils.T;

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
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        Intent intent;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.buy:
                if ((Boolean) SharedPreferencesUtils.getParam(this, "islogin", false)){
                    intent = new Intent(this, DepositActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("bean", (Serializable) bean);
//                    bundle.putString("id", id + "");
//                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    intent = new Intent(DetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                    T.ShowToastForLong(DetailsActivity.this,"未登录");

                }
                break;



        }
    }






}

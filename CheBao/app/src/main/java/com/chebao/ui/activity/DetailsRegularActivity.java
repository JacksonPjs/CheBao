package com.chebao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chebao.R;
import com.chebao.bean.BorrowDetailBean;
import com.chebao.net.NetWorks;
import com.pvj.xlibrary.log.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * 创建日期：2018/5/4 on 15:04
 * 描述:定期详情
 * 作者:jackson Administrator
 */
public class DetailsRegularActivity extends BaseActivity {
    @Bind(R.id.details_product)
    RelativeLayout product;
    @Bind(R.id.title)
    TextView title;
    BorrowDetailBean bean;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_type1);
        ButterKnife.bind(this);
        title.setText("定期详情");
        init();
    }

    private void init() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        net();
    }

    public void net() {
        NetWorks.queryBorrowDetail(id, new Subscriber<BorrowDetailBean>() {

            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.toString());
            }

            @Override
            public void onNext(BorrowDetailBean s) {
                bean = s;
                if (s.getState().getStatus() == 0) {

                    setData(s);
                } else {
                }
            }
        });
    }

    private void setData(BorrowDetailBean s) {

    }

    @OnClick({R.id.buy, R.id.back, R.id.details_product})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.details_product:
                intent = new Intent(this, DetailsProductActivity.class);
                startActivity(intent);
                break;

            case R.id.buy:
                intent = new Intent(this, DetailsProductActivity.class);
                startActivity(intent);
                break;

        }
    }

}

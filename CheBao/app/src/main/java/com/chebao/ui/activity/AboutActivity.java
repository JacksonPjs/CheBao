package com.chebao.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.chebao.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建日期：2018/5/19 on 18:41
 * 描述:
 * 作者:jackson Administrator
 */
public class AboutActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        title.setText("信息披露");
    }
}

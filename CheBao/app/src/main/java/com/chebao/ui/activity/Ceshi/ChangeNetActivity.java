package com.chebao.ui.activity.Ceshi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chebao.R;
import com.chebao.net.NetService;
import com.chebao.ui.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建日期：2018/5/22 on 18:07
 * 描述:
 * 作者:jackson Administrator
 */
public class ChangeNetActivity extends BaseActivity {
    @Bind(R.id.change_net)
    Button NetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_net);
        ButterKnife.bind(this);
    }

  @OnClick({R.id.change_net})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.change_net:
                break;
        }
  }


}

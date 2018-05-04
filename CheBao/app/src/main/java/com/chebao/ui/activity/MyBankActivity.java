package com.chebao.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.chebao.R;
import com.chebao.bean.BankListBean;
import com.chebao.utils.SharedPreferencesUtils;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/12/27.
 * 我的银行卡
 */

public class MyBankActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.addbank)
    TextView addbank;
    @Bind(R.id.addbank_contaitnr)
    View addbankContair;
    @Bind(R.id.bank_contair)
    View bankContair;
    @Bind(R.id.bankname)
    TextView bankname;
    @Bind(R.id.banknum)
    TextView banknum;
    @Bind(R.id.jiebang)
    TextView jiebang;
    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.layout_contiant)
    LoadingLayout layoutContiant;

    BankListBean bak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybank);
        ButterKnife.bind(this);
        title.setText("我的银行卡");
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (SharedPreferencesUtils.getIsBank(this)) {
            addbankContair.setVisibility(View.GONE);
            bankContair.setVisibility(View.VISIBLE);

        } else {
            layoutContiant.setStatus(LoadingLayout.Success);
            addbankContair.setVisibility(View.VISIBLE);
            bankContair.setVisibility(View.GONE);
        }

    }

    // 获取银行卡列表

    @OnClick({R.id.addbank, R.id.jiebang})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addbank:
                if (SharedPreferencesUtils.getIsRealName(this)) {
                    Intent intent = new Intent(MyBankActivity.this, AddBankActivity.class);
                    startActivity(intent);
                } else {
                    T.ShowToastForShort(this, "请先实名认证才能添加银行卡");
                }
                break;

            case R.id.jiebang:
//                CustomDialog.Builder builder = new CustomDialog.Builder(this);
//                //    builder.setMessage("这个就是自定义的提示框");
//                builder.setTitle("提示");
//                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    //设置你的操作事项
//                    deleteBankCard(bak.getData().get(0).getId());
//                }
//            });
//
//                builder.setNegativeButton("取消",
//                        new android.content.DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                builder.create().show();

                break;
        }


    }




}

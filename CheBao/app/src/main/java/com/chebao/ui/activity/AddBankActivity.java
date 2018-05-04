package com.chebao.ui.activity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.bean.Bank;
import com.chebao.net.NetService;
import com.chebao.net.OkHttpUtils;
import com.chebao.utils.BankDialog;
import com.chebao.utils.LoginRegisterUtils;
import com.chebao.utils.SharedPreferencesUtils;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/12/27.
 */

public class AddBankActivity extends BaseActivity implements BankDialog.Banklistenr {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.bankbtn)
    TextView bankbtn;
    @Bind(R.id.login_go)
    Button loginGo;
    Dialog dialogBank;
    Dialog dialog;
    Bank bank;
    @Bind(R.id.card)
    EditText card;
    @Bind(R.id.layout_contiant_bank)
    LoadingLayout layoutContiant;
    @Bind(R.id.zhihang)
    EditText zhihang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbank);
        ButterKnife.bind(this);
        title.setText("我的银行卡");

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBank(Bank bank) {
        this.bank = bank;
        bankbtn.setText(bank.getValue());
    }

    @OnClick({R.id.bankbtn, R.id.login_go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bankbtn:
                if (dialogBank == null) {
                    BankDialog bankDialog = new BankDialog();
                    bankDialog.setBanklistenr(this);
                    dialogBank = bankDialog.createProgressDialog(this);
                } else {
                    dialogBank.show();
                }
                break;
            case R.id.login_go:
                if (bank == null) {
                    T.ShowToastForShort(AddBankActivity.this, "请选择所属银行...");
                    return;
                }

                if (LoginRegisterUtils.isNullOrEmpty(card)) {
                    T.ShowToastForShort(AddBankActivity.this, "请输入银行卡号");
                    return;
                }


                if (card.getText().length()<16) {
                T.ShowToastForShort(AddBankActivity.this, "银行卡号输入不正确");
                return;
            }



                break;
        }
    }


    // 获取身份信息


    /**
     *
     * @param style   0  是 请求实名      1 ， 是请求插入银行卡
     */
}

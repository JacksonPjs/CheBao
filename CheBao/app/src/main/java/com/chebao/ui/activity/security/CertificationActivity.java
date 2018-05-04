package com.chebao.ui.activity.security;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chebao.MyApplication;
import com.chebao.R;
import com.chebao.ui.activity.BaseActivity;
import com.chebao.utils.LoginRegisterUtils;
import com.chebao.utils.SharedPreferencesUtils;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.pvj.xlibrary.loadinglayout.Utils;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * 实名认证
 * Created by pvj on 2016/12/27.
 */

public class CertificationActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.user_name)
    EditText userName;
    @Bind(R.id.sfz)
    EditText sfz;
    @Bind(R.id.rez_go)
    Button rezGo;
//    @Bind(R.id.loadinglayout)
//    LoadingLayout loadinglayout;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        ButterKnife.bind(this);
        title.setText("实名认证");

        if ((Boolean) SharedPreferencesUtils.getParam(this, "tPerson", false)) {
//            rezGo.setBackground(Utils.getDrawble(this, R.drawable.button_border_hui));
            rezGo.setEnabled(false);
            rezGo.setText("已认证");
        } else {
//            loadinglayout.setStatus(LoadingLayout.Success);
        }


    }



    @OnClick(R.id.rez_go)
    public void onClick() {
        if (LoginRegisterUtils.isNullOrEmpty(userName)) {
            T.ShowToastForShort(this, "请填写姓名");
            return;
        }

        if (LoginRegisterUtils.isNullOrEmpty(sfz)) {
            T.ShowToastForShort(this, "请填写身份证");
            return;
        }


        goShiMin(userName.getText().toString(), sfz.getText().toString());

    }


    public void goShiMin(String name, String no) {

        StringBuilder sb = new StringBuilder();
        sb.append(" _ed_token_");
        sb.append("=");
        sb.append((String) SharedPreferencesUtils.getParam(MyApplication.context, "token", ""));
        sb.append(";");

        sb.append(" _ed_username_");
        sb.append("=");
        sb.append((String) SharedPreferencesUtils.getParam(MyApplication.context, "name", ""));
        sb.append(";");

        sb.append(" _ed_cellphone_");
        sb.append("=");
        sb.append((String) SharedPreferencesUtils.getParam(MyApplication.context, "phone", ""));
        sb.append(";");

//        if (dialog == null) {
//            dialog = DialogUtils.createProgressDialog(CertificationActivity.this, "实名中...");
//        } else {
//            dialog.show();
//        }
//        OkHttpUtils
//                .post()
//                .url(NetService.API_SERVER + "/regPerson.html")
//                .addHeader("Cookie", sb.toString())
//                .addParams("realName", name)
//                .addParams("cardNo", no)
//                .build()
//                .execute(new Callback<String>() {
//
//                    @Override
//                    public String parseNetworkResponse(Response response) throws IOException {
//                        return response.body().string();
//                    }
//
//                    @Override
//                    public void onError(Request request, Exception e) {
//                        Logger.e(e.toString());
//                        dialog.dismiss();
//                    }
//
//                    @Override
//                    public void onResponse(String o) {
//
//                      Logger.json(o);
//                        try {
//                            JSONObject json = new JSONObject((String) o.toString());
//
//                            JSONObject sata = json.getJSONObject("state");
//
//                            int s = sata.getInt("status");
//                            if (s == 0) {
//                                SharedPreferencesUtils.setParam(CertificationActivity.this, "tPerson", true);
//                                dialog.dismiss();
//                                finish();
//                            } else if (s == 99) {
//                                netLogin(1);
//                            } else {
//                                dialog.dismiss();
//                                T.ShowToastForShort(CertificationActivity.this, sata.getString("info"));
//                            }
//                        } catch (JSONException e) {
//                            //  e.printStackTrace();
//                            dialog.dismiss();
//                            T.ShowToastForShort(CertificationActivity.this, "数据异常，请联系客服！");
//                        }
//
//                    }
//                });

    }



}

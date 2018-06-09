package com.chebao.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chebao.App.Constant;
import com.chebao.R;
import com.chebao.bean.CenterIndexBean;
import com.chebao.bean.LoginBean;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.Activity_discount;
import com.chebao.ui.activity.AnnouncementListActivity;
import com.chebao.ui.activity.mine.ChagerActivity;
import com.chebao.ui.activity.HuiKuanActivity;
import com.chebao.ui.activity.InvestmentActivity;
import com.chebao.ui.activity.MyActivtity;
import com.chebao.ui.activity.SecurityActivity;
import com.chebao.ui.activity.TransactionActivity;
import com.chebao.ui.activity.WithdrawActivity;
import com.chebao.ui.activity.login2register.LoginActivity;
import com.chebao.ui.activity.mine.ShareActivity;
import com.chebao.utils.IntentUtils;
import com.chebao.utils.PermissionsManager;
import com.chebao.utils.SharedPreferencesUtils;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class Fragment_Mine extends BaseFragment {

    //    @Bind(R.id.user_head)
//    CircleImageView userHead;
    @Bind(R.id.chager)
    TextView chager;
    @Bind(R.id.count_to)
    LinearLayout countTo;
    @Bind(R.id.touzi_to)
    LinearLayout touziTo;
    @Bind(R.id.money_to)
    LinearLayout moneyTo;
    @Bind(R.id.measgg_to)
    LinearLayout measggTo;
    @Bind(R.id.set)
    ImageView set;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.toatal)
    TextView toatal;
    @Bind(R.id.eableuse)
    TextView eableuse;
    @Bind(R.id.toatal2)
    TextView toatal2;
    @Bind(R.id.usableamount)
    TextView usableamount;
    @Bind(R.id.eye_set)
    ImageView eyeSet;


    @Bind(R.id.withdraw)
    View withdraw;
    boolean IsGone = false;


    /**
     * find view from layout and set listener
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine,
                null);
        ButterKnife.bind(this, rootView);

        //标题内容  ChagerActivity
        SharedPreferencesUtils.getUserName(getContext());

        return rootView;
    }

    public void init() {

    }

    /**
     * init data
     */
    @Override
    public void fillDate() {

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (grantResults != null && grantResults.length > 0) {
            switch (requestCode) {
                case 1:
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        call();
                    }
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        name.setText((String) SharedPreferencesUtils.getParam(getActivity(), "name", ""));
        toatal.setText((String) SharedPreferencesUtils.getParam(getActivity(), "total1", "0"));
        eableuse.setText((String) SharedPreferencesUtils.getParam(getActivity(), "total3", "0"));
        usableamount.setText((String) SharedPreferencesUtils.getParam(getActivity(), "usableAmount", "0"));
        toatal2.setText((String) SharedPreferencesUtils.getParam(getActivity(), "toatal2", "0"));
        netLogin();


    }

    /**
     * network request
     */
    @Override
    public void requestData() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.count_to, R.id.withdraw, R.id.chager, R.id.touzi_to, R.id.money_to,
            R.id.measgg_to, R.id.set, R.id.eye_set, R.id.mine_to, R.id.cell_phohe, R.id.xiaoxi,
            R.id.certification_go, R.id.share_to})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.certification_go:
                intent = new Intent(getActivity(), SecurityActivity.class);
                startActivity(intent);
                break;
            case R.id.cell_phohe:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.CALL_PHONE}, 1);
                } else {
                    call();
                }


                break;

            case R.id.xiaoxi:
                intent = new Intent(getActivity(), AnnouncementListActivity.class);
                startActivity(intent);
                break;

            case R.id.eye_set:
                if (!IsGone) {
                    eyeSet.setImageDrawable(getResources().getDrawable(R.mipmap.icon_eye_gone));
                    toatal.setText("--");
                    eableuse.setText("--");
                    toatal2.setText("--");
                    IsGone = true;
                } else {
                    eyeSet.setImageDrawable(getResources().getDrawable(R.mipmap.icon_eye));
                    toatal.setText((String) SharedPreferencesUtils.getParam(getActivity(), "total1", "0"));
                    eableuse.setText((String) SharedPreferencesUtils.getParam(getActivity(), "usableAmount", "0"));
                    toatal2.setText((String) SharedPreferencesUtils.getParam(getActivity(), "total2", "0"));
                    IsGone = false;
                }

                break;
            case R.id.mine_to:
                //我的滴滴宝
                intent = new Intent(getActivity(), MyActivtity.class);
                getActivity().startActivity(intent);
                break;

            case R.id.count_to:
                //我的红包
                intent = new Intent(getActivity(), Activity_discount.class);
                getActivity().startActivity(intent);
                break;
            case R.id.set:
                //安全设置
                intent = new Intent(getActivity(), SecurityActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.touzi_to:
                //回款查询
                intent = new Intent(getActivity(), HuiKuanActivity.class);
                getActivity().startActivity(intent);
                break;

            case R.id.chager:
                //充值
                if (SharedPreferencesUtils.getIsBank(getContext())) {
                    intent = new Intent(getActivity(), ChagerActivity.class);
                    getActivity().startActivity(intent);
                } else {
                    T.ShowToastForShort(getContext(), "充值前，需要添加银行卡.");
                    IntentUtils.ToastIntent(getActivity(), Constant.PAY_NO_BANK);


                }

                break;
            case R.id.withdraw:
                //提现
                if (SharedPreferencesUtils.getIsBank(getContext())) {
                    intent = new Intent(getActivity(), WithdrawActivity.class);
                    getActivity().startActivity(intent);
                } else {
                    IntentUtils.ToastIntent(getActivity(), Constant.PAY_NO_BANK);

                    T.ShowToastForShort(getContext(), "提现前，需要添加银行卡.");
                }

                break;
            case R.id.money_to:
                //交易记录
                intent = new Intent(getActivity(), TransactionActivity.class);
                getActivity().startActivity(intent);
                break;

            case R.id.measgg_to:
                //出借记录
                intent = new Intent(getActivity(), InvestmentActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.share_to:
                intent = new Intent(getActivity(), ShareActivity.class);
                getActivity().startActivity(intent);
                break;

        }
    }

    private void call() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CALL_PHONE}, 1);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + "4000651520"));
            startActivity(intent);
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
//            name.setText((String) SharedPreferencesUtils.getParam(getActivity(), "name", ""));
            toatal.setText((String) SharedPreferencesUtils.getParam(getActivity(), "total1", "0"));
            eableuse.setText((String) SharedPreferencesUtils.getParam(getActivity(), "usableAmount", "0"));


            selectUserIndex();
        }
    }

    private void selectUserIndex() {

        NetWorks.selectUserIndex(new Subscriber<CenterIndexBean>() {
                                     @Override
                                     public void onCompleted() {

                                     }

                                     @Override
                                     public void onError(Throwable e) {

                                         Logger.json(e.toString());
                                     }

                                     @Override
                                     public void onNext(CenterIndexBean loginBean) {
                                         if (loginBean.getState().getStatus() == 0) {
                                             SharedPreferencesUtils.savaUser2(getActivity(), loginBean);
//                                             name.setText(SharedPreferencesUtils.getUserName(getActivity()));
                                             toatal.setText(loginBean.getTotal1() + "");
                                             toatal2.setText(loginBean.getTotal2() + "");
                                             eableuse.setText(loginBean.getTotal3() + "");
                                             usableamount.setText(loginBean.getUsableAmount() + "");
                                         } else if (loginBean.getState().getStatus() == 99) {
                                             netLogin();
                                         }
                                     }
                                 }
        );
    }

    private void netLogin() {
        String name = SharedPreferencesUtils.getUserName(getContext());
        String psw = SharedPreferencesUtils.getPassword(getContext());


        NetWorks.login(name, psw, new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        Logger.json(e.toString());
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean.getState().getStatus() == 0) {
                            SharedPreferencesUtils.savaUser(getActivity(), loginBean, SharedPreferencesUtils.getPassword(getActivity()));

                            selectUserIndex();

                        } else if (loginBean.getState().getStatus() == 15) {
                            SharedPreferencesUtils.setParam(getContext(), "islogin", false);
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            SharedPreferencesUtils.setParam(getContext(), "islogin", false);
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                }
        );
    }
}
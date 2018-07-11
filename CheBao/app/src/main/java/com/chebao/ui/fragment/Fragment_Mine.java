package com.chebao.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chebao.Adapter.RecycleAdapter;
import com.chebao.App.Constant;
import com.chebao.R;
import com.chebao.bean.CenterIndexBean;
import com.chebao.bean.LoginBean;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.mine.Activity_discount;
import com.chebao.ui.activity.mine.AnnouncementListActivity;
import com.chebao.ui.activity.mine.ChagerActivity;
import com.chebao.ui.activity.mine.HuiKuanActivity;
import com.chebao.ui.activity.mine.InvestmentActivity;
import com.chebao.ui.activity.security.SecurityActivity;
import com.chebao.ui.activity.mine.TransactionActivity;
import com.chebao.ui.activity.pay.WithdrawActivity;
import com.chebao.ui.activity.login2register.LoginActivity;
import com.chebao.ui.activity.mine.ShareActivity;
import com.chebao.ui.activity.mine.ShareNoteActivity;
import com.chebao.utils.IntentUtils;
import com.chebao.utils.SharedPreferencesUtils;
import com.chebao.utils.onclick.AntiShake;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.T;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class Fragment_Mine extends BaseFragment {


    @Bind(R.id.chager)
    TextView chager;

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

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    List<String> explain;
    RecycleAdapter adapter;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    /**
     * find view from layout and set listener
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT >= 21) {
//            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//        //透明状态栏
//              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getActivity().getWindow();
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }




        View rootView = inflater.inflate(R.layout.fragment_mine,
                null);
        ButterKnife.bind(this, rootView);

        //标题内容  ChagerActivity
        SharedPreferencesUtils.getUserName(getContext());
        netLogin();
        init();
        return rootView;
    }

    public void init() {
        List<String> list = new ArrayList<String>();
        list.add("出借记录");
        list.add("回款查询");
        list.add("我的红包");
        list.add("交易记录");
        list.add("实名认证");
        list.add("邀请好友");
        list.add("邀请记录");
        list.add("更多");

        explain = new ArrayList<String>();
        explain.add("查看出借明细");
        explain.add("查看回款明细");
        explain.add("查看红包明细");
        explain.add("查看交易明细");
        explain.add("让账户更安全");
        explain.add("返利加息");
        explain.add("返利加息");
        explain.add("敬请期待");

        List<Integer> drawbles = new ArrayList<Integer>();
        drawbles.add(R.mipmap.icon_mine_investment);
        drawbles.add(R.mipmap.icon_mine_huikuan);
        drawbles.add(R.mipmap.icon_mine_hongbao);
        drawbles.add(R.mipmap.icon_mine_transaction);
        drawbles.add(R.mipmap.icon_mine_certification);
        drawbles.add(R.mipmap.icon_mine_share);
        drawbles.add(R.mipmap.icon_share_note);
        drawbles.add(R.mipmap.icon_mine_more);


        adapter = new RecycleAdapter(getActivity(), list, explain, drawbles);
        //设置布局管理器
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL_LIST));
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        //添加分割线
        //设置Adapter
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorPrimary});
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = null;
                if ((Boolean) SharedPreferencesUtils.getParam(getActivity(), "islogin", false)) {
                    selectUserIndex();
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    T.ShowToastForLong(getActivity(), "未登录");
                }


            }
        });
        //解决滑动冲突
        recyclerView.setNestedScrollingEnabled(false);
        adapter.setOnItemClickLitener(new RecycleAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if (AntiShake.check(view.getId())) {    //判断是否多次点击
                    return;
                }

                if (!(Boolean) SharedPreferencesUtils.getParam(getActivity(), "islogin", false)) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    T.ShowToastForLong(getActivity(), "未登录");
                    return;
                }
                Intent intent = null;
                switch (position) {
                    case 0:
                        //出借记录
                        intent = new Intent(getActivity(), InvestmentActivity.class);
                        getActivity().startActivity(intent);
                        break;
                    case 1:
                        //回款查询
                        intent = new Intent(getActivity(), HuiKuanActivity.class);
                        getActivity().startActivity(intent);
                        break;
                    case 2:
                        //我的红包
                        intent = new Intent(getActivity(), Activity_discount.class);
                        getActivity().startActivity(intent);
                        break;
                    case 3:
                        //交易记录
                        intent = new Intent(getActivity(), TransactionActivity.class);
                        getActivity().startActivity(intent);
                        break;
                    case 4:
                        //实名认证
                        intent = new Intent(getActivity(), SecurityActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        //邀请好友
                        intent = new Intent(getActivity(), ShareActivity.class);
                        getActivity().startActivity(intent);
                        break;
                    case 6:
                        //邀请好友记录
                        intent = new Intent(getActivity(), ShareNoteActivity.class);
                        getActivity().startActivity(intent);
                        break;
                }
            }
        });
    }

    /**
     * init data
     */
    @Override
    public void fillDate() {

    }

    //动态权限结果返回
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


    }

    /**
     * network request
     */
    @Override
    public void requestData() {
//        selectUserIndex();
    }


    @OnClick({R.id.withdraw, R.id.chager, R.id.set, R.id.eye_set, R.id.cell_phohe, R.id.xiaoxi})
    public void onClick(View view) {
        Intent intent = null;
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        if (!(Boolean) SharedPreferencesUtils.getParam(getActivity(), "islogin", false)) {

            intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            T.ShowToastForLong(getActivity(), "未登录");
            return;
        }

        switch (view.getId()) {

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
                    eableuse.setText((String) SharedPreferencesUtils.getParam(getActivity(), "total3", "0"));
                    toatal2.setText((String) SharedPreferencesUtils.getParam(getActivity(), "total2", "0"));
                    IsGone = false;
                }

                break;


            case R.id.set:
                //安全设置
                intent = new Intent(getActivity(), SecurityActivity.class);
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


//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        if (!hidden) {
////            name.setText((String) SharedPreferencesUtils.getParam(getActivity(), "name", ""));
//            toatal.setText((String) SharedPreferencesUtils.getParam(getActivity(), "total1", "0"));
//            eableuse.setText((String) SharedPreferencesUtils.getParam(getActivity(), "total3", "0"));
//
//
//            selectUserIndex();
//        }
//    }

    private void selectUserIndex() {

        NetWorks.selectUserIndex(new Subscriber<CenterIndexBean>() {
                                     @Override
                                     public void onCompleted() {

                                     }

                                     @Override
                                     public void onError(Throwable e) {
                                         swipeRefreshLayout.setRefreshing(false);
                                         T.ShowToastForShort(getActivity(), "网络异常");
                                         Logger.json(e.toString());
                                     }

                                     @Override
                                     public void onNext(CenterIndexBean loginBean) {
                                         swipeRefreshLayout.setRefreshing(false);
                                         if (loginBean.getState().getStatus() == 0) {


                                             SharedPreferencesUtils.savaUser2(getActivity(), loginBean);
//                                             name.setText(SharedPreferencesUtils.getUserName(getActivity()));
                                             toatal.setText(loginBean.getTotal1() + "");
                                             toatal2.setText(loginBean.getTotal2() + "");
                                             eableuse.setText(loginBean.getTotal3() + "");

                                             usableamount.setText(loginBean.getUsableAmount() + "");
                                             String huankuan = "暂无回款";
                                             BigDecimal huank = loginBean.getDhksum();
                                             int i = huank.compareTo(new BigDecimal(0));
                                             if (i != 0) {
                                                 huankuan = "回款金额" + huank + "元";
                                             }
                                             explain.set(1, huankuan);
                                             explain.set(2, "还剩" + loginBean.getSumhb() + "张");
                                             adapter.notifyDataSetChanged();
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

                        }
//                        else if (loginBean.getState().getStatus() == 15) {
//                            SharedPreferencesUtils.setParam(getContext(), "islogin", false);
//                            Intent intent = new Intent(getActivity(), LoginActivity.class);
//                            startActivity(intent);
//                        }
                        else {
                            SharedPreferencesUtils.setParam(getContext(), "islogin", false);
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
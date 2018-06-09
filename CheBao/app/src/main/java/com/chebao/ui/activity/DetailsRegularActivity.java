package com.chebao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chebao.R;
import com.chebao.bean.BorrowDetailBean;
import com.chebao.net.NetWorks;
import com.chebao.ui.activity.login2register.LoginActivity;
import com.chebao.utils.SharedPreferencesUtils;
import com.chebao.utils.T1changerString;
import com.chebao.widget.CircleProgress;
import com.chebao.widget.countdownview.CountdownView;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.DateUtils;
import com.pvj.xlibrary.utils.T;

import java.io.Serializable;
import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * 创建日期：2018/5/4 on 15:04
 * 描述:定期详情
 * 作者:jackson Administrator
 */
public class DetailsRegularActivity extends BaseActivity implements CountdownView.OnCountdownEndListener {
    @Bind(R.id.details_product)
    RelativeLayout product;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.type)
    TextView type;
    @Bind(R.id.maxamount)
    TextView maxAmount;
    @Bind(R.id.data)
    TextView data;
    @Bind(R.id.buy)
    TextView buy;
    @Bind(R.id.interestbearingtime)
    TextView interestBearingTime;
    @Bind(R.id.mininvestamount)
    TextView minInvestAmount;
    @Bind(R.id.tv_countdowntime)
    TextView tv_countdowntime;
    @Bind(R.id.date)
    TextView date;

    //    @Bind(R.id.time)
//    TextView time;
    @Bind(R.id.circleprogress)
    CircleProgress circleprogress;
    @Bind(R.id.countdowntime)
    CountdownView countdowntime;


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

    @Override
    protected void onResume() {
        super.onResume();
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


    private void setData(BorrowDetailBean b) {


        DecimalFormat df = new DecimalFormat("######0.00");
        BorrowDetailBean.DataBean d = b.getData();
        title.setText(d.getBorrowTitle());
//

        if (d.getBorrowType() == 5) {
            circleprogress.setHint(df.format(d.getAnnualRate() - 3) + "%+3%");

        } else {
            circleprogress.setHint(df.format(d.getAnnualRate() - 1) + "%+1%");

        }
//        percent.setText(df.format(d.getAnnualRate()) + "%");
        Spanned text = Html.fromHtml(d.getIntroductionInfos());
//        tv_expand.setText(text);
        minInvestAmount.setText((int) d.getMinInvestAmount() + "起投");
        interestBearingTime.setText(T1changerString.t1chager(d.getInterestBearingTime()));
////
        String deadliness = T1changerString.t2chager(d.getDeadline(), d.getDeadlineType());
        data.setText(deadliness);
        double hasborrwamount = d.getHasBorrowAmount();
        double BorrowAmount = d.getBorrowAmount();
        double progressi = hasborrwamount / BorrowAmount * 10000;
        circleprogress.setValue((float) progressi);

//        int progressint = (int) progressi;
//        progressBar.setProgress(progressint);
//        float protext = (float) (progressi / 100);
//        progress.setText(protext + "%");
//        if (d.getBorrowAmount() >= 10000) {
//            borrowAmount.setText("项目金额：" + d.getBorrowAmount() / 10000 + "万");
//
//        } else {
//            borrowAmount.setText("项目金额：" + d.getBorrowAmount() + "");
//
//        }


        maxAmount.setText("" + T1changerString.t3chager(d.getBorrowAmount() - d.getHasBorrowAmount()));
        type.setText(T1changerString.t4chager(d.getRepayType()));
        long millionSeconds = com.pvj.xlibrary.utils.DateUtils.getTimeSecond(d.getRemainTime() + "");
//        DateUtils.getStrTime(millionSeconds+"");
//        time.setText(millionSeconds + "");
//

        countdowntime.start(millionSeconds);

        countdowntime.setOnCountdownEndListener(this);
        if (d.getBorrowStatus() == 3) {
            buy.setBackgroundColor(this.getResources().getColor(R.color.text_org));
            buy.setText("立即出借");
            tv_countdowntime.setText("剩余时间");
            date.setVisibility(View.GONE);
            countdowntime.setVisibility(View.VISIBLE);

        } else {
            buy.setBackgroundColor(this.getResources().getColor(R.color.bar_clor));
            buy.setClickable(false);
            switch (d.getBorrowStatus()) {
                case 2:
                    date.setVisibility(View.GONE);
                    countdowntime.setVisibility(View.VISIBLE);
                    buy.setText("即将开标");
                    tv_countdowntime.setText("开标倒计时");
                    break;
                case 4:
                    buy.setText("满标审核中");

                    countdowntime.setVisibility(View.GONE);
                    date.setVisibility(View.VISIBLE);
                    date.setText(DateUtils.getStrTime2(d.getFullTime()+""));
                    break;
                case 5:
                    buy.setText("正在还款");
                    countdowntime.setVisibility(View.GONE);
                    date.setVisibility(View.VISIBLE);
                    date.setText(DateUtils.getStrTime2(d.getFullTime()+""));
                    tv_countdowntime.setText("满标时间");
                    break;
                case 6:
                    buy.setText("还款结束");
                    countdowntime.setVisibility(View.GONE);
                    date.setVisibility(View.VISIBLE);
                    date.setText(DateUtils.getStrTime3(d.getFullTime()+""));

                    tv_countdowntime.setText("满标时间");

                    break;
                case 9:

                    buy.setText("已流标");

                    break;
            }


        }

        if (d.getBorrowAmount() - d.getHasBorrowAmount() <= 0) {
//            hitiIslogin.setKeyListener(null);
//            textviewMoney.setVisibility(View.VISIBLE);
//            textviewMoney.setText("该标已满，没有剩余额度可投！");
        }


//        if ((Boolean) SharedPreferencesUtils.getParam(this, "islogin", false)) {
//            tv_money.setText("" + (String) SharedPreferencesUtils.getParam(this, "usableAmount", "0"));
////            hitiIslogin.setHint("可用总额:" + (String) SharedPreferencesUtils.getParam(this, "usableAmount", "0"));
//
//        } else {
//        }

        //  holder.circleProgressbar.setProgress(T1changerString.progress(d.getBorrowStatus(),d.getHasBorrowAmount(),d.getBorrowAmount()));
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
//                    bundle1.putSerializable("data", (Serializable) oneBean);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("bean", (Serializable) bean);
                bundle1.putString("id", id + "");
                intent.putExtras(bundle1);
                startActivity(intent);
                break;

            case R.id.buy:
                if ((Boolean) SharedPreferencesUtils.getParam(this, "islogin", false)) {
                    intent = new Intent(this, PayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", (Serializable) bean);
                    bundle.putString("id", id + "");
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    intent = new Intent(DetailsRegularActivity.this, LoginActivity.class);
                    startActivity(intent);
                    T.ShowToastForLong(DetailsRegularActivity.this, "未登录");

                }

                break;

        }
    }

    @Override
    public void onEnd(CountdownView cv) {
        Object tag = cv.getTag();
        if (bean.getData().getBorrowStatus() == 2) {
            buy.setBackgroundColor(this.getResources().getColor(R.color.text_org));
            buy.setClickable(true);
            buy.setText("立即出借");
        }


    }
}

package com.chebao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;


import com.chebao.R;
import com.chebao.bean.AnnouncementBean;
import com.chebao.bean.ConsultationBean;
import com.chebao.bean.InfoBean;
import com.chebao.bean.RansomBean;
import com.chebao.net.NetWorks;
import com.pvj.xlibrary.loadinglayout.LoadingLayout;
import com.pvj.xlibrary.log.Logger;
import com.pvj.xlibrary.utils.DateUtils;
import com.pvj.xlibrary.utils.T;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * 平台公告详情
 */

public class AnndatilsActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.biaoti)
    TextView biaoti;
    @Bind(R.id.laiyuan)
    TextView laiyuan;
    @Bind(R.id.neirong)
    TextView neirong;

    int type = -1;
    int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anndatils);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        type = intent.getIntExtra("type", -1);
        if (type == 0) {
            title.setText("平台公告");
            AnnouncementBean.DataBean b = (AnnouncementBean.DataBean) getIntent().getSerializableExtra("data");

            biaoti.setText(b.getNoticeTitle());
            laiyuan.setText("来源:官方公告"+ DateUtils.getStrTime2(b.getCreateTime()+""));


            Spanned text = Html.fromHtml(b.getNoticeContent());
            //  tv.setText(text);
            neirong.setText("        "+text);
//            net();

        } else {
            title.setText("媒体报道");
//            netApp();
            ConsultationBean.DataBean b = (ConsultationBean.DataBean) getIntent().getSerializableExtra("data");

            biaoti.setText(b.getTitle());
            laiyuan.setText("来源:媒体报道"+ DateUtils.getStrTime2(b.getCreateTime()+""));


            Spanned text = Html.fromHtml(b.getContent());
            //  tv.setText(text);
            neirong.setText("        "+text);

        }



    }

//    private void net() {
//        NetWorks.showNotice(id + "", new Subscriber<AnnouncementBean>() {
//
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Logger.json(e.toString());
//                T.ShowToastForShort(AnndatilsActivity.this, "网络异常");
//            }
//
//            @Override
//            public void onNext(AnnouncementBean bean) {
//                if (bean.getState().getStatus() == 0) {
//                    Spanned text = Html.fromHtml(bean.getData().get(0).getNoticeContent());
//                    neirong.setText("        " + text);
//
//                } else {
//                    T.ShowToastForShort(AnndatilsActivity.this, bean.getState().getInfo());
//                }
//
//            }
//        });
//
//
//    }

    private void netApp() {
        NetWorks.consultationApp(id + "", new Subscriber<InfoBean>() {

            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Logger.json(e.toString());
                T.ShowToastForShort(AnndatilsActivity.this, "网络异常");
            }

            @Override
            public void onNext(InfoBean bean) {
                if (bean.getState().getStatus() == 0) {


                } else {
                    T.ShowToastForShort(AnndatilsActivity.this, bean.getState().getInfo());
                }

            }
        });


    }

}
